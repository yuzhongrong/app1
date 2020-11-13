package com.blockchain.server.btc.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hugq
 * @date 2019/2/16 0016 16:31
 */
@Component
public class UsdtConstans {

    //usdt币种id
    public static int USDT_PROPERTY_ID = 31;

    //usdt币种名称
    public static final String USDT_SYMBOL = "USDT";


    @Value("${btc.usdt_property_id}")
    public void setUsdtPropertyId(int usdtPropertyId) {
        USDT_PROPERTY_ID = usdtPropertyId;
    }

}
