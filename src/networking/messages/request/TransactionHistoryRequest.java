package networking.messages.request;

import networking.messages.Message;
import networking.messages.Request;

import java.io.Serializable;

/**
 * A {@link Request} used by the client to request information about the
 * transaction history of the bank accounts owned by the user.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public final class TransactionHistoryRequest extends Request implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "TRANSACTIONHISTORYREQUEST";

    /**
     * Constructor.
     */
    public TransactionHistoryRequest() {
        super(TransactionHistoryRequest.TYPE);
    }

    @Override
    public String toString() {
        return (TransactionHistoryRequest.TYPE + ":\n"
                + "-----------------\n"
                + "username: " + this.getUsername() + "\n"
                + "-----------------\n");
    }
}
