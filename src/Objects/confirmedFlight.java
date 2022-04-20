package Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class confirmedFlight extends JFrame{
    private JButton doneButton;
    private JLabel bookingConLabel;
    private JLabel bookingDateLabel;
    private JLabel guestLabel;
    private JLabel flightDetailsLabel;
    private JLabel thankYouLabel;
    private JPanel confirmedFlightPanel;

    private paymentScreen paymentScreen;
    private paybudiOption paybudiOption;

    public confirmedFlight(paymentScreen paymentScreen){

        this.paymentScreen = paymentScreen;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        confirmedFlightPanel.setLayout(new GridLayout(20, 1, 2, 5));
        setContentPane(confirmedFlightPanel);
        setTitle("Confirmed Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentScreen.activate();
                deactivate();
            }
        });
    }


    public confirmedFlight(paybudiOption paybudiOption){

        this.paybudiOption = paybudiOption;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        confirmedFlightPanel.setLayout(new GridLayout(20, 1, 2, 5));
        setContentPane(confirmedFlightPanel);
        setTitle("Confirmed Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paybudiOption.activate();
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

