package database;

import dataModel.models.*;

import java.sql.*;
import java.sql.Connection;
/**
 * Database connection and controller between the database (MYSQL on db4free server) and the project.
 * Before any tests make sure that you added the data base drivers.
 */

/**
 * Created by Nikolay on 12/14/2016.
 */
public class DatabaseController {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://db4free.net:3306/banksystem";

    public static final String user = "banksystem_root";
    public static final String password = "banksystemroot";

    /**
     *  регистрация (име, парола)
     2. логин (име, парола)
     3. теглене (пари, сметка)
     4. внасяне (пари, сметка)
     5. трансфер (пари, сметка1, сметка2)
     6. проверка наличност (сметка)
     7. проверка история (сметка)
     и това е
     */

    private PreparedStatement registration;
    private PreparedStatement login;
    private PreparedStatement getSystemProfileType;
    private PreparedStatement getClient;

    private Connection connDatabase;

    public DatabaseController(){
        try {
            Class.forName(this.JDBC_DRIVER);
            this.connDatabase = DriverManager.getConnection(this.DB_URL,this.user,this.password);
            this.initDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();// or do log
        } catch (SQLException e) {
            e.printStackTrace();// or do log
        }
    }

    private void initDatabase() throws SQLException {
        this.registration = connDatabase.
                prepareStatement("INSERT INTO systemProfiles(userName,password,type_id,client_id) VALUES(?,?,?,?)");
        this.login = connDatabase.prepareStatement("SELECT * FROM systemProfiles WHERE userName = ?");
        this.getSystemProfileType = connDatabase.prepareStatement("SELECT * FROM systemProfileType WHERE id = ?");
        this.getClient = connDatabase.prepareStatement("SELECT * FROM clients WHERE id = ?");
    }

    private SystemProfileType getSystemProfileType(int type_id) {
        ResultSet resultSet = null;
        try{
            this.getSystemProfileType.setInt(1,type_id);
            resultSet = this.getSystemProfileType.executeQuery();
            if(resultSet.first()) {
                String name = resultSet.getString("name");
                boolean canRead = resultSet.getBoolean("canRead");
                boolean canWrite = resultSet.getBoolean("canWrite");
                boolean canTransfer = resultSet.getBoolean("canTransfer");
                SystemProfileType type = new SystemProfileType(type_id, name);
                type.setCanRead(canRead);
                type.setCanWrite(canWrite);
                type.setCanTransfer(canTransfer);
                return type;
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

    public Client getClient(int id){
        ResultSet resultSet = null;
        try {
            this.getClient.setInt(1,id);
            resultSet = this.getClient.executeQuery();
            if(resultSet.first()){
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                return new Client(id,firstName,lastName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public SystemProfile logIn(String userName, String password) {
        ResultSet resultSet = null;
        try {
            this.login.setString(1,userName);
            resultSet = this.login.executeQuery();
            if(resultSet.first()) {
                if (PasswordConver.isEqualPasswords(resultSet.getBytes("password"), PasswordConver.convertPssword(password))) {
                    SystemProfileType type = this.getSystemProfileType(resultSet.getInt("type_id"));
                    Client client = this.getClient(resultSet.getInt("client_id"));
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


    public Transfer getTrnasfer(){
        return null;
    }

    public void close(){
        try{
            this.connDatabase.close();
            this.getClient.close();
            this.login.close();
            this.registration.close();
            this.getSystemProfileType.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
