package Objects.CreateReservationWindows;

import Objects.EditReservationWindows.editReservationWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * CreditCardWindow, This class has the ability to let the user pay for the flight via credit card
 * if that is the option they choose in the choosePaymentWindow doesn't let them pass unless 16 digit card and 3 CVV num.
 * After that if everything is okay it will send you to the reservationSummaryWindow.
 * @author Juanito Herrera Sanchez
 */
public class creditCardWindow extends JFrame{
    private JTextField ownerText;
    private JTextField cvvText;
    private JButton confirmButton;
    private JButton cancelButton;
    private JTextField cardText;
    private JLabel InvalidCard;
    private JPanel paymentMethodPanel;
    private JLabel ownerLabel;
    private JLabel paymentMethod;
    private JLabel experationDateLabel;
    private JLabel cvvLabel;
    private JLabel cardNumberLabel;
    private JLabel invalidExpDateLabel;
    private JComboBox monthInput;
    private JComboBox yearInput;

    private final choosePaymentWindow choosePayment;
    private editReservationWindow editReservation;
    private bookFlightWindow bookFlight;
    private reservationSummaryWindow reservationSummary;

    private final double totalPrice;

    public creditCardWindow(choosePaymentWindow choosePaymentWindow, bookFlightWindow bookFlightWindow, double totalPrice){

        this.choosePayment = choosePaymentWindow;
        this.bookFlight = bookFlightWindow;

        this.totalPrice = totalPrice;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        InvalidCard.setVisible(false);

        setContentPane(paymentMethodPanel);
        setTitle("Payment Method");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumberLabelText = cardText.getText();
                String cvvLabelText = cvvText.getText();
                InvalidCard.setVisible(false);
                invalidExpDateLabel.setVisible(false);//add

                // date checker
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yy");
                LocalDateTime now = LocalDateTime.now();
                System.out.println(dtf.format(now));

                if(cardNumberLabelText.length() == 16) {
                    for (int i = 0; i < 15; i++) {
                        if (!((int) cardNumberLabelText.charAt(i) >= 48) || !((int) cardNumberLabelText.charAt(i) <= 57)) {
                            InvalidCard.setVisible(true);
                            return;
                        }
                    }
                } else{
                    InvalidCard.setVisible(true);
                    return;
                }
                if(cvvLabelText.length() == 3) {
                    for (int j = 0; j < 2; j++) {
                        if (!((int) cvvLabelText.charAt(j) >= 48) || !((int) cvvLabelText.charAt(j) <= 57)) {
                            InvalidCard.setVisible(true);
                            return;
                        }
                    }
                } else {
                    InvalidCard.setVisible(true);
                }

                try {
                    dispose();
                    bookFlight.setEnabled(true);
                    bookFlight.paymentAccepted(totalPrice, 1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePayment.activate();
                dispose();
            }
        });

    }

    public creditCardWindow(choosePaymentWindow choosePaymentWindow, editReservationWindow editReservationWindow, double totalPrice){

        this.choosePayment = choosePaymentWindow;
        this.editReservation = editReservationWindow;

        this.totalPrice = totalPrice;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        InvalidCard.setVisible(false);

        setContentPane(paymentMethodPanel);
        setTitle("Payment Method");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumberLabelText = cardText.getText();
                String cvvLabelText = cvvText.getText();
                InvalidCard.setVisible(false);

                if(cardNumberLabelText.length() == 16) {
                    for (int i = 0; i < 15; i++) {
                        if (!((int) cardNumberLabelText.charAt(i) >= 48) || !((int) cardNumberLabelText.charAt(i) <= 57)) {
                            InvalidCard.setVisible(true);
                            return;
                        }
                    }
                } else{
                    InvalidCard.setVisible(true);
                    return;
                }
                if(cvvLabelText.length() == 3) {
                    for (int j = 0; j < 2; j++) {
                        if (!((int) cvvLabelText.charAt(j) >= 48) || !((int) cvvLabelText.charAt(j) <= 57)) {
                            InvalidCard.setVisible(true);
                            return;
                        }
                    }
                } else {
                    InvalidCard.setVisible(true);
                }

                try {
                    dispose();
                    editReservation.setEnabled(true);
                    editReservation.paid();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePayment.activate();
                dispose();
            }
        });

    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}