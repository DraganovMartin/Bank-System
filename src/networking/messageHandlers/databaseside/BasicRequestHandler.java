package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class BasicRequestHandler implements MessageHandler {
    protected DatabaseHandler databaseHandler;

    public BasicRequestHandler(DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }


    @Override
    public Message handle(Message message) {
        return null;
    }
}
