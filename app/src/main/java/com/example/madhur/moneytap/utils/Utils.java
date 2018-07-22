package com.example.madhur.moneytap.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static final String md5(final String url) {
        try{
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for(int i=0; i<messageDigest.length; i++) {
                String h = Integer.toHexString(0XFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" +h;
                hexString.append(h);
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getCurrentLinuxTimeInSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }
}
