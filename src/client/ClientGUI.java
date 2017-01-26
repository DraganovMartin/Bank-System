
package client;

import dataModel.Money;
import dataModel.models.Currency;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import networking.connections.Client;
import networking.messageHandlers.MappedMessageHandler;
import networking.messages.DisconnectNotice;
import networking.messages.Message;
import networking.messages.Update;
import networking.messages.request.BalanceRequest;
import networking.messages.request.ChangePasswordRequest;
import networking.messages.request.CreateBankAccountRequest;
import networking.messages.request.CurrencyRatesRequest;
import networking.messages.request.DepositRequest;
import networking.messages.request.LoginRequest;
import networking.messages.request.LogoutRequest;
import networking.messages.request.RegisterRequest;
import networking.messages.request.TransactionHistoryRequest;
import networking.messages.request.TransferRequest;
import networking.messages.request.WithdrawRequest;
import networking.security.SSLContextFactory;
import testClasses.communication_client_server.Communication_Example_CLIENT_SSL;

/**
 * Клас за графичен потребителски интерфейс към банковата система.
 */
public class ClientGUI {

    boolean isLoggedIn; // дали потребителят е влязъл успешно в системата

    // мрежови елементи - не се пипат:
    public SSLContext sslContext;
    public SocketFactory socketFactory;
    public MappedMessageHandler clientsideHandler;
    public Client client;

    public Currency defaultCurrency;

    // графичен интерфейс - най-горно ниво:
    public static final String MAINFRAMETITLE = "Client";
    JFrame mainFrame;
    JPanel mainPanel;
    JScrollPane mainPanel_scrollpane;

    // графичен интерфейс - разделен на три зони:
    // - информация за валутите, сметките и историята на транзакциите
    // - полета за въвеждане на данни
    // - бутони за изпращане на заявки:
    JScrollPane display_currencyRates_scrollpane;
    JScrollPane display_balance_scrollpane;
    JScrollPane display_transferHistory_scrollpane;
    JPanel controls;
    JScrollPane controls_scrollpane;
    JPanel buttons;
    JScrollPane buttons_scrollpane;

