package networking.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messageHandlers.MessageHandler;
import networking.messages.*;
import networking.messages.request.ChangePasswordRequest;
import networking.messages.DisconnectNotice;
import networking.messages.request.LoginRequest;
import networking.messages.request.LogoutRequest;
import networking.messages.request.RegisterRequest;
import networking.messages.response.Response;

/**
 * A server-side version of the {@link Connection}. Receives and handles
 * messages from the client and returns responses when necessary. Implemented
 * logic: the server side execution terminates if if receives:
 * <p>
 * - any non-authenticating request (i.e. not {@link LoginRequest}, not
 * {@link RegisterRequest}) from a non-authenticated connection.
 * <p>
 * - a {@link LogoutRequest}.
 * <p>
 * A {@link DisconnectNotice} is sent to the client upon closing the connection.
 *
 * @see Message
 * @see Server
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
class Serverside extends Connection {

    /**
     * The parent {@link Server}.
     */
    Server server;

    /**
     * A log number for the current connection. Each new connection is assigned
     * a unique log number (by the {@link ConnectionManager}) in ascending
     * order.
     */
    BigInteger logNumber;

    /**
     * The validated client username for the connection. NULL if the connection
     * is not verified.
     */
    String username;

    /**
     * Constructor.
     *
     * @param server the parent {@link Server}.
     *
     * @param socket the socket that the connection uses to communicate to the
     * client.
     *
     * @param messageHandler a {@link MessageHandler} to process the incoming
     * messages.
     *
     * @param logNumber the log number (assigned by the
     * {@link ConnectionManager}.
     *
     * @see ConnectionManager
     */
    Serverside(Server server, Socket socket, MessageHandler messageHandler, BigInteger logNumber) {
        super(socket, messageHandler);
        this.server = server;
        this.logNumber = logNumber;
        this.username = null;
    }

    /**
     * Verifies a message - sets the value of its {@link Message#username}
     * field.
     *
     * @param message the message to verify.
     *
     * @return a verified message (with {@link Message#username} set to either
     * NULL if unverified, or to the value of the verified username).
     *
     * @see Message
     */
    synchronized Message verify(Message message) {
        // set the verified username for the connection:
        message.setUsername(this.username);
        if (this.username == null) {
            // only allow login and registration requests in unverified connections:
            String type = message.getType();
            if (type.equals(LoginRequest.TYPE) || type.equals(RegisterRequest.TYPE)) {
                return message;
            } else {
                return null;
            }
        } else {
            // forbid login and registration requests in verified connections:
            String type = message.getType();
            if (type.equals(LoginRequest.TYPE) || type.equals(RegisterRequest.TYPE)) {
                return null;
            } else {
                return message;
            }
        }
    }

    /**
     * Closes the connection. Sends a {@link DisconnectNotice} to the opposite
     * side (the client).
     */
    @Override
    synchronized void closeSocket() {
        try {
            this.send(new DisconnectNotice(DisconnectNotice.CLOSEDBYSERVER));
        } catch (IOException ex) {
            Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.closeSocket();
    }

    @Override
    public void run() {
        try {
            this.ostream = new ObjectOutputStream(this.socket.getOutputStream());
            this.istream = new ObjectInputStream(this.socket.getInputStream());
            boolean keepRunning = true;
            while (keepRunning) {
                try {
                    Message request = (Message) this.istream.readObject();
                    // assure the message is of allowed requestType:
                    Message verified = this.verify(request);
                    if (verified == null) {
                        keepRunning = false;
                    } else {
                        String requestType = verified.getType();
                        Response response = (Response) this.messageHandler.handle(verified);
                        // determine if additional processing is required for any authentication-related request:
                        switch (requestType) {
                            case LogoutRequest.TYPE: {
                                response = new Update(null, (Request) request, true, null);
                                // terminate on receiving a logout request;
                                keepRunning = false;
                            }
                            break;
                            case LoginRequest.TYPE: {
                                if (!((Update) response).isSuccessful()) {
                                    // terminate on receiving a failed login response;
                                    keepRunning = false;
                                } else {
                                    // register newly verified connection:
                                    this.username = ((LoginRequest) (((Update) response).getRequest())).getLoginUsername();
                                    this.server.connectionManager.register(this);
                                }
                            }
                            break;
                            case RegisterRequest.TYPE: {
                                if (!((Update) response).isSuccessful()) {
                                    // terminate on receiving a failed register response;
                                    keepRunning = false;
                                } else {
                                    // register newly verified connection:
                                    this.username = ((RegisterRequest) (((Update) response).getRequest())).getRegisterUsername();
                                    this.server.connectionManager.register(this);
                                }
                            }
                            break;
                            case ChangePasswordRequest.TYPE: {
                                if (!((Update) response).isSuccessful()) {
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
                        this.send(response);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
                    keepRunning = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Serverside.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeSocket();
            this.server.connectionManager.deregister(this);
        }
    }
}
