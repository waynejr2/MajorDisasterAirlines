package Objects.EditReservationWindows;

import Objects.LoginWindows.mainMenuWindow;

import javax.swing.*;
import java.awt.*;

public class editReservationWindow extends JFrame {
    private JButton cancelButton;
    private JPanel editReservationPanel;

    private final mainMenuWindow mainMenuChoicesWindow;

    private final int userID;

    public editReservationWindow(mainMenuWindow mainMenuChoicesWindow, int id){

        this.mainMenuChoicesWindow = mainMenuChoicesWindow;
        this.userID = id;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        setContentPane(editReservationPanel);
        setTitle("Edit Reservation");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
