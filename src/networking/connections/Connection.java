package networking.connections;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import networking.messages.Message;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Connection extends Thread {

    protected Socket socket;
    protected ObjectInputStream istream;
    protected ObjectOutputStream ostream;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public synchronized void send(Message message) {

    }

    @Override
    public abstract void run();
}
