package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.entity.Bill;
import com.blockchain.server.otc.mapper.BillMapper;
import com.blockchain.server.otc.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    @Transactional
    public int insertBill(String userId, String recordNumber, BigDecimal freeBalance, BigDecimal freezeBalance, String billType, String coinName) {
        Bill bill = new Bill();
        bill.setId(UUID.randomUUID().toString());
        bill.setUserId(userId);
        bill.setRecordNumber(recordNumber);
        bill.setBillType(billType);
        bill.setFreebalance(freeBalance);
        bill.setFreezebalance(freezeBalance);
        bill.setCoinName(coinName);
        bill.setCreateTime(new Date());
        return billMapper.insertSelective(bill);
    }
}
