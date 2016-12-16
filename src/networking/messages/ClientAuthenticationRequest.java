package networking.messages;

/**
 * A class that represents an authentication request issued by a user of the
 * system. The user specifies a pair of:
 * <p>
 * - username
 * <p>
 * - password.
 * <p>
 * The intended response class is {@link ClientAuthenticationResponse}.
 *
 * @see Message
 * @see ClientAuthenticationResponse
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class ClientAuthenticationRequest extends Message {

    /**
     * The type of message used for all instances of this class.
     *
     * @see Message
     */
    public final static String CLIENTAUTHENTICATIONREQUEST = "ClientAuthenticationRequest";

    /**
     * The username specified by the user.
     */
    private final String username;

    /**
     * The password specified by the user.
     */
    private final String password;

    /**
     * Returns the username specified by the user.
     *
     * @return the username specified by the user.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * Returns the password specified by the user.
     *
     * @return the password specified by the user.
     */
    public final String getPassword() {
        return this.password;
    }

    /**
     * Constructor.
     *
     * @param username the username specified by the user.
     *
     * @param password the password specified by the user.
     */
    public ClientAuthenticationRequest(String username, String password) {
        super(ClientAuthenticationRequest.CLIENTAUTHENTICATIONREQUEST);
        this.username = username;
        this.password = password;
    }
}
