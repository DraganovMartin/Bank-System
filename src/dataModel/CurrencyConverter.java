package dataModel;

import dataModel.models.Currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

/**
 * A class that supports currency conversion. A "currency value" is a relative
 * value of a currency. The exchange rate between a pair of currency types is
 * based on the relative value of each currency. The class uses a
 * {@link TreeMap<Currency, BigDecimal>} as internal representation. All class
 * methods are synchronized (towards {@link CurrencyConverter#currencyValues} as
 * necessary to support concurrent calls. All {@link CurrencyConverter} objects:
 * <p>
 * - work with a fixed precision (two decimal digits fractional part) that
 * allows operating with cents when converting {@link Money};
 * <p>
 * - use a fixed {@link RoundingMode} - {@link RoundingMode#HALF_UP} when
 * converting {@link Money}.
 * <p>
 * Objects of this class are {@link Serializable} so they can be sent over a
 * network connection on demand.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class CurrencyConverter implements Serializable {

    /**
     * The {@link RoundingMode} to use when converting {@link Money} objects.
     */
    private final static RoundingMode ROUNDINGMODE = RoundingMode.HALF_UP;

    /**
     * The precision (decimal digits fractional part) to use when converting
     * {@link Money} objects.
     */
    private final static int SCALE = 2;

    /**
     * Stores the relative (proportional) values of all supported currencies.
     * All methods that suggest concurrent access synchronize to this field.
     */
    private final TreeMap<Currency, BigDecimal> currencyValues;

    public CurrencyConverter() {
        this.currencyValues = new TreeMap<>();
    }

    /**
     * Sets the relative (proportional) value of a currency. Does not allow
     * setting a zero value, or setting a value for a NULL key.
     *
     * @param currency the currency to set the value of.
     *
     * @param value the value to set for the currency.
     *
     * @return true if successful, otherwise false.
     */
    public boolean setCurrencyValue(Currency currency, BigDecimal value) {
        if ((currency != null) && (value != null) && (value.compareTo(BigDecimal.ZERO) != 0)) {
            synchronized (this.currencyValues) {
                this.currencyValues.put(currency, value);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Sets the relative (proportional) valueString of a currency. Does not
     * allow setting a zero valueString, or setting a valueString for a NULL
     * key.
     *
     * @param currency the currency to set the valueString of.
     *
     * @param valueString the valueString to set for the currency.
     *
     * @return true if successful, otherwise false.
     */
    public boolean setCurrencyValue(Currency currency, String valueString) {
        if ((currency != null) && (valueString != null)) {
            BigDecimal value;
            try {
                value = new BigDecimal(valueString);
            } catch (Exception ex) {
                value = null;
            }
            return this.setCurrencyValue(currency, value);
        } else {
            return false;
        }
    }

    /**
     * Sets the relative (proportional) values of the provided currencies. Does
     * not allow setting a zero values, or setting a value for a NULL key. The
     * operation is atomic - all values are set within a single synchronized
     * call. If even a single operation is not legit, the entire request is
     * discarded. The arrays must have the same sizes and the same ordering.
     *
     * @param currencies the currencies to set the values of.
     *
     * @param values the values to set for the currencies.
     *
     * @return true if successful, otherwise false.
     */
    public boolean setCurrencyValues(Currency[] currencies, BigDecimal[] values) {
        if ((currencies != null) && (values != null)) {
            int l1 = currencies.length;
            int l2 = values.length;
            if (l1 == l2) {
                boolean legit = true;
                for (int i = 0; (i < l1) && legit; i++) {
                    if ((currencies[i] == null) || (values[i] == null)) {
                        legit = false;
                    } else if (values[i].compareTo(BigDecimal.ZERO) == 0) {
                        legit = false;
                    }
                }
                if (legit) {
                    synchronized (this.currencyValues) {
                        for (int i = 0; i < l1; i++) {
                            this.currencyValues.put(currencies[i], values[i]);
                        }
                    }
                }
                return legit;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Sets the relative (proportional) values of the provided currencies. Does
     * not allow setting a zero values, or setting a value for a NULL key. The
     * operation is atomic - all values are set within a single synchronized
     * call. If even a single operation is not legit, the entire request is
     * discarded. The arrays must have the same sizes and the same ordering.
     *
     * @param currencies the currencies to set the values of.
     *
     * @param valuesStr the values to set for the currencies.
     *
     * @return true if successful, otherwise false.
     */
    public boolean setCurrencyValues(Currency[] currencies, String[] valuesStr) {
        if ((currencies != null) && (valuesStr != null)) {
            int length = valuesStr.length;
            if (length > 0) {
                BigDecimal[] values = new BigDecimal[length];
                try {
                    for (int i = 0; i < length; i++) {
                        values[i] = new BigDecimal(valuesStr[i]);
                    }
                } catch (Exception ex) {
                    values = null;
                }
                return this.setCurrencyValues(currencies, values);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Converts a {@link Money} object to a {@link Money} object of the
     * specified {@link Currency} with equal value. All
     * {@link CurrencyConverter} objects:
     * <p>
     * - work with a fixed precision (two decimal digits fractional part) that
     * allows operating with cents when converting {@link Money};
     * <p>
     * - use a fixed {@link RoundingMode} - {@link RoundingMode#HALF_UP} when
     * converting {@link Money}.
     *
     * @param moneyFrom the {@link Money} to convert.
     *
     * @param currencyTo the requested {@link Currency}.
     *
     * @return a {@link Money} object of the specified currency with equal
     * value, NULL if failed (i.e. currency not supported).
     */
    public Money convert(Money moneyFrom, Currency currencyTo) {
        if ((moneyFrom != null) && (currencyTo != null)) {
            BigDecimal valueFrom;
            BigDecimal valueTo;
            synchronized (this.currencyValues) {
                valueFrom = this.currencyValues.get(moneyFrom.getCurrency());
                valueTo = this.currencyValues.get(currencyTo);
            }
            if ((valueFrom != null) && (valueTo != null)) {
                BigDecimal amountTo = moneyFrom.getAmount().multiply(valueFrom).divide(valueTo, CurrencyConverter.SCALE, CurrencyConverter.ROUNDINGMODE);
                return Money.createMoney(currencyTo, amountTo);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * For each supported {@link Currency}, converts the provided {@link Money}
     * object to a {@link Money} object of the supported {@link Currency} with
     * equal value. Adds the results in a {@link TreeMap<Currency, Money>}. All
     * {@link CurrencyConverter} objects:
     * <p>
     * - work with a fixed precision (two decimal digits fractional part) that
     * allows operating with cents when converting {@link Money};
     * <p>
     * - use a fixed {@link RoundingMode} - {@link RoundingMode#HALF_UP} when
     * converting {@link Money}.
     *
     * @param moneyFrom the {@link Money} to convert.
     *
     * @return a {@link TreeMap<Currency, Money>} object that is mapped currency
     * to money result. The map will be empty if the provided currency is not
     * supported.
     */
    public TreeMap<Currency, Money> convertToAllSupported(Money moneyFrom) {
        TreeMap<Currency, Money> result = new TreeMap<>();
        if (moneyFrom != null) {
            Currency currencyFrom = moneyFrom.getCurrency();
            BigDecimal valueFrom = this.currencyValues.get(currencyFrom);
            if (valueFrom != null) {
                synchronized (this.currencyValues) {
                    for (Map.Entry<Currency, BigDecimal> entry : this.currencyValues.entrySet()) {
                        Currency currencyTo = entry.getKey();
                        BigDecimal valueTo = entry.getValue();
                        BigDecimal amountTo = moneyFrom.getAmount().multiply(valueFrom).divide(valueTo, CurrencyConverter.SCALE, CurrencyConverter.ROUNDINGMODE);
                        Money moneyTo = Money.createMoney(currencyTo, amountTo);
                        result.put(currencyTo, moneyTo);
                    }
                }
            }
        }
        return result;
    }
}
