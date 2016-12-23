package testClasses.networking.client_server_gui_test_NOTWORKING;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import networking_DEPRECATED.MessageHandler;
import networking_DEPRECATED.clientside.ClientThread;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class FakeClientThread extends Thread {

    private final String threadID;
    private ClientThread clientThread;

    private final SocketFactory socketFactory;
    private final MessageHandler messageHandler;

    private JFrame mainFrame;
    private JPanel mainPanel;

    private JTextField serverAddressField;
    private JTextField serverPortField;
    private JTextField usernameField;
    private JTextField passwordField;

    private JLabel serverAddressLabel;
    private JLabel serverPortLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;

    private JButton connectButton;
    private JButton disconnectButton;

    public FakeClientThread(String threadID, SocketFactory socketFactory, MessageHandler messageHandler) {
        this.threadID = threadID;
        this.socketFactory = socketFactory;
        this.messageHandler = messageHandler;
        this.clientThread = null;
    }

    @Override
    public void run() {
        boolean keepRunning = true;

        // setup GUI:
        this.mainFrame = new JFrame(this.threadID);
        {
            this.mainPanel = new JPanel(new GridLayout(0, 2));
            {
                this.serverAddressField = new JTextField("localhost");
                this.serverPortField = new JTextField("15000");
                this.usernameField = new JTextField();
                this.passwordField = new JTextField();

                this.serverAddressLabel = new JLabel("server address:");
                this.serverPortLabel = new JLabel("server port:");
                this.usernameLabel = new JLabel("username:");
                this.passwordLabel = new JLabel("password:");

                this.connectButton = new JButton("Connect...");
                this.connectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        connect();
                    }
                });
                this.disconnectButton = new JButton("Disconnect");
                this.disconnectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        disconnect();
                    }
                });
            }
            {
                this.mainPanel.add(this.serverAddressLabel);
                this.mainPanel.add(this.serverAddressField);

                this.mainPanel.add(this.serverPortLabel);
                this.mainPanel.add(this.serverPortField);

                this.mainPanel.add(this.usernameLabel);
                this.mainPanel.add(this.usernameField);

                this.mainPanel.add(this.passwordLabel);
                this.mainPanel.add(this.passwordField);

                this.mainPanel.add(this.connectButton);
                this.mainPanel.add(this.disconnectButton);
            }
            this.mainFrame.add(mainPanel);
            this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.mainFrame.pack();
            this.mainFrame.setVisible(true);
        }

        // client thread operation:
        while (keepRunning && (!this.isInterrupted())) {
            if (this.clientThread != null) {
                // wait for client thread to finish:
                while (this.clientThread.isAlive()) {
                    try {
                        this.clientThread.join();
                    } catch (InterruptedException ex) {
                        keepRunning = false;
                    }
                }
            } else {
                try {
                    // check for interruption:
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    keepRunning = false;
                }
            }
        }

        // clean up:
        {
            disconnect();
            this.mainFrame.dispose();
        }
    }

    private synchronized void connect() {
        if (this.clientThread == null) {
            {
                this.clientThread = new ClientThread(this.socketFactory, this.serverAddressField.getText(), Integer.parseInt(this.serverPortField.getText()), new FakeClientsideHandler(this.threadID), this.usernameField.getText(), this.passwordField.getText());
                this.clientThread.start();
                this.mainFrame.setTitle(threadID + " RUNNING...");
                JOptionPane.showMessageDialog(this.mainFrame, this.threadID + " connected!");
            }
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, this.threadID + " has already connected!");
        }
    }

    private synchronized void disconnect() {
        if (this.clientThread != null) {
            {
                this.clientThread.interrupt();
                while (this.clientThread.isAlive()) {
                    try {
                        this.clientThread.join();
                    } catch (InterruptedException ex) {
                    }
                }
                this.clientThread = null;
                this.mainFrame.setTitle(threadID + " NOT RUNNING");
                JOptionPane.showMessageDialog(this.mainFrame, this.threadID + " disconnected!");
                interrupt();
            }
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, this.threadID + " not connected!");
        }
    }

    public static void main(String[] args) {
        FakeClientThread fake1 = new FakeClientThread("fake1", SocketFactory.getDefault(), new FakeClientsideHandler("fake1"));
        fake1.start();
        try {
            fake1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(FakeClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
