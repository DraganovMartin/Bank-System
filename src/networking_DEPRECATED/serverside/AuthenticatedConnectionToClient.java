package networking_DEPRECATED.serverside;

import java.net.Socket;
import networking_DEPRECATED.Connection;
import networking_DEPRECATED.MessageHandler;

/**
 * Uses an additional {@link MessageAuthenticator} handler to keep track of
 * whether the remote client has been authenticated that sets the
 * {@link Message#clientID} field to the assigned client ID for each incoming
 * message before redirecting it for further processing.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class AuthenticatedConnectionToClient extends Connection {

    /**
     * The {@link ConnectionManager} that the server uses. The thread notifies
     * the manager in case of thread termination, or when it finished its work.
     * Implemented in the {@link #cleanUp()} method.
     */
    private final ConnectionManager connectionManager;

    /**
     * the client ID as specified (obtained by the authentication procedure).
     */
    private final String clientID;

    /**
     * Constructor.
     *
     * @param socket the socket that is used by the connection.
     *
     * @param clientID the client ID as specified (obtained by the
     * authentication procedure).
     *
     * @param messageHandler the handler to redirect the message to after
     * setting the client ID.
     *
     * @param connectionManager the {@link ConnectionManager} that the server
     * uses. The thread notifies the manager in case of thread termination, or
     * when it finished its work. Implemented in the {@link #cleanUp()} method.
     *
     * @see Connection
     */
    public AuthenticatedConnectionToClient(Socket socket, String clientID, MessageHandler messageHandler, ConnectionManager connectionManager) {
        super(socket, new MessageAuthenticator(clientID, messageHandler), Connection.SIDE.SERVER);
        this.connectionManager = connectionManager;
        this.clientID = clientID;
    }

    /**
     * A method that is executed just before the thread {@link #run()} method
     * finishes. Overridden. The thread notifies the manager in case of thread
     * termination, or when it finished its work. Implemented in the
     * {@link #cleanUp()} method.
     */
    @Override
    public void cleanUp() {
        this.connectionManager.disconnect(this.clientID);
    }
}
