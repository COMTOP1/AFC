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

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Team servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/team"})
public class TeamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public TeamServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id <= 0) response.sendRedirect("teams");
            else {
                Connection con = MyUtils.getStoredConnection(request);
                Teams team = DBUtils.findTeam(con, id);
                String email = MyUtils.getEmailInCookie(request);
                Users user = DBUtils.findUser(con, email);
                if (team == null || !(team.getActive() || Objects.requireNonNull(user).getRole() == 6)) response.sendRedirect("teams");
                else {
                    request.setAttribute("team", team);
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/teamPage.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception e) {
            response.sendRedirect("teams");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
