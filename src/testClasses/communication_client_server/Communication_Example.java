package testClasses.communication_client_server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import networking.connections.Client;
import networking.connections.Server;
import networking.messageHandlers.MappedMessageHandler;
import networking.messages.LoginRequest;
import networking.messages.Response;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Communication_Example {

    public final static String HOSTNAME = "localhost";
    public final static int HOSTPORT = 15000;
    public final static int SLEEPTIME = 5000; // ms

    public static void main(String[] args) {
        // SERVER PART:
        System.out.println("Starting server...");
        // retrieve ServerSocketFactory object;
        ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
        // initialize a mapped message handler:
        MappedMessageHandler serversideHandler = new MappedMessageHandler();
        // create a server object:
        Server server = new Server(serverSocketFactory, serversideHandler);
        // add specific handlers to the mapped handler:
        serversideHandler.set(LoginRequest.TYPE, new ExampleLoginRequestHandler(server));
        // run the server:
        server.start(HOSTPORT);

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
            Logger.getLogger(Communication_Example.class.getName()).log(Level.SEVERE, null, ex);
        }

        // CLIENT PART:
        System.out.println("Starting client...");
        // retrieve SocketFactory object:
        SocketFactory socketFactory = SocketFactory.getDefault();
        // initialize a mapped message handler:
        MappedMessageHandler clientsideHandler = new MappedMessageHandler();
        //create a client object:
        Client client = new Client(socketFactory, clientsideHandler);
        // add specific handlers to the mapped handler:
        clientsideHandler.set(Response.TYPE, new ExampleResponseHandler(client));
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
                Logger.getLogger(Communication_Example.class.getName()).log(Level.SEVERE, null, ex);
            }
            // send message from client:
            System.out.println("Sending a message from client...");
            client.send(new LoginRequest("user1", "pass1"));
        } catch (IOException ex) {
            Logger.getLogger(Communication_Example.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Communication_Example.class.getName()).log(Level.SEVERE, null, ex);
        }

        // stop server:
        System.out.println("Stopping server (respectively all clients)...");
        server.stop();
    }
}
