package com.blockchain.server.user.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;

/**
 * @Datetime: 2020/5/20   11:18
 * @Author: Xia rong tao
 * @title   字符操作工具类
 */

public class StrUtils {


    private static final Logger LOG = LoggerFactory.getLogger(StrUtils.class);

    /**
     * 指定字符编码
     * @param str
     * @param encType  GBK,utf-8 ....
     * @return
     */
    public static String transcoding(String str, String encType) {

        try {
            str = URLEncoder.encode(str, encType);
        } catch (java.io.UnsupportedEncodingException e) {
            LOG.error("utf-8转码发生异常", e);
        }

        return str;
    }
}
