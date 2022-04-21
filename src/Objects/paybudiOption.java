package Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;
import java.sql.Statement;

public class paybudiOption extends JFrame{
    private JLabel payBudiWelcome;
    private JTextField loginTxt;
    private JTextField passwordTxt;
    private JButton confirmButton;
    private JPanel payBudiPanel;
    private JLabel loginlabel;
    private JLabel passwordLabel;
    private JLabel confirmLabel;
    private JLabel invalidLoginLabel;

    private paymentOptions paymentOptions;

    public paybudiOption(paymentOptions paymentOptions) throws SQLException {
        this.paymentOptions = paymentOptions;

        confirmedFlight confirmedFlightScreen  = new confirmedFlight(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;
        
        invalidLoginLabel.setVisible(false);

        setContentPane(payBudiPanel);
        setTitle("Pay Budi");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String login = loginTxt.getText();
                String password = passwordTxt.getText();
                invalidLoginLabel.setVisible(false);
                boolean found = false;
                int id =  0;
                String truePassword = null;

                try{
                    String sql = "SELECT login, password, id FROM PAYBUDI_users";
                    Connection conn = databaseConnector.getConnection();
                    Statement myStmt = conn.createStatement();
                    ResultSet RS = myStmt.executeQuery(sql);
                    while (RS.next()){

                        if(Objects.equals(RS.getString(1), login)){
                            found = true;
                            //retrieve true password
                            truePassword = RS.getString(2);
                            id = RS.getInt(3);
                            System.out.println(RS.getInt(3));
                            break;
                        }
                    }
                } catch (SQLException ex){
                    ex.printStackTrace();
                }

                if(!found){
                    invalidLoginLabel.setVisible(true);
                    return;
                }
                if(!Objects.equals(password, truePassword)){
                    invalidLoginLabel.setVisible(true);
                    return;
                }
                deactivate();
                confirmedFlightScreen.activate();
            }
        });
    }
    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}