package database;

import dataModel.Money;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Nikolay on 1/2/2017.
 */
public class DatabaseTransfersController extends DatabaseController {

    private PreparedStatement setStm;

    public DatabaseTransfersController(){
        super();
        try{
            this.setStm = connDatabase.prepareStatement
                    ("INSERT INTO transfers(amount,fromBankAccount_id,toBankAccount_id,currency_id) VALUES (?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTransfer(Money amount,int fromBankAccount_id,int toBankAccount_id){
        try {
            this.setStm.setBigDecimal(1,amount.getAmount());
            this.setStm.setInt(2,fromBankAccount_id);
            this.setStm.setInt(3,toBankAccount_id);
            this.setStm.setString(4,amount.getCurrency().getPrimaryKeyValue());
            int result = this.setStm.executeUpdate();
            if(result != 1){
                throw new IllegalArgumentException("There is problem with the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
