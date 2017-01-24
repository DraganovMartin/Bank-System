package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.TransferRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class TransferRequestHandler implements MessageHandler {
    private DatabaseHandler databaseHandler;

    public TransferRequestHandler(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }

    @Override
    public Message handle(Message message) {
        TransferRequest request = (TransferRequest) message;
        if(request != null){
            return this.databaseHandler.handleTransferRequest(request);
        }
        return null;
    }
}
