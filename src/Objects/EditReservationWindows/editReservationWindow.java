package Objects.EditReservationWindows;

import Objects.CreateReservationWindows.choosePaymentWindow;
import Objects.CreateReservationWindows.createReservationWindow;
import Objects.LoginWindows.mainMenuWindow;
import Objects.databaseConnector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Math.*;
import static java.lang.Math.ceil;

/**
 * This class creates a window based on a selected reservation where the user can edit the number of tickets and/or bags on the reservation, or delete the reservation altogether.
 * @author Rami Chaar
 */
public class editReservationWindow extends JFrame{
    private JButton backButton;
    private JPanel editReservationPanel;
    private JLabel reservationTitle;
    private JLabel flightTime;
    private JSpinner ticketsField;
    private JSpinner bagsField;
    private JButton cancelReservationButton;
    private JButton submitButton;
    private JPanel panel;
    private JButton useCreditButton;
    private JButton makePaymentButton;

    private boolean changes = false;

    private double difference;
    private final double reservationPrice;
    private double totalPrice = 0;
    private double chargePrice;

    private int flightCredit;
    private int creditDue;
    private int creditUsed;

    private int numTickets;
    private int numBags;
    private int chosenTickets;
    private int chosenBags;
    private final double ticketPrice;
    private final double bagPrice = 40.0;
    private final double taxRate = .075;
    private final int reservationNumber;

    private String time;
    private String date;
    private String details;

    private String[] priceInfo = new String[8];
    private int len = 32;

    private final mainMenuWindow mainMenu;
    private final editReservationWindow editReservation = this;
    private final int userID;
    private final int flight;

