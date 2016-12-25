package networking.messages;

import java.io.Serializable;

/**
 * A request {@link Message}.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public abstract class Request extends Message implements Serializable {

    /**
     * Constructor.
     *
     * @param type the value for {@link Message#type}.
     */
    public Request(String type) {
        super(type);
    }

    @Override
    public abstract String toString();
}
