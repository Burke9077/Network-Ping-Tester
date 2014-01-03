package network.ping.tester;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.Thread.State;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author burke9077
 */
public class Connection extends JPanel implements ActionListener {
    private final int ACTION_BUTTON_SIZE = 15;
    private final long DEFAULT_MILLISECONDS_BETWEEN_PINGS = 1000;
    private JPanel p_container;
    private JTextField tf_address;
    private JButton b_test;
    private JPanel p_address;
    private JLabel l_success;
    private double d_successPercentage = 0;
    private JButton b_start, b_pause, b_stop, b_restart, b_stats;
    private PingPerformer pp_ping;
    private StatisticsView sv_stats;
    private Thread t_ping;
    private Thread t_stats;
    private ArrayList<PingStatistic> resultSet = new ArrayList<>(0);
    
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
        l_success = new JLabel(d_successPercentage + "%");
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
        b_start.setToolTipText("Start pinging specified host");
        b_pause.setToolTipText("Pause pinging in progress");
        b_stop.setToolTipText("Stop pinging in progress");
        b_restart.setToolTipText("Restart pinging, clearing statistics");
        b_stats.setToolTipText("Launch the statistics viewer");
        p_actionButtons.add(b_start);
        p_actionButtons.add(b_pause);
        p_actionButtons.add(b_stop);
        p_actionButtons.add(b_restart);
        p_actionButtons.add(b_stats);
        // Setup the ping performer
        pp_ping = new PingPerformer(PingPerformer.STOP, DEFAULT_MILLISECONDS_BETWEEN_PINGS);
        // Setup the statistics viewer
        sv_stats = new StatisticsView();
        // Add action listeners
        tf_address.addActionListener(this);
        b_test.addActionListener(this);
        b_start.addActionListener(this);
        b_stop.addActionListener(this);
        b_pause.addActionListener(this);
        b_restart.addActionListener(this);
        b_stats.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b_restart) {
            resultSet.clear();
            t_ping.interrupt();
            pp_ping = new PingPerformer(PingPerformer.STOP, DEFAULT_MILLISECONDS_BETWEEN_PINGS);
            t_ping = new Thread(pp_ping);
            pp_ping.setStatus(PingPerformer.RUN);
            t_ping.start();
        } else if ((ae.getSource() == tf_address) || (ae.getSource() == b_test) || (ae.getSource() == b_start)) {
            // Check to see if the user asked to stop (saving stats)
            if (pp_ping.getStatus() == PingPerformer.STOP) {
                resultSet.clear();
            }
            // Ensure that it isn't already running, then run
            if (pp_ping.getStatus() != PingPerformer.RUN) {
                t_ping = new Thread(pp_ping);
                pp_ping.setStatus(PingPerformer.RUN);
                t_ping.start();
            }
        } else if (ae.getSource() == b_stop) {
            pp_ping.setStatus(PingPerformer.STOP);
        } else if (ae.getSource() == b_pause) {
            // Make sure that the performer wasn't previously stopped, then pause
            if (pp_ping.getStatus() != PingPerformer.STOP) {
                pp_ping.setStatus(PingPerformer.PAUSE);
            }
        } else if (ae.getSource() == b_stats) {
            sv_stats.setStatsVisible(true);
            t_stats = new Thread(sv_stats);
            t_stats.start();
        }
    }
    
    public double round(double unrounded, int precision, int roundingMode) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }
    
    public double getSuccessRatio() {
        if (resultSet.isEmpty()) {
            return 0;
        } else {
            double success = 0;
            double failure = 0;
            for (int i=0; i<resultSet.size(); i++) {
                if (((PingStatistic)resultSet.get(i)).getSuccess()) {
                    success++;
                } else {
                    failure++;
                }
            }
            return round(success/(success+failure)*100, 5, BigDecimal.ROUND_HALF_UP);
        }
    }
    
    public void updateLabel() {
        l_success.setText(getSuccessRatio() + "%");
    }
    
    private class PingPerformer implements Runnable {
        private int i_status;
        private long l_millisecondsBetweenPings;
        public static final int RUN = 0;
        public static final int PAUSE = 1;
        public static final int STOP = 2;

        public PingPerformer(int statusWord, long millisecondsBetweenPings) {
            i_status = statusWord;
            l_millisecondsBetweenPings = millisecondsBetweenPings;
        }
                
        // Add setters and getters for internal variables
        public void setStatus(int statusWord) {
            i_status = statusWord;
        }
        public int getStatus() {
            return i_status;
        }
        
        /**
         * isReachable issues a ICMP echo request using the host machine's
         * native utility. 
         * Stolen from:
         * http://stackoverflow.com/questions/2448666/how-to-do-a-true-java-ping-from-windows
         * @param address
         * @return reachability boolean
         */
        public boolean isReachable(String address) {
            try{
                String cmd = "";
                if(System.getProperty("os.name").startsWith("Windows")) {   
                        // For Windows
                        cmd = "ping -n 1 " + tf_address.getText();
                } else {
                        // For Linux and OSX
                        cmd = "ping -c 1 " + tf_address.getText();
                }
                Process myProcess = Runtime.getRuntime().exec(cmd);
                myProcess.waitFor();
                if(myProcess.exitValue() == 0) {
                    return true;
                } else {
                    return false;
                }
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        @Override
        public void run() {
            while (i_status == RUN) {
                try {
                    // Need to ping supplied destination based on operating system
                    long l_timeBeforePing, l_timeAfterPing;
                    boolean b_isReachable;
                    String address = tf_address.getText();
                    l_timeBeforePing = System.nanoTime();
                    b_isReachable = isReachable(address);
                    l_timeAfterPing = System.nanoTime();
                    resultSet.add(new PingStatistic(b_isReachable, l_timeBeforePing, l_timeAfterPing));
                    updateLabel();
                    // We have completed this round, sleep for defined duration
                    Thread.sleep(l_millisecondsBetweenPings);
                } catch (InterruptedException ex) {
                    // Something has prevented sleep, stop operation
                    setStatus(STOP);
                }
            } 
        }
    }
    private class StatisticsView extends JFrame implements WindowListener, Runnable {
        private JLabel l_successfulPings = new JLabel("Successful Pings: " + getNumOfSuccessfulPings() + " (" + percentageOfSuccessfulPings() + "%)");
        private JLabel l_unsuccessfulPings = new JLabel("Unsuccessful Pings: " + (resultSet.size() - getNumOfSuccessfulPings()) + " (" + percentageOfUnsuccessfulPings() + "%)");
        private JLabel l_pingCount = new JLabel("Total Pings: " + resultSet.size());
        
        public StatisticsView() {
            // Setup the swing specifics
            setSize(700,500);
            setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getWidth())/2, (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight())/2);
            setIconImage(new ImageIcon(NetworkPingTester.class.getResource("/images/stats.png")).getImage());
            // Setup the tabs
            JTabbedPane tp_panes = new JTabbedPane();
            add(tp_panes);
            JPanel p_overview = new JPanel();
            JPanel p_results = new JPanel();
            JPanel p_time = new JPanel();
            tp_panes.addTab("Overview", null, p_overview, "Overview of ping results");
            tp_panes.addTab("Ping Results", null, p_results, "Percentage chart of ping results");
            tp_panes.addTab("Latency", null, p_time, "Latency results of successful pings");
            // Setup the overview JPanel
            p_overview.setOpaque(false);
            p_overview.setBorder(new EmptyBorder(5,5,5,5));
            p_overview.setLayout(new GridLayout(0,1));
            JPanel p_successfulPings = new JPanel();
            p_successfulPings.setLayout(new FlowLayout(FlowLayout.CENTER));
            p_successfulPings.setOpaque(false);
            p_overview.add(p_successfulPings);
            p_successfulPings.add(l_successfulPings);
            JPanel p_unsuccessfulPings = new JPanel();
            p_unsuccessfulPings.setLayout(new FlowLayout(FlowLayout.CENTER));
            p_unsuccessfulPings.setOpaque(false);
            p_overview.add(p_unsuccessfulPings);
            p_unsuccessfulPings.add(l_unsuccessfulPings);
            JPanel p_pingCount = new JPanel();
            p_pingCount.setLayout(new FlowLayout(FlowLayout.CENTER));
            p_pingCount.setOpaque(false);
            p_overview.add(p_pingCount);
            p_pingCount.add(l_pingCount);
        }
        
        /**
         * setStatsVisiable is used to show and hide the statistics JFrame.
         * @param isVisable 
         */
        public void setStatsVisible(boolean isVisable) {
            if (isVisable) {
                setTitle("Statistics for connection to " + tf_address.getText());
                setVisible(true);
            } else {
                setVisible(false);
            }
        }
        
        /**
         * getNumOfSuccessfulPings returns the number of successful ping
         * results as an integer.
         * @return successful ping requests
         */
        public int getNumOfSuccessfulPings() {
            int success = 0;
            for (int i=0; i<resultSet.size(); i++) {
                if (resultSet.get(i).getSuccess()) {
                    success++;
                }
            }
            return success;
        }
        
        /**
         * percentageOfSuccessfulPings returns a double that represents the
         * percentage of successful ping requests.
         * @return percentage of successful ping requests
         */
        public double percentageOfSuccessfulPings() {
            int success = getNumOfSuccessfulPings();
            if (resultSet.isEmpty()) {
                return 0;
            } else {
                return round((((double)success)/((double)resultSet.size()))*100, 5, BigDecimal.ROUND_HALF_UP);
            }
        }
        
        /**
         * percentageOfUnsuccessfulPings returns a double that represents the
         * percentage of unsuccessful ping requests.
         * @return percentage of unsuccessful ping requests
         */
        public double percentageOfUnsuccessfulPings() {
            double success = getNumOfSuccessfulPings();
            double failure = resultSet.size()-success;
            if (resultSet.isEmpty()) {
                return 0;
            } else {
                return round((failure/((double)resultSet.size()))*100, 5, BigDecimal.ROUND_HALF_UP);
            }
        }

        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {
            setStatsVisible(false);
        }

        @Override
        public void windowClosed(WindowEvent e) {}

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}

        @Override
        public void run() {
            while (isVisible()) {
                // Done with this round of work, stop
                l_successfulPings.setText("Successful Pings: " + getNumOfSuccessfulPings() + " (" + percentageOfSuccessfulPings() + "%)");
                l_unsuccessfulPings.setText("Unsuccessful Pings: " + (resultSet.size() - getNumOfSuccessfulPings()) + " (" + percentageOfUnsuccessfulPings() + "%)");
                l_pingCount.setText("Total Pings: " + resultSet.size());
                setTitle("Statistics for connection to " + tf_address.getText());
                try {
                    Thread.sleep(DEFAULT_MILLISECONDS_BETWEEN_PINGS);
                } catch (InterruptedException ex) {
                    setStatsVisible(false);
                }
            }
        }
    }
}