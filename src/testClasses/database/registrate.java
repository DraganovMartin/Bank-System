package testClasses.database;

import database.DatabaseHandler;
import networking.messages.request.RegisterRequest;

import java.net.ConnectException;

/**
 * Created by Nikolay on 1/23/2017.
 */
public class registrate {
    public static void main(String[] args){
        try {
            DatabaseHandler dh = new DatabaseHandler();
            dh.handleRegisterRequest(new RegisterRequest("MIKI","1234n","Mark","Markov"));
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
