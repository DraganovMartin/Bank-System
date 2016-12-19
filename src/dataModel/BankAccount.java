package dataModel;

import java.io.Serializable;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class BankAccount implements Serializable {

    protected int id;
    protected String type;
    protected String currencyId;
    protected double amount;
    protected int clientId;

    public BankAccount(int id,String type,String currencyId,double amount,int clientId){
        this.id = id;
        this.type = type;
        this.currencyId = currencyId;
        this.amount = amount;
        this.clientId = clientId;
    }

    public int getId(){return this.id;}
    public String getType(){return this.type;}
    public String getCurrencyId(){return this.currencyId;}
    public double getAmount(){return this.amount;}
    public int getClientId(){return this.clientId;}

}
