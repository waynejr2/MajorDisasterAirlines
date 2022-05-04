package Objects.LoginWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class accountWindow extends JFrame{
    private JLabel header;
    private JButton returnButton;
    private JPanel accountPanel;
    private JLabel l1;
    private JLabel l2;
    private JButton changePassButton;
    private JButton changeUserButton;
    private JLabel completedReservationsLabel;
    private JLabel futureReservationsLabel;
    private JLabel flightCreditLabel;
    private JButton closeAccountButton;
    private JTextField usernameField;
    private JTextField passwordField;

    private final mainMenuWindow mainMenu;
    private final int userID;

    public accountWindow (mainMenuWindow mainMenuWindow, int userID) {

        this.mainMenu = mainMenuWindow;
        this.userID = userID;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;

        setContentPane(accountPanel);
        setTitle("Account");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivate();
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
