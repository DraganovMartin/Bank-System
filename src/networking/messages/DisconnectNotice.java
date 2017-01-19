package networking.messages;

/**
 * Sent exclusively by the server connection manager. Not intended to be
 * returned by the database as a result from transaction processing.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class DisconnectNotice extends Response {

    /**
     * Reference to the value of {@link Message#type} used by this class.
     */
    public static final String TYPE = "DISCONNECTNOTICE";

    public static final String CLOSEDBYSERVER = "Connection closed by the server!";

    public DisconnectNotice(String description) {
        super(DisconnectNotice.TYPE, description);
    }

    @Override
    public String toString() {
        return (DisconnectNotice.TYPE + ":\n"
                + "description: " + ((this.getDescription() == null) ? "null" : this.getDescription()) + "\n");
    }
}
