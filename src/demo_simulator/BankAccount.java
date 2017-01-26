package demo_simulator;

import dataModel.Money;

/**
 *
 * @author iliyan
 */
public class BankAccount {

    String id;
    String userName;
    String type;
    Money money;

    public BankAccount(
            String id,
            String userName,
            String type,
            Money money) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.money = money;
    }
}
