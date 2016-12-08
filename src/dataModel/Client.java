package dataModel;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Client extends PrimaryKey implements Serializable {

    public Client(String primaryKey) {
        super(primaryKey);
    }
}
