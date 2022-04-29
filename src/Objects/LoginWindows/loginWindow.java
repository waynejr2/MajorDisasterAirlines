package Objects.LoginWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;
import Objects.databaseConnector;
/**
 * This class created a window that prompts the user to either sign in with an existing account through the two text fields, create an account, or exit the application altogether.
 * @author Rami Chaar
 */
public class loginWindow extends JFrame {
    private JLabel Username;
    private JTextField usernameField;
    private JLabel Password;
    private JTextField passwordField;
    private JButton btnCancel;
    private JButton btnLogin;
    private JPanel mainPanel;
    private JLabel picture;
    private JButton createAccountButton;
    private JLabel invalidLabel;

    private String username;
    private String password;

    public loginWindow() throws SQLException{

        loginWindow thisWindow = this;

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
        invalidLabel.setVisible(false);
        setTitle("Major Disaster Airlines");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //action listener to exit
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //action listener to create account
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivate();
                createAccountWindow createAccount = new createAccountWindow(thisWindow);
                createAccount.activate();
            }
        });

        //action listener to login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //input text field entries
                String username = usernameField.getText();
                String password = passwordField.getText();
                invalidLabel.setVisible(false);
                boolean found = false;
                int id = 0;
                String truePassword = null;

                //check if the username exists in the database
                try {
                    ResultSet RS = databaseConnector.getResultSet("SELECT username, password, id FROM DMA_users");
                    while (RS.next()) {
                        //check username in database
                        if(Objects.equals(RS.getString(1), username)){
                            found = true;
                            //retrieve true password
                            truePassword = RS.getString(2);
                            id = RS.getInt(3);
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //check if username not found
                if(!found){
                    invalidLabel.setVisible(true);
                    return;
                }

                //check if password does not match
                if(!Objects.equals(password, truePassword)){
                    invalidLabel.setVisible(true);
                    return;
                }

                //close login window and open main window

                mainMenuWindow mainMenu = null;
                try {
                    mainMenu = new mainMenuWindow(id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                mainMenu.activate();
                dispose();
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
