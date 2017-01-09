package dataModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nikolay on 12/21/2016.
 */

/**
 * PasswordConver use static functions for convert and compare passwords.
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

    public static boolean isEqualPasswords(byte[] pass1,byte[] pass2){
        for(int i = 0;i < pass1.length;i++){
            if(pass1[i] != pass2[i]){
                return false;
            }
        }
        return true;
    }

}
