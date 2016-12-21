package networking_final.messages;

import dataModel.ProfileData;
import java.io.Serializable;

/**
 * A response {@link Message}. The field {@link #profileData} contains
 * information that will be provided to the user upon successful login or upon a
 * synchronization request (i.e. balance check, transfer history check, etc.),
 * NULL if the response is not successful.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Response extends Message implements Serializable {

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
     * @param type the value for {@link Message#type}.
     *
     * @param request the value for {@link #request}.
     *
     * @param isSuccessful the value for {@link #isSuccessful}.
     *
     * @param profileData the value for {@link #profileData}.
     *
     * @param description the value for {@link #description}.
     */
    public Response(String type, Request request, boolean isSuccessful, ProfileData profileData, String description) {
        super(type);
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
}
