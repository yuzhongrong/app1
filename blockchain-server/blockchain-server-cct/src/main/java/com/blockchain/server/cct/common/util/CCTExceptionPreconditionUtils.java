package com.blockchain.server.cct.common.util;

import com.blockchain.server.cct.common.enums.CctEnums;
import com.blockchain.server.cct.common.exception.CctException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class CCTExceptionPreconditionUtils {

    /**
     * 判断字符串不为空或空串
     *
     * @param checkString 需要判断的字符串
     * @param enums       业务异常提示枚举
     */
    public static void checkStringNotBlank(String checkString, CctEnums enums) {
        if (StringUtils.isBlank(checkString)) {
            throw new CctException(enums);
        }
    }

    /**
     * 判断一个对象不可以为空
     *
     * @param obj   需要判断的对象
     * @param enums 业务异常提示枚举
     */
    public static void checkNotNull(Object obj, CctEnums enums) {
        if (obj == null) {
            throw new CctException(enums);
        }
    }

    /***
     * 判断BigDecimal对象是否小于0
     *
     * @param checkBigDecimal
     * @param enums
     */
    public static void checkBigDecimalLessThanOrEqualZero(BigDecimal checkBigDecimal, CctEnums enums) {
        if (checkBigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CctException(enums);
        }
    }
}
