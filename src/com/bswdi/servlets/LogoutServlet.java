package com.bswdi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.utils.*;

/**
 * Logout servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public LogoutServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        if (email != null) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/logoutPage.jsp");
            dispatcher.forward(request, response);
        } else response.sendRedirect("home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getSession().removeAttribute("loggedInUser");
            request.getSession().removeAttribute("loggedInUserName");
            request.removeAttribute("loggedInUser");
            request.removeAttribute("loggedInUserName");
            MyUtils.deleteUserCookie(response);
            response.sendRedirect("home");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }
}
