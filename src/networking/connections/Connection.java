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
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
abstract class Connection extends Thread {

    final Socket socket;
    final MessageHandler messageHandler;
    protected ObjectInputStream istream;
    protected ObjectOutputStream ostream;

    Connection(Socket socket, MessageHandler messageHandler) {
        this.socket = socket;
        this.messageHandler = messageHandler;
        this.istream = null;
        this.ostream = null;
    }

    final synchronized void send(Message message) throws IOException {
        if (message != null) {
            this.ostream.writeObject(message);
            this.ostream.flush();
        }
    }

    final synchronized void closeSocket() {
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
