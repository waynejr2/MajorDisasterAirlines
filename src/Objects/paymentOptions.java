package Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class paymentOptions extends JFrame{
    private JButton paybudiButton;
    private JButton creditCardButton;
    private JPanel paymentOptionsPanel;
    private JLabel paymentOptionsLabel;
    private JButton cancelButton;

    private addAdditionalyBaggage baggageScreen;
    private paymentScreen paymentScreen;
    private paybudiOption paybudiScreen;

    public paymentOptions(addAdditionalyBaggage baggageScreen) throws SQLException {

        this.baggageScreen = baggageScreen;
        paymentScreen = new paymentScreen(this);
        paybudiScreen = new paybudiOption(this);


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
                paymentScreen.activate();
                deactivate();
            }
        });
        paybudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paybudiScreen.activate();
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