package com.blockchain.common.base.exception;

import com.blockchain.common.base.dto.ResultDTO;

/**
 * @author huangxl
 * @create 2018-11-21 21:19
 */
public class RPCException extends BaseException {
    public RPCException(int code, String msg) {
        super(code, msg);
    }
    public RPCException(ResultDTO rs) {
        this.code = rs.getCode();
        this.msg = rs.getMsg();
    }
}
