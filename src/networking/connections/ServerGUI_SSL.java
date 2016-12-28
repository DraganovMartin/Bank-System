package networking.connections;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.net.ssl.SSLContext;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import networking.messageHandlers.MessageHandler;
import networking.security.SSLContextFactory;

/**
 * In addition to the functionality of {@link ServerGUI}, provides controls to
 * create and use a {@link SSLContext} for data transmission protection as
 * specified in {@link SSLContextFactory}. The SSL controls are placed in a
 * JPanel ({@link #sslPanel}) which is inserted into {@link #mainFrame} in the
 * {@link BorderLayout#NORTH} position. Provides methods to disable/enable the
 * controls to setup the SSL context
 * ({@link #disableSSLcontrols()}, {@link #enableSSLcontrols()}).
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ServerGUI_SSL extends ServerGUI {

    final static String SSLNOTWORKING = "SSL context created successfully!";
    final static String SSLWORKING = "SSL context not properly initialized!";
    final static String KEYSTORENOTCHOSEN = "keystore not chosen!";
    final static String KEYSTORELABELTEXT = "Keystore:";
    final static String KEYSTOREPASSWORDLABELTEXT = "Keystore password:";
    final static String KEYSTORECHOOSEBUTTONTEXT = "Choose keystore...";
    final static String CREATESSLCONTEXTBUTTONTEXT = "Create SSL context";

    File keystoreFile;
    String keystorePassword;

    JPanel sslPanel;
    GridBagLayout sslPanelLayout;
    JLabel keystoreLabel;
    JTextField keystoreFileText;
    JLabel keystorePasswordLabel;
    JTextField keystorePasswordText;
    JButton keystoreChooseButton;
    JButton createSSLContextButton;

    public ServerGUI_SSL(MessageHandler messageHandler, String title) {
        super(null, messageHandler, title);
        this.keystoreFile = null;
        this.keystorePassword = null;

        // hide the server start/stop controls until a valid SSL context is created:
        this.disableServerControls();

        // create the additional SSL-related controls:
        this.sslPanel = new JPanel();
        this.sslPanelLayout = new GridBagLayout();
        this.sslPanel.setLayout(this.sslPanelLayout);
        this.keystoreLabel = new JLabel(ServerGUI_SSL.KEYSTORELABELTEXT);
        this.keystoreFileText = new JTextField(ServerGUI_SSL.KEYSTORENOTCHOSEN);
        this.keystoreFileText.setEnabled(false);
        this.keystoreChooseButton = new JButton(ServerGUI_SSL.KEYSTORECHOOSEBUTTONTEXT);
        {
            // set action listener for keystoreChooseButton:
            this.keystoreChooseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    keystoreFile = chooseFile(ServerGUI_SSL.KEYSTORECHOOSEBUTTONTEXT);
                    if (keystoreFile != null) {
                        keystoreFileText.setText(keystoreFile.getAbsolutePath());
                        mainFrame.pack();
                    }
                }
            });
        }
        this.keystorePasswordLabel = new JLabel(ServerGUI_SSL.KEYSTOREPASSWORDLABELTEXT);
        this.keystorePasswordText = new JTextField();
        this.createSSLContextButton = new JButton(ServerGUI_SSL.CREATESSLCONTEXTBUTTONTEXT);
        {
            // set action listener for createSSLContextButton:
            this.createSSLContextButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onCreateSSLContextButton();
                }
            });
        }
        // lay out elements using grid bag layout:
        {
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            c.weighty = 1;
            {
                c.gridx = 0;
                c.gridy = 0;
                this.sslPanel.add(this.keystoreLabel, c);
            }
            {
                c.gridx = 1;
                c.gridy = 0;
                this.sslPanel.add(this.keystoreFileText, c);
            }
            {
                c.gridx = 2;
                c.gridy = 0;
                this.sslPanel.add(this.keystoreChooseButton, c);
            }
            {
                c.gridx = 0;
                c.gridy = 1;
                c.gridwidth = 1;
                this.sslPanel.add(this.keystorePasswordLabel, c);
            }
            {
                c.gridx = 1;
                c.gridy = 1;
                c.gridwidth = 2;
                this.sslPanel.add(this.keystorePasswordText, c);
            }
            {
                c.gridx = 0;
                c.gridy = 2;
                c.gridwidth = 3;
                this.sslPanel.add(this.createSSLContextButton, c);
            }
        }
        this.mainFrame.add(sslPanel, BorderLayout.NORTH);
        this.mainFrame.pack();
    }

    File chooseFile(String title) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.showOpenDialog(this.mainFrame);
        File chosen = fileChooser.getSelectedFile();
        return chosen;
    }

    /**
     * Disables the controls to setup the SSL context.
     */
    synchronized final void disableSSLcontrols() {
        this.keystoreChooseButton.setEnabled(false);
        this.keystorePasswordText.setEnabled(false);
        this.createSSLContextButton.setEnabled(false);
    }

    /**
     * Enables the controls to setup the SSL context.
     */
    synchronized final void enableSSLcontrols() {
        this.keystoreChooseButton.setEnabled(true);
        this.keystorePasswordText.setEnabled(true);
        this.createSSLContextButton.setEnabled(true);
    }

    void onCreateSSLContextButton() {
        if (this.keystoreFile == null) {
            JOptionPane.showMessageDialog(this.mainFrame, "Keystore file not selected!");
        } else {
            this.keystorePassword = this.keystorePasswordText.getText();
            if (this.keystorePassword == null) {
                this.keystorePassword = "";
            }
            SSLContext sslContext = SSLContextFactory.getSSLContext(this.keystoreFile, keystorePassword);
            if (sslContext == null) {
                // NOT CORRECTLY INTIALIZED !!!
                JOptionPane.showMessageDialog(this.mainFrame, "SSL context creation was not successful!\nTry again!");
            } else {
                // CORRECTLY INTIALIZED !!!
                JOptionPane.showMessageDialog(this.mainFrame, "SSL context creation was successful!");
                this.setServerSocketFactory(sslContext.getServerSocketFactory());
                this.disableSSLcontrols();
                this.enableServerControls();
            }
        }
    }
}
