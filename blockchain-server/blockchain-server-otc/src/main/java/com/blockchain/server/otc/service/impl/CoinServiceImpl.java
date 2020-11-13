package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.coin.CoinDTO;
import com.blockchain.server.otc.dto.coin.CoinServiceChargeDTO;
import com.blockchain.server.otc.entity.Coin;
import com.blockchain.server.otc.mapper.CoinMapper;
import com.blockchain.server.otc.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinMapper coinMapper;

    @Override
    public Coin selectByCoinAndUnit(String coinName, String unitName) {
        return coinMapper.selectByCoinAndUnit(coinName, unitName);
    }

    @Override
    public List<CoinDTO> listCoin(String coinStatus) {
        return coinMapper.listCoin(coinStatus);
    }

    @Override
    public List<CoinServiceChargeDTO> listCoinServiceCharge() {
        return coinMapper.listCoinServiceCharge();
    }


}
