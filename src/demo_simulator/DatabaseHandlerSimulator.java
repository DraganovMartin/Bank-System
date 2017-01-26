package demo_simulator;

import dataModel.CurrencyConverter;
import dataModel.Money;
import dataModel.ProfileData;
import dataModel.models.Currency;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import networking.messageHandlers.SynchronizedMappedMessageHandler;
import networking.messages.DisconnectNotice;
import networking.messages.Message;
import networking.messages.Update;
import networking.messages.request.BalanceRequest;
import networking.messages.request.ChangePasswordRequest;
import networking.messages.request.CreateBankAccountRequest;
import networking.messages.request.CurrencyRatesRequest;
import networking.messages.request.DepositRequest;
import networking.messages.request.LoginRequest;
import networking.messages.request.RegisterRequest;
import networking.messages.request.TransactionHistoryRequest;
import networking.messages.request.TransferRequest;
import networking.messages.request.WithdrawRequest;

/**
 * Simulates a database management system.
 *
 * @author iliyan
 */
public class DatabaseHandlerSimulator extends SynchronizedMappedMessageHandler {

    SystemProfilesTable systemProfilesTable;
    BankAccountsTable bankAccountsTable;
    TransfersTable transfersTable;
    CurrencyConverter currencyConverter;

    public DatabaseHandlerSimulator() {
        super();
        this.systemProfilesTable = new SystemProfilesTable();
        this.bankAccountsTable = new BankAccountsTable();
        this.transfersTable = new TransfersTable();
        this.currencyConverter = new CurrencyConverter();
        currencyConverter.setCurrencyValue(new Currency("BGN"), "1.00000");
        currencyConverter.setCurrencyValue(new Currency("USD"), "1.85229");
        currencyConverter.setCurrencyValue(new Currency("GBP"), "2.33017");
        currencyConverter.setCurrencyValue(new Currency("EUR"), "1.95583");
        currencyConverter.setCurrencyValue(new Currency("CHF"), "1.81836");
        this.initHandlers();
    }

    final void initHandlers() {
        this.set(RegisterRequest.TYPE, (Message message) -> handleRegisterRequest((RegisterRequest) message));
        this.set(LoginRequest.TYPE, (Message message) -> handleLoginRequest((LoginRequest) message));
        this.set(TransferRequest.TYPE, (Message message) -> handleTransferRequest((TransferRequest) message));
        this.set(DepositRequest.TYPE, (Message message) -> handleDepositRequest((DepositRequest) message));
        this.set(WithdrawRequest.TYPE, (Message message) -> handleWithdrawRequest((WithdrawRequest) message));
        this.set(DisconnectNotice.TYPE, (Message message) -> handleDisconnectNotice((DisconnectNotice) message));
        this.set(ChangePasswordRequest.TYPE, (Message message) -> handleChangePasswordRequest((ChangePasswordRequest) message));
        this.set(BalanceRequest.TYPE, (Message message) -> handleBalanceRequest((BalanceRequest) message));
        this.set(TransactionHistoryRequest.TYPE, (Message message) -> handleTransactionHistoryRequest((TransactionHistoryRequest) message));
        this.set(CurrencyRatesRequest.TYPE, (Message message) -> handleCurrencyRatesRequest((CurrencyRatesRequest) message));
        this.set(CreateBankAccountRequest.TYPE, (Message message) -> handleCreateBankAccountRequest((CreateBankAccountRequest) message));
    }

    ProfileData getProfileData(String username) {
        ProfileData.Balance balance = new ProfileData.Balance();
        TreeMap<String, BankAccount> ownedBankAccounts = new TreeMap<>();
        {
            for (Map.Entry<String, BankAccount> entry : this.bankAccountsTable.data.entrySet()) {
                if (entry.getValue().userName.equals(username)) {
                    ownedBankAccounts.put(entry.getValue().id, entry.getValue());
                    balance.add(
                            entry.getValue().id,
                            entry.getValue().type,
                            entry.getValue().money.getCurrency().toString(),
                            entry.getValue().money.getAmount().toPlainString());
                }
            }
        }
        ProfileData.TransferHistory transferHistory = new ProfileData.TransferHistory();
        {
            for (Map.Entry<String, Transfer> entry : this.transfersTable.data.entrySet()) {
                if ((ownedBankAccounts.get(entry.getValue().fromBankAcount) != null) || (ownedBankAccounts.get(entry.getValue().toBankAccount) != null)) {
                    transferHistory.add(
                            entry.getValue().id,
                            entry.getValue().fromBankAcount,
                            entry.getValue().toBankAccount,
                            entry.getValue().money.getCurrency().toString(),
                            entry.getValue().money.getAmount().toPlainString(),
                            entry.getValue().date);
                }
            }
        }
        return new ProfileData(balance, transferHistory, this.currencyConverter);
    }

