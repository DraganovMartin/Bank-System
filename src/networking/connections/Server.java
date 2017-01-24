package networking.connections;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;

/**
 * A class that executes the server-side activities.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class Server {

    /**
     * A factory for creating server sockets.
     */
    ServerSocketFactory serverSocketFactory;

    /**
     * A {@link MessageHandler} to process the incoming messages.
     */
    final MessageHandler messageHandler;

    /**
     * The {@link ConnectionManager} for the server.
     */
    ConnectionManager connectionManager;

    /**
     * Constructor.
     *
     * @param serverSocketFactory a factory for creating server sockets.
     *
     * @param messageHandler a {@link MessageHandler} to process the incoming
     * messages.
     */
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
            this.serverSocketFactory = serverSocketFactory;
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
     * @throws NullPointerException if server socket factory is missing (not
     * set)
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
    public synchronized boolean start(int port) throws NullPointerException, IOException, SecurityException, IllegalArgumentException {
        if (!this.isRunning()) {
            boolean keepRunning = true;
            this.connectionManager = new ConnectionManager(this, port);
            try {
                this.connectionManager.initialize();
                this.connectionManager.start();
            } catch (NullPointerException ex) {
                keepRunning = false;
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
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

    /**
     * Stops the server and terminates the {@link ConnectionManager] and all active connections.
     */
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

    /**
     * Sends the provided {@link Message} to the client connected under the
     * specified connection log number;
     *
     * @param message the {@link Message} to send.
     *
     * @param logNumber the log number of the target connection.
     */
    public synchronized void send(Message message, BigInteger logNumber) {
        if (this.connectionManager != null) {
            Connection unverified = this.connectionManager.unverified.get(logNumber);
            if (unverified != null) {
                try {
                    unverified.send(message);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                for (Map.Entry<String, Serverside> entry : this.connectionManager.verified.entrySet()) {
                    if (entry.getValue().logNumber.equals(logNumber)) {
                        Connection verified = entry.getValue();
                        try {
                            verified.send(message);
                        } catch (IOException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
}
