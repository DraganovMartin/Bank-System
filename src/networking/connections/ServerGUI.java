package networking.connections;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import networking.messageHandlers.MessageHandler;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ServerGUI extends Server {

    static final String STARTSERVERBUTTONTEXT = "Start server";
    static final String STOPSERVERBUTTONTEXT = "Stop server";
    static final String PORTLABELTEXT = "Server port:";

    JFrame mainFrame;
    BorderLayout mainFrameLayout;
    JPanel centerPanel;
    JPanel northPanel;
    JPanel southPanel;
    JPanel eastPanel;
    JPanel westPanel;
    GridLayout southPanelLayout;
    JLabel portLabel;
    JTextField portField;
    JButton startServerButton;
    JButton stopServerButton;
    JButton exitButton;

    public ServerGUI(ServerSocketFactory serverSocketFactory, MessageHandler messageHandler, String title) {
        super(serverSocketFactory, messageHandler);

        // initialize the GUI:
        this.mainFrame = new JFrame(title);
        this.mainFrameLayout = new BorderLayout();
        this.mainFrame.setLayout(mainFrameLayout);
        this.centerPanel = new JPanel();
        this.northPanel = new JPanel();
        this.southPanel = new JPanel();
        this.eastPanel = new JPanel();
        this.westPanel = new JPanel();

        southPanelLayout = new GridLayout(2, 2);
        southPanel.setLayout(southPanelLayout);
        this.portLabel = new JLabel(ServerGUI.PORTLABELTEXT);
        this.portField = new JTextField();
        this.startServerButton = new JButton(ServerGUI.STARTSERVERBUTTONTEXT);
        this.stopServerButton = new JButton(ServerGUI.STOPSERVERBUTTONTEXT);
        this.stopServerButton.setEnabled(false);
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
        southPanel.add(this.portLabel);
        southPanel.add(this.portField);
        southPanel.add(this.startServerButton);
        southPanel.add(this.stopServerButton);

        this.mainFrame.add(this.centerPanel, BorderLayout.CENTER);
        this.mainFrame.add(this.northPanel, BorderLayout.NORTH);
        this.mainFrame.add(this.southPanel, BorderLayout.SOUTH);
        this.mainFrame.add(this.eastPanel, BorderLayout.EAST);
        this.mainFrame.add(this.westPanel, BorderLayout.WEST);
        this.mainFrame.pack();
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.setVisible(true);
    }

    synchronized void onStartServerButton() {
        try {
            int port = Integer.parseInt(this.portField.getText());
            super.start(port);
            this.startServerButton.setEnabled(false);
            this.portField.setEnabled(false);
            this.stopServerButton.setEnabled(true);
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
        }
    }
}
