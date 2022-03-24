import javax.swing.*;

public class MainFrame extends JFrame {
    private JLabel MainTitle;
    private JLabel UserName;
    private JTextField tfUserName;
    private JLabel Password;
    private JTextField tfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel mainPanel;

    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("Major Disaster Airlines");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }
}
