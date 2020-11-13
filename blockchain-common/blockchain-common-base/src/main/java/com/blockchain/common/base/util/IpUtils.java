package com.blockchain.common.base.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP方法
 *
 * @author hugq
 */
public class IpUtils {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};


    //获取X-Real-IP请求头地址
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        String ip = request.getHeader("X-Real-IP");

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取请求真实有效ip
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getRequestRealIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        String ip = null;

        for (String header : HEADERS_TO_TRY) {
            ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
                if (ip.length() > 15) {
                    if (ip.indexOf(",") > 0) {
                        ip = ip.substring(0, ip.indexOf(","));
                    }
                }
                return ip;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
