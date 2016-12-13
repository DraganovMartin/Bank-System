package networking;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class ClientAuthenticationRequest extends Message {

    public final static String CLIENTAUTHENTICATIONREQUEST = "ClientAuthenticationRequest";
    private final String username;
    private final String password;

    public final String getUsername() {
        return this.username;
    }

    public final String getPassword() {
        return this.password;
    }

    public ClientAuthenticationRequest(String username, String password) {
        super(ClientAuthenticationRequest.CLIENTAUTHENTICATIONREQUEST);
        this.username = username;
        this.password = password;
    }
}
