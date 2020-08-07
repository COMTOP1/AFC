package com.bswdi.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Edit team servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/editteam"})
@MultipartConfig(maxFileSize = 16177215)
public class EditTeamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EditTeamServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            if (user.getRole() == 1 || user.getRole() > 4) {
                Teams team = DBUtils.findTeam(con, id);
                request.getSession().setAttribute("team", team);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/editTeamPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("teams");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("teams");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String name, league, division, leagueTable, fixtures, coach, teamPhoto;
        boolean active, youth;
        int ages;
        Teams teamOld = null;
        try {
            teamOld = (Teams) request.getSession().getAttribute("team");
            request.getSession().setAttribute("team", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        name = request.getParameter("name");
        league = request.getParameter("league");
        division = request.getParameter("division");
        leagueTable = request.getParameter("leagueTable");
        fixtures = request.getParameter("fixtures");
        coach = request.getParameter("coach");
        active = "Y".equals(request.getParameter("active"));
        youth = "Y".equals(request.getParameter("youth"));
        InputStream inputStream;
        Part filePart;
        try {
            filePart = request.getPart("teamPhoto");
        } catch (Exception e) {
            e.printStackTrace();
            filePart = null;
        }
        if (filePart != null) {
            inputStream = filePart.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, bytesRead);
            byte[] imageBytes = outputStream.toByteArray();
            teamPhoto = Base64.getEncoder().encodeToString(imageBytes);
            if (teamPhoto.equals("") || teamPhoto.length() == 0) {
				assert teamOld != null;
				teamPhoto = teamOld.getTeamPhoto();
			}
            inputStream.close();
            outputStream.close();
        } else {
			assert teamOld != null;
			teamPhoto = teamOld.getTeamPhoto();
		}
        try {
            ages = Integer.parseInt(request.getParameter("ages"));
            if (ages < 0) ages = 0;
        } catch (Exception e) {
            ages = 0;
        }
        try {
            assert teamOld != null;
            Teams team = new Teams(teamOld.getID(), name, league, division, leagueTable, fixtures, coach, teamPhoto, active, youth, ages);
            DBUtils.updateTeam(con, team);
            response.sendRedirect("teams");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", e.toString());
            response.sendRedirect("teams");
        }
    }
}
