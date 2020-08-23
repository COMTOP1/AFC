package com.bswdi.servlets;

import java.io.File;
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
 * Delete document servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/deletedocument"})
public class DeleteDocumentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public DeleteDocumentServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            assert user != null;
            if (user.getRole() > 0) {
                request.setAttribute("id", id);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/deleteDocumentConfirmationPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("documents");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("documents");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        try {
            DBUtils.deleteDocument(con, Integer.parseInt(request.getParameter("id")));
            //String filepath = "C:\\Users\\ADMIN\\Desktop\\Code\\Java\\AFC\\FileStore\\";
            //String filepath = System.getProperty("catalina.home") + "/FileStore";
            String filepath = "/Users/liam/Desktop/Code/Java/AFC/FileStore/";
            File file = new File(filepath);
            boolean deleted;
            deleted = file.delete();
            if (!deleted) request.getSession().setAttribute("error", "Couldn't delete file from file system");
            response.sendRedirect("documents");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("documents");
        }
    }
}