package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.entity.QuantizedOrderInfo;
import com.blockchain.server.quantized.mapper.QuantizedOrderInfoMapper;
import com.blockchain.server.quantized.service.QuantizedOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-30 17:49
 **/
@Service
public class QuantizedOrderInfoServiceImpl implements QuantizedOrderInfoService {

    @Autowired
    QuantizedOrderInfoMapper quantizedOrderInfoMapper;


    @Override
    public int insert(QuantizedOrderInfo quantizedOrderInfo) {
        return quantizedOrderInfoMapper.insert(quantizedOrderInfo);
    }

    @Override
    public int update(QuantizedOrderInfo quantizedOrderInfo) {
        quantizedOrderInfo.setModifyTime(new Date());
        return quantizedOrderInfoMapper.updateByPrimaryKey(quantizedOrderInfo);
    }

    @Override
    public List<QuantizedOrderInfo> listByStatusAndCreateTime(String status, Date createTime) {
        return quantizedOrderInfoMapper.listByStatusAndCreateTime(status,createTime);
    }

    @Override
    public QuantizedOrderInfo selectByPrimaryKeyForUpdate(String id) {
        return quantizedOrderInfoMapper.selectByPrimaryKeyForUpdate(id);
    }
}
