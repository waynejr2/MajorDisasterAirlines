package Objects;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class WelcomeWindow extends JFrame{
    private JPanel mainPanel;
    private JButton continueButton;
    private JLabel picture;

    public WelcomeWindow()  throws SQLException{

        //add plane image
        ImageIcon plane = new ImageIcon("lib/plane.png");
        picture.setIcon(plane);

        //create panel
        setContentPane(mainPanel);
        setTitle("Major Disaster Airlines");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        //action listener for continue button
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
