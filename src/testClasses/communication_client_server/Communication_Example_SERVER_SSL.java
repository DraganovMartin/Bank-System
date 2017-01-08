package testClasses.communication_client_server;

import networking.connections.Server;
import networking.connections.ServerGUI_SSL;
import networking.messageHandlers.MappedMessageHandler;
import networking.messages.request.LoginRequest;

/**
 *
 * @author iliyan-kostov <https://github.com/iliyan-kostov/>
 */
public class Communication_Example_SERVER_SSL {

    public static void main(String[] args) {
        // SERVER PART:
        System.out.println("Starting server...");
        // initialize a mapped message handler:
        MappedMessageHandler serversideHandler = new MappedMessageHandler();
        // create a server object:
        Server server = new ServerGUI_SSL(serversideHandler, "Server");
        // add specific handlers to the mapped handler - can be done during runtime:
        serversideHandler.set(LoginRequest.TYPE, new ExampleLoginRequestHandler(server));
    }
}
