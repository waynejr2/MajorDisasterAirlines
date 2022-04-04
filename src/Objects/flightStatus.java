package Objects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class flightStatus extends JFrame{
    private JPanel flightStatusPanel;
    private JScrollPane statusPane;
    private JTextField textField1;
    private JButton OKButton;
    private JButton clearButton;
    private JButton backButton;

    public flightStatus() {
        setContentPane(flightStatusPanel);
        setTitle("flight status");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuChoices page=new mainMenuChoices();
                page.setVisible(true);
                setVisible(false);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
