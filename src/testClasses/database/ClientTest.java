package testClasses.database;

import dataModel.models.Client;
import dataModel.models.SystemProfile;
import dataModel.models.SystemProfileType;
import database.DatabaseController;

import java.sql.SQLException;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class ClientTest {
    public static void main(String[] args) {
        DatabaseController dc = new DatabaseController();
        int index = 1;

        Client client = dc.getClient(index);
        while(client != null){
            System.out.println(client.getFirstName() + "     " + client.getLastName());
            client = dc.getClient(++index);
        }
        index = 1;
        SystemProfileType type = dc.getSystemProfileType(index);
        while(type != null){
            System.out.println(type.getName());
            type = dc.getSystemProfileType(++index);
        }

        dc.close();
    }
}