package database;

import database.databaseController.DatabaseController;
import networking.connections.Server;
import networking.messageHandlers.MessageHandler;
import networking.messageHandlers.serverside.ServersideMessageHandler;
import networking.messages.Message;

/**
 * Created by Nikolay on 1/8/2017.
 */
public class DatabaseHandler extends ServersideMessageHandler implements MessageHandler {

    public DatabaseHandler(Server server){
        super(server);
        if(!DatabaseController.isOpen()){
            DatabaseController.open();
        }
    }

    @Override
    public Message handle(Message message) {
        return null;
    }
}
