package testClasses.communication_client_server;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import networking.connections.Client;
import networking.messages.Message;
import networking.messages.Response;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ExampleResponseHandler extends networking.messageHandlers.clientside.ResponseHandler {

    public ExampleResponseHandler(Client client) {
        super(client);
    }

    @Override
    public Message handle(Message message) {
        try {
            // typecast the message into the handler-specific type:
            Response response = (Response) message;
            {
                // debugging part:
                JFrame frame = new JFrame("CLIENT");
                JPanel panel = new JPanel();
                JLabel label = new JLabel();
                label.setText("CLIENT received a message:/n" + message.toString());
                panel.add(label);
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
