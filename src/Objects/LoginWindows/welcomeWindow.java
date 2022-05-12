package Objects.LoginWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
/**
 * This class creates the first window that is shown when starting the application, it welcomes the user, the user must click continue to move onto the login window.
 * @author Rami Chaar
 */

public class welcomeWindow extends JFrame{
    private JPanel mainPanel;
    private JButton continueButton;
    private JLabel picture;

    private final loginWindow login = new loginWindow();

    public welcomeWindow() throws SQLException, MalformedURLException {

        setWindow();

        //action listener for continue button
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                login.activate();
            }
        });
    }

    public void setWindow() throws MalformedURLException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 350;
        int windowWidth = 400;

        //add plane image
        URL url = new URL("https://i.imgur.com/D4BzXWA.png");
        Image plane = Toolkit.getDefaultToolkit().getImage(url);
        ImageIcon icon = new ImageIcon(plane);
        picture.setIcon(icon);

        //create panel
        setContentPane(mainPanel);
        setTitle("Major Disaster Airlines");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
