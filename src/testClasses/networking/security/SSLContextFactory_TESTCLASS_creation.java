package testClasses.networking.security;

import java.io.File;
import javax.net.ssl.SSLContext;
import javax.swing.JFileChooser;
import networking.security.SSLContextFactory;

/**
 * A class for testing the {@link SSLContextFactory} class.
 * <p>
 * IMPORANT: a pair of working server and client keystore files is provided in
 * the "documentation/example_certificats" project folder. The server keystore
 * file ("serverkeystore.jks") password is "server", the client keystore file
 * ("clientkeystore.jks")password is "client".
 * <p>
 * To generate a pair of compatible keystore files using the Java keytool
 * provided with the JRE (JDK):
 * <p>
 * keytool -genkey -alias server -keyalg RSA -keystore server.keystore -keysize
 * 2048
 * <p>
 * keytool -genkey -alias client -keyalg RSA -keystore client.keystore -keysize
 * 2048
 * <p>
 * keytool -export -alias server -file server.crt -keystore server.keystore
 * <p>
 * keytool -export -alias client -file client.crt -keystore client.keystore
 * <p>
 * keytool -import -alias server -file server.crt -keystore client.keystore
 * <p>
 * keytool -import -alias client -file client.crt -keystore server.keystore
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class SSLContextFactory_TESTCLASS_creation {

    public static void main(String[] args) {

        // DEFINE THE CLIENT AND SERVER KEYSTORE PASSWORDS HERE:
        String serverKeyStorePassword = "server";
        String clientKeyStorePassword = "client";

        // Point to the client and server keystore files here:        
        File serverKeyStoreFile;
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select SERVER keystore file:");
            chooser.showOpenDialog(null);
            serverKeyStoreFile = chooser.getSelectedFile();
        }
        File clientKeyStoreFile;
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select CLIENT keystore file:");
            chooser.showOpenDialog(null);
            clientKeyStoreFile = chooser.getSelectedFile();
        }

        // Creating the SSLContexts:
        SSLContext contextServer = SSLContextFactory.getSSLContext(serverKeyStoreFile, serverKeyStorePassword, serverKeyStoreFile, serverKeyStorePassword);
        if (contextServer != null) {
            System.out.println("Server ssl context: SUCCESSFUL!");
        } else {
            System.out.println("Server ssl context: FAILED!");
        }
        SSLContext contextClient = SSLContextFactory.getSSLContext(clientKeyStoreFile, clientKeyStorePassword, clientKeyStoreFile, clientKeyStorePassword);
        if (contextClient != null) {
            System.out.println("Client ssl context: SUCCESSFUL!");
        } else {
            System.out.println("Client ssl context: FAILED!");
        }
    }
}
