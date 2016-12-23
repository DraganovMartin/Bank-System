package networking.connections;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import networking.MessageHandler;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
class Server {

    final ServerSocketFactory serverSocketFactory;
    final MessageHandler messageHandler;
    ConnectionManager connectionManager;

    public Server(ServerSocketFactory serverSocketFactory, MessageHandler messageHandler) {
        this.serverSocketFactory = serverSocketFactory;
        this.messageHandler = messageHandler;
        this.connectionManager = null;
    }

    public synchronized void start(int port) {
        if (this.connectionManager != null) {
            this.connectionManager = new ConnectionManager(this, port);
            this.connectionManager.start();
        }
    }

    public synchronized void stop() {
        if (this.connectionManager != null) {
            this.connectionManager.terminate();
            while (this.connectionManager.isAlive()) {
                try {
                    this.connectionManager.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.connectionManager = null;
    }
}
