package networking_v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
public class Server extends Thread {

    public ServerSocketFactory serverSocketFactory;
    public int port;
    public ServerSocket serverSocket;

    public Server(ServerSocketFactory serverSocketFactory, int port) {
        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
        this.serverSocket = null;
    }

    @Override
    public void run() {
        try {
            // create server socket:
            this.serverSocket = serverSocketFactory.createServerSocket(this.port);
            while (!this.isInterrupted()) {
                try {
                    // listen for a connection request:
                    Socket socket = this.serverSocket.accept();
                    // create and start a server-side connection:
                    Serverside serverside = new Serverside(socket);
                    serverside.start();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // close the server socket:
            if (this.serverSocket != null) {
                try {
                    this.serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
