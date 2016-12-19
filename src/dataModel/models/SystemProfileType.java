package dataModel.models;

import java.io.Serializable;

/**
 * Created by Nikolay on 12/19/2016.
 */
public class SystemProfileType implements Serializable {

    private int id;
    private String name;
    private boolean canRead;
    private boolean canWrite;
    private boolean canTransfer;

    public SystemProfileType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public boolean isCanTransfer() {
        return canTransfer;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public void setCanTransfer(boolean canTransfer) {
        this.canTransfer = canTransfer;
    }
}
