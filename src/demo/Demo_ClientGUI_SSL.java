package demo;

import java.io.File;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import client.BankSystemUI;
import networking.messageHandlers.MessageHandler;
import networking.messages.Message;

/**
 * A class to start the client using SSL.
 *
 * @author iliyan
 */
public class Demo_ClientGUI_SSL {

    public static void main(String[] args) {

        MessageHandler messageHandler = new MessageHandler() {
            @Override
            public Message handle(Message message) {
                JOptionPane.showMessageDialog(null, "Set the client message handler!");
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        JOptionPane.showMessageDialog(null,
                "За да се използва SSL защита е необходимо да изберете файл (сертификат),\n"
                + "съдържащ частния ключ на клиента и публичния ключ на банката!\n"
                + "Подобен файл е приложен в директорията \"documentation\\example_certificates\" !\n"
                + "Името му е: \"client.keystore\", а паролата: \"client\"");
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Изберете файла с SSL ключовете за клиента:");
        chooser.showOpenDialog(null);
        File clientKeystore = chooser.getSelectedFile();
        SSLContext clientSSLContext = networking.security.SSLContextFactory.getSSLContext(clientKeystore, "client");
        SSLSocketFactory sslSocketFactory = clientSSLContext.getSocketFactory();
        networking.connections.Client clientConnection = new networking.connections.Client(sslSocketFactory, messageHandler);
        JOptionPane.showMessageDialog(null, "Успешно е създаден SSL контекст за клиента!");
        BankSystemUI ui = new BankSystemUI(clientConnection);
    }
}
