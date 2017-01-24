package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.TransferRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class TransferRequestHandler extends BalanceRequestHandler {

    public TransferRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
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
