package database.databaseController;

import dataModel.models.SystemProfileType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nikolay on 12/22/2016.
 */
public class DatabaseSystemProfileController {
    private Connection connDatabase;

    private PreparedStatement getSystemProfileType;
    private PreparedStatement lastId;

    public DatabaseSystemProfileController(Connection connDatabase){
        this.connDatabase = connDatabase;
        try {
            this.getSystemProfileType = this.connDatabase.prepareStatement("SELECT * FROM systemProfileType WHERE id = ?");
            this.lastId = this.connDatabase.prepareStatement("SELECT LAST_INSERT_ID()");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SystemProfileType getSystemProfileType(int type_id) {
        ResultSet resultSet = null;
        try{
            this.getSystemProfileType.setInt(1,type_id);
            resultSet = this.getSystemProfileType.executeQuery();
            if(resultSet.first()) {
                String name = resultSet.getString("name");
                boolean canRead = resultSet.getBoolean("canRead");
                boolean canWrite = resultSet.getBoolean("canWrite");
                boolean canTransfer = resultSet.getBoolean("canTransfer");
                SystemProfileType type = new SystemProfileType(type_id, name);
                type.setCanRead(canRead);
                type.setCanWrite(canWrite);
                type.setCanTransfer(canTransfer);
                return type;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void closeSystemProfileController(){
        try {
            if(this.lastId != null) {
                this.lastId.close();
            }
            if(this.getSystemProfileType != null) {
                this.getSystemProfileType.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
