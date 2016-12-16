package networking.messages;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class ClientAuthenticationResponse extends Message {

    public static final String CLIENTAUTHENTICATIONRESPONSE = "ClientAuthenticationResponse";
    private final boolean isSuccessful;
    private final String authenticatedClientPrimaryKey;

    public final boolean isSuccessful() {
        return this.isSuccessful;
    }

    public final String getAuthenticatedClientID() {
        return this.authenticatedClientPrimaryKey;
    }

    public ClientAuthenticationResponse(boolean isSuccessful, String authenticatedClientPrimaryKey) {
        super(ClientAuthenticationResponse.CLIENTAUTHENTICATIONRESPONSE);
        this.isSuccessful = isSuccessful;
        this.authenticatedClientPrimaryKey = authenticatedClientPrimaryKey;
    }
}
