package database.databaseController;

import dataModel.Money;
import dataModel.ProfileData;
import dataModel.models.Currency;
import dataModel.models.Transfer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikolay on 1/2/2017.
 * This class is responsible for controlling the transfers connection with the database.
 */
public class DatabaseTransfersController  {
    private Connection connDatabase;
    /**
     * Statement for set a transfer.
     * It takes amount, bank account id form which you took money,
     * bank account id on which you add money and currency.
     */
    private PreparedStatement setStm;
    /**
     * Statement for get transfers.
     * It takes only bank account id which we want.
     */
    private PreparedStatement getStm;

    public DatabaseTransfersController(Connection connDatabase){
        this.connDatabase = connDatabase;
        try{
            this.setStm = this.connDatabase.prepareStatement
                    ("INSERT INTO transfers(amount,fromBankAccount_id,toBankAccount_id,currency_id) VALUES (?,?,?,?)");
            this.getStm = this.connDatabase.prepareStatement
                    ("SELECT transfers.id,fromBankAccount_id,toBankAccount_id,transfers.currency_id,transfers.amount,transfers.date " +
                            "FROM systemprofiles,bankaccount,transfers WHERE " +
                            "systemprofiles.userName = bankAccount.userName AND " +
                            "(bankAccount.id = transfers.fromBankAccount_id OR bankAccount.id = transfers.toBankAccount_id) AND " +
                            "systemprofiles.userName = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean setTransfer(Money amount,int fromBankAccount_id,int toBankAccount_id){
        try {
            this.setStm.setBigDecimal(1,amount.getAmount());
            this.setStm.setInt(2,fromBankAccount_id);
            this.setStm.setInt(3,toBankAccount_id);
            this.setStm.setString(4,amount.getCurrency().getSymbol());
            int result = this.setStm.executeUpdate();
            if(result == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ProfileData.TransferHistory getHistory(String username){
        ResultSet set = null;
        ProfileData.TransferHistory transfers = new ProfileData.TransferHistory();
        try{
            this.getStm.setString(1,username);
            set = this.getStm.executeQuery();
            while(set.next()){
                String id = set.getString("id");
                String currencyId = set.getString("currency_id");
                String amount = set.getString("amount");
                Date date = set.getDate("date");
                String from = set.getString("fromBankAccount_id");
                String to = set.getString("toBankAccount_id");
                ProfileData.TransferHistory.SingleTransferHistory lineHistory =
                        new ProfileData.TransferHistory.SingleTransferHistory(id,from,to,currencyId,amount,date.toString());
                transfers.add(lineHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if(set != null){
                    set.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transfers;
    }

    public void closeTransferContoller(){
        try{
            if(this.setStm != null){
                this.setStm.close();
            }
            if(this.getStm != null){
                this.getStm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
