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
 * Add sponsor servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/addsponsor"})
@MultipartConfig(maxFileSize = 16177215)
public class AddSponsorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public AddSponsorServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            if (user.getRole() > 0) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addSponsorPage.jsp");
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
        String name, website, image = null, purpose, teamID;
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
            if (image.equals("")) image = null;
            inputStream.close();
            outputStream.close();
        }
        purpose = request.getParameter("purpose");
        teamID = request.getParameter("teamID");
        if (teamID == null || teamID.equals("")) teamID = "A";
        try {
            Sponsors sponsor = new Sponsors(0, name, website, image, purpose, teamID);
            DBUtils.insertSponsor(con, sponsor);
            response.sendRedirect("sponsors");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("sponsors");
        }
    }
}
