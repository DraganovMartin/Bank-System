package networking_v2;

import networking_v2.messages.Message;

/**
 * Processes a request {@link Message} and returns a response {@link Message} as
 * the result.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public interface MessageHandler {

    public Message handle(Message message);
}
