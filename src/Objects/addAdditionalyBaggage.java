package Objects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

    private final String flightNumber;

    // WILL THE PRIV FINAL BE FOR THE PAGES FOLOOWING?
    //private final createReservation createReservationWindow;
    //private final paymentOptions paymentOptionsWindow;

    // PaymentWindow Class Name Temporary
    // Update Class name

    public addAdditionalyBaggage(createReservation createReservationWindow, String fn) throws SQLException {
        //paymentOptionsWindow = new paymentOptions(this);

        this.flightNumber = fn;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;

        //invalidLabel1.setVisible(false);
        //invalidLabel2.setVisible(false);

        setContentPane(addBagGuestPanel);
        setTitle("Book Flight");
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
