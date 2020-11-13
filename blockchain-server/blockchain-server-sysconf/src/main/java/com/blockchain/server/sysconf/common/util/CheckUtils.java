package com.blockchain.server.sysconf.common.util;

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
            String reg = "1[3-9]\\d{9}";
            return phoneNum.matches(reg);
        }
        return false;
    }

    /**
     * 验证电话号码
     *
     * @param phoneNum 电话
     * @return
     */
    public static boolean checkPhone(String phoneNum) {

        boolean flag = false;
        if (StringUtils.isEmpty(phoneNum))
            return flag;
        if (phoneNum.contains("-")) {  //固定电话带区号 3-4
            String reg = "0[1-9]{2,3}-\\d{7,8}";
            flag = phoneNum.matches(reg);
        } else if (phoneNum.contains("+")) { // + 3-5区号 加手机号
            String reg1 = "\\+[1-9]\\d{2,4}1[3-9]\\d{9}"; //手机号
            String reg2 = "\\+[1-9]\\d{2,4}[1-9]\\d{6,7}"; //固定电话
            flag = phoneNum.matches(reg1) || phoneNum.matches(reg2);
        } else {
            String reg1 = "[1-9]\\d{6,7}";  //固定电话不带区号 7-8
            String reg2 = "1[3-9]\\d{9}";   //手机号
            flag = phoneNum.matches(reg1) || phoneNum.matches(reg2);
        }
        return flag;
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
            // String check =
            // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)？\\.)+[a-zA-Z]{2,}$";
            String check = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-zA-Z]{2,}$";
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
