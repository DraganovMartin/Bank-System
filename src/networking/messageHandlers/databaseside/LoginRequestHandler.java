package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.Update;
import networking.messages.request.LoginRequest;

import java.net.ConnectException;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class LoginRequestHandler implements MessageHandler {
    private DatabaseHandler dh;

    public LoginRequestHandler(DatabaseHandler databaseHandler){
        this.dh = databaseHandler;
    }

    @Override
    public Message handle(Message message) {
        LoginRequest login = (LoginRequest)message;
        if(login != null){
            Update update = dh.handleLoginRequest(login);
            return update;
        }
        return null;
    }

}
