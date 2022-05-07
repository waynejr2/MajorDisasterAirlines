package Objects.LoginWindows;

import Objects.CreateReservationWindows.createReservationWindow;
import Objects.databaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Objects;

import static java.lang.Long.parseLong;

public class accountWindow extends JFrame{
    private JLabel header;
    private JButton returnButton;
    private JPanel accountPanel;
    private JLabel l1;
    private JLabel l2;
    private JButton changePassButton;
    private JButton changeUserButton;
    private JLabel completedReservationsLabel;
    private JLabel futureReservationsLabel;
    private JLabel flightCreditLabel;
    private JButton closeAccountButton;
    private JTextField usernameField;
    private JTextField passwordField;

    private final mainMenuWindow mainMenu;
    private final accountWindow account = this;
    private final int userID;

    StringBuilder stars = new StringBuilder();

    private String oldUsername;
    private String oldPassword;

    Calendar date = Calendar.getInstance();
    int currMonth = createReservationWindow.monthToInt(date.getTime().toString().substring(4, 7));
    int currDay = createReservationWindow.dayToInt(date.getTime().toString().substring(8, 10));
    int currYear = createReservationWindow.yearToInt(date.getTime().toString().substring(24, 28));
    long currTime = parseLong(date.getTime().toString().substring(11,13) + date.getTime().toString().substring(14,16));

