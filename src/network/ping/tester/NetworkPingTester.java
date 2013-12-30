package network.ping.tester;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author burke9077
 */
public class NetworkPingTester extends JFrame implements ActionListener {

    private static final int MINIMUM_WIDTH = 750;
    private static final int MINIMUM_HEIGHT = 250;
    private static final int initialPanelCount = 3;
    private JButton b_plus, b_minus;
    private JTextField tf_connections;
    private JPanel p_connections;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // Initialize the JPanel
        JFrame testingPanel = new NetworkPingTester();
        testingPanel.setSize(10, 10);
        testingPanel.setTitle("Network Ping Tester");
        testingPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testingPanel.pack();
        testingPanel.setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width
                - testingPanel.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height
                - testingPanel.getHeight())/2);
        testingPanel.setVisible(true);
    }
    
    /**
     * NetworkPingTester creates the GUI and 
     */
    public NetworkPingTester() {
        // Setup the swing specific parameters
        this.setLayout(new BorderLayout());
        // Setup the JPanel to be used for the title
        JPanel p_titlePanel = new JPanel();
        this.add(p_titlePanel, BorderLayout.NORTH);
        p_titlePanel.setLayout(new BorderLayout());
        // Setup the JPanel to handle the number of connections
        JPanel p_numberOfConnections = new JPanel();
        p_numberOfConnections.setLayout(new FlowLayout(FlowLayout.LEFT));
        p_titlePanel.add(p_numberOfConnections, BorderLayout.NORTH);
        p_numberOfConnections.add(new JLabel("Number of Connections"));
        // Setup the number of panels
        tf_connections = new JTextField(3);
        tf_connections.setText(Integer.toString(initialPanelCount));
        p_numberOfConnections.add(tf_connections);
        // Setup the plus minus buttons
        EmptyBorder imageBorder = new EmptyBorder(0,5,0,0);
        b_plus = new JButton("+");
        b_plus.addActionListener(this);
        b_plus.setBorder(imageBorder);
        b_minus = new JButton("-");
        b_minus.addActionListener(this);
        b_minus.setBorder(imageBorder);
        p_numberOfConnections.add(b_plus);
        p_numberOfConnections.add(b_minus);
        // Setup the connections panel
        p_connections = new JPanel();
        p_connections.setLayout(new GridLayout(0,3));
        this.add(p_connections, BorderLayout.CENTER);
        populateConnections(tf_connections, p_connections);
    }
    
    /**
     * Populate Connections matches the user's desired number of connections to 
     * those in the Connections panel. 
     * @param _desiredConnectionCount
     * @param _connectionPanel 
     */
    public void populateConnections(
            JTextField _desiredConnectionCount, JPanel _connectionPanel) {
        try {
            while (Integer.parseInt(_desiredConnectionCount.getText()) !=
                    _connectionPanel.getComponentCount()) {
                if (Integer.parseInt(_desiredConnectionCount.getText()) > 
                        _connectionPanel.getComponentCount()) {
                    // User wants more connections
                    _connectionPanel.add(new Connection());
                    this.pack();
                } else {
                    // User wants to remove a connection
                    Component[] components = _connectionPanel.getComponents();
                    _connectionPanel.removeAll();
                    this.pack();
                    for (int i=0; i<components.length-1; i++) {
                        _connectionPanel.add(components[i]);
                    }
                    this.pack();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        /*
         * For plus & minus buttons pull the number of connections from the
         * connections JPanel, in case the user entered bad data in the 
         * JTextField.
         */
        if (ae.getSource() == b_plus) {
            tf_connections.setText(
                    Integer.toString(p_connections.getComponentCount()+1));
            populateConnections(tf_connections, p_connections);
        } else if (ae.getSource() == b_minus) {
            tf_connections.setText(
                    Integer.toString(p_connections.getComponentCount()-1));
            populateConnections(tf_connections, p_connections);
        }
    }
}
