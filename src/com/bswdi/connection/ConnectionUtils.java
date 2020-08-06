package com.bswdi.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection utilities
 *
 * @author Liam Burnand
 * @version 1.0
 */
public class ConnectionUtils {

    /**
     * Return connection to database
     *
     * @return Connection con
     * @throws ClassNotFoundException throws ClassNotFoundException
     * @throws SQLException           throws SQLException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return MySQLConUtils.getMySQLConnection();
    }

    /**
     * Closes connection quietly
     *
     * @param con connection
     */
    public static void closeQuietly(Connection con) {
        try {
            con.close();
        } catch (Exception ignored) {

        }
    }

    /**
     * Rolls back Connection
     *
     * @param con connection
     */
    public static void rollbackQuietly(Connection con) {
        try {
            con.rollback();
        } catch (Exception ignored) {

        }
    }
}
