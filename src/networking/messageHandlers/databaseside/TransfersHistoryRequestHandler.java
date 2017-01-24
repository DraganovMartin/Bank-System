package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.TransactionHistoryRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class TransfersHistoryRequestHandler implements MessageHandler {

    private DatabaseHandler dh;

    public TransfersHistoryRequestHandler(DatabaseHandler databaseHandler){
        this.dh = databaseHandler;
    }

    @Override
    public Message handle(Message message) {
        TransactionHistoryRequest request = (TransactionHistoryRequest) message;
        if(request != null){
            return this.dh.handleTransactionHistoryRequest(request);
        }
        return null;
    }
}
