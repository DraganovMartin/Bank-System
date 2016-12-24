package testClasses.networking.client_server_gui_test_NOTWORKING;

import networking_DEPRECATED.messages.Message;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class FakeTextMessage extends Message {

    public static final String FAKETEXTMESSAGE = "FakeTextMessage";

    private final String textContent;

    public String getTextContent() {
        return this.textContent;
    }

    public FakeTextMessage(String type, String textContent) {
        super(type);
        this.textContent = textContent;
    }
}
