package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
/**
 * ClassName describe what class does
 * @author
 */
public class bookFlightWindow extends JFrame {
    private JPanel addBagGuestPanel;
    private JButton backButton;
    private JButton nextButton;
    private JLabel additionalGuestLabel;
    private JLabel additionToReserveLabel;
    private JComboBox baggageComboBox;
    private JComboBox addTicketsComboBox;

    private String flightNumber;
    private String dateDescription;

    private final createReservationWindow createReservation;
    private choosePaymentWindow choosePayment;
    private final bookFlightWindow bookFlight = this;

    public bookFlightWindow(createReservationWindow createReservationWindow, String flightNumber, String dateDescription) throws SQLException {

        this.createReservation = createReservationWindow;
        this.flightNumber = flightNumber;
        this.dateDescription = dateDescription;
        this.choosePayment = new choosePaymentWindow(this, flightNumber);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;

        //invalidLabel1.setVisible(false);
        //invalidLabel2.setVisible(false);

        setContentPane(addBagGuestPanel);
        setTitle("Book Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


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
                // code to process if user wants to add baggage and additional passengers once pressed
                choosePayment.activate();
                deactivate();
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
