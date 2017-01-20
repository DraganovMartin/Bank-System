package client;

import dataModel.CurrencyConverter;
import dataModel.ProfileData;
import dataModel.ProfileData.Balance;
import dataModel.ProfileData.TransferHistory;
import dataModel.models.Currency;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import networking.connections.Client;
import networking.messageHandlers.MappedMessageHandler;
import networking.messageHandlers.MessageHandler;
import networking.messages.DisconnectNotice;
import networking.messages.Message;
import networking.messages.Update;
import networking.security.SSLContextFactory;
import testClasses.communication_client_server.Communication_Example_CLIENT_SSL;

public class ClientGUI implements MessageHandler {

    public final static String HOSTNAME = "localhost";
    public final static int HOSTPORT = 15000;
    public final static String CLIENT_KEYSTORE_PASSWORD = "client";
    public final static String CLIENT_KEYSTORE_LOCATION = "D:\\example_certificates\\client.keystore";

    public SSLContext sslContext;
    public SocketFactory socketFactory;
    public MappedMessageHandler clientsideHandler;
    public Client client;

    public Currency defaultCurrency;

    public static final String MAINFRAMETITLE = "Client";

    JFrame mainFrame;
    JPanel mainPanel;
    JScrollPane mainPanel_scrollpane;

    JScrollPane display_currencyRates_scrollpane;
    JScrollPane display_balance_scrollpane;
    JScrollPane display_transferHistory_scrollpane;
    JPanel controls;
    JScrollPane controls_scrollpane;

    // RegisterRequest:
    JTextField registerUsername;
    JTextField registerPassword;
    JTextField firstName;
    JTextField lastName;
    JLabel registerUsername_label;
    JLabel registerPassword_label;
    JLabel firstName_label;
    JLabel lastName_label;
    public static final String REGISTERUSERNAME_LABEL_TEXT = "registerUsername";
    public static final String REGISTERPASSWORD_LABEL_TEXT = "registerPassword";
    public static final String FIRSTNAME_LABEL_TEXT = "firstName";
    public static final String LASTNAME_LABEL_TEXT = "lastName";
    // LoginRequest:
    JTextField loginUsername;
    JTextField loginPassword;
    JLabel loginUsername_label;
    JLabel loginPassword_label;
    public static final String LOGINUSERNAME_LABEL_TEXT = "loginUsername";
    public static final String LOGINPASSWORD_LABEL_TEXT = "loginPassword";
    // ChangePasswordRequest:
    JTextField oldPassword;
    JTextField newPassword;
    JLabel oldPassword_label;
    JLabel newPassword_label;
    public static final String OLDPASSWORD_LABEL_TEXT = "oldPassword";
    public static final String NEWPASSWORD_LABEL_TEXT = "newPassword";
    // Money operations:
    JTextField moneyCurrency;
    JTextField moneyAmount;
    JLabel moneyCurrency_label;
    JLabel moneyAmount_label;
    public static final String MONEYCURRENCY_LABEL_TEXT = "moneyCurrency";
    public static final String MONEYAMOUNT_LABEL_TEXT = "moneyAmount";
    // CreateBankAccountRequest:
    JTextField bankAccountType;
    JLabel bankAccountType_label;
    public static final String BANKACCOUNTTYPE_LABEL_TEXT = "bankAccountType";
    // DepositRequest, TransferRequest:
    JTextField toBankAccount;
    JLabel toBankAccount_label;
    public static final String TOBANKACCOUNT_LABEL_TEXT = "toBankAccount";
    // WithdrawRequest, TransferRequest:
    JTextField fromBankAccount;
    JLabel fromBankAccount_label;
    public static final String FROMBANKACCOUNT_LABEL_TEXT = "fromBankAccount";

