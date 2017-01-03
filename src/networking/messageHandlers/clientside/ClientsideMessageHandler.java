package networking.messageHandlers.clientside;

import networking.connections.Client;
import networking.messageHandlers.MessageHandler;

/**
 * A base {@link MessageHandler} for the client.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public abstract class ClientsideMessageHandler implements MessageHandler {

    /**
     * Reference to the client that uses this message handler.
     */
    Client client;

    /**
     * Constructor.
     *
     * @param client reference to the client that uses this message handler.
     */
    public ClientsideMessageHandler(Client client) {
        this.client = client;
    }
}
