package com.blockchain.server.otc.common.util;

import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;

import java.math.BigDecimal;

public class CheckDecimalUtil {

    /***
     * 检查数据小数长度是否符合
     * @param checkBigDecimal
     * @param decimal
     * @param enums
     */
    public static void checkDecimal(BigDecimal checkBigDecimal, Integer decimal, OtcEnums enums) {
        //转为String类型
        String str = checkBigDecimal.toPlainString();
        //存在小数点
        if (str.indexOf(".") != -1) {
            //判断小数位长度是否超出限制
            if ((str.length() - str.indexOf(".") - 1) > decimal) {
                throw new OtcException(enums);
            }
        }
    }
}
