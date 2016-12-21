package networking_final.messages;

import java.io.Serializable;

/**
 * A {@link Message} used by the client to request being logged out of the
 * system.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class LogoutRequest extends Message implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "LOGOUTREQUEST";

    /**
     * Constructor.
     */
    public LogoutRequest() {
        super(LogoutRequest.TYPE);
    }
}
