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
 * Add team servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/addteam"})
@MultipartConfig(maxFileSize = 16177215)
public class AddTeamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public AddTeamServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addTeamPage.jsp");
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
        String name, league, division, leagueTable, fixtures, coach, physio, teamPhoto = null;
        boolean active, youth;
        int ages;
        name = request.getParameter("name");
        league = request.getParameter("league");
        division = request.getParameter("division");
        leagueTable = request.getParameter("leagueTable");
        if (leagueTable == null || leagueTable.equals("")) leagueTable = "#";
        fixtures = request.getParameter("fixtures");
        if (fixtures == null || fixtures.equals("")) fixtures = "#";
        coach = request.getParameter("coach");
        physio = request.getParameter("physio");
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
            if (teamPhoto.equals("")) teamPhoto = null;
            inputStream.close();
            outputStream.close();
        }
        try {
            ages = Integer.parseInt(request.getParameter("ages"));
            if (ages < 0) ages = 0;
        } catch (Exception e) {
            ages = 0;
        }
        try {
            Teams team = new Teams(0, name, league, division, leagueTable, fixtures, coach, physio, teamPhoto, active, youth, ages);
            DBUtils.insertTeam(con, team);
            response.sendRedirect("teams");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", e.toString());
            response.sendRedirect("teams");
        }
    }
}
