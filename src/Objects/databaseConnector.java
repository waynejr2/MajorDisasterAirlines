package Objects;

import java.sql.*;
import java.util.ArrayList;

public class databaseConnector {
    static Connection conn = null;
    static String db_url = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3476516";
    static String db_user = "sql3476516";
    static String db_password = "9lmMyhQM6u";

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                        db_url,
                        db_user,
                        db_password);
                if (conn != null) {
                    System.out.println("Connected to the database!");
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return conn;
    }

    public Connection getConnector() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                        db_url,
                        db_user,
                        db_password);
                if (conn != null) {
                    System.out.println("Connected to the database!");
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return conn;
    }
}
