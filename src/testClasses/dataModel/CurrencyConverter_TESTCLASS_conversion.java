package testClasses.dataModel;

import dataModel.models.Currency;
import dataModel.CurrencyConverter;
import dataModel.Money;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class CurrencyConverter_TESTCLASS_conversion {

    public static void main(String[] args) {

        // PREDEFINED CONSTANTS:
        Currency BGN = new Currency("BGN", "BG leva");
        Currency USD = new Currency("USD", "US dollars");
        Currency GBP = new Currency("GBP", "UK pounds");
        Currency EUR = new Currency("EUR", "Euros");
        Currency CHF = new Currency("CHF", "Swiss frank");

        CurrencyConverter converter = new CurrencyConverter();
        // assign the current rates in BGN:
        converter.setCurrencyValue(BGN, BigDecimal.ONE);
        converter.setCurrencyValue(USD, "1.85229");
        converter.setCurrencyValue(GBP, "2.33017");
        converter.setCurrencyValue(EUR, "1.95583");
        converter.setCurrencyValue(CHF, "1.81836");
        //alternative:
        {
            Currency[] currencies = {BGN, USD, GBP, EUR, CHF};
            String[] values = {"1.85229", "2.33017", "1.95583", "1.95583", "1.81836"};
            converter.setCurrencyValues(currencies, values);
        }

        // TESTS:
        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = BGN;
            Currency currencyTo = EUR;
            String amountFrom = "1";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            Money moneyTo = converter.convert(moneyFrom, currencyTo);
            System.out.println("COnverting " + moneyFrom + " to " + currencyTo + ": " + moneyTo);
        }

        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = USD;
            Currency currencyTo = EUR;
            String amountFrom = "1.454564";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            Money moneyTo = converter.convert(moneyFrom, currencyTo);
            System.out.println("COnverting " + moneyFrom + " to " + currencyTo + ": " + moneyTo);
        }

        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = BGN;
            Currency currencyTo = EUR;
            String amountFrom = "0.01";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            Money moneyTo = converter.convert(moneyFrom, currencyTo);
            System.out.println("COnverting " + moneyFrom + " to " + currencyTo + ": " + moneyTo);
        }

        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = BGN;
            Currency currencyTo = EUR;
            String amountFrom = "0.02";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            Money moneyTo = converter.convert(moneyFrom, currencyTo);
            System.out.println("COnverting " + moneyFrom + " to " + currencyTo + ": " + moneyTo);
        }

        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = BGN;
            Currency currencyTo = EUR;
            String amountFrom = "0.03";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            Money moneyTo = converter.convert(moneyFrom, currencyTo);
            System.out.println("COnverting " + moneyFrom + " to " + currencyTo + ": " + moneyTo);
        }

        // ALL SUPPORTED RATES:
        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = BGN;
            String amountFrom = "1";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            System.out.println();
            System.out.println("====== convert to supported currencies ======");
            TreeMap<Currency, Money> rates = converter.convertToAllSupported(moneyFrom);
            System.out.println("Value of " + moneyFrom + " in all supported currencies:");
            for (Map.Entry<Currency, Money> entry : rates.entrySet()) {
                System.out.println("\t" + entry.getValue());
            }
            System.out.println("====== convert to supported currencies ======");
        }

        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = USD;
            String amountFrom = "0.5";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            System.out.println();
            System.out.println("====== convert to supported currencies ======");
            TreeMap<Currency, Money> rates = converter.convertToAllSupported(moneyFrom);
            System.out.println("Value of " + moneyFrom + " in all supported currencies:");
            for (Map.Entry<Currency, Money> entry : rates.entrySet()) {
                System.out.println("\t" + entry.getValue());
            }
            System.out.println("====== convert to supported currencies ======");
        }

        {
            // CHANGE HERE FOR TESTING:
            Currency currencyFrom = CHF;
            String amountFrom = "3.14";

            // output result:
            Money moneyFrom = Money.createMoney(currencyFrom, amountFrom);
            System.out.println();
            System.out.println("====== convert to supported currencies ======");
            TreeMap<Currency, Money> rates = converter.convertToAllSupported(moneyFrom);
            System.out.println("Value of " + moneyFrom + " in all supported currencies:");
            for (Map.Entry<Currency, Money> entry : rates.entrySet()) {
                System.out.println("\t" + entry.getValue());
            }
            System.out.println("====== convert to supported currencies ======");
        }

        {
            // exchange rates:
            // CHANGE HERE FOR TESTING:
            Currency currencyTo = BGN;
            String minResult = "1.00";

            // Exchange rates:
            System.out.println();
            System.out.println("====== exchange rates to " + currencyTo.getSymbol() + ", minResult = " + minResult + "======");
            String[][] rates = converter.getSupportedExchangeRates(currencyTo, minResult);
            for (int i = 0; i < rates.length; i++) {
                for (int j = 0; j < rates[i].length; j++) {
                    System.out.print(rates[i][j] + "\t");
                }
                System.out.println();
            }
            System.out.println("====== exchange rates to " + currencyTo.getSymbol() + ", minResult = " + minResult + "======");
        }

        {
            // exchange rates:
            // CHANGE HERE FOR TESTING:
            Currency currencyTo = BGN;
            String minResult = "0.01";

            // Exchange rates:
            System.out.println();
            System.out.println("====== exchange rates to " + currencyTo.getSymbol() + ", minResult = " + minResult + "======");
            String[][] rates = converter.getSupportedExchangeRates(currencyTo, minResult);
            for (int i = 0; i < rates.length; i++) {
                for (int j = 0; j < rates[i].length; j++) {
                    System.out.print(rates[i][j] + "\t");
                }
                System.out.println();
            }
            System.out.println("====== exchange rates to " + currencyTo.getSymbol() + ", minResult = " + minResult + "======");
        }

        {
            // exchange rates as JPanel:
            // CHANGE HERE FOR TESTING:
            Currency currencyTo = BGN;
            String minResult = "0.01";

            // Exchange rates:
            JFrame frame = new JFrame();
            JPanel panel = converter.getSupportedExchangeRatesAsJPanel(currencyTo, minResult);
            frame.add(panel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }

        {
            // exchange rates as JPanel:
            // CHANGE HERE FOR TESTING:
            Currency currencyTo = BGN;
            String minResult = "1.00";

            // Exchange rates:
            JFrame frame = new JFrame();
            JPanel panel = converter.getSupportedExchangeRatesAsJPanel(currencyTo, minResult);
            frame.add(panel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }
}
