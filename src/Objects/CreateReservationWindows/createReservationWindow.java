package Objects.CreateReservationWindows;

import Objects.databaseConnector;
import Objects.LoginWindows.mainMenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

import static javax.swing.BorderFactory.createLineBorder;

public class createReservationWindow extends JFrame {
    private JPanel createReservationPanel;
    private JTextField fromEntry;
    private JLabel FromLabel;
    private JLabel AvailReservation;
    private JTextField toEntry;
    private JButton searchButton;
    private JButton backButton;
    private JLabel ToLabel;
    private JScrollPane availabilityPane;
    private JPanel panel;
    private JLabel invalidLabel1;
    private JLabel invalidLabel2;
    private JTextField flightNumberField;
    private JButton createReservationButton;
    private JLabel dateLabel;
    private JLabel invalidLabel3;

    private final mainMenuWindow mainMenuChoicesWindow;
    private final createReservationWindow createReservationWindow = this;

    private final int userID;

    public createReservationWindow(mainMenuWindow mainMenuChoicesWindow, int id){

        final String[] chosenFlight = {""};

        this.mainMenuChoicesWindow = mainMenuChoicesWindow;
        this.userID = id;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        setTitle("Choose");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(new GridLayout(20, 1, 2, 5));
        setContentPane(createReservationPanel);

        invalidLabel1.setVisible(false);
        invalidLabel2.setVisible(false);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    invalidLabel1.setVisible(false);
                    invalidLabel2.setVisible(false);
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();

                    String fromInput = fromEntry.getText().toLowerCase();
                    String toInput = toEntry.getText().toLowerCase();

                    chosenFlight[0] = "";

                    if(fromInput.equals("") && toInput.equals("")){
                        return;
                    }

                    int fromID = 0;
                    int toID = 0;

                    ArrayList<Integer> flights = new ArrayList<>();
                    int numFlights = 0;
                    int existingFlights = 0;

                    String sql = "SELECT location_name, id FROM airport_locations";
                    Connection conn = databaseConnector.getConnection();
                    Statement myStmt = conn.createStatement();
                    ResultSet RS = myStmt.executeQuery(sql);
                    while (RS.next()) {
                        if(fromInput.equals(RS.getString(1).toLowerCase())){
                            fromID = RS.getInt(2);
                        }
                        if(toInput.equals(RS.getString(1).toLowerCase())){
                            toID = RS.getInt(2);
                        }
                    }

                    if(fromID == 0){
                        if(toID == 0 && !toInput.equals("")){
                            invalidLabel2.setVisible(true);
                        }
                        invalidLabel1.setVisible(true);
                        return;
                    }
                    if(toID == 0 && !toInput.equals("")){
                        invalidLabel2.setVisible(true);
                        return;
                    }

                    sql = "SELECT source_id, destination_id, id FROM airline_connecting_flights";
                    RS = myStmt.executeQuery(sql);
                    while (RS.next()) {
                        if(fromID == RS.getInt(1) && (toID == RS.getInt(2) || toID == 0)){
                            numFlights++;
                            flights.add(RS.getInt(3));
                        }
                    }

                    sql = "SELECT flight, departureTime, flightNumber FROM flights";
                    RS = myStmt.executeQuery(sql);
                    String flightNumber = "";
                    String info = "";
                    String time = "";

                    ArrayList<JList<String>> labels = new ArrayList<>();
                    ArrayList<String> flightNumbers = new ArrayList<>();
                    int numLabels = 0;

                    while (RS.next()) {
                        for (int i = 0; i < numFlights; i++) {
                            if (RS.getInt(1) == flights.get(i)) {
                                existingFlights++;
                                numLabels++;

                                flightNumber = RS.getString(3);
                                time = RS.getString(2);
                                info = printFlightData(RS.getInt(1));

                                String[] flightInfo = new String[4];
                                flightInfo[0] = "Flight " + flightNumber + ": " + info;
                                flightInfo[1] = " ";
                                flightInfo[2] = "Departure Time: " + time;
                                flightInfo[3] = " ";

                                JList<String> label = new JList<String>(flightInfo);
                                label.setEnabled(false);
                                label.setSize(new Dimension(100, 100));
                                label.setBorder(createLineBorder(new Color(150, 150, 150)));
                                labels.add(label);
                                flightNumbers.add(flightNumber);
                                panel.add(label);
                            }
                        }
                    }

                    if(existingFlights == 0) {
                        String[] noResult = new String[1];
                        noResult[0] = "No Flights Found.";
                        JList<String> label = new JList<String>(noResult);
                        label.setEnabled(false);
                        label.setSize(new Dimension(100, 100));
                        label.setBorder(createLineBorder(new Color(150, 150, 150)));
                        panel.add(label);
                    }
                    panel.revalidate();
                    panel.repaint();

                    for(int i = 0; i < numLabels;i++) {
                        JList<String> label = labels.get(i);
                        String finalFlightNumber = flightNumbers.get(i);
                        int finalNumLabels = numLabels;

                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                chosenFlight[0] = finalFlightNumber;
                                System.out.println(chosenFlight[0]);
                                for (int k = 0; k < finalNumLabels; k++) {
                                    labels.get(k).setBackground(Color.decode("#FFFFFF"));
                                    labels.get(k).setForeground(Color.decode("#b3d7ff"));
                                }
                                label.setBackground(Color.decode("#b3d7ff"));
                            }
                        });

                    }


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuChoicesWindow.activate();
                deactivate();
            }
        });

        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String sql = "SELECT flightNumber FROM flights";
                    Connection conn = databaseConnector.getConnection();
                    Statement myStmt = conn.createStatement();
                    ResultSet RS = myStmt.executeQuery(sql);
                    while (RS.next()) {
                        if(RS.getString(1).equals(chosenFlight[0])){
                            bookFlightWindow baggageScreen = new bookFlightWindow(createReservationWindow, chosenFlight[0]);
                            baggageScreen.activate();
                            deactivate();
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public String printFlightData(int flight) {

        String statement = "";

        try {
            int departureID = 0;
            int destinationID = 0;

            String departure = "";
            String destination = "";

            String sql = "SELECT id, source_id, destination_id FROM airline_connecting_flights";
            Connection conn = databaseConnector.getConnection();
            Statement myStmt = conn.createStatement();
            ResultSet RS = myStmt.executeQuery(sql);
            while (RS.next()) {
                if (flight == RS.getInt(1)) {
                    departureID = RS.getInt(2);
                    destinationID = RS.getInt(3);
                }
            }

            sql = "SELECT id, location_name FROM airport_locations";
            RS = myStmt.executeQuery(sql);
            while (RS.next()) {
                if (departureID == RS.getInt(1)) {
                    departure = RS.getString(2);
                }
                if (destinationID == RS.getInt(1)) {
                    destination = RS.getString(2);
                }
            }

            statement = departure + " to " + destination;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return statement;
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
