package networking.messages;

/**
 * A class that represents an authentication response returned by the server in
 * the system. The server returns:
 * <p>
 * - whether the client authentication has been successful
 * <p>
 * - (if successful) the authenticated client ID of the owner of the system
 * profile that corresponds to the provided username-password pair, else NULL.
 * <p>
 * Created in response to a {@link ClientAuthenticationRequest}.
 *
 * @see Message
 * @see ClientAuthenticationRequest
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class ClientAuthenticationResponse extends Message {

    /**
     * The type of message used for all instances of this class.
     *
     * @see Message
     */
    public static final String CLIENTAUTHENTICATIONRESPONSE = "ClientAuthenticationResponse";

    /**
     * Whether the client authentication has been successful.
     */
    private final boolean isSuccessful;

    /**
     * The authenticated client ID of the owner of the system profile that
     * corresponds to the provided username-password pair. NULL if not
     * successfully identified.
     */
    private final String authenticatedClientID;

    /**
     * Returns whether the client authentication has been successful.
     *
     * @return whether the client authentication has been successful.
     */
    public final boolean isSuccessful() {
        return this.isSuccessful;
    }

    /**
     * Returns the authenticated client ID of the owner of the system profile
     * that corresponds to the provided username-password pair. NULL if not
     * successfully identified.
     *
     * @return the authenticated client ID of the owner of the system profile
     * that corresponds to the provided username-password pair. NULL if not
     * successfully identified.
     */
    public final String getAuthenticatedClientID() {
        return this.authenticatedClientID;
    }

    /**
     * Constructor.
     *
     * @param isSuccessful whether the client authentication has been
     * successful.
     *
     * @param authenticatedClientID the authenticated client ID of the owner of
     * the system profile that corresponds to the provided username-password
     * pair. NULL if not successfully identified.
     */
    public ClientAuthenticationResponse(boolean isSuccessful, String authenticatedClientID) {
        super(ClientAuthenticationResponse.CLIENTAUTHENTICATIONRESPONSE);
        this.isSuccessful = isSuccessful;
        this.authenticatedClientID = authenticatedClientID;
    }
}
