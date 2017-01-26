package demo_simulator;

import dataModel.Money;

/**
 *
 * @author iliyan
 */
public class Transfer {

    String id;
    String fromBankAcount;
    String toBankAccount;
    Money money;
    String date;

    public Transfer(
            String id,
            String fromBankAcount,
            String toBankAccount,
            Money money,
            String date) {
        this.id = id;
        this.fromBankAcount = fromBankAcount;
        this.toBankAccount = toBankAccount;
        this.money = money;
        this.date = date;
    }
}
