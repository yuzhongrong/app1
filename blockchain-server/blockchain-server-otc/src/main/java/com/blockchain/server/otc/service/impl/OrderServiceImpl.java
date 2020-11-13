package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.constant.PushConstants;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.TxForOreDTO;
import com.blockchain.common.base.enums.PushEnums;
import com.blockchain.server.otc.common.constant.*;
import com.blockchain.server.otc.common.enums.*;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.common.util.CheckDecimalUtil;
import com.blockchain.server.otc.common.util.ImUtil;
import com.blockchain.server.otc.dto.order.OrderDTO;
import com.blockchain.server.otc.dto.user.UserBaseDTO;
import com.blockchain.server.otc.entity.*;
import com.blockchain.server.otc.feign.PushFeign;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.OrderMapper;
import com.blockchain.server.otc.redis.OrderCache;
import com.blockchain.server.otc.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService, ITxTransaction {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private PushFeign pushFeign;
    @Autowired
    private AdService adService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private UserHandleLogService userHandleLogService;
    @Autowired
    private DealStatsService dealStatsService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private BillService billService;
    @Autowired
    private UserPayInfoService userPayInfoService;
    @Autowired
    private AppealService appealService;
    @Autowired
    private AppealHandleLogService appealHandleLogService;
    @Autowired
    private ImUtil imUtil;
    @Autowired
    private OrderCache orderCache;

    private static final BigDecimal DECIMAL_DISH = new BigDecimal("0.1"); //下单金额计算的偏差值

    @Override
    public List<Order> selectByAdIdAndStatus(String adId, String... status) {
        return orderMapper.selectByAdIdAndStatus(adId, status);
    }

    @Override
    public List<OrderDTO> listUserOrder(String userId, String coinName, String unitName, String orderType, String orderStatus, String payType) {
        List<OrderDTO> orderDTOS = orderMapper.listUserOrder(userId, coinName, unitName, orderType, orderStatus, payType);
        //设置角色
        for (OrderDTO orderDTO : orderDTOS) {
            //判断用户角色，true是买方
            boolean flag = checkBuyUserOrSellUser(userId, orderDTO.getBuyUserId(), orderDTO.getSellUserId());
            if (flag) {
                orderDTO.setRole(CommonConstans.BUY);
            } else {
                orderDTO.setRole(CommonConstans.SELL);
            }
        }
        return orderDTOS;
    }

    @Override
    public OrderDTO selectByUserIdAndOrderId(String userId, String orderId) {
        Order order = orderMapper.selectByUserIdAndOrderID(userId, orderId);
        //防空
        if (order == null) {
            throw new OtcException(OtcEnums.ORDER_NULL);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        //判断用户角色，true是买方
        boolean flag = checkBuyUserOrSellUser(userId, orderDTO.getBuyUserId(), orderDTO.getSellUserId());
        if (flag) {
            orderDTO.setRole(CommonConstans.BUY);
        } else {
            orderDTO.setRole(CommonConstans.SELL);
        }

        //查询双方用户数据，用于聊天展示
        UserBaseDTO buyUser = selectBaseUserByUserId(orderDTO.getBuyUserId());
        orderDTO.setBuyUserName(buyUser.getMobilePhone());
        orderDTO.setBuyNickName(buyUser.getNickName());
        orderDTO.setBuyAvatar(buyUser.getAvatar());

        UserBaseDTO sellUser = selectBaseUserByUserId(orderDTO.getSellUserId());
        orderDTO.setSellUserName(sellUser.getMobilePhone());
        orderDTO.setSellNickName(sellUser.getNickName());
        orderDTO.setSellAvatar(sellUser.getAvatar());

        return orderDTO;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public String buyOrder(String userId, String adId, BigDecimal amount, BigDecimal price, BigDecimal turnover) {
        //校验下单参数、用户权限
        dealOrderParamVerify(userId, adId, amount, price, turnover);
        //校验广告数据
        Ad ad = dealOrderAdVerify(adId, userId, amount, price, CommonConstans.SELL);
        //校验数据小数位
        Coin coin = dealOrderDecimalVerify(ad.getCoinName(), ad.getUnitName(), amount, turnover);
        //订单手续费
        BigDecimal serviceCharge = checkOrderServiceCharge(coin.getCoinServiceCharge(), userId);
        //检查下单交易额的小数失真偏差，返回较小偏差的交易额插入数据库
        BigDecimal realTurnover = checkTurnoverDish(turnover, price, amount, coin.getUnitDecimal());
        //发布订单
        Order order = insertOrder(ad, userId, amount, realTurnover, CommonConstans.BUY, serviceCharge);
        //广告扣除数量，更新广告
        updateAdLastNum(amount, ad, UserHandleConstants.DEAL);
        //记录用户操作
        insertUserHandleLog(userId, order.getOrderNumber(), UserHandleConstants.DEAL);
        //记录广告用户交易统计
        dealStatsService.updateAdTransNum(ad.getUserId());//广告交易次数
        //发送聊天提示信息
        sendNewOrderMsg(JgMsgEnums.FIRSTINIT_SELL.getName(), JgMsgEnums.FIRSTINIT_BUY.getName(),
                order.getSellUserId(), order.getBuyUserId(), order.getId());
        //发送手机消息通知
        pushToSingle(order.getSellUserId(), order.getId(), PushEnums.OTC_ORDER_DEAL_BUY.getPushType());
        //判断是否需要设置订单自动撤单
        checkNewOrderAutoCancel(order.getId());
        //返回订单id
        return order.getId();
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public String sellOrder(String userId, String adId, BigDecimal amount, BigDecimal price, BigDecimal turnover, String pass) {
        //校验下单参数、用户权限
        dealOrderParamVerify(userId, adId, amount, price, turnover);
        //检查密码
        walletService.isPassword(pass);
        //校验广告数据
        Ad ad = dealOrderAdVerify(adId, userId, amount, price, CommonConstans.BUY);
        //校验数据小数位
        Coin coin = dealOrderDecimalVerify(ad.getCoinName(), ad.getUnitName(), amount, turnover);
        //检查用户是否绑定买家支付方式
        checkSellUserPayIsBingding(userId, ad.getAdPay());
        //判断是否开启卖家手续费 ;关闭订单用户手续费 20190618
        BigDecimal chargeRatio = checkOrderServiceCharge(coin.getCoinServiceCharge(), userId);
        //检查下单交易额的小数失真偏差，返回较小偏差的交易额插入数据库
        BigDecimal realTurnover = checkTurnoverDish(turnover, price, amount, coin.getUnitDecimal());
        //发布订单
        Order order = insertOrder(ad, userId, amount, realTurnover, CommonConstans.SELL, chargeRatio);
        //广告扣除数量，更新广告
        updateAdLastNum(amount, ad, UserHandleConstants.DEAL);
        //下单卖单时更新钱包并记录资金变动
        dealSellOrderAndHandleWallet(userId, order.getOrderNumber(), coin.getCoinName(), coin.getUnitName(), amount, chargeRatio);
        //记录用户操作
        insertUserHandleLog(userId, order.getOrderNumber(), UserHandleConstants.DEAL);
        //记录广告用户交易统计
        dealStatsService.updateAdTransNum(ad.getUserId());//广告交易次数
        //发送提示信息
        sendNewOrderMsg(JgMsgEnums.FIRSTINIT_SELL.getName(), JgMsgEnums.FIRSTINIT_BUY.getName(),
                order.getSellUserId(), order.getBuyUserId(), order.getId());
        //发送手机消息通知
        pushToSingle(order.getBuyUserId(), order.getId(), PushEnums.OTC_ORDER_DEAL_SELL.getPushType());
        //判断是否需要设置订单自动撤单
        checkNewOrderAutoCancel(order.getId());
        //返回订单id
        return order.getId();
    }

    @Override
    @Transactional
    public void cancelBuyOrder(String userId, String orderId) {
        //检查订单类型、状态
        Order order = checkCancelBuyOrder(orderId, userId);
        //修改订单状态
        order.setBuyStatus(OrderConstants.CANCEL);
        order.setOrderStatus(OrderConstants.CANCEL);
        order.setModifyTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        //将撤单数量退回到广告中
        Ad ad = adService.selectByIdForUpdate(order.getAdId());
        updateAdLastNum(order.getAmount(), ad, UserHandleConstants.CANCEL);
        //记录用户操作
        insertUserHandleLog(userId, order.getOrderNumber(), UserHandleConstants.CANCEL);
        //订单取消，发送提示消息
        sendNewOrderMsg(JgMsgEnums.REPEAL_SELL.getName(), JgMsgEnums.REPEAL_BUY.getName(),
                order.getSellUserId(), order.getBuyUserId(), order.getId());
        //发送手机消息通知
        pushToSingle(order.getSellUserId(), order.getId(), PushEnums.OTC_ORDER_CANCEL.getPushType());
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void autoCancelOrder(String orderId) {
        //排他锁查询订单
        Order order = selectByIdForUpdate(orderId);
        //订单数据校验
        boolean flag = autoCancelOrderVerify(order);
        //返回false校验不通过，返回
        if (!flag) {
            return;
        }
        //如果是卖单，退钱
        autoCancelOrderHandleWallet(order);
        //更新订单状态
        order.setOrderStatus(OrderConstants.CANCEL);
        order.setModifyTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        //更新广告数量
        Ad ad = adService.selectByIdForUpdate(order.getAdId());
        updateAdLastNum(order.getAmount(), ad, UserHandleConstants.CANCEL);
        //订单取消，发送提示消息
        sendNewOrderMsg(JgMsgEnums.AUTO_REPEAL.getName(), JgMsgEnums.AUTO_REPEAL.getName(),
                order.getSellUserId(), order.getBuyUserId(), order.getId());
        //发送手机消息通知
        pushToSingle(order.getSellUserId(), order.getId(), PushEnums.OTC_ORDER_AUTO_CANCEL.getPushType());
        pushToSingle(order.getBuyUserId(), order.getId(), PushEnums.OTC_ORDER_AUTO_CANCEL.getPushType());
    }

    @Override
    public List<Order> listByStatus(String status) {
        return orderMapper.listByStatus(status);
    }

    @Override
    public boolean checkOrdersUnfinished(String adId) {
        //根据广告id和订单状态（新建、进行中、申诉中）查询订单列表
        List<Order> orders = this.selectByAdIdAndStatus(adId, OrderConstants.NEW,
                OrderConstants.UNDERWAY, OrderConstants.APPEAL);
        //存在未完成订单，返回true
        if (orders.size() != 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void pay(String userId, String orderId, String payType) {
        //订单id判空
        checkOrderIdNull(orderId);
        //支付类型判空
        if (StringUtils.isBlank(payType)) {
            throw new OtcException(OtcEnums.ORDER_PAY_PAYTYPE_NULL);
        }
        //排他锁查询订单
        Order order = orderMapper.selectByIdForUpdate(orderId);
        //订单是否存在
        checkOrderNull(order);
        //订单状态是否可确认付款
        checkOrderCanHandle(order, userId, UserHandleConstants.PAY);
        //排他锁查询广告信息
        Ad ad = adService.selectByIdForUpdate(order.getAdId());
        //广告是否为空
        checkAdNull(ad);
        //支付类型是否和广告设置的匹配
        checkPayTypeIsAdPayType(payType, ad.getAdPay());
        //确认支付时，设置订单支付信息
        order.setOrderPayType(payType);
        //更新订单状态
        receiptOrPayUpdateOrder(order, UserHandleConstants.PAY);
        //记录用户操作
        insertUserHandleLog(userId, order.getOrderNumber(), UserHandleConstants.PAY);
        //确认付款发送提示消息
        sendNewOrderMsg(JgMsgEnums.CONFIRM_BUYER_SELL.getName(), JgMsgEnums.CONFIRM_BUYER_BUY.getName(),
                order.getSellUserId(), order.getBuyUserId(), order.getId());
        //发送手机消息通知
        pushToSingle(order.getSellUserId(), order.getId(), PushEnums.OTC_ORDER_PAY.getPushType());
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void receipt(String userId, String orderId, String pass) {
        //订单id判空
        checkOrderIdNull(orderId);
        //校验密码
        walletService.isPassword(pass);
        //排他锁查询订单
        Order order = orderMapper.selectByIdForUpdate(orderId);
        //订单是否存在
        checkOrderNull(order);
        //检查订单能否确认收款
        checkOrderCanHandle(order, userId, UserHandleConstants.RECEIPT);
        //更新订单状态
        receiptOrPayUpdateOrder(order, UserHandleConstants.RECEIPT);
        //记录用户操作
        insertUserHandleLog(userId, order.getOrderNumber(), UserHandleConstants.RECEIPT);
        //更新广告发布方成交统计数据
        updateAdUserDealStats(order);
        //更新余额并记录资金变动
        receiptUpdateBalance(order);
        //判断广告是否可以结束
        checkAdCanFinish(order.getAdId());
        //确认收款发送提示消息
        sendNewOrderMsg(JgMsgEnums.CONFIRM_SELLER_SELL.getName(), JgMsgEnums.CONFIRM_SELLER_BUY.getName(),
                order.getSellUserId(), order.getBuyUserId(), order.getId());
        //发送手机消息通知
        pushToSingle(order.getBuyUserId(), order.getId(), PushEnums.OTC_ORDER_RECEIPT.getPushType());
    }

    @Override
    public Order selectByIdForUpdate(String orderId) {
        return orderMapper.selectByIdForUpdate(orderId);
    }

    @Override
    public Order selectById(String orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order selectByOrderNumber(String orderNumber) {
        return orderMapper.selectByOrderNumber(orderNumber);
    }

    @Override
    public List<String> selectAutoCancelOrder(String status, Date date, Integer pageNum, Integer pageSize) {
        return orderMapper.selectByStatusAndCreateTime(status, date, pageNum, pageSize);
    }

    /***
     * 校验下单参数、用户权限
     * @param userId
     * @param adId
     * @param amount
     * @param price
     * @param turnover
     */
    private void dealOrderParamVerify(String userId, String adId, BigDecimal amount, BigDecimal price, BigDecimal turnover) {
        //检查参数是否为空
        checkDealParam(amount, price, turnover, adId);
        //用户是否有权限交易
        userFeign.hasLowAuthAndUserList(userId);
    }

    /***
     * 校验下单的广告
     * @param adId
     * @param userId
     * @param amount
     * @param price
     * @return
     */
    private Ad dealOrderAdVerify(String adId, String userId, BigDecimal amount, BigDecimal price, String adType) {
        //排他锁查询广告
        Ad ad = adService.selectByIdForUpdate(adId);
        //检查广告状态、剩余数量
        checkAdStatus(ad);
        //判断广告类型是否正确
        checkAdType(ad.getAdType(), adType);
        //检查下单用户是不是广告发布用户
        checkAdUserAndOrderUserEquals(ad.getUserId(), userId);
        //检查检查下单数量、单价是否合法
        checkAmountAndPrice(ad, amount, price);

        return ad;
    }

    /***
     * 校验下单的数据小数位
     * @param coinName
     * @param unitName
     * @param amount
     * @param turnover
     * @return
     */
    private Coin dealOrderDecimalVerify(String coinName, String unitName, BigDecimal amount, BigDecimal turnover) {
        //查询币种
        Coin coin = coinService.selectByCoinAndUnit(coinName, unitName);
        //检查下单数量小数长度是否合法
        CheckDecimalUtil.checkDecimal(amount, coin.getCoinDecimal(), OtcEnums.ORDER_DEAL_AMOUNT_DECIMAL_ERROR);
        //检查下单交易额小数长度是否合法
        CheckDecimalUtil.checkDecimal(turnover, coin.getUnitDecimal(), OtcEnums.ORDER_DEAL_TURNOVER_DECIMAL_ERROR);

        return coin;
    }

    /***
     * 下单卖单时更新钱包并记录资金变动
     * @param userId
     * @param orderNumber
     * @param coinName
     * @param unitName
     * @param amount
     * @param chargeRatio
     */
    private void dealSellOrderAndHandleWallet(String userId, String orderNumber, String coinName, String unitName,
                                              BigDecimal amount, BigDecimal chargeRatio) {
        //扣除可用余额    数量 + （数量 * 手续费） * -1
        BigDecimal freeBalance = amount.add(amount.multiply(chargeRatio)).multiply(CommonConstans.MINUS_ONE);
        //增加冻结余额 数量 + （数量 * 手续费）
        BigDecimal freezeBalance = amount.add(amount.multiply(chargeRatio));
        //冻结余额
        walletService.handleBalance(userId, orderNumber, coinName, unitName, freeBalance, freezeBalance);
        //记录资金变动记录
        billService.insertBill(userId, orderNumber, freeBalance, freezeBalance, BillConstants.DEAL, coinName);
    }

    /***
     * 自动撤单的订单数据校验
     * @param order
     * @return
     */
    private boolean autoCancelOrderVerify(Order order) {
        //防空
        if (order == null) {
            return false;
        }
        //买家不等于新建，返回
        if (!order.getBuyStatus().equals(OrderConstants.NEW)) {
            return false;
        }
        //订单不等于新建，返回
        if (!order.getOrderStatus().equals(OrderConstants.NEW)) {
            return false;
        }
        return true;
    }

    /***
     * 撤销卖单时更新钱包
     * @param order
     */
    private void autoCancelOrderHandleWallet(Order order) {
        //true为买
        boolean flag = checkBuyOrSell(order.getOrderType());
        //订单卖出时解冻余额
        if (!flag) {
            //退回手续费
            BigDecimal chargeRatio = order.getChargeRatio();
            //可用余额
            BigDecimal freeBalance = order.getAmount().add(order.getAmount().multiply(chargeRatio));
            //冻结余额
            BigDecimal freezeBalance = freeBalance.multiply(CommonConstans.MINUS_ONE);
            //解冻卖家资金
            walletService.handleBalance(order.getSellUserId(), order.getOrderNumber(), order.getCoinName(), order.getUnitName(), freeBalance, freezeBalance);
            //记录资金变动
            billService.insertBill(order.getSellUserId(), order.getOrderNumber(), freeBalance, freezeBalance, BillConstants.AUTO_CANCEL, order.getCoinName());
        }
    }

    /***
     * 新增用户操作日志
     * @param userId
     * @param orderNumber
     * @param handleType
     * @return
     */
    private int insertUserHandleLog(String userId, String orderNumber, String handleType) {
        return userHandleLogService.insertUserHandleLog(userId, orderNumber, handleType, UserHandleConstants.ORDER);
    }

    /***
     * 判断用户角色
     * true是买方
     * @param userId
     * @param buyUserId
     * @param sellUserId
     * @return
     */
    private boolean checkBuyUserOrSellUser(String userId, String buyUserId, String sellUserId) {
        if (userId.equals(buyUserId)) {
            return true;
        }
        if (userId.equals(sellUserId)) {
            return false;
        }
        throw new OtcException(OtcEnums.BUY_USER_OR_SELL_USER_ERROR);
    }

    /***
     * 判断买卖类型
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
     * 新建订单时判断是否需要自动撤单
     * @param orderId
     */
    private void checkNewOrderAutoCancel(String orderId) {
        //查询是否开启自动撤单
        boolean flag = configService.selectAutoCancelIsY();
        if (flag) {
            //查询撤单时间间隔
            long interval = configService.selectAutoCancelInterval();
            //设置缓存
            orderCache.setNewOrderCache(orderId, interval);
        }
    }

    /***
     * 新建订单后发送聊天提示（极光IM聊天API）
     * @param sellMsg
     * @param buyMsg
     * @param sellUserId
     * @param buyUserId
     * @param orderId
     */
    private void sendNewOrderMsg(String sellMsg, String buyMsg, String sellUserId, String buyUserId, String orderId) {
        imUtil.postMsgIMSelf(buyMsg, sellUserId, buyUserId, orderId);
        imUtil.postMsgIMSelf(sellMsg, buyUserId, sellUserId, orderId);
    }

    /***
     * 发送手机消息通知（个推消息推送API）
     * @param userId
     * @param orderId
     * @param pushType
     */
    private void pushToSingle(String userId, String orderId, String pushType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(PushConstants.ORDER_ID, orderId);
        payload.put(PushConstants.PUSH_TYPE, pushType);
        pushFeign.pushToSingle(userId, pushType, payload);
    }

    /***
     * 检查交易参数
     * @param amount
     * @param price
     * @param turnover
     * @param adId
     */
    private void checkDealParam(BigDecimal amount, BigDecimal price, BigDecimal turnover, String adId) {
        //交易数量
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OtcException(OtcEnums.ORDER_DEAL_AMOUNT_NULL);
        }
        //单价
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OtcException(OtcEnums.ORDER_DEAL_PRICE_NULL);
        }
        //交易额
        if (turnover == null || turnover.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OtcException(OtcEnums.ORDER_DEAL_TURNOVER_NULL);
        }
        //广告id
        if (StringUtils.isBlank(adId)) {
            throw new OtcException(OtcEnums.ORDER_DEAL_ADID_NULL);
        }
    }

    /***
     * 检查
     * 广告是否存在
     * 广告状态是否可交易
     * 广告剩余数量是否充足
     * @param ad
     */
    private void checkAdStatus(Ad ad) {
        //广告是否存在
        if (ad == null) {
            throw new OtcException(OtcEnums.ORDER_AD_NULL);
        }
        //广告状态是否可以交易
        if (!ad.getAdStatus().equals(AdConstants.UNDERWAY) && !ad.getAdStatus().equals(AdConstants.DEFAULT)) {
            throw new OtcException(OtcEnums.ORDER_DEAL_AD_STATUS_ERROR);
        }
        //广告剩余数量是否大于0，剩余数量是否足够交易
        if (ad.getLastNum().compareTo(BigDecimal.ZERO) <= 0 ||
                (ad.getLastNum().multiply(ad.getPrice())).compareTo(ad.getMinLimit()) < 0) {
            throw new OtcException(OtcEnums.ORDER_DEAL_AD_NUM_ZERO);
        }
    }

    /***
     * 检查广告类型是否正确
     * @param adType 广告类型
     * @param trueType 正确的类型
     */
    private void checkAdType(String adType, String trueType) {
        if (!adType.equals(trueType)) {
            throw new OtcException(OtcEnums.ORDER_DEAL_AD_TYPE_ERROR);
        }
    }

    /***
     * 检查下单数量是否合法
     * 下单单价是否是最新单价
     * @param ad
     * @param amount
     * @param price
     */
    private void checkAmountAndPrice(Ad ad, BigDecimal amount, BigDecimal price) {
        //下单时单价和广告最新单价不一致
        if (price.compareTo(ad.getPrice()) != 0) {
            throw new OtcException(OtcEnums.ORDER_DEAL_PRICE_NOT_REAL_TIME);
        }
        //广告数量是否充足
        if (amount.compareTo(ad.getLastNum()) > 0) {
            throw new OtcException(OtcEnums.ORDER_DEAL_AD_NUM_ZERO);
        }
    }

    /****
     * 下单用户是不是广告发布的用户
     * @param adUserId
     * @param orderUserId
     */
    private void checkAdUserAndOrderUserEquals(String adUserId, String orderUserId) {
        if (adUserId.equals(orderUserId)) {
            throw new OtcException(OtcEnums.ORDER_USERID_EQUALS_AD_USERID);
        }
    }

    /***
     * 检查下单交易额的小数失真偏差
     * 偏差较小，返回交易额参数
     * 偏差较大，返回单价数量相乘的结果
     * @param turnover
     * @param price
     * @param amount
     * @return
     */
    private BigDecimal checkTurnoverDish(BigDecimal turnover, BigDecimal price, BigDecimal amount, Integer unitDecimal) {
        //保留法币小数长度
        BigDecimal computeTurnover = amount.multiply(price).setScale(unitDecimal, BigDecimal.ROUND_DOWN);
        //相减之后的绝对值作为偏差值
        BigDecimal dish = (turnover.subtract(computeTurnover)).abs();
        //如果偏差值较小，返回交易额参数
        if (dish.compareTo(DECIMAL_DISH) <= 0) {
            return turnover;
        } else {
            //偏差值较大，返回单价数量相乘的结果
            return computeTurnover;
        }
    }

    /***
     * 检查卖家是否绑定买家选择的支付方式
     * @param userId
     * @param pays
     */
    private void checkSellUserPayIsBingding(String userId, String pays) {
        //解析广告的支付方式
        String[] pay = pays.split(",");
        //判断用户时候绑定广告支付方式标识
        boolean flag = false;
        //循环
        for (String p : pay) {
            UserPayInfo userPayInfo = userPayInfoService.selectByUserIdAndPayType(userId, p);
            //如果绑定了其中一种，将标识改为true
            if (userPayInfo != null) {
                flag = true;
            }
        }
        //循环结束，如果没有绑定任何一种广告的支付方式，抛出异常
        if (!flag) {
            throw new OtcException(OtcEnums.ORDER_DEAL_AD_PAY_NOT_BINGDING);
        }
    }

    /***
     * 新增订单
     * @param ad
     * @param userId
     * @param amount
     * @param turnover
     * @param orderType
     * @param chargeRatio 手续费
     * @return
     */
    private Order insertOrder(Ad ad, String userId, BigDecimal amount, BigDecimal turnover, String orderType, BigDecimal chargeRatio) {
        Order order = new Order();
        Date now = new Date();
        String orderId = UUID.randomUUID().toString();
        String orderNumber = createAdNumber();
        order.setId(orderId);
        order.setOrderNumber(orderNumber);
        order.setAdId(ad.getId());
        order.setCoinName(ad.getCoinName());
        order.setUnitName(ad.getUnitName());
        order.setAmount(amount);
        order.setPrice(ad.getPrice());
        order.setTurnover(turnover);
        order.setChargeRatio(chargeRatio);
        order.setBuyStatus(OrderConstants.NEW);
        //判断双方角色，true的时候orderType为买
        if (checkBuyOrSell(orderType)) {
            order.setBuyUserId(userId);
            order.setSellUserId(ad.getUserId());
        } else {
            order.setBuyUserId(ad.getUserId());
            order.setSellUserId(userId);
        }
        order.setSellStatus(OrderConstants.NEW);
//        order.setOrderPayType(); 下单时不需要选择支付方式
        order.setOrderStatus(OrderConstants.NEW);
        order.setOrderType(orderType);
        order.setCreateTime(now);
        order.setModifyTime(now);
        orderMapper.insertSelective(order);
        return order;
    }

    /***
     * 更新广告剩余数量
     * @param amount
     * @param ad
     * @param type
     */
    private void updateAdLastNum(BigDecimal amount, Ad ad, String type) {
        //下单
        if (type.equals(UserHandleConstants.DEAL)) {
            //下单时将广告状态设置为交易中
            ad.setAdStatus(AdConstants.UNDERWAY);
            //扣除数量
            ad.setLastNum(ad.getLastNum().subtract(amount));
        }
        //撤单
        if (type.equals(UserHandleConstants.CANCEL)) {
            //查询广告伞下是否还有未完成订单
            boolean flag = checkOrdersUnfinished(ad.getId());
            //false代表没有未完成订单，将广告设置为挂单中
            if (!flag) {
                ad.setAdStatus(AdConstants.DEFAULT);
            }
            //增加数量
            ad.setLastNum(ad.getLastNum().add(amount));
        }
        //变动后的最大限额
        BigDecimal maxLimit = ad.getLastNum().multiply(ad.getPrice()).setScale(CommonConstans.LIMIT_CNY_DECIMAL, BigDecimal.ROUND_DOWN);
        //如果广告最小限额小于等于最大限额，更新广告最大限额
        if (ad.getMinLimit().compareTo(maxLimit) <= 0) {
            ad.setMaxLimit(maxLimit);
        } else {
            //剩余数量不足以交易，下架广告
            ad.setAdStatus(AdConstants.PENDING);
        }
        ad.setModifyTime(new Date());
        //更新广告
        adService.updateByPrimaryKeySelective(ad);
    }

    /***
     * 校验订单状态、类型是否可以撤单
     * @param orderId
     * @param userId
     */
    private Order checkCancelBuyOrder(String orderId, String userId) {
        //订单id是否为空
        checkOrderIdNull(orderId);
        //排他锁查询订单信息
        Order order = orderMapper.selectByIdForUpdate(orderId);
        //订单是否存在
        checkOrderNull(order);
        //订单买家和用户是否匹配
        if (!order.getBuyUserId().equals(userId)) {
            throw new OtcException(OtcEnums.ORDER_CANCEL_USERID_NOT_EQUALS);
        }
        //买家状态不是新建或者已确认
        if (!order.getBuyStatus().equals(OrderConstants.NEW) &&
                !order.getBuyStatus().equals(OrderConstants.CONFIRM)) {
            throw new OtcException(OtcEnums.ORDER_BUY_STATUS_CHANGES);
        }
        //卖家状态不是新建
        if (!order.getSellStatus().equals(OrderConstants.NEW)) {
            throw new OtcException(OtcEnums.ORDER_SELL_STATUS_CHANGES);
        }
        //订单状态不是新建或者进行中
        if (!order.getOrderStatus().equals(OrderConstants.NEW) &&
                !order.getOrderStatus().equals(OrderConstants.UNDERWAY)) {
            throw new OtcException(OtcEnums.ORDER_STATUS_CHANGES);
        }
        //订单类型不是买单
        if (!order.getOrderType().equals(CommonConstans.BUY)) {
            throw new OtcException(OtcEnums.ORDER_CANCEL_TYPE_NOT_BUY);
        }

        return order;
    }

    /***
     * 更新广告发布方成交统计数据
     * @param order
     */
    private void updateAdUserDealStats(Order order) {
        //orderType等于买flag为true
        boolean flag = checkBuyOrSell(order.getOrderType());
        //如果订单是买单，更新广告方成交卖单数量
        if (flag) {
            //已完成卖单数量
            dealStatsService.updateOrderSellNum(order.getSellUserId());
            //更新广告发布方成交数量
            dealStatsService.updateAdMarkNum(order.getSellUserId());
        } else {
            //如果订单是卖单，更新广告方成交买单数量
            //已完成买单数量
            dealStatsService.updateOrderBuyNum(order.getBuyUserId());
            //更新广告发布方成交数量
            dealStatsService.updateAdMarkNum(order.getBuyUserId());
        }
    }

    /***
     * 更新订单状态
     * @param order
     * @param handleType
     */
    private void receiptOrPayUpdateOrder(Order order, String handleType) {
        //确认付款操作时
        if (handleType.equals(UserHandleConstants.PAY)) {
            //更新买家状态为已确认
            order.setBuyStatus(OrderConstants.CONFIRM);
            //更新订单状态为进行中
            order.setOrderStatus(OrderConstants.UNDERWAY);
            order.setModifyTime(new Date());
        }
        //确认收款操作时
        if (handleType.equals(UserHandleConstants.RECEIPT)) {
            //订单状态为申诉时，确认收款后将申诉记录失效
            if (order.getOrderStatus().equals(OrderConstants.APPEAL)) {
                //将申诉记录改为已处理
                appealService.updateAppeal(order.getOrderNumber(), AppealConstants.HANDLE);
                //新增申诉记录处理日志
                appealHandleLogService.insertAppealHandleLog(order.getOrderNumber(),
                        AppealConstants.RECEIPT_SYS_USER_ID, AppealConstants.RECEIPT_IP_ADDR,
                        OrderConstants.FINISH, AppealConstants.RECEIPT_REMARK);
            }
            //更新卖家状态为已确认
            order.setSellStatus(OrderConstants.CONFIRM);
            //更新订单状态为已完成
            order.setOrderStatus(OrderConstants.FINISH);
            order.setModifyTime(new Date());
        }
        //更新订单
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /***
     * 检查确认付款时的支付类型参数
     * 跟广告设置的支付信息是否匹配
     *
     * @param payType
     * @param adPayType
     */
    private void checkPayTypeIsAdPayType(String payType, String adPayType) {
        //广告设置的支付信息
        String[] pay = adPayType.split(",");
        //如果匹配其中一个，将状态改为true
        boolean flag = false;
        for (String p : pay) {
            if (p.equals(payType)) {
                flag = true;
            }
        }
        //没有匹配其中一个，抛出异常
        if (!flag) {
            throw new OtcException(OtcEnums.ORDER_PAY_ERROR);
        }
    }

    /***
     * 订单id是否为空
     * @param orderId
     */
    private void checkOrderIdNull(String orderId) {
        //id是否为空
        if (StringUtils.isBlank(orderId)) {
            throw new OtcException(OtcEnums.ORDER_ID_NULL);
        }
    }

    /***
     * 订单是否为空
     * @param order
     */
    private void checkOrderNull(Order order) {
        //订单是否存在
        if (order == null) {
            throw new OtcException(OtcEnums.ORDER_NULL);
        }
    }

    /***
     * 广告是否为空
     * @param ad
     */
    private void checkAdNull(Ad ad) {
        if (ad == null) {
            throw new OtcException(OtcEnums.ORDER_AD_NULL);
        }
    }

    /***
     * 检查订单能否确认付款或确认收款
     * @param order
     * @param userId
     * @param handleType
     */
    private void checkOrderCanHandle(Order order, String userId, String handleType) {
        /***
         * 订单是否可以确认付款：
         * 订单状态等于新建 √
         * 买家id等于操作用户id √
         * 买家状态等于新建 √
         */
        if (handleType.equals(UserHandleConstants.PAY)) {
            if (!order.getOrderStatus().equals(OrderConstants.NEW)) {
                throw new OtcException(OtcEnums.ORDER_STATUS_CHANGES);
            }
            //操作用户和买家id是否匹配
            if (!order.getBuyUserId().equals(userId)) {
                throw new OtcException(OtcEnums.ORDER_PAY_USERID_NOT_EQUALS);
            }
            //买家状态是否可确认付款
            if (!order.getBuyStatus().equals(OrderConstants.NEW)) {
                throw new OtcException(OtcEnums.ORDER_BUY_STATUS_CHANGES);
            }
        }

        /***
         * 订单状态是否可以确认收款：
         * 订单状态等于进行中和申诉中 √
         * 卖家id等于操作用户id √
         * 卖家状态等于新建和申诉中 √
         */
        if (handleType.equals(UserHandleConstants.RECEIPT)) {
            if (!order.getOrderStatus().equals(OrderConstants.UNDERWAY) &&
                    !order.getOrderStatus().equals(OrderConstants.APPEAL)) {
                throw new OtcException(OtcEnums.ORDER_STATUS_CHANGES);
            }
            //操作用户和卖家id是否匹配
            if (!order.getSellUserId().equals(userId)) {
                throw new OtcException(OtcEnums.ORDER_PAY_USERID_NOT_EQUALS);
            }
            //卖家是否可以确认收款
            if (!order.getSellStatus().equals(OrderConstants.NEW) &&
                    !order.getSellStatus().equals(OrderConstants.APPEAL)) {
                throw new OtcException(OtcEnums.ORDER_SELL_STATUS_CHANGES);
            }
        }
    }

    /***
     * 确认收款后更新余额并记录资金变动
     * @param order
     */
    private void receiptUpdateBalance(Order order) {
        Ad ad = adService.selectById(order.getAdId());
        //交易数量
        BigDecimal amount = order.getAmount();
        //手续费
        BigDecimal serviceCharge;
        //买家实际获得数量
        BigDecimal realAmount;
        //卖家解冻数量
        BigDecimal minusAmount;

        //true为买
        boolean flag = checkBuyOrSell(order.getOrderType());
        if (flag) {
            //订单为买单-（买方是订单方，卖方是广告方）
            //手续费
            serviceCharge = amount.multiply(order.getChargeRatio());
            //买家实际获得数量
            realAmount = amount;
            //卖家解冻数量
            minusAmount = amount.add(amount.multiply(ad.getChargeRatio())).multiply(CommonConstans.MINUS_ONE);
        } else {
            //订单为卖单-（买方是广告方，卖方是订单方）
            //手续费
            serviceCharge = amount.multiply(ad.getChargeRatio());
            //买家实际获得数量
            realAmount = amount.subtract(serviceCharge);
            //卖家解冻数量
            minusAmount = amount.add(amount.multiply(order.getChargeRatio())).multiply(CommonConstans.MINUS_ONE);
        }

        String coinName = order.getCoinName();
        String unitName = order.getUnitName();
        String orderNumber = order.getOrderNumber();
        String buyUserId = order.getBuyUserId();
        String sellUserId = order.getSellUserId();

        //买家加款
        walletService.handleRealBalance(buyUserId, orderNumber, coinName, unitName, realAmount, BigDecimal.ZERO, serviceCharge);
        //记录资金变动
        billService.insertBill(buyUserId, orderNumber, realAmount, BigDecimal.ZERO, BillConstants.MARK, coinName);
        //卖家解冻
        walletService.handleRealBalance(sellUserId, orderNumber, coinName, unitName, BigDecimal.ZERO, minusAmount, BigDecimal.ZERO);
        //记录资金变动
        billService.insertBill(sellUserId, orderNumber, BigDecimal.ZERO, minusAmount, BillConstants.MARK, coinName);

        oreTx(buyUserId, amount, serviceCharge, coinName);
        oreTx(sellUserId, amount, serviceCharge, coinName);
    }

    /***
     * 判断广告是否能结束
     * @param adId
     */
    private void checkAdCanFinish(String adId) {
        //查询广告伞下是否还有未完成订单
        boolean unfinished = this.checkOrdersUnfinished(adId);

        //排他锁查询广告
        Ad ad = adService.selectByIdForUpdate(adId);

        //剩余交易额不足以交易
        if ((ad.getLastNum().multiply(ad.getPrice())).compareTo(ad.getMinLimit()) < 0) {
            //有未完成订单，下架
            if (unfinished) {
                ad.setAdStatus(AdConstants.PENDING);
            } else {//没有未完成订单
                //falge为卖
                boolean flag = checkBuyOrSell(ad.getAdType());
                //广告是卖出类型时 并且 有剩余数量时，解冻余额
                if (!flag && ad.getLastNum().compareTo(BigDecimal.ZERO) > 0) {
                    //广告手续费
                    BigDecimal serviceCharge = ad.getLastNum().multiply(ad.getChargeRatio());
                    //增加可用
                    BigDecimal freeBalance = ad.getLastNum().add(serviceCharge);
                    //扣除冻结
                    BigDecimal freezeBalance = freeBalance.multiply(CommonConstans.MINUS_ONE);
                    //解冻余额
                    walletService.handleBalance(ad.getUserId(), ad.getAdNumber(), ad.getCoinName(), ad.getUnitName(), freeBalance, freezeBalance);
                    //记录资金变动
                    billService.insertBill(ad.getUserId(), ad.getAdNumber(), freeBalance, freezeBalance, BillConstants.CANCEL, ad.getCoinName());
                }
                //已完成
                ad.setAdStatus(AdConstants.FINISH);
            }
        } else {
            //还有剩余剩余数量
            //有未完成订单，设置为进行中
            if (unfinished) {
                ad.setAdStatus(AdConstants.UNDERWAY);
            } else {
                //没有未完成订单，设置为挂单中
                ad.setAdStatus(AdConstants.DEFAULT);
            }
        }

        ad.setModifyTime(new Date());
        //更新广告
        adService.updateByPrimaryKeySelective(ad);
    }

    /***
     * 生成广告随机流水号
     * @return
     */
    private String createAdNumber() {
        return System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
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
     * 判断是否开启订单手续费、用户是否免手续费
     * @param coinServiceCharge 币种手续费
     * @param userId
     * @return
     */
    private BigDecimal checkOrderServiceCharge(BigDecimal coinServiceCharge, String userId) {
        boolean flag = verifyFreeTransaction(userId);
        //用户免除手续费
        if (flag) {
            return BigDecimal.ZERO;
        }
        //查询订单手续费是否开启
        Config config = configService.selectByKey(ConfigConstants.ORDER_SERVICE_CHARGE);
//        //查询卖家手续费是否开启
//        Config config = configService.selectByKey(ConfigConstants.SELL_SERVICE_CHARGE);
        //如果订单手续费配置为空或者配置状态为禁用
        if (config == null || config.getStatus().equals(CommonConstans.NO)) {
            //返回0手续费
            return BigDecimal.ZERO;
        }
        //返回手续费
        return coinServiceCharge;
    }

    /***
     * 成交时生成矿机佣金，调用Ore模块
     * @param userId
     * @param tokenAmount
     * @param fee
     * @param tokenSymbol
     */
    private void oreTx(String userId, BigDecimal tokenAmount, BigDecimal fee, String tokenSymbol) {
        TxForOreDTO oreDTO = new TxForOreDTO();
        oreDTO.setUserId(userId);
        oreDTO.setTxType(CommonConstans.C2C_APP);
        oreDTO.setTokenSymbol(tokenSymbol);
        oreDTO.setTokenAmount(tokenAmount);
        oreDTO.setFee(fee);
        oreDTO.setTxDate(new Date());
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
