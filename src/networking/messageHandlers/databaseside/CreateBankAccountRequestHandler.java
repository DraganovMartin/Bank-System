package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.CreateBankAccountRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class CreateBankAccountRequestHandler extends BasicRequestHandler {

    public CreateBankAccountRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
    }

    @Override
    public Message handle(Message message) {
        CreateBankAccountRequest request = (CreateBankAccountRequest) message;
        if(request != null){
            return this.databaseHandler.handleCreateBankAccountRequest(request);
        }
        return null;
    }
}
