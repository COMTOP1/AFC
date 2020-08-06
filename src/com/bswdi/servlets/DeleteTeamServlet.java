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
import com.bswdi.utils.*;

/**
 * Delete team servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/deleteteam"})
public class DeleteTeamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public DeleteTeamServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            if (user.getRole() > 0) {
                request.setAttribute("id", id);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/deleteTeamConfirmationPage.jsp");
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
        try {
            DBUtils.deleteTeam(con, Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("teams");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("teams");
        }
    }
}
