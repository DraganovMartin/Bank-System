package networking.serverside;

import java.net.Socket;
import networking.Connection;
import networking.MessageHandler;

/**
 * Uses an additional {@link MessageAuthenticator} handler to keep track of
 * whether the remote client has been authenticated that sets the
 * {@link Message#clientPrimaryKeyValue} field to the assigned client ID for
 * each incoming message before redirecting it for processing.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class AuthenticatedClientConnection extends Connection {

    /**
     * Constructor.
     *
     * @param socket the socket that is used by the connection.
     *
     * @param clientPrimaryKeyValue the client ID as specified (obtained by the
     * authentication procedure).
     *
     * @param messageHandler the handler to redirect the message to after
     * setting the client ID.
     *
     * @see {@link Connection}
     */
    public AuthenticatedClientConnection(Socket socket, String clientPrimaryKeyValue, MessageHandler messageHandler) {
        super(socket, new MessageAuthenticator(clientPrimaryKeyValue, messageHandler));
    }
}
