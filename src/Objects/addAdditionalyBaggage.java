package Objects;

import javax.swing.*;
import java.awt.*;

public class addAdditionalyBaggage extends JFrame {
    private JPanel addAdditonalBaggagePanel;
    private JTextField textField1;
    private JTextField textField2;

    private final String flightNumber;

    public addAdditionalyBaggage(createReservation createReservation, String fn) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        setTitle("Book Flight");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.flightNumber = fn;
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
