package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.dto.trading.DetailByOrderIdDTO;
import com.blockchain.server.cct.dto.trading.ListUserDetailDTO;
import com.blockchain.server.cct.entity.TradingDetail;
import com.blockchain.server.cct.mapper.TradingDetailMapper;
import com.blockchain.server.cct.service.TradingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradingDetailServiceImpl implements TradingDetailService {

    @Autowired
    private TradingDetailMapper tradingDetailMapper;

    @Override
    @Transactional
    public int insertTradingDetail(TradingDetail detail) {
        return tradingDetailMapper.insertSelective(detail);
    }

    @Override
    public List<ListUserDetailDTO> listUserDetail(String userId, String coinName, String unitName, String beginTime, String lastTime, String status) {
        return tradingDetailMapper.listUserDetail(userId, coinName, unitName, beginTime, lastTime, status);
    }

    @Override
    public List<DetailByOrderIdDTO> listDetailByOrderId(String orderId) {
        return tradingDetailMapper.listDetailByOrderId(orderId);
    }
}
