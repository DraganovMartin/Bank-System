package networking.messageHandlers.serverside;

import networking.connections.Server;
import networking.messages.Message;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class CurrencyRatesRequestHandler extends ServersideMessageHandler {

    public CurrencyRatesRequestHandler(Server server) {
        super(server);
    }

    @Override
    public Message handle(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
