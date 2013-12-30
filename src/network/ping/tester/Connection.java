package network.ping.tester;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author burke9077
 */
public class Connection extends JButton {
    private JTextField tf_address;
    private JPanel p_address;
    
    public Connection() {
        // Setup swing specifics
        this.setLayout(new BorderLayout());
        // Setup the address JPanel
        p_address = new JPanel();
        p_address.setLayout(new BorderLayout());
        this.add(p_address, BorderLayout.NORTH);
        p_address.add(new JLabel("Address:"), BorderLayout.NORTH);
        tf_address = new JTextField(10);
        p_address.add(tf_address, BorderLayout.CENTER);
    }
}