package networking_v2_DEPRECATED;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import networking_v2_DEPRECATED.messages.Message;
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

    /**
     * Debug mode.
     */
    public static boolean DEBUG = true;

    public final Socket socket;
    public final Server server;
    public ObjectInputStream istream;
    public ObjectOutputStream ostream;
    private boolean canSend_synch_lock;
    private final BigInteger logNumber; // used by the server to identify connections
    private String verifiedUsername;

    public Serverside(Socket socket, Server server, BigInteger logNumber) {
        this.socket = socket;
        this.server = server;
        this.istream = null;
        this.ostream = null;
        this.canSend_synch_lock = false;
        this.verifiedUsername = null;
        this.logNumber = logNumber;
    }

    @Override
    public void run() {
        if (Serverside.DEBUG) {
            System.out.println("SERVERSIDE run() started!");
        }
        boolean keepRunning = true;
        if (this.socket != null) {
            try {
                this.ostream = new ObjectOutputStream(this.socket.getOutputStream());
                this.istream = new ObjectInputStream(this.socket.getInputStream());
                this.sendEnable();
                if (Serverside.DEBUG) {
                    System.out.println("SERVERSIDE run() streams opened successfully!");
                }
                while (!this.isInterrupted() && keepRunning) {
                    try {
                        Message incoming = (Message) this.istream.readObject();
                        if (Serverside.DEBUG) {
                            System.out.println("SERVERSIDE received a message!");
                        }
                        Message response = this.handle(incoming);
                        if (response != null) {
                            // initial verification;
                            // either the connection is verified or not;
                            // if not verified, it is closed by the server:
                            if (this.getVerifiedUsername() == null) {
                                this.server.verifyConnection(this, response);
                            }
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
                if (Serverside.DEBUG) {
                    System.out.println("SERVERSIDE closing socket and streams...");
                }
                while (!this.socket.isClosed()) {
                    try {
                        this.socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (Serverside.DEBUG) {
                    System.out.println("SERVERSIDE streams closed successfully!");
                }
                this.server.stopConnection(this);
            }
        }
        if (Serverside.DEBUG) {
            System.out.println("SERVERSIDE run() finished!");
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

    public synchronized void stopThread() {
        this.sendDisable();
        if (this.isAlive() && !this.isInterrupted()) {
            this.interrupt();
        }
        if (this.socket != null) {
            while (!this.socket.isClosed()) {
                try {
                    this.socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public BigInteger getLogNumber() {
        return this.logNumber;
    }

    public String getVerifiedUsername() {
        return this.verifiedUsername;
    }

    public void setVerifiedUsername(String verifiedUsername) {
        this.verifiedUsername = verifiedUsername;
    }
}
