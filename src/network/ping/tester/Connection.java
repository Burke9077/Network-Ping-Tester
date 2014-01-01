package network.ping.tester;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author burke9077
 */
public class Connection extends JPanel {
    private final int ACTION_BUTTON_SIZE = 15;
    private JPanel p_container;
    private JTextField tf_address;
    private JButton b_test;
    private JPanel p_address;
    private JLabel l_success;
    private String s_successPercentage = "---";
    private double d_successPercentage;
    private JButton b_start, b_pause, b_stop, b_restart, b_stats;
    
    /**
     * Connection creates the graphical interface for users to enter addresses
     * and the listeners for action buttons, and to launch statistics.
     */
    public Connection() {
        // Setup swing specifics
        this.setOpaque(false);
        this.setBorder(new LineBorder(Color.BLACK, 1, false));
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
        p_actionButtons.setOpaque(false);
        p_bottomFormatting.add(p_actionButtons, BorderLayout.CENTER);
        b_start = new JButton(new ImageIcon(new ImageIcon(NetworkPingTester.class.getResource("/images/play.png")).getImage().getScaledInstance(ACTION_BUTTON_SIZE, ACTION_BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH)));
        b_pause = new JButton(new ImageIcon(new ImageIcon(NetworkPingTester.class.getResource("/images/pause.png")).getImage().getScaledInstance(ACTION_BUTTON_SIZE, ACTION_BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH)));
        b_stop = new JButton(new ImageIcon(new ImageIcon(NetworkPingTester.class.getResource("/images/stop.png")).getImage().getScaledInstance(ACTION_BUTTON_SIZE, ACTION_BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH)));
        b_restart = new JButton(new ImageIcon(new ImageIcon(NetworkPingTester.class.getResource("/images/reup.png")).getImage().getScaledInstance(ACTION_BUTTON_SIZE, ACTION_BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH)));
        b_stats = new JButton(new ImageIcon(new ImageIcon(NetworkPingTester.class.getResource("/images/stats.png")).getImage().getScaledInstance(ACTION_BUTTON_SIZE, ACTION_BUTTON_SIZE, java.awt.Image.SCALE_SMOOTH)));
        b_start.setBorder(null);
        b_pause.setBorder(null);
        b_stop.setBorder(null);
        b_restart.setBorder(null);
        b_stats.setBorder(null);
        b_start.setBorderPainted(false);
        b_start.setContentAreaFilled(false);
        b_start.setFocusPainted(false);
        b_start.setOpaque(false);
        b_pause.setBorderPainted(false);
        b_pause.setContentAreaFilled(false);
        b_pause.setFocusPainted(false);
        b_pause.setOpaque(false);
        b_stop.setBorderPainted(false);
        b_stop.setContentAreaFilled(false);
        b_stop.setFocusPainted(false);
        b_stop.setOpaque(false);
        b_restart.setBorderPainted(false);
        b_restart.setContentAreaFilled(false);
        b_restart.setFocusPainted(false);
        b_restart.setOpaque(false);
        b_stats.setBorderPainted(false);
        b_stats.setContentAreaFilled(false);
        b_stats.setFocusPainted(false);
        b_stats.setOpaque(false);
        p_actionButtons.add(b_start);
        p_actionButtons.add(b_pause);
        p_actionButtons.add(b_stop);
        p_actionButtons.add(b_restart);
        p_actionButtons.add(b_stats);
    }
}