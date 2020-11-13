package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.common.enums.CctEnums;
import com.blockchain.server.cct.common.exception.CctException;
import com.blockchain.server.cct.dto.coin.TradingOnDTO;
import com.blockchain.server.cct.entity.Coin;
import com.blockchain.server.cct.entity.TradingRecord;
import com.blockchain.server.cct.mapper.CoinMapper;
import com.blockchain.server.cct.service.CoinService;
import com.blockchain.server.cct.service.TradingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinMapper coinMapper;

    @Override
    public Coin selectCoin(String coinName, String unitName, String status) {
        return coinMapper.selectByCoinUnitAndStatus(coinName, unitName, status);
    }

    @Override
    public Coin checkCoinIsYes(String coinName, String unitName) {
        Coin coin = selectCoin(coinName, unitName, CctDataEnums.COMMON_STATUS_YES.getStrVlue());
        if (coin == null) {
            throw new CctException(CctEnums.TRADING_ON_NULL);
        }
        return coin;
    }

    @Override
    public List<TradingOnDTO> listCoinByUnit(String unitName, String stauts) {
        List<Coin> coins = coinMapper.listCoinByUnit(unitName, stauts);
        List<TradingOnDTO> tradingOns = new ArrayList<>();
        for (Coin coin : coins) {
            TradingOnDTO tradingOn = new TradingOnDTO();
            tradingOn.setCoinName(coin.getCoinName());
            tradingOn.setUnitName(coin.getUnitName());
            tradingOn.setUnitDecimals(coin.getUnitDecimals());
            tradingOn.setCoinDecimals(coin.getCoinDecimals());
            tradingOn.setNewestPrice(BigDecimal.ZERO);
            tradingOns.add(tradingOn);
        }
        return tradingOns;
    }

    @Override
    public List<String> listCoinGroupByUnit() {
        return coinMapper.listCoinGroupByUnit();
    }
}
