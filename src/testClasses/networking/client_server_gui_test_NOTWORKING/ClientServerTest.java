package testClasses.networking.client_server_gui_test_NOTWORKING;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

/**
 * A class for testing client-server.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ClientServerTest {

    public static void main(String[] args) {
        ServerGUI serverGUI = new ServerGUI(ServerSocketFactory.getDefault(), new FakeServersideHandler(), 5);
        serverGUI.start();
        try {
            serverGUI.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
            FakeClientThread fake1 = new FakeClientThread("fake1", SocketFactory.getDefault(), new FakeClientsideHandler("fake1"));
            FakeClientThread fake2 = new FakeClientThread("fake2", SocketFactory.getDefault(), new FakeClientsideHandler("fake2"));
            FakeClientThread fake3 = new FakeClientThread("fake3", SocketFactory.getDefault(), new FakeClientsideHandler("fake3"));
            
            fake1.start();
            fake2.start();
            fake3.start();
         */
    }
}
