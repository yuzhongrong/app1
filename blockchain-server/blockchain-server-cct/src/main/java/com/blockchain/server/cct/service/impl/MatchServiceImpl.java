package com.blockchain.server.cct.service.impl;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.TxForOreDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.common.redistool.RedisTool;
import com.blockchain.server.cct.common.util.CheckConfigUtil;
import com.blockchain.server.cct.dto.order.PublishOrderDTO;
import com.blockchain.server.cct.entity.*;
import com.blockchain.server.cct.feign.BotFeign;
import com.blockchain.server.cct.feign.CurrencyFeign;
import com.blockchain.server.cct.mapper.PublishOrderMapper;
import com.blockchain.server.cct.redis.PublishOrderCache;
import com.blockchain.server.cct.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.github.pagehelper.PageHelper;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MatchServiceImpl implements MatchService, ITxTransaction {
    private static final Logger LOG = LoggerFactory.getLogger(MatchServiceImpl.class);

    @Autowired
    private PublishOrderService orderService;
    @Autowired
    private TradingRecordService recordService;
    @Autowired
    private TradingDetailService detailService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private PublishOrderMapper orderMapper;
    @Autowired
    private CheckConfigUtil configUtil;
    @Autowired
    private CurrencyFeign currencyFeign;
    @Autowired
    private BotFeign botFeign;
    @Autowired
    private RedisTool redisTool;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private PublishOrderCache orderCache;


    //火币成交虚拟订单id
    public static final String HUOBI_ORDER_ID = "000000000000000000000000000000000000";

    //撮合计算参数常量
    private static final String TRANS_PRICE = "price";
    private static final String TRANS_NUM = "transNum";
    private static final String TRANS_TURNOVER = "turnover";

    //异步线程池
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static final BigDecimal MINUS_ONE = new BigDecimal("-1");//负一
    private static final BigDecimal ZERO = BigDecimal.ZERO;//零
    private static final int DEFAULT_PAGESIZE = 10; //查询撮合订单默认分页
    private static final int MATCH_BOT_ORDER_PAGESIZE = 50; //查询单价范围内的挂单默认分页

    @Override
    public List<PublishOrder> listBeMatchOrder(String orderId) {
        //查询订单
        PublishOrder order = orderService.selectById(orderId);
        String publishType = order.getPublishType(); //发布类型
        String orderType = order.getOrderType(); //订单类型
        String coinName = order.getCoinName(); //基本货币
        String unitName = order.getUnitName(); //二级货币
        BigDecimal price = order.getUnitPrice(); //单价
        String status = CctDataEnums.ORDER_STATUS_MATCH.getStrVlue(); //查询的订单状态

        //返回集合
        List<PublishOrder> matchs = new ArrayList<>();

        //查询限价可撮合订单
        if (publishType.equals(CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue())) {
            if (orderType.equals(CctDataEnums.ORDER_TYPE_BUY.getStrVlue())) {
                matchs = orderMapper.listMatchSellOrder(unitName, coinName, status, price, "", DEFAULT_PAGESIZE);
            } else {
                matchs = orderMapper.listMatchBuyOrder(unitName, coinName, status, price, "", DEFAULT_PAGESIZE);
            }
        }

        //查询市价可撮合订单
        if (publishType.equals(CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue())) {
            if (orderType.equals(CctDataEnums.ORDER_TYPE_BUY.getStrVlue())) {
                matchs = orderMapper.listMatchSellOrder(
                        unitName, coinName, status, price, CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), DEFAULT_PAGESIZE);
            } else {
                matchs = orderMapper.listMatchBuyOrder(
                        unitName, coinName, status, price, CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), DEFAULT_PAGESIZE);
            }
        }

        //返回查询集合，可以为空
        return matchs;
    }

    @Override
    public List<PublishOrderDTO> listBeMatchOrderToBot(String coinName, String unitName, BigDecimal minPrice, BigDecimal maxPrice) {
        String orderStatus = CctDataEnums.ORDER_STATUS_MATCH.getStrVlue();
        String publishType = CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue();
        List<PublishOrder> orders = orderMapper.listMatchOrderToBot(unitName, coinName, minPrice, maxPrice, orderStatus, publishType,
                MATCH_BOT_ORDER_PAGESIZE);

        List<PublishOrderDTO> orderDTOS = new ArrayList<>();
        for (PublishOrder order : orders) {
            PublishOrderDTO orderDTO = new PublishOrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            orderDTOS.add(orderDTO);
        }

        return orderDTOS;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void handleQuantizedTransation(String id, BigDecimal transNum, BigDecimal price, boolean isFill) {
        ExceptionPreconditionUtils.notEmpty(id, transNum);
        LOG.info("id:{0},transNum:{1},price:{2},isFill:{3}", id, transNum, price, isFill);
        if (transNum.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        //解决偶然发生会在sql后面拼接limit的情况
        PageHelper.clearPage();
        PublishOrder bymatch = orderService.selectByIdForUpdate(id);

        if (bymatch.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue()) ||
                bymatch.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_CANCEL.getStrVlue())) {
            //如果已经是完成状态或者撤销状态，不再处理
            return;
        }
        if (bymatch.getPublishType().equals(CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue())) {
            //处理市价交易
            handleQuantizedMarket(bymatch, transNum, price, isFill);
        } else if (bymatch.getPublishType().equals(CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue())) {
            //处理限价交易
            handleQuantizedLimit(bymatch, transNum, price, isFill);
        }
    }

    @Override
    public void convertAndSend(String coinName, String unitName) {
        //使用异步线程推送前端
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //发送广播到前端，更新前端行情
                redisTool.send(coinName.toUpperCase(), unitName.toUpperCase());
            }
        });
    }

    /**
     * 处理量化交易市价
     */
    private void handleQuantizedMarket(PublishOrder order, BigDecimal transNum, BigDecimal price, boolean isFill) {
        BigDecimal marketPrice = price.divide(transNum, 6, BigDecimal.ROUND_HALF_UP);
        Map<String, BigDecimal> bymatchMap = new HashMap<>();
        bymatchMap.put(TRANS_PRICE, marketPrice);//交易价格
        //市价卖
        if (order.getOrderType().equals(CctDataEnums.ORDER_TYPE_SELL.getStrVlue())) {
            if (isFill) {
                bymatchMap.put(TRANS_NUM, order.getLastNum());//成交数量为剩余数量
                order.setLastNum(BigDecimal.ZERO);//将剩余数量设置为0
                order.setOrderStatus(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue());//设置为已完成
            } else {
                bymatchMap.put(TRANS_NUM, transNum);
                order.setLastNum(order.getLastNum().subtract(transNum));
            }
            bymatchMap.put(TRANS_TURNOVER, price);
        }

        //市价买
        if (order.getOrderType().equals(CctDataEnums.ORDER_TYPE_BUY.getStrVlue())) {
            if (isFill) {
                bymatchMap.put(TRANS_TURNOVER, order.getLastTurnover());//成交总额为剩余总额
                order.setLastTurnover(BigDecimal.ZERO);//将剩余数量设置为0
                order.setOrderStatus(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue());//设置为已完成
            } else {
                bymatchMap.put(TRANS_TURNOVER, price);//成交总额
                order.setLastTurnover(order.getLastTurnover().subtract(price));//设置剩余数量
            }
            bymatchMap.put(TRANS_NUM, transNum);
        }

        //设置修改时间，修改版本
        order.setModifyTime(new Date());
        order.setVersion(order.getVersion() + 1);//版本号+1

        //插入成交双方订单信息
        String illId = HUOBI_ORDER_ID;//写死36个0
        String recordId = insertTradingRecord(illId, order.getId(), bymatchMap.get(TRANS_PRICE), bymatchMap.get(TRANS_PRICE),
                bymatchMap.get(TRANS_NUM), order.getCoinName(), order.getUnitName(), order.getOrderType());

        //挂单手续费
        BigDecimal makerCharge = configUtil.checkIsMakerCharge(order.getUserId());
        //更新钱包
        updateMatchWallet(bymatchMap, order, makerCharge, CctDataEnums.DETAIL_TYPE_MAKER.getStrVlue(), recordId);
        orderService.updateByPrimaryKeySelective(order);
    }

    /**
     * 处理量化交易限价交易
     */
    private void handleQuantizedLimit(PublishOrder order, BigDecimal transNum, BigDecimal price, boolean isFill) {
        Map<String, BigDecimal> bymatchMap = new HashMap<>();
        bymatchMap.put(TRANS_PRICE, order.getUnitPrice());//成交价格

        //判断是否是完全成交
        if (isFill) {
            bymatchMap.put(TRANS_NUM, order.getLastNum());
            bymatchMap.put(TRANS_TURNOVER, order.getLastTurnover());
            order.setLastNum(BigDecimal.ZERO);//将剩余数量设置为0
            order.setLastTurnover(BigDecimal.ZERO);//将剩余总额设置为0
            order.setOrderStatus(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue());//设置为已完成
        } else {
            bymatchMap.put(TRANS_NUM, transNum);
            bymatchMap.put(TRANS_TURNOVER, price);
            order.setLastNum(order.getLastNum().subtract(transNum));//设置剩余数量
            order.setLastTurnover(order.getLastTurnover().subtract(price));//设置剩余总额
        }

        //设置修改时间，修改版本
        Date now = new Date();
        order.setModifyTime(now);
        order.setVersion(order.getVersion() + 1);//版本号+1

        //插入成交双方订单信息
        String illId = HUOBI_ORDER_ID;//写死36个0
        String recordId = insertTradingRecord(illId, order.getId(), bymatchMap.get(TRANS_PRICE), bymatchMap.get(TRANS_PRICE),
                bymatchMap.get(TRANS_NUM), order.getCoinName(), order.getUnitName(), order.getOrderType());

        //挂单手续费
        BigDecimal makerCharge = configUtil.checkIsMakerCharge(order.getUserId());
        //更新钱包
        updateMatchWallet(bymatchMap, order, makerCharge, CctDataEnums.DETAIL_TYPE_MAKER.getStrVlue(), recordId);
        orderService.updateByPrimaryKeySelective(order);
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public boolean handleMatch(String matchId, String bymatchId) {
        //查询撮合订单和被撮合订单，使用排他锁
        PublishOrder match = orderService.selectByIdForUpdate(matchId);
        PublishOrder bymatch = orderService.selectByIdForUpdate(bymatchId);

        String coinName = match.getCoinName();
        String unitName = match.getUnitName();

        //如果被撮合订单状态不是已撮合，返回true，继续撮合下一条数据
        if (!bymatch.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_MATCH.getStrVlue())) {
            return true;
        }
        //如果撮合订单状态不是已撮合，返回false，终止外部撮合程序
        if (!match.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_MATCH.getStrVlue())) {
            return false;
        }

        //变量保存撮合订单数据，用于被撮合订单的数据计算
        BigDecimal mprice = match.getUnitPrice();
        BigDecimal mlastNum = match.getLastNum();
        BigDecimal mturnover = match.getLastTurnover();

        //计算撮合数据完毕，返回撮合数据
        Map<String, BigDecimal> matchMap = checkPublishType(match, bymatch.getUnitPrice(), bymatch.getLastNum(), bymatch.getLastTurnover());
        Map<String, BigDecimal> bymatchMap = checkPublishType(bymatch, mprice, mlastNum, mturnover);

        //插入成交双方订单信息
        String recordId = insertTradingRecord(match.getId(), bymatch.getId(), matchMap.get(TRANS_PRICE), bymatchMap.get(TRANS_PRICE),
                matchMap.get(TRANS_NUM), coinName, unitName, bymatch.getOrderType());

        //挂单手续费
        BigDecimal makerCharge = configUtil.checkIsMakerCharge(bymatch.getUserId());
        //吃单手续费
        BigDecimal takerCharge = configUtil.checkIsTakerCharge(match.getUserId());

        //更新撮合双方的钱包以及插入变更信息
        //主动撮合方是吃单、被动撮合是挂单
        updateMatchWallet(matchMap, match, takerCharge, CctDataEnums.DETAIL_TYPE_TAKER.getStrVlue(), recordId);
        updateMatchWallet(bymatchMap, bymatch, makerCharge, CctDataEnums.DETAIL_TYPE_MAKER.getStrVlue(), recordId);

        //被撮合订单的单价、成交数量、订单类型
        BigDecimal bymatchPrice = bymatchMap.get(TRANS_PRICE);
        BigDecimal bymatchTransNum = bymatchMap.get(TRANS_NUM);
        String bymatchOrderType = bymatch.getOrderType();
        //撮合订单的单价、成交数量、订单类型
        BigDecimal matchPrice = matchMap.get(TRANS_PRICE);
        BigDecimal matchTransNum = matchMap.get(TRANS_NUM);
        String matchOrderType = match.getOrderType();

        //判断订单剩余是否可继续撮合
        boolean isMatch = checkMatchLast(match);

        //更新缓存中的盘口数据
        lockRedisOrderList(coinName, unitName, matchOrderType, Double.parseDouble(matchPrice.toString()), matchTransNum);
        lockRedisOrderList(coinName, unitName, bymatchOrderType, Double.parseDouble(bymatchPrice.toString()), bymatchTransNum);

        //撮合完毕，异步执行行情数据更新、广播最新行情
        asyncSend(bymatchPrice, bymatchTransNum, coinName, unitName, bymatchOrderType);

        return isMatch;
    }

    /***
     * 检查发布类型
     * @param order
     * @param price
     * @param lastNum
     * @param lastTurnover
     */
    private Map<String, BigDecimal> checkPublishType(PublishOrder order, BigDecimal price, BigDecimal lastNum, BigDecimal lastTurnover) {
        //订单发布类型
        String publishType = order.getPublishType();
        //撮合数据
        Map<String, BigDecimal> trans = new HashMap<>();

        //限价订单
        if (publishType.equals(CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue())) {
            //计算后返回撮合数据
            trans = toCalculateLimitOrder(order, lastNum, lastTurnover);
            trans.put(TRANS_PRICE, order.getUnitPrice());
        }
        //市价订单
        if (publishType.equals(CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue())) {
            //计算后返回撮合数据
            trans = toCalculateMarketOrder(order, price, lastNum, lastTurnover);
            trans.put(TRANS_PRICE, price);
        }

        return trans;
    }

    /***
     * 计算限价订单
     * @param order
     * @param lastNum
     */
    private Map<String, BigDecimal> toCalculateLimitOrder(PublishOrder order, BigDecimal lastNum, BigDecimal lastTurnover) {
        //限价订单扣除情况一致
        //撮合数量
        BigDecimal transNum = toCalculateLimitTransNum(order.getUnitPrice(), order.getLastNum(), lastNum, lastTurnover);
        //撮合交易额
        BigDecimal turnover = toCalculateLimitTransTurnover(order.getUnitPrice(), order.getLastTurnover(), transNum, lastTurnover);
        //订单扣除 剩余数量 剩余交易额
        order.setLastNum(order.getLastNum().subtract(transNum));
        order.setLastTurnover(order.getLastTurnover().subtract(turnover));
        //如果剩余数量不足，设置为已完成
        if (order.getLastNum().compareTo(ZERO) <= 0) {
            order.setOrderStatus(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue());
        }
        //如果剩余交易额不足，设置为已完成
        if (order.getLastTurnover().compareTo(ZERO) <= 0) {
            order.setOrderStatus(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue());
        }
        //更新版本、时间
        order.setVersion(order.getVersion() + 1);
        order.setModifyTime(new Date());
        orderService.updateByPrimaryKeySelective(order);

        //返回撮合成功数据
        Map<String, BigDecimal> trans = new HashMap<>();
        trans.put(TRANS_NUM, transNum);
        trans.put(TRANS_TURNOVER, turnover);
        return trans;
    }

    /***
     * 计算限价交易撮合数量
     *
     * @param price
     * @param orderLastNum
     * @param lastNum
     * @param lastTurnover
     * @return
     */
    private BigDecimal toCalculateLimitTransNum(BigDecimal price, BigDecimal orderLastNum, BigDecimal lastNum, BigDecimal lastTurnover) {
        //如果没有剩余数量，代表对方订单是市价买单
        if (lastNum == null || lastNum.compareTo(ZERO) == 0) {
            //计算市价买单的剩余数量
            lastNum = lastTurnover.divide(price, CctDataEnums.DECIMAL_LENGTH.getIntValue(), BigDecimal.ROUND_DOWN);
        }
        //计算撮合数量
        return orderLastNum.min(lastNum);
    }

    /***
     * 计算限价交易撮合交易额
     *
     * @param price
     * @param orderLastTurnover
     * @param transNum
     * @param lastTurnover
     * @return
     */
    private BigDecimal toCalculateLimitTransTurnover(BigDecimal price, BigDecimal orderLastTurnover, BigDecimal transNum, BigDecimal lastTurnover) {
        //lastTurnover为空，代表对方订单是市价卖单
//        if (lastTurnover == null || lastTurnover.compareTo(ZERO) == 0) {
//            return price.multiply(transNum);
//        } else {
//            //其他情况返回
//            return orderLastTurnover.min(lastTurnover);
//        }

        return price.multiply(transNum);
    }

    /***
     * 计算市价订单类型（买卖）
     * @param order
     * @param price
     * @param lastNum
     * @param lastTurnover
     */
    private Map<String, BigDecimal> toCalculateMarketOrder(PublishOrder order, BigDecimal price, BigDecimal lastNum, BigDecimal lastTurnover) {
        //撮合数量
        BigDecimal transNum = ZERO;
        //交易额
        BigDecimal turnover = ZERO;
        //市价买
        if (order.getOrderType().equals(CctDataEnums.ORDER_TYPE_BUY.getStrVlue())) {
            //撮合交易额
            turnover = order.getLastTurnover().min(lastTurnover);
            //订单扣除 剩余交易额
            order.setLastTurnover(order.getLastTurnover().subtract(turnover));
            //如果剩余交易额不足，设置为已完成
            if (order.getLastTurnover().compareTo(ZERO) <= 0) {
                order.setOrderStatus(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue());
            }
            //撮合数量
            transNum = turnover.divide(price, CctDataEnums.DECIMAL_LENGTH.getIntValue(), BigDecimal.ROUND_DOWN);
        }

        //市价卖
        if (order.getOrderType().equals(CctDataEnums.ORDER_TYPE_SELL.getStrVlue())) {
            //撮合数量
            transNum = order.getLastNum().min(lastNum);
            //交易额
            turnover = price.multiply(transNum);
            //订单扣除 剩余数量
            order.setLastNum(order.getLastNum().subtract(transNum));
            //如果剩余数量不足，设置为已完成
            if (order.getLastNum().compareTo(ZERO) <= 0) {
                order.setOrderStatus(CctDataEnums.ORDER_STATUS_FINISH.getStrVlue());
            }
        }

        //更新版本、时间
        order.setVersion(order.getVersion() + 1);
        order.setModifyTime(new Date());
        orderService.updateByPrimaryKeySelective(order);

        //返回撮合成功数据
        Map<String, BigDecimal> trans = new HashMap<>();
        trans.put(TRANS_NUM, transNum);
        trans.put(TRANS_TURNOVER, turnover);
        return trans;
    }

    /****
     * 撮合后更新钱包数据，并插入变更记录
     *
     * 处理买方钱包：
     * coinWallet ：totalNum +（transNum - serviceCharge）
     *              freeNum +（transNum - serviceCharge）
     * unitWallet ：totalNum -（transNum * price）
     *              blockNum -（transNum * price）
     *
     * 处理卖方钱包：
     * coinWallet ：totalNum - transNum
     *              blockNum - transNum
     * unitWallet ：totalNum +（transNum * price - serviceCharge）
     *              freeNum +（transNum * price - serviceCharge）
     *
     * @param trans 撮合数据
     * @param order 订单对象
     * @param serviceChargeRatio 手续费比例
     * @param tradingType 交易类型 挂单 | 吃单
     * @param recordId
     */
    private void updateMatchWallet(Map<String, BigDecimal> trans, PublishOrder order, BigDecimal serviceChargeRatio, String
            tradingType, String recordId) {
        //基本货币、二级货币
        String coinName = order.getCoinName();
        String unitName = order.getUnitName();
        //查询币种详细信息
        Coin coin = coinService.selectCoin(coinName, unitName, CctDataEnums.COMMON_STATUS_YES.getStrVlue());

        String coinNet = coin.getCoinNet();
        String unitNet = coin.getUnitNet();
        String userId = order.getUserId();
        String orderId = order.getId();
        String orderType = order.getOrderType();

        //撮合数据
        BigDecimal transNum = trans.get(TRANS_NUM);
        BigDecimal transPrice = trans.get(TRANS_PRICE);
        BigDecimal turnover = trans.get(TRANS_TURNOVER);

        //扣除的手续费
        BigDecimal serviceCharge = BigDecimal.ZERO;
        //增加的代币
        String getCoin = null;
        //增加的代币数量
        BigDecimal getAmount = BigDecimal.ZERO;

        //买方
        if (orderType.equals(CctDataEnums.ORDER_TYPE_BUY.getStrVlue())) {
            //买方手续费
            serviceCharge = transNum.multiply(serviceChargeRatio);
            //增加余额的代币
            getCoin = coinName;
            //增加余额的数量
            getAmount = transNum;
            //coin钱包实际增加的数量
            BigDecimal realAmount = transNum.subtract(serviceCharge);
            //unit钱包扣除的数量
            BigDecimal deduct = turnover.multiply(MINUS_ONE);
            //插入成交订单详情记录
            String detailId = insertTradingDetail(orderId, recordId, transNum, transPrice, realAmount, tradingType,
                    serviceChargeRatio, serviceCharge, coinName);
            //扣除总余额、冻结余额，单位：二级货币
            walletService.handleRealBalance(userId, detailId, unitName, unitNet, ZERO, deduct, ZERO);

            //增加总余额、可用余额，单位：基本货币
            walletService.handleRealBalance(userId, detailId, getCoin, coinNet, realAmount, ZERO, serviceCharge);
        }

        //卖方
        if (orderType.equals(CctDataEnums.ORDER_TYPE_SELL.getStrVlue())) {
            //卖方手续费
            serviceCharge = turnover.multiply(serviceChargeRatio);
            //增加余额的代币
            getCoin = unitName;
            //增加余额的数量
            getAmount = turnover;
            //unit钱包实际增加的数量
            BigDecimal realAmount = turnover.subtract(serviceCharge);
            //coin钱包扣除的数量
            BigDecimal deduct = transNum.multiply(MINUS_ONE);
            //插入成交订单详情记录
            String detailId = insertTradingDetail(orderId, recordId, transNum, transPrice, realAmount, tradingType,
                    serviceChargeRatio, serviceCharge, unitName);
            //扣除总余额、冻结余额，单位：基本货币
            walletService.handleRealBalance(userId, detailId, coinName, coinNet, ZERO, deduct, ZERO);
            //增加总余额、可用余额，单位：二级货币
            walletService.handleRealBalance(userId, detailId, getCoin, unitNet, realAmount, ZERO, serviceCharge);
        }

        //生成矿机佣金
        oreTx(userId, getAmount, serviceCharge, getCoin);
    }

    /***
     * 判断订单是否可以继续撮合
     *
     * @param order
     * @return
     */
    private boolean checkMatchLast(PublishOrder order) {
        //如果是市价卖单，则判断剩余数量
        //其余的类型，则判断剩余交易额
        if (order.getPublishType().equals(CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue()) &&
                order.getOrderType().equals(CctDataEnums.ORDER_TYPE_SELL.getStrVlue())) {
            if (order.getLastNum().compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
        } else {
            if (order.getLastTurnover().compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
        }
        return true;
    }

    /***
     * 插入成交订单扣费记录
     * @param orderId 订单id
     * @param recordId 成交记录
     * @param realAmount 最终获得的数量
     * @param tradingType 成交订单类型 吃单|挂单
     * @param serviceChargeRatio 手续费比例
     * @param serviceCharge 实际手续费
     * @param coinName 币种
     * @return
     */
    private String insertTradingDetail(String orderId, String recordId, BigDecimal tradingNum, BigDecimal pirce,
                                       BigDecimal realAmount, String tradingType, BigDecimal serviceChargeRatio,
                                       BigDecimal serviceCharge, String coinName) {
        TradingDetail detail = new TradingDetail();
        String uuid = UUID.randomUUID().toString();
        detail.setId(UUID.randomUUID().toString());
        detail.setRecordId(recordId);
        detail.setPublishId(orderId);
        detail.setTotalAmount(realAmount.add(serviceCharge));
        detail.setRealAmount(realAmount);
        detail.setTradingNum(tradingNum);
        detail.setUnitPrice(pirce);
        detail.setServiceCharge(serviceCharge);
        detail.setChargeRatio(serviceChargeRatio);
        detail.setCoinName(coinName);
        detail.setTradingType(tradingType);
        detail.setCreateTime(new Date());
        try{
            detailService.insertTradingDetail(detail);
        }catch (Exception ex){
         LOG.error(ex.getMessage());
        }

        return uuid;
    }

    /***
     * 插入成交双方订单记录
     * @param takerId
     * @param makerId
     * @param takerPrice
     * @param makerPrice
     * @param transNum
     * @param coinName
     * @param unitName
     * @param tradingType
     * @return
     */
    private String insertTradingRecord(String takerId, String makerId, BigDecimal takerPrice,
                                       BigDecimal makerPrice, BigDecimal transNum,
                                       String coinName, String unitName, String tradingType) {
        String uuid = UUID.randomUUID().toString();
        Date now = new Date();
        TradingRecord record = new TradingRecord();
        record.setId(uuid);
        record.setTakerId(takerId);
        record.setMakerId(makerId);
        record.setTakerPrice(takerPrice);
        record.setMakerPrice(makerPrice);
        record.setTradingNum(transNum);
        record.setTradingType(tradingType);
        record.setCoinName(coinName);
        record.setUnitName(unitName);
        record.setCreateTime(now);
        recordService.insertTradingRecord(record);
        return uuid;
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
        oreDTO.setTxType(CctDataEnums.CCT_APP.getStrVlue());
        oreDTO.setTokenSymbol(tokenSymbol);
        oreDTO.setTokenAmount(tokenAmount);
        oreDTO.setFee(fee);
        oreDTO.setTxDate(new Date());
    }

    /***
     * 异步调用
     * 插入最新行情、广播前端
     * @param price
     * @param transNum
     * @param coinName
     * @param unitName
     * @param tradingType
     */
    private void asyncSend(BigDecimal price, BigDecimal transNum, String coinName, String unitName, String tradingType) {
        //使用异步线程插入最新行情
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //发送广播到前端，更新前端行情
                redisTool.send(coinName, unitName);
                //查询K线机器人状态
                Boolean flag = getBotCurrencyConfigStatus(coinName, unitName);
                //K线机器人关闭时
                if(!flag){
                    //开启插入最新行情
                    currencyFeign.save(coinName, unitName, price, transNum, new Date().getTime(), tradingType);
                }
            }
        });
    }

    /***
     * 根据币对查询K线机器人状态
     * @param coinName
     * @param unitName
     * @return
     */
    private Boolean getBotCurrencyConfigStatus(String coinName, String unitName) {
        ResultDTO<Boolean> result = botFeign.getCurrencyConfigStatus(coinName, unitName);
        return result.getData();
    }

    /***
     * 判断是否存在key
     * 存在则进行加锁并更新redis盘口数据
     * @param coinName
     * @param unitName
     * @param orderType
     * @param price
     * @param transNum
     */
    private void lockRedisOrderList(String coinName, String unitName, String orderType, Double price, BigDecimal transNum) {
        //是否成功获取锁标识
        boolean lockFlag = false;
        //加锁，进行添加数据
        while (!lockFlag) {
            //循环，直到获取锁为止
            lockFlag = orderCache.tryFairLock(redissonClient, coinName, unitName, orderType);
            //拿到锁，处理数据
            if (lockFlag) {
                //根据单价（sortSet排序分数）获取订单数据
                Set redisOrder = orderCache.getZSetByScore(coinName, unitName, orderType, price);
                //用迭代器更新数据
                Iterator iterator = redisOrder.iterator();
                //处理数据
                while (iterator.hasNext()) {
                    MarketDTO order = (MarketDTO) iterator.next();
                    //更新剩余数量
                    order.setTotalLastNum(order.getTotalLastNum().subtract(transNum));
                    //删除旧数据
                    orderCache.removeZSetByScore(coinName, unitName, orderType, price);
                    //如果剩余数量大于0，添加数据
                    if (order.getTotalLastNum().compareTo(BigDecimal.ZERO) > 0) {
                        //覆盖数据
                        orderCache.setZSetValue(coinName, unitName, orderType, order, price);
                    }
                }
                //释放锁
                orderCache.unFairLock(redissonClient, coinName, unitName, orderType);
            }
        }
    }
}
