package networking.connections;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;

/**
 * A base class for the client (user interface to the system). Uses a
 * {@link MessageHandler} to process the incoming messages.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class Client {

    final SocketFactory socketFactory;
    final MessageHandler messageHandler;
    Clientside clientside;

    /**
     * Constructor.
     *
     * @param socketFactory a {@link SocketFactory} to create the client socket
     * upon connecting.
     *
     * @param messageHandler a {@link MessageHandler} to process the incoming
     * messages.
     */
    public Client(SocketFactory socketFactory, MessageHandler messageHandler) {
        this.socketFactory = socketFactory;
        this.messageHandler = messageHandler;
        this.clientside = null;
    }

    /**
     * Establishes a connection to the server.
     *
     * @param hostname the server host name (address).
     *
     * @param port the server port.
     *
     * @throws UnknownHostException as defined in {@link InetAddress}.
     *
     * @throws IOException as defined in {@link InetAddress}.
     */
    public synchronized void connect(String hostname, int port) throws UnknownHostException, IOException {
        if (this.clientside == null) {
            InetAddress inetAddress = InetAddress.getByName(hostname);
            Socket socket = this.socketFactory.createSocket(inetAddress, port);
            this.clientside = new Clientside(this, socket, messageHandler);
            this.clientside.start();
        }
    }

    /**
     * Terminates the connection to the server.
     */
    public synchronized void stop() {
        if (this.clientside != null) {
            this.clientside.closeSocket();
            while (this.clientside.isAlive()) {
                try {
                    this.clientside.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.clientside = null;
        }
    }

    /**
     * Sends a message from the client to the server.
     *
     * @param message the message to send.
     *
     * @throws IOException if not successful.
     */
    public synchronized void send(Message message) throws IOException {
        if (this.clientside != null) {
            this.clientside.send(message);
        }
    }
}
