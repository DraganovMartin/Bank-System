package networking.messages;

import networking.messages.Message;

/**
 * A base class that represents a response message.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public abstract class Response extends Message {

    private final String description;

    /**
     * Constructor.
     *
     * @param type the type of response.
     *
     * @param description optional description.
     */
    public Response(String type, String description) {
        super(type);
        this.description = description;
    }

    /**
     * Returns the value of {@link #description}.
     *
     * @return the value of {@link #description}.
     */
    public final String getDescription() {
        return this.description;
    }
}
