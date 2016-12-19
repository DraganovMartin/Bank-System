package dataModel.models;

import dataModel.PrimaryKey;

import java.io.Serializable;

/**
 * Represents a client that has a {@link PrimaryKey} as unique identifier.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Client implements Serializable {

    protected int id;
    /**
     * The client first name.
     */
    protected String firstName;

    /**
     * The client last name.
     */
    protected String lastName;

    /**
     * Constructor.
     *
     * @param id the unique identifier value.
     *
     * @param firstName the client first name.
     *
     * @param lastName the client last name.
     */
    public Client(int id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the client first name.
     *
     * @return the client first name.
     */
    public final String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the client last name.
     *
     * @return the client last name.
     */
    public final String getLastName() {
        return this.lastName;
    }
}
