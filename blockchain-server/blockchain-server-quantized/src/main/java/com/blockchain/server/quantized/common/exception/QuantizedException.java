package com.blockchain.server.quantized.common.exception;

import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import lombok.Data;

/**
 * 自定义基础异常，用于向上跑出可预知异常
 *
 * @author huangxl
 * @create 2018-11-12 11:09
 */
@Data
public class QuantizedException extends BaseException {
    protected int code;
    protected String msg;

    public QuantizedException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public QuantizedException(QuantizedResultEnums rs) {
        this.code = rs.getCode();
        this.msg = rs.getMsg();
    }
}