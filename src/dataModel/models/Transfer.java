package dataModel.models;

import dataModel.Money;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class Transfer implements Serializable {

    private int id;
    private Money amount;
    private Date date;
    private int fromBankAccount;
    private int toBankAccount;
    private Currency currency;

    public Transfer(int id, Money amount, Date date, int fromBankAccount, int toBankAccount) {
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

    public Money getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public int getFromBankAccount() {
        return fromBankAccount;
    }

    public int getToBankAccount() {
        return toBankAccount;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString(){
        return this.amount.getAmount() + " " + this.amount.getCurrency().getSymbol()+" from: "+
                this.fromBankAccount + " to: " + this.toBankAccount + " on: "+this.date.toString();
    }
}
