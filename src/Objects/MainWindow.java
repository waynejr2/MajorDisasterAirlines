package Objects;

import javax.swing.*;
import java.sql.*;

public class MainWindow extends JFrame {
    private JPanel mainPanel;

    public MainWindow() throws SQLException{

        //create main panel
        setContentPane(mainPanel);
        setTitle("Major Disaster Airlines");
        setSize(400, 350);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }
}