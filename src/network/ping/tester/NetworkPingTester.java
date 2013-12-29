package network.ping.tester;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.*;

/**
 *
 * @author burke9077
 */
public class NetworkPingTester extends JFrame {

    private static final int MINIMUM_WIDTH = 600;
    private static final int MINIMUM_HEIGHT = 250;
    private static final int initialPanelCount = 3;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        // Initialize the JPanel
        JFrame testingPanel = new NetworkPingTester();
        testingPanel.setSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        testingPanel.setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width - testingPanel.getWidth())/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - testingPanel.getHeight())/2);
        testingPanel.setTitle("Network Ping Tester");
        testingPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JTextField tf_connections = new JTextField(3);
        tf_connections.setText(Integer.toString(initialPanelCount));
        p_numberOfConnections.add(tf_connections);
        // Add separator between title and connections
        p_titlePanel.add(new JSeparator());
        // Setup the connections panel
        JPanel p_connections = new JPanel();
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
    public void populateConnections(JTextField _desiredConnectionCount, JPanel _connectionPanel) {
        try {
            while (Integer.parseInt(_desiredConnectionCount.getText()) !=
                    _connectionPanel.getComponentCount()) {
                System.out.println("Populate");
                if (Integer.parseInt(_desiredConnectionCount.getText()) > 
                        _connectionPanel.getComponentCount()) {
                    // User wants more connections
                    _connectionPanel.add(new Connection());
                } else {
                    // User wants to remove a connection
                    int componentCount = _connectionPanel.getComponentCount();
                    _connectionPanel.remove(componentCount-1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
