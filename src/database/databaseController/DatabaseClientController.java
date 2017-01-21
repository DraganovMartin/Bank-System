package database.databaseController;

import dataModel.models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.DataFormatException;

/**
 * Created by Nikolay on 12/22/2016.
 */
public class DatabaseClientController{
    private Connection connDatabase;

    private PreparedStatement getClient;
    private PreparedStatement setClient;

    private PreparedStatement lastId;

    public DatabaseClientController(Connection connDatabase){
        this.connDatabase = connDatabase;
        try {
            this.getClient = this.connDatabase.prepareStatement("SELECT * FROM clients WHERE id = ?");
            this.setClient = this.connDatabase.prepareStatement("INSERT INTO clients(firstName,lastName) VALUES(?,?)");
            this.lastId = this.connDatabase.prepareStatement("SELECT LAST_INSERT_ID()");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public int regiestrateClient(String firstName,String lastName){
        ResultSet resultSet = null;
        try{
            this.setClient.setString(1,firstName);
            this.setClient.setString(2,lastName);
            int result = this.setClient.executeUpdate();
            if(result != 1){
                throw new DataFormatException("Something wrong hapen with database conetion");
            }
            resultSet = this.lastId.executeQuery();
            if(resultSet.first()){
                return resultSet.getInt("last_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public void close(){
        try {
                this.lastId.close();
                this.getClient.close();
                this.setClient.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
