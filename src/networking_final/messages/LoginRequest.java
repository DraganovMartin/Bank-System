package networking_final.messages;

/**
 * A {@link Message} used by the client to request access to the system by using
 * an already registered system profile. The user provides the pair of username
 * and password of the existing profile.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class LoginRequest extends Message {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "LOGINREQUEST";

    /**
     * The username provided by the user.
     */
    private final String loginUsername;

    /**
     * The password provided by the user.
     */
    private final String loginPassword;

    /**
     * Constructor.
     *
     * @param loginUsername the value for {@link #loginUsername}.
     *
     * @param loginPassword the value for {@link #loginPassword}.
     */
    public LoginRequest(String loginUsername, String loginPassword) {
        super(LoginRequest.TYPE);
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    /**
     * Returns the value of {@link #loginUsername}.
     *
     * @return the value of {@link #loginUsername}.
     */
    public final String getLoginUsername() {
        return this.loginUsername;
    }

    /**
     * Returns the value of {@link #loginPassword}.
     *
     * @return the value of {@link #loginPassword}.
     */
    public final String getLoginPassword() {
        return this.loginPassword;
    }
}
