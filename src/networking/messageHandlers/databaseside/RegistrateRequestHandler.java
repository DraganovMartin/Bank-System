package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.RegisterRequest;

import javax.xml.crypto.Data;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class RegistrateRequestHandler implements MessageHandler {

    private DatabaseHandler dh;

    public RegistrateRequestHandler(DatabaseHandler databaseHandler){
        this.dh = databaseHandler;
    }

    @Override
    public Message handle(Message message){
        RegisterRequest registerRequest = (RegisterRequest)message;
        if(registerRequest != null){
            return this.dh.handleRegisterRequest(registerRequest);
        }
        return null;
    }
}
