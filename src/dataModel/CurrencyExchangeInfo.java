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
    synchronized public final boolean setCurrencyValue(Currency currency, BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) > 0) {
            this.currencyValues.put(currency, value);
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
    synchronized public final void setCurrencyValuesMultiple(Currency[] currencyArr, BigDecimal[] valueArr) {
        if (currencyArr.length == valueArr.length) {
            int l = currencyArr.length;
            for (int i = 0; i < l; i++) {
                if ((currencyArr[i] != null) && (valueArr[i] != null)) {
                    if (valueArr[i].compareTo(BigDecimal.ZERO) > 0) {
                        this.currencyValues.put(currencyArr[i], valueArr[i]);
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
    synchronized public final BigDecimal getExchangeRate(Currency currencyFrom, Currency currencyTo) {
        BigDecimal valueFrom;
        BigDecimal valueTo;
        valueFrom = this.currencyValues.get(currencyFrom);
        valueTo = this.currencyValues.get(currencyTo);
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
     * and scale.
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
    synchronized public final TreeMap<Currency, BigDecimal> getCurrencyExchangedValues(Currency baseCurrency, BigDecimal baseAmount) {
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
}
