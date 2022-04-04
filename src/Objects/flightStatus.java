package Objects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class flightStatus extends JFrame{
    private JPanel flightStatusPanel;
    private JScrollPane statusPane;
    private JTextField ConNumTxt;
    private JButton OKButton;
    private JButton clearButton;
    private JButton backButton;

    private final mainMenuChoices mainMenuChoicesWindow;

    public flightStatus(mainMenuChoices mainMenuChoicesWindow) {

        this.mainMenuChoicesWindow = mainMenuChoicesWindow;

        setContentPane(flightStatusPanel);
        setTitle("flight status");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuChoicesWindow.activate();
                deactivate();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ConNumTxt.setText("");
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
