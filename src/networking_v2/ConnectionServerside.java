package networking_v2;

import java.net.Socket;

/**
 * A server-side connection. Executed as a thread.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ConnectionServerside extends Thread {

    public Socket socket;

    public ConnectionServerside(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
