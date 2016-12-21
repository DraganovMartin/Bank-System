package dataModel.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class Transfer implements Serializable {

    private int id;
    private double amount;
    private Date date;
    private BankAccount fromBankAccount;
    private BankAccount toBankAccount;
    private Currency currency;

    public Transfer(int id, double amount, Date date, BankAccount fromBankAccount, BankAccount toBankAccount, Currency currency) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.fromBankAccount = fromBankAccount;
        this.toBankAccount = toBankAccount;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public BankAccount getFromBankAccount() {
        return fromBankAccount;
    }

    public BankAccount getToBankAccount() {
        return toBankAccount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
