package Objects;

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

        //action listener for button to go to create reservation window
        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // is there a loss of information
                // when a back and forward is preformed like this?
                createReservation page = new createReservation();
                page.setVisible(true);
                setVisible(false);
            }
        });

        searchFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createReservation page = new createReservation();
                page.setVisible(true);
                setVisible(false);

                // Nice to add if search is press the enter button is faded out
            }
        });
        editReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editReservation page = new editReservation();
                page.setVisible(true);
                setVisible(false);
            }
        });
        viewReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flightStatus page = new flightStatus();
                page.setVisible(true);
                setVisible(false);
            }
        });
    }
}
