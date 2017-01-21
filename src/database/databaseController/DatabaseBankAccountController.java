package database.databaseController;

import dataModel.CurrencyConverter;
import dataModel.Money;
import dataModel.models.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nikolay on 12/22/2016.
 */
public class DatabaseBankAccountController {
    private Connection connDatabase;

   //private PreparedStatement getBankAccoutType;
    private PreparedStatement getAmount;
    private PreparedStatement getAmountByUserName;
    private PreparedStatement addAmount;

    public DatabaseBankAccountController(Connection connDatabase){
        this.connDatabase = connDatabase;
        try {
            //this.getBankAccoutType = connDatabase.prepareStatement("SELECT * FROM bankAccountType WHERE id = ?");
            this.getAmount = connDatabase.prepareStatement("SELECT amount, currency_id FROM bankAccount WHERE id = ?");
            this.getAmountByUserName = connDatabase.prepareStatement
                    ("SELECT amount, currency_id FROM bankAccount, systemProfiles WHERE bankAccount.client_id = systemProfiles.client_id AND systemProfiles.userName = ?");
            this.addAmount = connDatabase.prepareStatement("UPDATE bankAccount SET amount = ? WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean draw(Money amount, int bankAccountId,CurrencyConverter converter){
        try {
            this.connDatabase.setAutoCommit(false); // transaction starts
            this.getAmount.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmount.executeQuery();
            Money currnetAmount = null;
            if(amountSet.first()){
                String currencyId = amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount = Money.createMoney(new Currency(currencyId),m);
                amountSet.close();
            }

            if(currnetAmount != null && currnetAmount.compareTo(amount,converter) == 1){
                currnetAmount.subtract(amount,converter);
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
            this.connDatabase.commit(); // transaction ends
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean depositing(Money amount, int bankAccountId, CurrencyConverter converter){
        try {
            this.connDatabase.setAutoCommit(false);

            this.getAmount.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmount.executeQuery();
            Money currnetAmount = null;
            if(amountSet.first()){
                String m = amountSet.getString("amount");
                String currencyId = amountSet.getString("currency_id");
                currnetAmount = Money.createMoney(new Currency(currencyId),m);
                amountSet.close();
            } else{
                return false;
            }
            currnetAmount.add(amount,converter);
            this.addAmount.setBigDecimal(1,currnetAmount.getAmount());
            this.addAmount.setInt(2,bankAccountId);
            int result = this.addAmount.executeUpdate();
            if(result != 1){
                throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId+".");
            }

            this.connDatabase.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean transfer(Money amount,int bankAccountId1,int bankAccountId2,CurrencyConverter converter){
        try {
            this.connDatabase.setAutoCommit(false);

            this.getAmount.setInt(1,bankAccountId1);
            ResultSet amountSet = this.getAmount.executeQuery();
            Money currnetAmount1 = null;
            if(amountSet.first()){
                String currencyId =  amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount1 = Money.createMoney(new Currency(currencyId),m);
            } else{
                return false;
            }
            this.getAmount.setInt(1,bankAccountId2);
            amountSet = this.getAmount.executeQuery();
            Money currnetAmount2 = null;
            if(amountSet.first()){
                String currencyId =  amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount2 = Money.createMoney(new Currency(currencyId),m);
            } else{
                return false;
            }

            if(currnetAmount1.compareTo(amount,converter) == 1){
                currnetAmount1.subtract(amount,converter);
                this.addAmount.setBigDecimal(1,currnetAmount1.getAmount());
                this.addAmount.setInt(2,bankAccountId1);
                int result = this.addAmount.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId1+".");
                }
                currnetAmount2.add(amount,converter);
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

            this.connDatabase.commit();
            this.connDatabase.setAutoCommit(true);
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
                return Money.createMoney(new Currency(currency),money);
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

    public Money getAmount(String username,CurrencyConverter converter){
        ResultSet set = null;
        Money m = null;
        try {
            this.getAmountByUserName.setString(1,username);
            set = this.getAmountByUserName.executeQuery();
            if(set.first()){
                String currency = set.getString("currency_id");
                String money = set.getString("amount");
                m = Money.createMoney(new Currency(currency),money);
            }
            while(set.next()){
                String currency = set.getString("currency_id");
                String money = set.getString("amount");
                m.add(Money.createMoney(new Currency(currency),money),converter);
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
        return m;
    }
    public void close(){
        try{
            this.getAmount.close();
            this.getAmountByUserName.close();
            this.addAmount.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
