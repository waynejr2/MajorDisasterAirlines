package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
/**
 * choosePaymentWindow, this class lets the user choose a payment method after they are done choosing flights and
 * adding passengers and baggage has two options credit card and pay budi.
 * @author Juanito Herrera Sanchez
 */

public class choosePaymentWindow extends JFrame{
    private JButton paybudiButton;
    private JButton creditCardButton;
    private JPanel paymentOptionsPanel;
    private JLabel paymentOptionsLabel;
    private JButton cancelButton;
    private JButton flightCreditButton;

    private String flightNumber;

    private bookFlightWindow bookFlight;
    private creditCardWindow creditCard;
    private payBudiWindow payBudi;

    public choosePaymentWindow(bookFlightWindow bookFlightWindow, double price) throws SQLException {

        this.bookFlight = bookFlightWindow;
        this.flightNumber = flightNumber;
        creditCard = new creditCardWindow(this);
        payBudi = new payBudiWindow(this);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 300;
        int windowWidth = 250;


        setContentPane(paymentOptionsPanel);
        setTitle("Payment Options");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2 + 400, screenHeight/2 - windowHeight/2 - 50);
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
                dispose();
                bookFlight.setEnabled(true);
            }
        });
    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}