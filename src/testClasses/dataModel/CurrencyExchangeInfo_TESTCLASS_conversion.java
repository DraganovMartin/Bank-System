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
            // TreeMap version:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "3.445046545";
            Currency currency = BGN;

            // output the rates:
            Money base = Money.createMoney(currency, new BigDecimal(amount));
            {
                TreeMap<Currency, BigDecimal> exchanged = info.getCurrencyExchangeMap(base.getCurrency(), base.getAmount());
                System.out.println();
                System.out.println("Using rounding mode: " + ROUNDINGMODE.toString() + ", scale: " + SCALE);
                System.out.println("Value of " + base.getAmount() + " " + base.getCurrency().getPrimaryKeyValue() + ":");
                for (Map.Entry<Currency, BigDecimal> entry : exchanged.entrySet()) {
                    System.out.println(entry.getKey().getPrimaryKeyValue() + ": " + entry.getValue().toPlainString());
                }
            }
        }

        {
            // TreeMap version:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "634.4545";
            Currency currency = USD;

            // output the rates:
            Money base = Money.createMoney(currency, new BigDecimal(amount));
            {
                TreeMap<Currency, BigDecimal> exchanged = info.getCurrencyExchangeMap(base.getCurrency(), base.getAmount());
                System.out.println();
                System.out.println("Using rounding mode: " + ROUNDINGMODE.toString() + ", scale: " + SCALE);
                System.out.println("Value of " + base.getAmount() + " " + base.getCurrency().getPrimaryKeyValue() + ":");
                for (Map.Entry<Currency, BigDecimal> entry : exchanged.entrySet()) {
                    System.out.println(entry.getKey().getPrimaryKeyValue() + ": " + entry.getValue().toPlainString());
                }
            }
        }

        {
            // CurrencyExchangeInfo version:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "135.45426";
            Currency currency = CHF;
            ROUNDINGMODE = RoundingMode.DOWN;
            SCALE = 5;

            // output the rates:
            Money base = Money.createMoney(currency, new BigDecimal(amount));
            {
                CurrencyExchangeInfo exchanged = info.getCurrencyExchangeInfo(base.getCurrency(), base.getAmount(), ROUNDINGMODE, SCALE);
                System.out.println();
                System.out.println("Using rounding mode: " + ROUNDINGMODE.toString() + ", scale: " + SCALE);
                System.out.println("Value of " + base.getAmount() + " " + base.getCurrency().getPrimaryKeyValue() + ":");
                for (Map.Entry<Currency, BigDecimal> entry : exchanged.getCurrencyExchangeMap(currency, new BigDecimal(amount)).entrySet()) {
                    System.out.println(entry.getKey().getPrimaryKeyValue() + ": " + entry.getValue().toPlainString());
                }
            }
        }

        {
            // CurrencyExchangeInfo version:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "135.45426";
            Currency currency = CHF;
            ROUNDINGMODE = RoundingMode.HALF_UP;
            SCALE = 1;

            // output the rates:
            Money base = Money.createMoney(currency, new BigDecimal(amount));
            {
                CurrencyExchangeInfo exchanged = info.getCurrencyExchangeInfo(base.getCurrency(), base.getAmount(), ROUNDINGMODE, SCALE);
                System.out.println();
                System.out.println("Using rounding mode: " + ROUNDINGMODE.toString() + ", scale: " + SCALE);
                System.out.println("Value of " + base.getAmount() + " " + base.getCurrency().getPrimaryKeyValue() + ":");
                for (Map.Entry<Currency, BigDecimal> entry : exchanged.getCurrencyExchangeMap(currency, new BigDecimal(amount)).entrySet()) {
                    System.out.println(entry.getKey().getPrimaryKeyValue() + ": " + entry.getValue().toPlainString());
                }
            }
        }

        {
            // Money conversion:
            // CHANGE ONLY THESE FOR TESTING:
            String amount = "3.445046545";
            Currency currencyFrom = BGN;
            Currency currencyTo = CHF;

            // output the result:
            {
                Money moneyFrom = Money.createMoney(currencyFrom, new BigDecimal(amount));
                Money moneyTo = info.getConvertedMoney(moneyFrom, currencyTo);
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
                Money moneyTo = info.getConvertedMoney(moneyFrom, currencyTo);
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
                Money moneyTo = info.getConvertedMoney(moneyFrom, currencyTo);
                System.out.print("Converting " + moneyFrom.toString() + " to " + currencyTo.toString() + ":");
                System.out.println(" result: " + moneyTo.toString());
            }
        }
    }
}
