package networking.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messages.Message;
import networking.messageHandlers.MessageHandler;
import networking.messages.request.ChangePasswordRequest;
import networking.messages.DisconnectNotice;
import networking.messages.Update;
import networking.messages.request.LoginRequest;
import networking.messages.request.LogoutRequest;
import networking.messages.request.RegisterRequest;
import networking.messages.Response;

/**
 * A client-side version of the {@link Connection}. Sends messages to the server
 * and handles incoming server messages. Implemented logic: the client side
 * execution terminates if if receives:
 * <p>
 * - a successful response to a {@link LogoutRequest}.
 * <p>
 * - a unsuccessful response to a {@link LoginRequest}.
 * <p>
 * - a unsuccessful response to a {@link RegisterRequest}.
 * <p>
 * - a unsuccessful response to a {@link ChangePasswordRequest}.
 * <p>
 * - a {@link DisconnectNotice} (send exclusively by the server).
 *
 * @see Message
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
class Clientside extends Connection {

    /**
     * The parent (creator) {@link Client} of this {@link Clientside} object.
     */
    final Client client;

    /**
     * Constructor.
     *
     * @param client the parent (creator) {@link Client} of this
     * {@link Clientside} object.
     *
     * @param socket the client side socket.
     *
     * @param messageHandler a {@link MessageHandler} to process the incoming
     * messages.
     */
    public Clientside(Client client, Socket socket, MessageHandler messageHandler) {
        super(socket, messageHandler);
        this.client = client;
    }

    @Override
    public void run() {
        try {
            this.istream = new ObjectInputStream(this.socket.getInputStream());
            this.ostream = new ObjectOutputStream(this.socket.getOutputStream());
            boolean keepRunning = true;
            while (keepRunning) {
                try {
                    Response response = (Response) this.istream.readObject();
                    if (response.getType().equals(DisconnectNotice.TYPE)) {
                        // terminate on receiving a disconnect notice;
                        keepRunning = false;
                    } else {
                        Update update = (Update) response;
                        String requestType = update.getRequest().getType();
                        switch (requestType) {
                            case LogoutRequest.TYPE: {
                                if (update.isSuccessful()) {
                                    // terminate on receiving a successful logout response;
                                    keepRunning = false;
                                }
                            }
                            break;
                            case LoginRequest.TYPE: {
                                if (!update.isSuccessful()) {
                                    // terminate on receiving a failed login response;
                                    keepRunning = false;
                                }
                            }
                            break;
                            case RegisterRequest.TYPE: {
                                if (!update.isSuccessful()) {
                                    // terminate on receiving a failed register response;
                                    keepRunning = false;
                                }
                            }
                            break;
                            case ChangePasswordRequest.TYPE: {
                                if (!update.isSuccessful()) {
                                    // terminate on receiving a failed password change response;
                                    keepRunning = false;
                                }
                            }
                            break;
                            default: {
                                // any special treatment of a general requestType of message:
                                break;
                            }
                        }
                    }
                    this.messageHandler.handle(response);
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Clientside.class.getName()).log(Level.SEVERE, null, ex);
                    keepRunning = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Clientside.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeSocket();
            this.client.clientside = null;
        }
    }
}
