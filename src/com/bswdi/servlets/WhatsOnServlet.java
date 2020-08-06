package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.WhatsOn;
import com.bswdi.utils.DBUtils;
import com.bswdi.utils.MyUtils;

/**
 * What's on servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/whatson"})
public class WhatsOnServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WhatsOnServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id <= 0) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/whatsOnPage.jsp");
                dispatcher.forward(request, response);
            } else {
                Connection con = MyUtils.getStoredConnection(request);
                WhatsOn whatsOn = DBUtils.findWhatsOn(con, id);
                if (whatsOn == null) response.sendRedirect("whatsOn");
                else {
                    request.setAttribute("whatsOn", whatsOn);
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/whatsOnArticlePage.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception e) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/whatsOnPage.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
