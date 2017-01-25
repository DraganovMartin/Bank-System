package demo;

import java.io.File;
import java.io.IOException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import client.BankSystemUI;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messageHandlers.MappedMessageHandler;
import networking.messages.DisconnectNotice;
import networking.messages.Message;
import networking.messages.Update;

/**
 * A class to start the client using SSL.
 *
 * @author iliyan
 */
public class Demo_ClientGUI_SSL {

    public static void main(String[] args) {

        // настройки - тук:
        final String CLIENT_KEYSTORE_DEFAULT_PATH = "D:\\example_certificates\\client.keystore";
        final String CLIENT_KEYSTORE_DEFAULT_PASSWORD = "client";
        final String HOSTNAME = "localhost";
        final int HOSTPORT = 15000;

        // надолу - стартиране на клиента:
        // избор на файл със SSL сертификат за клиента и задаване на парола за сертификата:
        JOptionPane.showMessageDialog(null,
                "За да се използва SSL защита е необходимо да изберете файл (сертификат),\n"
                + "съдържащ частния ключ на клиента и публичния ключ на банката!\n"
                + "Подобен файл е приложен в директорията \"documentation\\example_certificates\" !\n"
                + "Името му е: \"client.keystore\", а паролата: \"client\"");
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Изберете файла с SSL ключовете за клиента:");
        chooser.showOpenDialog(null);
        //File clientKeystore = chooser.getSelectedFile();
        File clientKeystore = new File(CLIENT_KEYSTORE_DEFAULT_PATH);
        JOptionPane.showMessageDialog(null,
                "Избраният файл е променен чрез кода на програмата:\n"
                + clientKeystore.getAbsolutePath() + "\n"
                + "За настройки - в кода !!!");
        JOptionPane.showMessageDialog(null,
                "Зададена е парола за клиентския SSL ключ:\n"
                + CLIENT_KEYSTORE_DEFAULT_PASSWORD + "\n"
                + "За настройки - в кода !!!");

        // генериране на SSL контекст и създаване на обект за мрежова връзка:
        SSLContext clientSSLContext = networking.security.SSLContextFactory.getSSLContext(clientKeystore, CLIENT_KEYSTORE_DEFAULT_PASSWORD);
        if (clientSSLContext != null) {
            JOptionPane.showMessageDialog(null, "Успешно е създаден SSL контекст за клиента!");
        } else {
            JOptionPane.showMessageDialog(null, "Създаването на SSL контекст за клиента е НЕУСПЕШНО !!!");
        }
        SSLSocketFactory sslSocketFactory = clientSSLContext.getSocketFactory();
        MappedMessageHandler handler = new MappedMessageHandler();
        networking.connections.Client clientConnection = new networking.connections.Client(sslSocketFactory, handler);

        // свързване към сървъра:
        JOptionPane.showMessageDialog(null,
                "Зададен е адрес на сървъра:\n"
                + HOSTNAME + "\n"
                + "и порт за свързване:\n"
                + HOSTPORT + "\n"
                + "За настройки - в кода !!!");

        // свързване със сървъра:
        try {
            clientConnection.connect(HOSTNAME, HOSTPORT);
        } catch (IOException ex) {
            Logger.getLogger(Demo_ClientGUI_SSL.class.getName()).log(Level.SEVERE, null, ex);
        }

        // инстанциране на клиентския интерфейс:
        BankSystemUI ui = new BankSystemUI(clientConnection);

        // създаване на обект за обработка от клиентския интерфейс на входящи съобщения:
        networking.messageHandlers.MessageHandler defaultHandler = new networking.messageHandlers.MessageHandler() {

            @Override
            public Message handle(Message message) {
                // TODO Auto-generated method stub
                if (message != null) {
                    ui.mainWindow.handle(message);
                }
                return null;
            }

        };

        // задаване на обработките за клиентския интерфейс:
        handler.set(Update.TYPE, defaultHandler);
        handler.set(DisconnectNotice.TYPE, defaultHandler);

        // клиентският интерфейс е стартиран
    }
}
