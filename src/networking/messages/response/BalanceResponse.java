package networking.messages.response;

import networking.messages.Response;
import dataModel.Money;

import java.sql.ResultSet;

/**
 * Created by Nikolay on 1/8/2017.
 */
public class BalanceResponse extends Response {

    public static final String TYPE = "BALANCERESPONSE";

    private String userName;

    private Money amount;

    public BalanceResponse(String userName, Money money) {
        super(BalanceResponse.TYPE, "Data for " + userName + " about balance!");
        this.userName = userName;
        this.amount = money;
    }

    public String getUserName() {
        return userName;
    }

    public Money getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return null;
    }
}
