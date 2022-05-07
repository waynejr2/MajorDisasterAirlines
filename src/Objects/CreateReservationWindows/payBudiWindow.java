package Objects.CreateReservationWindows;

import Objects.EditReservationWindows.editReservationWindow;
import Objects.databaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
/**
 * payBudiWindow this class allows the user to pay with a system we named paybudi which just asks you to log in into
 * your paybudi account and that is enough to verify and take you to the reservation Summary Window.
 * @author Juanito Herrera Sanchez
 */

public class payBudiWindow extends JFrame{
    private JLabel payBudiWelcome;
    private JTextField loginTxt;
    private JTextField passwordTxt;
    private JButton confirmButton;
    private JPanel payBudiPanel;
    private JLabel loginlabel;
    private JLabel passwordLabel;
    private JLabel invalidLoginLabel;
    private JButton backButton;
    private JCheckBox checkBox;

    private final choosePaymentWindow choosePayment;
    private bookFlightWindow bookFlight;
    private editReservationWindow editReservation;

    private final double totalPrice;

    public payBudiWindow(choosePaymentWindow choosePaymentWindow, bookFlightWindow bookFlightWindow, double totalPrice) throws SQLException {

        this.choosePayment = choosePaymentWindow;
        this.bookFlight = bookFlightWindow;
        this.totalPrice = totalPrice;

        setWindow();

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                invalidLoginLabel.setVisible(false);
                invalidLoginLabel.setText("Invalid Login Credentials");

                if(!checkBox.isSelected()) {
                    invalidLoginLabel.setText("Please Accept Terms To Continue");
                    invalidLoginLabel.setVisible(true);
                    return;
                }

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
                dispose();
                bookFlight.setEnabled(true);
                try {
                    bookFlight.paymentAccepted(totalPrice, 2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePayment.activate();
                dispose();
            }
        });
    }

    public payBudiWindow(choosePaymentWindow choosePaymentWindow, editReservationWindow editReservationWindow, double totalPrice) throws SQLException {

        this.choosePayment = choosePaymentWindow;
        this.editReservation = editReservationWindow;
        this.totalPrice = totalPrice;

        setWindow();

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
                dispose();
                editReservation.setEnabled(true);
                try {
                    editReservation.paid();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePayment.activate();
                dispose();
            }
        });
    }

    public void setWindow() {
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
    }

    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}