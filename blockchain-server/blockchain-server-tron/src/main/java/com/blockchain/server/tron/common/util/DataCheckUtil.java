package com.blockchain.server.tron.common.util;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 检查数据类型工具类
 */
public class DataCheckUtil {

    /**
     * 转账金额格式化
     *
     * @param amount 转账金额
     * @return
     */
    public static BigDecimal strToBigDecimal(String amount) {
        ExceptionPreconditionUtils.checkStringNotBlank(amount, new TronWalletException(TronWalletEnums.NULL_TOKENADDR));
        BigDecimal _amount = BigDecimal.ZERO;
        try {
            _amount = new BigDecimal(amount);
        } catch (Exception e) {
            ExceptionPreconditionUtils.checkStringNotBlank(amount,
                    new TronWalletException(TronWalletEnums.NUMBER_TYPE_ERROR));
        }
        if (_amount.compareTo(BigDecimal.ZERO) > 0) {
            return _amount;
        } else {
            throw new TronWalletException(TronWalletEnums.NUMBER_COUNT_ERROR);
        }
    }

    /**
     * 转账金额格式
     *
     * @param amount
     * @param decimal
     * @return
     */
    public static BigDecimal bitToBigDecimal(String amount, int decimal) {
        BigDecimal _amount = null;
        _amount = new BigDecimal(amount);
        _amount = _amount.divide(BigDecimal.TEN.pow(decimal));
        return _amount;
    }

    /**
     * 转账金额格式
     *
     * @param amount
     * @return
     */
    public static BigDecimal bitToBigDecimal(BigInteger amount) {
        return bitToBigDecimal(amount, TronConstant.TronToken.TRON_TOKEN_DECIMALS);
    }

    /**
     * 转账金额格式
     *
     * @param amount
     * @param decimal
     * @return
     */
    public static BigDecimal bitToBigDecimal(BigInteger amount, int decimal) {
        BigDecimal _amount = null;
        _amount = new BigDecimal(amount);
        _amount = _amount.divide(BigDecimal.TEN.pow(decimal));
        return _amount;
    }

    /**
     * 转账金额格式
     *
     * @param amount
     * @return
     */
    public static BigDecimal bitToBigDecimal(String amount) {
        return bitToBigDecimal(amount, TronConstant.TronToken.TRON_TOKEN_DECIMALS);
    }

    /**
     * 对象转BigInteger
     *
     * @param object
     * @return
     */
    public static BigInteger objToBigInteger(Object object) {
        if (object == null) return null;
        if (!(object instanceof BigInteger)) return null;
        return (BigInteger) object;
    }

    /**
     * 对象转int
     *
     * @param object
     * @return
     */
    public static Integer objToInt(Object object) {
        if (object == null) return null;
        if (!(object instanceof Integer)) return null;
        return (Integer) object;
    }
}
