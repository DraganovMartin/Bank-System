package networking.messageHandlers.serverside;

import dataModel.CurrencyConverter;
import dataModel.Money;
import database.DatabaseHandler;
import database.databaseController.DatabaseBankAccountController;
import networking.connections.Server;
import networking.messages.request.BalanceRequest;
import networking.messages.Message;
import networking.messages.response.BalanceResponse;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class BalanceRequestHandler extends DatabaseHandler {

    private DatabaseBankAccountController bankAccountController;
    private CurrencyConverter converter;

    public BalanceRequestHandler(Server server,CurrencyConverter converter) {
        super(server);
        this.bankAccountController = new DatabaseBankAccountController();
        this.converter = converter;
    }

    @Override
    public Message handle(Message message) {
        BalanceRequest balanceRequest = (BalanceRequest)message;
        if(balanceRequest != null){
            Money money = this.bankAccountController.getAmount(balanceRequest.getUsername(),converter);
            return new BalanceResponse(balanceRequest.getUsername(),money);
        }
        return null;
    }
}
