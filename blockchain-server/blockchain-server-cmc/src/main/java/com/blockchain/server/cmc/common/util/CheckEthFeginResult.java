package com.blockchain.server.cmc.common.util;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.exception.RPCException;

/***
 * 校验ethFegin返回值
 */
public class CheckEthFeginResult {

    /***
     * 检查isPassword接口返回值
     * @param resultDTO isPassword接口返回值
     */
    public static void checkIsPassword(ResultDTO resultDTO) {
        if (BaseConstant.REQUEST_SUCCESS != resultDTO.getCode()) {
            throw new RPCException(resultDTO);
        }
    }

}
