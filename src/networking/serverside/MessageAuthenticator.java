package networking.serverside;

import networking.messages.Message;
import networking.MessageHandler;

/**
 * A {@link MessageAuthenticator} object acts as a {@link MessageHandler}. It
 * changes the {@link Message#clientID} field for each incoming message to a
 * predefined value before submitting the message for further processing to
 * another predefined handler. Once a successful client authentication is
 * performed and the {@link MessageAuthenticator} has been created, all
 * {@link Message} objects being handled by this specific
 * {@link MessageAuthenticator} will have their {@link Message#clientID} field
 * value set to the specified client ID.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 *
 * @see Message
 */
public class MessageAuthenticator implements MessageHandler {

    /**
     * The predefined client ID to use for signing messages. Should be assigned
     * to the {@link MessageAuthenticator} after the client has been
     * successfully authenticated within the system. Once a successful client
     * authentication is performed and the {@link MessageAuthenticator} has been
     * created, all {@link Message} objects being handled by this specific
     * {@link MessageAuthenticator} will have their {@link Message#clientID}
     * field value set to the specified client ID.
     */
    private final String clientID;

    /**
     * The predefined handler to submit authenticated messages to.
     */
    private final MessageHandler baseMessageHandler;

    /**
     * Constructor.
     *
     * @param clientID the predefined client ID to use for signing messages.
     *
     * @param baseMessageHandler the predefined handler to submit authenticated
     * messages to.
     */
    public MessageAuthenticator(String clientID, MessageHandler baseMessageHandler) {
        this.clientID = clientID;
        this.baseMessageHandler = baseMessageHandler;
    }

    @Override
    public Message handle(Message message) {
        message.setClientID(this.clientID);
        Message response = this.baseMessageHandler.handle(message);
        return response;
    }
}
