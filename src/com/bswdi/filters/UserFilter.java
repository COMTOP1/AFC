package com.bswdi.filters;

import com.bswdi.beans.Users;
import com.bswdi.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * User filter
 *
 * @author BSWDI
 * @version 1.0
 */
@WebFilter(filterName = "userFilter", urlPatterns = {"/*"})
public class UserFilter implements Filter {

    /**
     * Constructor
     */
    public UserFilter() {

    }

    @Override
    public void init(FilterConfig fConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        Connection con = MyUtils.getStoredConnection(req);
        Users user = MyUtils.getUser(req, res, con);
        if (MyUtils.getUser(req, res, con) != null && user == null) MyUtils.deleteUserCookie(res);
        boolean updatePassword = false;
        try {
            updatePassword = (boolean) req.getSession().getAttribute("UPDATE PASSWORD");
        } catch (Exception ignored) {

        }
        if (user != null && updatePassword && !req.getServletPath().contains("changepassword")) {
            res.sendRedirect("changepassword?email=" + user.getEmail());
        } else {
            chain.doFilter(request, response);
        }
    }
}
