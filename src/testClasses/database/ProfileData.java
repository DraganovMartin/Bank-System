package testClasses.database;

import database.DatabaseHandler;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;

/**
 * Created by Nikolay on 1/23/2017.
 */
public class ProfileData  {
    public static void main(String[] args) throws ConnectException {
        DatabaseHandler dh = new DatabaseHandler();
        dataModel.ProfileData profileData = dh.getUserProfileData("niksan");
        JFrame win = new JFrame();
        JTable balance = profileData.getBalanceTable();
        JTable transfers = profileData.getTransferHistoryTable();
        win.setLayout(new BorderLayout());
        win.add(balance,BorderLayout.WEST);
        win.add(transfers,BorderLayout.EAST);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);
    }
}
