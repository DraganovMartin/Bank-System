package networking.messages;

import java.io.Serializable;

/**
 * A {@link Request} used by the client to request information about the balance
 * of the bank accounts owned by the user.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class BalanceRequest extends Request implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "BALANCEREQUEST";

    /**
     * Constructor.
     */
    public BalanceRequest() {
        super(BalanceRequest.TYPE);
    }

    @Override
    public String toString() {
        return (BalanceRequest.TYPE + ":\n"
                + "-----------------\n"
                + "username: " + this.getUsername() + "\n"
                + "-----------------\n");
    }
}
