package networking_v2;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;

/**
 * Client class. Initiates connection requests and creates and manages
 * connections client-side.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Client {

    public SocketFactory socketFactory;
    public String hostName;
    public int port;

    public Client(SocketFactory socketFactory, String hostName, int port) {
        this.socketFactory = socketFactory;
        this.hostName = hostName;
        this.port = port;
    }

    public Clientside connect() {
        try {
            Socket socket = this.socketFactory.createSocket(this.hostName, this.port);
            return new Clientside(socket);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
