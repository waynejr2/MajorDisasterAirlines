package Objects.CreateReservationWindows;

import Objects.LoginWindows.mainMenuWindow;
import Objects.Reservation;
import Objects.databaseConnector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

/**
 * This window will prompt the user to select the number of tickets and bags they would like to purchase for the flight that they have selected to book a reservation for
 * @author Kelvin Martinez
 */
public class bookFlightWindow extends JFrame {
    private JPanel reservationPanel;
    private JButton backButton;
    private JButton makePaymentButton;
    private JLabel detailsLabel;
    private JLabel numBagsLabel;
    private JSpinner ticketsField;
    private JSpinner bagsField;
    private JLabel numTicketsLabel;
    private JPanel panel;
    private JButton submitButton;
    private JButton useCreditButton;

    private final int userID;
    private final String date;
    private final int flightInt;
    private int numTickets = 1;
    private int numBags;
    private final double ticketPrice;
    private final double bagPrice = 40.0;
    private static double totalPrice;
    private final double taxRate = .075;
    private int flightCredit;
    private int flightID;

    private final createReservationWindow createReservation;
    private choosePaymentWindow choosePayment;
    private final bookFlightWindow bookFlight = this;
    private final mainMenuWindow mainMenu;

    private String details;
    private String dateDescription;
    private String time;
    private String[] priceInfo = new String[8];
    private int len = 32;

    private int availableTickets;
    private int availableBags;
    private int maxBags = 4;

    private int creditUsed;
    private double chargePrice = totalPrice;

    public bookFlightWindow(createReservationWindow createReservationWindow, mainMenuWindow mainMenuWindow, int userID, String flightNumber, int flightInt, String dateDescription, String date, String time, double price, int flightID) throws SQLException {

        this.createReservation = createReservationWindow;
        this.mainMenu = mainMenuWindow;
        this.flightInt = flightInt;
        this.userID = userID;
        this.date = date;
        this.ticketPrice = price;
        this.dateDescription = dateDescription;
        this.time = time;
        this.flightID = flightID;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 800;

        setContentPane(reservationPanel);
        setTitle("Book Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        useCreditButton.setText("                      ");
        makePaymentButton.setText("                    ");


        details = "Flight " + flightNumber + ": " + createReservation.printFlightData(flightInt);
        detailsLabel.setText(details + " on " + dateDescription);
        ticketsField.setValue(1);

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

        ResultSet RS = databaseConnector.getResultSet("SELECT flightCredit FROM DMA_users WHERE id = " + userID);
        RS.next();
        flightCredit = RS.getInt(1);

        RS = databaseConnector.getResultSet("SELECT availableTickets, availableBaggage FROM flights WHERE id = " + flightID);
        RS.next();
        availableTickets = RS.getInt(1);
        availableBags = RS.getInt(2);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservation.activate();
                dispose();
            }
        });
        makePaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(chargePrice > 0) {
                        choosePayment = new choosePaymentWindow(bookFlight, totalPrice);
                        choosePayment.activate();
                        deactivate();
                    } else {
                        paymentAccepted(totalPrice, 3);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ticketsField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((int)ticketsField.getValue() > availableTickets){
                    ticketsField.setValue(availableTickets);
                }
                if((int)ticketsField.getValue() < 1){
                    ticketsField.setValue(1);
                }
                numTickets = (int) ticketsField.getValue();
                maxBags = numTickets*4;
                if((int) bagsField.getValue() > maxBags){
                    bagsField.setValue(maxBags);
                }
            }
        });
        bagsField.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) bagsField.getValue() > maxBags) {
                    bagsField.setValue(maxBags);
                }
                if ((int) bagsField.getValue() < 0) {
                    bagsField.setValue(0);
                }
                numBags = (int) bagsField.getValue();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalPrice = (taxRate + 1)*(ticketPrice*numTickets + bagPrice*numBags);
                String numTicketsString = String.valueOf(numTickets);
                String numBagsString = String.valueOf(numBags);
                String ticketPriceString = String.valueOf(numTickets*ticketPrice);
                String bagPriceString = String.valueOf(numBags*bagPrice);
                String subtotalPriceString = String.valueOf(numBags*bagPrice+numTickets*ticketPrice);
                String taxString = String.valueOf(taxRate*(ticketPrice*numTickets + bagPrice*numBags));
                String totalPriceString = String.valueOf(totalPrice);

                StringBuilder one = new StringBuilder();
                one.insert(0, "Tickets(" + numTicketsString + ")");
                int buffer = one.length();
                for(int i = 0; i < len - buffer - ticketPriceString.length(); i++) {
                    one.append(" ");
                }
                one.append(ticketPriceString);

                StringBuilder two = new StringBuilder();
                two.insert(0, "Bags(" + numBagsString + ")");
                buffer = two.length();
                for(int i = 0; i < len - buffer - bagPriceString.length(); i++) {
                    two.append(" ");
                }
                two.append(bagPriceString);

                StringBuilder three = new StringBuilder();
                for(int i = 0; i < len; i++){
                    three.append("-");
                }

                StringBuilder four = new StringBuilder();
                four.insert(0, "Subtotal:");
                buffer = four.length();
                for(int i = 0; i < len - buffer - subtotalPriceString.length() - 1; i++) {
                    four.append(" ");
                }
                four.append("$");
                four.append(subtotalPriceString);

                StringBuilder five = new StringBuilder();
                five.insert(0, "Taxes & Fees:");
                buffer = five.length();
                for(int i = 0; i < len - buffer - taxString.length() - 1; i++) {
                    five.append(" ");
                }
                five.append("$");
                five.append(taxString);

                StringBuilder six = new StringBuilder();
                six.insert(0, "Total:");
                buffer = six.length();
                for(int i = 0; i < len - buffer - totalPriceString.length() - 1; i++) {
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
            }
        });
        useCreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    makePaymentButton.setText("Confirm Reservation");
                }
            }
        });
    }

    public void paymentAccepted(double totalPrice, int paymentMethod) throws SQLException {
        flightCredit -= creditUsed;
        Connection conn = databaseConnector.getConnection();
        Statement myStmt = conn.createStatement();
        String sql = "UPDATE DMA_users SET flightCredit = " + flightCredit + " WHERE id = " + userID;
        myStmt.executeUpdate(sql);

        Reservation r = new Reservation(userID, flightInt, date, totalPrice, numTickets, numBags, paymentMethod, availableTickets, availableBags, flightID);

        reservationSummaryWindow reservationSummary = new reservationSummaryWindow(this, createReservation, mainMenu, details, dateDescription, time, numTickets, numBags, totalPrice, flightCredit);
        reservationSummary.activate();
        setEnabled(false);
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
