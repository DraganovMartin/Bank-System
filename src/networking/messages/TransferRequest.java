package networking.messages;

import dataModel.Money;
import java.io.Serializable;

/**
 * A {@link Request} used by the client to request the transfer of a specified
 * amount of money from a specified bank account to a specified bank account.
 * The user provides the IDs of the bank accounts and the
 * {@link dataModel.Money} to withdraw. The operation consists of two steps:
 * <p>
 * - withdrawing - the provided {@link dataModel.Money} has to be converted to
 * an equal amount of {@link dataModel.Money} matching the currency of the
 * source bank account before being withdrawn!
 * <p>
 * - depositing - the provided {@link dataModel.Money} has to be converted to an
 * equal amount of {@link dataModel.Money} matching the currency of the target
 * bank account before being deposited!
 *
 * @see dataModel.Money
 * @see dataModel.CurrencyConverter
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class TransferRequest extends Request implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "TRANSFERREQUEST";

    /**
     * The ID of the bank to withdraw from.
     */
    private final String fromBankAccount;

    /**
     * The ID of the bank to deposit to.
     */
    private final String toBankAccount;

    /**
     * The money to transfer.
     */
    private final Money money;

    /**
     * Constructor.
     *
     * @param fromBankAccount the value for {@link #fromBankAccount}.
     *
     * @param toBankAccount the value for {@link #toBankAccount}.
     *
     * @param money the value for {@link #money}.
     */
    public TransferRequest(String fromBankAccount, String toBankAccount, Money money) {
        super(TransferRequest.TYPE);
        this.fromBankAccount = fromBankAccount;
        this.toBankAccount = toBankAccount;
        this.money = money;
    }

    /**
     * Returns the value of {@link #fromBankAccount}.
     *
     * @return the value of {@link #fromBankAccount}.
     */
    public final String getFromBankAccount() {
        return this.fromBankAccount;
    }

    /**
     * Returns the value of {@link #toBankAccount}.
     *
     * @return the value of {@link #toBankAccount}.
     */
    public final String getToBankAccount() {
        return this.toBankAccount;
    }

    /**
     * Returns the value of {@link #money}.
     *
     * @return the value of {@link #money}.
     */
    public final Money getMoney() {
        return this.money;
    }

    @Override
    public String toString() {
        return (TransferRequest.TYPE + ":/n"
                + "-----------------/n"
                + "username: " + this.getUsername() + "/n"
                + "frombankAccount: " + this.getFromBankAccount() + "/n"
                + "toBankAccount: " + this.getToBankAccount() + "/n"
                + "money: " + this.getMoney().toString() + "/n"
                + "-----------------/n");
    }
}
