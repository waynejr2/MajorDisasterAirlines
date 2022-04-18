package Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class mainMenuChoices extends JFrame {
    private JPanel mainMenuChoicesPanel;
    private JButton searchFlightsButton;
    private JButton createReservationButton;
    private JButton viewReservationButton;
    private JButton editReservationButton;
    private JButton backButton;
    private JLabel picture2;
    private JLabel picture1;

    private final createReservation createReservationWindow;
    private final editReservation editReservationWindow;
    private final flightStatus flightStatusWindow;

    public mainMenuChoices(){

        createReservationWindow = new createReservation(this);
        editReservationWindow = new editReservation(this);
        flightStatusWindow = new flightStatus(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;

        ImageIcon planeIcon = new ImageIcon("lib/reservationSymbol.png");
        picture1.setIcon(planeIcon);

        ImageIcon editIcon = new ImageIcon("lib/editSymbol.png");
        picture2.setIcon(editIcon);

        setContentPane(mainMenuChoicesPanel);
        setTitle("Choose");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //action listener for button to go to create reservation window
        createReservationButton.addActionListener(new ActionListener() {
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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoginWindow newLoginWindow = new LoginWindow();
                    newLoginWindow.activate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                dispose();
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
