package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.News;
import com.bswdi.utils.DBUtils;
import com.bswdi.utils.MyUtils;

/**
 * What's on servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/news"})
public class NewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public NewsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id <= 0) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/newsPage.jsp");
                dispatcher.forward(request, response);
            } else {
                Connection con = MyUtils.getStoredConnection(request);
                News news = DBUtils.findNews(con, id);
                if (news == null) response.sendRedirect("news");
                else {
                    request.setAttribute("news", news);
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/newsArticlePage.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception e) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/newsPage.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
