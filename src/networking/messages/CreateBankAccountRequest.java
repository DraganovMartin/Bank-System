package networking.messages;

import dataModel.Money;
import java.io.Serializable;

/**
 * A {@link Request} used by the client to request creating a new bank account
 * under the ownership of the profile currently logged in the system. The user
 * provides the requested type of bank account and the initial
 * {@link dataModel.Money} to deposit.
 * <p>
 * The currency of the initial money determines the currency of the bank
 * account!
 *
 * @see dataModel.Money
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class CreateBankAccountRequest extends Request implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "CREATEBANKACCOUNTREQUEST";

    /**
     * The type of bank account requested by the user.
     */
    private final String bankAccountType;

    /**
     * The initial money to deposit. Determines the currency for the bank
     * account.
     */
    private final Money initialMoney;

    /**
     * Constructor.
     *
     * @param bankAccountType the value for {@link #bankAccountType}.
     *
     * @param initialMoney the value for {@link #initialMoney}.
     */
    public CreateBankAccountRequest(String bankAccountType, Money initialMoney) {
        super(CreateBankAccountRequest.TYPE);
        this.bankAccountType = bankAccountType;
        this.initialMoney = initialMoney;
    }

    /**
     * Returns the value of {@link #bankAccountType}.
     *
     * @return the value of {@link #bankAccountType}.
     */
    public final String getBankAccountType() {
        return this.bankAccountType;
    }

    /**
     * Returns the value of {@link #initialMoney}.
     *
     * @return the value of {@link #initialMoney}.
     */
    public final Money getInitialMoney() {
        return this.initialMoney;
    }

    @Override
    public String toString() {
        return (CreateBankAccountRequest.TYPE + ":/n"
                + "-----------------/n"
                + "username: " + this.getUsername() + "/n"
                + "bankAccountType: " + this.getBankAccountType() + "/n"
                + "initialMoney: " + this.getInitialMoney().toString() + "/n"
                + "-----------------/n");
    }
}
