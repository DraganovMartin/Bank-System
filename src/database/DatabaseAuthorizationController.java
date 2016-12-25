package database;

import dataModel.models.Client;
import dataModel.models.SystemProfile;
import dataModel.models.SystemProfileType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nikolay on 12/22/2016.
 */
public class DatabaseAuthorizationController extends DatabaseController {

    private PreparedStatement registration;
    private PreparedStatement login;

    private DatabaseClientController client;
    private DatabaseSystemProfileController systemProfile;

    public DatabaseAuthorizationController(){
        super();
        client = new DatabaseClientController();
        systemProfile = new DatabaseSystemProfileController();
        try {
            this.registration = connDatabase.
                    prepareStatement("INSERT INTO systemProfiles(userName,password,type_id,client_id) VALUES(?,?,?,?)");
            this.login = connDatabase.prepareStatement("SELECT * FROM systemProfiles WHERE userName = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public SystemProfile logIn(String userName, String password) {
        ResultSet resultSet = null;
        try {
            this.login.setString(1,userName);
            resultSet = this.login.executeQuery();
            if(resultSet.first()) {
                if (PasswordConver.isEqualPasswords(resultSet.getBytes("password"), PasswordConver.convertPssword(password))) {
                    SystemProfileType type = systemProfile.getSystemProfileType(resultSet.getInt("type_id"));
                    Client client = this.client.getClient(resultSet.getInt("client_id"));
                    return new SystemProfile(userName, client, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean registrate(String userName,String password,int type_id, int client_id){
        try{
            this.registration.setString(1,userName);
            this.registration.setBytes(2,PasswordConver.convertPssword(password));
            this.registration.setInt(3,type_id);
            this.registration.setInt(4,client_id);
            this.registration.executeUpdate();
            if(this.registration.getUpdateCount() == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeAuthorizationController(){
        try {
            lastId.close();
            login.close();
            registration.close();
            client.close();
            systemProfile.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
