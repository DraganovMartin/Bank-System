package networking_final.messages;

import java.io.Serializable;

/**
 * A {@link Message} used by the client to request information about the
 * transaction history of the bank accounts owned by the user.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class TransactionHistoryRequest extends Message implements Serializable {

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
}