    // лента с главно меню:
    JMenuBar menu_topMenu;
    JMenu menu_Account; // меню за идентифициране на потребителя
    JMenu menu_Update;  // меню за обновяване на информацията
    JMenu menu_Order;   // меню за нареждане на парични операции
    // разделяне на функционалностите според клас диаграмата - use_case_diagram_BG.pdf
    // и клас диаграмата на клиентските съобщения - class_diagram_messages.pdf:
    JMenuItem menu_Account_LoginRequest;           // активира режим за пращане на "LoginRequest"
    JMenuItem menu_Account_RegisterRequest;        // активира режим за пращане на "RegisterRequest"
    JMenuItem menu_Account_ChangePasswordRequest;  // активира режим за пращане на "ChangePasswordRequest"
    JMenuItem menu_Account_LogoutRequest;          // активира режим за пращане на "LogoutRequest"
    JMenuItem menu_Update_BalanceRequest;               // активира режим за пращане на "BalanceRequest"
    JMenuItem menu_Update_TransactionHistoryRequest;    // активира режим за пращане на "TransactionHistoryRequest"
    JMenuItem menu_Update_CurrencyRatesRequest;         // активира режим за пращане на "CurrencyRatesRequest"
    JMenuItem menu_Order_CreateBankAccountRequest;  // активира режим за пращане на "CreateBankAccountRequest"
    JMenuItem menu_Order_DepositRequest;            // активира режим за пращане на "DepositRequest"
    JMenuItem menu_Order_WithdrawRequest;           // активира режим за пращане на "WithdrawRequest"
    JMenuItem menu_Order_TransferRequest;           // активира режим за пращане на "TransferRequest"
    // имена на менютата:
    static final String MENU_ACCOUNT_TEXT = "Account";  // меню за идентифициране на потребителя
    static final String MENU_UPDATE_TEXT = "Update";    // меню за обновяване на информацията
    static final String MENU_ORDER_TEXT = "Order";      // меню за нареждане на парични операции
    static final String MENU_ACCOUNT_LOGINREQUEST_TEXT = "Login";                       // активира режим за пращане на "LoginRequest"
    static final String MENU_ACCOUNT_REGISTERREQUEST_TEXT = "Register";                 // активира режим за пращане на "RegisterRequest"
    static final String MENU_ACCOUNT_CHANGEPASSWORDREQUEST_TEXT = "Change Password";    // активира режим за пращане на "ChangePasswordRequest"
    static final String MENU_ACCOUNT_LOGOUTREQUEST_TEXT = "Logout";                     // активира режим за пращане на "LogoutRequest"
    static final String MENU_UPDATE_BALANCEREQUEST_TEXT = "Balance";                            // активира режим за пращане на "BalanceRequest"
    static final String MENU_UPDATE_TRANSACTIONHISTORYREQUEST_TEXT = "Transaction History";     // активира режим за пращане на "TransactionHistoryRequest"
    static final String MENU_UPDATE_CURRENCYRATESREQUEST_TEXT = "Currency Rates";               // активира режим за пращане на "CurrencyRatesRequest"
    static final String MENU_ORDER_CREATEBANKACCOUNTREQUEST_TEXT = "CreateBankAccount"; // активира режим за пращане на "CreateBankAccountRequest"
    static final String MENU_ORDER_DEPOSITREQUEST_TEXT = "Deposit";                     // активира режим за пращане на "DepositRequest"
    static final String MENU_ORDER_WITHDRAWREQUEST_TEXT = "Withdraw";                   // активира режим за пращане на "WithdrawRequest"
    static final String MENU_ORDER_TRANSFERREQUEST_TEXT = "Transfer";                   // активира режим за пращане на "TransferRequest"

    // класифициране на полетата спрямо това за кои заявки се използват:
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

    // класифициране на бутоните спрямо това за кои заявки се използват:
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

    // EXIT button:
    JButton exitButton;
    public static final String EXITBUTTON_TEXT = "EXIT...";

