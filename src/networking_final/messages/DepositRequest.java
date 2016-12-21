package networking_final.messages;

import dataModel.Money;

/**
 * A {@link Message} used by the client to request a deposit to a specific bank
 * account. The user provides the ID of the bank account and the
 * {@link dataModel.Money} to deposit.
 * <p>
 * The provided {@link dataModel.Money} has to be converted to an equal amount
 * of {@link dataModel.Money} of currency matching the bank account currency
 * before being deposited!
 *
 * @see dataModel.Money
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class DepositRequest extends Message {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "DEPOSITREQUEST";

    /**
     * The ID of the bank to deposit to.
     */
    private final String toBankAccount;

    /**
     * The money to deposit.
     */
    private final Money money;

    /**
     * Constructor.
     *
     * @param toBankAccount the value for {@link #toBankAccount}.
     *
     * @param money the value for {@link #money}.
     */
    public DepositRequest(String toBankAccount, Money money) {
        super(DepositRequest.TYPE);
        this.toBankAccount = toBankAccount;
        this.money = money;
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
}
