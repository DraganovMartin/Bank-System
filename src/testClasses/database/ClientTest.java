package testClasses.database;

import dataModel.models.Client;
import dataModel.models.SystemProfile;
import dataModel.models.SystemProfileType;
import database.DatabaseClientController;
import database.DatabaseController;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class ClientTest {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        DatabaseController.open();
        DatabaseClientController dbClient = new DatabaseClientController();
        System.out.println("Place write the data:");
        String firstName = sc.nextLine();
        String lastName = sc.nextLine();

        int id = dbClient.regiestrateClient(firstName,lastName);
        Client client = dbClient.getClient(id);

        System.out.println(firstName + " == "+ client.getFirstName());
        System.out.println(lastName + " == " + client.getLastName());

        dbClient.closeClientController();
        DatabaseController.close();
    }
}
