package networking.messageHandlers.serverside;

import networking.connections.Server;
import networking.messages.Message;

/**
 *
 * @author Nikolay
 */
public class RegisterRequestHandler extends ServersideMessageHandler {

    public RegisterRequestHandler(Server server) {
        super(server);
    }

    @Override
    public Message handle(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
