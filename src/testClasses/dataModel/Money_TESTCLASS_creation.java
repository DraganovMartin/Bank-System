package testClasses.dataModel;

import dataModel.models.Currency;
import dataModel.Money;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class Money_TESTCLASS_creation {

    public static void main(String[] args) {

        Currency BGN = new Currency("BGN");
        Currency USD = new Currency("USD");
        Currency GBP = new Currency("GBP");
        Currency EUR = new Currency("EUR");
        Currency CHF = new Currency("CHF");

        {
            // CHANGE HERE FOR TESTING:
            String amount = "1.5";
            Currency currency = CHF;

            // output result:
            Money money = Money.createMoney(currency, amount);
            System.out.println("createMoney(" + currency.toString() + ", " + amount + ") = " + money.toString());
        }

        {
            // CHANGE HERE FOR TESTING:
            String amount = "0.94536";
            Currency currency = EUR;

            // output result:
            Money money = Money.createMoney(currency, amount);
            System.out.println("createMoney(" + currency.toString() + ", " + amount + ") = " + money.toString());
        }

        {
            // CHANGE HERE FOR TESTING:
            String amount = "1.532432";
            Currency currency = GBP;

            // output result:
            Money money = Money.createMoney(currency, amount);
            System.out.println("createMoney(" + currency.toString() + ", " + amount + ") = " + money.toString());
        }
    }
}
