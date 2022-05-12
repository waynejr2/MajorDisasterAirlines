import Objects.CreateReservationWindows.createReservationWindow;
import Objects.LoginWindows.welcomeWindow;

import java.net.MalformedURLException;
import java.sql.*;
import java.util.Calendar;

/**
 * MajorDisasterAirlines main class
 * @author wayne
 */
public class MajorDisasterAirlines {
    /**
     *
     * @param args for future use
     * @throws SQLException just in case
     */
    public static void main(String[] args) throws SQLException, MalformedURLException {
        welcomeWindow WW = new welcomeWindow();
        WW.activate();
        System.out.println("Program Running!");
    }
}

