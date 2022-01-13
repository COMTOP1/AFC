package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;

import javax.management.BadStringOperationException;
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
            boolean modification = false, update = false;
            try {
                modification = (boolean) request.getSession().getAttribute("allowedOwnUserModification");
            } catch (Exception ignored) {

            } try {
                update = (boolean) request.getSession().getAttribute("UPDATE PASSWORD");
            } catch (Exception ignored) {

            }
            if (modification || update) {
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
        String email = MyUtils.getEmailInCookie(request), passwordOld = "", passwordNew1 = "", passwordNew2 = "", errorString = null;
        byte[] hash, salt;
        try {
            passwordOld = request.getParameter("passwordold");
            if (DBUtils.checkPassword(con, email, passwordOld)) {
                passwordNew1 = request.getParameter("passwordnew1");
                passwordNew2 = request.getParameter("passwordnew2");
                if (passwordNew1.equals(passwordNew2)) {
                    salt = MyUtils.getNextSalt();
                    hash = MyUtils.hash(passwordNew1.toCharArray(), salt);
                    if (MyUtils.verifyPassword(passwordNew1.toCharArray(), salt, hash)) {
                        Arrays.fill(passwordOld.toCharArray(), Character.MIN_VALUE);
                        Arrays.fill(passwordNew1.toCharArray(), Character.MIN_VALUE);
                        Arrays.fill(passwordNew2.toCharArray(), Character.MIN_VALUE);
                        DBUtils.changePassword(con, email, hash, salt);
                        request.getSession().setAttribute("passwordchanged", "Password has been changed");
                        request.getSession().setAttribute("UPDATE PASSWORD", false);
                        request.getSession().setAttribute("UPDATE PASSWORD NOTIFICATION", null);
                    } else throw new Exception("Failed to verify new hash!");
                } else request.getSession().setAttribute("error", "Can't change password");
            } else request.getSession().setAttribute("error", "Can't change password");
        } catch (Exception e) {
            Arrays.fill(passwordOld.toCharArray(), Character.MIN_VALUE);
            Arrays.fill(passwordNew1.toCharArray(), Character.MIN_VALUE);
            Arrays.fill(passwordNew2.toCharArray(), Character.MIN_VALUE);
            errorString = e.getMessage();
            e.printStackTrace();
        }
        request.setAttribute("error", errorString);
        response.sendRedirect("account");
    }
}
