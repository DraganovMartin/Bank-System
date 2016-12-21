package networking_v2;

import networking_v2.messages.Message;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import networking_v2.messages.LoginResponse;
import networking_v2.messages.RegisterRequest;
import networking_v2.messages.RegisterResponse;

/**
 * Server class. Executed as a thread. Accepts incoming connection requests and
 * creates and manages connections server-side.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Server extends Thread implements MessageHandler {

    /**
     * Debug mode.
     */
    public static boolean DEBUG = true;

    public final ServerSocketFactory serverSocketFactory;
    public int port;
    public ServerSocket serverSocket;
    public final MessageHandler messageHandler;
    // public final ExecutorService executor;
    private boolean canHandle_synch_lock;
    private BigInteger curentLogNumber_synch_lock; // used to identify connections
    private final TreeMap<BigInteger, Serverside> unverifiedConnections;
    private final TreeMap<String, Serverside> verifiedConnections;

    public Server(ServerSocketFactory serverSocketFactory, int port, MessageHandler messageHandler) {
        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
        this.serverSocket = null;
        this.messageHandler = messageHandler;
        //this.executor = Executors.newCachedThreadPool();
        this.canHandle_synch_lock = false;
        this.curentLogNumber_synch_lock = BigInteger.ZERO;
        this.unverifiedConnections = new TreeMap<>();
        this.verifiedConnections = new TreeMap<>();
    }

    @Override
    public void run() {
        this.handleEnable();
        if (Server.DEBUG) {
            System.out.println("SERVER run() started!");
        }
        try {
            // create server socket:
            this.serverSocket = serverSocketFactory.createServerSocket(this.port);
            if (Server.DEBUG) {
                System.out.println("SERVER started at port: " + this.port + " !");
            }
            while (!this.isInterrupted()) {
                try {
                    if (Server.DEBUG) {
                        System.out.println("SERVER waiting for connection request at port: " + this.port + " ...");
                    }
                    // listen for a connection request:
                    Socket socket = this.serverSocket.accept();
                    if (Server.DEBUG) {
                        System.out.println("SERVER accepted socket at port: " + this.port + " !");
                    }
                    // create and start a server-side connection:
                    //this.startUnverifiedConnection(new Serverside(socket, this, this.getNextLogNumber()));
                    this.startUnverifiedConnection(socket);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    if (!this.isInterrupted()) {
                        // exception thrown when accepting a connection request;
                        // it is possible that the exception is thrown due to an interrupt;
                        // interrupts are checked in the "while" loop - not missed;
                        try {
                            // try to deal with non-interrupt exception (i.e. socket closed);
                            // try to recreate the server socket without closing it;
                            // if it fails, that means the server socket is OK;
                            // if it succeeds, then the problem has been resolved;
                            this.serverSocket = serverSocketFactory.createServerSocket(this.port);
                        } catch (IOException ex1) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.handleDisable();
            this.cleanUp();
        }
        if (Server.DEBUG) {
            System.out.println("SERVER run() finished!");
        }
    }

    @Override
    public synchronized Message handle(Message message) {
        if (this.canHandle()) {
            return this.messageHandler.handle(message);
        } else {
            return null;
        }
    }

    private synchronized void cleanUp() {
        this.handleDisable();
        if (Server.DEBUG) {
            System.out.println("SERVER closing at port: " + this.port + " ...");
        }
        // close the server socket:
        if (this.serverSocket != null) {
            while (!this.serverSocket.isClosed()) {
                try {
                    this.serverSocket.close();
                    if (Server.DEBUG) {
                        System.out.println("SERVER at port: " + this.port + " closed!");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // terminate all server-side connections to clients:
        if (Server.DEBUG) {
            System.out.println("SERVER executor terminating active connections ...");
        }
        // stop all connections:
        this.stopAllConnections();
        // finish:
        if (Server.DEBUG) {
            System.out.println("SERVER finished at port: " + this.port + " !");
        }
    }

    public synchronized void stopServer() {
        this.handleDisable();
        if (this.isAlive() && !this.isInterrupted()) {
            this.interrupt();
        }
        if (this.serverSocket != null) {
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private synchronized void handleEnable() {
        this.canHandle_synch_lock = true;
    }

    private synchronized void handleDisable() {
        this.canHandle_synch_lock = false;
    }

    private synchronized boolean canHandle() {
        return this.canHandle_synch_lock;
    }

    private synchronized void startUnverifiedConnection(Socket socket) {
        this.curentLogNumber_synch_lock = this.curentLogNumber_synch_lock.add(BigInteger.ONE);
        BigInteger logNumber = this.curentLogNumber_synch_lock;
        Serverside connection = new Serverside(socket, this, logNumber);
        this.unverifiedConnections.put(logNumber, connection);
        //this.executor.execute(connection);
        connection.start();
    }

    public synchronized void verifyConnection(Serverside connection, Message message) {
        boolean terminate = false;
        BigInteger logNumber = connection.getLogNumber();
        // if not already verified:
        if (connection.getVerifiedUsername() != null) {
            if (message.getType().compareTo(LoginResponse.TYPE) == 0) {
                // if a valid login respones object was provided:
                LoginResponse loginResponse = (LoginResponse) message;
                String verifiedUsername = loginResponse.getVerifiedUsername();
                // if login was successful:
                if (loginResponse.isSuccessful() && (verifiedUsername != null)) {
                    // close already existing connection to the same verified username:
                    Serverside alreadyExisting = this.verifiedConnections.get(verifiedUsername);
                    if (alreadyExisting != null) {
                        // terminate the existing connection:
                        alreadyExisting.stopThread();
                        while (alreadyExisting.isAlive()) {
                            try {
                                alreadyExisting.join();
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        // remove the existing connection from the list of verified connections:
                        this.verifiedConnections.remove(verifiedUsername);
                    }
                    // verify the connection:
                    connection.setVerifiedUsername(verifiedUsername);
                    // add to the list of verified connections:
                    this.verifiedConnections.put(verifiedUsername, connection);
                    // remove from the list of unverified connections:
                    this.unverifiedConnections.remove(logNumber);
                } else {
                    terminate = true;
                }
            } else if (message.getType().compareTo(RegisterResponse.TYPE) == 0) {
                // if a valid login respones object was provided:
                RegisterResponse registerResponse = (RegisterResponse) message;
                String verifiedUsername = registerResponse.getLoginUsername();
                // if login was successful:
                if (registerResponse.isSuccessful() && (verifiedUsername != null)) {
                    // close already existing connection to the same verified username:
                    Serverside alreadyExisting = this.verifiedConnections.get(verifiedUsername);
                    if (alreadyExisting != null) {
                        // terminate the existing connection:
                        alreadyExisting.stopThread();
                        while (alreadyExisting.isAlive()) {
                            try {
                                alreadyExisting.join();
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        // remove the existing connection from the list of verified connections:
                        this.verifiedConnections.remove(verifiedUsername);
                    }
                    // verify the connection:
                    connection.setVerifiedUsername(verifiedUsername);
                    // add to the list of verified connections:
                    this.verifiedConnections.put(verifiedUsername, connection);
                    // remove from the list of unverified connections:
                    this.unverifiedConnections.remove(logNumber);
                } else {
                    terminate = true;
                }
            } else {
                terminate = true;
            }
        }
        if (terminate) {
            // terminate unverified connection:
            connection.stopThread();
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // remove from the list of unverified connections:
            this.unverifiedConnections.remove(logNumber);
        }
    }

    public synchronized void stopConnection(Serverside connection) {
        if (connection.getVerifiedUsername() == null) {
            // connection is unverified;
            BigInteger logNumber = connection.getLogNumber();
            if (Server.DEBUG) {
                System.out.println("SERVER closing unverified connection: " + logNumber + "...");
            }
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Serverside listed = this.unverifiedConnections.get(logNumber);
            if (listed != null) {
                this.unverifiedConnections.remove(logNumber);
            }
            if (Server.DEBUG) {
                System.out.println("SERVER closed unverified connection: " + logNumber + " !");
            }
        } else {
            // connection is verified;
            String verifiedUsername = connection.getVerifiedUsername();
            if (Server.DEBUG) {
                System.out.println("SERVER closing verified connection: " + verifiedUsername + "...");
            }
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Serverside listed = this.verifiedConnections.get(verifiedUsername);
            if (listed != null) {
                this.verifiedConnections.remove(verifiedUsername);
            }
            if (Server.DEBUG) {
                System.out.println("SERVER closed verified connection: " + verifiedUsername + " !");
            }
        }
    }

    private synchronized void stopAllConnections() {
        if (Server.DEBUG) {
            System.out.println("SERVER stopping all connections...");
        }
        this.stopAllUnverifiedConnections();
        this.stopAllVerifiedConnections();
    }

    private synchronized void stopAllUnverifiedConnections() {
        int size = this.unverifiedConnections.size();
        int i = size;
        if (Server.DEBUG) {
            System.out.println("SERVER currently unverified connections: " + (i) + "...");
        }
        for (Map.Entry<BigInteger, Serverside> entry : this.unverifiedConnections.entrySet()) {
            if (Server.DEBUG) {
                System.out.println("SERVER stopping unverified, remaining: " + (i--) + "...");
            }
            Serverside connection = entry.getValue();
            BigInteger logNumber = connection.getLogNumber();
            connection.stopThread();
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // clear the list of unverified connections:
        this.unverifiedConnections.clear();
    }

    private synchronized void stopAllVerifiedConnections() {
        int size = this.verifiedConnections.size();
        int i = size;
        if (Server.DEBUG) {
            System.out.println("SERVER currently verified connections: " + (i) + "...");
        }
        for (Map.Entry<String, Serverside> entry : this.verifiedConnections.entrySet()) {
            if (Server.DEBUG) {
                System.out.println("SERVER stopping verified, remaining: " + (i--) + "...");
            }
            Serverside connection = entry.getValue();
            String verifiedUsername = connection.getVerifiedUsername();
            connection.stopThread();
            while (connection.isAlive()) {
                try {
                    connection.join();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // clear the list of unverified connections:
        this.verifiedConnections.clear();
    }
}
