package networking;

import networking.messages.Message;
import java.util.TreeMap;

/**
 * A message handler that executes a predefined handler according to the type of
 * the message argument. Either one or no handler is allowed for a specific
 * message type (can be a handler that includes multiple nested handlers).
 * Synchronization is not provided by the class and has to be done by specific
 * handlers that require it.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class MappedMessageHandler implements MessageHandler {

    /**
     * A mapping (collection of key-value pairs) for specific message types to
     * their respective message handlers.
     */
    private final TreeMap<String, MessageHandler> handlerMap;

    /**
     * No-argument constructor.
     */
    public MappedMessageHandler() {
        this.handlerMap = new TreeMap<>();
    }

    /**
     * Sets a {@link MessageHandler} for the specified type of {@link Message}.
     * If a handler was previously specified, it is overwritten.
     *
     * @param type the type of {@link Message}.
     *
     * @param messageHandler the {@link MessageHandler} to set for that type.
     */
    public final void set(String type, MessageHandler messageHandler) {
        if ((type != null) && (messageHandler != null)) {
            synchronized (this.handlerMap) {
                this.handlerMap.put(type, messageHandler);
            }
        }
    }

    /**
     * Returns the {@link MessageHandler} specified for the provided type of
     * {@link Message}.
     *
     * @param type the type of message.
     *
     * @return {@link MessageHandler} specified for the provided type of
     * {@link Message}, or NULL if not specified.
     */
    protected final MessageHandler get(String type) {
        if (type != null) {
            synchronized (this.handlerMap) {
                return this.handlerMap.get(type);
            }
        } else {
            return null;
        }
    }

    /**
     * Executes the {@link MessageHandler} specified for the type of the
     * {@link Message}, if any, and returns the result. Synchronization is not
     * provided by the class and has to be done by specific handlers that
     * require it.
     *
     * @param message the {@link Message} to process.
     *
     * @return the result of processing if a valid handler was set, otherwise
     * NULL.
     */
    @Override
    public Message handle(Message message) {
        if (message != null) {
            MessageHandler handler = this.get(message.getType());
            if (handler != null) {
                return handler.handle(message);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
