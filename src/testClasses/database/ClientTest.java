package testClasses.database;

import database.databaseController.DatabaseClientController;
import database.databaseController.DatabaseController;

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
    }
}
