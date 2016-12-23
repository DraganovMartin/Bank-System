package networking_v2_DEPRECATED.messages;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class RegisterResponse extends Message implements Serializable {

    public static final String TYPE = "REGISTERRESPONSE";

    private final String loginUsername;
    private final String loginPassword;
    private final String firstName;
    private final String lastName;
    private final boolean isSuccessful;

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

    public final boolean isSuccessful() {
        return this.isSuccessful;
    }

    public RegisterResponse(String loginUsername, String loginPassword, String firstName, String lastName, boolean isSuccessful) {
        super(LoginRequest.TYPE);
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isSuccessful = isSuccessful;
    }
}
