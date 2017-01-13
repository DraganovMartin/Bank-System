package database.databaseController;

import dataModel.CurrencyConverter;
import dataModel.Money;
import dataModel.models.*;

import java.sql.*;
import java.sql.Connection;
import java.util.concurrent.ExecutionException;
import java.util.zip.DataFormatException;
/**
 * Database connection and controller between the database (MYSQL on db4free server) and the project.
 * Before any tests make sure that you added the data base drivers.
 */

/**
 * Created by Nikolay on 12/14/2016.
 */
public class DatabaseController {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://db4free.net:3306/banksystem";

    public static final String user = "banksystem_root";
    public static final String password = "banksystemroot";

    /**
     *  регистрация (име, парола)
     2. логин (име, парола)
     3. теглене (пари, сметка)
     4. внасяне (пари, сметка)
     5. трансфер (пари, сметка1, сметка2)
     6. проверка наличност (сметка)
     7. проверка история (сметка)
     и това е
     */


    protected   PreparedStatement lastId;


    protected static Connection connDatabase = null;

    public DatabaseController(){
    }

    public static void open(){
        try {
            Class.forName(JDBC_DRIVER);
            connDatabase = DriverManager.getConnection(DB_URL,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();// or do log
        } catch (SQLException e) {
            e.printStackTrace();// or do log
        }
    }


    public static void close(){
        try{
            if(connDatabase != null) {
                connDatabase.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOpen() {
        return connDatabase != null;
    }
}