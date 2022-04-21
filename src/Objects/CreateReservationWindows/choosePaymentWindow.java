package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class choosePaymentWindow extends JFrame{
    private JButton paybudiButton;
    private JButton creditCardButton;
    private JPanel paymentOptionsPanel;
    private JLabel paymentOptionsLabel;
    private JButton cancelButton;

    private bookFlightWindow baggageScreen;
    private creditCardWindow creditCardWindow;
    private payBudiWindow paybudiScreen;

    public choosePaymentWindow(bookFlightWindow baggageScreen) throws SQLException {

        this.baggageScreen = baggageScreen;
        creditCardWindow = new creditCardWindow(this);
        paybudiScreen = new payBudiWindow(this);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        paymentOptionsPanel.setLayout(new GridLayout(20, 1, 2, 5));
        setContentPane(paymentOptionsPanel);
        setTitle("Payment Options");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCardWindow.activate();
                deactivate();
            }
        });
        paybudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCardWindow.activate();
                deactivate();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                baggageScreen.activate();
                deactivate();
            }
        });
    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}