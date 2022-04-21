package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private choosePaymentWindow choosePaymentWindow;

    public creditCardWindow(choosePaymentWindow choosePaymentWindow){

        this.choosePaymentWindow = choosePaymentWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        InvalidCard.setVisible(false);

        paymentMethodPanel.setLayout(new GridLayout(20, 1, 2, 5));
        setContentPane(paymentMethodPanel);
        setTitle("Payment Method");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumberLabelText = cardNumberLabel.getText();
                String cvvLabelText = cvvLabel.getText();
                    InvalidCard.setVisible(false);
            }

        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePaymentWindow.activate();
                deactivate();
            }
        });
    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}