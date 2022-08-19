package com.bswdi.filters;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bswdi.beans.Users;
import com.bswdi.utils.*;

/**
 * Cookie filter
 *
 * @author BSWDI
 * @version 1.0
 */
@WebFilter(filterName = "cookieFilter", urlPatterns = {"/*"})
public class CookieFilter implements Filter {

    /**
     * Constructor
     */
    public CookieFilter() {

    }

    @Override
    public void init(FilterConfig fConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Connection con = MyUtils.getStoredConnection(request);
        String checked = (String) session.getAttribute("COOKIE_CHECKED");
        if (checked == null && con != null) {
            Users user = null;
            try {
                user = MyUtils.getUser(req, res, con);
                req.setAttribute("loggedInUser", user);
            } catch (Exception e) {
                if (user != null) e.printStackTrace();
            }
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
        }
        chain.doFilter(request, response);
    }
}
