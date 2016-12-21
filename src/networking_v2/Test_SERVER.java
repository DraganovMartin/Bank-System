package networking_v2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import networking_v2.messages.LoginRequest;
import networking_v2.messages.LoginResponse;
import networking_v2.messages.Message;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class Test_SERVER {

    public static void main(String[] args) {

        // ==========================================================================================
        // ======================== CONFIG (CHANGE HERE TO TEST): ===================================
        // ==========================================================================================
        boolean DEBUGMODE = false;

        int PREDEFINEDUSERCOUNT = 4;
        String[] PREDEFINEDUSERNAMES = {"john", "peter", "jenny", "maria"};
        String[] PREDEFINEDPASSWORDS = {"johnpass", "peterpass", "jennypass", "mariapass"};

        String TESTNAME1 = "peter";
        String TESTPASS1 = "peterpass";
        String TESTNAME2 = "peter";
        String TESTPASS2 = "NOTpeterpass";

        ServerSocketFactory SERVERSOCKETFACTORY = ServerSocketFactory.getDefault();
        SocketFactory SOCKETFACTORY = SocketFactory.getDefault();
        String HOSTNAME = "localhost";
        int HOSTPORT = 15000;
        int SLEEPTIME = 5000; // timeout (in milliseconds) for the server to start before the client attempts to connect

        // ==========================================================================================
        // ======== ACTUAL TESTING BELOW, DO NOT CHANGE UNLESS YOU KNOW WHAT YOU'RE DOING: ==========
        // ==========================================================================================
        MessageHandler SERVERHANDLER = new MessageHandler() {
            @Override
            public Message handle(Message message) {
                if (message != null) {
                    String messageType = message.getType();
                    System.out.println("Server received a message of type: " + messageType + " !");
                    if (messageType.compareTo(LoginRequest.TYPE) == 0) {
                        // typecast to derived class:
                        LoginRequest request = (LoginRequest) message;
                        String username = request.getLoginUsername();
                        String password = request.getLoginPassword();
                        for (int i = 0; i < PREDEFINEDUSERCOUNT; i++) {
                            if (PREDEFINEDUSERNAMES[i].compareTo(username) == 0) {
                                if (PREDEFINEDPASSWORDS[i].compareTo(password) == 0) {
                                    // successful login:
                                    System.out.println("Server accepted the login of: " + username + " @ " + password + " !");
                                    return new LoginResponse(true, username);
                                }
                            }
                        }
                        // unsuccessful login:
                        System.out.println("Server denied the login of: " + username + " @ " + password + " !");
                        return new LoginResponse(false, null);
                    }
                    return null;
                } else {
                    return null;
                }
            }
        };

        MessageHandler CLIENTHANDLER = new MessageHandler() {
            @Override
            public Message handle(Message message) {
                if (message != null) {
                    String messageType = message.getType();
                    System.out.println("Client received a message of type: " + messageType + " !");
                    if (messageType.compareTo(LoginResponse.TYPE) == 0) {
                        // typecast to derived class:
                        LoginResponse response = (LoginResponse) message;
                        boolean isSuccessful = response.isSuccessful();
                        String verifiedUsername = response.getVerifiedUsername();
                        if (isSuccessful) {
                            // successful login:
                            System.out.println("Client was allowed to connect as: " + verifiedUsername + " !");
                        } else {
                            // unsuccessful login:
                            System.out.println("Client was not allowed to connect!");
                        }
                    }
                    return null;
                } else {
                    return null;
                }
            }
        };

        Server.DEBUG = DEBUGMODE;
        Client.DEBUG = DEBUGMODE;
        Serverside.DEBUG = DEBUGMODE;
        Clientside.DEBUG = DEBUGMODE;

        // tests:
        Server server = new Server(SERVERSOCKETFACTORY, HOSTPORT, SERVERHANDLER);
        server.start();

        // timeout for the server to start - normally server is running when clients try to connect:
        Thread timerStart = new Thread() {
            @Override
            public void run() {
                try {
                    int ticks = SLEEPTIME / 1000;
                    System.out.println("Waiting for the server to start (" + SLEEPTIME / 1000 + "s - change if necessary) ...");
                    for (int i = ticks; i > 0; i--) {
                        Thread.sleep(SLEEPTIME / ticks);
                        System.out.println("Client start in ... " + i + " ...");
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timerStart.start();
        try {
            timerStart.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }

        // client1 connecting:
        Client client1 = new Client(SOCKETFACTORY, HOSTNAME, HOSTPORT, CLIENTHANDLER);
        client1.connect();
        while (!client1.canSend()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            client1.send(new LoginRequest(TESTNAME1, TESTPASS1));
            System.out.println("Called client1.send()...");
        } catch (IOException ex) {
            Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }

        // client2 connecting:
        Client client2 = new Client(SOCKETFACTORY, HOSTNAME, HOSTPORT, CLIENTHANDLER);
        client2.connect();
        while (!client2.canSend()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            client2.send(new LoginRequest(TESTNAME2, TESTPASS2));
            System.out.println("Called client2.send()...");
        } catch (IOException ex) {
            Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }

        // timeout for the stopping the server - test server and client termination:
        Thread timerStop = new Thread() {
            @Override
            public void run() {
                try {
                    int ticks = SLEEPTIME / 1000;
                    System.out.println("Waiting for the server shutdown (" + SLEEPTIME / 1000 + "s - change if necessary) ...");
                    for (int i = ticks; i > 0; i--) {
                        Thread.sleep(SLEEPTIME / ticks);
                        System.out.println("Server shutdown in ... " + i + " ...");
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timerStop.start();
        try {
            timerStop.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
        }

        // stop the server:
        server.stopServer();
        while (server.isAlive()) {
            try {
                server.join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Logger.getLogger(Test_SERVER.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