    // Send buttons:
    // RegisterRequest
    JButton sendButton_RegisterRequest;
    public static final String SENDBUTTON_REGISTERREQUEST_TEXT = "Send RegisterRequest...";
    // LoginRequest
    JButton sendButton_LoginRequest;
    public static final String SENDBUTTON_LOGINREQUEST_TEXT = "Send LoginRequest...";
    // LogoutRequest
    JButton sendButton_LogoutRequest;
    public static final String SENDBUTTON_LOGOUTREQUEST_TEXT = "Send LogoutRequest...";
    // ChangePasswordRequest
    JButton sendButton_ChangePasswordRequest;
    public static final String SENDBUTTON_CHANGEPASSWORDREQUEST_TEXT = "Send ChangePasswordRequest...";
    // BalanceRequest
    JButton sendButton_BalanceRequest;
    public static final String SENDBUTTON_BALANCEREQUEST_TEXT = "Send BalanceRequest...";
    // TransactionHistoryRequest
    JButton sendButton_TransactionHistoryRequest;
    public static final String SENDBUTTON_TRANSACTIONHISTORYREQUEST_TEXT = "Send TransactionHistoryRequest...";
    // CurrencyRatesRequest
    JButton sendButton_CurrencyRatesRequest;
    public static final String SENDBUTTON_CURRENCYRATESREQUEST_TEXT = "Send CurrencyRatesRequest...";
    // CreateBankAccountRequest
    JButton sendButton_CreateBankAccountRequest;
    public static final String SENDBUTTON_CREATEBANKACCOUNTREQUEST_TEXT = "Send CreateBankAccountRequest...";
    // DepositRequest
    JButton sendButton_DepositRequest;
    public static final String SENDBUTTON_DEPOSITREQUEST_TEXT = "Send DepositRequest...";
    // WithdrawRequest
    JButton sendButton_WithdrawRequest;
    public static final String SENDBUTTON_WITHDRAWREQUEST_TEXT = "Send WithdrawRequest...";
    // TransferRequest
    JButton sendButton_TransferRequest;
    public static final String SENDBUTTON_TRANSFERREQUEST_TEXT = "Send TransferRequest...";

