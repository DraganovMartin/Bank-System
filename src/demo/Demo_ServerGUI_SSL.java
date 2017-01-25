package demo;

import java.net.ConnectException;

import database.DatabaseHandler;
import java.io.File;
import networking.connections.ServerGUI_SSL;
import networking.messageHandlers.MappedMessageHandler;
import networking.messageHandlers.databaseside.*;
import networking.messages.request.*;

/**
 * A class to start the server using {@link ServerGUI_SSL}.
 *
 * @author iliyan
 */
public class Demo_ServerGUI_SSL {

    public static void main(String[] args) {

        // настройки - тук:
        final String SERVER_FRAME_TITLE = "Sever";
        final String SERVER_KEYSTORE_DEFAULT_PATH = "D:\\example_certificates\\server.keystore";
        final String SERVER_KEYSTORE_DEFAULT_PASSWORD = "server";
        final int SERVER_DEFAULT_PORT = 15000;

        //надолу - инстанциране на сървъра и базата:
        DatabaseHandler dbHandler = null;
        System.out.println("Starting server...");

        try {
            // инициализиране на базата
            dbHandler = new DatabaseHandler();
            {
                // създаване на Mapped Message Handler - за обработка на входящи съобщения от клиенти:
                MappedMessageHandler serversideHandler = new MappedMessageHandler();
                {
                    serversideHandler.set(BalanceRequest.TYPE, new BalanceRequestHandler(dbHandler));
                    serversideHandler.set(ChangePasswordRequest.TYPE, new ChangePasswordRequestHandler(dbHandler));
                    serversideHandler.set(CreateBankAccountRequest.TYPE, new CreateBankAccountRequestHandler(dbHandler));
                    serversideHandler.set(CurrencyRatesRequest.TYPE, new CurrencyRateRequestHandler(dbHandler));
                    serversideHandler.set(DepositRequest.TYPE, new DepositRequestHandler(dbHandler));
                    serversideHandler.set(LoginRequest.TYPE, new LoginRequestHandler(dbHandler));
                    serversideHandler.set(RegisterRequest.TYPE, new RegistrateRequestHandler(dbHandler));
                    serversideHandler.set(TransferRequest.TYPE, new TransferRequestHandler(dbHandler));
                    serversideHandler.set(TransactionHistoryRequest.TYPE, new TransfersHistoryRequestHandler(dbHandler));
                    serversideHandler.set(WithdrawRequest.TYPE, new WithdrawRequestHandler(dbHandler));
                }

                // създаване на графичния интерфейс на сървъра:
                ServerGUI_SSL server = new ServerGUI_SSL(serversideHandler, SERVER_FRAME_TITLE);

                // задаване на стойности на полетата по подразбиране - според настройките горе:
                server.setDefaultKeystoreAndPassword(new File(SERVER_KEYSTORE_DEFAULT_PATH), SERVER_KEYSTORE_DEFAULT_PASSWORD);
                server.setDefaultPortNumber(SERVER_DEFAULT_PORT);

                // интерфейсът на сървъра е готов, може да се стартира оттам
                while (true) {
                    System.out.println("Server GUI started successfully!");
                    break;
                }
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
