package networking_v2;

import java.net.Socket;

/**
 * A client-side connection. Executed as a thread.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ConnectionClientside extends Thread {

    public Socket socket;

    public ConnectionClientside(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
