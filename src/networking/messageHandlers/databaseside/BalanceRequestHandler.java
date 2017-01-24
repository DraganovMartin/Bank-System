package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.BalanceRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class BalanceRequestHandler implements MessageHandler {
    private DatabaseHandler databaseHandler;

    public BalanceRequestHandler(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    @Override
    public Message handle(Message message) {
        BalanceRequest request = (BalanceRequest) message;
        if(request != null){
            return this.databaseHandler.handleBalanceRequest(request);
        }
        return null;
    }
}
