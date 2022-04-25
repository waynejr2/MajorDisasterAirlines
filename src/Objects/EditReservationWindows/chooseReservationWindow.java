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

import static javax.swing.BorderFactory.createLineBorder;

public class chooseReservationWindow extends JFrame {
    private JButton cancelButton;
    private JPanel choseReservationPanel;
    private JButton backButton;
    private JScrollPane reservationPane;
    private JPanel reservationPanel;
    private JButton editButton;

    private final mainMenuWindow mainMenu;

    private editReservationWindow editReservation;

    private final int userID;
    private int chosenReservation = -1;

    public chooseReservationWindow(mainMenuWindow mainMenuWindow, int id) throws SQLException {

        this.mainMenu = mainMenuWindow;
        this.userID = id;
        int reservation;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 600;
        int windowWidth = 1000;

        setContentPane(choseReservationPanel);

        setTitle("Your Reservations");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        reservationPanel.setLayout(new GridLayout(10, 1, 5, 5));
        reservationPanel.revalidate();
        reservationPanel.repaint();

        ArrayList<JList<String>> labels = new ArrayList<>();
        ArrayList<Integer> reservations = new ArrayList<>();
        int numLabels = 0;

        ResultSet RS = databaseConnector.getResultSet("SELECT DMAuserID, flightNumber, reservations.flight, departureTime, date, numberOfTickets, numberOfBags, totalCost, reservations.id, status FROM reservations JOIN flights ON reservations.flight = flights.id");
        ResultSet RS1;
        while (RS.next()) {
            if(RS.getInt(1)==userID){
                numLabels++;
                reservations.add(RS.getInt(9));


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
                reservationPanel.add(label);
            }
        }


        reservationPanel.setLayout(new GridLayout(numLabels, 1, 5, 5));
        reservationPanel.revalidate();
        reservationPanel.repaint();

        for(int i = 0; i < numLabels;i++) {
            JList<String> label = labels.get(i);
            int finalNumLabels = numLabels;
            int curr = reservations.get(i);

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
                }
            });
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu.activate();
                dispose();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(chosenReservation != -1){
                    try {
                        editReservation = new editReservationWindow(mainMenu, chosenReservation, userID);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    editReservation.activate();
                    dispose();
                }
            }
        });
    }

    public void activate() {
        setVisible(true);
    }
    public void deactivate() {
        setVisible(false);
    }
}
