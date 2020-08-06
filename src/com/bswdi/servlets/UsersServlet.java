package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.Users;
import com.bswdi.utils.DBUtils;
import com.bswdi.utils.MyUtils;

/**
 * Users Servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public UsersServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = MyUtils.getEmailInCookie(request);
            Connection con = MyUtils.getStoredConnection(request);
            if (email == null) response.sendRedirect("home");
            else {
                Users user = DBUtils.findUser(con, email);
                if (user.getRole() > 4 || user.getRole() == 1) {
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/usersPage.jsp");
                    dispatcher.forward(request, response);
                } else response.sendRedirect("home");
            }
        } catch (Exception e) {
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
