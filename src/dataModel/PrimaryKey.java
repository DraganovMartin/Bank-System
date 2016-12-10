package dataModel;

import java.io.Serializable;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class PrimaryKey implements Serializable {

    private final String primaryKey;

    public PrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public final String getPrimaryKeyValue() {
        return this.primaryKey;
    }
}
