package networking.serversideMessageHandlers;

import networking.MessageHandler;
import networking.connections.Server;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
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
