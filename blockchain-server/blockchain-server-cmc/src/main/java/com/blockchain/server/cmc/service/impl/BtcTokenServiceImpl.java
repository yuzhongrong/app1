package com.blockchain.server.cmc.service.impl;

import com.blockchain.server.cmc.common.constants.BtcConstans;
import com.blockchain.server.cmc.common.constants.UsdtConstans;
import com.blockchain.server.cmc.common.enums.BtcEnums;
import com.blockchain.server.cmc.common.exception.BtcException;
import com.blockchain.server.cmc.dto.BtcTokenDTO;
import com.blockchain.server.cmc.entity.BtcToken;
import com.blockchain.server.cmc.mapper.BtcTokenMapper;
import com.blockchain.server.cmc.service.BtcTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BtcTokenServiceImpl implements BtcTokenService {
    @Autowired
    private BtcTokenMapper btcTokenMapper;

    @Autowired
    private BtcTokenService btcTokenService;

    @Override
    public List<BtcTokenDTO> listToken() {
        return btcTokenMapper.listToken();
    }

    @Override
    public BtcTokenDTO selectTokenById(Integer tokenId) {
        //校验币种id
        btcTokenService.getAndVerifyTokenNameById(tokenId);
        return btcTokenMapper.selectTokenById(tokenId);
    }

    @Override
    public int getAndVerifyTokenIdByName(String tokenName) {
        if (tokenName.equalsIgnoreCase(BtcConstans.BTC_SYMBOL)) {
            return BtcConstans.BTC_PROPERTY_ID;
        } else if (tokenName.equalsIgnoreCase(UsdtConstans.USDT_SYMBOL)) {
            return UsdtConstans.USDT_PROPERTY_ID;
        } else {
            throw new BtcException(BtcEnums.INEXISTENCE_TOKENNAME);
        }
    }

    @Override
    public String getAndVerifyTokenNameById(int tokenId) {
        if (tokenId == BtcConstans.BTC_PROPERTY_ID) {
            return BtcConstans.BTC_SYMBOL;
        } else if (tokenId == UsdtConstans.USDT_PROPERTY_ID) {
            return UsdtConstans.USDT_SYMBOL;
        } else {
            throw new BtcException(BtcEnums.INEXISTENCE_TOKENID);
        }
    }

    @Override
    public BtcToken selectByTokenName(String coinName) {
        BtcToken where = new BtcToken();
        where.setTokenSymbol(coinName);
        BtcToken token = btcTokenMapper.selectOne(where);
        if(token == null){
            throw new BtcException(BtcEnums.INEXISTENCE_TOKENNAME);
        }
        return token;
    }

}
