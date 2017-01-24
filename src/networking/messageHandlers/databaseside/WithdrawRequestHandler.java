package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.WithdrawRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class WithdrawRequestHandler implements MessageHandler {
    private DatabaseHandler databaseHandler;

    public WithdrawRequestHandler(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    @Override
    public Message handle(Message message) {
        WithdrawRequest request = (WithdrawRequest) message;
        if(request != null){
            return this.databaseHandler.handleWithdrawRequest(request);
        }
        return null;
    }
}
