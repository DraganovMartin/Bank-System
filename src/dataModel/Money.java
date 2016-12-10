package dataModel;

import java.math.BigDecimal;

/**
 * Represents a set amount of money of a specific currency. Provides a factory
 * that only allows creating objects with legal values (i.e. not NULL).
 *
 * @see {@link Currency}
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Money {

    /**
     * The {@link Currency} of the money.
     */
    private final Currency currency;

    /**
     * The amount of the money.
     */
    private final BigDecimal amount;

    /**
     * Constructor.
     *
     * @param currency the {@link Currency} of the money.
     *
     * @param amount the amount of the money.
     */
    private Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * Returns an object that represents a certain amount of money of the
     * specified currency.
     *
     * @param currency the {@link Currency} of the money. Can not be NULL.
     *
     * @param amount the amount of the money. Can not be NULL.
     *
     * @return an object that represents a certain amount of money of the
     * specified currency, NULL if failed.
     */
    public static final Money createMoney(Currency currency, BigDecimal amount) {
        if ((currency != null) && (amount != null)) {
            return new Money(currency, amount);
        } else {
            return null;
        }
    }

    /**
     * Returns the {@link Currency} of the money.
     *
     * @return the {@link Currency} of the money.
     */
    public final Currency getCurrency() {
        return this.currency;
    }

    /**
     * Returns the amount of the money.
     *
     * @return the amount of the money.
     */
    public final BigDecimal getAmount() {
        return this.amount;
    }
}
