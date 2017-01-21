package networking.connections;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import networking.messageHandlers.MappedMessageHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.DisconnectNotice;
import networking.messages.Message;
import networking.messages.Update;
import networking.messages.request.LoginRequest;

/**
 * A class to start the ServerGUI.
 * <p>
 * NB! Logic has to be added - either in runtime or compile-time.
 *
 * @author iliyan
 */
public class ServerGUI_SSL_Starter {

    public static void main(String[] args) {

        MappedMessageHandler messageHandler = new MappedMessageHandler();
        final String TITLE = "Server";

        // SERVER LOGIC - CHANGE HERE:
        {
            // change here:
            String[] usernames = new String[]{"user1", "user2", "user3"};
            String[] passwords = new String[]{"pass1", "pass2", "pass3"};

            // do not change here:
            MessageHandler messageHandler_Login = (Message message) -> {
                try {
                    if (usernames.length != passwords.length) {
                        throw new IllegalArgumentException("Different array length!");
                    }
                    LoginRequest request = (LoginRequest) message;
                    JOptionPane.showMessageDialog(null, "Server received a message: " + request.getType());
                    String loginUsername = request.getLoginUsername();
                    String loginPassword = request.getLoginPassword();
                    int i = 0;
                    int l = usernames.length;
                    while (i < l) {
                        if (usernames[i].compareTo(loginUsername) == 0) {
                            if (passwords[i].compareTo(loginPassword) == 0) {
                                JOptionPane.showMessageDialog(null, "Server answers: " + "SUCCESSFUL LOGIN");
                                return new Update("not implemented", request, true, null);
                            } else {
                                JOptionPane.showMessageDialog(null, "Server answers: " + "DISCONNECTNOTICE");
                                return new DisconnectNotice("Wrong password!");
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Server answers: " + "DISCONNECTNOTICE");
                    return new DisconnectNotice("User not found!");
                } catch (Exception ex) {
                    Logger.getLogger(ServerGUI_SSL_Starter.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            };
            messageHandler.set(LoginRequest.TYPE, messageHandler_Login);
        }
        ServerGUI_SSL serverGUI_SSL = new ServerGUI_SSL(messageHandler, TITLE);
        serverGUI_SSL.keystorePasswordText.setText("server");
        serverGUI_SSL.portField.setText("15000");
    }
}
