package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Add programme season servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/addprogrammeseason"})
public class AddProgrammeSeasonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public AddProgrammeSeasonServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, response, con);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addProgrammeSeasonPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("programmes");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("programmes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String season;
        season = request.getParameter("season");
        try {
            ProgrammeSeasons programmeSeason = new ProgrammeSeasons(0, season);
            DBUtils.insertProgrammeSeason(con, programmeSeason);
            response.sendRedirect("programmes");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("programmes");
        }
    }
}
