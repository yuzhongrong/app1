package com.blockchain.server.currency.service.impl;

import com.blockchain.server.currency.dto.CurrencyDTO;
import com.blockchain.server.currency.mapper.CurrencyMapper;
import com.blockchain.server.currency.model.Currency;
import com.blockchain.server.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public Currency get(String currencyName) {
        return currencyMapper.selectByPrimaryKey(currencyName);
    }

    @Override
    public CurrencyDTO getCurrencyInfo(String currencyName, String lg) {
        return currencyMapper.getByCurrencyName(currencyName, lg);
    }

    @Override
    public List<String> getQuoteCurrency() {
        return currencyMapper.getQuoteCurrency();
    }
}
