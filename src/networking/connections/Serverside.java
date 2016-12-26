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

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
class Serverside extends Connection {

    Server server;
    BigInteger logNumber;
    String username;

    Serverside(Server server, Socket socket, MessageHandler messageHandler, BigInteger logNumber) {
        super(socket, messageHandler);
        this.server = server;
        this.logNumber = logNumber;
        this.username = null;
    }

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
