package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.entity.OrderErr;
import com.blockchain.server.quantized.mapper.OrderErrMapper;
import com.blockchain.server.quantized.service.OrderErrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author: Liusd
 * @create: 2019-04-18 20:07
 **/
@Service
public class OrderErrServiceImpl implements OrderErrService {

    @Autowired
    private OrderErrMapper orderErrMapper;

    @Override
    @Transactional
    public int insert(OrderErr orderErr) {
        return orderErrMapper.insert(orderErr);
    }

}
