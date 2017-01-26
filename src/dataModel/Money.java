package dataModel;

import dataModel.models.Currency;
import java.io.Serializable;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a set amount of money of a specific currency. Provides a factory
 * that only allows creating objects with legal values (i.e. not NULL). All
 * {@link Money} objects:
 * <p>
 * - work with a fixed precision (two decimal digits fractional part) that
 * allows operating with cents;
 * <p>
 * - use a fixed {@link RoundingMode} - {@link RoundingMode#HALF_UP}
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class Money implements Serializable {

    /**
     * The {@link RoundingMode} to use for all {@link Money} objects.
     */
    private final static RoundingMode ROUNDINGMODE = RoundingMode.HALF_UP;

    /**
     * The precision (decimal digits fractional part) to use for all
     * {@link Money} objects.
     */
    private final static int SCALE = 2;

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
     * @param amount the amount of the money. The amount is rounded according to
     * the value of {@link Money#SCALE} and {@link Money#ROUNDINGMODE} fields.
     */
    private Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount.divide(BigDecimal.ONE, SCALE, ROUNDINGMODE);
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
    public static final Money createMoney(Currency currency, String amount) {
        if ((currency != null) && (amount != null)) {
            BigDecimal amountValue;
            try {
                amountValue = new BigDecimal(amount);
            } catch (Exception ex) {
                amountValue = null;
            }
            if (amountValue != null) {
                return new Money(currency, amountValue);
            } else {
                return null;
            }
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

    /**
     * Returns a {@link Money} object with value equal to the sum of the values
     * of "this" and the argument. The {@link Currency} of the result is
     * determined by the currency of "this". Uses a provided
     * {@link CurrencyConverter} for the exchange rates.
     * <p>
     * Calls {@link CurrencyConverter#calcSum(dataModel.Money, dataModel.Money)}
     * internally and returns the result.
     *
     * @param money the money to add.
     *
     * @param converter the converter to use.
     *
     * @see CurrencyConverter
     *
     * @return {@link Money} object with value equal to the sum of the values of
     * "this" and the argument, NULL if either currency is not supported by the
     * converter.
     */
    public final Money add(Money money, CurrencyConverter converter) {
        return converter.calcSum(this, money);
    }

    /**
     * Returns a {@link Money} object with value equal to the difference of the
     * values of "this" and the argument. The {@link Currency} of the result is
     * determined by the currency of "this". Uses a provided
     * {@link CurrencyConverter} for the exchange rates.
     * <p>
     * Calls
     * {@link CurrencyConverter#calcDifference(dataModel.Money, dataModel.Money)}
     * internally and returns the result.
     *
     * @param money the money to subtract.
     *
     * @param converter the converter to use.
     *
     * @see CurrencyConverter
     *
     * @return {@link Money} object with value equal to the difference of the
     * values of "this" and the argument, NULL if either currency is not
     * supported by the converter.
     */
    public final Money subtract(Money money, CurrencyConverter converter) {
        return converter.calcDifference(this, money);
    }

    /**
     * Returns whether the value of "this" is greater than the value of the
     * argument, using the specified {@link CurrencyConverter} for the exchange
     * rates.
     * <p>
     * Calls {@link CurrencyConverter#compare(dataModel.Money, dataModel.Money)}
     * internally and returns the result.
     *
     * @param money
     *
     * @param converter
     *
     * @see CurrencyConverter
     *
     * @return -1 / 0 / 1 as specified by
     * {@link CurrencyConverter#compare(dataModel.Money, dataModel.Money)}:
     * <p>
     * -1 if the value of "this" is less than the value of "money"
     * <p>
     * 0 if the values are equal
     * <p>
     * 1 if the value of "this" is greater than the value of "money"
     *
     * @throws IllegalArgumentException if either currency is not supported.
     */
    public int compareTo(Money money, CurrencyConverter converter) throws IllegalArgumentException {
        return converter.compare(this, money);
    }

    @Override
    public String toString() {
        return this.amount.toPlainString() + " " + this.currency.toString();
    }
}
