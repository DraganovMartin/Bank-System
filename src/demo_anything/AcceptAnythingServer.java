package demo_anything;

import java.io.File;

import networking.connections.ServerGUI_SSL;
import networking.messageHandlers.MessageHandler;

/**
 * A class to start the server using {@link ServerGUI_SSL}.
 *
 * @author iliyan
 */
public class AcceptAnythingServer {

    public static void main(String[] args) {

        // настройки - тук:
        final String SERVER_FRAME_TITLE = "Sever";
        final String SERVER_KEYSTORE_DEFAULT_PATH = "D:\\example_certificates\\server.keystore";
        final String SERVER_KEYSTORE_DEFAULT_PASSWORD = "server";
        final int SERVER_DEFAULT_PORT = 15000;

        //надолу - инстанциране на сървъра и базата:
        System.out.println("Starting server...");

        // инстанциране на псевдо-база данни:
        MessageHandler serversideHandler = new AcceptAnythingHandler();

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
}
