package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.otc.common.constant.MarketApplyConstants;
import com.blockchain.server.otc.common.constant.MarketUserConstants;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.entity.MarketApply;
import com.blockchain.server.otc.entity.MarketFreeze;
import com.blockchain.server.otc.entity.MarketUser;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.MarketApplyMapper;
import com.blockchain.server.otc.service.ConfigService;
import com.blockchain.server.otc.service.MarketApplyService;
import com.blockchain.server.otc.service.MarketFreezeService;
import com.blockchain.server.otc.service.MarketUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class MarketApplyServiceImpl implements MarketApplyService {

    @Autowired
    private MarketApplyMapper marketApplyMapper;
    @Autowired
    private MarketFreezeService marketFreezeService;
    @Autowired
    private MarketUserService marketUserService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserFeign userFeign;

    @Override
    @Transactional
    public void applyMarket(String userId, String applyType) {
        //提交市商申请时，判断用户是否通过高级认证
        checkUserHighAuth(userId, applyType);
        //判断用户市商状态
        MarketUser marketUser = checkMarketUserStatus(userId, applyType);
        //判断用户是否存在未处理的申请记录
        checkUserHasMoreApply(userId, applyType);
        //申请取消市商时，更新市商用户状态
        cancelApplyUpdateMarketUser(marketUser, applyType);
        //新增市商申请
        insertMarketApply(userId, applyType);
    }

    @Override
    public MarketApply getByUserIdAndApplyTypeAndStatus(String userId, String applyType, String status) {
        return marketApplyMapper.selectByUserIdAndApplyTypeAndStatus(userId, applyType, status);
    }

    /***
     * 检查市商用户状态
     * @param userId
     * @param applyType
     */
    private MarketUser checkMarketUserStatus(String userId, String applyType) {
        MarketUser marketUser = marketUserService.getMarketUserByUserId(userId);

        //申请成为市商
        if (applyType.equals(MarketApplyConstants.MARKET)) {
            if (marketUser != null) {
                //市商状态不等于未认证
                if (!marketUser.getStatus().equals(MarketUserConstants.NOTMARKET)) {
                    throw new OtcException(OtcEnums.USER_IS_MRAKET);
                }
            }
        }

        //申请取消市商
        if (applyType.equals(MarketApplyConstants.CANCEL)) {
            //市商用户为空
            if (marketUser == null) {
                throw new OtcException(OtcEnums.MARKET_USER_NULL);
            }
            //市商用户状态不等于市商
            if (!marketUser.getStatus().equals(MarketUserConstants.MARKET)) {
                throw new OtcException(OtcEnums.CANCEL_MARKET_USER_STATUS_ERROR);
            }
        }

        return marketUser;
    }

    /***
     * 申请市商时，检查
     * @param userId
     * @param applyType
     */
    private void checkUserHighAuth(String userId, String applyType) {
        if (applyType.equals(MarketApplyConstants.MARKET)) {
            //判断用户是否通过高级认证
            userFeign.hasHighAuthAndUserList(userId);
        }
    }

    /***
     * 新增市商申请
     * @param userId
     * @param applyType
     * @return
     */
    private int insertMarketApply(String userId, String applyType) {
        MarketApply marketApply = new MarketApply();
        Date now = new Date();
        marketApply.setId(UUID.randomUUID().toString());
        marketApply.setUserId(userId);
        marketApply.setApplyType(applyType);
        marketApply.setCreateTime(now);
        marketApply.setModifyTime(now);
        marketApply.setStatus(MarketApplyConstants.NEW);

        //市商申请时，查询配置表获取保证金信息
        if (applyType.equals(MarketApplyConstants.MARKET)) {
            //查询保证金扣除的代币
            String coin = configService.selectMarketFreezeCoin();
            marketApply.setCoinName(coin);
            //查询保证金扣除的数量
            BigDecimal amount = configService.selectMarketFreezeAmount();
            marketApply.setAmount(amount);
        }

        //取消市商时，查询押金表获取保证金信息
        if (applyType.equals(MarketApplyConstants.CANCEL)) {
            MarketFreeze marketFreeze = marketFreezeService.getByUserId(userId);
            //押金记录不存在，抛出异常
            if (marketFreeze == null) {
                throw new OtcException(OtcEnums.MARKET_FREEZE_NULL);
            }
            marketApply.setCoinName(marketFreeze.getCoinName());
            marketApply.setAmount(marketFreeze.getAmount());
        }

        return marketApplyMapper.insertSelective(marketApply);
    }

    /***
     * 提交取消市商，更新市商用户状态
     * @param marketUser
     * @param applyType
     */
    private void cancelApplyUpdateMarketUser(MarketUser marketUser, String applyType) {
        if (applyType.equals(MarketApplyConstants.CANCEL)) {
            //状态改为取消中
            marketUser.setStatus(MarketUserConstants.CANCELING);
            marketUser.setModifyTime(new Date());
            marketUserService.updateByPrimaryKeySelective(marketUser);
        }
    }

    /***
     * 判断用户是否存在未处理的申请记录
     * @param userId
     * @param applyType
     */
    private void checkUserHasMoreApply(String userId, String applyType) {
        MarketApply marketApply = getByUserIdAndApplyTypeAndStatus(userId, applyType, MarketApplyConstants.NEW);
        if (marketApply != null) {
            throw new OtcException(OtcEnums.MARKET_APPLY_HAS_MORE);
        }
    }
}
