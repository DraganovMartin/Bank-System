package testClasses.communication_client_server;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import networking.connections.Client;
import networking.messages.Message;
import networking.messages.Update;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class ExampleUpdateHandler extends networking.messageHandlers.clientside.ResponseHandler {

    public ExampleUpdateHandler(Client client) {
        super(client);
    }

    @Override
    public Message handle(Message message) {
        try {
            // typecast the message into the handler-specific type:
            Update response = (Update) message;
            {
                // debugging part:
                JFrame frame = new JFrame("CLIENT");
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                String[] text = ("CLIENT received a message:\n" + message.toString()).split("\n");
                for (int i = 0; i < text.length; i++) {
                    panel.add(new JLabel(text[i]));
                }
                frame.add(panel);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
            {
                // process the response: not answering to a response:
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
