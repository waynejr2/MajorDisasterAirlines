package Objects.CreateReservationWindows;

import Objects.LoginWindows.mainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * reservationSummaryWindow, this class basically summarizes everything from the Guest Name and flight date and information.
 * After you are done with the whole process this window will be like your confirmation and then will send you back to the
 * main menu.
 * @author Juanito Herrera Sanchez
 */

public class reservationSummaryWindow extends JFrame{
    private JButton doneButton;
    private JPanel confirmedFlightPanel;

    private JLabel locationsLabel;
    private JLabel dateLabel;
    private JLabel ticketsLabel;
    private JLabel bagsLabel;
    private JLabel priceLabel;
    private JLabel creditsLabel;

    private final mainMenuWindow mainMenu;
    private final createReservationWindow createReservation;
    private final bookFlightWindow bookFlight;

    public reservationSummaryWindow(bookFlightWindow bookFlightWindow, createReservationWindow createReservationWindow, mainMenuWindow mainMenuWindow, String locations, String date, String time, int tickets, int bags, double price, int credits){

        this.bookFlight = bookFlightWindow;
        this.createReservation = createReservationWindow;
        this.mainMenu = mainMenuWindow;

        setWindow();

        locationsLabel.setText(locations);
        dateLabel.setText("Departing: " + date + " at " + time);
        ticketsLabel.setText(String.valueOf("Tickets: " + tickets));
        bagsLabel.setText(String.valueOf("Bags: " + bags));
        priceLabel.setText(String.valueOf("Total Price: $" + price));
        creditsLabel.setText(String.valueOf("Total Remaining Flight Credit: " + credits));

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookFlight.dispose();
                createReservation.dispose();
                dispose();
                mainMenu.activate();
            }
        });
    }

    public void setWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 300;
        int windowWidth = 500;

        setContentPane(confirmedFlightPanel);
        setTitle("Reservation Summary");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);}

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}

