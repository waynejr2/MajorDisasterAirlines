package Objects.CreateReservationWindows;

import Objects.EditReservationWindows.editReservationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static java.lang.Math.min;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * choosePaymentWindow, this class lets the user choose a payment method after they are done choosing flights and
 * adding passengers and baggage has two options credit card and pay budi.
 * @author Juanito Herrera Sanchez
 */

public class choosePaymentWindow extends JFrame{
    private JButton paybudiButton;
    private JButton creditCardButton;
    private JPanel paymentOptionsPanel;
    private JButton cancelButton;
    private JLabel totalLabel;

    private bookFlightWindow bookFlight;
    private editReservationWindow editReservation;

    private final choosePaymentWindow choosePayment = this;
    private creditCardWindow creditCard;
    private payBudiWindow payBudi;


    public choosePaymentWindow(bookFlightWindow bookFlightWindow, double totalPrice) throws SQLException {

        this.bookFlight = bookFlightWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 300;
        int windowWidth = 250;

        setContentPane(paymentOptionsPanel);
        setTitle("Choose Payment Method");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        totalLabel.setText("$" + totalPrice + " due");

        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCard = new creditCardWindow(choosePayment, bookFlight, totalPrice);
                creditCard.activate();
                deactivate();
            }
        });
        paybudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    payBudi = new payBudiWindow(choosePayment, bookFlight, totalPrice);
                    payBudi.activate();
                    deactivate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                bookFlight.activate();
            }
        });
    }

    public choosePaymentWindow(editReservationWindow editReservationWindow, double totalPrice) throws SQLException {

        this.editReservation = editReservationWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 300;
        int windowWidth = 250;

        setContentPane(paymentOptionsPanel);
        setTitle("Choose Payment Method");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        totalLabel.setText("$" + totalPrice + " due");

        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCard = new creditCardWindow(choosePayment, editReservation, totalPrice);
                creditCard.activate();
                deactivate();
            }
        });
        paybudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    payBudi = new payBudiWindow(choosePayment, editReservation, totalPrice);
                    payBudi.activate();
                    deactivate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                editReservation.activate();
                editReservation.setVisible(true);
            }
        });
    }
    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}