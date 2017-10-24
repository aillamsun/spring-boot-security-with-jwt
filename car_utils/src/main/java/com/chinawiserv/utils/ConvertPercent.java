package com.chinawiserv.utils;

/**
 * Created by wangt on 2017/7/12.
 */
public class ConvertPercent {

    //判断是否为16进制数
    public static boolean isHex(char c){
        if(((c >= '0') && (c <= '9')) ||
                ((c >= 'a') && (c <= 'f')) ||
                ((c >= 'A') && (c <= 'F')))
            return true;
        else
            return false;
    }

    public static String convertPercent(String str){
        StringBuilder sb = new StringBuilder(str);

        for(int i = 0; i < sb.length(); i++){
            char c = sb.charAt(i);
            //判断是否为转码符号%
            if(c == '%'){
                if(((i + 1) < sb.length() -1) && ((i + 2) < sb.length() - 1)){
                    char first = sb.charAt(i + 1);
                    char second = sb.charAt(i + 2);
                    //如只是普通的%则转为%25
                    if(!(isHex(first) && isHex(second)))
                        sb.insert(i+1, "25");
                }
                else{//如只是普通的%则转为%25
                    sb.insert(i+1, "25");
                }

            }
        }

        return sb.toString();
    }
}
