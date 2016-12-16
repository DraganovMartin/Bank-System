package networking;

import networking.messages.Message;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Connection extends Thread {

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

    public Connection(Socket socket, MessageHandler messageHandler) {
        this.socket = socket;
        this.inputStream = null;
        this.outputStream = null;
        this.messageHandler = messageHandler;
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
                this.inputStream = new ObjectInputStream(this.socket.getInputStream());
                this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
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
