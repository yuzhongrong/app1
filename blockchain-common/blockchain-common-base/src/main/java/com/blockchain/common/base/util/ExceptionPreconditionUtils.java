package com.blockchain.common.base.util;

import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class ExceptionPreconditionUtils {
    /**
     * 用于判断参数是否符合条件
     *
     * @param flag 判断的条件
     * @param e    如果对象不符合条件，平台需要返回的业务错误
     */
    public static void checkBool(boolean flag, BaseException e) {
        if (!flag) {
            throw e;
        }
    }

    /**
     * 判断字符串不为空或空串
     *
     * @param checkString 需要判断的字符串
     * @param e           返回的业务错误
     */
    public static void checkStringNotBlank(String checkString, BaseException e) {
        if (StringUtils.isBlank(checkString)) {
            throw e;
        }
    }

    /**
     * 判断一个集合是否为空（包含null和size为0的情况）
     *
     * @param collection
     * @param e
     */
    public static void checkCollectionNotEmpty(Collection collection, BaseException e) {
        if (collection == null || collection.size() == 0) {
            throw e;
        }
    }

    /**
     * 判断一个对象不可以为空
     *
     * @param obj 需要判断的对象
     * @param e   如果对象为空，平台需要返回的业务错误代码
     */
    public static void checkNotNull(Object obj, BaseException e) {
        if (obj == null) {
            throw e;
        }
    }

    /**
     * 检验参数不能为空
     *
     * @param args 多个参数
     */
    public static void notEmpty(Object... args) {
        if (args == null) {
            throw new BaseException(BaseResultEnums.PARAMS_ERROR);
        }
        for (Object obj : args) {
            if (obj instanceof String) {
                if (StringUtils.isEmpty((String) obj)) {
                    throw new BaseException(BaseResultEnums.PARAMS_ERROR);
                }
            }
            if (obj == null) {
                throw new BaseException(BaseResultEnums.PARAMS_ERROR);
            }
        }
    }

}
