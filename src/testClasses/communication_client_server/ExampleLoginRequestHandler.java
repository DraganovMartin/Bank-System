package testClasses.communication_client_server;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import networking.connections.Server;
import networking.messages.request.LoginRequest;
import networking.messages.Message;
import networking.messages.Update;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class ExampleLoginRequestHandler extends networking.messageHandlers.serverside.LoginRequestHandler {

    public ExampleLoginRequestHandler(Server server) {
        super(server);
    }

    @Override
    public Message handle(Message message) {
        try {
            // typecast the message into the handler-specific type:
            LoginRequest request = (LoginRequest) message;
            {
                // debugging part:
                JFrame frame = new JFrame("SERVER");
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                String[] text = ("SERVER received a message:\n" + message.toString()).split("\n");
                for (int i = 0; i < text.length; i++) {
                    panel.add(new JLabel(text[i]));
                }
                frame.add(panel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
            {
                // process the request:
                boolean successful;
                String loginUsername = request.getLoginUsername();
                String loginPassword = request.getLoginPassword();
                switch (loginUsername) {
                    case "user1": {
                        if (loginPassword.compareTo("pass1") == 0) {
                            successful = true;
                        } else {
                            successful = false;
                        }
                    }
                    break;
                    case "user2": {
                        if (loginPassword.compareTo("pass2") == 0) {
                            successful = true;
                        } else {
                            successful = false;
                        }
                    }
                    break;
                    case "user3": {
                        if (loginPassword.compareTo("pass3") == 0) {
                            successful = true;
                        } else {
                            successful = false;
                        }
                    }
                    break;
                    default: {
                        successful = false;
                    }
                    break;
                }
                String description;
                if (successful) {
                    description = "Login successful!";
                } else {
                    description = "Wrong username or password!";
                }
                return new Update(description, request, successful, null);
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
