package dataModel.models;

import java.io.Serializable;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class BankAccount implements Serializable {

    private int id;
    private double amount;
    private Client client;
    private Currency currency;
    private BankAccountType type;

    public BankAccount(int id,double amount,Client client,Currency currency,BankAccountType type){
        this.id = id;
        this.amount = amount;
        this.client = client;
        this.currency = currency;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Client getClient() {
        return client;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BankAccountType getType() {
        return type;
    }
}
