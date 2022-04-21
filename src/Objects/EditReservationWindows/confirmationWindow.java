package Objects.EditReservationWindows;

import Objects.LoginWindows.mainMenuWindow;
import Objects.databaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class confirmationWindow extends JFrame{

    private JPanel confirmationPanel;
    private JButton cancelButton;
    private JButton confirmButton;

    public confirmationWindow(editReservationWindow editReservation, mainMenuWindow mainMenu, int reservationNumber, int userID) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 200;
        int windowWidth = 400;

        setTitle("Confirm Cancellation");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(confirmationPanel);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editReservation.setEnabled(true);
                dispose();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = databaseConnector.getConnection();
                    Statement myStmt = conn.createStatement();
                    myStmt.executeUpdate("DELETE FROM reservations WHERE id = " + reservationNumber);
                    chooseReservationWindow chooseReservation = new chooseReservationWindow(mainMenu, userID);
                    chooseReservation.activate();
                    editReservation.dispose();
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

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
