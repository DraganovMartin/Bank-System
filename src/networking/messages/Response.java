package networking.messages;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Response extends Message {

    private final String description;

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
