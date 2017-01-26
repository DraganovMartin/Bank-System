package demo_simulator;

import java.util.TreeMap;

/**
 *
 * @author iliyan
 */
class BankAccountsTable {

    TreeMap<String, BankAccount> data;

    BankAccountsTable() {
        this.data = new TreeMap<>();
    }

    boolean add(BankAccount bankAccount) {
        if (this.data.get(bankAccount.id) != null) {
            return false;
        } else {
            this.data.put(bankAccount.id, bankAccount);
            return true;
        }
    }

    BankAccount get(String id) {
        return this.data.get(id);
    }
}
