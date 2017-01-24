package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.ChangePasswordRequest;
import testClasses.database.ChangePassword;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class ChangePasswordRequestHandler implements MessageHandler {

    private DatabaseHandler dh;

    public ChangePasswordRequestHandler(DatabaseHandler databaseHandler){
        this.dh = databaseHandler;
    }

    @Override
    public Message handle(Message message) {
        ChangePasswordRequest request = (ChangePasswordRequest) message;
        if(request != null){
            return this.dh.handleChangePasswordRequest(request);
        }
        return null;
    }
}
