package client;

import dataModel.Money;
import dataModel.models.Currency;
import java.awt.Color;
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

public class ClientGUI {

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
    JPanel buttons;
    JScrollPane buttons_scrollpane;

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

    // EXIT button:
    JButton exitButton;
    public static final String EXITBUTTON_TEXT = "EXIT...";

    public ClientGUI(String client_keystore_location, String client_keystore_password, String hostname, int hostport) {
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

    public final synchronized void disconnect() {
        JOptionPane.showMessageDialog(this.mainFrame, "Trying to disconnect...");
        try {
            client.stop();
        } catch (Exception ex) {
        }
        JOptionPane.showMessageDialog(mainFrame, "Connection to server closed!");
        mainFrame.dispose();
    }

    public synchronized void send(Message message) throws IOException {
        if (this.client.isConnected()) {
            this.client.send(message);
            JOptionPane.showMessageDialog(this.mainFrame, "Message sent!");
        } else {
            JOptionPane.showMessageDialog(this.mainFrame, "Not connected to server!");
        }
    }

    public synchronized Message handleUpdate(Update update) {
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
        JOptionPane.showMessageDialog(this.mainFrame, "Disconnected from server!");
        return null;
    }

    public synchronized void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public static void main(String[] args) {

        final String HOSTNAME = "localhost";
        final int HOSTPORT = 15000;
        final String CLIENT_KEYSTORE_PASSWORD = "client";
        final String CLIENT_KEYSTORE_LOCATION = "D:\\example_certificates\\client.keystore";

        ClientGUI clientGUI = new ClientGUI(CLIENT_KEYSTORE_LOCATION, CLIENT_KEYSTORE_PASSWORD, HOSTNAME, HOSTPORT);
    }
}
