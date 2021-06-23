package com.jrsoft.business.modules.extInterface.web;

import java.security.MessageDigest;

/**
 * MD5专用工具类
 */
public final class Md5Util {
    private static String[] hex = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F' };
    private Md5Util(){}
    public static String encodeByMd5(String password) {
//        MessageDigest md5 = MessageDigest.getInstance("MD5");
//        byte[] byteArray = md5.digest(password.getBytes());
//        return byteArrayToHexString(byteArray);

        try {
            byte[] btInput = password.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuffer sb = new StringBuffer();
        for(byte b : byteArray){
            String hex = byteToHexString(b);
            sb.append(hex);
        }
        return sb.toString();
    }
    private static String byteToHexString(byte b) {
        int n = b;
        if(n<0){
            n = n+256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hex[d1]+hex[d2];
    }
	
	
}
