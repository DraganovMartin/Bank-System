package client;

import dataModel.CurrencyConverter;
import dataModel.ProfileData;
import dataModel.ProfileData.Balance;
import dataModel.ProfileData.TransferHistory;
import dataModel.models.Currency;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import networking.messageHandlers.MessageHandler;
import networking.messages.DisconnectNotice;
import networking.messages.Message;
import networking.messages.Update;

public class ClientGUI implements MessageHandler {

    public Currency defaultCurrency;

    public static final String MAINFRAMETITLE = "Client";

    JFrame mainFrame;
    JPanel mainPanel;
    JScrollPane mainPanel_scrollpane;

    JScrollPane display_currencyRates_scrollpane;
    JScrollPane display_balance_scrollpane;
    JScrollPane display_transferHistory_scrollpane;
    JScrollPane display_other;

    public ClientGUI(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;

        this.mainFrame = new JFrame(ClientGUI.MAINFRAMETITLE);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));

        this.display_currencyRates_scrollpane = new JScrollPane();
        this.display_balance_scrollpane = new JScrollPane();
        this.display_transferHistory_scrollpane = new JScrollPane();
        this.display_other = new JScrollPane();
        this.display_currencyRates_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.display_balance_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.display_transferHistory_scrollpane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.display_other.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        this.mainPanel.add(this.display_currencyRates_scrollpane);
        this.mainPanel.add(this.display_balance_scrollpane);
        this.mainPanel.add(this.display_transferHistory_scrollpane);
        this.mainPanel.add(this.display_other);

        this.mainPanel_scrollpane = new JScrollPane(this.mainPanel);
        this.mainFrame.add(this.mainPanel_scrollpane);
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // STOP CONNECTION FIRST !!! USE EXIT BUTTON !!!
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }

    public synchronized void update(Update update) {
        JTable currencyRates = update.getProflieData().getCurrencyConverter().getSupportedExchangeRatesAsJTable(this.defaultCurrency, "0.01");
        this.display_currencyRates_scrollpane.setViewportView(currencyRates);
        this.display_currencyRates_scrollpane.setPreferredSize(currencyRates.getPreferredSize());

        JTable balance = update.getProflieData().getBalanceTable();
        this.display_balance_scrollpane.setViewportView(balance);
        this.display_balance_scrollpane.setPreferredSize(balance.getPreferredSize());

        JTable transferHistory = update.getProflieData().getTransferHistoryTable();
        this.display_transferHistory_scrollpane.setViewportView(transferHistory);
        this.display_transferHistory_scrollpane.setPreferredSize(transferHistory.getPreferredSize());

        this.mainFrame.revalidate();
        this.mainFrame.pack();
    }

    public synchronized void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public Message handle(Message message) {
        String messageType = message.getType();
        switch (messageType) {
            case Update.TYPE: {
                Update update = (Update) message;
                this.update(update);
            }
            break;
            case DisconnectNotice.TYPE: {
                DisconnectNotice disconnectNotice = (DisconnectNotice) message;
                // HANDLE DISCONNECT
            }
            break;
            default: {
                // DO NOTHING
            }
            break;
        }
        return null;
    }

    public static void main(String[] args) {

        Currency defaultCurrency = new Currency("BGN");
        ClientGUI clientGUI = new ClientGUI(defaultCurrency);

        Balance balance1 = new Balance();
        {
            balance1.add("Acc00001", "deposit", "BGN", "1234.56");
            balance1.add("Acc00002", "deposit", "BGN", "3456.78");
            balance1.add("Acc00003", "credit", "USD", "2345.67");
            // etc. etc.
        }
        TransferHistory transferHistory1 = new TransferHistory();
        {
            transferHistory1.add("Transfer00001", "Acc00001", "Acc00002", "BGN", "123.45", "01.01.2017");
            transferHistory1.add("Transfer00002", "Acc00002", "Acc00003", "BGN", "456.78", "02.01.2017");
            transferHistory1.add("Transfer00003", "Acc00003", "Acc00002", "BGN", "345.67", "03.01.2017");
            transferHistory1.add("Transfer00004", "Acc00001", "Acc00002", "BGN", "234.56", "04.01.2017");
            // etc. etc.
        }
        CurrencyConverter currencyConverter1 = new CurrencyConverter();
        {
            currencyConverter1.setCurrencyValue(new Currency("BGN"), "1.00000");
            currencyConverter1.setCurrencyValue(new Currency("USD"), "1.83336");
            currencyConverter1.setCurrencyValue(new Currency("GBP"), "2.25964");
            currencyConverter1.setCurrencyValue(new Currency("CHF"), "1.82294");
            // etc. etc.
        }
        ProfileData profileData1 = new ProfileData(balance1, transferHistory1, currencyConverter1);
        Update update1 = new Update("nodescription", null, true, profileData1);
        clientGUI.update(update1);
    }
}
