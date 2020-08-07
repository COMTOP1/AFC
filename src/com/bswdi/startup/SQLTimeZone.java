package com.bswdi.startup;

import java.sql.Connection;

import javax.servlet.http.HttpServlet;

import com.bswdi.connection.ConnectionUtils;
import com.bswdi.utils.DBUtils;

/**
 * SQL Time zone adjust
 *
 * @author BSWDI
 * @version 1.0
 */
public class SQLTimeZone extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() {
        try {
            Connection con = ConnectionUtils.getConnection();
            DBUtils.SQLTimeZone(con);
            System.out.println("----------");
            System.out.println("---------- SQL Time zone success ---------");
            System.out.println("----------");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("**********");
            System.out.println("********** SQL Time zone failed **********");
            System.out.println("**********");
        }
    }
}
