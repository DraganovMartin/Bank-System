package networking_v2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import networking_v2.messages.Message;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A server-side connection. Executed as a thread.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Serverside extends Thread implements MessageHandler {

    public final Socket socket;
    public final Server server;
    public ObjectInputStream istream;
    public ObjectOutputStream ostream;
    private boolean canSend_synch_lock;

    public Serverside(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.istream = null;
        this.ostream = null;
        this.canSend_synch_lock = false;
    }

    @Override
    public void run() {
        boolean keepRunning = true;
        if (this.socket != null) {
            try {
                this.ostream = new ObjectOutputStream(this.socket.getOutputStream());
                this.istream = new ObjectInputStream(this.socket.getInputStream());
                this.sendEnable();
                while (!this.isInterrupted() && keepRunning) {
                    try {
                        Message incoming = (Message) this.istream.readObject();
                        Message response = this.handle(incoming);
                        if (response != null) {
                            try {
                                this.send(response);
                            } catch (IOException ex) {
                                Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
                                keepRunning = false;
                            }
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
                        keepRunning = false;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                this.sendDisable();
                while (!this.socket.isClosed()) {
                    try {
                        this.socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public synchronized Message handle(Message message) {
        return this.server.handle(message);
    }

    public synchronized boolean send(Message message) throws IOException {
        if (this.canSend()) {
            this.ostream.writeObject(message);
            this.ostream.flush();
            return true;
        } else {
            return false;
        }
    }

    private synchronized void sendEnable() {
        this.canSend_synch_lock = true;
    }

    private synchronized void sendDisable() {
        this.canSend_synch_lock = false;
    }

    private synchronized boolean canSend() {
        return this.canSend_synch_lock;
    }
}
