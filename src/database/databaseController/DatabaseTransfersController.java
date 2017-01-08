package database.databaseController;

import dataModel.Money;
import dataModel.models.Currency;
import dataModel.models.Transfer;

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
public class DatabaseTransfersController extends DatabaseController {
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

    public DatabaseTransfersController(){
        super();
        try{
            this.setStm = connDatabase.prepareStatement
                    ("INSERT INTO transfers(amount,fromBankAccount_id,toBankAccount_id,currency_id) VALUES (?,?,?,?)");
            this.getStm = connDatabase.prepareStatement
                    ("SELECT * FROM transfers WHERE fromBankAccount_id = ? OR toBankAccount_id = ? ORDER BY date DESC");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTransfer(Money amount,int fromBankAccount_id,int toBankAccount_id){
        try {
            this.setStm.setBigDecimal(1,amount.getAmount());
            this.setStm.setInt(2,fromBankAccount_id);
            this.setStm.setInt(3,toBankAccount_id);
            this.setStm.setString(4,amount.getCurrency().getSymbol());
            int result = this.setStm.executeUpdate();
            if(result != 1){
                throw new IllegalArgumentException("There is problem with the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transfer> getHistory(int bankAccountId){
        ResultSet set = null;
        List<Transfer> transfers = new ArrayList<Transfer>();
        try{
            this.getStm.setInt(1,bankAccountId);
            this.getStm.setInt(2,bankAccountId);
            set = this.getStm.executeQuery();
            while(set.next()){
                int id = set.getInt("id");
                String currencyId = set.getString("currency_id");
                Money money = Money.createMoney(new Currency(currencyId,currencyId),set.getString("amount"));
                Date date = set.getDate("date");
                int from = set.getInt("fromBankAccount_id");
                int to = set.getInt("toBankAccount_id");
                transfers.add(new Transfer(id,money,date,from,to));
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
