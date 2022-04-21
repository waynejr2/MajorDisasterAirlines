package Objects.CreateReservationWindows;

import Objects.databaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class payBudiWindow extends JFrame{
    private JLabel payBudiWelcome;
    private JTextField loginTxt;
    private JTextField passwordTxt;
    private JButton confirmButton;
    private JPanel payBudiPanel;
    private JLabel loginlabel;
    private JLabel passwordLabel;
    private JLabel confirmLabel;
    private JLabel invalidLoginLabel;

    private choosePaymentWindow choosePayment;
    private reservationSummaryWindow reservationSummary;

    public payBudiWindow(choosePaymentWindow choosePaymentWindow) throws SQLException {

        this.choosePayment = choosePaymentWindow;
        this.reservationSummary = new reservationSummaryWindow(this);

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

                invalidLoginLabel.setVisible(false);

                String login = loginTxt.getText();
                String password = passwordTxt.getText();
                boolean found = false;
                String truePassword = "";

                try{
                    ResultSet RS = databaseConnector.getResultSet("SELECT login, password FROM PAYBUDI_users");
                    while (RS.next()){
                        if(Objects.equals(RS.getString(1), login)){
                            found = true;
                            //retrieve true password
                            truePassword = RS.getString(2);
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
                if(!Objects.equals(truePassword, password)){
                    invalidLoginLabel.setVisible(true);
                    return;
                }
                deactivate();
                reservationSummary.activate();
            }
        });
    }
    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}