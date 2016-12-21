package testClasses.database;

import dataModel.models.Client;
import database.DatabaseController;

import java.sql.SQLException;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class ClientTest {
    public static void main(String[] args){
        DatabaseController dc = new DatabaseController();
        Client client = null;
        try {
            client = dc.getClient(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(client.getFirstName().equals("Niki") && client.getLastName().equals("Miki")){
            System.out.println(true);
        }

        dc.close();
    }
}
