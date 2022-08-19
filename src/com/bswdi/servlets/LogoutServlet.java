
package com.bswdi.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Logout servlet
 *
 * @author Liam Burnand
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public LogoutServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        Users user = null;
        try {
            user = MyUtils.getUser(request, response, con);
        } catch (Exception ignored) {

        }
        if (user != null) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/logoutPage.jsp");
            dispatcher.forward(request, response);
        } else response.sendRedirect("home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, response, con);
            List<JWTToken> list = DBUtils.queryJWToken(con);
            assert user != null;
            for (JWTToken token : list)
                if (user.getEmail().equals(token.getEmail()))
                    DBUtils.deleteJWTToken(con, token.getId());
            MyUtils.deleteUserCookie(response);
            response.sendRedirect("home");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }
}
