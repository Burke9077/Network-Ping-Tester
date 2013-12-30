package network.ping.tester;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author burke9077
 */
public class Connection extends JButton {
    private JPanel p_container;
    private JTextField tf_address;
    private JButton b_test;
    private JPanel p_address;
    private JLabel l_success;
    private String s_successPercentage = "---";
    
    public Connection() {
        // Setup swing specifics
        this.setOpaque(false);
        // Setup the containing JPanel
        p_container = new JPanel();
        p_container.setLayout(new BorderLayout());
        p_container.setOpaque(false);
        p_container.setBorder(new EmptyBorder(4,4,4,4));
        this.add(p_container);
        // Setup the address JPanel
        p_address = new JPanel();
        p_address.setOpaque(false);
        p_address.setLayout(new BorderLayout());
        p_container.add(p_address, BorderLayout.NORTH);
        p_address.add(new JLabel("Address:"), BorderLayout.NORTH);
        JPanel p_testAddress = new JPanel();
        p_testAddress.setOpaque(false);
        tf_address = new JTextField(10);
        p_testAddress.add(tf_address);
        b_test = new JButton("Test");
        p_testAddress.add(b_test);
        p_address.add(p_testAddress, BorderLayout.CENTER);
        // Set up the simple stats
        JPanel p_simpleStats = new JPanel();
        p_simpleStats.setOpaque(false);
        p_container.add(p_simpleStats);
        p_simpleStats.add(new JLabel("Percent Success: "));
        l_success = new JLabel(s_successPercentage + "%");
        p_simpleStats.add(l_success);
        // Set up separator and action buttons
        JPanel p_bottomFormatting = new JPanel();
        p_bottomFormatting.setOpaque(false);
        p_bottomFormatting.setLayout(new BorderLayout());
        p_container.add(p_bottomFormatting, BorderLayout.SOUTH);
        // Setup action buttons
        JPanel p_actionButtons = new JPanel();
        p_actionButtons.setOpaque(true);
        p_bottomFormatting.add(p_actionButtons, BorderLayout.CENTER);
    }
}