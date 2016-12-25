package testClasses.communication_client_server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import networking.connections.Server;
import networking.messages.LoginRequest;
import networking.messages.Message;
import networking.messages.Response;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
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
                JLabel label = new JLabel();
                label.setText("SERVER received a message:/n" + message.toString());
                panel.add(label);
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
                return new Response(request, successful, null, description);
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
