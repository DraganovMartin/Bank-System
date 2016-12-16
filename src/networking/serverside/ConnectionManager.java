package networking.serverside;

import java.util.Map;
import java.util.TreeMap;
import networking.Connection;

/**
 * A server side class that manages the active connections to clients. Only
 * allows one active connection to a client. When adding a new connection for a
 * client all existing active connections to that client are closed.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ConnectionManager {

    /**
     * A mapping of client IDs to active connections. All methods that suggest
     * concurrent access synchronize to this field. When adding a new connection
     * for a client all existing active connections to that client are closed.
     */
    private final TreeMap<String, Connection> activeConnections;

    /**
     * Whether the connection manager should accept new connections. Set to
     * false by default.
     */
    private boolean isAccepting;

    /**
     * Constructor. Accepting new connections is disabled by default. Use
     * {@link ConnectionManager#enableAccepting()} to allow accepting.
     */
    public ConnectionManager() {
        this.activeConnections = new TreeMap<>();
        isAccepting = false;
    }

    /**
     * Returns whether the connection manager should accept new connections. Set
     * to false by default.
     *
     * @return true if accepting, otherwise false.
     */
    public final boolean isAccepting() {
        return this.isAccepting;
    }

    /**
     * Enables the accepting of new connections
     */
    public final void enableAccepting() {
        synchronized (this.activeConnections) {
            this.isAccepting = true;
        }
    }

    /**
     * Disables the accepting of new connections
     */
    public final void disableAccepting() {
        synchronized (this.activeConnections) {
            this.isAccepting = false;
        }
    }

    /**
     * Adds a client-connection mapping. When adding a new connection for a
     * client all existing active connections to that client are closed.
     *
     * @param clientID the client ID.
     *
     * @param connection the current active connection to use for the client.
     *
     * @return true if successful, otherwise false.
     */
    public boolean connect(String clientID, Connection connection) {
        if (this.isAccepting && (clientID != null) && (connection != null)) {
            synchronized (this.activeConnections) {
                // closes any already active connection to the same client:
                Connection alreadyActive = this.activeConnections.get(clientID);
                if (alreadyActive != null) {
                    alreadyActive.interrupt();
                    while (alreadyActive.isAlive()) {
                        try {
                            alreadyActive.join();
                        } catch (Exception ex) {
                        }
                    }
                    this.activeConnections.remove(clientID);
                }
                // adds the connection to the mapping:
                this.activeConnections.put(clientID, connection);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Closes the active connection to the specified client.
     *
     * @param clientID the client ID.
     *
     * @return true if successful, otherwise false.
     */
    public boolean disconnect(String clientID) {
        if (clientID != null) {
            synchronized (this.activeConnections) {
                Connection alreadyActive = this.activeConnections.get(clientID);
                if (alreadyActive != null) {
                    alreadyActive.interrupt();
                    while (alreadyActive.isAlive()) {
                        try {
                            alreadyActive.join();
                        } catch (Exception ex) {
                        }
                    }
                    this.activeConnections.remove(clientID);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Stops all active connections and disables the addition of new
     * connections.
     *
     * @return true if successful, otherwise false.
     */
    public boolean stopAll() {
        synchronized (this.activeConnections) {
            this.isAccepting = false;
            for (Map.Entry<String, Connection> entry : this.activeConnections.entrySet()) {
                Connection alreadyActive = entry.getValue();
                if (alreadyActive != null) {
                    alreadyActive.interrupt();
                    while (alreadyActive.isAlive()) {
                        try {
                            alreadyActive.join();
                        } catch (Exception ex) {
                        }
                    }
                    this.activeConnections.remove(entry.getKey());
                }
            }
        }
        return true;
    }
}
