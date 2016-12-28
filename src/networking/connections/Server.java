package networking.connections;

import java.io.IOException;
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

    /**
     * Returns whether the server is running.
     *
     * @return whether the server is running.
     */
    public final synchronized boolean isRunning() {
        if (this.connectionManager != null) {
            return this.connectionManager.isAlive();
        } else {
            return false;
        }
    }

    /**
     * Changes the server socket factory that is currently being used by the
     * server. Only works if the server (the connection manager) is not running
     * as defined in {@link #isRunning()}.
     *
     * @param serverSocketFactory the new server socket factory.
     *
     * @return
     */
    public synchronized boolean setServerSocketFactory(ServerSocketFactory serverSocketFactory) {
        if (!this.isRunning()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Starts the server using the specified port number. Propagates exceptions
     * thrown by {@link ConnectionManager#initialize()}, which in turn
     * propagates exceptions thrown by
     * {@link ServerSocketFactory#createServerSocket(int)}.
     *
     * @param port the number of the port to be used by the server.
     *
     * @return true if the server has been successfully started, otherwise
     * false.
     *
     * @see ConnectionManager
     * @see ConnectionManager#initialize()
     * @see ServerSocketFactory
     * @see ServerSocketFactory#createServerSocket(int)
     *
     * @throws IOException for networking errors
     *
     * @throws SecurityException if a security manager exists and its
     * checkListen method doesn't allow the operation
     *
     * @throws IllegalArgumentException if the port parameter is outside the
     * specified range of valid port values, which is between 0 and 65535,
     * inclusive
     */
    public synchronized boolean start(int port) throws IOException, SecurityException, IllegalArgumentException {
        if (!this.isRunning()) {
            boolean keepRunning = true;
            this.connectionManager = new ConnectionManager(this, port);
            try {
                this.connectionManager.initialize();
                this.connectionManager.start();
            } catch (Exception ex) {
                keepRunning = false;
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                if (!keepRunning) {
                    this.connectionManager = null;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public synchronized void stop() {
        if (this.connectionManager != null) {
            this.connectionManager.terminate();
            while (this.isRunning()) {
                try {
                    this.connectionManager.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.connectionManager = null;
        }
    }
}
