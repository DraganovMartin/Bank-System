package dataModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class Transfer implements Serializable {
    protected int id;
    protected int fromBankAccount;
    protected int toBankAccount;
    protected String currencyId;
    protected double amount;
    protected Date date;

    public Transfer(int id,int fromBankAccount,int toBankAccount,String currencyId,double amount,Date date){
        this.id = id;
        this.fromBankAccount = fromBankAccount;
        this.toBankAccount = toBankAccount;
        this.currencyId = currencyId;
        this.amount = amount;
        this.date = date;
    }

    public int getId(){return this.id;}
    public int getFromBankAccount(){return this.fromBankAccount;}
    public int getToBankAccount(){return this.toBankAccount;}
    public String getCurrencyId(){return this.currencyId;}
    public double getAmount(){return this.amount;}
    public Date getDate(){return this.date;}
}
