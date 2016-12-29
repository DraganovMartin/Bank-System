package networking.messageHandlers.clientside;

import networking.connections.Client;
import networking.messages.Message;

/**
 * A abse client-side handler for server responses.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class ResponseHandler extends ClientsideMessageHandler {

    /**
     * Constructor.
     *
     * @param client reference to the client that uses this message handler.
     */
    public ResponseHandler(Client client) {
        super(client);
    }

    @Override
    public Message handle(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
