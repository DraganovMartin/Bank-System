package networking_v2;

import java.net.Socket;

/**
 * A server-side connection. Executed as a thread.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Serverside extends Thread implements MessageHandler {

    public final Socket socket;
    public final Server server;

    public Serverside(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

    }

    @Override
    public Message handle(Message message) {
        return this.server.handle(message);
    }
}
