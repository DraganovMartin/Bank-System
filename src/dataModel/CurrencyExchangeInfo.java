package dataModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class that provides information necessary for supporting the exchange from
 * one type of currency to another. Objects of this class are
 * {@link Serializable} so they can be sent over a network connection on demand.
 * A "currency value" is a relative value of a currency. The exchange rate
 * between a pair of currency types is based on the relative value of each
 * currency.
 * <p>
 * The {@link CurrencyExchangeInfo} objects are created with a fixed
 * {@link RoundingMode} and scale specified at constructor time that define the
 * precision to be used when performing conversions, especially divisions, as
 * defined by
 * {@link BigDecimal#divide(java.math.BigDecimal, int, java.math.RoundingMode)}.
 * <p>
 * The class is intended to provide synchronized operations so its instances can
 * be accessed from many concurrent threads while preserving data integrity (for
 * example when modifying current exchange rates). The synchronization should be
 * performed to the data source ({@link CurrencyExchangeInfo#currencyValues}) to
 * ensure that all operations performed used a valid snapshot of the exchange
 * rates. This policy should also be enforced in derived classes.
 *
 * @see
 * {@link BigDecimal#divide(java.math.BigDecimal, int, java.math.RoundingMode)}
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class CurrencyExchangeInfo implements Serializable {

    /**
     * A {@link TreeMap} that stores information about the current relative
     * values of different currencies. {@link Currency} types are recognized by
     * using their {@link Currency#getPrimaryKeyValue()} method (used as keys
     * for the map). The current exchange rate for currency A to currency B is
     * the ratio of their relative values - the relative value of B divided by
     * the relative value of A. The class does not allow the addition of
     * non-positive relative values. The class implements {@link Serializable}
     * so a serialized version of the current exchange information can be sent
     * over the network.
     * <p>
     * Operations that access or modify this object should synchronize to it.
     */
    private final TreeMap<Currency, BigDecimal> currencyValues;

    /**
     * The {@link RoundingMode} to be used.
     *
     * @see {@link CurrencyExchangeInfo}
     */
    private final RoundingMode roundingMode;

    /**
     * The scale to be used.
     *
     * @see {@link CurrencyExchangeInfo}
     */
    private final int scale;

    /**
     * Returns the {@link RoundingMode} to be used.
     *
     * @return the {@link RoundingMode} to be used.
     *
     * @see {@link CurrencyExchangeInfo}
     */
    public final RoundingMode getRoundingMode() {
        return this.roundingMode;
    }

    /**
     * Returns the scale to be used.
     *
     * @return the scale to be used.
     *
     * @see {@link CurrencyExchangeInfo}
     */
    public final int getScale() {
        return this.scale;
    }

    /**
     * Constructor.
     *
     * @param roundingMode the {@link RoundingMode} to be used.
     *
     * @param scale the scale to be used.
     *
     * @see {@link CurrencyExchangeInfo}
     */
    public CurrencyExchangeInfo(RoundingMode roundingMode, int scale) {
        this.currencyValues = new TreeMap<>();
        this.roundingMode = roundingMode;
        this.scale = scale;
    }

    /**
     * Sets the current relative value of a currency.
     *
     * @param currency the currency which relative value is to be set.
     * {@link Currency} types are recognized by using their
     * {@link Currency#getPrimaryKeyValue()} method (used as keys for the map).
     *
     * @param value the relative value to set.
     *
     * @return true if successful, otherwise false.
     */
    public final boolean setCurrencyValue(Currency currency, BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) > 0) {
            synchronized (this.currencyValues) {
                this.currencyValues.put(currency, value);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the current relative values of multiple currencies at the same time.
     * Requires an array of currencies and an array of relative values of
     * matching sizes.
     *
     * @param currencyArr an array of the currencies which relative values are
     * to be set. {@link Currency} types are recognized by using their
     * {@link Currency#getPrimaryKeyValue()} method (used as keys for the map).
     *
     * @param valueArr an array of the relative values to set.
     */
    public final void setCurrencyValuesMultiple(Currency[] currencyArr, BigDecimal[] valueArr) {
        if (currencyArr.length == valueArr.length) {
            int l = currencyArr.length;
            synchronized (this.currencyValues) {
                for (int i = 0; i < l; i++) {
                    if ((currencyArr[i] != null) && (valueArr[i] != null)) {
                        if (valueArr[i].compareTo(BigDecimal.ZERO) > 0) {
                            this.currencyValues.put(currencyArr[i], valueArr[i]);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the current exchange rate for currency A to currency B - the
     * ratio of their relative values - the relative value of B divided by the
     * relative value of A. Uses the predefined {@link RoundingMode} and scale.
     *
     * @param currencyFrom the provided currency.
     *
     * @param currencyTo the desired currency.
     *
     * @return the exchange rate, NULL failed (i.e. either currency relative
     * value not yet assigned).
     *
     * @see {@link CurrencyExchangeInfo}
     */
    public final BigDecimal getExchangeRate(Currency currencyFrom, Currency currencyTo) {
        BigDecimal valueFrom;
        BigDecimal valueTo;
        synchronized (this.currencyValues) {
            valueFrom = this.currencyValues.get(currencyFrom);
            valueTo = this.currencyValues.get(currencyTo);
        }
        if ((valueFrom != null) && (valueTo != null)) {
            return valueTo.divide(valueFrom, this.scale, this.roundingMode);
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link TreeMap<Currency, BigDecimal>} object with information
     * drawn from the current key-value mapping. It describes the equivalence of
     * a certain amount of a specified currency type when converted to any of
     * the supported types of currency. Uses the predefined {@link RoundingMode}
     * and scale. Can be used to convert between currencies. Useful when
     * converting to and from a currency with a low relative value (for example
     * one measured in thousands of currency units).
     *
     * @param baseCurrency the type of currency to convert from. Can not be
     * NULL.
     *
     * @param baseAmount the amount of money of the base currency type. Can not
     * be NULL.
     *
     * @return a {@link TreeMap<Currency, BigDecimal>} object generated based on
     * the current state, NULL if failed (i.e. undefined currency type
     * provided).
     *
     * @see {@link CurrencyExchangeInfo}
     */
    public final TreeMap<Currency, BigDecimal> getCurrencyExchangeMap(Currency baseCurrency, BigDecimal baseAmount) {
        if ((baseCurrency != null) && (baseAmount != null)) {
            synchronized (this.currencyValues) {
                BigDecimal baseValue = this.currencyValues.get(baseCurrency);
                if (baseValue != null) {
                    TreeMap<Currency, BigDecimal> result = new TreeMap<>();
                    for (Map.Entry<Currency, BigDecimal> entry : this.currencyValues.entrySet()) {
                        result.put(entry.getKey(), entry.getValue().multiply(baseAmount).divide(baseValue, this.scale, this.roundingMode));
                    }
                    return result;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link CurrencyExchangeInfo} object with information drawn from
     * the current key-value mapping. It describes the equivalence of a certain
     * amount of a specified currency type when converted to any of the
     * supported types of currency. Uses the predefined {@link RoundingMode} and
     * scale. Can be used to convert between currencies. Useful when converting
     * to and from a currency with a low relative value (for example one
     * measured in thousands of currency units). Allows the change of the
     * specified {@link RoundingMode} and scale.
     *
     * @param baseCurrency the type of currency to convert from. Can not be
     * NULL.
     *
     * @param baseAmount the amount of money of the base currency type. Can not
     * be NULL.
     *
     * @param roundingMode the {@link RoundingMode} to use in the new object.
     *
     * @param scale the scale to use in the new object.
     *
     * @return a {@link TreeMap<Currency, BigDecimal>} object generated based on
     * the current state, NULL if failed (i.e. undefined currency type
     * provided).
     *
     * @see {@link CurrencyExchangeInfo}
     */
    public final CurrencyExchangeInfo getCurrencyExchangeInfo(Currency baseCurrency, BigDecimal baseAmount, RoundingMode roundingMode, int scale) {
        if ((baseCurrency != null) && (baseAmount != null)) {
            synchronized (this.currencyValues) {
                BigDecimal baseValue = this.currencyValues.get(baseCurrency);
                if (baseValue != null) {
                    CurrencyExchangeInfo result = new CurrencyExchangeInfo(roundingMode, scale);
                    for (Map.Entry<Currency, BigDecimal> entry : this.currencyValues.entrySet()) {
                        result.setCurrencyValue(entry.getKey(), entry.getValue().multiply(baseAmount).divide(baseValue, this.scale, this.roundingMode));
                    }
                    return result;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Returns a {@link Money} object of the requested currency with value equal
     * to the value of the provided {@link Money} object, according to the
     * current exchange rates.
     *
     * @param moneyFrom the {@link Money} provided.
     *
     * @param currencyTo the requested currency to convert to.
     *
     * @return a {@link Money} object of the requested currency with value equal
     * to the value of the provided {@link Money} object, according to the
     * current exchange rates. Returns NULL if the conversion fails (i.e.
     * requested currency not supported).
     */
    public Money getConvertedMoney(Money moneyFrom, Currency currencyTo) {
        BigDecimal amountFrom = moneyFrom.getAmount();
        BigDecimal amountTo;
        Currency currencyFrom = moneyFrom.getCurrency();
        BigDecimal valueFrom;
        BigDecimal valueTo;
        synchronized (this.currencyValues) {
            valueFrom = this.currencyValues.get(currencyFrom);
            valueTo = this.currencyValues.get(currencyTo);
        }
        if ((valueFrom != null) && (valueTo != null)) {
            amountTo = amountFrom.multiply(valueTo).divide(valueFrom, this.scale, this.roundingMode);
            return Money.createMoney(currencyTo, amountTo);
        } else {
            return null;
        }
    }
}
