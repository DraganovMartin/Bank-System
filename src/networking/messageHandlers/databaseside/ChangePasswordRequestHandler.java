package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.ChangePasswordRequest;
import testClasses.database.ChangePassword;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class ChangePasswordRequestHandler extends BalanceRequestHandler {

    public ChangePasswordRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
    }

    @Override
    public Message handle(Message message) {
        ChangePasswordRequest request = (ChangePasswordRequest) message;
        if(request != null){
            return this.databaseHandler.handleChangePasswordRequest(request);
        }
        return null;
    }
}
