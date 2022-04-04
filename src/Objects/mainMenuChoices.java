package Objects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class mainMenuChoices extends JFrame {
    private JPanel mainMenuChoicesPanel;
    private JButton searchFlightsButton;
    private JButton createReservationButton;
    private JButton viewReservationButton;
    private JButton editReservationButton;

    private final LoginWindow loginWindow;

    private final createReservation createReservationWindow;
    private final editReservation editReservationWindow;

    public mainMenuChoices(LoginWindow loginWindow){

        this.loginWindow = loginWindow;

        createReservationWindow = new createReservation(this);
        editReservationWindow = new editReservation(this);

        setContentPane(mainMenuChoicesPanel);
        setTitle("Choose");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //action listener for button to go to create reservation window
        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservationWindow.activate();
                deactivate();
            }
        });

        searchFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservationWindow.activate();
                deactivate();
            }
        });
        editReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editReservationWindow.activate();
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
