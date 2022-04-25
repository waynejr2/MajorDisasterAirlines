package Objects;

import java.sql.*;

public class databaseConnector {
    static Connection connection = null;
    static String dbUrl = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3476516";
    static String dbUser = "sql3476516";
    static String dbPassword = "9lmMyhQM6u";

    public static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(
                        dbUrl,
                        dbUser,
                        dbPassword);
                if (connection != null) {
                    System.out.println("Connected to the database!");
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return connection;
    }

    public Connection getConnector() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        dbUrl,
                        dbUser,
                        dbPassword);
                if (connection != null) {
                    System.out.println("Connected to the database!");
                }
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return connection;
    }

    public static ResultSet getResultSet(String sql) throws SQLException {
        Connection conn = databaseConnector.getConnection();
        Statement myStmt = conn.createStatement();
        return myStmt.executeQuery(sql);
    }
}
