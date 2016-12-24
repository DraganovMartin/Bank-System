package database;

import dataModel.Money;
import dataModel.models.BankAccountType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nikolay on 12/22/2016.
 */
public class DatabaseBankAccountController extends DatabaseController {
    private PreparedStatement getBankAccoutType;

    public DatabaseBankAccountController(){
        super();
        try {
            this.getBankAccoutType = connDatabase.prepareStatement("SELECT * FROM bankAccountType WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeBankAccountController(){
        try {
            lastId.close();
            getBankAccoutType.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
