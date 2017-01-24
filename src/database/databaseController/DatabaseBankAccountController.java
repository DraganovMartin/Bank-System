package database.databaseController;

import dataModel.CurrencyConverter;
import dataModel.Money;
import dataModel.ProfileData;
import dataModel.models.BankAccountType;
import dataModel.models.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikolay on 12/22/2016.
 */
public class DatabaseBankAccountController {
    private Connection connDatabase;

    private DatabaseTransfersController transfersController;

    private PreparedStatement getBankAccountTypeStatment;
    private PreparedStatement getAmountStatment;
    private PreparedStatement getAmountByUserNameStatment;
    private PreparedStatement addAmountStatment;
    private PreparedStatement getAccountsStatemt;
    private PreparedStatement setBankAccountStatment;

    private PreparedStatement lastIdStatment;

    public DatabaseBankAccountController(Connection connDatabase,DatabaseTransfersController databaseTransfersController){
        this.connDatabase = connDatabase;
        this.transfersController = databaseTransfersController;
        try {
            //this.getBankAccountTypeStatment = connDatabase.prepareStatement("SELECT * FROM bankAccountType WHERE id = ?");
            this.getAmountStatment = this.connDatabase.prepareStatement("SELECT amount, currency_id FROM bankAccount WHERE id = ?");
            this.getAmountByUserNameStatment = this.connDatabase.prepareStatement
                    ("SELECT amount, currency_id FROM bankAccount, systemProfiles WHERE bankAccount.client_id = systemProfiles.client_id AND systemProfiles.userName = ?");
            this.addAmountStatment = this.connDatabase.prepareStatement("UPDATE bankAccount SET amount = ? WHERE id = ?");
            this.getAccountsStatemt = connDatabase.prepareStatement("SELECT * FROM systemprofiles,bankAccount " +
                    "WHERE systemprofiles.userName = bankaccount.userName AND systemprofiles.userName = ?");
            this.setBankAccountStatment = connDatabase.prepareStatement("INSERT INTO bankAccount(amount,currency_id,type_id,userName)" +
                    " VALUES(?,?,?,?)");
            this.lastIdStatment = this.connDatabase.prepareStatement("SELECT LAST_INSERT_ID()");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean draw(Money amount, int bankAccountId,CurrencyConverter converter){
        try {
            this.connDatabase.setAutoCommit(false); // transaction starts
            this.getAmountStatment.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmountStatment.executeQuery();
            Money currnetAmount = null;
            if(amountSet.first()){
                String currencyId = amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount = Money.createMoney(new Currency(currencyId),m);
                amountSet.close();
            }

            if(currnetAmount != null && currnetAmount.compareTo(amount,converter) == 1){
                currnetAmount = currnetAmount.subtract(amount,converter);
                this.addAmountStatment.setBigDecimal(1,currnetAmount.getAmount());
                this.addAmountStatment.setInt(2,bankAccountId);
                int result = this.addAmountStatment.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId+".");
                }
            }
            else{
                return false;
            }
            Money money = Money.createMoney(currnetAmount.getCurrency(),"-"+amount.getAmount().toPlainString());
            this.transfersController.setTransfer(money,bankAccountId,bankAccountId);
            this.connDatabase.commit(); // transaction ends
            this.connDatabase.setAutoCommit(true);
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

            this.getAmountStatment.setInt(1,bankAccountId);
            ResultSet amountSet = this.getAmountStatment.executeQuery();
            Money currnetAmount = null;
            if(amountSet.first()){
                String m = amountSet.getString("amount");
                String currencyId = amountSet.getString("currency_id");
                currnetAmount = Money.createMoney(new Currency(currencyId),m);
                amountSet.close();
            } else{
                return false;
            }
            currnetAmount = currnetAmount.add(amount,converter);
            this.addAmountStatment.setBigDecimal(1,currnetAmount.getAmount());
            this.addAmountStatment.setInt(2,bankAccountId);
            int result = this.addAmountStatment.executeUpdate();
            if(result != 1){
                throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId+".");
            }

            this.transfersController.setTransfer(amount,bankAccountId,bankAccountId);
            this.connDatabase.commit();
            this.connDatabase.setAutoCommit(true);
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

            this.getAmountStatment.setInt(1,bankAccountId1);
            ResultSet amountSet = this.getAmountStatment.executeQuery();
            Money currnetAmount1 = null;
            if(amountSet.first()){
                String currencyId =  amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount1 = Money.createMoney(new Currency(currencyId),m);
            } else{
                return false;
            }
            this.getAmountStatment.setInt(1,bankAccountId2);
            amountSet = this.getAmountStatment.executeQuery();
            Money currnetAmount2 = null;
            if(amountSet.first()){
                String currencyId =  amountSet.getString("currency_id");
                String m = amountSet.getString("amount");
                currnetAmount2 = Money.createMoney(new Currency(currencyId),m);
            } else{
                return false;
            }

            if(currnetAmount1.compareTo(amount,converter) == 1){
                currnetAmount1 = currnetAmount1.subtract(amount,converter);
                this.addAmountStatment.setBigDecimal(1,currnetAmount1.getAmount());
                this.addAmountStatment.setInt(2,bankAccountId1);
                int result = this.addAmountStatment.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId1+".");
                }
                currnetAmount2 = currnetAmount2.add(amount,converter);
                this.addAmountStatment.setBigDecimal(1,currnetAmount2.getAmount());
                this.addAmountStatment.setInt(2,bankAccountId2);
                result = this.addAmountStatment.executeUpdate();
                if(result != 1){
                    throw new IllegalArgumentException("There is problem with the bank account id ="+bankAccountId2+".");
                }
            }
            else{
                return false;
            }
            amountSet.close();

            this.transfersController.setTransfer(amount,bankAccountId1,bankAccountId2);
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
            this.getAmountStatment.setInt(1,bankAccountId);
            set = this.getAmountStatment.executeQuery();
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
            this.getAmountByUserNameStatment.setString(1,username);
            set = this.getAmountByUserNameStatment.executeQuery();
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

    /**
     * Get all bankAccounts
     * @param username
     * @return List<Integer> bankAccounts id
     */
    public ProfileData.Balance getBankAccounts(String username){
        ProfileData.Balance balance = new ProfileData.Balance();
        try{
            this.getAccountsStatemt.setString(1,username);
            ResultSet set = this.getAccountsStatemt.executeQuery();
            while(set.next()){
                int id = set.getInt("id");
                String type = set.getString("type_id");
                String currency = set.getString("currency_id");
                String amount = set.getString("amount");
                ProfileData.Balance.SingleBankAccountBalance balanceLine =
                        new ProfileData.Balance.SingleBankAccountBalance(id+"",type,currency,amount);
                balance.add(balanceLine);
            }
            if(set != null){
                set.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public int setBankAccount(String userName,Money amount,String bankAccountType){
        try{
            this.setBankAccountStatment.setBigDecimal(1,amount.getAmount());
            this.setBankAccountStatment.setString(2,amount.getCurrency().getSymbol());
            this.setBankAccountStatment.setString(3, bankAccountType);
            this.setBankAccountStatment.setString(4,userName);
            int result = this.setBankAccountStatment.executeUpdate();
            if(result == 1){
                ResultSet set = this.lastIdStatment.executeQuery();
                set.first();
                int id = set.getInt(1);
                set.close();
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void close(){
        try{
            this.getAmountStatment.close();
            this.getAmountByUserNameStatment.close();
            this.lastIdStatment.close();
            this.setBankAccountStatment.close();
            this.getAccountsStatemt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
