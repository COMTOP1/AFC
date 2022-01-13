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
 * Add user servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/adduser"})
@MultipartConfig(maxFileSize = 16177215)
public class AddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public AddUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            assert user != null;
            if (user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addUserPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("users");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String name, email, phone, image = null;
        int teamID;
        Role role;
        name = request.getParameter("name");
        email = request.getParameter("email");
        phone = request.getParameter("phone");
        try {
            teamID = Integer.parseInt(request.getParameter("teamID"));
            if (teamID < 0) teamID = 0;
        } catch (Exception e) {
            teamID = 0;
        }
        role = Role.valueOf(request.getParameter("role"));
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
        try {
            Users user = new Users(name, email, phone, teamID, role, image);
            byte[] salt = MyUtils.getNextSalt(), hash = MyUtils.hash("AFCpa£$word".toCharArray(), salt);
            if (MyUtils.verifyPassword("AFCpa£$word".toCharArray(), salt, hash)) {
                DBUtils.insertUser(con, user, hash, salt);
                request.getSession().setAttribute("success", "User successfully added");
                response.sendRedirect("users");
            } else throw new Exception("Failed to verify hash!");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", e.toString());
            response.sendRedirect("adduser");
        }
    }
}
