package networking.messages;

import java.io.Serializable;

/**
 * A {@link Request} used by the client to request information about the
 * up-to-date currency rates.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class CurrencyRatesRequest extends Request implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "CURRENCYRATESREQUEST";

    /**
     * Constructor.
     */
    public CurrencyRatesRequest() {
        super(CurrencyRatesRequest.TYPE);
    }

    @Override
    public String toString() {
        return (CurrencyRatesRequest.TYPE + ":/n"
                + "-----------------/n"
                + "username: " + this.getUsername() + "/n"
                + "-----------------/n");
    }
}
