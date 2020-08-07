package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.Users;
import com.bswdi.utils.*;

/**
 * Delete user servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/deleteuser"})
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public DeleteUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request), email1 = request.getParameter("email");
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            if ((user.getRole() == 1 || user.getRole() > 4) && !Objects.equals(email, "liamb1216@gmail.com")) {
                request.setAttribute("email", email1);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/deleteUserConfirmationPage.jsp");
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
        try {
            DBUtils.deleteUser(con, request.getParameter("email"));
            response.sendRedirect("users");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("users");
        }
    }
}
