package dataModel.models;

import java.io.Serializable;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class SystemProfile implements Serializable {
    private String userName;
    private String password;
    private Client client;
    private SystemProfileType type;

    public SystemProfile(String userName, String password, Client client, SystemProfileType type) {
        this.userName = userName;
        this.password = password;
        this.client = client;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Client getClient() {
        return client;
    }

    public SystemProfileType getType() {
        return type;
    }
}