    public editReservationWindow(mainMenuWindow mainMenuWindow, int reservationNumber, int id, double reservationPrice) throws SQLException {

        this.mainMenu = mainMenuWindow;
        this.userID = id;
        this.reservationPrice = reservationPrice;
        this.reservationNumber = reservationNumber;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        setTitle("Edit Reservation");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(editReservationPanel);

        ResultSet RS = databaseConnector.getResultSet("SELECT flightNumber, reservations.flight, departureTime, date, numberOfTickets, numberOfBags, totalCost, reservations.id, status, departureTime FROM reservations JOIN flights ON reservations.flight = flights.id WHERE reservations.id = " + reservationNumber);
        RS.next();

        details = "Flight " + RS.getString(1) + ": " + createReservationWindow.printFlightData(RS.getInt(2));
        date = RS.getString(4);
        time = RS.getString(10);

        reservationTitle.setText("        " + details);
        reservationTitle.setFont(new Font(reservationTitle.getFont().getName(), Font.PLAIN, 20));

        flightTime.setText(RS.getString(4) + " (" + RS.getString(3) + ")        ");
        flightTime.setFont(new Font(flightTime.getFont().getName(), Font.PLAIN, 20));

        ticketsField.setValue(RS.getInt(5));
        bagsField.setValue(RS.getInt(6));

        ResultSet RS1 = databaseConnector.getResultSet("SELECT flight FROM flights WHERE id = " + RS.getInt(2));
        RS1.next();
        flight = RS1.getInt(1);

        RS1 = databaseConnector.getResultSet("SELECT ticketPrice FROM airline_connecting_flights  WHERE id = " + flight);
        RS1.next();
        ticketPrice = RS1.getDouble(1);

        StringBuilder spacer = new StringBuilder();
        for(int i = 0; i < len; i++){
            spacer.append(" ");
        }
        for(int i = 0; i < 8; i++) {
            priceInfo[i] = spacer.toString();
        }

        JList<String> label = new JList<String>(priceInfo);
        label.setEnabled(false);
        label.setSize(new Dimension(100, 100));
        label.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(label);
        panel.revalidate();
        panel.repaint();

        ResultSet RS3 = databaseConnector.getResultSet("SELECT id, flightCredit FROM DMA_users");

        while(RS3.next()){
            if(RS3.getInt(1) == userID){
                flightCredit = RS3.getInt(2);
            }
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chooseReservationWindow chooseReservation = new chooseReservationWindow(mainMenu, userID);
                    chooseReservation.activate();
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ticketsField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((int)ticketsField.getValue() > 99){
                    ticketsField.setValue(99);
                }
                if((int)ticketsField.getValue() < 1){
                    ticketsField.setValue(1);
                }
            }
        });
        bagsField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) bagsField.getValue() > 99) {
                    bagsField.setValue(99);
                }
                if ((int) bagsField.getValue() < 0) {
                    bagsField.setValue(0);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changes = true;
                try {
                    numTickets = RS.getInt(5);
                    numBags = RS.getInt(6);
                    chosenTickets = (int)ticketsField.getValue();
                    chosenBags = (int)bagsField.getValue();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                difference = (chosenTickets - numTickets)*ticketPrice + (chosenBags - numBags)*bagPrice;
                totalPrice = (taxRate + 1) * difference;
                String numTicketsString = String.valueOf(chosenTickets-numTickets);
                String numBagsString = String.valueOf(chosenBags-numBags);
                String ticketPriceString = String.valueOf((chosenTickets-numTickets) * ticketPrice);
                String bagPriceString = String.valueOf((chosenBags-numBags) * bagPrice);
                String subtotalPriceString = String.valueOf(difference);
                String taxString = String.valueOf(taxRate * difference);
                String totalPriceString = String.valueOf(totalPrice);

                if(difference < 0){
                    creditDue = abs((int)floor(totalPrice));
                } else {
                    creditDue = 0;
                }
                String totalCreditString = String.valueOf(creditDue);

                String ticketCond = "";
                String bagCond = "";

                if(chosenBags == numBags && chosenTickets == numTickets){
                    for(int i = 0; i < 8; i++) {
                        priceInfo[i] = spacer.toString();
                    }
                    JList<String> label = new JList<String>(priceInfo);
                    label.setEnabled(false);
                    label.setSize(new Dimension(100, 100));
                    label.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    panel.removeAll();
                    panel.add(label);
                    panel.revalidate();
                    panel.repaint();
                    changes = false;

                    useCreditButton.setText("                                  ");
                    makePaymentButton.setText("                                     ");
                    return;
                }

                if(chosenTickets > numTickets && chosenBags > numBags){
                    ticketCond = "Additional Tickets(";
                    bagCond = "Additional Bags(";
                } else if(chosenTickets > numTickets && chosenBags < numBags){
                    ticketCond = "Additional Tickets(";
                    bagCond = "Refund Bags(";
                } else if(chosenTickets < numTickets && chosenBags > numBags){
                    ticketCond = "Refund Tickets(";
                    bagCond = "Additional Bags(";
                } else if(chosenTickets < numTickets && chosenBags < numBags){
                    ticketCond = "Refund Tickets(";
                    bagCond = "Refund Bags(";
                } else if(chosenTickets == numTickets && chosenBags > numBags){
                    ticketCond = "Additional Tickets(";
                    bagCond = "Additional Bags(";
                } else if(chosenTickets < numTickets && chosenBags == numBags){
                    ticketCond = "Refund Tickets(";
                    bagCond = "Refund Bags(";
                } else if(chosenTickets > numTickets && chosenBags == numBags){
                    ticketCond = "Additional Tickets(";
                    bagCond = "Additional Bags(";
                } else {
                    ticketCond = "Refund Tickets(";
                    bagCond = "Refund Bags(";
                }

                numTicketsString = String.valueOf(abs(chosenTickets-numTickets));
                numBagsString = String.valueOf(abs(chosenBags-numBags));

                if(difference >= 0) {

                    StringBuilder one = new StringBuilder();
                    one.insert(0, ticketCond + numTicketsString + ")");
                    int buffer = one.length();
                    for (int i = 0; i < len - buffer - ticketPriceString.length(); i++) {
                        one.append(" ");
                    }
                    one.append(ticketPriceString);

                    StringBuilder two = new StringBuilder();
                    two.insert(0, bagCond + numBagsString + ")");
                    buffer = two.length();
                    for (int i = 0; i < len - buffer - bagPriceString.length(); i++) {
                        two.append(" ");
                    }
                    two.append(bagPriceString);

                    StringBuilder three = new StringBuilder();
                    for (int i = 0; i < len; i++) {
                        three.append("-");
                    }

                    StringBuilder four = new StringBuilder();
                    four.insert(0, "Subtotal:");
                    buffer = four.length();
                    for (int i = 0; i < len - buffer - subtotalPriceString.length() - 1; i++) {
                        four.append(" ");
                    }
                    four.append("$");
                    four.append(subtotalPriceString);

                    StringBuilder five = new StringBuilder();
                    five.insert(0, "Taxes & Fees:");
                    buffer = five.length();
                    for (int i = 0; i < len - buffer - taxString.length() - 1; i++) {
                        five.append(" ");
                    }
                    five.append("$");
                    five.append(taxString);

                    StringBuilder six = new StringBuilder();
                    six.insert(0, "Total:");
                    buffer = six.length();
                    for (int i = 0; i < len - buffer - totalPriceString.length() - 1; i++) {
                        six.append(" ");
                    }
                    six.append("$");
                    six.append(totalPriceString);

                    priceInfo[0] = String.valueOf(one);
                    priceInfo[1] = String.valueOf(two);
                    priceInfo[2] = String.valueOf(three);
                    priceInfo[3] = String.valueOf(four);
                    priceInfo[4] = String.valueOf(five);
                    priceInfo[5] = String.valueOf(six);
                    priceInfo[6] = String.valueOf(spacer);
                    priceInfo[7] = String.valueOf(spacer);

                    JList<String> label = new JList<String>(priceInfo);
                    label.setEnabled(false);
                    label.setSize(new Dimension(100, 100));
                    label.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    panel.removeAll();
                    panel.add(label);
                    panel.revalidate();
                    panel.repaint();

                    useCreditButton.setText("Use Credit");
                    makePaymentButton.setText("Make Payment");

                    creditUsed = 0;
                    chargePrice = totalPrice;
                } else {

                    StringBuilder one = new StringBuilder();
                    one.insert(0, ticketCond + numTicketsString + ")");
                    int buffer = one.length();
                    for (int i = 0; i < len - buffer - ticketPriceString.length(); i++) {
                        one.append(" ");
                    }
                    one.append(ticketPriceString);

                    StringBuilder two = new StringBuilder();
                    two.insert(0, bagCond + numBagsString + ")");
                    buffer = two.length();
                    for (int i = 0; i < len - buffer - bagPriceString.length(); i++) {
                        two.append(" ");
                    }
                    two.append(bagPriceString);

                    StringBuilder three = new StringBuilder();
                    for (int i = 0; i < len; i++) {
                        three.append("-");
                    }

                    StringBuilder four = new StringBuilder();
                    four.insert(0, "Subtotal:");
                    buffer = four.length();
                    for (int i = 0; i < len - buffer - subtotalPriceString.length() - 1; i++) {
                        four.append(" ");
                    }
                    four.append("$");
                    four.append(subtotalPriceString);

                    StringBuilder five = new StringBuilder();
                    five.insert(0, "Taxes & Fees:");
                    buffer = five.length();
                    for (int i = 0; i < len - buffer - taxString.length() - 1; i++) {
                        five.append(" ");
                    }
                    five.append("$");
                    five.append(taxString);

                    StringBuilder six = new StringBuilder();
                    six.insert(0, "Refund Credits Due:");
                    buffer = six.length();
                    for (int i = 0; i < len - buffer - totalCreditString.length(); i++) {
                        six.append(" ");
                    }
                    six.append(totalCreditString);

                    priceInfo[0] = String.valueOf(one);
                    priceInfo[1] = String.valueOf(two);
                    priceInfo[2] = String.valueOf(three);
                    priceInfo[3] = String.valueOf(four);
                    priceInfo[4] = String.valueOf(five);
                    priceInfo[5] = String.valueOf(six);
                    priceInfo[6] = String.valueOf(spacer);
                    priceInfo[7] = String.valueOf(spacer);

                    JList<String> label = new JList<String>(priceInfo);
                    label.setEnabled(false);
                    label.setSize(new Dimension(100, 100));
                    label.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    panel.removeAll();
                    panel.add(label);
                    panel.revalidate();
                    panel.repaint();

                    makePaymentButton.setText("Confirm Refund");
                    useCreditButton.setText("                                  ");

                    creditUsed = 0;
                }
            }
        });
        useCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(changes && difference > 0) {
                    makePaymentButton.setText("Make Payment");
                    creditUsed = (int) min(flightCredit, ceil(totalPrice));
                    String flightCreditString = String.valueOf(creditUsed);

                    chargePrice = totalPrice-creditUsed;
                    if(chargePrice < 0){
                        chargePrice = 0;
                    }
                    String finalPriceString = String.valueOf(chargePrice);

                    StringBuilder seven = new StringBuilder();
                    seven.append("Flight Credit:");
                    String buffer = String.valueOf(seven);
                    for(int i = 0; i < len - buffer.length() - flightCreditString.length() - 1; i++){
                        seven.append(" ");
                    }
                    seven.append("-");
                    seven.append(flightCreditString);

                    StringBuilder eight = new StringBuilder();
                    eight.append("Amount Due:");
                    buffer = String.valueOf(eight);
                    for(int i = 0; i < len - buffer.length() - finalPriceString.length() - 1; i++) {
                        eight.append(" ");
                    }
                    eight.append("$");
                    eight.append(finalPriceString);

                    priceInfo[6] = String.valueOf(seven);
                    priceInfo[7] = String.valueOf(eight);

                    JList<String> label1 = new JList<String>(priceInfo);
                    label1.setEnabled(false);
                    label1.setSize(new Dimension(100, 100));
                    label1.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    panel.removeAll();
                    panel.add(label1);
                    panel.revalidate();
                    panel.repaint();

                    if(chargePrice == 0) {
                        makePaymentButton.setText("Confirm Changes");
                    }
                }
            }
        });
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = databaseConnector.getConnection();
                    Statement myStmt = conn.createStatement();
                    if(changes && difference < 0) {
                        flightCredit += creditDue;

                        double p = reservationPrice + totalPrice;

                        myStmt.executeUpdate("UPDATE reservations SET numberOfTickets = " + chosenTickets + " WHERE id = " + reservationNumber);
                        myStmt.executeUpdate("UPDATE reservations SET numberOfBags = " + chosenBags + " WHERE id = " + reservationNumber);
                        myStmt.executeUpdate("UPDATE reservations SET totalCost = " + p + " WHERE id = " + reservationNumber);
                        myStmt.executeUpdate("UPDATE DMA_users SET flightCredit = " + flightCredit + " WHERE id = " + userID);

                        creditRefundWindow creditRefund = new creditRefundWindow(editReservation, mainMenu, details, date, time, chosenTickets, chosenBags, p, flightCredit);
                        creditRefund.activate();

                    } else if(changes && chargePrice == 0) {
                        paid();
                    } else if (changes){
                        choosePaymentWindow choosePayment = new choosePaymentWindow(editReservation, chargePrice);
                        choosePayment.activate();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditDue = (int)ceil(reservationPrice);
                confirmationWindow confirmation = new confirmationWindow(editReservation, mainMenu, reservationNumber, userID, creditDue);
                confirmation.activate();
                setEnabled(false);
            }
        });
    }

    public void paid() throws SQLException {
        Connection conn = databaseConnector.getConnection();
        Statement myStmt = conn.createStatement();
        double p = reservationPrice + totalPrice;
        flightCredit -= creditUsed;


        myStmt.executeUpdate("UPDATE reservations SET numberOfTickets = " + chosenTickets + " WHERE id = " + reservationNumber);
        myStmt.executeUpdate("UPDATE reservations SET numberOfBags = " + chosenBags + " WHERE id = " + reservationNumber);
        myStmt.executeUpdate("UPDATE reservations SET totalCost = " + p + " WHERE id = " + reservationNumber);
        myStmt.executeUpdate("UPDATE DMA_users SET flightCredit = " + flightCredit + " WHERE id = " + userID);

        creditRefundWindow creditRefund = new creditRefundWindow(editReservation, mainMenu, details, date, time, chosenTickets, chosenBags, p, flightCredit);
        creditRefund.activate();
    }
    public void cancel() throws SQLException {
        creditDue = (int)floor(reservationPrice);
        flightCredit = flightCredit + creditDue;
        Connection conn = databaseConnector.getConnection();
        Statement myStmt = conn.createStatement();
        myStmt.executeUpdate("UPDATE DMA_users SET flightCredit = " + flightCredit + " WHERE id = " + userID);
    }


    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
