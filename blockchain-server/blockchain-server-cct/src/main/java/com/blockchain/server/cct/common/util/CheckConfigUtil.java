package com.blockchain.server.cct.common.util;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.common.enums.CctEnums;
import com.blockchain.server.cct.common.exception.CctException;
import com.blockchain.server.cct.entity.Config;
import com.blockchain.server.cct.feign.UserFeign;
import com.blockchain.server.cct.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/***
 * 配置信息表相关的配置查询、校验
 */
@Component
public class CheckConfigUtil {

    @Autowired
    private ConfigService configService;
    @Autowired
    private UserFeign userFeign;

    /***
     * 判断是否休市中
     */
    public void checkIsClosed() {
        //查询休市配置信息
        Config config = configService.selectByKey(
                CctDataEnums.CONFIG_SERVICE_REST.getStrVlue(), CctDataEnums.COMMON_STATUS_YES.getStrVlue());

        //如果配置不为空，代表休市中，抛出异常
        if (config != null) {
            throw new CctException(CctEnums.CLOSED);
        }
    }

    /***
     * 查询是否有挂单手续费配置
     * @return Bigdecimal
     */
    public BigDecimal checkIsTakerCharge(String userId) {
        //查询挂单手续费配置信息
        Config config = configService.selectByKey(
                CctDataEnums.CONFIG_TAKER_CHARGE.getStrVlue(), CctDataEnums.COMMON_STATUS_YES.getStrVlue());

        //查询用户是否免交易手续费
        Boolean isServiceCharge = verifyFreeTransaction(userId);
        //如果为true，代表免交易手续费
        if (isServiceCharge) {
            return BigDecimal.ZERO;
        }

        //如果配置为空，返回0
        if (config == null) {
            return BigDecimal.ZERO;
        }

        //返回手续费
        return new BigDecimal(config.getDataValue());
    }

    /***
     * 查询是否有吃单手续费配置
     * @return Bigdecimal
     */
    public BigDecimal checkIsMakerCharge(String userId) {
        //查询吃单手续费配置信息
        Config config = configService.selectByKey(
                CctDataEnums.CONFIG_MAKER_CHARGE.getStrVlue(), CctDataEnums.COMMON_STATUS_YES.getStrVlue());

        //查询用户是否免交易手续费
        Boolean isServiceCharge = verifyFreeTransaction(userId);
        //如果为true，代表免交易手续费
        if (isServiceCharge) {
            return BigDecimal.ZERO;
        }

        //如果配置为空，返回0
        if (config == null) {
            return BigDecimal.ZERO;
        }

        //返回手续费
        return new BigDecimal(config.getDataValue());
    }

    /***
     * 检查用户是否免交易手续费
     * @param userId
     * @return
     */
    private Boolean verifyFreeTransaction(String userId) {
        ResultDTO<Boolean> resultDTO = userFeign.verifyFreeTransaction(userId);
        return resultDTO.getData();
    }

}
