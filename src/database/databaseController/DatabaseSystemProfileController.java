package database.databaseController;

import dataModel.PasswordConver;
import dataModel.models.SystemProfileType;
import sun.security.util.Password;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nikolay on 12/22/2016.
 */
public class DatabaseSystemProfileController {
    private Connection connDatabase;

    private PreparedStatement lastId;
    private PreparedStatement loginStatment;
    private PreparedStatement registrateStatment;
    private PreparedStatement changePasswordStatment;

    public DatabaseSystemProfileController(Connection connDatabase){
        this.connDatabase = connDatabase;
        try {
            this.registrateStatment = connDatabase.
                    prepareStatement("INSERT INTO systemProfiles(userName,password,firstName,secondName) VALUES(?,?,?,?)");
            this.lastId = this.connDatabase.prepareStatement("SELECT LAST_INSERT_ID()");
            this.loginStatment = this.connDatabase.prepareStatement("SELECT userName,password FROM systemProfiles WHERE userName = ?");
            this.changePasswordStatment = this.connDatabase.prepareStatement("UPDATE sustempprofiles SET password = ? WHERE userName = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String registrate(String userName,String password,String firsrName,String secondName){
        try{
            System.out.println(userName);
            this.registrateStatment.setString(1,userName);
            this.registrateStatment.setBytes(2, PasswordConver.convertPssword(password));
            this.registrateStatment.setString(3,firsrName);
            this.registrateStatment.setString(4,secondName);
            int result = this.registrateStatment.executeUpdate();
            if(result == 1){
                return userName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String login(String username,String password){
        ResultSet resultSet = null;
        try {
            this.loginStatment.setString(1,username);
            resultSet = this.loginStatment.executeQuery();
            if(resultSet.first()) {
                if (PasswordConver.isEqualPasswords(resultSet.getBytes("password"), PasswordConver.convertPssword(password))) {
                    return username;
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

    public boolean changePassword(String userName,String currPassword,String newPassword){
        try{
            this.loginStatment.setString(1,userName);
            ResultSet resultSet = this.loginStatment.executeQuery();
            if(resultSet.first()) {
                if (PasswordConver.isEqualPasswords(resultSet.getBytes("password"), PasswordConver.convertPssword(currPassword))) {
                    this.changePasswordStatment.setBytes(1, PasswordConver.convertPssword(newPassword));
                    this.changePasswordStatment.setString(2,userName);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void closeSystemProfileController(){
        try {
            this.lastId.close();
            this.registrateStatment.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
