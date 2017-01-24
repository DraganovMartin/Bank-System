package database;

import dataModel.CurrencyConverter;
import dataModel.Money;
import dataModel.ProfileData;
import dataModel.models.BankAccount;
import dataModel.models.SystemProfile;
import database.databaseController.*;
import networking.messages.Update;
import networking.messages.request.*;
import sun.nio.cs.HistoricallyNamedCharset;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * This class will be use for main connection between all controllers and
 * all messages. All functions require some message and all of them return some response.
 * Created by Nikolay on 1/21/2017.
 */
public class DatabaseHandler implements DatabaseToServerAdapter {

    /**
     * Authorization for the database.
     */

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bank-system";

    public static final String user = "root";
    public static final String password = "";

    private Connection connection;

    /**
     * Main data base controllers user for controlling the data.
     */
    private DatabaseBankAccountController bankAccountController;
    private DatabaseCurrensyController currensyController;
    private DatabaseSystemProfileController systemProfileController;
    private DatabaseTransfersController transfersController;

    private CurrencyConverter converter;

    public DatabaseHandler() throws ConnectException {
        this.connection = initDatabase();
        if(connection == null){
            throw new ConnectException("The database is unreachable.");
        }
        this.transfersController = new DatabaseTransfersController(this.connection);
        this.systemProfileController = new DatabaseSystemProfileController(this.connection);
        this.currensyController = new DatabaseCurrensyController(this.connection);
        this.bankAccountController = new DatabaseBankAccountController(this.connection,this.transfersController);
        this.converter = this.currensyController.getConverter();
    }