    public ClientGUI() {
        JOptionPane.showMessageDialog(this.mainFrame, "Initializing SSL Encription engine and networking, please wait...");
        this.sslContext = SSLContextFactory.getSSLContext(new File(ClientGUI.CLIENT_KEYSTORE_LOCATION), ClientGUI.CLIENT_KEYSTORE_PASSWORD);
        this.socketFactory = sslContext.getSocketFactory();
        this.clientsideHandler = new MappedMessageHandler();
        this.clientsideHandler.set(Update.TYPE, (Message message) -> handleUpdate((Update) message));
        this.clientsideHandler.set(DisconnectNotice.TYPE, (Message message) -> handleDisconnectNotice((DisconnectNotice) message));
        this.client = new Client(socketFactory, clientsideHandler);
        JOptionPane.showMessageDialog(this.mainFrame, "SSL Encription engine and networking initialization finished!");

        this.defaultCurrency = null;

        this.mainFrame = new JFrame(ClientGUI.MAINFRAMETITLE);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.controls = new JPanel(new GridLayout(0, 2));
        {
            // RegisterRequest:
            this.registerUsername = new JTextField();
            this.registerPassword = new JTextField();
            this.firstName = new JTextField();
            this.lastName = new JTextField();
            this.registerUsername_label = new JLabel(ClientGUI.REGISTERUSERNAME_LABEL_TEXT);
            this.registerPassword_label = new JLabel(ClientGUI.REGISTERPASSWORD_LABEL_TEXT);
            this.firstName_label = new JLabel(ClientGUI.FIRSTNAME_LABEL_TEXT);
            this.lastName_label = new JLabel(ClientGUI.LASTNAME_LABEL_TEXT);
            // LoginRequest:
            this.loginUsername = new JTextField();
            this.loginPassword = new JTextField();
            this.loginUsername_label = new JLabel(ClientGUI.LOGINUSERNAME_LABEL_TEXT);
            this.loginPassword_label = new JLabel(ClientGUI.LOGINPASSWORD_LABEL_TEXT);
            // ChangePasswordRequest:
            this.oldPassword = new JTextField();
            this.newPassword = new JTextField();
            this.oldPassword_label = new JLabel(ClientGUI.OLDPASSWORD_LABEL_TEXT);
            this.newPassword_label = new JLabel(ClientGUI.NEWPASSWORD_LABEL_TEXT);
            // Money operations:
            this.moneyCurrency = new JTextField();
            this.moneyAmount = new JTextField();
            this.moneyCurrency_label = new JLabel(ClientGUI.MONEYCURRENCY_LABEL_TEXT);
            this.moneyAmount_label = new JLabel(ClientGUI.MONEYAMOUNT_LABEL_TEXT);
            // CreateBankAccountRequest:
            this.bankAccountType = new JTextField();
            this.bankAccountType_label = new JLabel(ClientGUI.BANKACCOUNTTYPE_LABEL_TEXT);
            // DepositRequest, TransferRequest:
            this.toBankAccount = new JTextField();
            this.toBankAccount_label = new JLabel(ClientGUI.TOBANKACCOUNT_LABEL_TEXT);
            // WithdrawRequest, TransferRequest:
            this.fromBankAccount = new JTextField();
            this.fromBankAccount_label = new JLabel(ClientGUI.FROMBANKACCOUNT_LABEL_TEXT);
        }
        {
            // RegisterRequest:
            this.controls.add(this.registerUsername_label);
            this.controls.add(this.registerUsername);
            this.controls.add(this.registerPassword_label);
            this.controls.add(this.registerPassword);
            this.controls.add(this.firstName_label);
            this.controls.add(this.firstName);
            this.controls.add(this.lastName_label);
            this.controls.add(this.lastName);
            // LoginRequest:
            this.controls.add(this.loginUsername_label);
            this.controls.add(this.loginUsername);
            this.controls.add(this.loginPassword_label);
            this.controls.add(this.loginPassword);
            // ChangePasswordRequest:
            this.controls.add(this.oldPassword_label);
            this.controls.add(this.oldPassword);
            this.controls.add(this.newPassword_label);
            this.controls.add(this.newPassword);
            // Money operations:
            this.controls.add(this.moneyCurrency_label);
            this.controls.add(this.moneyCurrency);
            this.controls.add(this.moneyAmount_label);
            this.controls.add(this.moneyAmount);
            // CreateBankAccountRequest:
            this.controls.add(this.bankAccountType_label);
            this.controls.add(this.bankAccountType);
            // DepositRequest, TransferRequest:
            this.controls.add(this.toBankAccount_label);
            this.controls.add(this.toBankAccount);
            // WithdrawRequest, TransferRequest:
            this.controls.add(this.fromBankAccount_label);
            this.controls.add(this.fromBankAccount);
        }
        {
            // Send buttons:
            // RegisterRequest
            this.sendButton_RegisterRequest = new JButton(ClientGUI.SENDBUTTON_REGISTERREQUEST_TEXT);
            // LoginRequest
            this.sendButton_LoginRequest = new JButton(ClientGUI.SENDBUTTON_LOGINREQUEST_TEXT);
            // LogoutRequest
            this.sendButton_LogoutRequest = new JButton(ClientGUI.SENDBUTTON_LOGOUTREQUEST_TEXT);
            // ChangePasswordRequest
            this.sendButton_ChangePasswordRequest = new JButton(ClientGUI.SENDBUTTON_CHANGEPASSWORDREQUEST_TEXT);
            // BalanceRequest
            this.sendButton_BalanceRequest = new JButton(ClientGUI.SENDBUTTON_BALANCEREQUEST_TEXT);
            // TransactionHistoryRequest
            this.sendButton_TransactionHistoryRequest = new JButton(ClientGUI.SENDBUTTON_TRANSACTIONHISTORYREQUEST_TEXT);
            // CurrencyRatesRequest
            this.sendButton_CurrencyRatesRequest = new JButton(ClientGUI.SENDBUTTON_CURRENCYRATESREQUEST_TEXT);
            // CreateBankAccountRequest
            this.sendButton_CreateBankAccountRequest = new JButton(ClientGUI.SENDBUTTON_CREATEBANKACCOUNTREQUEST_TEXT);
            // DepositRequest
            this.sendButton_DepositRequest = new JButton(ClientGUI.SENDBUTTON_DEPOSITREQUEST_TEXT);
            // WithdrawRequest
            this.sendButton_WithdrawRequest = new JButton(ClientGUI.SENDBUTTON_WITHDRAWREQUEST_TEXT);
            // TransferRequest
            this.sendButton_TransferRequest = new JButton(ClientGUI.SENDBUTTON_TRANSFERREQUEST_TEXT);
        }

        this.display_currencyRates_scrollpane = new JScrollPane();
        this.display_balance_scrollpane = new JScrollPane();
        this.display_transferHistory_scrollpane = new JScrollPane();
        this.controls_scrollpane = new JScrollPane(this.controls);
        this.display_currencyRates_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.display_balance_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.display_transferHistory_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.controls_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        this.mainPanel.add(this.display_currencyRates_scrollpane);
        this.mainPanel.add(this.display_balance_scrollpane);
        this.mainPanel.add(this.display_transferHistory_scrollpane);
        this.mainPanel.add(this.controls_scrollpane);

        this.mainPanel_scrollpane = new JScrollPane(this.mainPanel);
        this.mainFrame.add(this.mainPanel_scrollpane);
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // STOP CONNECTION FIRST !!! USE EXIT BUTTON !!!
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }

