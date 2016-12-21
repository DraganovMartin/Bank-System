package testClasses.database;

import database.DatabaseController;

/**
 * Created by Nikolay on 12/21/2016.
 */
public class registrate {
    public static void main(String[] args){
        DatabaseController db = new DatabaseController();
         System.out.println(db.registrate("miksan","1234",1,1));
    }
}
