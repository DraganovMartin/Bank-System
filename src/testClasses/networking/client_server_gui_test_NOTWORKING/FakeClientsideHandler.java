package testClasses.networking.client_server_gui_test_NOTWORKING;

import javax.swing.JOptionPane;
import networking.MessageHandler;
import networking.messages.Message;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class FakeClientsideHandler implements MessageHandler {

    private final String threadID;

    public FakeClientsideHandler(String threadID) {
        this.threadID = threadID;
    }

    @Override
    public Message handle(Message message) {
        String textContent = "";
        if (message.getType().compareTo(FakeTextMessage.FAKETEXTMESSAGE) == 0) {
            textContent = ((FakeTextMessage) message).getTextContent();
        } else {
            textContent = "";
        }
        JOptionPane.showMessageDialog(null, "Client: " + this.threadID + " received a Message of type: " + message.getType() + ", content: " + textContent);
        return null;
    }
}
