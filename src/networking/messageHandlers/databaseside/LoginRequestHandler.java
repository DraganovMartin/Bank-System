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
public class LoginRequestHandler extends BalanceRequestHandler {

    public LoginRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
    }

    @Override
    public Message handle(Message message) {
        LoginRequest login = (LoginRequest)message;
        if(login != null){
            Update update = this.databaseHandler.handleLoginRequest(login);
            return update;
        }
        return null;
    }

}
