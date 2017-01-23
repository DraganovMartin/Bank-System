package database;

import dataModel.CurrencyConverter;
import dataModel.Money;
import dataModel.ProfileData;
import dataModel.models.BankAccount;
import database.databaseController.*;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class will be use for main connection between all controllers and
 * all messages. All functions require some message and all of them return some response.
 * Created by Nikolay on 1/21/2017.
 */
public class DatabaseHandler {

    /**
     * Authorization for the database.
     */

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://db4free.net:3306/banksystem";

    public static final String user = "banksystem_root";
    public static final String password = "banksystemroot";

    private Connection connection;

    /**
     * Main data base controllers user for controlling the data.
     */
    private DatabaseAuthorizationController authorizationController;
    private DatabaseBankAccountController bankAccountController;
    private DatabaseClientController clientController;
    private DatabaseCurrensyController currensyController;
    private DatabaseSystemProfileController systemProfileController;
    private DatabaseTransfersController transfersController;

    private CurrencyConverter converter;

    public DatabaseHandler() throws ConnectException {
        this.connection = initDatabase();
        if(connection != null){
            throw new ConnectException("The database is unreachable.");
        }
        this.transfersController = new DatabaseTransfersController(this.connection);
        this.systemProfileController = new DatabaseSystemProfileController(this.connection);
        this.currensyController = new DatabaseCurrensyController(this.connection);
        this.clientController = new DatabaseClientController(this.connection);
        this.bankAccountController = new DatabaseBankAccountController(this.connection);
        this.authorizationController =
                new DatabaseAuthorizationController(this.connection,this.clientController,this.systemProfileController);
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
     * @param username
     * @return  ProfileData
     */
    public ProfileData getProfileData(int username){
        return null;
    }
}
