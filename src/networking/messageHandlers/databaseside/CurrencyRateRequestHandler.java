package networking.messageHandlers.databaseside;

import database.DatabaseHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;
import networking.messages.request.CurrencyRatesRequest;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class CurrencyRateRequestHandler extends BasicRequestHandler {

    public CurrencyRateRequestHandler(DatabaseHandler databaseHandler){
        super(databaseHandler);
    }

    @Override
    public Message handle(Message message) {
        CurrencyRatesRequest request = (CurrencyRatesRequest) message;
        if(request != null){
            return this.databaseHandler.handleCurrencyRatesRequest(request);
        }
        return null;
    }
}
