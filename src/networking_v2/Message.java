package networking_v2;

import java.io.Serializable;

/**
 * A class used for communication and transmitting data over the network.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Message implements Serializable {

    /**
     * The type of the message. Used to recognize objects of different derived
     * classes when reading them from input streams as serialized objects.
     */
    public final String type;

    /**
     * The username (that sent or requested the message) - within the system is
     * verified and controlled by the server!!!
     */
    public String username;

    /**
     * Returns the value of {@link #type}.
     *
     * @return the value of {@link #type}.
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Returns the value of {@link #username}.
     *
     * @return the value of {@link #username}.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Constructor.
     *
     * @param type the value for {@link #type}.
     */
    public Message(String type) {
        this.type = type;
        this.username = null;
    }
}
