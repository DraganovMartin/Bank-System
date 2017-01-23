package testClasses.database;

import dataModel.Money;
import dataModel.models.Currency;
import dataModel.models.SystemProfile;
import database.DatabaseHandler;
import networking.messages.Update;
import networking.messages.request.ChangePasswordRequest;
import networking.messages.request.DepositRequest;
import networking.messages.request.WithdrawRequest;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;

/**
 * Created by Nikolay on 1/23/2017.
 */
public class ChangePassword  {
    public static void main(String[] args) throws ConnectException {
        DatabaseHandler dh = new DatabaseHandler();
        ChangePasswordRequest request = new ChangePasswordRequest("miok","midobre");
        request.setUsername("kake");
        Update update = dh.handleChangePasswordRequest(request);
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
