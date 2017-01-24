package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.DepositRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class DepositRequestHandler implements MessageHandler {
    private DatabaseHandler databaseHandler;

    public DepositRequestHandler(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    @Override
    public Message handle(Message message) {
        DepositRequest request = (DepositRequest) message;
        if(request != null){
            return this.databaseHandler.handleDepositRequest(request);
        }
        return null;
    }
}
