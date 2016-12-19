package database;

import dataModel.models.BankAccount;
import dataModel.models.Client;
import dataModel.models.Transfer;

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

    private Connection connDatabase;

    public DatabaseController(){
        try {
            Class.forName(this.JDBC_DRIVER);
            this.connDatabase = DriverManager.getConnection(this.DB_URL,this.user,this.password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();// or do log
        } catch (SQLException e) {
            e.printStackTrace();// or do log
        }
    }

    public Client getClient(int id) throws SQLException {
        String firstName;
        String lastName;
        Statement stmt = null;
        ResultSet set = null;
        try {
            System.out.println("Creating statment for get a client...");
            stmt = this.connDatabase.createStatement();
            String sql = "SELECT * FROM clients WHERE id = "+id;
            set = stmt.executeQuery(sql);
            set.first();
            firstName = set.getString("firstName");
            lastName = set.getString("lastName");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            set.close();
            stmt.close();
            
        }
        return new Client(id,firstName,lastName);
    }

    public Transfer getTrnasfer(){
        return null;
    }

    public void close(){
        try{
            this.connDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
