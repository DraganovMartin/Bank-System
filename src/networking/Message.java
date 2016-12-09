package networking;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Message implements Serializable {

    public enum TYPE {
        ANY
        // TBA
    };

    private final TYPE type;

    public Message(TYPE type) {
        this.type = type;
    }

    public final TYPE getType() {
        return this.type;
    }
}
