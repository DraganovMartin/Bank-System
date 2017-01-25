package testClasses.database;

import database.DatabaseHandler;
import networking.messages.request.LoginRequest;

import java.net.ConnectException;

/**
 * Created by Nikolay on 1/23/2017.
 */
public class login {
    public static void main(String[] args) throws ConnectException {
        DatabaseHandler dh = new DatabaseHandler();
        dh.handleLoginRequest(new LoginRequest("alex","12345"));
        //dh.handleLoginRequest(new LoginRequest("mi","kake"));
    }
}
