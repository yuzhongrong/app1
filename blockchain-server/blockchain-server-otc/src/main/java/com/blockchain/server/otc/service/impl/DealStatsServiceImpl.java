package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.entity.DealStats;
import com.blockchain.server.otc.mapper.DealStatsMapper;
import com.blockchain.server.otc.service.DealStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DealStatsServiceImpl implements DealStatsService {

    @Autowired
    private DealStatsMapper dealStatsMapper;

    @Override
    @Transactional
    public int insertIsNotExist(String userId) {
        DealStats isNull = dealStatsMapper.selectByPrimaryKey(userId);
        if (isNull != null) {
            return 0;
        }
        DealStats dealStats = new DealStats();
        Date now = new Date();
        dealStats.setUserId(userId);
        dealStats.setCreateTime(now);
        dealStats.setModifyTime(now);
        return dealStatsMapper.insertSelective(dealStats);
    }

    @Override
    public DealStats selectByUserId(String userId) {
        return dealStatsMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional
    public int updateAdTransNum(String userId) {
        this.insertIsNotExist(userId);
        return dealStatsMapper.updateAdTransNum(userId, new Date());
    }

    @Override
    @Transactional
    public int updateAdMarkNum(String userId) {
        this.insertIsNotExist(userId);
        return dealStatsMapper.updateAdMarkNum(userId, new Date());
    }

    @Override
    @Transactional
    public int updateOrderSellNum(String userId) {
        this.insertIsNotExist(userId);
        return dealStatsMapper.updateOrderSellNum(userId, new Date());
    }

    @Override
    @Transactional
    public int updateOrderBuyNum(String userId) {
        this.insertIsNotExist(userId);
        return dealStatsMapper.updateOrderBuyNum(userId, new Date());
    }

}
