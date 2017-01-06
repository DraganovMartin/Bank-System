package dataModel;

import dataModel.models.Currency;
import java.awt.GridLayout;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
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

    /**
     * Returns a list of all supported currencies.
     *
     * @return a list of all supported currencies, NULL if no currency is
     * supported.
     */
    public Currency[] getSupportedCurrencies() {
        Currency[] result;
        synchronized (this.currencyValues) {
            int size = this.currencyValues.entrySet().size();
            if (size > 0) {
                result = new Currency[size];
                int i = 0;
                for (Map.Entry<Currency, BigDecimal> entry : this.currencyValues.entrySet()) {
                    result[i] = entry.getKey();
                    i++;
                }
            } else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Returns the exchange rates of all supported currencies towards the
     * specified base currency in the format of a 2D array (as a table):
     * <p>
     * <table border=1>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency1
     * </td>
     * <td>=
     * </td>
     * <td>Amount1
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency2
     * </td>
     * <td>=
     * </td>
     * <td>Amount2
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * <td>=
     * </td>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>CurrencyN
     * </td>
     * <td>=
     * </td>
     * <td>AmountN
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * </table>
     *
     * <p>
     * When necessary, multiplies the left side by a power of 10.
     *
     * @param currencyTo the currency to convert to.
     *
     * @param minResult the minimal result to accept for the right side and , if
     * necessary, multiply both sides by a power of 10 (i.e. 10, 100, 1000 et.).
     * Used to avoid results with zero value. If NULL is provided, returns any
     * results greater that 0.00, otherwise multiplies them.
     *
     * @return a table with the exchange rates, NULL if no currency is
     * supported.
     */
    public String[][] getSupportedExchangeRates(Currency currencyTo, BigDecimal minResult) {
        BigDecimal min = minResult;
        if (min == null) {
            min = BigDecimal.ZERO;
        } else if (min.compareTo(BigDecimal.ZERO) < 0) {
            min = BigDecimal.ZERO;
        }
        synchronized (this.currencyValues) {
            BigDecimal value = this.currencyValues.get(currencyTo);
            if (value != null) {
                int size = this.currencyValues.size();
                String[][] result = new String[size][5];
                int i = 0;
                for (Map.Entry<Currency, BigDecimal> entry : this.currencyValues.entrySet()) {
                    Currency currencyFrom = entry.getKey();
                    BigDecimal amountFrom = BigDecimal.ONE;
                    Money moneyFrom;
                    Money moneyTo;
                    do {
                        moneyFrom = Money.createMoney(currencyFrom, amountFrom);
                        moneyTo = this.convert(moneyFrom, currencyTo);
                        amountFrom = amountFrom.multiply(BigDecimal.TEN);
                    } while (moneyTo.getAmount().compareTo(min) < 0);
                    result[i][0] = moneyFrom.getAmount().toPlainString();
                    result[i][1] = moneyFrom.getCurrency().getSymbol();
                    result[i][2] = "=";
                    result[i][3] = moneyTo.getAmount().toPlainString();
                    result[i][4] = moneyTo.getCurrency().getSymbol();
                    i++;
                }
                return result;
            } else {
                return null;
            }
        }
    }

    /**
     * Returns the exchange rates of all supported currencies towards the
     * specified base currency in the format of a 2D array (as a table):
     * <p>
     * <table border=1>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency1
     * </td>
     * <td>=
     * </td>
     * <td>Amount1
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency2
     * </td>
     * <td>=
     * </td>
     * <td>Amount2
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * <td>=
     * </td>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>CurrencyN
     * </td>
     * <td>=
     * </td>
     * <td>AmountN
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * </table>
     *
     * <p>
     * When necessary, multiplies the left side by a power of 10.
     *
     * @param currencyTo the currency to convert to.
     *
     * @param minResult the minimal result to accept for the right side and , if
     * necessary, multiply both sides by a power of 10 (i.e. 10, 100, 1000 et.).
     * Used to avoid results with zero value. If NULL is provided, returns any
     * results greater that 0.00, otherwise multiplies them.
     *
     * @return a table with the exchange rates, NULL if no currency is
     * supported.
     */
    public String[][] getSupportedExchangeRates(Currency currencyTo, String minResult) {
        BigDecimal min;
        try {
            min = new BigDecimal(minResult);
        } catch (Exception ex) {
            min = null;
        }
        return this.getSupportedExchangeRates(currencyTo, min);
    }

    /**
     * Returns the exchange rates of all supported currencies towards the
     * specified base currency in the format of a JPanel formatted as a table:
     * <p>
     * <table border=1>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency1
     * </td>
     * <td>=
     * </td>
     * <td>Amount1
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency2
     * </td>
     * <td>=
     * </td>
     * <td>Amount2
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * <td>=
     * </td>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>CurrencyN
     * </td>
     * <td>=
     * </td>
     * <td>AmountN
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * </table>
     *
     * <p>
     * When necessary, multiplies the left side by a power of 10.
     *
     * @param currencyTo the currency to convert to.
     *
     * @param minResult the minimal result to accept for the right side and , if
     * necessary, multiply both sides by a power of 10 (i.e. 10, 100, 1000 et.).
     * Used to avoid results with zero value. If NULL is provided, returns any
     * results greater that 0.00, otherwise multiplies them.
     *
     * @return a JPanel formatted as a table with the exchange rates, NULL if no
     * currency is supported.
     */
    public JPanel getSupportedExchangeRatesAsJPanel(Currency currencyTo, BigDecimal minResult) {
        String[][] rates = this.getSupportedExchangeRates(currencyTo, minResult);
        if (rates != null) {
            JPanel result = new JPanel(new GridLayout(rates.length, rates[0].length));
            for (int i = 0; i < rates.length; i++) {
                for (int j = 0; j < rates[i].length; j++) {
                    result.add(new JLabel(rates[i][j]));
                }
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * Returns the exchange rates of all supported currencies towards the
     * specified base currency in the format of a JPanel formatted as a table:
     * <p>
     * <table border=1>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency1
     * </td>
     * <td>=
     * </td>
     * <td>Amount1
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>Currency2
     * </td>
     * <td>=
     * </td>
     * <td>Amount2
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * <tr>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * <td>=
     * </td>
     * <td>...
     * </td>
     * <td>...
     * </td>
     * </tr>
     *
     * <tr>
     * <td>1
     * </td>
     * <td>CurrencyN
     * </td>
     * <td>=
     * </td>
     * <td>AmountN
     * </td>
     * <td>BaseCurrency
     * </td>
     * </tr>
     *
     * </table>
     *
     * <p>
     * When necessary, multiplies the left side by a power of 10.
     *
     * @param currencyTo the currency to convert to.
     *
     * @param minResult the minimal result to accept for the right side and , if
     * necessary, multiply both sides by a power of 10 (i.e. 10, 100, 1000 et.).
     * Used to avoid results with zero value. If NULL is provided, returns any
     * results greater that 0.00, otherwise multiplies them.
     *
     * @return a JPanel formatted as a table with the exchange rates, NULL if no
     * currency is supported.
     */
    public JPanel getSupportedExchangeRatesAsJPanel(Currency currencyTo, String minResult) {
        BigDecimal min;
        try {
            min = new BigDecimal(minResult);
        } catch (Exception ex) {
            min = null;
        }
        return this.getSupportedExchangeRatesAsJPanel(currencyTo, min);
    }

    /**
     * Returns (a + b) a {@link Money} object of the requested currency with
     * value equal to the sum of the values of both arguments, according to
     * converter the exchange rates. Returns NULL if either currency is not
     * supported.
     *
     * @param a the first argument.
     *
     * @param b the second argument.
     *
     * @param currency the requested currency for the result.
     *
     * @return (a + b) a {@link Money} object of the requested currency with
     * value equal to the sum of the values of both arguments, according to
     * converter the exchange rates. Returns NULL if either currency is not
     * supported.
     */
    public Money calcSum(Money a, Money b, Currency currency) {
        Money convertedA = this.convert(a, currency);
        Money convertedB = this.convert(b, currency);
        if ((convertedA != null) && (convertedB != null)) {
            return Money.createMoney(currency, (convertedA.getAmount()).add(convertedB.getAmount()));
        } else {
            return null;
        }
    }

    /**
     * Returns (a + b) a {@link Money} object with value equal to the sum of the
     * values of both arguments, according to converter the exchange rates. The
     * currency of the result is determined by the currency of the first
     * argument. Returns NULL if either currency is not supported. Calls
     * {@link #calcSum(dataModel.Money, dataModel.Money, dataModel.models.Currency)}
     * internally.
     *
     * @see #calcSum(dataModel.Money, dataModel.Money,
     * dataModel.models.Currency)
     *
     * @param a the first argument. Determines the currency of the result.
     *
     * @param b the second argument.
     *
     * @return (a + b) a {@link Money} object with value equal to the sum of the
     * values of both arguments, according to converter the exchange rates. The
     * currency of the result is determined by the currency of the first
     * argument. Returns NULL if either currency is not supported. Calls
     * {@link #calcSum(dataModel.Money, dataModel.Money, dataModel.models.Currency)}
     * internally.
     */
    public Money calcSum(Money a, Money b) {
        Currency currency = a.getCurrency();
        return this.calcSum(a, b, currency);
    }

    /**
     * Returns (a - b) a {@link Money} object of the requested currency with
     * value equal to the difference of the values of both arguments, according
     * to converter the exchange rates. Returns NULL if either currency is not
     * supported.
     *
     * @param a the first argument.
     *
     * @param b the second argument.
     *
     * @param currency the requested currency for the result.
     *
     * @return (a - b) a {@link Money} object of the requested currency with
     * value equal to the difference of the values of both arguments, according
     * to converter the exchange rates. Returns NULL if either currency is not
     * supported.
     */
    public Money calcDifference(Money a, Money b, Currency currency) {
        Money convertedA = this.convert(a, currency);
        Money convertedB = this.convert(b, currency);
        if ((convertedA != null) && (convertedB != null)) {
            return Money.createMoney(currency, (convertedA.getAmount()).subtract(convertedB.getAmount()));
        } else {
            return null;
        }
    }

    /**
     * Returns (a - b) a {@link Money} object with value equal to the difference
     * of the values of both arguments, according to converter the exchange
     * rates. The currency of the result is determined by the currency of the
     * first argument. Returns NULL if either currency is not supported. Calls
     * {@link #calcDifference(dataModel.Money, dataModel.Money, dataModel.models.Currency)}
     * internally.
     *
     * @see #calcDifference(dataModel.Money, dataModel.Money,
     * dataModel.models.Currency)
     *
     * @param a the first argument. Determines the currency of the result.
     *
     * @param b the second argument.
     *
     * @return (a - b) a {@link Money} object with value equal to the difference
     * of the values of both arguments, according to converter the exchange
     * rates. The currency of the result is determined by the currency of the
     * first argument. Returns NULL if either currency is not supported. Calls
     * {@link #calcSum(dataModel.Money, dataModel.Money, dataModel.models.Currency)}
     * internally.
     */
    public Money calcDifference(Money a, Money b) {
        Currency currency = a.getCurrency();
        return this.calcDifference(a, b, currency);
    }
}
