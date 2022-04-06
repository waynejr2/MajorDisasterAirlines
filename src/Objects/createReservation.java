package Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class createReservation extends JFrame {
    private JPanel createReservationPanel;
    private JTextField fromEntry;
    private JLabel FromLabel;
    private JLabel selectionTitle;
    private JLabel AvailReservation;
    private JTextField textField1;
    private JTextField textField2;
    private JButton searchButton;
    private JButton backButton;
    private JButton selectButton;
    private JLabel ToLabel;
    private JLabel DepartureDateLabel;
    private JScrollPane availabilityPane;

    private final mainMenuChoices mainMenuChoicesWindow;

    public createReservation(mainMenuChoices mainMenuChoicesWindow){

        this.mainMenuChoicesWindow = mainMenuChoicesWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;


        setContentPane(createReservationPanel);
        setTitle("Choose");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuChoicesWindow.activate();
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