    Message handleRegisterRequest(RegisterRequest message) {
        SystemProfile systemProfile = new SystemProfile(message);
        if (this.systemProfilesTable.add(systemProfile)) {
            return new Update("Registered successfully!", message, true, this.getProfileData(message.getRegisterUsername()));
        } else {
            return new DisconnectNotice("Failed to register!");
            //return new Update("Failed to register!", message, false, null);
        }
    }

    Message handleLoginRequest(LoginRequest message) {
        SystemProfile systemProfile = this.systemProfilesTable.get(message.getLoginUsername());
        if (systemProfile != null && systemProfile.userName.equals(message.getLoginUsername()) && systemProfile.password.equals(message.getLoginPassword())) {
            return new Update("Login successful!", message, true, this.getProfileData(message.getLoginUsername()));
        } else {
            return new DisconnectNotice("Failed to login!");
            //return new Update("Failed to login!", message, false, null);
        }
    }

    Message handleTransferRequest(TransferRequest message) {
        Money money = message.getMoney();
        BankAccount from = this.bankAccountsTable.get(message.getFromBankAccount());
        BankAccount to = this.bankAccountsTable.get(message.getToBankAccount());
        if ((money.getAmount().compareTo(BigDecimal.ZERO) > 0) && from != null && to != null && from.userName.equals(message.getUsername())) {
            if (from.money.compareTo(money, this.currencyConverter) >= 0) {
                from.money = from.money.subtract(money, this.currencyConverter);
                to.money = to.money.add(money, this.currencyConverter);
                return new Update("Transfered successfully!", message, true, this.getProfileData(message.getUsername()));
            }
        }
        return new Update("Transfer failed!", message, false, this.getProfileData(message.getUsername()));
    }

    Message handleDepositRequest(DepositRequest message) {
        Money money = message.getMoney();
        BankAccount to = this.bankAccountsTable.get(message.getToBankAccount());
        if ((money.getAmount().compareTo(BigDecimal.ZERO) > 0) && to != null) {
            to.money = to.money.add(money, this.currencyConverter);
            return new Update("Deposited successfully!", message, true, this.getProfileData(message.getUsername()));
        }
        return new Update("Deposit failed!", message, false, this.getProfileData(message.getUsername()));
    }

    Message handleWithdrawRequest(WithdrawRequest message) {
        Money money = message.getMoney();
        BankAccount from = this.bankAccountsTable.get(message.getFromBankAccount());
        if ((money.getAmount().compareTo(BigDecimal.ZERO) > 0) && from != null && from.userName.equals(message.getUsername())) {
            from.money = from.money.subtract(money, this.currencyConverter);
            return new Update("Withdrew successfully!", message, true, this.getProfileData(message.getUsername()));
        }
        return new Update("Withdraw failed!", message, false, this.getProfileData(message.getUsername()));
    }

    Message handleDisconnectNotice(DisconnectNotice message) {
        return new DisconnectNotice("Disconnected from server!");
    }

    Message handleChangePasswordRequest(ChangePasswordRequest message) {
        String username = message.getUsername();
        String oldpass = message.getOldPassword();
        String newpass = message.getNewPassword();
        SystemProfile profile = this.systemProfilesTable.get(username);
        if (profile != null) {
            if (profile.password.equals(oldpass)) {
                profile.password = newpass;
                return new Update("Successfully changed password!", message, true, this.getProfileData(message.getUsername()));
            }
        }
        return new Update("Password change failed!", message, false, this.getProfileData(message.getUsername()));
    }

    Message handleBalanceRequest(BalanceRequest message) {
        return new Update("Balance!", message, true, this.getProfileData(message.getUsername()));
    }

    Message handleTransactionHistoryRequest(TransactionHistoryRequest message) {
        return new Update("Transaction history!", message, true, this.getProfileData(message.getUsername()));
    }

    Message handleCurrencyRatesRequest(CurrencyRatesRequest message) {
        return new Update("Currency rates!", message, true, this.getProfileData(message.getUsername()));
    }

    Message handleCreateBankAccountRequest(CreateBankAccountRequest message) {
        String userName = message.getUsername();
        String type = message.getBankAccountType();
        Money money = message.getInitialMoney();
        boolean successful = this.bankAccountsTable.add(new BankAccount(String.valueOf(this.bankAccountsTable.data.size() + 1), userName, type, money));
        if (successful) {
            return new Update("Creation of bank account successful!", message, true, this.getProfileData(message.getUsername()));
        } else {
            return new Update("Creation of bank account failed!", message, false, this.getProfileData(message.getUsername()));
        }
    }
}
