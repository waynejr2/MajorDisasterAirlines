package Objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    public String username;
    private String password;

    public String firstName;
    public String lastName;
    public String emailAddress;
    public String phoneNumber;

    public User(String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
        Statement myStmt = createStatmentSQL();

        //command to insert user into the database
        String sql = "INSERT INTO DMA_users (username, password) VALUES ('" + this.username + "', '" + this.password + "')";
        myStmt.executeUpdate(sql);
    }

    //method to create SQL statement in order to execute SQL commands in java
    public static Statement createStatmentSQL() throws SQLException {
        String URL = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3476516";
        String username = "sql3476516";
        String password = "9lmMyhQM6u";
        Connection connection = DriverManager.getConnection(URL, username, password);
        return connection.createStatement();
    }
}
