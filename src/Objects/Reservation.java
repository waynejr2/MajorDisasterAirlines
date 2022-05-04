package Objects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class will represent a reservation that a user can make under their account
 * @author Rami Chaar
 */
public class Reservation {

    private final int userID;
    private final int flight;
    private final String date;
    private final double price;
    private final int tickets;
    private final int bags;
    private final int paymentMethod;
    private final int availableTickets;
    private final int availableBags;
    private final int flightID;

    public Reservation (int userID, int flight, String date, double price, int tickets, int bags, int paymentmethod, int availableTickets, int availableBags, int flightID) throws SQLException {
        this.userID = userID;
        this.flight = flight;
        this.date = date;
        this.price = price;
        this.tickets = tickets;
        this.bags = bags;
        this.paymentMethod = paymentmethod;
        this.availableTickets = availableTickets;
        this.availableBags = availableBags;
        this.flightID = flightID;

        Connection conn = databaseConnector.getConnection();
        Statement myStmt = conn.createStatement();
        //command to insert user into the database
        String sql = "INSERT INTO reservations (status, flight, date, DMAuserID, totalCost, numberOfTickets, numberOfBags) VALUES (" + this.paymentMethod+ ", " + this.flightID+ ", '"+ this.date+ "', "+ this.userID+ ", "+ this.price+ ", "+ this.tickets+ ", "+ this.bags+")";
        myStmt.executeUpdate(sql);
        sql = "UPDATE flights SET availableTickets = " + (availableTickets-tickets) + " WHERE id = " + flightID;
        myStmt.executeUpdate(sql);
        sql = "UPDATE flights SET availableBaggage = " + (availableBags-bags) + " WHERE id = " + flightID;
        myStmt.executeUpdate(sql);
    }
}
