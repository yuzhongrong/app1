package com.blockchain.server.currency.service.impl;

import com.blockchain.server.currency.dto.CurrencyPairDTO;
import com.blockchain.server.currency.mapper.CurrencyPairMapper;
import com.blockchain.server.currency.model.CurrencyPair;
import com.blockchain.server.currency.service.CurrencyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyPairServiceImpl implements CurrencyPairService {

    @Autowired
    private CurrencyPairMapper currencyPairMapper;

    @Override
    public CurrencyPair get(String currencyPair) {
        return currencyPairMapper.selectByPrimaryKey(currencyPair);
    }

    @Override
    public void update(CurrencyPair currencyPair) {
        currencyPairMapper.updateByPrimaryKeySelective(currencyPair);
    }

    /**
     * 获取可用币对列表
     *
     * @return
     */
    @Override
    public List<CurrencyPairDTO> getUsableList() {
        return currencyPairMapper.getUsableList();
    }

    /**
     * 获取首页列表
     *
     * @return
     */
    @Override
    public List<CurrencyPairDTO> getHomeList() {
        return currencyPairMapper.getHomeList();
    }

    /**
     * 根据主要货币获取其所有币对
     *
     * @param currencyName
     * @return
     */
    @Override
    public List<CurrencyPairDTO> getQuoteList(String currencyName) {
        return currencyPairMapper.getQuoteList("%-" + currencyName);
    }

}
