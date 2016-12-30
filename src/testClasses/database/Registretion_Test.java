package testClasses.database;

import dataModel.models.SystemProfile;
import database.DatabaseAuthorizationController;
import database.DatabaseController;

import java.util.Scanner;

/**
 * Created by Nikolay on 12/21/2016.
 */
public class Registretion_Test {

    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        DatabaseController db = new DatabaseController();
        DatabaseAuthorizationController dbAuthorization = new DatabaseAuthorizationController();
        System.out.println("Place enter your data:");
        String userName = sc.nextLine();
        String password = sc.nextLine();
        SystemProfile profile = dbAuthorization.logIn(userName,password);

        System.out.println(profile.getUserName()+ " firs name: "+profile.getClient().getFirstName() + " last name: "+
            profile.getClient().getLastName() + " control: "+profile.getType().getName());

        dbAuthorization.close();
        db.close();
    }
}
