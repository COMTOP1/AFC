package com.bswdi.servlets;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Edit programme season servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/editprogrammeseason"})
public class EditProgrammeSeasonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EditProgrammeSeasonServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, response, con);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                int id = Integer.parseInt(request.getParameter("id"));
                ProgrammeSeasons programmeSeason = DBUtils.findProgrammeSeason(con, id);
                request.getSession().setAttribute("programmeSeason", programmeSeason);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/editProgrammeSeasonPage.jsp");
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
        ProgrammeSeasons programmeSeasonOld = null;
        try {
            programmeSeasonOld = (ProgrammeSeasons) request.getSession().getAttribute("programmeSeason");
            request.getSession().setAttribute("programmeSeason", null);
        } catch (Exception e) {
            request.getSession().setAttribute("programmeSeason", null);
        }
        season = request.getParameter("season");
        try {
            assert programmeSeasonOld != null;
            ProgrammeSeasons programmeSeason = new ProgrammeSeasons(programmeSeasonOld.getID(), season);
            DBUtils.updateProgrammeSeason(con, programmeSeason);
            response.sendRedirect("programmes");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("programmes");
        }
    }
}
