package Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ClassName describe what class does
 * @author
 */
public class User {

    public String username;
    private String password;

    public String firstName;
    public String lastName;
    public String emailAddress;
    public String phoneNumber;

    /**
     *
     * @param username
     * @param password
     * @throws SQLException
     */
    public User(String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
        Connection conn = databaseConnector.getConnection();
        Statement myStmt = conn.createStatement();

        //command to insert user into the database
        String sql = "INSERT INTO DMA_users (username, password) VALUES ('" + this.username + "', '" + this.password + "')";
        myStmt.executeUpdate(sql);
    }
}
