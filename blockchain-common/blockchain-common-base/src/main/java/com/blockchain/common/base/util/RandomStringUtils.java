package com.blockchain.common.base.util;

import java.util.Random;

public class RandomStringUtils {

    private static final char[] BASE = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'm', 'n', 'p', 'q', 'r', 's', 't',
            '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int BIN_LEN = BASE.length;

    private static final Random RANDOM = new Random();


    /**
     * 生成指定长度随机字符串，去掉容易混淆的字符，如l、1，o、0等
     *
     * @param len 长度
     * @return
     */
    public static String random(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(BASE[RANDOM.nextInt(BIN_LEN)]);
        }
        return sb.toString();
    }

}