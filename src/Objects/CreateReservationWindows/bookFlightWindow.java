package Objects.CreateReservationWindows;

import Objects.LoginWindows.mainMenuWindow;
import Objects.Reservation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
/**
 * This window will prompt the user to select the number of tickets and bags they would like to purchase for the flight that they have selected to book a reservation for
 * @author Kelvin Martinez
 */
public class bookFlightWindow extends JFrame {
    private JPanel addBagGuestPanel;
    private JButton backButton;
    private JButton nextButton;
    private JLabel detailsLabel;
    private JLabel numBagsLabel;
    private JSpinner ticketsField;
    private JSpinner bagsField;
    private JLabel numTicketsLabel;

    private final int userID;
    private final String date;
    private final int flightInt;
    private int numTickets = 1;
    private int numBags;
    private final double ticketPrice;
    private final double bagPrice = 40.0;
    private double totalPrice;

    private final createReservationWindow createReservation;
    private choosePaymentWindow choosePayment;
    private final bookFlightWindow bookFlight = this;
    private final mainMenuWindow mainMenu;

    public bookFlightWindow(createReservationWindow createReservationWindow, mainMenuWindow mainMenuWindow, int userID, String flightNumber, int flightInt, String dateDescription, String date, double price) throws SQLException {

        this.createReservation = createReservationWindow;
        this.mainMenu = mainMenuWindow;
        this.flightInt = flightInt;
        this.userID = userID;
        this.date = date;
        this.ticketPrice = price;


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;

        setContentPane(addBagGuestPanel);
        setTitle("Book Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        detailsLabel.setText("Flight " + flightNumber + ": " + createReservation.printFlightData(flightInt) + " on " + dateDescription);
        ticketsField.setValue(1);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservation.activate();
                dispose();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    choosePayment = new choosePaymentWindow(bookFlight, ticketPrice, bagPrice, numTickets, numBags);
                    choosePayment.activate();
                    totalPrice = choosePaymentWindow.getPrice();
                    setEnabled(false);
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
                numTickets = (int) ticketsField.getValue();
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
                numBags = (int) bagsField.getValue();
            }
        });
    }

    public void paymentAccepted(double totalPrice, int paymentMethod) throws SQLException {
        Reservation r = new Reservation(userID, flightInt, date, totalPrice, numTickets, numBags, paymentMethod);
        reservationSummaryWindow reservationSummary = new reservationSummaryWindow(this, mainMenu);
        deactivate();
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
