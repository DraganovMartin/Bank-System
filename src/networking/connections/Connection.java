package networking.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import networking.messages.Message;
import networking.MessageHandler;

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

    final synchronized void closeSocket() throws IOException {
        if (this.socket != null) {
            while (!this.socket.isClosed()) {
                this.socket.close();
            }
        }
    }

    @Override
    public abstract void run();
}
