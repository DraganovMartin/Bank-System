package networking.messages.request;

import networking.messages.Message;
import networking.messages.Request;

import java.io.Serializable;

/**
 * A {@link Request} used by the client to request access to the system by
 * registering a new profile. The user requests the creation of a specific pair
 * of username and password for the new profile by providing first and last
 * name.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public final class RegisterRequest extends Request implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "REGISTERREQUEST";

    /**
     * The username requested by the user.
     */
    private final String registerUsername;

    /**
     * The password requested by the user.
     */
    private final String registerPassword;

    /**
     * The first name provided by the user.
     */
    private final String firstName;

    /**
     * The last name provided by the user.
     */
    private final String lastName;

    /**
     * Constructor.
     *
     * @param registerUsername the value for {@link #registerUsername}.
     *
     * @param registerPassword the value for {@link #registerPassword}.
     *
     * @param firstName the value for {@link #firstName}.
     *
     * @param lastName the value for {@link #lastName}.
     */
    public RegisterRequest(String registerUsername, String registerPassword, String firstName, String lastName) {
        super(RegisterRequest.TYPE);
        this.registerUsername = registerUsername;
        this.registerPassword = registerPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the value of {@link #registerUsername}.
     *
     * @return the value of {@link #registerUsername}.
     */
    public final String getRegisterUsername() {
        return this.registerUsername;
    }

    /**
     * Returns the value of {@link #registerPassword}.
     *
     * @return the value of {@link #registerPassword}.
     */
    public final String getRegisterPassword() {
        return this.registerPassword;
    }

    /**
     * Returns the value of {@link #firstName}.
     *
     * @return the value of {@link #firstName}.
     */
    public final String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the value of {@link #lastName}.
     *
     * @return the value of {@link #lastName}.
     */
    public final String getLastName() {
        return this.lastName;
    }

    @Override
    public String toString() {
        return (RegisterRequest.TYPE + ":\n"
                + "-----------------\n"
                + "username: " + this.getUsername() + "\n"
                + "registerUsername: " + this.getRegisterUsername() + "\n"
                + "registerPassword: " + this.getRegisterPassword() + "\n"
                + "firstName: " + this.getFirstName() + "\n"
                + "lastName: " + this.getLastName() + "\n"
                + "-----------------\n");
    }
}
