package demo_anything;

import dataModel.CurrencyConverter;
import dataModel.ProfileData;
import dataModel.models.Currency;
import java.util.Random;
import networking.messages.Message;
import networking.messages.Request;
import networking.messages.Update;

/**
 *
 * @author iliyan
 */
public class RandomSuccessfulUpdateFactory {

    static final Random RANDOM = new Random();

    public static Update getRandomSuccessfulUpdate(Message message) {
        ProfileData.Balance balance = new ProfileData.Balance();
        ProfileData.TransferHistory transferHistory = new ProfileData.TransferHistory();
        CurrencyConverter currencyConverter = new CurrencyConverter();
        ProfileData profileData;
        {
            int MAXCURRENCYCOUNT = 5;
            int currencyCount = 1 + Math.abs(RANDOM.nextInt() % MAXCURRENCYCOUNT);
            for (int i = 0; i < currencyCount; i++) {
                Double val = new Double(1 + Math.abs(RANDOM.nextInt() % 1000)) / 1000;
                currencyConverter.setCurrencyValue(new Currency("currency_" + (i + 1)), val.toString());
            }

            int MAXACCOUNTSCOUNT = 5;
            int accountsCount = 1 + Math.abs(RANDOM.nextInt() % MAXACCOUNTSCOUNT);
            for (int i = 0; i < accountsCount; i++) {
                Currency currency = currencyConverter.getSupportedCurrencies()[Math.abs(RANDOM.nextInt() % currencyCount)];
                Double amount = new Double(1 + Math.abs(RANDOM.nextInt() % 1000));
                balance.add("acc_" + (i + 1), "type", currency.getSymbol(), amount.toString());
            }

            int MAXTRANSFERSCOUNT = 5;
            int transfersCount = 1 + Math.abs(RANDOM.nextInt() % MAXTRANSFERSCOUNT);
            for (int i = 0; i < transfersCount; i++) {
                Currency currency = currencyConverter.getSupportedCurrencies()[Math.abs(RANDOM.nextInt() % currencyCount)];
                Double amount = new Double(1 + Math.abs(RANDOM.nextInt() % 1000));
                int from = 1 + Math.abs(RANDOM.nextInt() % MAXACCOUNTSCOUNT);
                int to = 1 + Math.abs(RANDOM.nextInt() % MAXACCOUNTSCOUNT);
                while (to == from) {
                    to = 1 + Math.abs(RANDOM.nextInt() % MAXACCOUNTSCOUNT);
                }
                transferHistory.add("transfer_" + (i + 1), "acc_" + from, "acc_" + to, currency.getSymbol(), amount.toString(), "date_" + (i + 1));
            }

            profileData = new ProfileData(balance, transferHistory, currencyConverter);
        }
        Update update = new Update("Update from server", (Request) message, true, profileData);

        return update;
    }
}
