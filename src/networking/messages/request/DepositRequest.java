package networking.messages.request;

import dataModel.Money;
import networking.messages.Message;
import networking.messages.Request;

import java.io.Serializable;

/**
 * A {@link Request} used by the client to request the deposit of a specified
 * amount of money to a specified bank account. The user provides the ID of the
 * bank account and the {@link dataModel.Money} to deposit.
 * <p>
 * The provided {@link dataModel.Money} has to be converted to an equal amount
 * of {@link dataModel.Money} matching the currency of the target bank account
 * before being deposited!
 *
 * @see dataModel.Money
 * @see dataModel.CurrencyConverter
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public final class DepositRequest extends Request implements Serializable {

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

    @Override
    public String toString() {
        return (DepositRequest.TYPE + ":\n"
                + "-----------------\n"
                + "username: " + this.getUsername() + "\n"
                + "toBankAccount: " + this.getToBankAccount() + "\n"
                + "money: " + this.getMoney().toString() + "\n"
                + "-----------------\n");
    }
}
