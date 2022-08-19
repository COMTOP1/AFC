package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Login servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public LoginServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        Users user = null;
        try {
            user = MyUtils.getUser(request, response, con);
        } catch (Exception ignored) {

        }
        if (user == null) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/loginPage.jsp");
            dispatcher.forward(request, response);
        } else response.sendRedirect("home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Users user = null;
        boolean error = false, change = false;
        String errorString = null;
        if (email == null || password == null || email.length() == 0 || password.length() == 0) {
            error = true;
            errorString = "An email and password are required!";
        } else {
            try {
                try {
                    user = DBUtils.login(con, email, password, request);
                } catch (Exception ignored) {
                }
                if (password.equals("AFCpa£$word")) change = true;
				if (user == null) {
                    error = true;
                    errorString = "Email or password are incorrect";
                }
            } catch (Exception e) {
                e.printStackTrace();
                error = true;
                errorString = e.getMessage();
            }
        }
        if (error) {
            request.setAttribute("error", errorString);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/loginPage.jsp");
            dispatcher.forward(request, response);
        } else {
            MyUtils.storeUser(request, response, con, user);
            if (change) response.sendRedirect("changepassword?email=" + user.getEmail());
            else response.sendRedirect("home");
        }
    }
}
