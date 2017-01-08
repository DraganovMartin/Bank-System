package networking.messages.request;

import networking.messages.Message;
import networking.messages.Request;

import java.io.Serializable;

/**
 * A {@link Request} used by the client to request being logged out of the
 * system.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public final class LogoutRequest extends Request implements Serializable {

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

    @Override
    public String toString() {
        return (LogoutRequest.TYPE + ":\n"
                + "-----------------\n"
                + "username: " + this.getUsername() + "\n"
                + "-----------------\n");
    }
}
