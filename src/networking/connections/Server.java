package networking.connections;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import networking.messageHandlers.MessageHandler;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Server {

    final ServerSocketFactory serverSocketFactory;
    final MessageHandler messageHandler;
    ConnectionManager connectionManager;

    public Server(ServerSocketFactory serverSocketFactory, MessageHandler messageHandler) {
        this.serverSocketFactory = serverSocketFactory;
        this.messageHandler = messageHandler;
        this.connectionManager = null;
    }

    public synchronized void start(int port) {
        if (this.connectionManager == null) {
            this.connectionManager = new ConnectionManager(this, port);
            this.connectionManager.start();
        }
    }

    public synchronized void stop() {
        if (this.connectionManager != null) {
            this.connectionManager.terminate();
            while (this.connectionManager != null && this.connectionManager.isAlive()) {
                try {
                    System.out.println("Joining...");
                    this.connectionManager.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Joined successfully!");
            this.connectionManager = null;
        }
    }
}
