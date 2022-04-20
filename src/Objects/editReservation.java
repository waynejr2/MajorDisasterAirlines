package Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editReservation extends JFrame {
    private JButton cancelButton;
    private JButton enterButton;
    private JTextField enterBirthInput;
    private JTextField enterConfirmInput;
    private JLabel enterConfirmLabel;
    private JLabel enterBirthLabel;
    private JPanel editReservationPanel;

    private final mainMenuChoices mainMenuChoicesWindow;

    private final int userID;

    public editReservation(mainMenuChoices mainMenuChoicesWindow, int id){

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

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuChoicesWindow.activate();
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
