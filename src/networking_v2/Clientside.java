package networking_v2;

import networking_v2.messages.Message;
import java.net.Socket;

/**
 * A client-side connection. Executed as a thread.
 * <p>
 * TO DO: fields and methods visibility, general improvements.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Clientside extends Thread implements MessageHandler {

    public final Client client;
    public final Socket socket;

    public Clientside(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {

    }

    @Override
    public Message handle(Message message) {
        return this.client.handle(message);
    }
}
