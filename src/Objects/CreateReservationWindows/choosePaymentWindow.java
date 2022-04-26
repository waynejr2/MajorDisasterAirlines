package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
/**
 * ClassName describe what class does
 * @author
 */
public class choosePaymentWindow extends JFrame{
    private JButton paybudiButton;
    private JButton creditCardButton;
    private JPanel paymentOptionsPanel;
    private JLabel paymentOptionsLabel;
    private JButton cancelButton;

    private String flightNumber;

    private bookFlightWindow bookFlight;
    private creditCardWindow creditCard;
    private payBudiWindow payBudi;

    public choosePaymentWindow(bookFlightWindow bookFlightWindow, String flightNumber) throws SQLException {

        this.bookFlight = bookFlightWindow;
        this.flightNumber = flightNumber;
        creditCard = new creditCardWindow(this);
        payBudi = new payBudiWindow(this);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;


        setContentPane(paymentOptionsPanel);
        setTitle("Payment Options");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCard.activate();
                deactivate();
            }
        });
        paybudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payBudi.activate();
                deactivate();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookFlight.activate();
                deactivate();
            }
        });
    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}