package testClasses.networking.client_server_gui_test_NOTWORKING;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.net.ServerSocketFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import networking_DEPRECATED.MessageHandler;
import networking_DEPRECATED.messages.Message;
import networking_DEPRECATED.serverside.ServerThread;

/**
 * The graphical user interface for a {@link ServerThread}. Synchronously
 * processes and logs all incoming {@link Message}s in the order they are
 * received by any of the server's active connections to clients.
 * <p>
 * The class implements {@link MessageHandler}. Instances of the class can be
 * used as message handlers.
 * <p>
 * It is possible for instances of the class to handle messages without threads
 * being started. Starting the thread visualizes the GUI that initiates and
 * controls the server thread execution.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ServerGUI extends Thread implements MessageHandler {

    private boolean serverThreadIsRunning;
    private ServerThread serverThread;
    private int port;
    private final ServerSocketFactory serverSocketFactory;
    private final MessageHandler messageHandler;

    private final int logMaxLength;

    private static final String FRAMETITLE = "Server";
    private static final String DEFAULTSTATUSLABELTEXT = "Server not started.";
    private static final String DEFAULTSERVERPORTLABELTEXT = "Server port:";
    private static final String DEFAULTSERVERPORTFIELDTEXT = "enter server port...";
    private static final String DEFAULTSTARTSERVERBUTTONTEXT = "START server";
    private static final String DEFAULTSTOPSERVERBUTTONTEXT = "STOP server";

    private JFrame mainFrame;

    private JPanel mainPanel;
    private JPanel statusPanel;
    private JLabel statusLabel;

    private JPanel logPanel;
    private JScrollPane logScrollPane;
    private DefaultListModel logListModel;
    private JList logList;

    private JPanel commandPanel;
    private JLabel serverPortLabel;
    private JTextField serverPortField;
    private JButton startServerButton;
    private JButton stopServerButton;

    public ServerGUI(ServerSocketFactory serverSocketFactory, MessageHandler messageHandler, int logMaxLength) {
        this.serverSocketFactory = serverSocketFactory;
        this.messageHandler = messageHandler;
        this.serverThread = null;
        this.serverThreadIsRunning = false;
        if (logMaxLength > 1) {
            this.logMaxLength = logMaxLength;
        } else {
            this.logMaxLength = 1;
        }
    }

    @Override
    public void run() {
        boolean keepRunning = true;

        // initialize the GUI:
        {
            this.mainFrame = new JFrame(ServerGUI.FRAMETITLE);
            this.mainPanel = new JPanel(new BorderLayout());
            {
                {
                    this.statusPanel = new JPanel();
                    this.statusLabel = new JLabel(ServerGUI.DEFAULTSTATUSLABELTEXT);
                    this.statusPanel.add(this.statusLabel);
                    this.mainPanel.add(this.statusPanel, BorderLayout.NORTH);
                }
                {
                    this.commandPanel = new JPanel(new GridLayout(2, 2));
                    this.serverPortLabel = new JLabel(ServerGUI.DEFAULTSERVERPORTLABELTEXT);
                    this.serverPortField = new JTextField(ServerGUI.DEFAULTSERVERPORTFIELDTEXT);
                    this.startServerButton = new JButton(ServerGUI.DEFAULTSTARTSERVERBUTTONTEXT);
                    this.startServerButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            startServer();
                        }
                    });
                    this.stopServerButton = new JButton(ServerGUI.DEFAULTSTOPSERVERBUTTONTEXT);
                    this.stopServerButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            stopServer();
                        }
                    });
                    this.commandPanel.add(this.serverPortLabel);
                    this.commandPanel.add(this.serverPortField);
                    this.commandPanel.add(this.startServerButton);
                    this.commandPanel.add(this.stopServerButton);
                    this.mainPanel.add(this.commandPanel, BorderLayout.SOUTH);
                }
                {
                    this.logPanel = new JPanel(new BorderLayout());
                    this.logListModel = new DefaultListModel<>();
                    this.logList = new JList(this.logListModel);
                    this.logScrollPane = new JScrollPane(this.logList);
                    this.logPanel.add(this.logScrollPane);
                    this.mainPanel.add(this.logPanel, BorderLayout.CENTER);
                }
            }
            this.mainFrame.add(this.mainPanel);
            this.mainFrame.pack();
            this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.mainFrame.setVisible(true);
        }

        // wait for the server thread to be started and executed:
        while (keepRunning && (!this.isInterrupted())) {
            if (this.serverThread != null) {
                // wait for server thread to finish:
                while (this.serverThread.isAlive()) {
                    try {
                        this.serverThread.join();
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
            stopServer();
            this.mainFrame.dispose();
        }
    }

    @Override
    public synchronized Message handle(Message message) {
        String logEntry = "Received a " + message.getType() + " from:" + message.getClientID() + ", processing...";
        this.logListModel.addElement(logEntry);
        Message handled = this.messageHandler.handle(message);
        this.logListModel.set(this.logListModel.getSize() - 1, logEntry + " DONE!");
        if (this.logListModel.getSize() > this.logMaxLength) {
            this.logListModel.remove(0);
        }
        return handled;
    }

    private synchronized void startServer() {
        if (this.serverThreadIsRunning) {
            JOptionPane.showMessageDialog(null, "Server is already running!");
        } else {
            // stop the active server thread:
            if (this.serverThread != null) {
                this.serverThread.interrupt();
                while (this.serverThread.isAlive()) {
                    try {
                        this.serverThread.join();
                    } catch (InterruptedException ex) {
                    }
                }
                this.serverThread = null;
            }
            this.serverThreadIsRunning = false;
            try {
                this.port = Integer.parseInt(this.serverPortField.getText());
                this.serverThread = new ServerThread(this.serverSocketFactory, this.port, this.messageHandler);
                this.serverThread.start();
                this.serverThreadIsRunning = true;
                this.statusLabel.setText("Server running at port: " + port);
                JOptionPane.showMessageDialog(null, "Server started!");
            } catch (Exception ex) {
                this.serverThread = null;
                this.serverThreadIsRunning = false;
                JOptionPane.showMessageDialog(null, "Server not started!");
            }
        }
    }

    private synchronized void stopServer() {
        if (!this.serverThreadIsRunning) {
            JOptionPane.showMessageDialog(null, "Server is not running!");
        } else {
            // stop the active server thread:
            if (this.serverThread != null) {
                this.serverThread.interrupt();
                while (this.serverThread.isAlive()) {
                    try {
                        this.serverThread.join();
                    } catch (InterruptedException ex) {
                    }
                }
                this.serverThread = null;
            }
            this.serverThreadIsRunning = false;
            this.statusLabel.setText(DEFAULTSTATUSLABELTEXT);
            JOptionPane.showMessageDialog(null, "Server stopped!");
            interrupt();
        }
    }
}
