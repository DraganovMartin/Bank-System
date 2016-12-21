package dataModel.models;

import dataModel.PrimaryKey;

import java.io.Serializable;

/**
 * Represents a type of currency that has a {@link PrimaryKey} as unique
 * identifier. Final class - no extension intended.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class Currency extends PrimaryKey implements Serializable {

    /**
     * The abbreviation of the currency.
     */
    private final String symbol;

    /**
     * Constructor.
     *
     * @param primaryKey the primary key value assigned to this currency in the
     * database.
     *
     * @param symbol the abbreviation of the currency.
     */
    public Currency(String primaryKey, String symbol) {
        super(primaryKey);
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
