package Objects.CreateReservationWindows;

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
    private int numTickets;
    private int numBags;
    private double totalPrice;

    private final createReservationWindow createReservation;
    private choosePaymentWindow choosePayment;
    private final bookFlightWindow bookFlight = this;

    public bookFlightWindow(createReservationWindow createReservationWindow, int userID, String flightNumber, int flightInt, String dateDescription, String date, double ticketPrice) throws SQLException {

        this.createReservation = createReservationWindow;
        this.flightInt = flightInt;
        this.userID = userID;
        this.date = date;


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
                    totalPrice = numTickets*ticketPrice + numBags*40;
                    choosePayment = new choosePaymentWindow(bookFlight, totalPrice);
                    choosePayment.activate();
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

    public void createReservation(int paymentMethod) throws SQLException {
        Reservation r = new Reservation(userID, flightInt, date, totalPrice, numTickets, numBags, paymentMethod);
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
