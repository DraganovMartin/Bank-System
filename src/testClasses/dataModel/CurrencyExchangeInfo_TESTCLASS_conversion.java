package testClasses.dataModel;

import dataModel.Currency;
import dataModel.CurrencyExchangeInfo;
import dataModel.Money;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class CurrencyExchangeInfo_TESTCLASS_conversion {

    public static void main(String[] args) {

        // PREDEFINED CONSTANTS:
        RoundingMode ROUNDINGMODE = RoundingMode.HALF_UP;
        int SCALE = 2;
        Currency BGN = new Currency("BGN", "BG leva");
        Currency USD = new Currency("USD", "US dollars");
        Currency GBP = new Currency("GBP", "UK pounds");
        Currency EUR = new Currency("EUR", "Euros");
        Currency CHF = new Currency("CHF", "Swiss frank");

        CurrencyExchangeInfo info = new CurrencyExchangeInfo(ROUNDINGMODE, SCALE);

        // assign the current rates in BGN:
        info.setCurrencyValue(BGN, BigDecimal.ONE);
        info.setCurrencyValue(USD, new BigDecimal("1.85229"));
        info.setCurrencyValue(GBP, new BigDecimal("2.33017"));
        info.setCurrencyValue(EUR, new BigDecimal("1.95583"));
        info.setCurrencyValue(CHF, new BigDecimal("1.81836"));

        // TESTING:
        {
            // Readjusting rates:
            // CHANGE ONLY THESE FOR TESTING:
            Currency newCurrency = EUR;
            BigDecimal newValue = new BigDecimal("1");
            RoundingMode newRoundingMode = RoundingMode.CEILING;
            int newScale = 6;

            // output the result:
            TreeMap<Currency, BigDecimal> values;
            System.out.println("Readjusting rates example:");
            System.out.println("Old rates: ");
            {
                values = info.getCurrencyValues();
                for (Map.Entry<Currency, BigDecimal> entry : values.entrySet()) {
                    System.out.println("\t" + (entry.getKey() + " " + entry.getValue()));
                }
            }
            CurrencyExchangeInfo newInfo = info.getCurrencyExchangeInfo(newCurrency, newValue, newRoundingMode, newScale);
            System.out.println("New rates (recalculalted for: " + newCurrency + " value: " + newValue);
            {
                values = newInfo.getCurrencyValues();
                for (Map.Entry<Currency, BigDecimal> entry : values.entrySet()) {
                    System.out.println("\t" + (entry.getKey() + " " + entry.getValue()));
                }
            }
        }

        System.out.println();

        {
            // Readjusting rates:
            // CHANGE ONLY THESE FOR TESTING:
            Currency newCurrency = USD;
            BigDecimal newValue = new BigDecimal("10");
            RoundingMode newRoundingMode = RoundingMode.CEILING;
            int newScale = 6;

            // output the result:
            TreeMap<Currency, BigDecimal> values;
            System.out.println("Readjusting rates example:");
            System.out.println("Old rates: ");
            {
                values = info.getCurrencyValues();
                for (Map.Entry<Currency, BigDecimal> entry : values.entrySet()) {
                    System.out.println("\t" + (entry.getKey() + " " + entry.getValue()));
                }
            }
            CurrencyExchangeInfo newInfo = info.getCurrencyExchangeInfo(newCurrency, newValue, newRoundingMode, newScale);
            System.out.println("New rates (recalculalted for: " + newCurrency + " value: " + newValue);
            {
                values = newInfo.getCurrencyValues();
                for (Map.Entry<Currency, BigDecimal> entry : values.entrySet()) {
                    System.out.println("\t" + (entry.getKey() + " " + entry.getValue()));
                }
            }
        }

        System.out.println();

        {
            // Money conversion:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "3.445046545";
            Currency currencyFrom = BGN;
            Currency currencyTo = CHF;

            // output the result:
            {
                Money moneyFrom = Money.createMoney(currencyFrom, new BigDecimal(amount));
                Money moneyTo = info.convert(moneyFrom, currencyTo);
                System.out.print("Converting " + moneyFrom.toString() + " to " + currencyTo.toString() + ":");
                System.out.println(" result: " + moneyTo.toString());
            }
        }

        {
            // Money conversion:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "0.0001";
            Currency currencyFrom = USD;
            Currency currencyTo = GBP;

            // output the result:
            {
                Money moneyFrom = Money.createMoney(currencyFrom, new BigDecimal(amount));
                Money moneyTo = info.convert(moneyFrom, currencyTo);
                System.out.print("Converting " + moneyFrom.toString() + " to " + currencyTo.toString() + ":");
                System.out.println(" result: " + moneyTo.toString());
            }
        }

        {
            // Money conversion:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "1.1";
            Currency currencyFrom = CHF;
            Currency currencyTo = GBP;

            // output the result:
            {
                Money moneyFrom = Money.createMoney(currencyFrom, new BigDecimal(amount));
                Money moneyTo = info.convert(moneyFrom, currencyTo);
                System.out.print("Converting " + moneyFrom.toString() + " to " + currencyTo.toString() + ":");
                System.out.println(" result: " + moneyTo.toString());
            }
        }

        System.out.println();

        {
            // Money exhange rates:
            // CHANGE ONLY THESE FOR TESTING:
            Currency currencyFrom = BGN;
            Currency currencyTo = GBP;

            // output the result:
            {
                System.out.print("Converting " + currencyFrom.toString() + " to " + currencyTo.toString() + ":");
                System.out.println(" exchange rate: " + info.getExchangeRate(currencyFrom, currencyTo));
            }
        }

        {
            // Money exhange rates:
            // CHANGE ONLY THESE FOR TESTING:
            Currency currencyFrom = USD;
            Currency currencyTo = CHF;

            // output the result:
            {
                System.out.print("Converting " + currencyFrom.toString() + " to " + currencyTo.toString() + ":");
                System.out.println(" exchange rate: " + info.getExchangeRate(currencyFrom, currencyTo));
            }
        }

        {
            // Money exhange rates:
            // CHANGE ONLY THESE FOR TESTING:
            Currency currencyFrom = GBP;
            Currency currencyTo = EUR;

            // output the result:
            {
                System.out.print("Converting " + currencyFrom.toString() + " to " + currencyTo.toString() + ":");
                System.out.println(" exchange rate: " + info.getExchangeRate(currencyFrom, currencyTo));
            }
        }

        System.out.println();

        {
            // All supported currency exhange rates:
            // CHANGE ONLY THESE FOR TESTING:
            Currency currencyFrom = GBP;

            // output the result:
            {
                System.out.println("All supported exchange rates for currency: " + currencyFrom.toString() + ":");
                TreeMap<Currency, BigDecimal> rates = info.getExchangeRates(currencyFrom);
                for (Map.Entry<Currency, BigDecimal> entry : rates.entrySet()) {
                    System.out.println(currencyFrom + " to " + entry.getKey() + ": " + entry.getValue());
                }
            }
        }

        System.out.println();

        {
            // All supported currency exhange rates:
            // CHANGE ONLY THESE FOR TESTING:
            Currency currencyFrom = EUR;

            // output the result:
            {
                System.out.println("All supported exchange rates for currency: " + currencyFrom.toString() + ":");
                TreeMap<Currency, BigDecimal> rates = info.getExchangeRates(currencyFrom);
                for (Map.Entry<Currency, BigDecimal> entry : rates.entrySet()) {
                    System.out.println(currencyFrom + " to " + entry.getKey() + ": " + entry.getValue());
                }
            }
        }

        System.out.println();
    }
}
