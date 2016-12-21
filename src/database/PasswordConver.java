package database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nikolay on 12/21/2016.
 */
public class PasswordConver {

    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static byte[] convertPssword(String password){
        md.update("12345".getBytes());
        byte byteData[] = md.digest();
        return byteData;
    }

}
