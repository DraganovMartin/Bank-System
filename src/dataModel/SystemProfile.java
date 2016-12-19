package dataModel;

import java.io.Serializable;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class SystemProfile implements Serializable {

    protected String userName;
    protected String password;
    protected String type;
    private int clientId;

    public SystemProfile(String userName,String password,String type,int clientId){
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.clientId = clientId;
    }

    public String getUserName(){return this.userName;}
    public String getPassword(){return this.password;}
    public String getType(){return this.type;}
    public int getClientId(){return this.clientId;}

}