    public synchronized void connect() {
        JOptionPane.showMessageDialog(this.mainFrame, "Trying to connect...");
        try {
            client.connect(ClientGUI.HOSTNAME, ClientGUI.HOSTPORT);
        } catch (IOException ex) {
            Logger.getLogger(Communication_Example_CLIENT_SSL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void discconnect() {
        JOptionPane.showMessageDialog(this.mainFrame, "Trying to disconnect...");
        client.stop();
    }

    public synchronized void send(Message message) throws IOException {
        this.client.send(message);
    }

    public synchronized Message handleUpdate(Update update) {
        if (update.getProflieData() != null) {
            if (this.defaultCurrency == null) {
                this.defaultCurrency = update.getProflieData().getCurrencyConverter().getSupportedCurrencies()[0];
            }
            JTable currencyRates = update.getProflieData().getCurrencyConverter().getSupportedExchangeRatesAsJTable(this.defaultCurrency, "0.01");
            this.display_currencyRates_scrollpane.setViewportView(currencyRates);
            {
                Dimension p = currencyRates.getPreferredSize();
                this.display_currencyRates_scrollpane.setPreferredSize(new Dimension((int) p.getWidth(), (int) p.getHeight() + 25));
                Container current = this.display_currencyRates_scrollpane;
                do {
                    current.revalidate();
                    current = current.getParent();
                } while (current != null);
            }

            JTable balance = update.getProflieData().getBalanceTable();
            this.display_balance_scrollpane.setViewportView(balance);
            {
                Dimension p = balance.getPreferredSize();
                this.display_balance_scrollpane.setPreferredSize(new Dimension((int) p.getWidth(), (int) p.getHeight() + 25));
                Container current = this.display_balance_scrollpane;
                do {
                    current.revalidate();
                    current = current.getParent();
                } while (current != null);
            }

            JTable transferHistory = update.getProflieData().getTransferHistoryTable();
            this.display_transferHistory_scrollpane.setViewportView(transferHistory);
            {
                Dimension p = transferHistory.getPreferredSize();
                this.display_transferHistory_scrollpane.setPreferredSize(new Dimension((int) p.getWidth(), (int) p.getHeight() + 25));
                Container current = this.display_transferHistory_scrollpane;
                do {
                    current.revalidate();
                    current = current.getParent();
                } while (current != null);
            }

            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
        return null;
    }

    public synchronized Message handleDisconnectNotice(DisconnectNotice disconnectNotice) {
        // HANDLE DISCONNECTNOTICE
        this.display_currencyRates_scrollpane.setViewportView(null);
        this.display_balance_scrollpane.setViewportView(null);
        this.display_transferHistory_scrollpane.setViewportView(null);

        this.display_currencyRates_scrollpane.setPreferredSize(new Dimension(0, 0));
        this.display_balance_scrollpane.setPreferredSize(new Dimension(0, 0));
        this.display_transferHistory_scrollpane.setPreferredSize(new Dimension(0, 0));

        {
            Container current = this.display_currencyRates_scrollpane;
            do {
                current.revalidate();
                current = current.getParent();
            } while (current != null);
        }
        {
            Container current = this.display_balance_scrollpane;
            do {
                current.revalidate();
                current = current.getParent();
            } while (current != null);
        }
        {
            Container current = this.display_transferHistory_scrollpane;
            do {
                current.revalidate();
                current = current.getParent();
            } while (current != null);
        }

        this.mainFrame.revalidate();
        this.mainFrame.pack();
        return null;
    }

    public synchronized void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public synchronized Message handle(Message message) {
        if (message != null) {
            String messageType = message.getType();
            JOptionPane.showMessageDialog(this.mainFrame, "Received from server: " + messageType);
            if (messageType != null) {
                return this.clientsideHandler.handle(message);
            }
        }
        return null;
    }

    public static void main(String[] args) {

        // 1. Create simple user interface:
        ClientGUI clientGUI = new ClientGUI();

        // 2. Create an instance of Update (message):
        Update update1;
        {
            Balance balance1 = new Balance();
            {
                // fill in data from the database - table "BankAccounts":
                balance1.add("Acc00001", "deposit", "BGN", "1234.56");
                balance1.add("Acc00002", "deposit", "BGN", "3456.78");
                balance1.add("Acc00003", "credit", "USD", "2345.67");
                // etc. etc.
            }
            TransferHistory transferHistory1 = new TransferHistory();
            {
                // fill in data from the database - table "TransferHistory":
                transferHistory1.add("Transfer00001", "Acc00001", "Acc00002", "BGN", "123.45", "01.01.2017");
                transferHistory1.add("Transfer00002", "Acc00002", "Acc00003", "BGN", "456.78", "02.01.2017");
                transferHistory1.add("Transfer00003", "Acc00003", "Acc00002", "BGN", "345.67", "03.01.2017");
                transferHistory1.add("Transfer00004", "Acc00001", "Acc00002", "BGN", "234.56", "04.01.2017");
                // etc. etc.
            }
            CurrencyConverter currencyConverter1 = new CurrencyConverter();
            {
                // fill in data from the database - table "Currencies":
                currencyConverter1.setCurrencyValue(new Currency("BGN"), "1.00000");
                currencyConverter1.setCurrencyValue(new Currency("USD"), "1.83336");
                currencyConverter1.setCurrencyValue(new Currency("GBP"), "2.25964");
                currencyConverter1.setCurrencyValue(new Currency("CHF"), "1.82294");
                // etc. etc.
            }
            ProfileData profileData1 = new ProfileData(balance1, transferHistory1, currencyConverter1);
            update1 = new Update("nodescription", null, true, profileData1);
        }

        // 3. Simulate receiving the message update1 from the client (will be automatic):
        clientGUI.handle(update1);

        // 2. Create another instance of Update (message):
        Update update2;
        {
            Balance balance1 = new Balance();
            {
                // fill in data from the database - table "BankAccounts":
                balance1.add("NEW-ACC1", "deposit", "USD", "5000");
                balance1.add("NEW-ACC2", "credit", "BGN", "200");
                // etc. etc.
            }
            TransferHistory transferHistory1 = new TransferHistory();
            {
                // fill in data from the database - table "TransferHistory":
                transferHistory1.add("Tr001", "NEW-ACC1", "NEW-ACC2", "BGN", "10", "04.01.2017");
                transferHistory1.add("Tr002", "NEW-ACC2", "NEW-ACC1", "BGN", "100", "05.01.2017");
                transferHistory1.add("Tr003", "NEW-ACC2", "NEW-ACC1", "USD", "1000", "06.01.2017");
                // etc. etc.
            }
            CurrencyConverter currencyConverter1 = new CurrencyConverter();
            {
                // fill in data from the database - table "Currencies":
                currencyConverter1.setCurrencyValue(new Currency("BGN"), "1.00000");
                currencyConverter1.setCurrencyValue(new Currency("USD"), "1.83336");
                currencyConverter1.setCurrencyValue(new Currency("GBP"), "2.25964");
                currencyConverter1.setCurrencyValue(new Currency("CHF"), "1.82294");
                // etc. etc.
            }
            ProfileData profileData1 = new ProfileData(balance1, transferHistory1, currencyConverter1);
            update2 = new Update("nodescription", null, true, profileData1);
        }

        // 3. Simulate receiving the message update2 from the client (will be automatic):
        clientGUI.handle(update2);

        // 4. Simulate receiving a DisconnectNotice from the server:
        clientGUI.handle(new DisconnectNotice("The server has closed your connection!"));
    }
}
