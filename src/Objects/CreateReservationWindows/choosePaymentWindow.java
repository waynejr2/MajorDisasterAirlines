package Objects.CreateReservationWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static java.lang.Math.ceil;
import static java.lang.Math.min;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * choosePaymentWindow, this class lets the user choose a payment method after they are done choosing flights and
 * adding passengers and baggage has two options credit card and pay budi.
 * @author Juanito Herrera Sanchez
 */

public class choosePaymentWindow extends JFrame{
    private JButton paybudiButton;
    private JButton creditCardButton;
    private JPanel paymentOptionsPanel;
    private JButton cancelButton;
    private JPanel panel;
    private JButton flightCredit;

    private int numTickets;
    private int numBags;
    private final double ticketPrice;
    private final double bagPrice;
    private static double totalPrice = 0;
    private final double taxRate = .075;

    private bookFlightWindow bookFlight;
    private creditCardWindow creditCard;
    private payBudiWindow payBudi;

    private String[] priceInfo = new String[8];
    private int len = 34;

    private int creditUsed;
    private double chargePrice = totalPrice;

    public choosePaymentWindow(bookFlightWindow bookFlightWindow, double ticketPrice, double bagPrice, int numTickets, int numBags) throws SQLException {

        this.bookFlight = bookFlightWindow;
        this.numTickets = numTickets;
        this.numBags = numBags;
        this.ticketPrice = ticketPrice;
        this.bagPrice = bagPrice;
        this.totalPrice = (taxRate + 1)*(ticketPrice*numTickets + bagPrice*numBags);

        creditCard = new creditCardWindow(this);
        payBudi = new payBudiWindow(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        int windowHeight = 300;
        int windowWidth = 400;

        setContentPane(paymentOptionsPanel);
        setTitle("Payment Summary");
        setSize(windowWidth, windowHeight);
        setLocation(screenWidth/2 - windowWidth/2 + 400, screenHeight/2 - windowHeight/2 - 50);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String numTicketsString = String.valueOf(numTickets);
        String numBagsString = String.valueOf(numBags);
        String ticketPriceString = String.valueOf(numTickets*ticketPrice);
        String bagPriceString = String.valueOf(numBags*bagPrice);
        String subtotalPriceString = String.valueOf(numBags*bagPrice+numTickets*ticketPrice);
        String taxString = String.valueOf(taxRate*(ticketPrice*numTickets + bagPrice*numBags));
        String totalPriceString = String.valueOf(totalPrice);

        StringBuilder one = new StringBuilder();
        one.insert(0, "Tickets(" + numTicketsString + ")");
        int buffer = one.length();
        for(int i = 0; i < len - buffer - ticketPriceString.length(); i++) {
            one.append(" ");
        }
        one.append(ticketPriceString);

        StringBuilder two = new StringBuilder();
        two.insert(0, "Bags(" + numBagsString + ")");
        buffer = two.length();
        for(int i = 0; i < len - buffer - bagPriceString.length(); i++) {
            two.append(" ");
        }
        two.append(bagPriceString);

        StringBuilder three = new StringBuilder();
        for(int i = 0; i < len; i++){
            three.append("-");
        }

        StringBuilder four = new StringBuilder();
        four.insert(0, "Subtotal:");
        buffer = four.length();
        for(int i = 0; i < len - buffer - subtotalPriceString.length() - 1; i++) {
            four.append(" ");
        }
        four.append("$");
        four.append(subtotalPriceString);

        StringBuilder five = new StringBuilder();
        five.insert(0, "Taxes & Fees:");
        buffer = five.length();
        for(int i = 0; i < len - buffer - taxString.length() - 1; i++) {
            five.append(" ");
        }
        five.append("$");
        five.append(taxString);

        StringBuilder six = new StringBuilder();
        six.insert(0, "Total:");
        buffer = six.length();
        for(int i = 0; i < len - buffer - totalPriceString.length() - 1; i++) {
            six.append(" ");
        }
        six.append("$");
        six.append(totalPriceString);

        priceInfo[0] = String.valueOf(one);
        priceInfo[1] = String.valueOf(two);
        priceInfo[2] = String.valueOf(three);
        priceInfo[3] = String.valueOf(four);
        priceInfo[4] = String.valueOf(five);
        priceInfo[5] = String.valueOf(six);

        JList<String> label = new JList<String>(priceInfo);
        label.setEnabled(false);
        label.setSize(new Dimension(100, 100));
        label.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(label);
        panel.revalidate();
        panel.repaint();

        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCard.activate();
                deactivate();
            }
        });
        paybudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payBudi.activate();
                deactivate();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookFlight.activate();
                dispose();
                bookFlight.setEnabled(true);
            }
        });
        flightCredit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                int flightCredit = 1000;
                //
                creditUsed = (int) min(flightCredit, ceil(totalPrice));
                String flightCreditString = String.valueOf(creditUsed);

                chargePrice = totalPrice-creditUsed;
                if(chargePrice < 0){
                    chargePrice = 0;
                }
                String finalPriceString = String.valueOf(chargePrice);

                StringBuilder seven = new StringBuilder();
                seven.append("Flight Credit:");
                String buffer = String.valueOf(seven);
                for(int i = 0; i < len - buffer.length() - flightCreditString.length() - 2; i++){
                    seven.append(" ");
                }
                seven.append("-$");
                seven.append(flightCreditString);

                StringBuilder eight = new StringBuilder();
                eight.append("Amount due:");
                buffer = String.valueOf(eight);
                for(int i = 0; i < len - buffer.length() - finalPriceString.length() - 1; i++) {
                    eight.append(" ");
                }
                eight.append("$");
                eight.append(finalPriceString);

                priceInfo[6] = String.valueOf(seven);
                priceInfo[7] = String.valueOf(eight);

                JList<String> label1 = new JList<String>(priceInfo);
                label1.setEnabled(false);
                label1.setSize(new Dimension(100, 100));
                label1.setFont(new Font("Monospaced", Font.PLAIN, 12));
                panel.removeAll();
                panel.add(label1);
                panel.revalidate();
                panel.repaint();
            }
        });
    }

    public static double getPrice() {
        return totalPrice;
    }
    public void activate() {setVisible(true);}
    public void deactivate() {setVisible(false);}
}