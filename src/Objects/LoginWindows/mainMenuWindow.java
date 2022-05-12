package Objects.LoginWindows;

import Objects.CreateReservationWindows.createReservationWindow;
import Objects.EditReservationWindows.chooseReservationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
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
    private JButton accountButton;

    private createReservationWindow createReservation;
    private final accountWindow account;
    private final mainMenuWindow mainMenu = this;

    private final int userID;

    public mainMenuWindow(int id) throws SQLException, MalformedURLException {

        this.userID = id;
        this.account = new accountWindow(this, userID);

        setWindow();

        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservationWindow createReservation = null;
                try {
                    createReservation = new createReservationWindow(mainMenu, userID);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
                    loginWindow login = new loginWindow();
                    login.activate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivate();
                try {
                    account.activate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setWindow() throws MalformedURLException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;


        URL url = new URL("https://i.imgur.com/kDm374l.png");
        Image plane = Toolkit.getDefaultToolkit().getImage(url);
        ImageIcon icon = new ImageIcon(plane);
        picture1.setIcon(icon);

        url = new URL("https://i.imgur.com/3meyP36.png");
        plane = Toolkit.getDefaultToolkit().getImage(url);
        icon = new ImageIcon(plane);
        picture2.setIcon(icon);


        setContentPane(mainMenuChoicesPanel);
        setTitle("Major Disaster Airlines");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
