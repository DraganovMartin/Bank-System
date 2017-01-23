package testClasses.database;

import dataModel.Money;
import dataModel.models.Currency;
import database.DatabaseHandler;
import networking.messages.request.TransferRequest;

import java.net.ConnectException;

/**
 * Created by Nikolay on 1/23/2017.
 */
public class transfers {
    public static void main(String[] args) throws ConnectException {
        DatabaseHandler dh = new DatabaseHandler();
            dh.handleTransferRequest(new TransferRequest("24","60", Money.createMoney(new Currency("BGN"), "99.5")));
    }
}
