package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.otc.common.constant.*;
import com.blockchain.server.otc.common.enums.*;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.common.util.CheckDecimalUtil;
import com.blockchain.server.otc.dto.ad.ListAdDTO;
import com.blockchain.server.otc.dto.ad.ListUserAdDTO;
import com.blockchain.server.otc.dto.ad.PublishAdParamDTO;
import com.blockchain.server.otc.dto.user.UserBaseDTO;
import com.blockchain.server.otc.entity.*;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.AdMapper;
import com.blockchain.server.otc.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AdServiceImpl implements AdService, ITxTransaction {
    private static final Logger LOG = LoggerFactory.getLogger(AdServiceImpl.class);
    @Autowired
    private AdMapper adMapper;
    @Autowired
    private CoinService coinService;
    @Autowired
    private DealStatsService dealStatsService;
    @Autowired
    private UserPayInfoService userPayInfoService;
    @Autowired
    private UserHandleLogService userHandleLogService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private BillService billService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private MarketUserService marketUserService;
    @Autowired
    private UserFeign userFeign;

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void publishBuyAd(PublishAdParamDTO param) {
        //发布广告参数校验方法
        Coin coin = publishAdVerify(param);
        //检查市商否能发布更多广告
        checkMarketAdCount(param.getUserId(), CommonConstans.BUY, coin.getCoinName(), coin.getUnitName());
        //广告手续费率
        BigDecimal serviceCharge = checkAdServiceCharge(coin.getCoinServiceCharge(), param.getUserId());
        //新建广告数据，返回广告流水号
        String adNumber = insertAd(param, CommonConstans.BUY, serviceCharge);
        //新增用户交易数据表
        dealStatsService.insertIsNotExist(param.getUserId());
        //记录用户操作
        insertUserHandleLog(param.getUserId(), adNumber, UserHandleConstants.PUBLISH);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void publishSellAd(PublishAdParamDTO param) {
        //发布广告参数校验方法
        Coin coin = publishAdVerify(param);
        //检查市商否能发布更多广告
        checkMarketAdCount(param.getUserId(), CommonConstans.SELL, coin.getCoinName(), coin.getUnitName());
        //检查卖家是否绑定支付方式
        checkPublishPaysIsBinging(param);
        //判断是否开启广告手续费
        BigDecimal chargeRatio = checkAdServiceCharge(coin.getCoinServiceCharge(), param.getUserId());
        //新建广告数据，返回广告流水号
        String adNumber = insertAd(param, CommonConstans.SELL, chargeRatio);
        //更新钱包并记录资金变动
        publishSellAdHandleWallet(param, adNumber, chargeRatio);
        //新增用户交易数据表
        dealStatsService.insertIsNotExist(param.getUserId());
        //记录用户操作
        insertUserHandleLog(param.getUserId(), adNumber, UserHandleConstants.PUBLISH);
    }

    /***
     * 检查市商否能发布更多广告
     *
     * @param userId
     * @param adType
     */
    private void checkMarketAdCount(String userId, String adType, String coinName, String unitName) {
        String[] adStatus = {AdConstants.DEFAULT, AdConstants.PENDING, AdConstants.UNDERWAY};
        //查询用户是否存在交易中、挂单中、下架中的广告
        List<Ad> ads = adMapper.listUserAdByStatusAndType(userId, adType, coinName, unitName, adStatus);
        //可发布次数
        Integer count;
        //根据买卖查询买卖单可发布次数
        if (checkBuyOrSell(adType)) {
            count = configService.selectMarketBuyAdCount();
        } else {
            count = configService.selectMarketSellAdCount();
        }

        //如果用户未完成广告数量大于限制，抛出异常
        if (count != null && ads.size() >= count) {
            throw new OtcException(OtcEnums.PUBLISH_AD_EXCEED_THE_LIMIT);
        }
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void cancelAd(String userId, String adId) {
        //校验的状态
        String[] realStatus = {AdConstants.DEFAULT, AdConstants.PENDING};
        //撤销广告时的校验方法
        Ad ad = handleAdVerify(userId, adId, realStatus);
        //查询广告伞下是否还有未完成订单
        checkOrdersUnfinished(adId);
        //如果是卖单，解冻余额
        cancelSellAdHandleWallet(ad);
        //新增用户操作记录
        insertUserHandleLog(userId, ad.getAdNumber(), UserHandleConstants.CANCEL);
        //更新修改时间和状态
        ad.setAdStatus(AdConstants.CANCEL);
        ad.setModifyTime(new Date());
        adMapper.updateByPrimaryKeySelective(ad);
    }

    @Override
    @Transactional
    public void setAdToPending(String userId, String adId) {
        //校验的状态
        String[] realStatus = {AdConstants.DEFAULT};
        //下架广告时的校验方法
        Ad ad = handleAdVerify(userId, adId, realStatus);
        //查询广告伞下是否还有未完成订单
        checkOrdersUnfinished(adId);
        //新增用户操作记录
        insertUserHandleLog(userId, ad.getAdNumber(), UserHandleConstants.PENDING);
        //更新修改时间和状态
        ad.setAdStatus(AdConstants.PENDING);
        ad.setModifyTime(new Date());
        adMapper.updateByPrimaryKeySelective(ad);
    }

    @Override
    @Transactional
    public void setAdToDefault(String userId, String adId) {
        //校验的状态
        String[] realStatus = {AdConstants.PENDING};
        Ad ad = handleAdVerify(userId, adId, realStatus);
        //上架时判断剩余数量是否足以交易
        if ((ad.getLastNum().multiply(ad.getPrice())).compareTo(ad.getMinLimit()) < 0) {
            throw new OtcException(OtcEnums.AD_LAST_NUM_LESS_THAN_LIMIT);
        }
        //新增用户操作记录
        insertUserHandleLog(userId, ad.getAdNumber(), UserHandleConstants.DEFAULT);
        //更新修改时间和状态
        ad.setAdStatus(AdConstants.DEFAULT);
        ad.setModifyTime(new Date());
        adMapper.updateByPrimaryKeySelective(ad);
    }

    @Override
    public List<ListAdDTO> listAd(String userId, String adType, String coinName, String unitName, String adStatus[],
                                  String priceSort, String beginTime, String endTime) {
        LOG.info("userId:" + userId + " adType:" + adType + " coinName:" + coinName + " unitName:" + unitName +
                " adStatus:" + adStatus + " priceSort:" + priceSort + " beginTime:" + beginTime + " endTime:" + endTime+" adMapper is:"+adMapper.getClass());
        List<ListAdDTO> listAdDTOS = adMapper.listAd(userId, adType, coinName, unitName, adStatus, priceSort, beginTime, endTime);
        for (ListAdDTO listAdDTO : listAdDTOS) {
            UserBaseDTO userBaseDTO = selectBaseUserByUserId(listAdDTO.getUserId());
            //查询用户信息
            listAdDTO.setNickname(userBaseDTO.getNickName());
            listAdDTO.setUsername(userBaseDTO.getMobilePhone());
            listAdDTO.setUserImg(userBaseDTO.getAvatar());
        }
        return listAdDTOS;
    }

    @Override
    public List<ListUserAdDTO> listUserAd(String userId, String adType, String coinName, String unitName,
                                          String adStatus, String beginTime, String endTime) {
        return adMapper.listUserAd(userId, adType, coinName, unitName, adStatus, beginTime, endTime);
    }

    @Override
    public Ad selectByIdForUpdate(String adId) {
        return adMapper.selectByIdForUpdate(adId);
    }

    @Override
    public Ad selectById(String adId) {
        return adMapper.selectByPrimaryKey(adId);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Ad ad) {
        return adMapper.updateByPrimaryKeySelective(ad);
    }

    /****
     * 发布广告前的校验方法
     * @param param
     * @return
     */
    private Coin publishAdVerify(PublishAdParamDTO param) {
        //检查发布参数
        checkPublishParam(param);
        //判断用户是否可以发布广告
        marketUserService.checkMarketUser(param.getUserId());
        //检查密码
        walletService.isPassword(param.getPass());
        //检查币对
        Coin coin = checkCoinIsNull(param.getCoinName(), param.getUnitName());
        //检查发布数量、单价小数长度、单价下限是否合法
        checkPublishNumber(param, coin);
        //检查发布交易额与交易下限是否合法
        checkPublishLimit(param);

        return coin;
    }

    /***
     * 撤销广告时的校验方法
     * 上架、下架、撤销广告时的校验方法
     * @param userId
     * @param adId
     * @return
     */
    private Ad handleAdVerify(String userId, String adId, String[] status) {
        //广告id是否为空
        if (StringUtils.isBlank(adId)) {
            throw new OtcException(OtcEnums.AD_ID_NULL);
        }
        //查询广告
        Ad ad = adMapper.selectByIdForUpdate(adId);
        //用户id不一致
        if (!ad.getUserId().equals(userId)) {
            throw new OtcException(OtcEnums.AD_USERID_NOT_EQUALS);
        }
        //记录是否外部状态是否匹配广告状态
        boolean statusFlag = false;
        //判断外部传入的status参数
        for (String s : status) {
            //匹配其中一个，改变flag为true
            if (ad.getAdStatus().equals(s)) {
                statusFlag = true;
            }
        }
        //一个都不匹配，抛出异常
        if (!statusFlag) {
            throw new OtcException(OtcEnums.AD_STATUS_ERROR);
        }

        return ad;
    }

    /***
     * 发布卖出广告时更新钱包并记录资金变动
     * @param param
     * @param adNumber
     * @param chargeRatio
     */
    private void publishSellAdHandleWallet(PublishAdParamDTO param, String adNumber, BigDecimal chargeRatio) {
        //增加冻结余额
        BigDecimal freezeBalance = param.getTotalNum().add(param.getTotalNum().multiply(chargeRatio));
        //扣除可用余额
        BigDecimal freeBalance = freezeBalance.multiply(CommonConstans.MINUS_ONE);
        //调用feign冻结余额
        walletService.handleBalance(param.getUserId(), adNumber, param.getCoinName(), param.getUnitName(), freeBalance, freezeBalance);
        //新增资金对账记录
        billService.insertBill(param.getUserId(), adNumber, freeBalance, freezeBalance, BillConstants.PUBLISH, param.getCoinName());
    }

    /****
     * 撤销卖出广告时更新钱包并记录资金变动
     * @param ad
     */
    private void cancelSellAdHandleWallet(Ad ad) {
        //判断是买或卖，买为true
        boolean adTypeFlag = checkBuyOrSell(ad.getAdType());
        //广告为卖出时，退回余额
        if (!adTypeFlag) {
            //手续费
            BigDecimal serviceCharge = ad.getChargeRatio();
            //增加可用余额
            BigDecimal freeBalance = ad.getLastNum().add(ad.getLastNum().multiply(serviceCharge));
            //减少冻结余额
            BigDecimal freezeBalance = freeBalance.multiply(CommonConstans.MINUS_ONE);
            //解冻余额
            walletService.handleBalance(ad.getUserId(), ad.getAdNumber(), ad.getCoinName(), ad.getUnitName(), freeBalance, freezeBalance);
            //新增资金对账记录
            billService.insertBill(ad.getUserId(), ad.getAdNumber(), freeBalance, freezeBalance, BillConstants.CANCEL, ad.getCoinName());
        }
    }

    /***
     * 判断类型是买或卖
     * true是买
     * @param type
     * @return
     */
    private boolean checkBuyOrSell(String type) {
        if (type.equals(CommonConstans.BUY)) {
            return true;
        }
        if (type.equals(CommonConstans.SELL)) {
            return false;
        }
        throw new OtcException(OtcEnums.BUY_OR_SELL_TYPE_ERROR);
    }

    /***
     * 检查币种是否可用
     * @param coinName
     * @param unitName
     * @return
     */
    private Coin checkCoinIsNull(String coinName, String unitName) {
        Coin coin = coinService.selectByCoinAndUnit(coinName, unitName);
        //判空
        if (coin == null) {
            throw new OtcException(OtcEnums.COIN_NULL);
        }
        //是否可用
        if (coin.getStatus().equals(CommonConstans.NO)) {
            throw new OtcException(OtcEnums.COIN_NULL);
        }
        return coin;
    }

    /***
     * 检查发布广告参数
     * @param param
     */
    private void checkPublishParam(PublishAdParamDTO param) {
        if (StringUtils.isBlank(param.getCoinName())) {
            throw new OtcException(OtcEnums.PUBLISH_AD_COIN_NULL);
        }
        if (StringUtils.isBlank(param.getUnitName())) {
            throw new OtcException(OtcEnums.PUBLISH_AD_UNIT_NULL);
        }
        if (StringUtils.isBlank(param.getUserId())) {
            throw new OtcException(OtcEnums.USERID_NULL);
        }
        if (param.getMinLimit() == null || param.getMinLimit().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OtcException(OtcEnums.PUBLISH_AD_MINLIMIT_NULL);
        }
//        if (param.getMinLimit().toString().indexOf(".") != -1) {
//            throw new OtcException(OtcEnums.PUBLISH_AD_MINLIMIT_ERROR);
//        }
        if (param.getPrice() == null || param.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OtcException(OtcEnums.PUBLISH_AD_PRICE_NULL);
        }
        if (param.getTotalNum() == null || param.getTotalNum().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OtcException(OtcEnums.PUBLISH_AD_TOTALNUM_NULL);
        }
        if (param.getPayType() == null || param.getPayType().length == 0) {
            throw new OtcException(OtcEnums.PUBLISH_AD_PAYTYPE_NULL);
        }
    }

    /***
     * 检查发布数量、小数长度是否合法
     * @param param
     * @param coin
     */
    private void checkPublishNumber(PublishAdParamDTO param, Coin coin) {
        //检查单价小数长度
        CheckDecimalUtil.checkDecimal(param.getPrice(), coin.getUnitDecimal(), OtcEnums.PUBLISH_AD_PRICE_ERROR);
        //检查数量小数长度
        CheckDecimalUtil.checkDecimal(param.getTotalNum(), coin.getCoinDecimal(), OtcEnums.PUBLISH_AD_TOTALNUM_ERROR);
    }

    /***
     * 校验支付信息是否匹配枚举
     * 并返回支付信息拼接字符串
     * @param paysType
     * @return
     */
    private String checkPaysTypeIsRealAndGeneratePayStr(String[] paysType) {
        //最终返回的支付类型拼接字符串
        String pays = "";
        for (int i = 0; i < paysType.length; i++) {
            String pay = paysType[i];
            //是否是银行类型
            boolean isBank = pay.equals(UserPayConstants.BANK);
            //是否是微信类型
            boolean isWx = pay.equals(UserPayConstants.WX);
            //是否是支付宝类型
            boolean isZfb = pay.equals(UserPayConstants.ZFB);
            //如果以上三个类型都不匹配，抛出异常
            if (!isBank && !isWx && !isZfb) {
                throw new OtcException(OtcEnums.PUBLISH_AD_PAY_ERROR);
            }
            //如果是数组的最后一个，不拼接逗号
            if (i == paysType.length - 1) {
                pays += pay;
            } else {
                pays += pay + ",";
            }
        }
        return pays;
    }

    /***
     * 检查发布卖单时，用户选择的支付方式是否已绑定
     * @param param
     */
    private void checkPublishPaysIsBinging(PublishAdParamDTO param) {
        for (String pay : param.getPayType()) {
            UserPayInfo userPayInfo = userPayInfoService.selectByUserIdAndPayType(param.getUserId(), pay);
            if (userPayInfo == null) {
                if (pay.equals(UserPayConstants.BANK)) {
                    throw new OtcException(OtcEnums.PUBLISH_AD_PAY_BANK_NULL);
                }
                if (pay.equals(UserPayConstants.WX)) {
                    throw new OtcException(OtcEnums.PUBLISH_AD_PAY_WX_NULL);
                }
                if (pay.equals(UserPayConstants.ZFB)) {
                    throw new OtcException(OtcEnums.PUBLISH_AD_PAY_ZFB_NULL);
                }
            }
        }
    }

    /***
     * 检查发布数量、单价与交易上/下限是否合法
     * @param param
     */
    private void checkPublishLimit(PublishAdParamDTO param) {
        BigDecimal turnover = param.getPrice().multiply(param.getTotalNum());
        //判断交易总额是否比交易下限低
        if (turnover.compareTo(param.getMinLimit()) < 0) {
            throw new OtcException(OtcEnums.PUBLISH_AD_MINLIMIT_TOO_HIGH);
        }
    }

    /***
     * 新增广告记录，返回广告流水号
     * @param param
     * @param adType
     * @param coinChargeRaito
     * @return adNumber
     */
    private String insertAd(PublishAdParamDTO param, String adType, BigDecimal coinChargeRaito) {
        //检查发布支付类型与枚举是否正确，并返回支付信息字符串
        String pays = checkPaysTypeIsRealAndGeneratePayStr(param.getPayType());

        Ad ad = new Ad();
        Date now = new Date();
        String adId = UUID.randomUUID().toString();
        String adNumber = createAdNumber();
        ad.setId(adId);
        ad.setUserId(param.getUserId());
        ad.setAdNumber(adNumber);
        ad.setTotalNum(param.getTotalNum());
        ad.setLastNum(param.getTotalNum());
        ad.setPrice(param.getPrice());
        ad.setMaxLimit(param.getPrice().multiply(param.getTotalNum()).setScale(CommonConstans.LIMIT_CNY_DECIMAL, BigDecimal.ROUND_DOWN));
        ad.setMinLimit(param.getMinLimit().setScale(CommonConstans.LIMIT_CNY_DECIMAL, BigDecimal.ROUND_DOWN));
        ad.setChargeRatio(coinChargeRaito);
        ad.setUnitName(param.getUnitName());
        ad.setCoinName(param.getCoinName());
        ad.setAdPay(pays);
        ad.setAdStatus(AdConstants.DEFAULT);
        ad.setAdType(adType);
        ad.setAdRemark(param.getRemark());
        ad.setCreateTime(now);
        ad.setModifyTime(now);
        adMapper.insertSelective(ad);
        return adNumber;
    }

    /***
     * 新增用户操作日志
     * @param userId
     * @param adNumber
     * @param handleType
     * @return
     */
    private int insertUserHandleLog(String userId, String adNumber, String handleType) {
        return userHandleLogService.insertUserHandleLog(userId, adNumber, handleType, UserHandleConstants.AD);
    }

    /***
     * 查询广告伞下是否还有未完成订单
     * @param adId
     */
    private void checkOrdersUnfinished(String adId) {
        //查询广告伞下是否还有未完成订单
        boolean flag = orderService.checkOrdersUnfinished(adId);
        //true存在未完成订单，抛出异常
        if (flag) {
            throw new OtcException(OtcEnums.CANCEL_AD_ORDERS_UNFINISHED);
        }
    }

    /***
     * 查询用户主体信息
     * @param userId
     * @return
     */
    private UserBaseDTO selectBaseUserByUserId(String userId) {
        ResultDTO<UserBaseDTO> result = userFeign.selectUserInfoById(userId);
        return result.getData();
    }

    /***
     * 生成广告随机流水号
     * @return
     */
    private String createAdNumber() {
        return System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    /***
     * 判断是否开启广告手续费、用户是否免手续费
     * @param coinServiceCharge 币种手续费
     * @param userId
     * @return
     */
    private BigDecimal checkAdServiceCharge(BigDecimal coinServiceCharge, String userId) {
        boolean flag = verifyFreeTransaction(userId);
        //用户免除手续费
        if (flag) {
            return BigDecimal.ZERO;
        }
        //查询广告手续费是否开启
        Config config = configService.selectByKey(ConfigConstants.AD_SERVICE_CHARGE);
//        //查询卖家手续费是否开启
//        Config config = configService.selectByKey(ConfigConstants.SELL_SERVICE_CHARGE);
        //如果广告手续费配置为空或者配置状态为禁用
        if (config == null || config.getStatus().equals(CommonConstans.NO)) {
            //返回0手续费
            return BigDecimal.ZERO;
        }
        //返回手续费
        return coinServiceCharge;
    }

    /***
     * 查询用户是否免交易手续费
     * true是免手续费
     * @param userId
     * @return
     */
    private boolean verifyFreeTransaction(String userId) {
        ResultDTO<Boolean> result = userFeign.verifyFreeTransaction(userId);
        return result.getData();
    }
}
