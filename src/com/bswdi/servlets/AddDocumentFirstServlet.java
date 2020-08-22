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
 * Add document first servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/adddocument"})
public class AddDocumentFirstServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public AddDocumentFirstServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            assert user != null;
            if (user.getRole() > 0) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addDocumentFirstPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("documents");
        } catch (Exception e) {
            response.sendRedirect("documents");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null || name.equals("")) {
            request.getSession().setAttribute("error", "Name must not be empty");
            response.sendRedirect("adddocument");
        } else {
            request.getSession().setAttribute("documentName", name);
            request.getSession().setAttribute("allow", true);
            response.sendRedirect("adddocumentsecond");
        }
    }
}
