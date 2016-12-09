package networking;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public interface MessageProcessor {

    /**
     * Processes an incoming {@link Message} and returns a {@link Message} if a
     * response {@link Message} is required.
     *
     * @param message the {@link Message} that requires processing.
     *
     * @return a response {@link Message} or NULL if no response is required.
     */
    public Message process(Message message);
}
