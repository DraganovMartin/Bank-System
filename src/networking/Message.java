package networking;

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
 * {@link Message#type} field.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Message implements Serializable {

    private final String type;

    public Message(String type) {
        this.type = type;
    }

    public final String getType() {
        return this.type;
    }
}
