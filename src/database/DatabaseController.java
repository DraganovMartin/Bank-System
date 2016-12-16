package database;

import java.sql.*;
import java.sql.Connection;

/**
 * Created by Nikolay on 12/14/2016.
 */
public abstract class DatabaseController {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/banksystem";

    public static final String user = "root";
    public static final String password = "";

    private Connection connDatabase;

    public DatabaseController(){
        // connection to the database
        try {
            Class.forName(this.JDBC_DRIVER);
            this.connDatabase = DriverManager.getConnection(this.DB_URL,this.user,this.password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();// or do log
        } catch (SQLException e) {
            e.printStackTrace();// or do log
        }
    }

    public void close(){
        try{
            this.connDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
