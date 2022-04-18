package Objects;
import javax.swing.*;
import java.awt.*;

public class addAdditionalyBaggage extends JFrame {
    private JPanel addBagGuestPanel;
    private JTextField baggageInput;
    private JTextField additionalGuestInput;
    private JButton backButton;
    private JButton nextButton;
    private JLabel additionalGuestLabel;
    private JScrollPane infoPane;
    private JLabel currentItinLabel;
    private JLabel additionToReserveLabel;


    // WILL THE PRIV FINAL BE FOR THE PAGES FOLOOWING?
    //private final createReservation createReservationWindow;
    //private final paymentOptions paymentOptionsWindow;

    // PaymentWindow Class Name Temporary
    // Update Class name

    public addAdditionalyBaggage(createReservation createReservationWindow) throws SQLException{
        //paymentOptionsWindow = new paymentOptions(this);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;

        //invalidLabel1.setVisible(false);
        //invalidLabel2.setVisible(false);

        addBagGuestPanel.setLayout(new GridLayout(20, 1, 2, 5));
        setContentPane(addBagGuestPanel);
        setTitle("Add Baggage and Guest");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //createReservation.activate();
                deactivate();
            }
        });
    }
    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
