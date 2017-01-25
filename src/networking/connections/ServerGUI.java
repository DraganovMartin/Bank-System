package networking.connections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import networking.messageHandlers.MessageHandler;

/**
 * Shows a graphical user interface to control a server. Uses a main frame
 * ({@link #mainFrame}) with a one-column {@link BorderLayout}. Resizing the
 * main frame is disabled.
 * <p>
 * The controls (components) are placed in a panel ({@link #controlPanel}) which
 * is inserted into {@link #mainFrame} in the {@link BorderLayout#SOUTH}
 * position. Provides methods to disable/enable the controls to start/stop the
 * server ({@link #disableServerControls()}, {@link #enableServerControls()}).
 * <p>
 * The server status is displayed in a JPanel ({@link #statusPanel}) which is
 * inserted into {@link #mainFrame} in the {@link BorderLayout#CENTER} position.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class ServerGUI extends Server {

    class Row implements Comparable<Row> {

        BigInteger logNumber;
        String username;
        boolean isVerified;

        public Row(BigInteger logNumber, String username, boolean isVerified) {
            this.logNumber = logNumber;
            this.username = username;
            this.isVerified = isVerified;
        }

        @Override
        public int compareTo(Row other) {
            if (!this.isVerified && other.isVerified) {
                return -1;
            } else if (this.isVerified && !other.isVerified) {
                return 1;
            } else if (this.isVerified) {
                return this.username.compareTo(other.username);
            } else {
                return this.logNumber.compareTo(other.logNumber);
            }
        }
    }

    static final String STARTSERVERBUTTONTEXT = "Start server";
    static final String STOPSERVERBUTTONTEXT = "Stop server";
    static final String EXITBUTTONTEXT = "Exit";
    static final String REFRESHCLIENTLISTBUTTONTEXT = "Refresh client list...";
    static final String PORTLABELTEXT = "Server port:";
    static final String STATUSPANELTITLE = "Server status";
    static final String CONTROLPANELTITLE = "Server control panel";
    static final String CLIENTLISTPANETITLE = "Clients list";
    static final String STATUSRUNNING = "Server is running!";
    static final Color STATUSRUNNINGCOLOR = Color.GREEN;
    static final String STATUSNOTRUNNING = "Server is NOT running!";
    static final Color STATUSNOTRUNNINGCOLOR = Color.RED;

    JFrame mainFrame;
    JPanel statusPanel;
    JLabel statusLabel;
    JPanel controlPanel;
    JPanel controlPanelCenter;
    JPanel controlPanelNorth;
    JPanel controlPanelSouth;
    JPanel controlPanelEast;
    JPanel controlPanelWest;
    JLabel portLabel;
    JTextField portField;
    JButton startServerButton;
    JButton stopServerButton;
    JButton exitButton;
    JButton refreshClientListButton;
    JTable clientListTable;
    static final String[] CLIENTLISTTABLE_COLUMNNAMES = new String[]{"connection ID", "verified", "username"};
    String[][] clientListTable_rowData;
    JScrollPane clientListPane;

    /**
     * Constructor. Provides a graphical user interface for a {@link Server}.
     *
     * @param serverSocketFactory a factory for creating server sockets.
     *
     * @param messageHandler a {@link MessageHandler} to process the incoming
     * messages.
     *
     * @param title a title for the server GUI window.
     */
    public ServerGUI(ServerSocketFactory serverSocketFactory, MessageHandler messageHandler, String title) {
        super(serverSocketFactory, messageHandler);

        // initialize the GUI:
        this.mainFrame = new JFrame(title);
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.setResizable(false);
        this.statusPanel = new JPanel();
        this.statusPanel.setBorder(new TitledBorder(ServerGUI.STATUSPANELTITLE));
        this.statusLabel = new JLabel();
        this.statusPanel.add(this.statusLabel);
        this.setRunningStatus(false);
        this.controlPanel = new JPanel();
        this.controlPanel.setLayout(new BorderLayout());
        this.controlPanel.setBorder(new TitledBorder(ServerGUI.CONTROLPANELTITLE));
        {
            // create control panel structure:
            this.controlPanelCenter = new JPanel();
            this.controlPanelNorth = new JPanel(new GridLayout(0, 2));
            this.controlPanelSouth = new JPanel();
            this.controlPanelEast = new JPanel();
            this.controlPanelWest = new JPanel();
            // create components or the control panel:
            this.portLabel = new JLabel(ServerGUI.PORTLABELTEXT);
            this.portField = new JTextField();
            this.startServerButton = new JButton(ServerGUI.STARTSERVERBUTTONTEXT);
            this.stopServerButton = new JButton(ServerGUI.STOPSERVERBUTTONTEXT);
            this.exitButton = new JButton(ServerGUI.EXITBUTTONTEXT);
            this.refreshClientListButton = new JButton(ServerGUI.REFRESHCLIENTLISTBUTTONTEXT);
            {
                this.clientListTable_rowData = new String[0][0];
                this.clientListTable = new JTable(this.clientListTable_rowData, ServerGUI.CLIENTLISTTABLE_COLUMNNAMES);
            }
            this.clientListPane = new JScrollPane(this.clientListTable);
            this.clientListPane.setBorder(new TitledBorder(ServerGUI.CLIENTLISTPANETITLE));
            this.startServerButton.setEnabled(true);
            this.stopServerButton.setEnabled(false);
            this.exitButton.setEnabled(true);
            this.refreshClientListButton.setEnabled(false);
            // add action listeners to the buttons:
            this.startServerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onStartServerButton();
                }
            });
            this.stopServerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onStopServerButton();
                }
            });
            this.exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onExitButton();
                }
            });
            this.refreshClientListButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onRefreshClientListButton();
                }
            });
            // insert the components into the control panel:
            this.controlPanelNorth.add(this.portLabel);
            this.controlPanelNorth.add(this.portField);
            this.controlPanelNorth.add(this.startServerButton);
            this.controlPanelNorth.add(this.stopServerButton);
            this.controlPanelSouth.add(this.exitButton);
            this.controlPanelSouth.add(this.refreshClientListButton);
            this.controlPanelCenter.add(this.clientListPane);
            // lay out the control panel:
            this.controlPanel.add(this.controlPanelCenter, BorderLayout.CENTER);
            this.controlPanel.add(this.controlPanelNorth, BorderLayout.NORTH);
            this.controlPanel.add(this.controlPanelSouth, BorderLayout.SOUTH);
            this.controlPanel.add(this.controlPanelEast, BorderLayout.EAST);
            this.controlPanel.add(this.controlPanelWest, BorderLayout.WEST);
        }
        // pack the GUI:
        this.mainFrame.add(this.statusPanel, BorderLayout.CENTER);
        this.mainFrame.add(this.controlPanel, BorderLayout.SOUTH);
        this.mainFrame.pack();
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.setVisible(true);
    }

    /**
     * Executed when the {@link #startServerButton} is pressed.
     */
    synchronized void onStartServerButton() {
        try {
            int port = Integer.parseInt(this.portField.getText());
            boolean keepRunning = true;
            try {
                super.start(port);
            } catch (NullPointerException ex) {
                keepRunning = false;
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this.mainFrame, "Cannot start server!\n\n" + ex.getMessage());
            } catch (IOException ex) {
                keepRunning = false;
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this.mainFrame, "Cannot start server!\nNetworking error!\n\n" + ex.getMessage());
            } catch (SecurityException ex) {
                keepRunning = false;
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this.mainFrame, "Cannot start server!\nSecurity exception!\n\n" + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                keepRunning = false;
                Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this.mainFrame, "Cannot start server!\nThe port parameter is outside\nthe specified range of valid port values,\nwhich is between 0 and 65535!\n\n" + ex.getMessage());
            }
            if (keepRunning) {
                this.startServerButton.setEnabled(false);
                this.portField.setEnabled(false);
                this.stopServerButton.setEnabled(true);
                this.exitButton.setEnabled(false);
                this.refreshClientListButton.setEnabled(true);
                this.setRunningStatus(true);
                JOptionPane.showMessageDialog(this.mainFrame, "Server started at port: " + port);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Incorect port format!");
        }
    }

    /**
     * Executed when the {@link #stopServerButton} is pressed.
     */
    synchronized void onStopServerButton() {
        try {
            super.stop();
        } catch (Exception ex) {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.startServerButton.setEnabled(true);
            this.portField.setEnabled(true);
            this.stopServerButton.setEnabled(false);
            this.exitButton.setEnabled(true);
            this.refreshClientListButton.setEnabled(false);
            this.setRunningStatus(false);
            this.onRefreshClientListButton();
        }
    }

    /**
     * Executed when the {@link #exitButton} is pressed.
     */
    synchronized void onExitButton() {
        this.mainFrame.dispose();
    }

    /**
     * Executed when the {@link #refreshClientListButtonButton} is pressed.
     */
    synchronized void onRefreshClientListButton() {
        Vector<Row> rows = new Vector<>();

        if (this.connectionManager != null) {

            // add list of unverified clients to the table:
            for (Map.Entry<BigInteger, Serverside> entry : this.connectionManager.unverified.entrySet()) {
                String username = entry.getValue().username;
                BigInteger logNumber = entry.getValue().logNumber;
                boolean isVerified = false;
                rows.add(new Row(logNumber, username, isVerified));
            }

            // add list of verified clients to the table:
            for (Map.Entry<String, Serverside> entry : this.connectionManager.verified.entrySet()) {
                String username = entry.getValue().username;
                BigInteger logNumber = entry.getValue().logNumber;
                boolean isVerified = true;
                rows.add(new Row(logNumber, username, isVerified));
            }
        }

        {
            // DEBUG !!!
            //rows.add(new Row(new BigInteger("5"), "user_5", true));
            //rows.add(new Row(new BigInteger("2"), "user_2", false));
            //rows.add(new Row(new BigInteger("6"), "user_6", false));
            //rows.add(new Row(new BigInteger("4"), "user_4", true));
            //rows.add(new Row(new BigInteger("7"), "user_7", true));
            //rows.add(new Row(new BigInteger("1"), "user_1", false));
            //rows.add(new Row(new BigInteger("3"), "user_3", true));
        }

        // sort rows
        Collections.sort(rows);

        // enter table data:
        {
            int size = rows.size();
            this.clientListTable_rowData = new String[size][];
            Iterator<Row> it = rows.iterator();
            for (int i = 0; i < size; i++) {
                this.clientListTable_rowData[i] = new String[3];
                Row current = it.next();
                this.clientListTable_rowData[i][0] = current.logNumber.toString();
                this.clientListTable_rowData[i][1] = "" + current.isVerified;
                this.clientListTable_rowData[i][2] = (current.username != null) ? current.username : "";
            }
        }

        //refresh table:
        this.clientListTable = new JTable(this.clientListTable_rowData, ServerGUI.CLIENTLISTTABLE_COLUMNNAMES);

        //refresh client list:
        this.clientListPane.setViewportView(this.clientListTable);
    }

    /**
     * Disables the controls to start/stop the server.
     */
    synchronized final void disableServerControls() {
        this.startServerButton.setEnabled(false);
        this.portField.setEnabled(false);
    }

    /**
     * Enables the controls to start/stop the server.
     */
    synchronized final void enableServerControls() {
        this.startServerButton.setEnabled(true);
        this.portField.setEnabled(true);
    }

    /**
     * Changes the display of the server GUI to reflect whether the server is
     * running.
     *
     * @param isRunning whether the server is running.
     */
    synchronized final void setRunningStatus(boolean isRunning) {
        if (isRunning) {
            this.statusLabel.setText(ServerGUI.STATUSRUNNING);
            this.statusPanel.setBackground(ServerGUI.STATUSRUNNINGCOLOR);
        } else {
            this.statusLabel.setText(ServerGUI.STATUSNOTRUNNING);
            this.statusPanel.setBackground(ServerGUI.STATUSNOTRUNNINGCOLOR);
        }
    }

    public synchronized void setDefaultPortNumber(int defaultPortNumber) {
        if (!this.isRunning() || this.startServerButton.isEnabled()) {
            this.portField.setText(String.valueOf(defaultPortNumber));
        }
    }
}
