package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.common.constant.MarketUserConstants;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.entity.MarketUser;
import com.blockchain.server.otc.mapper.MarketUserMapper;
import com.blockchain.server.otc.service.MarketUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MarketUserServiceImpl implements MarketUserService {

    @Autowired
    private MarketUserMapper marketUserMapper;

    @Override
    public void checkMarketUser(String userId) {
        MarketUser marketUser = marketUserMapper.selectByUserId(userId);
        //市商用户为空或者不等于市商状态，抛出异常
        if (marketUser == null || !marketUser.getStatus().equals(MarketUserConstants.MARKET)) {
            throw new OtcException(OtcEnums.USER_NOT_MARKET);
        }
    }

    @Override
    public String getMarketUserStatus(String userId) {
        MarketUser marketUser = marketUserMapper.selectByUserId(userId);
        if (marketUser != null) {
            return marketUser.getStatus();
        }
        return null;
    }

    @Override
    public MarketUser getMarketUserByUserId(String userId) {
        return marketUserMapper.selectByUserId(userId);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(MarketUser marketUser) {
        return marketUserMapper.updateByPrimaryKeySelective(marketUser);
    }
}
