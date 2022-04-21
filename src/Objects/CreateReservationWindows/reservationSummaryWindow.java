package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class reservationSummaryWindow extends JFrame{
    private JButton doneButton;
    private JLabel bookingConLabel;
    private JLabel bookingDateLabel;
    private JLabel guestLabel;
    private JLabel flightDetailsLabel;
    private JLabel thankYouLabel;
    private JPanel confirmedFlightPanel;

    private creditCardWindow creditCard;
    private payBudiWindow payBudi;

    public reservationSummaryWindow(creditCardWindow creditCardWindow){

        this.creditCard = creditCardWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;


        setContentPane(confirmedFlightPanel);
        setTitle("Confirmed Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCardWindow.activate();
                deactivate();
            }
        });
    }

    public reservationSummaryWindow(payBudiWindow payBudiWindow){

        this.payBudi = payBudiWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;


        setContentPane(confirmedFlightPanel);
        setTitle("Confirmed Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payBudiWindow.activate();
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

