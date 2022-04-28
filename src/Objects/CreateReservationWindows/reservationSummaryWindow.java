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
    private JLabel bookingConLabel;
    private JLabel bookingDateLabel;
    private JLabel guestLabel;
    private JLabel flightDetailsLabel;
    private JLabel thankYouLabel;
    private JPanel confirmedFlightPanel;

    private final mainMenuWindow mainMenu;
    private final bookFlightWindow bookFlight;

    public reservationSummaryWindow(bookFlightWindow bookFlightWindow, mainMenuWindow mainMenuWindow){

        this.bookFlight = bookFlightWindow;
        this.mainMenu = mainMenuWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 300;
        int windowWidth = 500;


        setContentPane(confirmedFlightPanel);
        setTitle("Confirmed Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookFlight.dispose();
                dispose();
                mainMenu.activate();
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

