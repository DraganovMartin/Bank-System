package networking_v2.messages;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class RegisterRequest extends Message implements Serializable {

    public static final String TYPE = "REGISTERREQUEST";

    public final String loginUsername;
    public final String loginPassword;
    public final String firstName;
    public final String lastName;

    public final String getLoginUsername() {
        return this.loginUsername;
    }

    public final String getLoginPassword() {
        return this.loginPassword;
    }

    public final String getFirstName() {
        return this.firstName;
    }

    public final String getLastName() {
        return this.lastName;
    }

    public RegisterRequest(String loginUsername, String loginPassword, String firstName, String lastName) {
        super(LoginRequest.TYPE);
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
