package com.chinawiserv.utils.encryption;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * Created by sungang on 2017/3/31.
 */
public class EncryptionHelper {
    //加密
    public static String encrypt(String word) {
        String result = "";
        try {
            String KEY_SHA = "SHA";
            MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
            sha.update(word.getBytes());
            byte[] result1 = sha.digest();
            result = (new BASE64Encoder()).encodeBuffer(result1).replaceAll("\\r\\n","").trim();
        } catch (Exception ex) {
            result = word;
        }

        return result;
    }

    public static void main(String[] args) {
        String encrypt_pwd = EncryptionHelper.encrypt("111111");
        System.out.println(encrypt_pwd);
    }
}
