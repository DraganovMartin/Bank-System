package testClasses.password_test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nikolay on 12/21/2016.
 */
public class test1 {
    public static void main(String []args){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("12345".getBytes());
            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            System.out.println("Digest(in hex format):: " + sb.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
