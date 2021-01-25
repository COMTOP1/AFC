package com.bswdi.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL connection utilities
 *
 * @author BSWDI
 * @version 1.0
 */
public class MySQLConUtils {

    /**
     * Supplies details for database connection and return connection to database
     *
     * @return Connection con
     * @throws ClassNotFoundException throws ClassNotFoundException
     * @throws SQLException           throws SQLException
     */
    public static Connection getMySQLConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String DBName = "AFC";
        String username = "websiteAccess";
        String password = "websitePassword";
        return getMySQLConnection(hostName, DBName, username, password);
    }

    /**
     * Return connects to database
     *
     * @param hostName hostname
     * @param DBName   database name
     * @param username username
     * @param password password
     * @return Connection con
     * @throws ClassNotFoundException throws ClassNotFoundException
     * @throws SQLException           throws SQLException
     */
    public static Connection getMySQLConnection(String hostName, String DBName, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + DBName + "?requireSSL=true&serverTimezone=GMT";
        return DriverManager.getConnection(connectionURL, username, password);
    }
}
