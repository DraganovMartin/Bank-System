package networking.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messageHandlers.MessageHandler;
import networking.messages.ChangePasswordRequest;
import networking.messages.Update;
import networking.messages.LoginRequest;
import networking.messages.LogoutRequest;
import networking.messages.RegisterRequest;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
class Clientside extends Connection {

    final Client client;

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
                    Update response = (Update) this.istream.readObject();
                    String requestType = response.getRequest().getType();
                    switch (requestType) {
                        case LogoutRequest.TYPE: {
                            if (response.isSuccessful()) {
                                // terminate on receiving a successful logout response;
                                keepRunning = false;
                            }
                        }
                        break;
                        case LoginRequest.TYPE: {
                            if (!response.isSuccessful()) {
                                // terminate on receiving a failed login response;
                                keepRunning = false;
                            }
                        }
                        break;
                        case RegisterRequest.TYPE: {
                            if (!response.isSuccessful()) {
                                // terminate on receiving a failed register response;
                                keepRunning = false;
                            }
                        }
                        break;
                        case ChangePasswordRequest.TYPE: {
                            if (!response.isSuccessful()) {
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
