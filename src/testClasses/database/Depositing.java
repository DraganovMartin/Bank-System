package testClasses.database;

import dataModel.Money;
import dataModel.models.Currency;
import dataModel.models.SystemProfile;
import database.DatabaseHandler;
import networking.messages.Update;
import networking.messages.request.DepositRequest;
import networking.messages.request.WithdrawRequest;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;

/**
 * Created by Nikolay on 1/23/2017.
 */
public class Depositing  {
    public static void main(String[] args) throws ConnectException {
        DatabaseHandler dh = new DatabaseHandler();
        DepositRequest request = new DepositRequest("1", Money.createMoney(new Currency("USD"),"6666.66"));
        request.setUsername("niksan");
        //Update update  = dh.handleDepositRequest(request);
        WithdrawRequest withdrawRequest = new WithdrawRequest("1",Money.createMoney(new Currency("USD"),"6666.66"));
        withdrawRequest.setUsername("niksan");
        Update update = dh.handleWithdrawRequest(withdrawRequest);
        JFrame win = new JFrame();
        System.out.println(update.isSuccessful());
        JTable balance = update.getProflieData().getBalanceTable();
        JTable transfers = update.getProflieData().getTransferHistoryTable();

        win.setLayout(new BorderLayout());
        win.add(balance,BorderLayout.WEST);
        win.add(transfers,BorderLayout.EAST);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}
