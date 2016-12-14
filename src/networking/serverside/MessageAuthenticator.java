package networking.serverside;

import networking.Message;
import networking.MessageHandler;

/**
 * A {@link MessageAuthenticator} object acts as a {@link MessageHandler}. It
 * changes the {@link Message#clientPrimaryKeyValue} field for each incoming
 * message to a predefined value before submitting the message for further
 * processing to another predefined handler.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class MessageAuthenticator implements MessageHandler {

    /**
     * The predefined client ID to use for signing messages.
     */
    private final String clientPrimaryKeyValue;

    /**
     * The predefined handler to submit authenticated messages to.
     */
    private final MessageHandler baseMessageHandler;

    /**
     * Constructor.
     *
     * @param clientPrimaryKeyValue the predefined client ID to use for signing
     * messages.
     *
     * @param baseMessageHandler the predefined handler to submit authenticated
     * messages to.
     */
    public MessageAuthenticator(String clientPrimaryKeyValue, MessageHandler baseMessageHandler) {
        this.clientPrimaryKeyValue = clientPrimaryKeyValue;
        this.baseMessageHandler = baseMessageHandler;
    }

    @Override
    public Message handle(Message message) {
        message.setClientPrimaryKeyValue(this.clientPrimaryKeyValue);
        Message response = this.baseMessageHandler.handle(message);
        return response;
    }
}
