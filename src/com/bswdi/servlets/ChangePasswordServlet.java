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
 * Change password servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/changepassword"})
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public ChangePasswordServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if ((boolean) request.getSession().getAttribute("allowedOwnUserModification")) {
                request.getSession().setAttribute("allowedOwnUserModification", false);
                Connection con = MyUtils.getStoredConnection(request);
                String email = request.getParameter("email");
                String errorString = null;
                Users user = null;
                try {
                    user = DBUtils.findUser(con, email);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    errorString = e.getMessage();
                    e.printStackTrace();
                }
                if (errorString != null || user == null) {
                    response.sendRedirect("account");
                    return;
                }
                request.setAttribute("error", errorString);
                request.setAttribute("user", user);
                request.setAttribute("old", email);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/changePasswordPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("account");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("account");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String email = MyUtils.getEmailInCookie(request), passwordOld, passwordNew1, passwordNew2, errorString = null;
        try {
            passwordOld = request.getParameter("passwordold");
            if (DBUtils.checkPassword(con, email, passwordOld)) {
                passwordNew1 = request.getParameter("passwordnew1");
                passwordNew2 = request.getParameter("passwordnew2");
                if (passwordNew1.equals(passwordNew2)) {
                    DBUtils.changePassword(con, email, passwordNew1);
                    request.getSession().setAttribute("passwordchanged", "Password has been changed");
                } else request.getSession().setAttribute("error", "Can't change password");
            } else request.getSession().setAttribute("error", "Can't change password");
        } catch (Exception e) {
            errorString = e.getMessage();
            e.printStackTrace();
        }
        request.setAttribute("error", errorString);
        response.sendRedirect("account");
    }
}
