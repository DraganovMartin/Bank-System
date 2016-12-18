package networking_v2;

import javax.net.SocketFactory;

/**
 * Client class. Executed as a thread. Initiates connection requests and creates
 * and manages connections client-side.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Client extends Thread {

    public SocketFactory socketFactory;
    public String hostName;
    public int port;

    public Client(SocketFactory socketFactory, String hostName, int port) {
        this.socketFactory = socketFactory;
    }

    @Override
    public void run() {

    }
}
