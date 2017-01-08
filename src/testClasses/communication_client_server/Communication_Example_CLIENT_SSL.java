package testClasses.communication_client_server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import networking.connections.Client;
import networking.messageHandlers.MappedMessageHandler;
import networking.messages.DisconnectNotice;
import networking.messages.request.LoginRequest;
import networking.messages.Update;
import networking.security.SSLContextFactory;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class Communication_Example_CLIENT_SSL {

    public final static String HOSTNAME = "localhost";
    public final static int HOSTPORT = 15000;
    public final static int SLEEPTIME = 5000; // ms
    public final static String CLIENT_KEYSTORE_PASSWORD = "client";
    public final static String CLIENT_KEYSTORE_LOCATION = "D:\\example_certificates\\client.keystore";

    public static void main(String[] args) {

        // CLIENT PART:
        System.out.println("Starting client...");
        // initialize SSLContext:
        SSLContext sslContext = SSLContextFactory.getSSLContext(new File(CLIENT_KEYSTORE_LOCATION), CLIENT_KEYSTORE_PASSWORD);
        // get SSLSocketFactory from the context:
        SocketFactory socketFactory = sslContext.getSocketFactory();
        // initialize a mapped message handler:
        MappedMessageHandler clientsideHandler = new MappedMessageHandler();
        //create a client object:
        Client client = new Client(socketFactory, clientsideHandler);
        // add specific handlers to the mapped handler:
        clientsideHandler.set(Update.TYPE, new ExampleUpdateHandler(client));
        // add specific handlers to the mapped handler:
        clientsideHandler.set(DisconnectNotice.TYPE, new ExampleDisconnectNoticeHandler(client));
        // run the client:
        try {
            client.connect(HOSTNAME, HOSTPORT);
            // allow time for client to connect - SLEEP:
            try {
                System.out.print("Waiting...");
                for (int i = SLEEPTIME / 1000; i > 0; i--) {
                    System.out.print(" " + i);
                    Thread.sleep(1000);
                }
                System.out.println(" DONE.");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Logger.getLogger(Communication_Example_CLIENT_SSL.class.getName()).log(Level.SEVERE, null, ex);
            }
            // send message from client:
            System.out.println("Sending a message from client...");
            client.send(new LoginRequest("user1", "pass1"));
        } catch (IOException ex) {
            Logger.getLogger(Communication_Example_CLIENT_SSL.class.getName()).log(Level.SEVERE, null, ex);
        }

        // SLEEP:
        try {
            System.out.print("Waiting...");
            for (int i = SLEEPTIME / 1000; i > 0; i--) {
                System.out.print(" " + i);
                Thread.sleep(1000);
            }
            System.out.println(" DONE.");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Communication_Example_CLIENT_SSL.class.getName()).log(Level.SEVERE, null, ex);
        }

        // stop the client:
        // client.stop();
        // stop the client:
        // client.stop();
    }
}
