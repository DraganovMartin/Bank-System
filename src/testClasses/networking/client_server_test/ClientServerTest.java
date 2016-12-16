package testClasses.networking.client_server_test;

import networking.serverside.ServerGUI;
import javax.net.ServerSocketFactory;

/**
 * A class for testing client-server.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ClientServerTest {

    public static void main(String[] args) {
        ServerGUI gui = new ServerGUI(ServerSocketFactory.getDefault(), null, 5);
        gui.start();
    }
}