    public accountWindow (mainMenuWindow mainMenuWindow, int userID) throws SQLException {

        this.mainMenu = mainMenuWindow;
        this.userID = userID;

        setWindow();
        refreshDetails();

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivate();
                mainMenu.activate();
            }
        });
        changeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(changeUserButton.getText(), "Edit")){
                    changeUserButton.setText("Confirm");
                    usernameField.setEnabled(true);
                } else {
                    if(Objects.equals(usernameField.getText(), oldUsername)){
                        changeUserButton.setText("Edit");
                        usernameField.setEnabled(false);
                        usernameField.setText(oldUsername);
                        return;
                    } else if (Objects.equals(usernameField.getText(), "") || usernameField.getText().length() > 50){
                        popupMessageWindow popupMessage = new popupMessageWindow(account, "Invalid number of characters", 0, 0);
                        setEnabled(false);
                        changeUserButton.setText("Edit");
                        usernameField.setText(oldUsername);
                        usernameField.setEnabled(false);
                        return;
                    }
                    try {
                        ResultSet RS1 = databaseConnector.getResultSet("SELECT username FROM DMA_users");
                        while(RS1.next()){
                            if(Objects.equals(RS1.getString(1), usernameField.getText())){
                                popupMessageWindow popupMessage = new popupMessageWindow(account, "Username already exists", 0, 0);
                                setEnabled(false);
                                usernameField.setText(oldUsername);
                                changeUserButton.setText("Edit");
                                usernameField.setEnabled(false);
                                return;
                            }
                        }

                        Connection conn = databaseConnector.getConnection();
                        Statement myStmt = conn.createStatement();
                        myStmt.executeUpdate("UPDATE DMA_users SET username = '" + usernameField.getText() + "' WHERE id = " + userID);
                        popupMessageWindow popupMessage = new popupMessageWindow(account, "", 1, 0);
                        setEnabled(false);
                        changeUserButton.setText("Edit");
                        oldUsername = usernameField.getText();
                        usernameField.setEnabled(false);
                        return;

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        changePassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(changePassButton.getText(), "Edit")){
                    changePassButton.setText("Confirm");
                    passwordField.setText(oldPassword);
                    passwordField.setEnabled(true);
                } else {
                    if(Objects.equals(passwordField.getText(), oldPassword)){
                        changePassButton.setText("Edit");
                        passwordField.setEnabled(false);
                        passwordField.setText(String.valueOf(stars));
                        return;
                    } else if (Objects.equals(passwordField.getText(), "") || passwordField.getText().length() > 20){
                        popupMessageWindow popupMessage = new popupMessageWindow(account, "Invalid number of characters", 0, 1);
                        setEnabled(false);
                        changePassButton.setText("Edit");
                        passwordField.setText(String.valueOf(stars));
                        passwordField.setEnabled(false);
                        return;
                    }

                    try {
                        Connection conn = databaseConnector.getConnection();
                        Statement myStmt = conn.createStatement();
                        myStmt.executeUpdate("UPDATE DMA_users SET password = '" + passwordField.getText() + "' WHERE id = " + userID);
                        popupMessageWindow popupMessage = new popupMessageWindow(account, "", 1, 1);
                        setEnabled(false);
                        changePassButton.setText("Edit");
                        oldPassword = passwordField.getText();
                        stars = new StringBuilder("");
                        stars.append("*".repeat(oldPassword.length()));
                        passwordField.setText(String.valueOf(stars));
                        passwordField.setEnabled(false);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMessageWindow popupMessage = new popupMessageWindow(account, "message", 2, 0);
                setEnabled(false);
            }
        });
    }

    public void close() throws SQLException {

        Connection conn = databaseConnector.getConnection();
        Statement myStmt = conn.createStatement();

        ResultSet RS = databaseConnector.getResultSet("SELECT id FROM reservations WHERE DMAuserID = " + userID);

        while(RS.next()){
            ResultSet RS1 = databaseConnector.getResultSet("SELECT numberOfTickets, numberOfBags, availableTickets, availableBaggage, flights.id, date, departureTime FROM reservations JOIN flights on reservations.flight = flights.id WHERE reservations.id = " + RS.getInt(1));
            RS1.next();

            long year = parseLong(RS1.getString(6).substring(0, 4));
            long month = parseLong(RS1.getString(6).substring(5, 7));
            long day = parseLong(RS1.getString(6).substring(8, 10));
            String timeString = RS1.getString(7);
            long time = parseLong(timeString.substring(0, 2) + timeString.substring(3, 5));

            if(year > currYear || ((year == currYear && month > currMonth) || (year == currYear && month == currMonth && day > currDay) || (year == currYear && month == currMonth && day == currDay && time > currTime))){
                myStmt.executeUpdate("UPDATE flights SET availableTickets = " + (RS1.getInt(1) + RS1.getInt(3)) + " WHERE id = " + RS1.getInt(5));
                myStmt.executeUpdate("UPDATE flights SET availableBaggage = " + (RS1.getInt(2) + RS1.getInt(4)) + " WHERE id = " + RS1.getInt(5));
            }
        }

        myStmt.executeUpdate("DELETE FROM reservations WHERE DMAuserID = " + userID);
        myStmt.executeUpdate("DELETE FROM DMA_users WHERE id = " + userID);

        mainMenu.dispose();
        dispose();
        loginWindow login = new loginWindow();
        login.activate();
    }

    public void refreshDetails() throws SQLException {
        ResultSet RS = databaseConnector.getResultSet("SELECT flightCredit FROM DMA_users WHERE id = " + userID);
        RS.next();
        flightCreditLabel.setText(String.valueOf(RS.getInt(1)));

        Calendar date = Calendar.getInstance();
        currMonth = createReservationWindow.monthToInt(date.getTime().toString().substring(4, 7));
        currDay = createReservationWindow.dayToInt(date.getTime().toString().substring(8, 10));
        currYear = createReservationWindow.yearToInt(date.getTime().toString().substring(24, 28));
        currTime = parseLong(date.getTime().toString().substring(11,13) + date.getTime().toString().substring(14,16));

        int completed = 0;
        int future = 0;

        RS = databaseConnector.getResultSet("SELECT date, departureTime FROM reservations JOIN flights ON reservations.flight = flights.id WHERE DMAuserID = " + userID);
        while(RS.next()) {
            long year = parseLong(RS.getString(1).substring(0, 4));
            long month = parseLong(RS.getString(1).substring(5, 7));
            long day = parseLong(RS.getString(1).substring(8, 10));
            String timeString = RS.getString(2);
            long time = parseLong(timeString.substring(0, 2) + timeString.substring(3, 5));

            if (year < currYear) {
                completed++;
            } else if (year == currYear && month < currMonth) {
                completed++;
            } else if (year == currYear && month == currMonth && day < currDay) {
                completed++;
            } else if(year == currYear && month == currMonth && day == currDay && time < currTime) {
                completed++;
            } else {
                future++;
            }
        }

        completedReservationsLabel.setText(String.valueOf(completed));
        futureReservationsLabel.setText(String.valueOf(future));

        RS = databaseConnector.getResultSet("SELECT username, password FROM DMA_users WHERE id = " + userID);
        RS.next();

        oldUsername = RS.getString(1);
        oldPassword = RS.getString(2);

        usernameField.setText(oldUsername);
        usernameField.setEnabled(false);

        stars = new StringBuilder("");
        stars.append("*".repeat(oldPassword.length()));
        passwordField.setText(String.valueOf(stars));
        passwordField.setEnabled(false);
    }

    public void setWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 500;
        int windowWidth = 600;

        setContentPane(accountPanel);
        setTitle("Account");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void activate() throws SQLException {
        setVisible(true);
        refreshDetails();
    }
    public void deactivate() {
        setVisible(false);
    }
}
