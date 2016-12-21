package networking_final.messages;

import java.io.Serializable;

/**
 * {@link Message} is a {@link Serializable} class that serves the transmission
 * of data over the network. {@link Message} is an abstract base class that
 * holds a private field {@link Message#type}. This field is used to recognize
 * the specific derived class in order to be able to successfully typecast the
 * base serialized {@link Message} reference to its actual intended type.
 * {@link Message#type} is implemented as {@link String} so that the class is
 * extensible and forward-compatible. It is expected that within the same system
 * all different derived classes use different values for the
 * {@link Message#type} field. The field {@link Message#username} can be used
 * for setting or accessing either the sender or the receiver for the message.
 * The {@link Message#username} for a specific {@link Message} is intended to be
 * set and controlled by the server through authentication procedures once the
 * message enters the system's boundary. The value of the field corresponds to
 * the value of a specific record in the database (SystemProfiles.Username).
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Message implements Serializable {

    /**
     * The username.
     */
    private String username;

    /**
     * The {@link Message} type.
     */
    private final String type;

    /**
     * Constructor. The value of {@link Message#username} is set to null.
     *
     * @param type the {@link Message} type.
     */
    public Message(String type) {
        this.type = type;
        this.username = null;
    }

    /**
     * Returns the {@link Message} type.
     *
     * @return the {@link Message} type.
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Returns the username.
     *
     * @return the username.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     *
     * @param username the client ID.
     */
    public final void setUsername(String username) {
        this.username = username;
    }
}
