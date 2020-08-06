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
 * Delete news servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/deletenews"})
public class DeleteNewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public DeleteNewsServlet() {
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
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/deleteNewsConfirmationPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("news");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("news");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        try {
            DBUtils.deleteNews(con, Integer.parseInt(request.getParameter("id")));
            response.sendRedirect("news");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("news");
        }
    }
}
