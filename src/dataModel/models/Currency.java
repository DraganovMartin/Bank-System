package dataModel.models;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public final class Currency implements Serializable {

    /**
     * The abbreviation of the currency.
     */
    private final String symbol;

    /**
     * Constructor.
     *
     * @param symbol the abbreviation of the currency.
     */
    public Currency(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the abbreviation of the currency.
     *
     * @return the abbreviation of the currency.
     */
    public final String getSymbol() {
        return this.symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
