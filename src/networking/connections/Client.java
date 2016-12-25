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
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Client {

    final SocketFactory socketFactory;
    final MessageHandler messageHandler;
    Clientside clientside;

    public Client(SocketFactory socketFactory, MessageHandler messageHandler) {
        this.socketFactory = socketFactory;
        this.messageHandler = messageHandler;
        this.clientside = null;
    }

    public synchronized void connect(String hostname, int port) throws UnknownHostException, IOException {
        if (this.clientside == null) {
            InetAddress inetAddress = InetAddress.getByName(hostname);
            Socket socket = this.socketFactory.createSocket(inetAddress, port);
            this.clientside = new Clientside(this, socket, messageHandler);
            this.clientside.start();
        }
    }

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

    public synchronized void send(Message message) throws IOException {
        if (this.clientside != null) {
            this.clientside.send(message);
        }
    }
}
