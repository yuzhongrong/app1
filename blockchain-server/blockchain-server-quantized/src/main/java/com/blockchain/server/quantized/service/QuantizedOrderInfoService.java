package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.entity.QuantizedOrderInfo;

import java.util.Date;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-30 17:48
 **/
public interface QuantizedOrderInfoService {
    int insert(QuantizedOrderInfo quantizedOrderInfo);

    int update(QuantizedOrderInfo quantizedOrderInfo);

    List<QuantizedOrderInfo> listByStatusAndCreateTime(String status, Date createTime);

    QuantizedOrderInfo selectByPrimaryKeyForUpdate(String id);
}
