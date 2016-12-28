package networking.connections;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import javax.net.ssl.SSLContext;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import networking.messageHandlers.MessageHandler;
import networking.security.SSLContextFactory;

/**
 * In addition to the functionality of {@link ServerGUI}, provides controls to
 * create and use a {@link SSLContext} for data transmission protection as
 * specified in {@link SSLContextFactory}. The SSL controls are placed in a
 * JPanel ({@link #sslPanel}) which is inserted into {@link #mainFrame} in the
 * {@link BorderLayout#NORTH} position.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ServerGUI_SSL extends ServerGUI {

    final static String SSLNOTWORKING = "SSL context created successfully!";
    final static String SSLWORKING = "SSL context not properly initialized!";
    final static String KEYSTORENOTCHOSEN = "keystore not chosen!";
    final static String TRUSTSTORENOTCHOSEN = "truststore not chosen!";
    final static String KEYSTORELABELTEXT = "Keystore:";
    final static String TRUSTSTORELABELTEXT = "Truststore:";
    final static String KEYSTORECHOOSEBUTTONTEXT = "Choose keystore...";
    final static String TRUSTSTORECHOOSEBUTTONTEXT = "Choose truststore...";
    final static String CREATESSLCONTEXTBUTTONTEXT = "Create SSL context";

    File keystoreFile;
    File truststoreFile;

    JPanel sslPanel;
    GridBagLayout sslPanelLayout;
    JLabel keystoreLabel;
    JLabel truststoreLabel;
    JTextField keystoreFileText;
    JTextField truststoreFileText;
    JButton keystoreChooseButton;
    JButton truststoreChooseButton;
    JButton createSSLContextButton;

    public ServerGUI_SSL(MessageHandler messageHandler, String title) {
        super(null, messageHandler, title);
        this.keystoreFile = null;
        this.truststoreFile = null;

        // hide the server start/stop controls until a valid SSL context is created:
        this.disableServerControls();

        // create the additional SSL-related controls:
        this.sslPanel = new JPanel();
        this.sslPanelLayout = new GridBagLayout();
        this.sslPanel.setLayout(this.sslPanelLayout);
        this.keystoreLabel = new JLabel(ServerGUI_SSL.KEYSTORELABELTEXT);
        this.truststoreLabel = new JLabel(ServerGUI_SSL.TRUSTSTORELABELTEXT);
        this.keystoreFileText = new JTextField(ServerGUI_SSL.KEYSTORENOTCHOSEN);
        this.truststoreFileText = new JTextField(ServerGUI_SSL.TRUSTSTORENOTCHOSEN);
        this.keystoreFileText.setEnabled(false);
        this.truststoreFileText.setEnabled(false);
        this.keystoreChooseButton = new JButton(ServerGUI_SSL.KEYSTORECHOOSEBUTTONTEXT);
        this.truststoreChooseButton = new JButton(ServerGUI_SSL.TRUSTSTORECHOOSEBUTTONTEXT);
        this.createSSLContextButton = new JButton(CREATESSLCONTEXTBUTTONTEXT);
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
                this.sslPanel.add(this.truststoreLabel, c);
            }
            {
                c.gridx = 1;
                c.gridy = 1;
                this.sslPanel.add(this.truststoreFileText, c);
            }
            {
                c.gridx = 2;
                c.gridy = 1;
                this.sslPanel.add(this.truststoreChooseButton, c);
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
        File chosen = fileChooser.getSelectedFile();
        return chosen;
    }
}
