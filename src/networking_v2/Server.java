package networking_v2;

import networking_v2.messages.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;

/**
 * Server class. Executed as a thread. Accepts incoming connection requests and
 * creates and manages connections server-side.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Server extends Thread implements MessageHandler {

    Serverside TESTTHREAD;

    /**
     * Debug mode.
     */
    public static boolean DEBUG = true;

    public final ServerSocketFactory serverSocketFactory;
    public int port;
    public ServerSocket serverSocket;
    public final MessageHandler messageHandler;
    public final ExecutorService executor;
    private boolean canHandle_synch_lock;

    public Server(ServerSocketFactory serverSocketFactory, int port, MessageHandler messageHandler) {
        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
        this.serverSocket = null;
        this.messageHandler = messageHandler;
        this.executor = Executors.newCachedThreadPool();
        this.canHandle_synch_lock = false;
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
                    TESTTHREAD = new Serverside(socket, this);
                    this.executor.execute(TESTTHREAD);
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
        List<Runnable> shutdownNow = this.executor.shutdownNow();
        {

        }
        try {
            this.executor.awaitTermination(10, TimeUnit.SECONDS);
            if (Server.DEBUG) {
                System.out.println("SERVER awaiting executor termination ...");
            }
        } catch (InterruptedException ex) {
            InterruptedException ex2 = ex;
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            TESTTHREAD.interrupt();
            TESTTHREAD.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
