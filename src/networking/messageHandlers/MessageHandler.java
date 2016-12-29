package networking.messageHandlers;

import networking.messages.Message;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public interface MessageHandler {

    /**
     * Processes an incoming {@link Message} and returns a {@link Message} if a
     * response {@link Message} is required.
     *
     * @param message the {@link Message} that requires processing.
     *
     * @return a response {@link Message} or NULL if no response is required.
     */
    public Message handle(Message message);
}
