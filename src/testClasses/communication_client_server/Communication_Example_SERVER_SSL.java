package testClasses.communication_client_server;

import javax.net.ServerSocketFactory;
import networking.connections.Server;
import networking.connections.ServerGUI;
import networking.connections.ServerGUI_SSL;
import networking.messageHandlers.MappedMessageHandler;
import networking.messages.LoginRequest;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Communication_Example_SERVER_SSL {

    public final static String HOSTNAME = "localhost";
    public final static int HOSTPORT = 15000;
    public final static int SLEEPTIME = 5000000; // ms

    public static void main(String[] args) {
        // SERVER PART:
        System.out.println("Starting server...");
        // initialize a mapped message handler:
        MappedMessageHandler serversideHandler = new MappedMessageHandler();
        // create a server object:
        Server server = new ServerGUI_SSL(serversideHandler, "Server");
        // add specific handlers to the mapped handler:
        serversideHandler.set(LoginRequest.TYPE, new ExampleLoginRequestHandler(server));

        /*
        // run the server:
        server.start(HOSTPORT);

        // SLEEP:
        try {
            System.out.print("Waiting for clients to connect...");
            for (int i = SLEEPTIME / 1000; i > 0; i--) {
                System.out.print(" " + i);
                Thread.sleep(1000);
            }
            System.out.println(" DONE.");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Communication_Example_SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }

        // stop server:
        System.out.println("Stopping server (respectively all clients)...");
        server.stop();
         */
    }
}
