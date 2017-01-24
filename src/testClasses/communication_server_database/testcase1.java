package testClasses.communication_server_database;

import database.DatabaseHandler;
import networking.messageHandlers.MappedMessageHandler;
import networking.messageHandlers.databaseside.LoginRequestHandler;
import networking.messageHandlers.databaseside.RegistrateRequestHandler;
import networking.messages.Update;
import networking.messages.request.LoginRequest;
import networking.messages.request.RegisterRequest;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;

/**
 * Created by Nikolay on 1/24/2017.
 */
public class testcase1 extends JFrame {

    private DatabaseHandler databaseHandler;
    private MappedMessageHandler messageHandler;


    public testcase1(){
        try {
            this.databaseHandler = new DatabaseHandler();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        this.messageHandler = new MappedMessageHandler();
        this.messageHandler.set(LoginRequest.TYPE,new LoginRequestHandler(this.databaseHandler));
        this.messageHandler.set(RegisterRequest.TYPE,new RegistrateRequestHandler(this.databaseHandler));
    }

    private void inti(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }

    public static void main(String[] args){
        testcase1 t = new testcase1();
        Update update = (Update)t.messageHandler.handle(new LoginRequest("niki","dungeon"));
        //Update update = (Update)t.messageHandler.handle(new RegisterRequest("niki","dungeon","Nikolay","Nikolov"));
        System.out.println(update.isSuccessful() + " "+update.getRequest().getUsername());
    }
}
