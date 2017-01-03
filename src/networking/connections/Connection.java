package networking.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messages.Message;
import networking.messageHandlers.MessageHandler;

/**
 * An abstract class. Uses a pair of input and output streams. Derived classes
 * implement the networking protocol of choice for the system.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
abstract class Connection extends Thread {

    /**
     * The socket to operate on.
     */
    final Socket socket;

    /**
     * A {@link MessageHandler} to process the incoming messages.
     */
    final MessageHandler messageHandler;

    /**
     * The input stream for the connection.
     */
    protected ObjectInputStream istream;

    /**
     * The output stream for the connection.
     */
    protected ObjectOutputStream ostream;

    /**
     * Constructor.
     *
     * @param socket the socket to operate on.
     *
     * @param messageHandler a {@link MessageHandler} to process the incoming
     * messages.
     */
    Connection(Socket socket, MessageHandler messageHandler) {
        this.socket = socket;
        this.messageHandler = messageHandler;
        this.istream = null;
        this.ostream = null;
    }

    /**
     * Sends a message to the other side.
     *
     * @param message the message to send.
     *
     * @throws IOException if not successful.
     */
    final synchronized void send(Message message) throws IOException {
        if (message != null) {
            this.ostream.writeObject(message);
            this.ostream.flush();
        }
    }

    /**
     * Closes the socket thus terminating the connection.
     */
    synchronized void closeSocket() {
        if (this.socket != null) {
            while (!this.socket.isClosed()) {
                try {
                    this.socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (this.ostream != null) {
            try {
                this.ostream.close();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (this.istream != null) {
            try {
                this.istream.close();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public abstract void run();
}
