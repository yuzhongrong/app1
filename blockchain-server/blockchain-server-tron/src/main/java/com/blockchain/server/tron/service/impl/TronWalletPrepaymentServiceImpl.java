package com.blockchain.server.tron.service.impl;


import com.blockchain.server.tron.dto.PrepaymentInsertInDTO;
import com.blockchain.server.tron.dto.PrepaymentInsertOutDTO;
import com.blockchain.server.tron.entity.TronWalletPrepayment;
import com.blockchain.server.tron.mapper.TronWalletPrepaymentMapper;
import com.blockchain.server.tron.service.TronWalletPrepaymentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * 以太坊商城——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class TronWalletPrepaymentServiceImpl implements TronWalletPrepaymentService {
    @Autowired
    private TronWalletPrepaymentMapper tronWalletPrepaymentMapper;

    @Override
    public TronWalletPrepayment findById(String id) {
        return tronWalletPrepaymentMapper.selectByPrimaryKey(id);
    }

    @Override
    public TronWalletPrepayment findByTradeNo(String appId, String tradeNo) {
        TronWalletPrepayment where = new TronWalletPrepayment();
        where.setAppId(appId);
        where.setTradeNo(tradeNo);
        return tronWalletPrepaymentMapper.selectOne(where);
    }

    @Override
    public int findByAppIdAndTradeNoLimit(String appId, String tradeNo) {
        return tronWalletPrepaymentMapper.findByAppIdAndTradeNoLimit(appId, tradeNo);
    }

    @Override
    @Transactional
    public PrepaymentInsertOutDTO insert(PrepaymentInsertInDTO inParams) {
        TronWalletPrepayment walletPrepayment = new TronWalletPrepayment();
        BeanUtils.copyProperties(inParams, walletPrepayment);
        String id = UUID.randomUUID().toString();   //  预支付ID
        Date createTime = new Date();   //  创建时间
        walletPrepayment.setId(id);
        walletPrepayment.setCreateTime(createTime);
        tronWalletPrepaymentMapper.insertSelective(walletPrepayment);

        PrepaymentInsertOutDTO outParams = new PrepaymentInsertOutDTO();
        BeanUtils.copyProperties(inParams, outParams);
        outParams.setPrePayId(id);
        outParams.setTimestamp(createTime.getTime());
        return outParams;
    }
}
