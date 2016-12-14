package testClasses.dataModel;

import dataModel.Currency;
import dataModel.Money;
import java.math.BigDecimal;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Money_TESTCLASS_creation {

    public static void main(String[] args) {

        Currency BGN = new Currency("BGN", "BG leva");
        Currency USD = new Currency("USD", "US dollars");
        Currency GBP = new Currency("GBP", "UK pounds");
        Currency EUR = new Currency("EUR", "Euros");
        Currency CHF = new Currency("CHF", "Swiss frank");

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
