package Objects.LoginWindows;

import Objects.CreateReservationWindows.createReservationWindow;
import Objects.EditReservationWindows.chooseReservationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
/**
 * This Class creates a window that acts as a main window where a user can choose to either search for a flight to make a reservation, view existing reservations on their account, or log out of their account.
 * @author Kelvin Martinez
 */

public class mainMenuWindow extends JFrame {
    private JPanel mainMenuChoicesPanel;
    private JButton searchFlightsButton;
    private JButton createReservationButton;
    private JButton viewReservationButton;
    private JButton editReservationButton;
    private JButton backButton;
    private JLabel picture2;
    private JLabel picture1;

    private final createReservationWindow createReservation;
    private final mainMenuWindow mainMenu = this;

    private final int userID;

    public mainMenuWindow(int id) throws SQLException {

        this.userID = id;

        createReservation = new createReservationWindow(this, userID);

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
        setTitle("Major Disaster Airlines");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //action listener for button to go to create reservation window
        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservation.activate();
                deactivate();
            }
        });

        editReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chooseReservationWindow chooseReservation = new chooseReservationWindow(mainMenu, userID);
                    chooseReservation.activate();
                    deactivate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LoginWindow login = new LoginWindow();
                    login.activate();
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