    /**
     * Клас за графичен потребителск иинтерфейс към банковата система. Ключове и
     * пароли - в папката "documentation/example_certificates/".
     *
     * @param client_keystore_location път до клиентския ключ (файл със SSL
     * сертификати).
     *
     * @param client_keystore_password парола на ключа.
     *
     * @param hostname адрес на сървъра.
     *
     * @param hostport порт на сървъра.
     */
    public ClientGUI(String client_keystore_location, String client_keystore_password, String hostname, int hostport) {
        this.isLoggedIn = false;

        JOptionPane.showMessageDialog(this.mainFrame, "Initializing SSL Encription engine and networking, please wait...");
        // създаване на SSL контекст за криптиране на връзката:
        this.sslContext = SSLContextFactory.getSSLContext(new File(client_keystore_location), client_keystore_password);
        if (this.sslContext == null) {
            JOptionPane.showMessageDialog(this.mainFrame, "SSL encryption engine initialization FAILED!");
            throw new NullPointerException("SSL encryption engine initialization FAILED!");
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, "SSL encryption engine initialization successful!");
            // създаване на SocketFactory за връзката:
            this.socketFactory = sslContext.getSocketFactory();
            if (this.socketFactory == null) {
                JOptionPane.showMessageDialog(this.mainFrame, "Failed to create socket factory!");
                throw new NullPointerException("Failed to create socket factory!");
            } else {
                JOptionPane.showMessageDialog(this.mainFrame, "Socket factory created successfully!");
                // създаване на обработчик на входящи съобщения:
                this.clientsideHandler = new MappedMessageHandler();
                // задаване на обработките:
                this.clientsideHandler.set(Update.TYPE, (Message message) -> handleUpdate((Update) message));
                this.clientsideHandler.set(DisconnectNotice.TYPE, (Message message) -> handleDisconnectNotice((DisconnectNotice) message));
                // създаване на мрежови клиент:
                this.client = new Client(socketFactory, clientsideHandler);
                JOptionPane.showMessageDialog(this.mainFrame, "Networking client initialization successful!");

                // подреждане на графичния интерфейс:
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

                this.buttons = new JPanel(new GridLayout(0, 2));
                {
                    // Send buttons:
                    // RegisterRequest
                    this.sendButton_RegisterRequest = new JButton(ClientGUI.SENDBUTTON_REGISTERREQUEST_TEXT);
                    this.sendButton_RegisterRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new RegisterRequest(
                                        registerUsername.getText(),
                                        registerPassword.getText(),
                                        firstName.getText(),
                                        lastName.getText()));
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_RegisterRequest);
                    // LoginRequest
                    this.sendButton_LoginRequest = new JButton(ClientGUI.SENDBUTTON_LOGINREQUEST_TEXT);
                    this.sendButton_LoginRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new LoginRequest(
                                        loginUsername.getText(),
                                        loginPassword.getText()));
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_LoginRequest);
                    // LogoutRequest
                    this.sendButton_LogoutRequest = new JButton(ClientGUI.SENDBUTTON_LOGOUTREQUEST_TEXT);
                    this.sendButton_LogoutRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new LogoutRequest());
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_LogoutRequest);
                    // ChangePasswordRequest
                    this.sendButton_ChangePasswordRequest = new JButton(ClientGUI.SENDBUTTON_CHANGEPASSWORDREQUEST_TEXT);
                    this.sendButton_ChangePasswordRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new ChangePasswordRequest(
                                        oldPassword.getText(),
                                        newPassword.getText()));
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_ChangePasswordRequest);
                    // BalanceRequest
                    this.sendButton_BalanceRequest = new JButton(ClientGUI.SENDBUTTON_BALANCEREQUEST_TEXT);
                    this.sendButton_BalanceRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new BalanceRequest());
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_BalanceRequest);
                    // TransactionHistoryRequest
                    this.sendButton_TransactionHistoryRequest = new JButton(ClientGUI.SENDBUTTON_TRANSACTIONHISTORYREQUEST_TEXT);
                    this.sendButton_TransactionHistoryRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new TransactionHistoryRequest());
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_TransactionHistoryRequest);
                    // CurrencyRatesRequest
                    this.sendButton_CurrencyRatesRequest = new JButton(ClientGUI.SENDBUTTON_CURRENCYRATESREQUEST_TEXT);
                    this.sendButton_CurrencyRatesRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new CurrencyRatesRequest());
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_CurrencyRatesRequest);
                    // CreateBankAccountRequest
                    this.sendButton_CreateBankAccountRequest = new JButton(ClientGUI.SENDBUTTON_CREATEBANKACCOUNTREQUEST_TEXT);
                    this.sendButton_CreateBankAccountRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new CreateBankAccountRequest(
                                        bankAccountType.getText(),
                                        Money.createMoney(new Currency(moneyCurrency.getText()), moneyAmount.getText())));
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_CreateBankAccountRequest);
                    // DepositRequest
                    this.sendButton_DepositRequest = new JButton(ClientGUI.SENDBUTTON_DEPOSITREQUEST_TEXT);
                    this.sendButton_DepositRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new DepositRequest(
                                        toBankAccount.getText(),
                                        Money.createMoney(new Currency(moneyCurrency.getText()), moneyAmount.getText())));
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_DepositRequest);
                    // WithdrawRequest
                    this.sendButton_WithdrawRequest = new JButton(ClientGUI.SENDBUTTON_WITHDRAWREQUEST_TEXT);
                    this.sendButton_WithdrawRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new WithdrawRequest(
                                        fromBankAccount.getText(),
                                        Money.createMoney(new Currency(moneyCurrency.getText()), moneyAmount.getText())));
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_WithdrawRequest);
                    // TransferRequest
                    this.sendButton_TransferRequest = new JButton(ClientGUI.SENDBUTTON_TRANSFERREQUEST_TEXT);
                    this.sendButton_TransferRequest.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                send(new TransferRequest(
                                        fromBankAccount.getText(),
                                        toBankAccount.getText(),
                                        Money.createMoney(new Currency(moneyCurrency.getText()), moneyAmount.getText())));
                            } catch (IOException ex) {
                                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    this.buttons.add(this.sendButton_TransferRequest);

                    // EXIT button:
                    this.exitButton = new JButton(ClientGUI.EXITBUTTON_TEXT);
                    this.buttons.add(this.exitButton);
                    {
                        this.exitButton.addActionListener((ActionEvent e) -> {
                            disconnect();
                        });
                    }
                }

                this.display_currencyRates_scrollpane = new JScrollPane();
                this.display_balance_scrollpane = new JScrollPane();
                this.display_transferHistory_scrollpane = new JScrollPane();
                this.controls_scrollpane = new JScrollPane(this.controls);
                this.buttons_scrollpane = new JScrollPane(this.buttons);

                this.display_currencyRates_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                this.display_balance_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                this.display_transferHistory_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                this.controls_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                this.buttons_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                this.mainPanel.add(this.display_currencyRates_scrollpane);
                this.mainPanel.add(this.display_balance_scrollpane);
                this.mainPanel.add(this.display_transferHistory_scrollpane);
                this.mainPanel.add(this.controls_scrollpane);
                this.mainPanel.add(this.buttons_scrollpane);

                this.mainPanel_scrollpane = new JScrollPane(this.mainPanel);
                this.mainFrame.add(this.mainPanel_scrollpane);
                this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // STOP CONNECTION FIRST !!! USE EXIT BUTTON !!!

                // добавяне на лентата с главното меню:
                {
                    this.menu_topMenu = new JMenuBar();
                    this.menu_Account = new JMenu(ClientGUI.MENU_ACCOUNT_TEXT);
                    {
                        this.menu_Account_LoginRequest = new JMenuItem(ClientGUI.MENU_ACCOUNT_LOGINREQUEST_TEXT);
                        this.menu_Account_RegisterRequest = new JMenuItem(ClientGUI.MENU_ACCOUNT_REGISTERREQUEST_TEXT);
                        this.menu_Account_ChangePasswordRequest = new JMenuItem(ClientGUI.MENU_ACCOUNT_CHANGEPASSWORDREQUEST_TEXT);
                        this.menu_Account_LogoutRequest = new JMenuItem(ClientGUI.MENU_ACCOUNT_LOGOUTREQUEST_TEXT);
                        this.menu_Account.add(this.menu_Account_LoginRequest);
                        this.menu_Account.add(this.menu_Account_RegisterRequest);
                        this.menu_Account.add(this.menu_Account_ChangePasswordRequest);
                        this.menu_Account.add(this.menu_Account_LogoutRequest);
                        // добавяне на action listener-и:
                        this.menu_Account_LoginRequest.addActionListener((ActionEvent e) -> {
                            on_Account_LoginRequest();
                        });
                        this.menu_Account_RegisterRequest.addActionListener((ActionEvent e) -> {
                            on_Account_RegisterRequest();
                        });
                        this.menu_Account_ChangePasswordRequest.addActionListener((ActionEvent e) -> {
                            on_Account_ChangePasswordRequest();
                        });
                        this.menu_Account_LogoutRequest.addActionListener((ActionEvent e) -> {
                            on_Account_LogoutRequest();
                        });
                        // прикачване на позициите към менюто:
                        this.menu_Account.add(this.menu_Account_LoginRequest);
                        this.menu_Account.add(this.menu_Account_RegisterRequest);
                        this.menu_Account.add(this.menu_Account_ChangePasswordRequest);
                        this.menu_Account.add(this.menu_Account_LogoutRequest);
                    }
                    this.menu_Update = new JMenu(ClientGUI.MENU_UPDATE_TEXT);
                    {
                        this.menu_Update_BalanceRequest = new JMenuItem(ClientGUI.MENU_UPDATE_BALANCEREQUEST_TEXT);
                        this.menu_Update_TransactionHistoryRequest = new JMenuItem(ClientGUI.MENU_UPDATE_TRANSACTIONHISTORYREQUEST_TEXT);
                        this.menu_Update_CurrencyRatesRequest = new JMenuItem(ClientGUI.MENU_UPDATE_CURRENCYRATESREQUEST_TEXT);
                        // добавяне на action listener-и:
                        this.menu_Update_BalanceRequest.addActionListener((ActionEvent e) -> {
                            on_Update_BalanceRequest();
                        });
                        this.menu_Update_TransactionHistoryRequest.addActionListener((ActionEvent e) -> {
                            on_Update_TransactionHistoryRequest();
                        });
                        this.menu_Update_CurrencyRatesRequest.addActionListener((ActionEvent e) -> {
                            on_Update_CurrencyRatesRequest();
                        });
                        // прикачване на позициите към менюто:
                        this.menu_Update.add(this.menu_Update_BalanceRequest);
                        this.menu_Update.add(this.menu_Update_TransactionHistoryRequest);
                        this.menu_Update.add(this.menu_Update_CurrencyRatesRequest);
                    }
                    this.menu_Order = new JMenu(ClientGUI.MENU_ORDER_TEXT);
                    {
                        this.menu_Order_CreateBankAccountRequest = new JMenuItem(ClientGUI.MENU_ORDER_CREATEBANKACCOUNTREQUEST_TEXT);
                        this.menu_Order_DepositRequest = new JMenuItem(ClientGUI.MENU_ORDER_DEPOSITREQUEST_TEXT);
                        this.menu_Order_WithdrawRequest = new JMenuItem(ClientGUI.MENU_ORDER_WITHDRAWREQUEST_TEXT);
                        this.menu_Order_TransferRequest = new JMenuItem(ClientGUI.MENU_ORDER_TRANSFERREQUEST_TEXT);
                        // добавяне на action listener-и:
                        this.menu_Order_CreateBankAccountRequest.addActionListener((ActionEvent e) -> {
                            on_Order_CreateBankAccountRequest();
                        });
                        this.menu_Order_DepositRequest.addActionListener((ActionEvent e) -> {
                            on_Order_DepositRequest();
                        });
                        this.menu_Order_WithdrawRequest.addActionListener((ActionEvent e) -> {
                            on_Order_WithdrawRequest();
                        });
                        this.menu_Order_TransferRequest.addActionListener((ActionEvent e) -> {
                            on_Order_TransferRequest();
                        });
                        // прикачване на позициите към менюто:
                        this.menu_Order.add(this.menu_Order_CreateBankAccountRequest);
                        this.menu_Order.add(this.menu_Order_DepositRequest);
                        this.menu_Order.add(this.menu_Order_WithdrawRequest);
                        this.menu_Order.add(this.menu_Order_TransferRequest);
                    }
                    this.menu_topMenu.add(this.menu_Account);
                    this.menu_topMenu.add(this.menu_Update);
                    this.menu_topMenu.add(this.menu_Order);
                    this.mainFrame.setJMenuBar(this.menu_topMenu);
                }

                // скриване на контролите за изпращане на съобщения:
                this.on_all_hide();

                this.mainFrame.pack();
                this.mainFrame.setVisible(true);

                // свързване със сървъра:
                try {
                    this.connect(hostname, hostport);
                } catch (NullPointerException ex) {
                    this.disconnect();
                }
            }
        }
    }

    /**
     * Свързва мрежовия клиент към сървъра.
     *
     * @param hostname адрес на сървъра.
     *
     * @param hostport порт на сървъра.
     */
    public final synchronized void connect(String hostname, int hostport) {
        JOptionPane.showMessageDialog(this.mainFrame, "Trying to connect...");
        try {
            client.connect(hostname, hostport);
            JOptionPane.showMessageDialog(this.mainFrame, "Connected successfully!");
        } catch (IOException ex) {
            Logger.getLogger(Communication_Example_CLIENT_SSL.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this.mainFrame, "Failed to connect!");
            throw new NullPointerException("Failed to connect!");
        }
    }

    /**
     * Прекратява връзката на мрежовия клиент към сървъра и затваря графичния
     * потребителски интерфейс.
     */
    public final synchronized void disconnect() {
        JOptionPane.showMessageDialog(this.mainFrame, "Trying to disconnect...");
        try {
            client.stop();
        } catch (Exception ex) {
        }
        JOptionPane.showMessageDialog(mainFrame, "Connection to server closed!");
        mainFrame.dispose();
    }

    /**
     * Изпраща съобщение до сървъра, използвайки мрежовия клиент.
     *
     * @param message съобщението за изпращане.
     *
     * @throws IOException при неосъществено изпращане.
     */
    public synchronized void send(Message message) throws IOException {
        if (this.client.isConnected()) {
            this.client.send(message);
            JOptionPane.showMessageDialog(this.mainFrame, "Message sent!");
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, "Not connected to server!");
        }
    }

    /**
     * Извиква се автоматично от мрежовия клиент за обработка на първия от два
     * типа входящо от сървъра съобщение - с данни.
     * <p>
     * Визуализира включените в обекта-съобщение таблици за валутните курсове,
     * за баланса по сметки и за историята на трансферите. Данните се
     * изобразяват в зоната за информация.
     *
     * @param update входящото съобщение от сървъра.
     *
     * @return не връща отговор - клиентският интерфейс само изобразява
     * получените данни.
     */
    public synchronized Message handleUpdate(Update update) {
        this.isLoggedIn = true;

        if (update.getProflieData() != null) {
            if (this.defaultCurrency == null) {
                this.defaultCurrency = update.getProflieData().getCurrencyConverter().getSupportedCurrencies()[0];
            }
            JTable currencyRates = update.getProflieData().getCurrencyConverter().getSupportedExchangeRatesAsJTable(this.defaultCurrency, "0.01");
            this.display_currencyRates_scrollpane.setViewportView(currencyRates);
            if (currencyRates != null) {
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
            if (balance != null) {
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
            if (transferHistory != null) {
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

    /**
     * Извиква се автоматично от мрежовия клиент за обработка на втория от два
     * типа входящо от сървъра съобщение - за прекратяване на връзката.
     *
     * @param disconnectNotice входящото съобщение от сървъра.
     *
     * @return не връща отговор - клиентският интерфейс само изобразява
     * получените данни.
     */
    public synchronized Message handleDisconnectNotice(DisconnectNotice disconnectNotice) {
        this.isLoggedIn = false;

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
        JOptionPane.showMessageDialog(this.mainFrame, "Disconnected from server!");
        return null;
    }

    /**
     * Задава избрана от потребителя валута по подразбиране.
     *
     * @param defaultCurrency валута по подразбиране
     */
    public synchronized void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    // скрива контролите за изпращане на съобщения
    final synchronized void on_all_hide() {
        {
            Component[] all = this.controls.getComponents();
            for (Component entry : all) {
                this.controls.remove(entry);
            }
            this.controls.add(new JLabel("Choose an option from the menu..."));
            this.controls.revalidate();
        }
        {
            Component[] all = this.buttons.getComponents();
            for (Component entry : all) {
                this.buttons.remove(entry);
            }
            this.buttons.add(this.exitButton);
            this.buttons.revalidate();
        }
        this.mainFrame.revalidate();
        this.mainFrame.pack();
    }

    // активира режим за пращане на "LoginRequest"
    final synchronized void on_Account_LoginRequest() {
        if (this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Already logged in!");
        } else {
            {
                Component[] all = this.controls.getComponents();
                for (Component entry : all) {
                    this.controls.remove(entry);
                }
                this.controls.add(this.loginUsername_label);
                this.controls.add(this.loginUsername);
                this.controls.add(this.loginPassword_label);
                this.controls.add(this.loginPassword);
                this.controls.revalidate();
            }
            {
                Component[] all = this.buttons.getComponents();
                for (Component entry : all) {
                    this.buttons.remove(entry);
                }
                this.buttons.add(this.sendButton_LoginRequest);
                this.buttons.add(this.exitButton);
                this.buttons.revalidate();
            }
            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
    }

    // активира режим за пращане на "RegisterRequest"
    final synchronized void on_Account_RegisterRequest() {
        if (this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Already logged in!");
        } else {
            {
                Component[] all = this.controls.getComponents();
                for (Component entry : all) {
                    this.controls.remove(entry);
                }
                this.controls.add(this.registerUsername_label);
                this.controls.add(this.registerUsername);
                this.controls.add(this.registerPassword_label);
                this.controls.add(this.registerPassword);
                this.controls.add(this.firstName_label);
                this.controls.add(this.firstName);
                this.controls.add(this.lastName_label);
                this.controls.add(this.lastName);
                this.controls.revalidate();
            }
            {
                Component[] all = this.buttons.getComponents();
                for (Component entry : all) {
                    this.buttons.remove(entry);
                }
                this.buttons.add(this.sendButton_RegisterRequest);
                this.buttons.add(this.exitButton);
                this.buttons.revalidate();
            }
            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
    }

    // активира режим за пращане на "ChangePasswordRequest"
    final synchronized void on_Account_ChangePasswordRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            {
                Component[] all = this.controls.getComponents();
                for (Component entry : all) {
                    this.controls.remove(entry);
                }
                this.controls.add(this.oldPassword_label);
                this.controls.add(this.oldPassword);
                this.controls.add(this.newPassword_label);
                this.controls.add(this.newPassword);
                this.controls.revalidate();
            }
            {
                Component[] all = this.buttons.getComponents();
                for (Component entry : all) {
                    this.buttons.remove(entry);
                }
                this.buttons.add(this.sendButton_ChangePasswordRequest);
                this.buttons.add(this.exitButton);
                this.buttons.revalidate();
            }
            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
    }

    // активира режим за пращане на "LogoutRequest"
    final synchronized void on_Account_LogoutRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            this.on_all_hide();
            this.disconnect();
        }
    }

    // активира режим за пращане на "BalanceRequest"
    final synchronized void on_Update_BalanceRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            this.on_all_hide();
            try {
                this.send(new BalanceRequest());
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // активира режим за пращане на "TransactionHistoryRequest"
    final synchronized void on_Update_TransactionHistoryRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            this.on_all_hide();
            try {
                this.send(new TransactionHistoryRequest());
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // активира режим за пращане на "CurrencyRatesRequest"
    final synchronized void on_Update_CurrencyRatesRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            this.on_all_hide();
            try {
                this.send(new CurrencyRatesRequest());
            } catch (IOException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // активира режим за пращане на "CreateBankAccountRequest"
    final synchronized void on_Order_CreateBankAccountRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            {
                Component[] all = this.controls.getComponents();
                for (Component entry : all) {
                    this.controls.remove(entry);
                }
                this.controls.add(this.bankAccountType_label);
                this.controls.add(this.bankAccountType);
                this.controls.add(this.moneyAmount_label);
                this.controls.add(this.moneyAmount);
                this.controls.add(this.moneyCurrency_label);
                this.controls.add(this.moneyCurrency);
                this.controls.revalidate();
            }
            {
                Component[] all = this.buttons.getComponents();
                for (Component entry : all) {
                    this.buttons.remove(entry);
                }
                this.buttons.add(this.sendButton_CreateBankAccountRequest);
                this.buttons.add(this.exitButton);
                this.buttons.revalidate();
            }
            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
    }

    // активира режим за пращане на "DepositRequest"
    final synchronized void on_Order_DepositRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            {
                Component[] all = this.controls.getComponents();
                for (Component entry : all) {
                    this.controls.remove(entry);
                }
                this.controls.add(this.toBankAccount_label);
                this.controls.add(this.toBankAccount);
                this.controls.add(this.moneyAmount_label);
                this.controls.add(this.moneyAmount);
                this.controls.add(this.moneyCurrency_label);
                this.controls.add(this.moneyCurrency);
                this.controls.revalidate();
            }
            {
                Component[] all = this.buttons.getComponents();
                for (Component entry : all) {
                    this.buttons.remove(entry);
                }
                this.buttons.add(this.sendButton_DepositRequest);
                this.buttons.add(this.exitButton);
                this.buttons.revalidate();
            }
            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
    }

    // активира режим за пращане на "WithdrawRequest"
    final synchronized void on_Order_WithdrawRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            {
                Component[] all = this.controls.getComponents();
                for (Component entry : all) {
                    this.controls.remove(entry);
                }
                this.controls.add(this.fromBankAccount_label);
                this.controls.add(this.fromBankAccount);
                this.controls.add(this.moneyAmount_label);
                this.controls.add(this.moneyAmount);
                this.controls.add(this.moneyCurrency_label);
                this.controls.add(this.moneyCurrency);
                this.controls.revalidate();
            }
            {
                Component[] all = this.buttons.getComponents();
                for (Component entry : all) {
                    this.buttons.remove(entry);
                }
                this.buttons.add(this.sendButton_WithdrawRequest);
                this.buttons.add(this.exitButton);
                this.buttons.revalidate();
            }
            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
    }

    // активира режим за пращане на "TransferRequest"
    final synchronized void on_Order_TransferRequest() {
        if (!this.isLoggedIn) {
            this.on_all_hide();
            JOptionPane.showMessageDialog(this.mainFrame, "Not logged in!");
        } else {
            {
                Component[] all = this.controls.getComponents();
                for (Component entry : all) {
                    this.controls.remove(entry);
                }
                this.controls.add(this.fromBankAccount_label);
                this.controls.add(this.fromBankAccount);
                this.controls.add(this.toBankAccount);
                this.controls.add(this.toBankAccount_label);
                this.controls.add(this.toBankAccount);
                this.controls.add(this.moneyAmount_label);
                this.controls.add(this.moneyAmount);
                this.controls.add(this.moneyCurrency_label);
                this.controls.add(this.moneyCurrency);
                this.controls.revalidate();
            }
            {
                Component[] all = this.buttons.getComponents();
                for (Component entry : all) {
                    this.buttons.remove(entry);
                }
                this.buttons.add(this.sendButton_TransferRequest);
                this.buttons.add(this.exitButton);
                this.buttons.revalidate();
            }
            this.mainFrame.revalidate();
            this.mainFrame.pack();
        }
    }

    /**
     * Главен метод - предлага стартиране на интерфейс.
     * <p>
     * Съдържа дефиниции по подразбиране на четирите параметъра за свързване:
     * <p>
     * адрес на сървъра
     * <p>
     * порт на сървъра
     * <p>
     * път до клиентския SSL ключ
     * <p>
     * парола за клиентския SSL ключ
     *
     * @param args
     */
    public static void main(String[] args) {

        final String HOSTNAME = "localhost";
        final int HOSTPORT = 15000;
        final String CLIENT_KEYSTORE_LOCATION = "D:\\example_certificates\\client.keystore";
        final String CLIENT_KEYSTORE_PASSWORD = "client";

        ClientGUI clientGUI = new ClientGUI(CLIENT_KEYSTORE_LOCATION, CLIENT_KEYSTORE_PASSWORD, HOSTNAME, HOSTPORT);
    }
}
