package networking.messageHandlers.clientside;

import networking.connections.Client;
import networking.messages.Message;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ResponseHandler extends ClientsideMessageHandler {

    public ResponseHandler(Client client) {
        super(client);
    }

    @Override
    public Message handle(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
