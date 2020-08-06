package com.bswdi.filters;

import com.bswdi.beans.Users;
import com.bswdi.utils.MyUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * User filter
 *
 * @author Liam Burnand
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
        HttpSession session = req.getSession();
        Users user = MyUtils.getLoggedInUser(session);
        if (MyUtils.getEmailInCookie(req) != null && user == null) MyUtils.deleteUserCookie(res);
        chain.doFilter(request, response);
    }
}
