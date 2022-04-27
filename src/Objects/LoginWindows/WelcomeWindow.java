package Objects.LoginWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
/**
 * This class creates the first window that is shown when starting the application, it welcomes the user, the user must click continue to move onto the login window.
 * @author Rami Chaar
 */

public class WelcomeWindow extends JFrame{
    private JPanel mainPanel;
    private JButton continueButton;
    private JLabel picture;

    private final LoginWindow login;

    public WelcomeWindow()  throws SQLException{

        login = new LoginWindow();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 350;
        int windowWidth = 400;

        //add plane image
        ImageIcon plane = new ImageIcon("lib/plane.png");
        picture.setIcon(plane);

        //create panel
        setContentPane(mainPanel);
        setTitle("Major Disaster Airlines");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //action listener for continue button
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                login.activate();
            }
        });

    }
}
