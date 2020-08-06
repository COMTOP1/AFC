package com.bswdi.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Information servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/info"})
public class InfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public InfoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/infoPage.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
