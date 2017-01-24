package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.WithdrawRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class WithdrawRequestHandler extends BalanceRequestHandler {

    public WithdrawRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
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
