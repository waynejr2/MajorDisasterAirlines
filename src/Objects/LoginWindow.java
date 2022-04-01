package Objects;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class LoginWindow extends JFrame {
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

    public LoginWindow() throws SQLException{

        //add plane image
        ImageIcon plane = new ImageIcon("lib/plane.png");
        picture.setIcon(plane);

        //create panel
        setContentPane(mainPanel);
        invalidLabel.setVisible(false);
        setTitle("Major Disaster Airlines");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //action listener to exit
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
    }
}
