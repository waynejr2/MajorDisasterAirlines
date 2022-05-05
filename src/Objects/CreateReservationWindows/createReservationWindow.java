package Objects.CreateReservationWindows;

import Objects.LoginWindows.mainMenuWindow;
import Objects.databaseConnector;
import com.toedter.calendar.JDateChooser;

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
import java.util.Date;

import static java.lang.Math.max;
import static javax.swing.BorderFactory.createLineBorder;

public class createReservationWindow extends JFrame {
    private JPanel createReservationPanel;
    private JLabel FromLabel;
    private JLabel AvailReservation;
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
    private JPanel calendarPanel;
    private JComboBox filterOption;
    private JComboBox fromEntry;
    private JComboBox toEntry;
    private JLabel invalidLabel3;

    private Calendar date = Calendar.getInstance();
    private JDateChooser calendar = new JDateChooser(date.getTime());

    private String dateDescription = "";
    private String dateString = "";
    private String timeString = "";
    final String[] chosenFlight = {""};
    final int[] chosenFlightInt = {0};
    private int chosenFlightID;
    double pricePerTicket = 0;

    private final mainMenuWindow mainMenu;
    private final createReservationWindow createReservation = this;

    private ArrayList<Integer> flights = new ArrayList<>();
    private ArrayList<String> prices = new ArrayList<>();
    private ArrayList<Double> priceValues = new ArrayList<>();
    private ArrayList<Integer> times = new ArrayList<>();
    private ArrayList<String> timeStrings = new ArrayList<>();
    private ArrayList<String> flightNumbers = new ArrayList<>();
    private ArrayList<Integer> flightType = new ArrayList<>();
    private ArrayList<String> flightData = new ArrayList<>();

    private final int userID;

    private final static String [] CITIES = new String []{"Los Angeles", "New York", "Atlanta"};



