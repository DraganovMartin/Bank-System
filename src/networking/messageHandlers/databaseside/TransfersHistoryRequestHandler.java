package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.TransactionHistoryRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class TransfersHistoryRequestHandler extends BasicRequestHandler {

    public TransfersHistoryRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
    }

    @Override
    public Message handle(Message message) {
        TransactionHistoryRequest request = (TransactionHistoryRequest) message;
        if(request != null){
            return this.databaseHandler.handleTransactionHistoryRequest(request);
        }
        return null;
    }
}
