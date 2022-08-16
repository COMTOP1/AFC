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
 * Edit user servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/edituser"})
@MultipartConfig(maxFileSize = 16177215)
public class EditUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EditUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, con);
            assert user != null;
            if (user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) {
                Users user1 = DBUtils.findUser(con, email);
                request.getSession().setAttribute("user", user1);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/editUserPage.jsp");
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
        String name, email, phone, image;
        int teamID;
        Role role;
        Users userOld = null;
        try {
            userOld = (Users) request.getSession().getAttribute("user");
            request.getSession().setAttribute("user", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        name = request.getParameter("name");
        email = request.getParameter("email");
        phone = request.getParameter("phone");
        try {
            teamID = Integer.parseInt(request.getParameter("teamID"));
            if (teamID < 0) teamID = 0;
        } catch (Exception e) {
            teamID = 0;
        }
        role = Role.getRole(request.getParameter("role"));
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
        } else {
            assert userOld != null;
            image = userOld.getImage();
        }
        try {
            Users user = new Users(name, email, phone, teamID, role, image);
            assert userOld != null;
            DBUtils.updateUser(con, user, userOld.getEmail());
            request.getSession().setAttribute("success", "User successfully added");
            response.sendRedirect("users");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", e.toString());
            response.sendRedirect("edituser");
        }
    }
}
