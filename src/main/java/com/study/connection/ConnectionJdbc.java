package com.study.connection;

import java.sql.*;

public class ConnectionJdbc {

    static final String DB_URL = "jdbc:mysql://localhost:3306/ebrainsoft_study";
    static final String USER = "ebsoft";
    static final String PASS = "ebsoft";

    public static Connection getConnection() {
        Connection conn = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("Error in ConnectionJdbc!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return conn;
    }

    public static void releaseResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
