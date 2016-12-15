package networking.serverside;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import networking.ClientAuthenticationRequest;
import networking.ClientAuthenticationResponse;
import networking.Connection;
import networking.MessageHandler;

/**
 * A class that executes the server side activities.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ServerThread extends Thread {

    /**
     * The server {@link MessageHandler} for client messages. Incoming client
     * messages are sent to this handler for response.
     */
    private final MessageHandler messageHandler;

    /**
     * The {@link ConnectionManager} for the server to use.
     */
    private final ConnectionManager connectionManager;

    /**
     * The {@link ServerSocketFactory} used to create server sockets.
     */
    private final ServerSocketFactory serverSocketFactory;

    /**
     * The port that the server will operate on.
     */
    private final int port;

    /**
     * The currently active {@link ServerSocket} where the server accepts
     * incoming client connections.
     */
    private ServerSocket serverSocket;

    /**
     * Constructor.
     *
     * @param serverSocketFactory the {@link ServerSocketFactory} used to create
     * server sockets.
     *
     * @param port the port that the server will operate on.
     *
     * @param messageHandler the server {@link MessageHandler} for client
     * messages. Incoming client messages are sent to this handler for response.
     */
    public ServerThread(ServerSocketFactory serverSocketFactory, int port, MessageHandler messageHandler) {
        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
        this.messageHandler = messageHandler;
        this.connectionManager = new ConnectionManager();
        this.serverSocket = null;
    }

    @Override
    public void run() {
        // if server is porperly initialized:
        if ((this.messageHandler != null) && (this.connectionManager != null) && (this.serverSocketFactory != null)) {
            boolean keepRunning = true;
            this.connectionManager.enableAccepting();
            while (keepRunning && !(this.isInterrupted())) {
                try {
                    this.serverSocket = this.serverSocketFactory.createServerSocket(this.port);
                } catch (Exception ex) {
                    keepRunning = false;
                }
                while (keepRunning && !(this.isInterrupted())) {
                    Socket incoming = null;
                    try {
                        incoming = this.serverSocket.accept();
                    } catch (IOException ex) {
                        // recreate the server socket:
                        try {
                            this.serverSocket = this.serverSocketFactory.createServerSocket(this.port);
                        } catch (Exception ex1) {
                            keepRunning = false;
                        }
                    }
                    if (keepRunning && incoming != null) {
                        // get the streams of the incoming socket:
                        ObjectInputStream istream = null;
                        ObjectOutputStream ostream = null;
                        try {
                            istream = new ObjectInputStream(incoming.getInputStream());
                            ostream = new ObjectOutputStream(incoming.getOutputStream());
                        } catch (Exception ex) {
                            // terminate the connection if any exception is thrown:
                            try {
                                incoming.close();
                            } catch (IOException ex1) {
                            }
                        }
                        // authenticate the incoming connection:
                        ClientAuthenticationRequest request = null;
                        ClientAuthenticationResponse response = null;
                        if (istream != null && ostream != null) {
                            try {
                                request = (ClientAuthenticationRequest) istream.readObject();
                                response = (ClientAuthenticationResponse) this.messageHandler.handle(request);
                            } catch (Exception ex) {
                                response = null;
                            }
                            if (response != null) {
                                try {
                                    ostream.writeObject(response);
                                    ostream.flush();
                                } catch (Exception ex) {
                                    try {
                                        incoming.close();
                                    } catch (Exception ex1) {
                                    }
                                    incoming = null;
                                }
                            }
                        }
                        if (incoming != null && response != null && response.isSuccessful()) {
                            // create connection and add it to the connection manager:
                            String clientPrimaryKeyValue = response.getAuthenticatedClientID();
                            Connection connection = new AuthenticatedClientConnection(incoming, clientPrimaryKeyValue, this.messageHandler, this.connectionManager);
                            this.connectionManager.connect(clientPrimaryKeyValue, connection);
                            connection.start();
                        } else if (incoming != null) {
                            // terminate the connection:
                            try {
                                incoming.close();
                            } catch (IOException ex) {
                            }
                        }
                    }
                }
            }
            try {
                this.serverSocket.close();
            } catch (IOException ex) {
            }
            this.connectionManager.disableAccepting();
            this.connectionManager.stopAll();
        }
    }
}
