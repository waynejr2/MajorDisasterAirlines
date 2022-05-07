package Objects.LoginWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Objects;

public class popupMessageWindow extends JFrame {

    private final accountWindow account;
    private final String text;
    private final int state;
    private final int type;

    private JPanel popupPanel;
    private JButton okayButton;
    private JLabel reasonLabel;
    private JLabel stateLabel;
    private JButton doNotCloseButton;

    public popupMessageWindow(accountWindow accountWindow, String text, int state, int type){
        this.account = accountWindow;
        this.text = text;
        this.state = state;
        this.type = type;

        setWindow();
        setText();

        okayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                account.setEnabled(true);
                if(Objects.equals(okayButton.getText(), "Confirm")){
                    try {
                        account.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        doNotCloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                account.setEnabled(true);
            }
        });
    }

    public void setWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 200;
        int windowWidth = 350;

        setContentPane(popupPanel);
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
        doNotCloseButton.setVisible(false);
    }

    public void setText() {
        if(state == 0){
            if(type == 0){
                stateLabel.setText("Username could not be changed:");
            } else {
                stateLabel.setText("Password could not be changed:");
            }
            reasonLabel.setText(text);
        } else if (state == 1){
            if(type == 0){
                stateLabel.setText("Username successfully changed");
            } else {
                stateLabel.setText("Password successfully changed");
            }
            reasonLabel.setText("");
        } else {
            stateLabel.setText("Please confirm account closure.");
            reasonLabel.setText("All reservations and flight credit will be removed.");
            doNotCloseButton.setVisible(true);
            okayButton.setText("Confirm");
            okayButton.setForeground(Color.decode("#BB362E"));
        }
    }
}
