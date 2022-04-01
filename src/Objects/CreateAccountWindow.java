package Objects;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Objects;

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

    public CreateAccountWindow(){

        setContentPane(mainPanel);
        invalidLabel1.setVisible(false);
        invalidLabel2.setVisible(false);
        invalidLabel3.setVisible(false);
        setTitle("Major Disaster Airlines");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

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
                    String sql = "SELECT username FROM DMA_users";
                    Statement myStmt = User.createStatmentSQL();
                    ResultSet RS = myStmt.executeQuery(sql);
                    ResultSetMetaData RSMD = RS.getMetaData();
                    int columnsNumber = RSMD.getColumnCount();
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
                    setVisible(false);
                    LoginWindow LW = new LoginWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //return back to login screen
                setVisible(false);
                try {
                    LoginWindow LW = new LoginWindow();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
