package com.marta.admin.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SimpleProtector {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String valueToEnc, String secKey) throws Exception {
        Key key = new SecretKeySpec(secKey.getBytes(), ALGORITHM);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encValue);
        return encryptedValue;
    }

    public static String decrypt(String encryptedValue, String secKey) throws Exception {
    	String decryptedValue = "";
    	try{
    	Key key = new SecretKeySpec(secKey.getBytes(), ALGORITHM);
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
        byte[] decValue = c.doFinal(decordedValue);
        decryptedValue = new String(decValue);
      
       }catch(Exception e){
    	   decryptedValue = "Invalid Number";
    	   e.printStackTrace();
       }
       return decryptedValue;
    }
}