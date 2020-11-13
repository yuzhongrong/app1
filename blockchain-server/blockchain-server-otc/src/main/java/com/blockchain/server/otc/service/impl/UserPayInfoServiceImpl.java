package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.common.constant.OrderConstants;
import com.blockchain.server.otc.common.constant.UserPayConstants;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.entity.Ad;
import com.blockchain.server.otc.entity.Order;
import com.blockchain.server.otc.entity.UserPayInfo;
import com.blockchain.server.otc.mapper.UserPayInfoMapper;
import com.blockchain.server.otc.service.AdService;
import com.blockchain.server.otc.service.OrderService;
import com.blockchain.server.otc.service.UserPayInfoService;
import com.blockchain.server.otc.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserPayInfoServiceImpl implements UserPayInfoService {

    @Autowired
    private UserPayInfoMapper userPayInfoMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AdService adService;
    @Autowired
    private WalletService walletService;

    @Override
    public UserPayInfo selectByUserIdAndPayType(String userId, String payType) {
        return userPayInfoMapper.selectByUserIdAndPayType(userId, payType);
    }

    @Override
    public List<UserPayInfo> listUserPay(String userId) {
        return userPayInfoMapper.listByUserId(userId);
    }

    @Override
    public List<UserPayInfo> selectSellUserPayInfoByAdPayType(String userId, String orderId) {
        Order order = orderService.selectById(orderId);
        //返回参数
        List<UserPayInfo> userPayInfos = new ArrayList<>();
        //订单状态新建时，查询广告的支付方法
        if (order.getOrderStatus().equals(OrderConstants.NEW)) {
            //查询广告
            Ad ad = adService.selectById(order.getAdId());
            //获取广告设置的支付信息
            String[] pays = ad.getAdPay().split(",");
            //遍历查询支付信息，添加到集合中
            for (String pay : pays) {
                UserPayInfo userPayInfo = selectByUserIdAndPayType(order.getSellUserId(), pay);
                //防空
                if (userPayInfo == null) {
                    continue;
                }
                userPayInfos.add(userPayInfo);
            }
        } else {
            //其他状态时，查询确认付款后选择的支付方式
            UserPayInfo userPayInfo = selectByUserIdAndPayType(order.getSellUserId(), order.getOrderPayType());
            userPayInfos.add(userPayInfo);
        }

        return userPayInfos;
    }

    @Override
    @Transactional
    public int insertWXorZFB(String userId, String payType, String accountInfo, String codeUrl, String pass) {
        //判空
        if (StringUtils.isBlank(accountInfo)) {
            throw new OtcException(OtcEnums.USER_PAY_ACCOUNTINFO_NULL);
        }
        if (StringUtils.isBlank(codeUrl)) {
            throw new OtcException(OtcEnums.USER_PAY_CODE_URL_NULL);
        }
        //判断是否已存在
        checkUserPayIsExist(userId, payType);
        //校验密码
        walletService.isPassword(pass);
        UserPayInfo userPayInfo = new UserPayInfo();
        Date now = new Date();
        userPayInfo.setUserId(userId);
        userPayInfo.setId(UUID.randomUUID().toString());
        userPayInfo.setAccountInfo(accountInfo);
        userPayInfo.setCollectionCodeUrl(codeUrl);
        userPayInfo.setPayType(payType);
        userPayInfo.setCreateTime(now);
        userPayInfo.setModifyTime(now);
        return userPayInfoMapper.insertSelective(userPayInfo);
    }

    @Override
    @Transactional
    public int insertBank(String userId, String bankNumber, String bankUserName, String bankType, String pass) {
        //判空
        if (StringUtils.isBlank(bankNumber)) {
            throw new OtcException(OtcEnums.USER_PAY_BANK_NUMBER_NULL);
        }
        if (StringUtils.isBlank(bankUserName)) {
            throw new OtcException(OtcEnums.USER_PAY_BANK_USER_NAME_NULL);
        }
        if (StringUtils.isBlank(bankType)) {
            throw new OtcException(OtcEnums.USER_PAY_BANK_TYPE_NULL);
        }
        //判断是否已存在
        checkUserPayIsExist(userId, UserPayConstants.BANK);
        //校验密码
        walletService.isPassword(pass);
        UserPayInfo userPayInfo = new UserPayInfo();
        Date now = new Date();
        userPayInfo.setUserId(userId);
        userPayInfo.setId(UUID.randomUUID().toString());
        userPayInfo.setBankNumber(bankNumber);
        userPayInfo.setBankUserName(bankUserName);
        userPayInfo.setBankType(bankType);
        userPayInfo.setPayType(UserPayConstants.BANK);
        userPayInfo.setCreateTime(now);
        userPayInfo.setModifyTime(now);
        return userPayInfoMapper.insertSelective(userPayInfo);
    }

    @Override
    @Transactional
    public int updateWXorZFB(String userId, String payType, String accountInfo, String codeUrl, String pass) {
        UserPayInfo userPayInfo = checkUserPayIsNull(userId, payType);
        //校验密码
        walletService.isPassword(pass);
        userPayInfo.setAccountInfo(accountInfo);
        userPayInfo.setCollectionCodeUrl(codeUrl);
        userPayInfo.setModifyTime(new Date());
        return userPayInfoMapper.updateByPrimaryKeySelective(userPayInfo);
    }

    @Override
    @Transactional
    public int updateBank(String userId, String bankNumber, String bankUserName, String bankType, String pass) {
        UserPayInfo userPayInfo = checkUserPayIsNull(userId, UserPayConstants.BANK);
        //校验密码
        walletService.isPassword(pass);
        userPayInfo.setBankNumber(bankNumber);
        userPayInfo.setBankUserName(bankUserName);
        userPayInfo.setBankType(bankType);
        userPayInfo.setModifyTime(new Date());
        return userPayInfoMapper.updateByPrimaryKeySelective(userPayInfo);
    }

    /***
     * 判断用户对应支付信息是否 '已存在'
     * @param userId
     * @param payType
     */
    private void checkUserPayIsExist(String userId, String payType) {
        UserPayInfo isExist = selectByUserIdAndPayType(userId, payType);
        if (isExist != null) {
            throw new OtcException(OtcEnums.USER_PAY_INSERT_EXIST);
        }
    }

    /***
     * 判断用户对应支付信息是否 '不存在'
     * @param userId
     * @param payType
     * @return
     */
    private UserPayInfo checkUserPayIsNull(String userId, String payType) {
        UserPayInfo isNull = selectByUserIdAndPayType(userId, payType);
        if (isNull == null) {
            throw new OtcException(OtcEnums.USER_PAY_INSERT_EXIST);
        }
        return isNull;
    }
}
