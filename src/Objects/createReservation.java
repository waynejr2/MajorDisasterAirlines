package Objects;

import javax.swing.*;
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


        setContentPane(createReservationPanel);
        setTitle("Choose");
        setSize(400, 350);
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
