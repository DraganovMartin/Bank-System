package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.BalanceRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class BalanceRequestHandler extends BasicRequestHandler {

    public BalanceRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
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
