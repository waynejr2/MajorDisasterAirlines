package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * CreditCardWindow, This class has the ability to let the user pay for the flight via credit card
 * if that is the option they choose in the choosePaymentWindow doesn't let them pass unless 16 digit card and 3 CVV num.
 * After that if everything is okay it will send you to the reservationSummaryWindow.
 * @author Juanito Herrera Sanchez
 */
public class creditCardWindow extends JFrame{
    private JTextField ownerText;
    private JTextField cvvText;
    private JTextField exText;
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

    private choosePaymentWindow choosePayment;
    private reservationSummaryWindow reservationSummary;

    public creditCardWindow(choosePaymentWindow choosePaymentWindow){

        this.choosePayment = choosePaymentWindow;
        reservationSummary = new reservationSummaryWindow(this);

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
                }
                else{
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
                    deactivate();
                    reservationSummary.activate();
                } else {
                    InvalidCard.setVisible(true);
                }


            }

        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePayment.activate();
                deactivate();
            }
        });

    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}