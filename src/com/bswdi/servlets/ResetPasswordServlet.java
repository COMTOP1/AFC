package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Reset password servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/resetpassword"})
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public ResetPasswordServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = MyUtils.getStoredConnection(request);
            String email = MyUtils.getEmailInCookie(request);
            Users user = DBUtils.findUser(con, email);
            if (user != null && (user.getRole() == 1 || user.getRole() > 4)) {
                String email1 = request.getParameter("email");
                if (email1 == null) response.sendRedirect("users");
                else {
                    DBUtils.resetPassword(con, email1);
                    request.getSession().setAttribute("success", "Password changed successfully");
                    response.sendRedirect("users");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("users");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
