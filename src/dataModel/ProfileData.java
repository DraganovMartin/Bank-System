package dataModel;

import dataModel.ProfileData.Balance.SingleBankAccountBalance;
import dataModel.ProfileData.TransferHistory.SingleTransferHistory;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTable;

/**
 * A class that contains information that will be provided to the user upon
 * successful login or upon a synchronization request (i.e. balance check,
 * transfer history check, etc.).
 * <p>
 * Provides methods to create {@link JTable} objects based on the data stored:
 * <p>
 * {@link ProfileData#getBalanceTable()} - for balance.
 * <p>
 * {@link ProfileData#getTransferHistoryTable()} - for transfer history.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class ProfileData implements Serializable {

    /**
     * Contains a list of {@link SingleBankAccountBalance} objects.
     */
    public static class Balance implements java.io.Serializable {

        /**
         * Balance data about a single bank account according to database table
         * "BankAccounts".
         */
        public static class SingleBankAccountBalance implements java.io.Serializable {

            public final String bankAccountID;
            public final String type;
            public final String currencyID;
            public final String amount;

            /**
             * Constructor.
             * <p>
             * Parameters - according to database columns for table
             * "BankAccounts".
             *
             * @param bankAccountID
             * @param type
             * @param currencyID
             * @param amount
             */
            public SingleBankAccountBalance(String bankAccountID, String type, String currencyID, String amount) {
                this.bankAccountID = bankAccountID;
                this.type = type;
                this.currencyID = currencyID;
                this.amount = amount;
            }
        }

        /**
         * Contains a list of {@link SingleBankAccountBalance} objects.
         */
        public final Vector<SingleBankAccountBalance> entries;

        /**
         * Constructor. Creates a {@link Balance} object with an empty list. Add
         * entries using
         * {@link #add(dataModel.ProfileData.Balance.SingleBankAccountBalance)}.
         */
        public Balance() {
            this.entries = new Vector<>();
        }

        /**
         * Adds a {@link SingleBankAccountBalance} object to the balance list.
         *
         * @param singleBankAccountBalance the {@link SingleBankAccountBalance}
         * object to add to the balance list.
         *
         * @return true (as specified by
         * {@link Collection#add(java.lang.Object)}).
         */
        public boolean add(SingleBankAccountBalance singleBankAccountBalance) {
            return this.entries.add(singleBankAccountBalance);
        }

        /**
         * Adds a {@link SingleBankAccountBalance} object that is constructed
         * from the arguments provided to the balance list.
         * <p>
         * Parameters - according to database columns for table "BankAccounts".
         * <p>
         * @see
         * SingleBankAccountBalance#SingleBankAccountBalance(java.lang.String,
         * java.lang.String, java.lang.String, java.lang.String)}
         *
         * @param bankAccountID
         * @param type
         * @param currencyID
         * @param amount
         *
         * @return true (as specified by
         * {@link Collection#add(java.lang.Object)}).
         */
        public boolean add(String bankAccountID, String type, String currencyID, String amount) {
            return this.entries.add(new SingleBankAccountBalance(bankAccountID, type, currencyID, amount));
        }
    }

    /**
     * Contains a list of {@link SingleTransferHistory} objects.
     */
    public static class TransferHistory implements java.io.Serializable {

        /**
         * History data about a single transfer according to database table
         * "TransferHistory".
         */
        public static class SingleTransferHistory implements java.io.Serializable {

            public final String transferID;
            public final String fromBankAccountID;
            public final String toBankAccountID;
            public final String currencyID;
            public final String amount;
            public final String date;

            /**
             * Constructor.
             * <p>
             * Parameters - according to database columns for table
             * "TransferHistory".
             *
             * @param transferID
             * @param fromBankAccountID
             * @param toBankAccountID
             * @param currencyID
             * @param amount
             * @param date
             */
            public SingleTransferHistory(String transferID, String fromBankAccountID, String toBankAccountID, String currencyID, String amount, String date) {
                this.transferID = transferID;
                this.fromBankAccountID = fromBankAccountID;
                this.toBankAccountID = toBankAccountID;
                this.currencyID = currencyID;
                this.amount = amount;
                this.date = date;
            }
        }

        /**
         * Contains a list of {@link SingleTransferHistory} objects.
         */
        public final Vector<SingleTransferHistory> entries;

        /**
         * Constructor. Creates a {@link TransferHistory} object with an empty
         * list. Add entries using
         * {@link #add(dataModel.ProfileData.TransferHistory.SingleTransferHistory)}.
         */
        public TransferHistory() {
            this.entries = new Vector<>();
        }

        /**
         * Adds a {@link SingleTransferHistory} object to the history list.
         *
         * @param singleTransferHistory the {@link SingleTransferHistory} object
         * to add to the history list.
         *
         * @return true (as specified by
         * {@link Collection#add(java.lang.Object)}).
         */
        public boolean add(SingleTransferHistory singleTransferHistory) {
            return this.entries.add(singleTransferHistory);
        }

        /**
         * Adds a {@link SingleTransferHistory} object that is constructed from
         * the arguments provided to the history list.
         * <p>
         * Parameters - according to database columns for table
         * "TransferHistory".
         * <p>
         * @see SingleTransferHistory#SingleTransferHistory(java.lang.String,
         * java.lang.String, java.lang.String, java.lang.String,
         * java.lang.String, java.lang.String)}
         *
         * @param transferID
         * @param fromBankAccountID
         * @param toBankAccountID
         * @param currencyID
         * @param amount
         * @param date
         *
         * @return true (as specified by
         * {@link Collection#add(java.lang.Object)}).
         */
        public boolean add(String transferID, String fromBankAccountID, String toBankAccountID, String currencyID, String amount, String date) {
            return this.entries.add(new SingleTransferHistory(transferID, fromBankAccountID, toBankAccountID, currencyID, amount, date));
        }
    }

    public Balance balance;
    public TransferHistory transferHistory;
    public CurrencyConverter currencyConverter;

    public ProfileData(Balance balance, TransferHistory transferHistory, CurrencyConverter currencyConverter) {
        this.balance = balance;
        this.transferHistory = transferHistory;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     * Returns a {@link JTable} that represents the profile balance.
     *
     * @return a {@link JTable} that represents the profile balance, empty
     * {@link JTable} if empty.
     */
    public JTable getBalanceTable() {
        int rows = this.balance.entries.size();
        if (rows < 1) {
            return null;
        } else {
            String[] columnNames = new String[]{"Bank Account ID", "Bank Account Type", "Currency", "Amount"};
            String[][] rowData = new String[rows][];
            int i = 0;
            Iterator<SingleBankAccountBalance> it = this.balance.entries.iterator();
            while (it.hasNext()) {
                SingleBankAccountBalance current = it.next();
                rowData[i] = new String[4];
                rowData[i][0] = current.bankAccountID;
                rowData[i][1] = current.type;
                rowData[i][2] = current.currencyID;
                rowData[i][3] = current.amount;
                i++;
            }
            return new JTable(rowData, columnNames);
        }
    }

    /**
     * Returns a {@link JTable} that represents the profile transfer history.
     *
     * @return a {@link JTable} that represents the profile transfer history,
     * empty {@link JTable} if empty
     */
    public JTable getTransferHistoryTable() {
        int rows = this.transferHistory.entries.size();
        if (rows < 1) {
            return null;
        } else {
            String[] columnNames = new String[]{"Transfer ID", "From Bank Account", "To Bank Account", "Currency", "Amount", "Date"};
            String[][] rowData = new String[rows][];
            int i = 0;
            Iterator<TransferHistory.SingleTransferHistory> it = this.transferHistory.entries.iterator();
            while (it.hasNext()) {
                SingleTransferHistory current = it.next();
                rowData[i] = new String[6];
                rowData[i][0] = current.transferID;
                rowData[i][1] = current.fromBankAccountID;
                rowData[i][2] = current.toBankAccountID;
                rowData[i][3] = current.currencyID;
                rowData[i][4] = current.amount;
                rowData[i][5] = current.date;
                i++;
            }
            return new JTable(rowData, columnNames);
        }
    }

    /**
     * Returns the {@link CurrencyConverter}.
     *
     * @return the {@link CurrencyConverter}.
     */
    public CurrencyConverter getCurrencyConverter() {
        return this.currencyConverter;
    }

    /**
     * Returns the {@link Balance}.
     *
     * @return the {@link Balance}.
     */
    public Balance getBalance() {
        return this.balance;
    }

    /**
     * Returns the {@link TransferHistory}.
     *
     * @return the {@link TransferHistory}.
     */
    public TransferHistory getTransferHistory() {
        return this.transferHistory;
    }
}
