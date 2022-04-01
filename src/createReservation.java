import javax.swing.*;

public class createReservation extends JFrame {
    private JPanel createReservationPanel;
    private JTextField fromEntry;
    private JLabel FromLabel;
    private JLabel selectionTitle;
    private JLabel AvailReservation;
    private JTextField textField1;
    private JTextField textField2;
    private JButton searchButton;
    private JButton backButton;
    private JButton selectButton;
    private JLabel ToLabel;
    private JLabel DepartureDateLabel;
    private JScrollPane availabilityPane;

    public createReservation(){
        setContentPane(createReservationPanel);
        setTitle("Choose");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
