package dataModel;

import java.io.Serializable;

/**
 * Represents the abstraction of entities being identifiable by an unique
 * attribute or set of attributes in the case of a text-based storage -
 * {@link String}.
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class PrimaryKey implements Serializable, Comparable<PrimaryKey> {

    /**
     * The unique identifier.
     */
    private final String primaryKey;

    /**
     * Constructor.
     *
     * @param primaryKey the unique identifier value.
     */
    public PrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Returns the unique identifier.
     *
     * @return the unique identifier.
     */
    public final String getPrimaryKeyValue() {
        return this.primaryKey;
    }

    /**
     * Used to compare the values of two unique identifiers. Allows ordering
     * based on the primary key.
     *
     * @param other the identifier to compare to.
     *
     * @return as defined by {@link Comparable#compareTo(java.lang.Object)}.
     */
    @Override
    public final int compareTo(PrimaryKey other) {
        return (this.primaryKey).compareTo(other.primaryKey);
    }

    /**
     * Returns whether this identifier has the same value as another one.
     *
     * @param other the other identifier.
     *
     * @return true if equal, otherwise false.
     */
    public final boolean equals(PrimaryKey other) {
        return (this.primaryKey).equals(other.primaryKey);
    }
}
