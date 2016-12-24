package networking.messages;

import java.io.Serializable;

/**
 * A {@link Request} used by the client to request a change of the password of
 * the profile currently logged in the system. The user provides a pair of the
 * old and the newly requested password.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class ChangePasswordRequest extends Request implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "CHANGEPASSWORDREQUEST";

    /**
     * The old password provided by the user.
     */
    private final String oldPassword;

    /**
     * The new password requested by the user.
     */
    private final String newPassword;

    /**
     * Constructor.
     *
     * @param oldPassword the value for {@link #oldPassword}.
     *
     * @param newPassword the value for {@link #newPassword}.
     */
    public ChangePasswordRequest(String oldPassword, String newPassword) {
        super(ChangePasswordRequest.TYPE);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    /**
     * Returns the value of {@link #oldPassword}.
     *
     * @return the value of {@link #oldPassword}.
     */
    public final String getOldPassword() {
        return this.oldPassword;
    }

    /**
     * Returns the value of {@link #newPassword}.
     *
     * @return the value of {@link #newPassword}.
     */
    public final String getNewPassword() {
        return this.newPassword;
    }

    @Override
    public String toString() {
        return (ChangePasswordRequest.TYPE + ":/n"
                + "-----------------/n"
                + "username: " + this.getUsername() + "/n"
                + "old password: " + this.getOldPassword() + "/n"
                + "new password: " + this.getNewPassword() + "/n"
                + "-----------------/n");
    }
}
