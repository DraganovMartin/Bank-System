package networking.connections;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // clear the list of verified connections:
        this.verified.clear();
    }

    synchronized void terminate() {
        if (this.serverSocket != null) {
            while (!this.serverSocket.isClosed()) {
                try {
                    this.serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            this.serverSocket = server.serverSocketFactory.createServerSocket(this.port);
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
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (this.serverSocket != null) {
                while (!this.serverSocket.isClosed()) {
                    try {
                        this.serverSocket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.serverSocket = null;
            }
            // clean up !!!
            stopAllUnverified();
            stopAllVerified();
            this.server.connectionManager = null;
        }
    }
}
