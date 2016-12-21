package networking_final.messages;

import java.io.Serializable;

/**
 * A response {@link Message}.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Response extends Message implements Serializable {

    /**
     * The {@link Request} that is being responded to.
     */
    private final Request toRequest;

    /**
     * Whether the requested operation(s) were successful.
     */
    private final boolean isSuccessful;

    /**
     * An optional description of the response.
     */
    private final String description;

    /**
     * Constructor.
     *
     * @param type the value for {@link Message#type}.
     *
     * @param toRequest the value for {@link #toRequest}.
     *
     * @param isSuccessful the value for {@link #isSuccessful}.
     *
     * @param description the value for {@link #description}.
     */
    public Response(String type, Request toRequest, boolean isSuccessful, String description) {
        super(type);
        this.toRequest = toRequest;
        this.isSuccessful = isSuccessful;
        this.description = description;
    }
}
