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

    private final addAdditionalyBaggage addAdditionalyBaggagePanel;

    public paymentScreen(addAdditionalyBaggage addAdditionalyBaggagePanel){

        this.addAdditionalyBaggagePanel = addAdditionalyBaggagePanel;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        InvalidCard.setVisible(false);

        paymentMethodPanel.setLayout(new GridLayout(20, 1, 2, 5));
        setContentPane(paymentMethodPanel);
        setTitle("Choose");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumberLabelText = cardNumberLabel.getText();
                String cvvLabelText = cvvLabel.getText();
                    InvalidCard.setVisible(false);
            }

        });

    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}