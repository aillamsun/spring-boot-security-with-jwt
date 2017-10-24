package com.chinawiserv.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/2/15.
 */
public class IPUtils {

    /**
     * 获取域名 不包含端口
     *
     * @return
     */
    public static String getDomainNameExitPort(HttpServletRequest request) {
//获取域名
        StringBuffer url = request.getRequestURL();
        String domain_name = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
        if (domain_name.contains(":")) {
            int i = domain_name.length() - domain_name.indexOf(":");
            domain_name = domain_name.substring(0, (i - 2));
        }
        String temp_domain_name = domain_name.substring(domain_name.length() - 1);
        if (!temp_domain_name.contains("/")) {
            domain_name = domain_name + "/";
        }
        return domain_name;
    }

    public static String getDomainNam(HttpServletRequest request) {
//获取域名
        String url = "http://"+request.getLocalAddr()+":"+request.getLocalPort();
        return url;
    }
}
