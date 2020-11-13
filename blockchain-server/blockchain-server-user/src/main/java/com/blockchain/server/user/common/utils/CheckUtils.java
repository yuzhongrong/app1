package com.blockchain.server.user.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * \* <p>Desciption:数据校检验证工具类</p>
 * \*
 * \* CreateTime: 2018/8/10 0010 15:49
 * \* User: YeHao
 * \
 */
public class CheckUtils {
    /**
     * 校验手机号
     *
     * @param phoneNum 手机号
     * @return
     */
    public static boolean checkMobilePhone(String phoneNum) {
        if (!StringUtils.isEmpty(phoneNum)) {
            String reg = "^\\d+$";
            return phoneNum.matches(reg);
        }
        return false;
    }

    /**
     * 校验密码
     * @param password 密码
     * @return
     */
    public static boolean checkPassword(String password) {
        String pattern = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*.]){6,16}$";
        if (password.matches(pattern)) {
            return true;
        }
        return false;
    }

    /**
     * 验证邮箱地址是否正确
     *
     * @param email 邮箱
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-zA-Z0-9_\\-\\.])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 校验微信账号
     *
     * @param wxCode 微信账号
     * @return
     */
    public static boolean checkWeixin(String wxCode) {

        boolean flag = false;
        if (!StringUtils.isEmpty(wxCode)) {

            if (!StringUtils.isEmpty(wxCode)) {
                if (wxCode.contains("@")) {  //验证邮箱号
                    String check = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-zA-Z]{2,}$";
                    Pattern regex = Pattern.compile(check);
                    Matcher matcher = regex.matcher(wxCode);
                    flag = matcher.matches();
                } else {
                    String reg1 = "[1-9]\\d{5,19}";  //qq号 6 - 20
                    String reg2 = "1[3-9]\\d{9}";  //qq号或者手机号 11
                    String reg3 = "[a-zA-Z][-_a-zA-Z0-9]{5,19}"; //微信号带字母的 6-20
                    flag = wxCode.matches(reg1) || wxCode.matches(reg2) || wxCode.matches(reg3);
                }
            }

        }
        return flag;

    }

    /**
     * 校验支付宝账号
     *
     * @param zgbCode 支付宝账号
     * @return
     */
    public static boolean checkZhiFuBao(String zgbCode) {
        boolean flag = false;
        if (checkEmail(zgbCode) || checkWeixin(zgbCode)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 校检银行卡号
     *
     * @param bankCard 银行卡号
     * @return
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeBankCard
     * @return
     */
    private static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

}
