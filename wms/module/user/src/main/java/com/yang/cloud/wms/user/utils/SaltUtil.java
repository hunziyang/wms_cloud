package com.yang.cloud.wms.user.utils;

import java.util.Random;

public class SaltUtil {

    private static final char[] chars = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String getSalt() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int index = new Random().nextInt(chars.length);
            stringBuffer.append(chars[index]);
        }
        return stringBuffer.toString();
    }
}
