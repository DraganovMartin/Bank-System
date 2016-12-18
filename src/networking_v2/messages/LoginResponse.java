package networking_v2.messages;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class LoginResponse extends Message implements Serializable {

    public static final String TYPE = "LOGINRESPONSE";

    public final boolean isSuccessful;
    public final String verifiedUsername;

    public final boolean isSuccessful() {
        return this.isSuccessful;
    }

    public final String getVerifiedUsername() {
        return this.verifiedUsername;
    }

    public LoginResponse(boolean isSuccessful, String verifiedUsername) {
        super(LoginResponse.TYPE);
        this.isSuccessful = isSuccessful;
        this.verifiedUsername = verifiedUsername;
    }
}
