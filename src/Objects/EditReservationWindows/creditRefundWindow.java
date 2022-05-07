package Objects.EditReservationWindows;

import Objects.LoginWindows.mainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class creditRefundWindow extends JFrame{
    private JButton doneButton;

    private JLabel locationsLabel;
    private JLabel dateLabel;
    private JLabel ticketsLabel;
    private JLabel bagsLabel;
    private JLabel priceLabel;
    private JLabel creditsLabel;
    private JPanel creditPanel;

    public creditRefundWindow (editReservationWindow editReservation, mainMenuWindow mainMenu, String locations, String date, String time, int tickets, int bags, double price, int credits) {

        setWindow();

        locationsLabel.setText(locations);
        dateLabel.setText("Departing: " + date + " at " + time);
        ticketsLabel.setText("Tickets: " + tickets);
        bagsLabel.setText("Bags: " + bags);
        priceLabel.setText("Total Price: $" + price);
        creditsLabel.setText("Total Remaining Flight Credit: " + credits);

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editReservation.dispose();
                dispose();
                mainMenu.activate();
            }
        });
    }

    public void setWindow()  {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 300;
        int windowWidth = 500;

        setContentPane(creditPanel);
        setTitle("Reservation Summary");
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
