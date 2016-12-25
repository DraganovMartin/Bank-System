package networking.messages;

import dataModel.ProfileData;
import java.io.Serializable;

/**
 * A response {@link Message}. The field {@link #profileData} contains
 * information that will be provided to the user upon successful login or upon a
 * synchronization request (i.e. balance check, transfer history check, etc.),
 * NULL if the response is not successful. The value of the {@link Message#type}
 * field of the response is set to the value of the
 * {@link Request} {@link Message#type} field.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public final class Response extends Message implements Serializable {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "RESPONSE";

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
     * An optional description of the response.
     */
    private final String description;

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
    public Response(Request request, boolean isSuccessful, ProfileData profileData, String description) {
        super(Response.TYPE);
        this.request = request;
        this.isSuccessful = isSuccessful;
        this.profileData = profileData;
        this.description = description;
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

    /**
     * Returns the value of {@link #description}.
     *
     * @return the value of {@link #description}.
     */
    public final String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return ("Response message:/n"
                + "-----------------/n"
                + ((this.request == null) ? "null" : this.request.toString())
                + "-----------------/n"
                + "isSuccessful: " + this.isSuccessful + "/n"
                + "-----------------/n"
                + ((this.profileData == null) ? "null" : this.profileData.toString())
                + "-----------------/n"
                + "description: " + ((this.description == null) ? "null" : this.description) + "/n");
    }
}
