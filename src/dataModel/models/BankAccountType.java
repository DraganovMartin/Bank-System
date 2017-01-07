package dataModel.models;

import dataModel.Money;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class BankAccountType implements Serializable {

    private String id;
    /**
     * The currency is aways in BGN
     */
    private Money overdraft;
    private Money intresteRate;
    private Money tax;

    public BankAccountType(String id, Money overdraft) {
        this.id = id;
        this.overdraft = overdraft;
    }

    public String getId() {
        return id;
    }

    public Money getOverdraft() {
        return overdraft;
    }

    public Money getIntresteRate() {
        return intresteRate;
    }

    public Money getTax() {
        return tax;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOverdraft(Money overdraft) {
        this.overdraft = overdraft;
    }

    public void setIntresteRate(Money intresteRate) {
        this.intresteRate = intresteRate;
    }

    public void setTax(Money tax) {
        this.tax = tax;
    }
}
