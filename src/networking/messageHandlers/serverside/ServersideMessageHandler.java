package networking.messageHandlers.serverside;

import networking.connections.Server;
import networking.messageHandlers.MessageHandler;

/**
 *
 * @author Nikolay
 */
public abstract class ServersideMessageHandler implements MessageHandler {

    /**
     * Reference to the server that uses this message handler.
     */
    Server server;

    public ServersideMessageHandler(Server server) {
        this.server = server;
    }
}
