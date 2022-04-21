package Objects.LoginWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;
import Objects.User;
import Objects.databaseConnector;

public class CreateAccountWindow extends JFrame{
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField confirmPasswordField;
    private JPanel mainPanel;
    private JButton createButton;
    private JButton backButton;
    private JLabel invalidLabel1;
    private JLabel invalidLabel2;
    private JLabel invalidLabel3;

    private final LoginWindow login;

    public CreateAccountWindow(LoginWindow loginWindow){

        this.login = loginWindow;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 350;
        int windowWidth = 400;

        setContentPane(mainPanel);
        invalidLabel1.setVisible(false);
        invalidLabel2.setVisible(false);
        invalidLabel3.setVisible(false);
        setTitle("Major Disaster Airlines");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //action listener on button to create account
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //receive input
                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                invalidLabel1.setVisible(false);
                invalidLabel2.setVisible(false);
                invalidLabel3.setVisible(false);

                //make sure inputs were made
                if(Objects.equals(username, "") || Objects.equals(password, "") || Objects.equals(confirmPassword, "")){
                    invalidLabel1.setVisible(true);
                    invalidLabel2.setVisible(true);
                    return;
                }

                //make sure input lengths are appropriate and passwords match
                if(!Objects.equals(password, confirmPassword) || password.length() > 20 || username.length() > 50){
                    invalidLabel1.setVisible(true);
                    invalidLabel2.setVisible(true);
                    return;
                }

                //check if username already exists in database
                try {
                    ResultSet RS = databaseConnector.getResultSet("SELECT username FROM DMA_users");
                    while (RS.next()) {
                        if(Objects.equals(RS.getString(1), username)){
                            invalidLabel3.setVisible(true);
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //crete new user and return to login window
                try{
                    User newUser = new User(username, password);
                    deactivate();
                    login.activate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        //action listener for button to go back to log in window without making an account
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //return back to login screen
                deactivate();
                login.activate();
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
