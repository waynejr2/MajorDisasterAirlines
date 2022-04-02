import java.sql.*;
import java.util.ArrayList;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class DatabaseExample {

    public static void main(String[] args) {
        Connection conn = null;
        ArrayList<String> result = new ArrayList();
        String SQL_SELECT = "Select * from airport_locations";
        //Host: sql3.freesqldatabase.com
        //Database name: sql3476516
        //Database user: sql3476516
        //Database password: 9lmMyhQM6u
        //Port number: 3306
        //MainFrame mf = new MainFrame();
        System.out.println("Howdy World, Java app");

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3476516",
                    "sql3476516",
                    "9lmMyhQM6u");
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
            {
                //DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3476516" +
                //        "&user=sql3476516&password=9lmMyhQM6u");

                // Do something with the Connection

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String location_name = resultSet.getString("location_name");
                    System.out.println(id + "  " + location_name);
                }
            }


          /*  {
                if (conn != null) {
                    System.out.println("Connected to the database!");
                } else {
                    System.out.println("Failed to make connection!");
                }
            }*/
            } catch(SQLException ex){
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }


    }