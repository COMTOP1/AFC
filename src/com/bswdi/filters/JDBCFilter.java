package com.bswdi.filters;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.bswdi.connection.ConnectionUtils;
import com.bswdi.utils.MyUtils;

/**
 * JDBC filter
 *
 * @author BSWDI
 * @version 1.0
 */
@WebFilter(filterName = "jdbcFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter {

    /**
     * Constructor
     */
    public JDBCFilter() {

    }

    @Override
    public void init(FilterConfig fConfig) {

    }

    @Override
    public void destroy() {

    }

    private boolean needJDBC(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String urlPattern = servletPath;
        if (pathInfo != null) urlPattern = servletPath + "/*";
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) return true;
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (this.needJDBC(req)) {
            Connection con = null;
            try {
                con = MyUtils.getStoredConnection(request);
                ConnectionUtils.closeQuietly(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                con = ConnectionUtils.getConnection();
                con.setAutoCommit(false);
                MyUtils.storeConnection(request, con);
                chain.doFilter(request, response);
                con.commit();
            } catch (Exception e) {
                ConnectionUtils.rollbackQuietly(con);
                e.printStackTrace();
                throw new ServletException();
            } finally {
                ConnectionUtils.closeQuietly(con);
            }
        } else chain.doFilter(request, response);
    }
}
