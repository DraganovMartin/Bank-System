package networking.connections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ServerGUI extends Server {

    static final String STARTSERVERBUTTONTEXT = "Start server";
    static final String STOPSERVERBUTTONTEXT = "Stop server";
    static final String EXITBUTTONTEXT = "Exit";
    static final String PORTLABELTEXT = "Server port:";
    static final String STATUSPANELTITLE = "Server status";
    static final String CONTROLPANELTITLE = "Server control panel";
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
            this.startServerButton.setEnabled(true);
            this.stopServerButton.setEnabled(false);
            this.exitButton.setEnabled(true);
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
            // insert the components into the control panel:
            this.controlPanelNorth.add(this.portLabel);
            this.controlPanelNorth.add(this.portField);
            this.controlPanelNorth.add(this.startServerButton);
            this.controlPanelNorth.add(this.stopServerButton);
            this.controlPanelSouth.add(this.exitButton);
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
                this.setRunningStatus(true);
                JOptionPane.showMessageDialog(this.mainFrame, "Server started at port: " + port);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.mainFrame, "Incorect port format!");
        }
    }

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
            this.setRunningStatus(false);
        }
    }

    synchronized void onExitButton() {
        this.mainFrame.dispose();
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
}
