package networking_v2.messages;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class LoginRequest extends Message implements Serializable {

    public static final String TYPE = "LOGINREQUEST";

    public final String loginUsername;
    public final String loginPassword;

    public final String getLoginUsername() {
        return this.loginUsername;
    }

    public final String getLoginPassword() {
        return this.loginPassword;
    }

    public LoginRequest(String loginUsername, String loginPassword) {
        super(LoginRequest.TYPE);
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }
}
