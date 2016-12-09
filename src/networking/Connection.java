package networking;

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
    protected final MessageProcessor messageProcessor;

    public synchronized void send(Message message) throws IOException {
        if ((this.outputStream != null) && (message != null)) {
            this.outputStream.writeObject(message);
        }
    }

    public Connection(Socket socket, MessageProcessor messageProcessor) {
        this.socket = socket;
        this.inputStream = null;
        this.outputStream = null;
        this.messageProcessor = messageProcessor;
    }

    @Override
    public void run() {
        try {
            boolean keepRunning = true;
            if (this.messageProcessor == null) {
                keepRunning = false;
            } else {
                this.inputStream = new ObjectInputStream(this.socket.getInputStream());
                this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            }
            while (!(this.isInterrupted()) && keepRunning) {
                try {
                    Message inputMessage = (Message) this.inputStream.readObject();
                    Message outputMessage;
                    outputMessage = this.messageProcessor.process(inputMessage);
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
    }
}
