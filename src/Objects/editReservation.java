package Objects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editReservation extends JFrame {
    private JButton cancelButton;
    private JButton enterButton;
    private JTextField enterBirthInput;
    private JTextField enterConfirmInput;
    private JLabel enterConfirmLabel;
    private JLabel enterBirthLabel;
    private JPanel editReservationPanel;

    private final mainMenuChoices mainMenuChoicesWindow;

    public editReservation(mainMenuChoices mainMenuChoicesWindow){

        this.mainMenuChoicesWindow = mainMenuChoicesWindow;

        setContentPane(editReservationPanel);
        setTitle("Edit Reservation");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
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
