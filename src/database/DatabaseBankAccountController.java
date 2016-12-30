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
    private PreparedStatement getAmount;
    private PreparedStatement addAmount;

    public DatabaseBankAccountController(){
        super();
        try {
            this.getBankAccoutType = connDatabase.prepareStatement("SELECT * FROM bankAccountType WHERE id = ?");
            this.getAmount = connDatabase.prepareStatement("SELECT amount FROM bankAccount WHERE id = ?");
            this.addAmount = connDatabase.prepareStatement("UPDATE bankAccount SET amount = ? WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean draw(int amount,int bankAccountId){
        try {
            connDatabase.setAutoCommit(false); // transaction starts
            this.getAmount.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmount.executeQuery();
            int currnetAmount = 0;
            if(amountSet.first()){
                currnetAmount = amountSet.getInt("amount");
                amountSet.close();
            }

            if(currnetAmount > amount){
                int newAmount = currnetAmount - amount;
                this.addAmount.setInt(1,newAmount);
                this.addAmount.setInt(2,bankAccountId);
                int result = this.addAmount.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId+".");
                }
            }
            else{
                return false;
            }
            connDatabase.commit(); // transaction ends
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean depositing(int amount,int bankAccountId){
        try {
            connDatabase.setAutoCommit(false);

            this.getAmount.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmount.executeQuery();
            int currnetAmount = 0;
            if(amountSet.first()){
                currnetAmount = amountSet.getInt("amount");
                amountSet.close();
            }
            int newAmount = currnetAmount + amount;
            this.addAmount.setInt(1,newAmount);
            this.addAmount.setInt(2,bankAccountId);
            int result = this.addAmount.executeUpdate();
            if(result != 1){
                throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId+".");
            }

            connDatabase.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean transfer(int amount,int bankAccountId1,int bankAccountId2){
        try {
            connDatabase.setAutoCommit(false);

            this.getAmount.setInt(1,bankAccountId1);
            ResultSet amountSet = this.getAmount.executeQuery();
            int currnetAmount1 = 0;
            if(amountSet.first()){
                currnetAmount1 = amountSet.getInt("amount");
            }
            this.getAmount.setInt(1,bankAccountId2);
            amountSet = this.getAmount.executeQuery();
            int currnetAmount2 = 0;
            if(amountSet.first()){
                currnetAmount2 = amountSet.getInt("amount");
            }

            if(currnetAmount1 > amount){
                int newAmount1 = currnetAmount1 - amount;
                this.addAmount.setInt(1,newAmount1);
                this.addAmount.setInt(2,bankAccountId1);
                int result = this.addAmount.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId1+".");
                }
                int newAmount2 = currnetAmount2 + amount;
                this.addAmount.setInt(1,newAmount2);
                this.addAmount.setInt(2,bankAccountId2);
                result = this.addAmount.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId2+".");
                }
            }
            else{
                return false;
            }

            connDatabase.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e){
            e.printStackTrace();;
            return false;
        }
        return true;
    }

    public void closeBankAccountController(){
        try {
            lastId.close();
            getBankAccoutType.close();
            getAmount.close();
            addAmount.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
