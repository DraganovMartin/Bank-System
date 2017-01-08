package testClasses.password_test;

import dataModel.PasswordConver;

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
            md.update("1234533242342342344234234".getBytes());
            byte[] byteData2 = md.digest();
            for(int i = 0;i < byteData.length;i++){
                System.out.print(byteData[i] + " ");
            }
            System.out.println(PasswordConver.isEqualPasswords(byteData,byteData2));
            for(int i = 0;i < byteData2.length;i++){
                System.out.print(byteData2[i] + " ");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
