package demo;

import dataModel.CurrencyConverter;
import dataModel.ProfileData;
import dataModel.models.Currency;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DatabaseHandler;
import networking.connections.ServerGUI_SSL;
import networking.messageHandlers.MappedMessageHandler;
import networking.messageHandlers.databaseside.*;
import networking.messages.DisconnectNotice;
import networking.messages.Update;
import networking.messages.request.*;

/**
 * A class to start the server using {@link ServerGUI_SSL}.
 *
 * @author iliyan
 */
public class Demo_ServerGUI_SSL {

    public static void main(String[] args) {

        final String SERVER_FRAME_TITLE = "Sever";
        DatabaseHandler dbHandler = null;
        System.out.println("Starting server...");
        // инициализиране на базата
        try {
             dbHandler = new DatabaseHandler();
            {
                // initialize a mapped message handler:
                MappedMessageHandler serversideHandler = new MappedMessageHandler();
                serversideHandler.set(BalanceRequest.TYPE, new BalanceRequestHandler(dbHandler));
                serversideHandler.set(ChangePasswordRequest.TYPE, new ChangePasswordRequestHandler(dbHandler));
                serversideHandler.set(CreateBankAccountRequest.TYPE, new CreateBankAccountRequestHandler(dbHandler));
                serversideHandler.set(CurrencyRatesRequest.TYPE, new CurrencyRateRequestHandler(dbHandler));
                serversideHandler.set(DepositRequest.TYPE, new DepositRequestHandler(dbHandler));
                serversideHandler.set(LoginRequest.TYPE, new LoginRequestHandler(dbHandler));
                serversideHandler.set(RegisterRequest.TYPE, new RegistrateRequestHandler(dbHandler));
                serversideHandler.set(TransferRequest.TYPE, new TransferRequestHandler(dbHandler));
                serversideHandler.set(TransactionHistoryRequest.TYPE, new TransfersHistoryRequestHandler(dbHandler));
                serversideHandler.set(WithdrawRequest.TYPE, new WithdrawRequestHandler(dbHandler));
                // create a server object with GUI:
                ServerGUI_SSL server = new ServerGUI_SSL(serversideHandler, SERVER_FRAME_TITLE);
                // сървърът е готов

                if (server == null)
                {
                    // testing control panel:
                    Random random = new Random();
                    JFrame commandsFramme = new JFrame();
                    JPanel commandsPanel = new JPanel(new GridLayout(0, 2));
                    JLabel targetLabel = new JLabel("Send to connection:");
                    JTextField targetTextField = new JTextField();
                    JButton sendUpdate = new JButton("Send Update...");
                    sendUpdate.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (server.isRunning()) {
                                BigInteger target = new BigInteger(targetTextField.getText());
                                ProfileData.Balance balance = new ProfileData.Balance();
                                ProfileData.TransferHistory transferHistory = new ProfileData.TransferHistory();
                                CurrencyConverter currencyConverter = new CurrencyConverter();
                                ProfileData profileData;
                                {
                                    int MAXCURRENCYCOUNT = 5;
                                    int currencyCount = 2 + random.nextInt() % MAXCURRENCYCOUNT;
                                    for (int i = 0; i < currencyCount; i++) {
                                        Double val = new Double(1 + random.nextInt() % 1000) / 1000;
                                        currencyConverter.setCurrencyValue(new Currency("currency_" + (i + 1)), val.toString());
                                    }

                                    int MAXACCOUNTSCOUNT = 5;
                                    int accountsCount = 2 + random.nextInt() % MAXACCOUNTSCOUNT;
                                    for (int i = 0; i < accountsCount; i++) {
                                        Currency currency = currencyConverter.getSupportedCurrencies()[random.nextInt() % currencyCount];
                                        Double amount = new Double(1 + random.nextInt() % 1000);
                                        balance.add("acc_" + (i + 1), "type", currency.getSymbol(), amount.toString());
                                    }

                                    int MAXTRANSFERSCOUNT = 5;
                                    int transfersCount = 2 + random.nextInt() % MAXTRANSFERSCOUNT;
                                    for (int i = 0; i < transfersCount; i++) {
                                        Currency currency = currencyConverter.getSupportedCurrencies()[random.nextInt() % currencyCount];
                                        Double amount = new Double(1 + random.nextInt() % 1000);
                                        int from = 1 + random.nextInt() % MAXACCOUNTSCOUNT;
                                        int to = 1 + random.nextInt() % MAXACCOUNTSCOUNT;
                                        while (to == from) {
                                            to = 1 + random.nextInt() % MAXACCOUNTSCOUNT;
                                        }
                                        transferHistory.add("transfer_" + (i + 1), "acc_" + from, "acc_" + to, currency.getSymbol(), amount.toString(), "date_" + (i + 1));
                                    }

                                    profileData = new ProfileData(balance, transferHistory, currencyConverter);
                                }
                                Update update = new Update("Update from server", null, true, profileData);
                                server.send(update, target);
                            } else {
                                JOptionPane.showMessageDialog(null, "Server is NOT running!");
                            }
                        }
                    });
                    JButton sendDisconnectNotice = new JButton("Send DisconnectNotice...");
                    sendDisconnectNotice.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (server.isRunning()) {
                                BigInteger target = new BigInteger(targetTextField.getText());
                                server.send(new DisconnectNotice("The server has closed your connection!"), target);
                            } else {
                                JOptionPane.showMessageDialog(null, "Server is NOT running!");
                            }
                        }
                    });
                    commandsPanel.add(targetLabel);
                    commandsPanel.add(targetTextField);
                    commandsPanel.add(sendUpdate);
                    commandsPanel.add(sendDisconnectNotice);
                    commandsFramme.add(commandsPanel);
                    commandsFramme.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    commandsFramme.pack();
                    commandsFramme.setVisible(true);
                }
            }
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
