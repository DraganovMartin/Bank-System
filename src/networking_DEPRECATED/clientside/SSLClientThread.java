package networking_DEPRECATED.clientside;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import networking_DEPRECATED.messages.ClientAuthenticationRequest;
import networking_DEPRECATED.messages.ClientAuthenticationResponse;
import networking_DEPRECATED.Connection;
import networking_DEPRECATED.MessageHandler;

/**
 * A class that executes the client side activities.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class SSLClientThread extends Thread {

    /**
     * The username that the client provides to login.
     */
    private final String username;

    /**
     * The password that the client provides to login.
     */
    private final String password;

    /**
     * The client {@link MessageHandler} for server messages. Incoming server
     * messages are sent to this handler for response.
     */
    private final MessageHandler messageHandler;

    /**
     * A {@link SSLSocketFactory} used to provide communication security
     * (encryption).
     */
    private final SSLSocketFactory sslSocketFactory;

    /**
     * Secure socket protocol implementation for the client to use.
     */
    private final SSLContext sslContext;

    /**
     * The server host name to which to connect.
     */
    private final String host;

    /**
     * The server port to connect to.
     */
    private final int port;

    /**
     * The currently active {@link Socket} connected to the server.
     */
    private Socket socket;

    /**
     * The {@link Connection} that is established after successful login.
     */
    private Connection connection;

    /**
     * Constructor.
     *
     * @param sslContext secure socket protocol implementation for the client to
     * use.
     *
     * @param host the server host name to which to connect.
     *
     * @param port the server port to connect to.
     *
     * @param messageHandler the client {@link MessageHandler} for server
     * messages. Incoming server messages are sent to this handler for response.
     *
     * @param username the username that the client provides to login.
     *
     * @param password the password that the client provides to login.
     */
    public SSLClientThread(SSLContext sslContext, String host, int port, MessageHandler messageHandler, String username, String password) {
        this.sslContext = sslContext;
        this.host = host;
        this.port = port;
        this.messageHandler = messageHandler;
        this.sslSocketFactory = sslContext.getSocketFactory();
        this.socket = null;
        this.username = username;
        this.password = password;
        this.connection = null;
    }

    @Override
    public void run() {
        // if client is porperly initialized:
        if ((this.sslContext != null) && (this.host != null) && (this.messageHandler != null)) {
            boolean keepRunning = true;
            while (keepRunning && !(this.isInterrupted())) {
                this.socket = null;
                try {
                    this.socket = this.sslSocketFactory.createSocket(this.host, this.port);
                } catch (Exception ex) {
                    keepRunning = false;
                }
                ObjectInputStream istream = null;
                ObjectOutputStream ostream = null;
                if (keepRunning && this.socket != null) {
                    try {
                        istream = new ObjectInputStream(socket.getInputStream());
                        ostream = new ObjectOutputStream(socket.getOutputStream());
                    } catch (Exception ex) {
                        keepRunning = false;
                    }
                }
                ClientAuthenticationRequest request = null;
                ClientAuthenticationResponse response = null;
                if (keepRunning && (istream != null) && (ostream != null)) {
                    request = new ClientAuthenticationRequest(username, password);
                    try {
                        ostream.writeObject(request);
                        ostream.flush();
                    } catch (Exception ex) {
                        keepRunning = false;
                    }
                    if (keepRunning) {
                        try {
                            response = (ClientAuthenticationResponse) istream.readObject();
                        } catch (Exception ex) {
                            keepRunning = false;
                        }
                    }
                    if (keepRunning && (response != null)) {
                        this.connection = new Connection(socket, messageHandler, Connection.SIDE.CLIENT);
                        this.connection.start();
                    }
                }
                while (keepRunning) {
                    try {
                        this.connection.join();
                    } catch (InterruptedException ex) {
                        keepRunning = false;
                        this.connection.interrupt();
                        while (this.connection.isAlive()) {
                            try {
                                this.connection.join();
                            } catch (InterruptedException ex1) {
                            }
                        }
                        this.connection = null;
                    }
                }
            }
            try {
                this.socket.close();
            } catch (IOException ex) {
            }
        }
    }
}
