package Objects.LoginWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class WelcomeWindow extends JFrame{
    private JPanel mainPanel;
    private JButton continueButton;
    private JLabel picture;

    private final LoginWindow loginWindow;

    public WelcomeWindow()  throws SQLException{

        loginWindow = new LoginWindow();

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
                loginWindow.activate();
            }
        });

    }
}
