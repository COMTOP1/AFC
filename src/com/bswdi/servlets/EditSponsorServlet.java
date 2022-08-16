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
 * Edit sponsor servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/editsponsor"})
@MultipartConfig(maxFileSize = 16177215)
public class EditSponsorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EditSponsorServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, con);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                int id = Integer.parseInt(request.getParameter("id"));
                Sponsors sponsor = DBUtils.findSponsor(con, id);
                request.getSession().setAttribute("sponsor", sponsor);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/editSponsorPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("sponsors");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("sponsors");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String name, website, image, purpose, teamID;
        Sponsors sponsorOld = null;
        try {
            sponsorOld = (Sponsors) request.getSession().getAttribute("sponsor");
            request.getSession().setAttribute("sponsor", null);
        } catch (Exception e) {
            request.getSession().setAttribute("sponsor", null);
        }
        name = request.getParameter("name");
        website = request.getParameter("website");
        if (website == null || website.equals("")) website = "#";
        InputStream inputStream;
        Part filePart;
        try {
            filePart = request.getPart("image");
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
            image = Base64.getEncoder().encodeToString(imageBytes);
            if (image.equals("")) {
	            assert sponsorOld != null;
				image = sponsorOld.getImage();
            }
            inputStream.close();
            outputStream.close();
        } else {
			assert sponsorOld != null;
			image = sponsorOld.getImage();
		}
        purpose = request.getParameter("purpose");
        teamID = request.getParameter("teamID");
        if (teamID == null || teamID.equals("")) teamID = "A";
        try {
			assert sponsorOld != null;
			Sponsors sponsor = new Sponsors(sponsorOld.getID(), name, website, image, purpose, teamID);
            DBUtils.updateSponsor(con, sponsor);
            response.sendRedirect("sponsors");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("sponsors");
        }
    }
}
