import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class mainMenuChoices extends JFrame {
    private JPanel mainMenuChoicesPanel;
    private JButton searchFlightsButton;
    private JButton createReservationButton;
    private JButton viewReservationButton;
    private JButton editReservationButton;


    public mainMenuChoices(){
        setContentPane(mainMenuChoicesPanel);
        setTitle("Choose");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservation page = new createReservation();
                page.setVisible(true);
                setVisible(false);

            }

        });

    }
}