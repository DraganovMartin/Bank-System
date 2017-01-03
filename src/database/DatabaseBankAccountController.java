package database;

import dataModel.Money;
import dataModel.models.BankAccountType;
import dataModel.models.Currency;

import java.math.BigDecimal;
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
            this.getAmount = connDatabase.prepareStatement("SELECT amount, currency_id FROM bankAccount WHERE id = ?");
            this.addAmount = connDatabase.prepareStatement("UPDATE bankAccount SET amount = ? WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean draw(Money amount, int bankAccountId){
        try {
            connDatabase.setAutoCommit(false); // transaction starts
            this.getAmount.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmount.executeQuery();
            Money currnetAmount = null;
            if(amountSet.first()){
                String currencyId = amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount = Money.createMoney(new Currency(currencyId,currencyId),m);
                amountSet.close();
            }

            if(currnetAmount != null && currnetAmount.compareTo(amount) == 1){
                currnetAmount.substract(amount);
                this.addAmount.setBigDecimal(1,currnetAmount.getAmount());
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

    public boolean depositing(Money amount,int bankAccountId){
        try {
            connDatabase.setAutoCommit(false);

            this.getAmount.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmount.executeQuery();
            Money currnetAmount = null;
            if(amountSet.first()){
                String m = amountSet.getString("amount");
                String currencyId = amountSet.getString("currency_id");
                currnetAmount = Money.createMoney(new Currency(currencyId,currencyId),m);
                amountSet.close();
            } else{
                return false;
            }
            currnetAmount.add(amount);
            this.addAmount.setBigDecimal(1,currnetAmount.getAmount());
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
    public boolean transfer(Money amount,int bankAccountId1,int bankAccountId2){
        try {
            connDatabase.setAutoCommit(false);

            this.getAmount.setInt(1,bankAccountId1);
            ResultSet amountSet = this.getAmount.executeQuery();
            Money currnetAmount1 = null;
            if(amountSet.first()){
                String currencyId =  amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount1 = Money.createMoney(new Currency(currencyId,currencyId),m);
            } else{
                return false;
            }
            this.getAmount.setInt(1,bankAccountId2);
            amountSet = this.getAmount.executeQuery();
            Money currnetAmount2 = null;
            if(amountSet.first()){
                String currencyId =  amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount2 = Money.createMoney(new Currency(currencyId,currencyId),m);
            } else{
                return false;
            }

            if(currnetAmount1.compareTo(amount) == 1){
                currnetAmount1.substract(amount);
                this.addAmount.setBigDecimal(1,currnetAmount1.getAmount());
                this.addAmount.setInt(2,bankAccountId1);
                int result = this.addAmount.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId1+".");
                }
                currnetAmount2.add(amount);
                this.addAmount.setBigDecimal(1,currnetAmount2.getAmount());
                this.addAmount.setInt(2,bankAccountId2);
                result = this.addAmount.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId2+".");
                }
            }
            else{
                return false;
            }
            amountSet.close();

            connDatabase.commit();
            connDatabase.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e){
            e.printStackTrace();;
            return false;
        }
        return true;
    }

    public Money getAmount(int bankAccountId){
        ResultSet set = null;
        try {
            this.getAmount.setInt(1,bankAccountId);
            set = this.getAmount.executeQuery();
            if(set.first()){
                String currency = set.getString("currency_id");
                String money = set.getString("amount");
                return Money.createMoney(new Currency(currency,currency),money);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
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