    /**
     * Initialize the database connection;
     * @return Connection
     */
    private Connection initDatabase() {
        Connection connDatabase = null;
        try {
            Class.forName(JDBC_DRIVER);
            connDatabase = DriverManager.getConnection(DB_URL,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();// or do log
        } catch (SQLException e) {
            e.printStackTrace();// or do log
        }
        return connDatabase;
    }

    /**
     * This method first of all update the converter
     * and then return it.
     * @return CurrncyConverter
     */
    @Override
    public CurrencyConverter getCurrencyConverter() {
        this.converter = this.currensyController.getConverter();
        return this.converter;
    }

    /**
     * Get the current information about user.
     * @param username
     * @return ProfileData
     */
    @Override
    public ProfileData getUserProfileData(String username) {
        ProfileData.Balance balance = this.bankAccountController.getBankAccounts(username);
        ProfileData.TransferHistory transfers = this.transfersController.getHistory(username);
        /** Update converter */
        this.converter = this.currensyController.getConverter();
        ProfileData profileData = new ProfileData(balance,transfers,this.converter);
        return profileData;
    }

    /**
     * Registration on profile and
     * @param registerRequest входящото съобщение.
     *
     * @return
     */
    @Override
    public Update handleRegisterRequest(RegisterRequest registerRequest) {
        if(registerRequest.getUsername() == null) {
            String userName = this.systemProfileController.registrate(registerRequest.getRegisterUsername(), registerRequest.getRegisterPassword(),
                    registerRequest.getFirstName(), registerRequest.getLastName());
            if(userName != null) {
                registerRequest.setUsername(userName);
                return new Update(RegisterRequest.TYPE, registerRequest, true, null);
            } else {
                return new Update(RegisterRequest.TYPE, registerRequest, false, null);
            }
        }
        return null;
    }

    @Override
    public Update handleLoginRequest(LoginRequest loginRequest) {
        if(loginRequest.getUsername() == null){
            String user = this.systemProfileController.login(loginRequest.getLoginUsername(),loginRequest.getLoginPassword());
            if(user != null){
                loginRequest.setUsername(user);
                return new Update(LoginRequest.TYPE,loginRequest,true,this.getUserProfileData(loginRequest.getUsername()));
            } else {
                return new Update(LoginRequest.TYPE,loginRequest,false,null);
            }
        }
        return null;
    }

    @Override
    public Update handleLogoutRequest(LogoutRequest logoutRequest) {
        return null;
    }

    @Override
    public Update handleChangePasswordRequest(ChangePasswordRequest changePasswordRequest) {
        if(changePasswordRequest.getUsername() != null){
            boolean result = this.systemProfileController
                    .changePassword(changePasswordRequest.getUsername(),changePasswordRequest.getOldPassword(),changePasswordRequest.getNewPassword());

            if(result){
                return new Update(ChangePasswordRequest.TYPE,changePasswordRequest,true,this.getUserProfileData(changePasswordRequest.getUsername()));
            } else {
                return new Update(ChangePasswordRequest.TYPE,changePasswordRequest,false,this.getUserProfileData(changePasswordRequest.getUsername()));
            }
        }
        return null;
    }

    @Override
    public Update handleBalanceRequest(BalanceRequest balanceRequest) {
        if(balanceRequest.getUsername() != null) {
            return new Update(BalanceRequest.TYPE,balanceRequest,true,this.getUserProfileData(balanceRequest.getUsername()));
        }
        return null;
    }

    @Override
    public Update handleTransactionHistoryRequest(TransactionHistoryRequest transactionHistoryRequest) {
        if(transactionHistoryRequest.getUsername() != null) {
            return new Update(BalanceRequest.TYPE,transactionHistoryRequest,true,this.getUserProfileData(transactionHistoryRequest.getUsername()));
        }
        return null;
    }

    @Override
    public Update handleCurrencyRatesRequest(CurrencyRatesRequest currencyRatesRequest) {
        if(currencyRatesRequest.getUsername() != null) {
            return new Update(BalanceRequest.TYPE,currencyRatesRequest,true,this.getUserProfileData(currencyRatesRequest    .getUsername()));
        }
        return null;
    }

    @Override
    public Update handleCreateBankAccountRequest(CreateBankAccountRequest createBankAccountRequest) {
        if(createBankAccountRequest.getUsername() != null){
            int bankAccounIid = this.bankAccountController.setBankAccount(createBankAccountRequest.getUsername(),
                    createBankAccountRequest.getInitialMoney(),createBankAccountRequest.getBankAccountType());
            if(bankAccounIid != 0){
                return new Update(CreateBankAccountRequest.TYPE,createBankAccountRequest,true,
                        this.getUserProfileData(createBankAccountRequest.getUsername()));
            } else {
                return new Update(CreateBankAccountRequest.TYPE,createBankAccountRequest,false,
                        this.getUserProfileData(createBankAccountRequest.getUsername()));
            }
        }
        return null;
    }

    @Override
    public Update handleDepositRequest(DepositRequest depositRequest) {
        if(depositRequest.getUsername() != null) {
            int id = Integer.parseInt(depositRequest.getToBankAccount());
            boolean result = this.bankAccountController.depositing(depositRequest.getMoney(),id,this.converter);

            if (result) {
                return new Update(DepositRequest.TYPE, depositRequest, true,
                        this.getUserProfileData(depositRequest.getUsername()));
            } else {
                return new Update(DepositRequest.TYPE, depositRequest, false,
                        this.getUserProfileData(depositRequest.getUsername()));
            }
        }
        return null;
    }

    @Override
    public Update handleWithdrawRequest(WithdrawRequest withdrawRequest) {
        if(withdrawRequest.getUsername() != null) {
            int id = Integer.parseInt(withdrawRequest.getFromBankAccount());
            boolean result = this.bankAccountController.draw(withdrawRequest.getMoney(),id,this.converter);

            if (result) {
                return new Update(DepositRequest.TYPE, withdrawRequest, true,
                        this.getUserProfileData(withdrawRequest.getUsername()));
            } else {
                return new Update(DepositRequest.TYPE, withdrawRequest, false,
                        this.getUserProfileData(withdrawRequest.getUsername()));
            }
        }
        return null;
    }

    @Override
    public Update handleTransferRequest(TransferRequest transferRequest) {
        int from = Integer.parseInt(transferRequest.getFromBankAccount());
        int to = Integer.parseInt(transferRequest.getToBankAccount());
        boolean result = this.bankAccountController.transfer(transferRequest.getMoney(),from,to,this.converter);

        if(result){
            return new Update(TransferRequest.TYPE,transferRequest,true,
                    this.getUserProfileData(transferRequest.getUsername()));
        } else {
            return new Update(TransferRequest.TYPE,transferRequest,false,
                    this.getUserProfileData(transferRequest.getUsername()));
        }
    }
}