    public createReservationWindow(mainMenuWindow mainMenuWindow, int id){

        this.mainMenu = mainMenuWindow;
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
        setContentPane(createReservationPanel);

        calendarPanel.add(calendar);

        invalidLabel1.setVisible(false);
        invalidLabel2.setVisible(false);
        invalidLabel3.setVisible(false);

        panel.revalidate();
        panel.repaint();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = calendar.getDate();
                dateDescription = d.toString().substring(4, 10) + ", " + d.toString().substring(24, 28);
                int selectedMonth = monthToInt(d.toString().substring(4,7));
                int selectedDay = dayToInt(d.toString().substring(8, 10));
                int selectedYear = yearToInt(d.toString().substring(24, 28));

                int month = monthToInt(date.getTime().toString().substring(4, 7));
                int day = dayToInt(date.getTime().toString().substring(8, 10));
                int year = yearToInt(date.getTime().toString().substring(24, 28));

                dateString = selectedYear + "-" + selectedMonth + "-" + selectedDay;

                String selectedDay1;
                String selectedMonth1;

                if(selectedMonth <10){
                    selectedMonth1 = "0" + String.valueOf(selectedMonth);
                } else {
                    selectedMonth1 = String.valueOf(selectedMonth);
                }
                if(selectedDay <10){
                    selectedDay1 = "0" + String.valueOf(selectedDay);
                } else {
                    selectedDay1 = String.valueOf(selectedDay);
                }

                String dateStringSQL = selectedYear + "-" + selectedMonth1 + "-" + selectedDay1;

                int filterNum = filterOption.getSelectedIndex();

                try {
                    invalidLabel1.setVisible(false);
                    invalidLabel2.setVisible(false);
                    invalidLabel3.setVisible(false);

                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();


                    String fromInput = "";
                    String toInput = "";

                    int fromChoice = fromEntry.getSelectedIndex();
                    int toChoice = toEntry.getSelectedIndex();
                    if(fromChoice == toChoice){
                        invalidLabel3.setVisible(true);
                        return;
                    }
                    fromInput = choseCity(fromChoice);
                    toInput = choseCity(toChoice);


                    chosenFlight[0] = "";

                    if(fromInput.equals("") && toInput.equals("")){
                        return;
                    }

                    int fromID = 0;
                    int toID = 0;

                    ResultSet RS = databaseConnector.getResultSet("SELECT location_name, id FROM airport_locations");
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

                    if(selectedYear < year){
                        String[] noResult = new String[1];
                        noResult[0] = "Please enter a valid flight date.";
                        JList<String> label = new JList<String>(noResult);
                        label.setEnabled(false);
                        label.setSize(new Dimension(100, 100));
                        label.setBorder(createLineBorder(new Color(150, 150, 150)));
                        panel.add(label);
                        panel.revalidate();
                        panel.repaint();
                        panel.setLayout(new GridLayout(6, 1, 2, 5));
                        return;
                    } else if(selectedYear == year && selectedMonth < month) {
                        String[] noResult = new String[1];
                        noResult[0] = "Please enter a valid flight date.";
                        JList<String> label = new JList<String>(noResult);
                        label.setEnabled(false);
                        label.setSize(new Dimension(100, 100));
                        label.setBorder(createLineBorder(new Color(150, 150, 150)));
                        panel.add(label);
                        panel.revalidate();
                        panel.repaint();
                        panel.setLayout(new GridLayout(6, 1, 2, 5));
                        return;
                    } else if(selectedYear == year && selectedMonth == month && selectedDay < day){
                        String[] noResult = new String[1];
                        noResult[0] = "Please enter a valid flight date.";
                        JList<String> label = new JList<String>(noResult);
                        label.setEnabled(false);
                        label.setSize(new Dimension(100, 100));
                        label.setBorder(createLineBorder(new Color(150, 150, 150)));
                        panel.add(label);
                        panel.revalidate();
                        panel.repaint();
                        panel.setLayout(new GridLayout(6, 1, 2, 5));
                        return;
                    }

                    flights.clear();
                    prices.clear();
                    priceValues.clear();
                    times.clear();
                    timeStrings.clear();
                    flightNumbers.clear();
                    flightType.clear();
                    flightData.clear();
                    
                    int numFlights = 0;

                    RS = databaseConnector.getResultSet("SELECT source_id, destination_id, flights.id, ticketPrice, " +
                            "departureTime, flights.flightNumber, flights.flight, description, availableTickets FROM airline_connecting_flights JOIN flights ON airline_connecting_flights.id = " +
                            "flights.flight WHERE flights.departureDate = '" + dateStringSQL + "' AND source_id = '" + fromID + "'");
                    while (RS.next()) {
                        if((toID == RS.getInt(2) || toID == 0 )&& RS.getInt(9) > 0){
                            numFlights++;

                            String time = RS.getString(5);
                            double price = RS.getDouble(4);

                            flights.add(RS.getInt(3));
                            prices.add(String.valueOf(price));
                            priceValues.add(price);
                            times.add(timeToInt(time));
                            timeStrings.add(time);
                            flightNumbers.add(RS.getString(6));
                            flightType.add(RS.getInt(7));
                            flightData.add(RS.getString(8));
                        }
                    }

                    switch (filterNum) {
                        case 1 -> filter1();
                        case 2 -> filter2();
                        case 3 -> filter3();
                        case 4 -> filter4();
                    }

                    String flightNumber;
                    String info;
                    String time;

                    ArrayList<JList<String>> labels = new ArrayList<>();
                    int numLabels = 0;
                    int existingFlights = 0;

                    for(int i = 0; i < numFlights; i++){
                        existingFlights++;
                        numLabels++;

                        flightNumber = flightNumbers.get(i);
                        time = timeStrings.get(i);
                        info = flightData.get(i);

                        String[] flightInfo = new String[5];
                        flightInfo[0] = "Flight " + flightNumber + ": " + info;
                        flightInfo[1] = " ";
                        flightInfo[2] = "Departure Time: " + time;
                        flightInfo[3] = " ";
                        flightInfo[4] = "Price: $" + prices.get(i);

                        JList<String> label = new JList<String>(flightInfo);
                        label.setEnabled(false);
                        label.setSize(new Dimension(100, 100));
                        label.setBorder(createLineBorder(new Color(150, 150, 150)));
                        labels.add(label);
                        panel.add(label);
                    }
                    panel.setLayout(new GridLayout(max(numLabels, 6), 1, 2, 5));

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
                        int finalFlightInt = flightType.get(i);
                        int finalNumLabels = numLabels;
                        int finalI = i;
                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                chosenFlight[0] = finalFlightNumber;
                                chosenFlightInt[0] = finalFlightInt;
                                chosenFlightID = flights.get(finalI);
                                timeString = timeStrings.get(finalI);
                                pricePerTicket = Double.parseDouble(prices.get(finalI));
                                for (int k = 0; k < finalNumLabels; k++) {
                                    labels.get(k).setBackground(Color.decode("#FFFFFF"));
                                    labels.get(k).setForeground(Color.decode("#b3d7ff"));
                                }
                                label.setBackground(Color.decode("#b3d7ff"));
                            }
                        });

                    }

                    panel.revalidate();
                    panel.repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuWindow.activate();
                dispose();
            }
        });
        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    ResultSet RS = databaseConnector.getResultSet("SELECT flightNumber FROM flights");
                    while (RS.next()) {
                        if(RS.getString(1).equals(chosenFlight[0])){
                            bookFlightWindow baggageScreen = new bookFlightWindow(createReservation, mainMenu, userID, chosenFlight[0], chosenFlightInt[0], dateDescription, dateString, timeString, pricePerTicket, chosenFlightID);
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

    public static String printFlightData(int flight) {
        String statement = "";
        try {
            ResultSet RS = databaseConnector.getResultSet("SELECT description FROM airline_connecting_flights WHERE id = " + flight);
            RS.next();
            statement = RS.getString(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return statement;
    }

    public void filter1() {
        int pos;
        double temp1;
        String temp2;
        int temp3;
        for (int i = 0; i < priceValues.size(); i++)
        {
            pos = i;
            for (int j = i+1; j <  priceValues.size(); j++)
            {
                if ( priceValues.get(j) < priceValues.get(pos))                  //find the index of the minimum element
                {
                    pos = j;
                }
            }


            temp1 = priceValues.get(pos);            //swap the current element with the minimum element
            priceValues.set(pos, priceValues.get(i));
            priceValues.set(i, temp1);

            temp2 = prices.get(pos);            //swap the current element with the minimum element
            prices.set(pos, prices.get(i));
            prices.set(i, temp2);

            temp2 = timeStrings.get(pos);
            timeStrings.set(pos, timeStrings.get(i));
            timeStrings.set(i, temp2);

            temp2 = flightData.get(pos);
            flightData.set(pos, flightData.get(i));
            flightData.set(i, temp2);

            temp2 = flightNumbers.get(pos);
            flightNumbers.set(pos, flightNumbers.get(i));
            flightNumbers.set(i, temp2);

            temp3 = flightType.get(pos);
            flightType.set(pos, flightType.get(i));
            flightType.set(i, temp3);

            temp3 = flights.get(pos);            //swap the current element with the minimum element
            flights.set(pos, flights.get(i));
            flights.set(i, temp3);
        }
    }
    public void filter2() {
        int pos;
        double temp1;
        String temp2;
        int temp3;
        for (int i = 0; i < priceValues.size(); i++)
        {
            pos = i;
            for (int j = i+1; j <  priceValues.size(); j++)
            {
                if ( priceValues.get(j) > priceValues.get(pos))                  //find the index of the minimum element
                {
                    pos = j;
                }
            }

            temp1 = priceValues.get(pos);            //swap the current element with the minimum element
            priceValues.set(pos, priceValues.get(i));
            priceValues.set(i, temp1);

            temp2 = prices.get(pos);            //swap the current element with the minimum element
            prices.set(pos, prices.get(i));
            prices.set(i, temp2);

            temp2 = flightData.get(pos);
            flightData.set(pos, flightData.get(i));
            flightData.set(i, temp2);

            temp2 = timeStrings.get(pos);
            timeStrings.set(pos, timeStrings.get(i));
            timeStrings.set(i, temp2);

            temp2 = flightNumbers.get(pos);
            flightNumbers.set(pos, flightNumbers.get(i));
            flightNumbers.set(i, temp2);

            temp3 = flightType.get(pos);
            flightType.set(pos, flightType.get(i));
            flightType.set(i, temp3);

            temp3 = flights.get(pos);            //swap the current element with the minimum element
            flights.set(pos, flights.get(i));
            flights.set(i, temp3);
        }
    }
    public void filter3() {
        int pos;
        int temp1;
        String temp2;
        for (int i = 0; i < times.size(); i++)
        {
            pos = i;
            for (int j = i+1; j <  times.size(); j++)
            {
                if (times.get(j) < times.get(pos))                  //find the index of the minimum element
                {
                    pos = j;
                }
            }

            temp1 = times.get(pos);            //swap the current element with the minimum element
            times.set(pos, times.get(i));
            times.set(i, temp1);

            temp1 = flightType.get(pos);
            flightType.set(pos, flightType.get(i));
            flightType.set(i, temp1);

            temp2 = flightData.get(pos);
            flightData.set(pos, flightData.get(i));
            flightData.set(i, temp2);

            temp2 = timeStrings.get(pos);
            timeStrings.set(pos, timeStrings.get(i));
            timeStrings.set(i, temp2);

            temp2 = flightNumbers.get(pos);
            flightNumbers.set(pos, flightNumbers.get(i));
            flightNumbers.set(i, temp2);

            temp2 = prices.get(pos);            //swap the current element with the minimum element
            prices.set(pos, prices.get(i));
            prices.set(i, temp2);

            temp1 = flights.get(pos);            //swap the current element with the minimum element
            flights.set(pos, flights.get(i));
            flights.set(i, temp1);
        }
    }
    public void filter4() {
        int pos;
        int temp1;
        String temp2;
        for (int i = 0; i < times.size(); i++)
        {
            pos = i;
            for (int j = i+1; j <  times.size(); j++)
            {
                if (times.get(j) > times.get(pos))                  //find the index of the minimum element
                {
                    pos = j;
                }
            }

            temp1 = times.get(pos);            //swap the current element with the minimum element
            times.set(pos, times.get(i));
            times.set(i, temp1);

            temp1 = flightType.get(pos);
            flightType.set(pos, flightType.get(i));
            flightType.set(i, temp1);

            temp2 = flightData.get(pos);
            flightData.set(pos, flightData.get(i));
            flightData.set(i, temp2);

            temp2 = timeStrings.get(pos);
            timeStrings.set(pos, timeStrings.get(i));
            timeStrings.set(i, temp2);

            temp2 = flightNumbers.get(pos);
            flightNumbers.set(pos, flightNumbers.get(i));
            flightNumbers.set(i, temp2);

            temp2 = prices.get(pos);            //swap the current element with the minimum element
            prices.set(pos, prices.get(i));
            prices.set(i, temp2);

            temp1 = flights.get(pos);            //swap the current element with the minimum element
            flights.set(pos, flights.get(i));
            flights.set(i, temp1);
        }
    }

    public int monthToInt(String m) {
        switch (m){
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
            default: return 0;
        }
    }
    public int dayToInt(String d) {
        return ((int)d.charAt(1)-48) + ((int)d.charAt(0)-48)*10;
    }
    public int yearToInt(String y) {
        return ((int)y.charAt(0)-48)*1000 + ((int)y.charAt(1)-48)*100 + ((int)y.charAt(2)-48)*10 + ((int)y.charAt(3)-48);
    }
    public int timeToInt(String t) {
        return ((int)t.charAt(7)-48)+((int)t.charAt(6)-48)*10+((int)t.charAt(4)-48)*100+((int)t.charAt(3)-48)*1000+((int)t.charAt(1)-48)*10000+((int)t.charAt(0)-48)*100000;
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
    public String choseCity(int choice){
        String fromInput;
        switch(choice){
            case 1:
                fromInput = "Los Angeles".toLowerCase();
                break;
            case 2:
                fromInput = "New York City".toLowerCase();
                break;
            case 3:
                fromInput = "Chicago".toLowerCase();
                break;
            case 4:
                fromInput = "Dallas".toLowerCase();
                break;
            case 5:
                fromInput = "Atlanta".toLowerCase();
                break;
            case 6:
                fromInput = "Washington DC".toLowerCase();
                break;
            case 7:
                fromInput = "San Francisco".toLowerCase();
                break;
            case 8:
                fromInput = "Seattle".toLowerCase();
                break;
            case 9:
                fromInput = "San Diego".toLowerCase();
                break;
            default:
                fromInput = "null";
                break;
        }
        return fromInput;
    }
    public static void predict(JTextField field){
        String text = field.getText ();

        String prediction = null;

        for (String city: CITIES) {
            if (city.startsWith (text) && !city.equals (text)) {
                if (prediction != null) return;

                prediction = city;
            }
        }

        if (prediction != null) {
            field.setText (prediction);

            field.setCaretPosition (text.length ());
            field.select (text.length (), prediction.length ());
        }

    }

}
