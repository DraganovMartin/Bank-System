package networking.messageHandlers;

import networking.messages.Message;

/**
 * A message handler that executes a predefined handler according to the type of
 * the message argument. Either one or no handler is allowed for a specific
 * message type (can be a handler that includes multiple nested handlers).
 * Derived from {@link MappedMessageHandler}.
 * <p>
 * The class provides synchronization for all of its methods.
 *
 * @see MappedMessageHandler
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class SynchronizedMappedMessageHandler extends MappedMessageHandler implements MessageHandler {

    /**
     * No-argument constructor.
     */
    public SynchronizedMappedMessageHandler() {
        super();
    }

    /**
     * Sets a {@link MessageHandler} for the specified type of {@link Message}.
     * If a handler was previously specified, it is overwritten.
     * <p>
     * This method is synchronized.
     *
     * @param type the type of {@link Message}.
     *
     * @param messageHandler the {@link MessageHandler} to set for that type.
     */
    @Override
    public synchronized void set(String type, MessageHandler messageHandler) {
        super.set(type, messageHandler);
    }

    /**
     * Returns the {@link MessageHandler} specified for the provided type of
     * {@link Message}.
     * <p>
     * This method is synchronized.
     *
     * @param type the type of message.
     *
     * @return {@link MessageHandler} specified for the provided type of
     * {@link Message}, or NULL if not specified.
     */
    @Override
    protected MessageHandler get(String type) {
        return super.get(type);
    }

    /**
     * Executes the {@link MessageHandler} specified for the type of the
     * {@link Message}, if any, and returns the result.
     * <p>
     * This method is synchronized.
     *
     * @param message the {@link Message} to process.
     *
     * @return the result of processing if a valid handler was set, otherwise
     * NULL.
     */
    @Override
    public synchronized Message handle(Message message) {
        return super.handle(message);
    }
}
