package networking_DEPRECATED;

import networking_DEPRECATED.messages.Message;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Connection extends Thread {

    /**
     * Defines the side of the connection - server-side or client-side. The side
     * determines the order of opening input/output streams from the socket:
     * <p>
     * - the server opens in the order: OUTPUT, INPUT
     * <p>
     * - the client opens in the order: INPUT, OUTPUT
     */
    public static enum SIDE {
        SERVER,
        CLIENT
    }

    /**
     * Defines the side of the connection - server-side or client-side. The side
     * determines the order of opening input/output streams from the socket:
     * <p>
     * - the server opens in the order: OUTPUT, INPUT
     * <p>
     * - the client opens in the order: INPUT, OUTPUT
     */
    private final SIDE side;

    protected Socket socket;
    protected ObjectInputStream inputStream;
    protected ObjectOutputStream outputStream;
    protected final MessageHandler messageHandler;

    public synchronized void send(Message message) throws IOException {
        if ((this.outputStream != null) && (message != null)) {
            this.outputStream.writeObject(message);
            this.outputStream.flush();
        }
    }

    public Connection(Socket socket, MessageHandler messageHandler, Connection.SIDE side) {
        this.socket = socket;
        this.inputStream = null;
        this.outputStream = null;
        this.messageHandler = messageHandler;
        this.side = side;
    }

    /**
     * A method that is executed just before the thread {@link #run()} method
     * finishes. Intended for overriding in derived classes.
     */
    protected void cleanUp() {
    }

    @Override
    public void run() {
        try {
            boolean keepRunning = true;
            if (this.messageHandler == null) {
                keepRunning = false;
            } else {
                switch (this.side) {
                    case CLIENT: {
                        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
                        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
                    }
                    break;
                    case SERVER: {
                        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
                        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
                    }
                    break;
                }
            }
            while (!(this.isInterrupted()) && keepRunning) {
                try {
                    Message inputMessage = (Message) this.inputStream.readObject();
                    Message outputMessage;
                    outputMessage = this.messageHandler.handle(inputMessage);
                    this.send(outputMessage);
                } catch (IOException | ClassNotFoundException ex) {
                    keepRunning = false;
                }
            }
        } catch (IOException ex) {
        } finally {
            try {
                this.socket.close();
            } catch (IOException ex) {
            }
            this.socket = null;
            this.inputStream = null;
            this.outputStream = null;
        }
        cleanUp();
    }
}
