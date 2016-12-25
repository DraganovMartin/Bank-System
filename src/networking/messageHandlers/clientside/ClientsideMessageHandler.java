package networking.messageHandlers.clientside;

import networking.connections.Client;
import networking.messageHandlers.MessageHandler;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class ClientsideMessageHandler implements MessageHandler {

    /**
     * Reference to the client that uses this message handler.
     */
    Client client;

    public ClientsideMessageHandler(Client client) {
        this.client = client;
    }
}
