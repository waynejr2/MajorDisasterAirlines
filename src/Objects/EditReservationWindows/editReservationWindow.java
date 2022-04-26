package Objects.EditReservationWindows;

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
/**
 * ClassName describe what class does
 * @author
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

    private final mainMenuWindow mainMenu;
    private final editReservationWindow editReservation = this;
    private final int userID;

    public editReservationWindow(mainMenuWindow mainMenuWindow, int reservationNumber, int id) throws SQLException {

        this.mainMenu = mainMenuWindow;
        this.userID = id;

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

        ResultSet RS = databaseConnector.getResultSet("SELECT flightNumber, reservations.flight, departureTime, date, numberOfTickets, numberOfBags, totalCost, reservations.id, status FROM reservations JOIN flights ON reservations.flight = flights.id WHERE reservations.id = " + reservationNumber);
        RS.next();

        reservationTitle.setText("        Flight " + RS.getString(1) + ": " + createReservationWindow.printFlightData(RS.getInt(2)));
        reservationTitle.setFont(new Font(reservationTitle.getFont().getName(), Font.PLAIN, 20));

        flightTime.setText(RS.getString(4) + " (" + RS.getString(3) + ")        ");
        flightTime.setFont(new Font(flightTime.getFont().getName(), Font.PLAIN, 20));

        ticketsField.setValue(RS.getInt(5));
        bagsField.setValue(RS.getInt(6));

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

        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationWindow confirmation = new confirmationWindow(editReservation, mainMenu, reservationNumber, userID);
                confirmation.activate();
                setEnabled(false);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numTickets = (int)ticketsField.getValue();
                    int numBags = (int)bagsField.getValue();

                    if(numTickets == RS.getInt(5) && numBags == RS.getInt(6)){
                        return;
                    }

                    Connection conn = databaseConnector.getConnection();
                    Statement myStmt = conn.createStatement();
                    myStmt.executeUpdate("UPDATE reservations SET numberOfTickets = " +numTickets+ " WHERE id = " + reservationNumber);
                    myStmt.executeUpdate("UPDATE reservations SET numberOfBags = " +numBags+ " WHERE id = " + reservationNumber);
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
                System.out.println((int)ticketsField.getValue());
                if((int)ticketsField.getValue() > 99){
                    System.out.println("here");
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
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
