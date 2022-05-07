package Objects.EditReservationWindows;

import Objects.CreateReservationWindows.createReservationWindow;
import Objects.LoginWindows.mainMenuWindow;
import Objects.databaseConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static java.lang.Long.parseLong;
import static java.lang.Math.max;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * This class creates a window that displays all the reservations under the account of the user. The user can also select any reservation and click the edit button to open a window that will allow them to edit or cancel the reservation
 * @author Rami Chaar
 */
public class chooseReservationWindow extends JFrame {
    private JButton cancelButton;
    private JPanel choseReservationPanel;
    private JButton backButton;
    private JScrollPane reservationPane;
    private JPanel reservationPanel;
    private JButton editButton;
    private JButton showPastReservationsButton;

    private final mainMenuWindow mainMenu;

    private editReservationWindow editReservation;

    private final int userID;
    private int chosenReservation = -1;

    ArrayList<JList<String>> labels = new ArrayList<>();
    ArrayList<Integer> reservations = new ArrayList<>();
    ArrayList<Double> prices = new ArrayList<>();
    ArrayList<Long> times = new ArrayList<>();

    private double reservationCost;

    public chooseReservationWindow(mainMenuWindow mainMenuWindow, int id) throws SQLException {

        this.mainMenu = mainMenuWindow;
        this.userID = id;

        setWindow();

        loadReservations(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.activate();
                dispose();
            }
        });
        showPastReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Objects.equals(showPastReservationsButton.getText(), "Show Past Reservations")) {
                        showPastReservationsButton.setText("Only Show Future Reservations");
                        loadReservations(true);
                    } else {
                        showPastReservationsButton.setText("Show Past Reservations");
                        loadReservations(false);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chosenReservation != -1){
                    try {
                        editReservation = new editReservationWindow(mainMenu, chosenReservation, userID, reservationCost);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    editReservation.activate();
                    dispose();
                }
            }
        });
    }

    public void setWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;
        setContentPane(choseReservationPanel);
        reservationPanel.setLayout(new GridLayout(6, 1, 5, 5));
        reservationPanel.revalidate();
        reservationPanel.repaint();
        setTitle("Your Reservations");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void loadReservations(boolean all) throws SQLException {

        reservationPanel.removeAll();
        reservationPanel.revalidate();
        reservationPanel.repaint();
        labels.clear();
        reservations.clear();
        prices.clear();
        times.clear();

        chosenReservation = -1;

        if(all) {
            int numLabels = 0;
            int numPastLabels = 0;

            ResultSet RS = databaseConnector.getResultSet("SELECT DMAuserID, flightNumber, flights.flight, departureTime, date, numberOfTickets, numberOfBags, totalCost, reservations.id, status FROM reservations JOIN flights ON reservations.flight = flights.id");
            ResultSet RS1;

            int count = 0;

            Calendar date = Calendar.getInstance();
            long currMonth = createReservationWindow.monthToInt(date.getTime().toString().substring(4, 7));
            long currDay = createReservationWindow.dayToInt(date.getTime().toString().substring(8, 10));
            long currYear = createReservationWindow.yearToInt(date.getTime().toString().substring(24, 28));
            long currTime = parseLong(date.getTime().toString().substring(11,13) + date.getTime().toString().substring(14,16));

            while (RS.next()) {
                long year = parseLong(RS.getString(5).substring(0, 4));
                long month = parseLong(RS.getString(5).substring(5, 7));
                long day = parseLong(RS.getString(5).substring(8, 10));
                String timeString = RS.getString(4);
                long time = parseLong(timeString.substring(0, 2) + timeString.substring(3, 5));
                boolean future = true;
                if (year < currYear) {
                    future = false;
                } else if (year == currYear && month < currMonth) {
                    future = false;
                } else if (year == currYear && month == currMonth && day < currDay) {
                    future = false;
                } else if(year == currYear && month == currMonth && day == currDay && time < currTime) {
                    future = false;
                }

                long dateValue = time;
                dateValue += day * 10000;
                dateValue += month * 1000000;
                dateValue += year * 100000000;

                if (RS.getInt(1) == userID && future) {
                    numLabels++;
                    times.add(dateValue);
                    reservations.add(RS.getInt(9));
                    prices.add(RS.getDouble(8));

                    RS1 = databaseConnector.getResultSet("SELECT description FROM reservation_status WHERE id = " + RS.getString(10));
                    RS1.next();

                    String info1 = RS.getString(5) + " (" + RS.getString(4) + ")";
                    String info2 = "Flight " + RS.getString(2) + ": " + createReservationWindow.printFlightData(RS.getInt(3));
                    String info3 = "Number of tickets: " + RS.getString(6);
                    String info4 = "Number of bags: " + RS.getString(7);
                    String info5 = "Total: $" + RS.getString(8);
                    String info6 = "Status: " + RS1.getString(1);
                    String spacer1 = "                   ";

                    String[] flightInfo = new String[6];
                    flightInfo[0] = info1 + spacer1 + info2;
                    flightInfo[1] = " ";
                    flightInfo[2] = info3;
                    flightInfo[3] = info4;
                    flightInfo[4] = info5;
                    flightInfo[5] = info6;

                    JList<String> label = new JList<String>(flightInfo);
                    label.setEnabled(false);
                    label.setSize(new Dimension(100, 100));
                    label.setBorder(createLineBorder(new Color(150, 150, 150)));
                    labels.add(label);
                    count++;
                } else if (RS.getInt(1) == userID) {
                    numPastLabels++;
                    times.add(dateValue);
                    reservations.add(RS.getInt(9));
                    prices.add(RS.getDouble(8));

                    RS1 = databaseConnector.getResultSet("SELECT description FROM reservation_status WHERE id = " + RS.getString(10));
                    RS1.next();

                    String info1 = RS.getString(5) + " (" + RS.getString(4) + ")";
                    String info2 = "Flight " + RS.getString(2) + ": " + createReservationWindow.printFlightData(RS.getInt(3));
                    String info3 = "Number of tickets: " + RS.getString(6);
                    String info4 = "Number of bags: " + RS.getString(7);
                    String info5 = "Total: $" + RS.getString(8);
                    String info6 = "Status: " + RS1.getString(1);
                    String spacer1 = "                   ";

                    String[] flightInfo = new String[6];
                    flightInfo[0] = info1 + spacer1 + info2;
                    flightInfo[1] = " ";
                    flightInfo[2] = info3;
                    flightInfo[3] = info4;
                    flightInfo[4] = info5;
                    flightInfo[5] = info6;

                    JList<String> label = new JList<String>(flightInfo);
                    label.setEnabled(false);
                    label.setBackground(Color.getColor("GRAY"));
                    label.setSize(new Dimension(100, 100));
                    label.setBorder(createLineBorder(new Color(150, 150, 150)));
                    labels.add(label);
                    count++;
                }
            }

            if (count == 0) {
                String info1 = "You currently have no reservations.";

                String[] flightInfo = new String[6];
                flightInfo[0] = info1;
                flightInfo[1] = " ";
                flightInfo[2] = " ";
                flightInfo[3] = " ";
                flightInfo[4] = " ";
                flightInfo[5] = " ";

                JList<String> label = new JList<String>(flightInfo);
                label.setEnabled(false);
                label.setSize(new Dimension(100, 100));
                label.setBorder(createLineBorder(new Color(150, 150, 150)));
                labels.add(label);
                reservationPanel.add(label);
            }

            sortByDate();

            for (int i = 0; i < numPastLabels; i++) {
                reservationPanel.add(labels.get(i));
            }
            for (int i = numPastLabels; i < numLabels + numPastLabels; i++) {
                JList<String> label = labels.get(i);
                reservationPanel.add(labels.get(i));
                int finalNumLabels = numLabels;
                int finalNumPastLabels = numPastLabels;
                int curr = reservations.get(i);
                int finalI = i;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        for (int k = finalNumPastLabels; k < finalNumLabels + finalNumPastLabels; k++) {
                            labels.get(k).setBackground(Color.decode("#FFFFFF"));
                            labels.get(k).setForeground(Color.decode("#b3d7ff"));
                        }
                        label.setBackground(Color.decode("#b3d7ff"));
                        chosenReservation = curr;
                        reservationCost = prices.get(finalI);
                    }
                });
            }

            reservationPanel.setLayout(new GridLayout(max(4, numLabels + numPastLabels), 1, 5, 5));
            reservationPanel.revalidate();
            reservationPanel.repaint();
        } else {
            int numLabels = 0;

            ResultSet RS = databaseConnector.getResultSet("SELECT DMAuserID, flightNumber, flights.flight, departureTime, date, numberOfTickets, numberOfBags, totalCost, reservations.id, status FROM reservations JOIN flights ON reservations.flight = flights.id");
            ResultSet RS1;

            int count = 0;

            Calendar date = Calendar.getInstance();
            int currMonth = createReservationWindow.monthToInt(date.getTime().toString().substring(4, 7));
            int currDay = createReservationWindow.dayToInt(date.getTime().toString().substring(8, 10));
            int currYear = createReservationWindow.yearToInt(date.getTime().toString().substring(24, 28));
            long currTime = parseLong(date.getTime().toString().substring(11,13) + date.getTime().toString().substring(14,16));

            while (RS.next()) {
                long year = parseLong(RS.getString(5).substring(0, 4));
                long month = parseLong(RS.getString(5).substring(5, 7));
                long day = parseLong(RS.getString(5).substring(8, 10));
                String timeString = RS.getString(4);
                long time = parseLong(timeString.substring(0, 2) + timeString.substring(3, 5));
                boolean future = true;
                if (year < currYear) {
                    future = false;
                } else if (year == currYear && month < currMonth) {
                    future = false;
                } else if (year == currYear && month == currMonth && day < currDay) {
                    future = false;
                } else if(year == currYear && month == currMonth && day == currDay && time < currTime) {
                    future = false;
                }

                long dateValue = time;
                dateValue += day * 10000;
                dateValue += month * 1000000;
                dateValue += year * 100000000;

                if (RS.getInt(1) == userID && future) {
                    times.add(dateValue);
                    numLabels++;
                    reservations.add(RS.getInt(9));

                    prices.add(RS.getDouble(8));

                    RS1 = databaseConnector.getResultSet("SELECT description FROM reservation_status WHERE id = " + RS.getString(10));
                    RS1.next();

                    String info1 = RS.getString(5) + " (" + RS.getString(4) + ")";
                    String info2 = "Flight " + RS.getString(2) + ": " + createReservationWindow.printFlightData(RS.getInt(3));
                    String info3 = "Number of tickets: " + RS.getString(6);
                    String info4 = "Number of bags: " + RS.getString(7);
                    String info5 = "Total: $" + RS.getString(8);
                    String info6 = "Status: " + RS1.getString(1);
                    String spacer1 = "                   ";

                    String[] flightInfo = new String[6];
                    flightInfo[0] = info1 + spacer1 + info2;
                    flightInfo[1] = " ";
                    flightInfo[2] = info3;
                    flightInfo[3] = info4;
                    flightInfo[4] = info5;
                    flightInfo[5] = info6;

                    JList<String> label = new JList<String>(flightInfo);
                    label.setEnabled(false);
                    label.setSize(new Dimension(100, 100));
                    label.setBorder(createLineBorder(new Color(150, 150, 150)));
                    labels.add(label);
                    count++;
                }
            }

            if (count == 0) {
                String info1 = "You currently have no reservations.";

                String[] flightInfo = new String[6];
                flightInfo[0] = info1;
                flightInfo[1] = " ";
                flightInfo[2] = " ";
                flightInfo[3] = " ";
                flightInfo[4] = " ";
                flightInfo[5] = " ";

                JList<String> label = new JList<String>(flightInfo);
                label.setEnabled(false);
                label.setSize(new Dimension(100, 100));
                label.setBorder(createLineBorder(new Color(150, 150, 150)));
                labels.add(label);
                reservationPanel.add(label);
            }

            sortByDate();
            for (int i = 0; i < numLabels; i++) {
                JList<String> label = labels.get(i);
                reservationPanel.add(labels.get(i));
                int finalNumLabels = numLabels;
                int curr = reservations.get(i);
                int finalI = i;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        for (int k = 0; k < finalNumLabels; k++) {
                            labels.get(k).setBackground(Color.decode("#FFFFFF"));
                            labels.get(k).setForeground(Color.decode("#b3d7ff"));
                        }
                        label.setBackground(Color.decode("#b3d7ff"));
                        chosenReservation = curr;
                        reservationCost = prices.get(finalI);
                    }
                });
            }

            reservationPanel.setLayout(new GridLayout(max(4, numLabels), 1, 5, 5));
            reservationPanel.revalidate();
            reservationPanel.repaint();
        }
    }

    public void sortByDate(){
        int pos;

        JList<String> temp1;
        int temp2;
        Double temp3;
        Long temp4;

        for (int i = 0; i < times.size(); i++) {
            pos = i;
            for (int j = i+1; j <  times.size(); j++) {
                if (times.get(j) < times.get(pos)) {
                    pos = j;
                }
            }

            temp4 = times.get(pos);
            times.set(pos, times.get(i));
            times.set(i, temp4);

            temp1 = labels.get(pos);
            labels.set(pos, labels.get(i));
            labels.set(i, temp1);

            temp2 = reservations.get(pos);
            reservations.set(pos, reservations.get(i));
            reservations.set(i, temp2);

            temp3 = prices.get(pos);
            prices.set(pos, prices.get(i));
            prices.set(i, temp3);
        }
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
