package testClasses.database;

import dataModel.Money;
import dataModel.models.Currency;
import database.DatabaseHandler;
import networking.messages.request.CreateBankAccountRequest;

import java.net.ConnectException;

/**
 * Created by Nikolay on 1/23/2017.
 */
public class bankAccountCreate {
    public static void main(String[] args) throws ConnectException {
        DatabaseHandler dh = new DatabaseHandler();
        Money money1 = Money.createMoney(new Currency("BGN"),"1000");
        Money money2 = Money.createMoney(new Currency("USD"),"1000");
        Money money3 = Money.createMoney(new Currency("GPB"),"1000");
        Money money4 = Money.createMoney(new Currency("EUR"),"1000");
        Money money5 = Money.createMoney(new Currency("CHF"),"1000");
        CreateBankAccountRequest request1 = new CreateBankAccountRequest("deposit",money1);
        request1.setUsername("niksan");
        CreateBankAccountRequest request2 = new CreateBankAccountRequest("market",money5);
        request2.setUsername("niksan");
        CreateBankAccountRequest request3 = new CreateBankAccountRequest("sevings",money2);
        request3.setUsername("niksan");
        CreateBankAccountRequest request4 = new CreateBankAccountRequest("deposit",money3);
        request4.setUsername("alex");
        CreateBankAccountRequest request5 = new CreateBankAccountRequest("deposit",money4);
        request5.setUsername("marko");
        CreateBankAccountRequest request6 = new CreateBankAccountRequest("deposit",money1);
        request6.setUsername("marko");
        CreateBankAccountRequest request7 = new CreateBankAccountRequest("market",money5);
        request7.setUsername("mizonet");
        CreateBankAccountRequest request8 = new CreateBankAccountRequest("sevings",money2);
        request8.setUsername("mizonet");
        CreateBankAccountRequest request9 = new CreateBankAccountRequest("deposit",money3);
        request9.setUsername("alex");
        CreateBankAccountRequest request10 = new CreateBankAccountRequest("deposit",money4);
        request10.setUsername("niksan");
        dh.handleCreateBankAccountRequest(request1);
        dh.handleCreateBankAccountRequest(request2);
        dh.handleCreateBankAccountRequest(request3);
        dh.handleCreateBankAccountRequest(request4);
        dh.handleCreateBankAccountRequest(request5);
        dh.handleCreateBankAccountRequest(request6);
        dh.handleCreateBankAccountRequest(request7);
        dh.handleCreateBankAccountRequest(request8);
        dh.handleCreateBankAccountRequest(request9);
        dh.handleCreateBankAccountRequest(request10);
        /**
         * converter.setCurrencyValue(BGN, BigDecimal.ONE);
         converter.setCurrencyValue(USD, "1.85229");
         converter.setCurrencyValue(GBP, "2.33017");
         converter.setCurrencyValue(EUR, "1.95583");
         converter.setCurrencyValue(CHF, "1.81836");
         */
    }
}
