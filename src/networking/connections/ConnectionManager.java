package networking.connections;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
class ConnectionManager extends Thread {

    final Server server;
    final int port;
    ServerSocket serverSocket;
    final TreeMap<BigInteger, Serverside> unverified;
    final TreeMap<String, Serverside> verified;
    BigInteger currentLogNumber;

    ConnectionManager(Server server, int port) {
        this.server = server;
        this.port = port;
        this.serverSocket = null;
        this.unverified = new TreeMap<>();
        this.verified = new TreeMap<>();
        this.currentLogNumber = BigInteger.ONE;
    }

    synchronized void register(Serverside serverside) {
        if (this.serverSocket != null) {
            // do work
            if (serverside.username == null) {
                // register unverified connection:
                this.unverified.put(currentLogNumber, serverside);
                // increment unverified counter:
                this.currentLogNumber = this.currentLogNumber.add(BigInteger.ONE);
            } else {
                // register verified connection:
                // check if username is connected:
                Serverside existing = this.verified.get(serverside.username);
                if (existing != null) {
                    // disconnect the existing connections that uses the same username:
                    existing.closeSocket();
                    // DO NOT .join() THREAD !!! DEADLOCK !!! USE deregister() INSTEAD !!!
                    // deregister the existing connections that uses the same username:
                    this.deregister(existing);
                }
                // add to verified connections list:
                this.verified.put(serverside.username, serverside);
                // remove from unverified connections list:
                this.unverified.remove(serverside.logNumber);
            }
        }
    }

    synchronized void deregister(Serverside serverside) {
        // remove from the verified connections list:
        if (serverside.username != null) {
            // only remove the active mapping if both the logNumber and username fields match:
            if ((this.verified.get(serverside.username)).logNumber.equals(serverside.logNumber)) {
                this.verified.remove(serverside.username);
            }
        }
        // remove from the unverified connections list:
        this.unverified.remove(serverside.logNumber);
    }

    synchronized void stopAllUnverified() {
        // stop all unverified connections:
        for (Map.Entry<BigInteger, Serverside> entry : this.unverified.entrySet()) {
            Serverside connection = entry.getValue();
            connection.closeSocket();
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // clear the list of unverified connections:
        this.unverified.clear();
    }

    synchronized void stopAllVerified() {
        // stop all verified connections:
        for (Map.Entry<String, Serverside> entry : this.verified.entrySet()) {
            Serverside connection = entry.getValue();
            connection.closeSocket();
            // DO NOT .JOIN() THREADS !!! DEADLOCK !!! THREADS TERMINATE IN THEIR finally{} BLOCK !!!
            /*
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             */
        }
        // clear the list of verified connections:
        this.verified.clear();
    }

    synchronized void terminate() {
        while (this.serverSocket != null && !this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        stopAllUnverified();
        stopAllVerified();
        this.serverSocket = null;
        this.server.connectionManager = null;
    }

    /**
     * Initializes the connection manager (creates a server socket using the
     * provided server socket factory). Propagates exceptions thrown by
     * {@link ServerSocketFactory#createServerSocket(int)}.
     *
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
    synchronized void initialize() throws NullPointerException, IOException, SecurityException, IllegalArgumentException {
        if (this.server.serverSocketFactory == null) {
            throw new NullPointerException("Server socket factory missing (not set)!");
        } else if (this.serverSocket == null) {
            boolean keepRunning = true;
            try {
                this.serverSocket = server.serverSocketFactory.createServerSocket(this.port);
            } catch (IOException ex) {
                // for networking errors:
                keepRunning = false;
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (SecurityException ex) {
                // if a security manager exists and its checkListen method doesn't allow the operation:
                keepRunning = false;
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IllegalArgumentException ex) {
                // if the port parameter is outside the specified range of valid
                // port values, which is between 0 and 65535, inclusive:
                keepRunning = false;
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                if (!keepRunning) {
                    this.serverSocket = null;
                }
            }
        }
    }

    /**
     * Starts the connection manager operation. The connection manager has to
     * have been successfully initialized in order to run the thread.
     */
    @Override
    public void run() {
        if (this.serverSocket != null) {
            boolean keepRunning = true;
            while (keepRunning) {
                try {
                    Socket incoming = this.serverSocket.accept();
                    // register and start connection !!!
                    Serverside newConnection = new Serverside(this.server, incoming, this.server.messageHandler, this.currentLogNumber);
                    this.register(newConnection);
                    newConnection.start();
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                    keepRunning = false;
                }
            }
            // clean up !!!
            this.terminate();
        }
    }
}
