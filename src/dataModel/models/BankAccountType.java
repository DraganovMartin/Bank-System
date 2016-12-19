package dataModel.models;

import java.io.Serializable;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class BankAccountType implements Serializable {

    private String id;
    private double overdraft;
    private double intresteRate;
    private double tax;

    public BankAccountType(String id, double overdraft) {
        this.id = id;
        this.overdraft = overdraft;
    }

    public String getId() {
        return id;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public double getIntresteRate() {
        return intresteRate;
    }

    public double getTax() {
        return tax;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    public void setIntresteRate(double intresteRate) {
        this.intresteRate = intresteRate;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
