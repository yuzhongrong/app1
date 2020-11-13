package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.entity.QuantizedAccount;
import com.blockchain.server.quantized.mapper.AccountMapper;
import com.blockchain.server.quantized.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-25 16:12
 **/
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public QuantizedAccount findOne() {
        return accountMapper.selectAll().get(0);
    }
}
