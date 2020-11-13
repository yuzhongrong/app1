package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.entity.TradingRecord;
import com.blockchain.server.cct.mapper.TradingRecordMapper;
import com.blockchain.server.cct.service.TradingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradingRecordServiceImpl implements TradingRecordService {

    @Autowired
    private TradingRecordMapper tradingRecordMapper;

    @Override
    @Transactional
    public int insertTradingRecord(TradingRecord record) {
        return tradingRecordMapper.insertSelective(record);
    }

    @Override
    public List<TradingRecord> listRecordByCoinAndUnit(String coinName, String unitName) {
        return tradingRecordMapper.listRecordByCoinAndUnit(coinName, unitName);
    }

    @Override
    public TradingRecord getRecordByCoinAndUnitLimitOne(String coinName, String unitName) {
        return tradingRecordMapper.selectByCoinAndUnitLimitOne(coinName, unitName);
    }
}
