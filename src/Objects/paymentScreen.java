package Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paymentScreen extends JFrame{
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

    private paymentOptions paymentOptions;

    public paymentScreen(paymentOptions paymentOptions){

        this.paymentOptions = paymentOptions;

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
                String cardNumberLabelText = cardNumberLabel.getText();
                String cvvLabelText = cvvLabel.getText();
                if(cardNumberLabelText.length() == 16){
                    for(int i = 0; i< 16; i++){
                        if(!((int)cardNumberLabelText.charAt(i) >= 48) || !((int)cardNumberLabelText.charAt(i) <=57)){
                            InvalidCard.setVisible(false);
                        }
                    }
                } else {
                    if(cvvLabelText.length() == 3)
                        for(int i = 0; i< 13; i++) {
                            if (!((int) cvvLabelText.charAt(i) >= 48) || !((int) cvvLabelText.charAt(i) <= 57)) {
                                InvalidCard.setVisible(false);
                            }
                        }
                }
                    return;
                }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentOptions.activate();
                deactivate();
            }
        });
    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}