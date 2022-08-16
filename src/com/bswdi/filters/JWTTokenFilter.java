package com.bswdi.filters;

import com.bswdi.utils.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;

/**
 * JWT token filter
 *
 * @author BSWDI
 * @version 1.0
 */
@WebFilter(filterName = "jwtTokenFilter", urlPatterns = {"/*"})
public class JWTTokenFilter implements Filter {

    /**
     * Constructor
     */
    public JWTTokenFilter() {

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
        Connection con = MyUtils.getStoredConnection(req);
        try {
            DBUtils.deleteJWTTokenExpired(con, MyUtils.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }
}
