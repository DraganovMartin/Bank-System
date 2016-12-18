package networking_v2;

import java.net.ServerSocket;
import javax.net.ServerSocketFactory;

/**
 * Server class. Executed as a thread. Accepts incoming connection requests and
 * creates and manages connections server-side.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Server extends Thread {

    public ServerSocketFactory serverSocketFactory;
    public int port;
    public ServerSocket serverSocket;

    public Server(ServerSocketFactory serverSocketFactory, int port) {
        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
        this.serverSocket = null;
    }

    @Override
    public void run() {
        
    }
}
