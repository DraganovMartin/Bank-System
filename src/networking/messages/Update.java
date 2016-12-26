package networking.messages;

import dataModel.ProfileData;
import java.io.Serializable;

/**
 * A response {@link Message} that is used to transmit data about the user over
 * the network - from the server to the client. The field {@link #profileData}
 * contains information that will be provided to the user upon successful login
 * or upon a synchronization request (i.e. balance check, transfer history
 * check, etc.), NULL if the response is not successful. The value of the
 * {@link Message#type} field of the response is set to the value of the
 * {@link Request} {@link Message#type} field.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class Update extends Response implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "UPDATE";

    /**
     * The {@link Request} that is being responded to.
     */
    private final Request request;

    /**
     * Whether the requested operation(s) were successful.
     */
    private final boolean isSuccessful;

    /**
     * A container for information that will be provided to the user upon
     * successful login or upon a synchronization request (i.e. balance check,
     * transfer history check, etc.), NULL if the response is not successful.
     */
    private final ProfileData profileData;

    /**
     * Constructor.
     *
     * @param request the value for {@link #request}.
     *
     * @param isSuccessful the value for {@link #isSuccessful}.
     *
     * @param profileData the value for {@link #profileData}.
     *
     * @param description the value for {@link #description}.
     */
    public Update(String description, Request request, boolean isSuccessful, ProfileData profileData) {
        super(Update.TYPE, description);
        this.request = request;
        this.isSuccessful = isSuccessful;
        this.profileData = profileData;
    }

    /**
     * Returns the value of {@link #request}.
     *
     * @return the value of {@link #request}.
     */
    public final Request getRequest() {
        return this.request;
    }

    /**
     * Returns the value of {@link #isSuccessful}.
     *
     * @return the value of {@link #isSuccessful}.
     */
    public final boolean isSuccessful() {
        return this.isSuccessful;
    }

    /**
     * Returns the value of {@link #profileData}.
     *
     * @return the value of {@link #profileData}.
     */
    public final ProfileData getProflieData() {
        return this.profileData;
    }

    @Override
    public String toString() {
        return (Update.TYPE + ":\n"
                + "-----------------\n"
                + ((this.request == null) ? "null" : this.request.toString())
                + "-----------------\n"
                + "isSuccessful: " + this.isSuccessful + "\n"
                + "-----------------\n"
                + ((this.profileData == null) ? "null" : this.profileData.toString())
                + "-----------------\n"
                + "description: " + ((this.getDescription() == null) ? "null" : this.getDescription()) + "\n");
    }
}
