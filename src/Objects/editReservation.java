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

    public editReservation(){
        setContentPane(editReservationPanel);
        setTitle("Edit Reservation");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuChoices page = new mainMenuChoices();
                page.setVisible(true);
                setVisible(false);

            }
        });
    }
}
