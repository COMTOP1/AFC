package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Add programme servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/addprogramme"})
public class AddProgrammeFirstServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public AddProgrammeFirstServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, response, con);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addProgrammeFirstPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("programmes");
        } catch (Exception e) {
            response.sendRedirect("programmes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        long dateOfProgramme = 0L;
        int programmeSeasonID = 0;
        if (name == null || name.equals("")) {
            request.getSession().setAttribute("error", "Name must not be empty");
            response.sendRedirect("addprogramme");
        } else {
            try {
                String dateString = request.getParameter("dateOfProgramme");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(dateString);
                dateOfProgramme = date.getTime();
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Please enter a date");
                response.sendRedirect("addprogramme");
            }
            try {
                programmeSeasonID = Integer.parseInt(request.getParameter("programmeSeason"));
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Please select a season");
                response.sendRedirect("addprogramme");
            }
            if (dateOfProgramme > 0) {
                request.getSession().setAttribute("programmeName", name);
                request.getSession().setAttribute("programmeDate", dateOfProgramme);
                request.getSession().setAttribute("programmeSeasonID", programmeSeasonID);
                request.getSession().setAttribute("allowProgrammeSecond", true);
                response.sendRedirect("addprogrammesecond");
            } else {
                request.getSession().setAttribute("error", "Date must not be empty");
                response.sendRedirect("addprogramme");
            }
        }
    }
}
