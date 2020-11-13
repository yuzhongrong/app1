package com.blockchain.server.cct.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.server.cct.common.constants.QuantizedConstant;
import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.common.enums.CctEnums;
import com.blockchain.server.cct.common.exception.CctException;
import com.blockchain.server.cct.common.util.CCTExceptionPreconditionUtils;
import com.blockchain.server.cct.common.util.CheckConfigUtil;
import com.blockchain.server.cct.dto.order.PublishOrderParamDTO;
import com.blockchain.server.cct.entity.Coin;
import com.blockchain.server.cct.entity.PublishOrder;
import com.blockchain.server.cct.entity.TradingOn;
import com.blockchain.server.cct.feign.QuantizedFeign;
import com.blockchain.server.cct.feign.UserFeign;
import com.blockchain.server.cct.mapper.PublishOrderMapper;
import com.blockchain.server.cct.redis.PublishOrderCache;
import com.blockchain.server.cct.service.CoinService;
import com.blockchain.server.cct.service.PublishOrderService;
import com.blockchain.server.cct.service.WalletService;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PublishOrderServiceImpl implements PublishOrderService, ITxTransaction {
    private static final Logger LOG = LoggerFactory.getLogger(PublishOrderServiceImpl.class);

    @Autowired
    private CoinService coinService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PublishOrderMapper publishOrderMapper;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private QuantizedFeign quantizedFeign;
    @Autowired
    private CheckConfigUtil checkConfigUtil;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private PublishOrderCache orderCache;

    private static final BigDecimal MINUS_ONE = new BigDecimal("-1");//负一

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public String handleLimitBuy(PublishOrderParamDTO paramDTO) {
        //发布参数判空、是否合法
        checkPublishStrParam(paramDTO);
        checkLimitPublishNum(paramDTO);
        //是否休市
        checkConfigUtil.checkIsClosed();
        //币对是否可用
        Coin coin = coinService.checkCoinIsYes(paramDTO.getCoinName(), paramDTO.getUnitName());
        //校验数量、单价小数位是否合法
        checkPublishDecimal(paramDTO.getTotalNum(), coin.getCoinDecimals(), CctEnums.ORDER_NUM_ERROR);
        checkPublishDecimal(paramDTO.getUnitPrice(), coin.getUnitDecimals(), CctEnums.ORDER_PRICE_ERROR);

        //用户是否有权限交易
        userFeign.hasLowAuthAndUserList(paramDTO.getUserId());
        //校验密码
        walletService.isPassword(paramDTO.getPass());

        //计算数据
        BigDecimal totalNum = paramDTO.getTotalNum(); //发布数量
        BigDecimal price = paramDTO.getUnitPrice(); //单价
        //交易额
        BigDecimal turnover = totalNum.multiply(price).setScale(CctDataEnums.DECIMAL_LENGTH.getIntValue(), BigDecimal.ROUND_DOWN);
        //设置交易额
        paramDTO.setTurnover(turnover);
        //计算冻结金额
        BigDecimal deduct = turnover.multiply(MINUS_ONE);
        //插入订单
        String publishId = insertOrder(paramDTO, CctDataEnums.ORDER_TYPE_BUY.getStrVlue(),
                CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), CctDataEnums.ORDER_STATUS_NEW.getStrVlue());

        // 如果是量化交易，调用量化交易feign
        if (this.getTradingOn(paramDTO.getCoinName(), paramDTO.getUnitName())) {
            ResultDTO resultDTO = quantizedFeign.createBuyLimit(
                    (paramDTO.getCoinName() + paramDTO.getUnitName()).toLowerCase(),
                    paramDTO.getTotalNum(),
                    paramDTO.getUnitPrice(),
                    paramDTO.getUserId(), publishId);
            if (resultDTO == null) throw new CctException(CctEnums.NET_ERROR);
            if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS)
                throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
        }
        //冻结余额
        walletService.handleBalance(paramDTO.getUserId(), publishId, coin.getUnitName(), coin.getUnitNet(), deduct, turnover);

        //将新发布的盘口数据存入缓存中
        addRedisOrderList(paramDTO, CctDataEnums.ORDER_TYPE_BUY.getStrVlue());

        return publishId;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public String handleLimitSell(PublishOrderParamDTO paramDTO) {
        //发布参数判空、是否合法
        checkPublishStrParam(paramDTO);
        checkLimitPublishNum(paramDTO);
        //是否休市
        checkConfigUtil.checkIsClosed();
        //币对是否可用
        Coin coin = coinService.checkCoinIsYes(paramDTO.getCoinName(), paramDTO.getUnitName());
        //校验数量、单价小数位是否合法
        checkPublishDecimal(paramDTO.getTotalNum(), coin.getCoinDecimals(), CctEnums.ORDER_NUM_ERROR);
        checkPublishDecimal(paramDTO.getUnitPrice(), coin.getUnitDecimals(), CctEnums.ORDER_PRICE_ERROR);

        //用户是否有交易权限
        userFeign.hasLowAuthAndUserList(paramDTO.getUserId());
        //校验密码
        walletService.isPassword(paramDTO.getPass());


        BigDecimal totalNum = paramDTO.getTotalNum(); //发布数量
        BigDecimal price = paramDTO.getUnitPrice(); //单价
        //交易额
        BigDecimal turnover = totalNum.multiply(price).setScale(CctDataEnums.DECIMAL_LENGTH.getIntValue(), BigDecimal.ROUND_DOWN);
        //设置交易额
        paramDTO.setTurnover(turnover);
        //计算冻结余额
        BigDecimal deduct = totalNum.multiply(MINUS_ONE);
        //插入订单
        String publishId = insertOrder(paramDTO, CctDataEnums.ORDER_TYPE_SELL.getStrVlue(),
                CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), CctDataEnums.ORDER_STATUS_NEW.getStrVlue());

        // 如果是量化交易，调用量化交易feign
        if (this.getTradingOn(paramDTO.getCoinName(), paramDTO.getUnitName())) {
            ResultDTO resultDTO = quantizedFeign.createSellLimit(
                    (paramDTO.getCoinName() + paramDTO.getUnitName()).toLowerCase(),
                    paramDTO.getTotalNum(),
                    paramDTO.getUnitPrice(),
                    paramDTO.getUserId(), publishId);
            if (resultDTO == null) throw new CctException(CctEnums.NET_ERROR);
            if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS)
                throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
        }
        //冻结余额
        walletService.handleBalance(paramDTO.getUserId(), publishId, coin.getCoinName(), coin.getCoinNet(), deduct, totalNum);

        //将新发布的盘口数据存入缓存中
        addRedisOrderList(paramDTO, CctDataEnums.ORDER_TYPE_SELL.getStrVlue());

        return publishId;
    }

    @Override
    @Transactional
    @TxTransaction
    public String handleLimitBuyToBot(String userId, String coinName, String unitName, BigDecimal totalNum, BigDecimal price) {
        //币对是否可用
        Coin coin = coinService.checkCoinIsYes(coinName, unitName);
        //交易额
        BigDecimal turnover = totalNum.multiply(price).setScale(CctDataEnums.DECIMAL_LENGTH.getIntValue(), BigDecimal.ROUND_DOWN);
        //构造发布参数对象
        PublishOrderParamDTO paramDTO = new PublishOrderParamDTO(userId, null, unitName, coinName, totalNum, price, turnover);
        //计算冻结金额
        BigDecimal deduct = turnover.multiply(MINUS_ONE);
        //插入订单
        String publishId = insertOrder(paramDTO, CctDataEnums.ORDER_TYPE_BUY.getStrVlue(),
                CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), CctDataEnums.ORDER_STATUS_MATCH.getStrVlue());
        //冻结余额
        walletService.handleBalance(userId, publishId, coin.getUnitName(), coin.getUnitNet(), deduct, turnover);
        //将新发布的盘口数据存入缓存中
        addRedisOrderList(paramDTO, CctDataEnums.ORDER_TYPE_BUY.getStrVlue());
        //返回订单id
        return publishId;
    }

    @Override
    @Transactional
    @TxTransaction
    public String handleLimitSellToBot(String userId, String coinName, String unitName, BigDecimal totalNum, BigDecimal price) {
        //币对是否可用
        Coin coin = coinService.checkCoinIsYes(coinName, unitName);
        //交易额
        BigDecimal turnover = totalNum.multiply(price).setScale(CctDataEnums.DECIMAL_LENGTH.getIntValue(), BigDecimal.ROUND_DOWN);
        //构造发布参数对象
        PublishOrderParamDTO paramDTO = new PublishOrderParamDTO(userId, null, unitName, coinName, totalNum, price, turnover);
        //计算冻结余额
        BigDecimal deduct = totalNum.multiply(MINUS_ONE);
        //插入订单
        String publishId = insertOrder(paramDTO, CctDataEnums.ORDER_TYPE_SELL.getStrVlue(),
                CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), CctDataEnums.ORDER_STATUS_MATCH.getStrVlue());
        //冻结余额
        walletService.handleBalance(userId, publishId, coin.getCoinName(), coin.getCoinNet(), deduct, totalNum);
        //将新发布的盘口数据存入缓存中
        addRedisOrderList(paramDTO, CctDataEnums.ORDER_TYPE_SELL.getStrVlue());
        //返回订单id
        return publishId;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public String handleMarketBuy(PublishOrderParamDTO paramDTO) {
        //发布参数判空、是否合法
        checkPublishStrParam(paramDTO);
        checkMarketPublishNum(paramDTO, CctDataEnums.ORDER_TYPE_BUY.getStrVlue());
        //是否休市
        checkConfigUtil.checkIsClosed();
        //币对是否可用
        Coin coin = coinService.checkCoinIsYes(paramDTO.getCoinName(), paramDTO.getUnitName());
        //校验数量、单价小数位是否合法
        checkPublishDecimal(paramDTO.getTurnover(), coin.getUnitDecimals(), CctEnums.ORDER_TURNOVER_ERROR);

        //用户是否有权限交易
        userFeign.hasLowAuthAndUserList(paramDTO.getUserId());
        //校验密码
        walletService.isPassword(paramDTO.getPass());


        //计算冻结余额
        BigDecimal deduct = paramDTO.getTurnover().multiply(MINUS_ONE);
        //插入订单
        String publishId = insertOrder(paramDTO, CctDataEnums.ORDER_TYPE_BUY.getStrVlue(),
                CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue(), CctDataEnums.ORDER_STATUS_NEW.getStrVlue());
        // 如果是量化交易，调用量化交易feign
        if (this.getTradingOn(paramDTO.getCoinName(), paramDTO.getUnitName())) {
            ResultDTO resultDTO = quantizedFeign.createBuyMarket(
                    (paramDTO.getCoinName() + paramDTO.getUnitName()).toLowerCase(),
                    paramDTO.getTurnover(),
                    paramDTO.getUserId(), publishId);
            if (resultDTO == null) throw new CctException(CctEnums.NET_ERROR);
            if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS)
                throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
        }
        //冻结余额
        walletService.handleBalance(paramDTO.getUserId(), publishId, coin.getUnitName(), coin.getUnitNet(), deduct, paramDTO.getTurnover());

        return publishId;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public String handleMarketSell(PublishOrderParamDTO paramDTO) {
        //发布参数判空、是否合法
        checkPublishStrParam(paramDTO);
        checkMarketPublishNum(paramDTO, CctDataEnums.ORDER_TYPE_SELL.getStrVlue());
        //是否休市
        checkConfigUtil.checkIsClosed();
        //币对是否可用
        Coin coin = coinService.checkCoinIsYes(paramDTO.getCoinName(), paramDTO.getUnitName());
        //校验数量、单价小数位是否合法
        checkPublishDecimal(paramDTO.getTotalNum(), coin.getCoinDecimals(), CctEnums.ORDER_NUM_ERROR);

        //用户是否有权限交易
        userFeign.hasLowAuthAndUserList(paramDTO.getUserId());
        //校验密码
        walletService.isPassword(paramDTO.getPass());


        //计算冻结余额
        BigDecimal deduct = paramDTO.getTotalNum().multiply(MINUS_ONE);
        //插入订单
        String publishId = insertOrder(paramDTO, CctDataEnums.ORDER_TYPE_SELL.getStrVlue(),
                CctDataEnums.PUBLISH_TYPE_MARKET.getStrVlue(), CctDataEnums.ORDER_STATUS_NEW.getStrVlue());

        // 如果是量化交易，调用量化交易feign
        if (this.getTradingOn(paramDTO.getCoinName(), paramDTO.getUnitName())) {
            ResultDTO resultDTO = quantizedFeign.createSellMarket(
                    (paramDTO.getCoinName() + paramDTO.getUnitName()).toLowerCase(),
                    paramDTO.getTotalNum(),
                    paramDTO.getUserId(), publishId);
            if (resultDTO == null) throw new CctException(CctEnums.NET_ERROR);
            if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS)
                throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
        }
        //冻结余额
        walletService.handleBalance(paramDTO.getUserId(), publishId, coin.getCoinName(), coin.getCoinNet(), deduct, paramDTO.getTotalNum());

        return publishId;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public int handleCancelOrder(String orderId, String userId) {
        //参数判空
        CCTExceptionPreconditionUtils.checkStringNotBlank(orderId, CctEnums.ORDER_ID_NULL);
        CCTExceptionPreconditionUtils.checkStringNotBlank(userId, CctEnums.ORDER_USERID_NULL);

        //查询订单（排他锁）
        PublishOrder order = publishOrderMapper.selectByIdForUpdate(orderId);
        //判断订单是否为空
        CCTExceptionPreconditionUtils.checkNotNull(order, CctEnums.ORDER_NULL);
        //操作用户不匹配
        if (!order.getUserId().equals(userId)) {
            throw new CctException(CctEnums.ORDER_USERID_ERROR);
        }
        if (order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_CANCEL.getStrVlue())) {
            //如果是已撤單狀態，直接返回
            return 1;
        }
        //订单状态不等于新建和已撮合和撤单中
        if (!order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_NEW.getStrVlue()) &&
                !order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_MATCH.getStrVlue()) &&
                !order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_CANCELING.getStrVlue())) {
            throw new CctException(CctEnums.ORDER_CANCEL_ERROR);
        }
        //退回撤单余额
        checkCancelOrderType(order);


        //更新订单
        order.setOrderStatus(CctDataEnums.ORDER_STATUS_CANCEL.getStrVlue());
        order.setModifyTime(new Date());
        int row = publishOrderMapper.updateByPrimaryKeySelective(order);
        if (row > 0) {
            Double price = Double.parseDouble(order.getUnitPrice().toString());
            //更新redis缓存中的订单数据
            updateRedisOrderList(price, order.getLastNum(), order.getCoinName(), order.getUnitName(), order.getOrderType());
            return row;
        } else {
            return row;
        }
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public int handleCancelOrder(String orderId) {
        //查询订单（排他锁）
        PublishOrder order = publishOrderMapper.selectByIdForUpdate(orderId);
        //判断订单是否为空
        if (order == null) {
            return 0;
        }
        //判断订单状态是否是已撮合
        if (!order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_MATCH.getStrVlue())) {
            return 0;
        }
        //退回撤单余额
        checkCancelOrderType(order);

        //更新订单
            order.setOrderStatus(CctDataEnums.ORDER_STATUS_CANCEL.getStrVlue());
        order.setModifyTime(new Date());
        return publishOrderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    @Transactional
    @TxTransaction
    public int handleCancelOrderToBot(String orderId, String userId) {
        //查询订单（排他锁）
        PublishOrder order = publishOrderMapper.selectByIdForUpdate(orderId);
        //判断订单是否为空
        CCTExceptionPreconditionUtils.checkNotNull(order, CctEnums.ORDER_NULL);
        //操作用户不匹配
        if (!order.getUserId().equals(userId)) {
            return 0;
        }
        //订单状态不等于新建和已撮合
        if (!order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_NEW.getStrVlue()) &&
                !order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_MATCH.getStrVlue())) {
            return 0;
        }
        //退回撤单余额
        checkCancelOrderType(order);

        //更新订单
        order.setOrderStatus(CctDataEnums.ORDER_STATUS_CANCEL.getStrVlue());
        order.setModifyTime(new Date());
        int row = publishOrderMapper.updateByPrimaryKeySelective(order);
        if (row > 0) {
            Double price = Double.parseDouble(order.getUnitPrice().toString());
            //更新redis缓存中的订单数据
            updateRedisOrderList(price, order.getLastNum(), order.getCoinName(), order.getUnitName(), order.getOrderType());
            return row;
        } else {
            return row;
        }
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(PublishOrder order) {
        return publishOrderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    @Transactional
    public int updateStatusInVersionLock(String orderId, String beforeStatus, String laterStatus) {
        return publishOrderMapper.updateStatusInVersionLock(orderId, beforeStatus, laterStatus, new Date());
    }

    @Override
    public PublishOrder selectById(String orderId) {
        return publishOrderMapper.selectById(orderId);
    }

    @Override
    public PublishOrder selectByIdForUpdate(String orderId) {
        return publishOrderMapper.selectByIdForUpdate(orderId);
    }

    @Override
    public List<MarketDTO> listOrder(String coinName, String unitName, String orderType, String orderStatus, String publishType, String sort) {
        //redis是否存在对应的key
        boolean flag = orderCache.hasKey(coinName, unitName, orderType);
        //redis存在key
        if (flag) {
            //返回数据结构
            List<MarketDTO> resultOrders = new ArrayList<>();
            //升序
            if (sort.equals(CctDataEnums.SORT_ASC.getStrVlue())) {
                //range升序获取Redis的sortSet数据
                Set<MarketDTO> redisOrders = orderCache.getZSetASC(coinName, unitName, orderType, 0, -1);
                //用迭代器封装返回数据
                Iterator<MarketDTO> iterator = redisOrders.iterator();
                while (iterator.hasNext()) {
                    resultOrders.add(iterator.next());
                }
            }
            //倒序
            if (sort.equals(CctDataEnums.SORT_DESC.getStrVlue())) {
                //reverseRange降序获取Redis的sortSet数据
                Set<MarketDTO> redisOrders = orderCache.getZSetDESC(coinName, unitName, orderType, 0, -1);
                //用迭代器封装返回数据
                Iterator<MarketDTO> iterator = redisOrders.iterator();
                while (iterator.hasNext()) {
                    resultOrders.add(iterator.next());
                }
            }
            //返回数据
            return resultOrders;
        } else {
            //redis不存在key
            //返回空数据
            return new ArrayList<>();
        }
    }

    @Override
    public List<MarketDTO> listOrderByCoinAndUnitAndStatus(String coinName, String unitName, String orderStatus, String orderType, String sort) {
        return publishOrderMapper.listOrderByCoinAndUnitAndStatus(coinName, unitName, orderStatus,
                orderType, CctDataEnums.PUBLISH_TYPE_LIMIT.getStrVlue(), sort);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<PublishOrder> listUserOrder(String userId, String coinName, String unitName, String orderType, String publishType, String[] orderStatus) {
        return publishOrderMapper.listUserOrder(userId, coinName, unitName, orderType, publishType, orderStatus);
    }

    @Override
    public void handleOrderToCanceling(String orderId) {
        //查询订单（排他锁）
        PublishOrder order = publishOrderMapper.selectByIdForUpdate(orderId);

        if (order == null) {
            LOG.error("没有此订单信息，orderId = " + orderId);
            return;
        }

        if (order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_CANCEL.getStrVlue()) ||
                order.getOrderStatus().equals(CctDataEnums.ORDER_STATUS_CANCELING.getStrVlue())) {
            //如果是已撤單狀態或者撤單中狀態，直接返回
            return;
        }
        order.setOrderStatus(CctDataEnums.ORDER_STATUS_CANCELING.getStrVlue());
        order.setModifyTime(new Date());
        publishOrderMapper.updateByPrimaryKey(order);
    }

    @Override
    public List<PublishOrder> selectByStatus(String status) {
        return null;
    }

    /***
     * 判断撤销订单类型，并解冻余额
     */
    private void checkCancelOrderType(PublishOrder order) {
        //查询币种信息
        Coin coin = coinService.selectCoin(order.getCoinName(), order.getUnitName(), CctDataEnums.COMMON_STATUS_YES.getStrVlue());

        BigDecimal amount = BigDecimal.ZERO; //退回的可用余额
        BigDecimal decut = BigDecimal.ZERO; //解冻的冻结余额
        String coinName = ""; //退回的币种钱包
        String coinNet = ""; //退回的币种主网标识

        //买单退回剩余交易额，单位是二级货币
        if (order.getOrderType().equals(CctDataEnums.ORDER_TYPE_BUY.getStrVlue())) {
            amount = order.getLastTurnover();
            decut = amount.multiply(MINUS_ONE);
            coinName = order.getUnitName();
            coinNet = coin.getUnitNet();
        }
        //卖单退回剩余数量，单位是基本货币
        if (order.getOrderType().equals(CctDataEnums.ORDER_TYPE_SELL.getStrVlue())) {
            amount = order.getLastNum();
            decut = amount.multiply(MINUS_ONE);
            coinName = order.getCoinName();
            coinNet = coin.getCoinNet();
        }

        //解冻余额
        walletService.handleBalance(order.getUserId(), order.getId(), coinName, coinNet, amount, decut);
    }

    /***
     * 校验限价订单的发布数量
     * @param paramDTO
     */
    private void checkLimitPublishNum(PublishOrderParamDTO paramDTO) {
        //单价、数量判空
        CCTExceptionPreconditionUtils.checkNotNull(paramDTO.getTotalNum(), CctEnums.ORDER_NUM_NULL);
        CCTExceptionPreconditionUtils.checkNotNull(paramDTO.getUnitPrice(), CctEnums.ORDER_PRICE_NULL);
        //是否小于等于0
        CCTExceptionPreconditionUtils.checkBigDecimalLessThanOrEqualZero(paramDTO.getTotalNum(), CctEnums.ORDER_NUM_ERROR);
        CCTExceptionPreconditionUtils.checkBigDecimalLessThanOrEqualZero(paramDTO.getUnitPrice(), CctEnums.ORDER_PRICE_ERROR);
    }

    /***
     * 校验市价订单的发布数量
     * @param paramDTO
     * @param orderType
     */
    private void checkMarketPublishNum(PublishOrderParamDTO paramDTO, String orderType) {
        //判断买卖类型
        if (orderType.equals(CctDataEnums.ORDER_TYPE_BUY.getStrVlue())) {
            //判空
            if (paramDTO.getTurnover() == null) {
                throw new CctException(CctEnums.ORDER_TURNOVER_NULL);
            }
            //小于0
            if (paramDTO.getTurnover().compareTo(BigDecimal.ZERO) <= 0) {
                throw new CctException(CctEnums.ORDER_TURNOVER_ERROR);
            }
        }
        if (orderType.equals(CctDataEnums.ORDER_TYPE_SELL.getStrVlue())) {
            //判空
            if (paramDTO.getTotalNum() == null) {
                throw new CctException(CctEnums.ORDER_NUM_NULL);
            }
            //小于0
            if (paramDTO.getTotalNum().compareTo(BigDecimal.ZERO) <= 0) {
                throw new CctException(CctEnums.ORDER_NUM_ERROR);
            }
        }
    }


    /***
     * 校验订单的发布参数
     * @param paramDTO
     */
    private void checkPublishStrParam(PublishOrderParamDTO paramDTO) {
        //用户id、手机、密码、交易对
        CCTExceptionPreconditionUtils.checkStringNotBlank(paramDTO.getUserId(), CctEnums.ORDER_USERID_NULL);
        CCTExceptionPreconditionUtils.checkStringNotBlank(paramDTO.getPass(), CctEnums.ORDER_PASS_NULL);
        CCTExceptionPreconditionUtils.checkStringNotBlank(paramDTO.getCoinName(), CctEnums.ORDER_COINNAME_NULL);
        CCTExceptionPreconditionUtils.checkStringNotBlank(paramDTO.getUnitName(), CctEnums.ORDER_UNITNAME_NULL);
    }


    /***
     * 判断小数长度是否超出限制
     * @param checkBigDecimal
     * @param decimal
     */
    private void checkPublishDecimal(BigDecimal checkBigDecimal, Integer decimal, CctEnums enums) {
        //转为String类型
        String str = checkBigDecimal.toPlainString();

        //判断小数位长度是否超出限制
        if ((str.length() - str.indexOf(".") - 1) > decimal) {
            throw new CctException(enums);
        }
    }

    /**
     * 判断是否量化交易
     *
     * @param coinName
     * @param unitName
     * @return
     */
    public Boolean getTradingOn(String coinName, String unitName) {
        ResultDTO<TradingOn> resultDTO = quantizedFeign.getTradingOn(coinName, unitName);
        if (resultDTO == null) {
            throw new CctException(CctEnums.NET_ERROR);
        }
        if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
        }
        TradingOn tradingOn = resultDTO.getData();
        if (tradingOn == null) {
            return false;
        }
        if (tradingOn.getState().equalsIgnoreCase(QuantizedConstant.STATUS_CLOSEING)) {
            throw new CctException(CctEnums.QUANTIZED_CLOSING);
        }
        if (tradingOn.getState().equalsIgnoreCase(QuantizedConstant.STATUS_OPEN)) {
            return true;
        }
        return false;
    }

    /***
     * 插入订单
     * @param paramDTO
     * @param orderType
     * @param publishType
     * @param orderStatus
     * @return
     */
    private String insertOrder(PublishOrderParamDTO paramDTO, String orderType, String publishType, String orderStatus) {
        PublishOrder order = new PublishOrder();
        String uuid = UUID.randomUUID().toString();
        Date now = new Date();
        order.setId(uuid);
        order.setUserId(paramDTO.getUserId());
        order.setUnitPrice(paramDTO.getUnitPrice());
        order.setTotalNum(paramDTO.getTotalNum());
        order.setLastNum(paramDTO.getTotalNum());
        order.setTotalTurnover(paramDTO.getTurnover());
        order.setLastTurnover(paramDTO.getTurnover());
        order.setCoinName(paramDTO.getCoinName());
        order.setUnitName(paramDTO.getUnitName());
        order.setOrderType(orderType);
        order.setPublishType(publishType);
        order.setOrderStatus(orderStatus);
        order.setCreateTime(now);
        order.setModifyTime(now);
        order.setVersion(0);
        publishOrderMapper.insertSelective(order);
        return uuid;
    }

    /***
     * 发布订单后将订单数据添加进redis中
     * @param paramDTO
     * @param tradingType 交易方向/订单类型，买/卖
     */
    private void addRedisOrderList(PublishOrderParamDTO paramDTO, String tradingType) {
        String coinName = paramDTO.getCoinName();
        String unitName = paramDTO.getUnitName();
        Double price = Double.parseDouble(paramDTO.getUnitPrice().toString());

        //redis是否存在对应的key
        Boolean flag = orderCache.hasKey(coinName, unitName, tradingType);

        //redis存在key
        if (flag) {
            //是否成功获取锁标识
            boolean lockFlag = false;
            //加锁，进行添加数据
            while (!lockFlag) {
                //循环，直到获取锁为止
                lockFlag = orderCache.tryFairLock(redissonClient, coinName, unitName, tradingType);
                //拿到锁，处理数据
                if (lockFlag) {
                    //根据单价（sortSet排序分数）获取订单数据
                    Set redisOrder = orderCache.getZSetByScore(coinName, unitName, tradingType, price);
                    //防止为空，无法更新数组中对应的对象！
                    if (redisOrder.size() > 0) {
                        //用迭代器更新数据
                        Iterator iterator = redisOrder.iterator();
                        //处理数据
                        while (iterator.hasNext()) {
                            MarketDTO order = (MarketDTO) iterator.next();
                            //增加总数量和剩余数量
                            order.setTotalNum(order.getTotalNum().add(paramDTO.getTotalNum()));
                            order.setTotalLastNum(order.getTotalLastNum().add(paramDTO.getTotalNum()));
                            //删除旧数据
                            orderCache.removeZSetByScore(coinName, unitName, tradingType, price);
                            //添加数据
                            orderCache.setZSetValue(coinName, unitName, tradingType, order, price);
                        }
                    } else {
                        //为空时，直接添加新的单价对象
                        addRedisOrder(paramDTO, tradingType);
                    }

                    //释放锁
                    orderCache.unFairLock(redissonClient, coinName, unitName, tradingType);
                }
            }
        } else {
            //redis不存在key
            addRedisOrder(paramDTO, tradingType);
        }
    }

    /***
     * 添加一个盘口数据对象到Redis中
     * @param paramDTO
     * @param tradingType
     */
    private void addRedisOrder(PublishOrderParamDTO paramDTO, String tradingType) {
        //构建数据对象
        MarketDTO order = new MarketDTO();
        order.setUnitPrice(paramDTO.getUnitPrice());
        order.setTotalNum(paramDTO.getTotalNum());
        order.setTotalLastNum(paramDTO.getTotalNum());
        order.setCoinName(paramDTO.getCoinName());
        order.setUnitName(paramDTO.getUnitName());
        order.setTradingType(tradingType);
        //添加进缓存中
        orderCache.setZSetValue(paramDTO.getCoinName(), paramDTO.getUnitName(),
                tradingType, order, Double.valueOf(paramDTO.getUnitPrice().toString()));
    }

    /**
     * 撤单后将订单数据更新到redis中
     *
     * @param price
     * @param updateNum
     * @param coinName
     * @param unitName
     * @param tradingType
     */
    private void updateRedisOrderList(Double price, BigDecimal updateNum, String coinName, String unitName, String tradingType) {
        //redis是否存在对应的key
        boolean flag = orderCache.hasKey(coinName, unitName, tradingType);
        //redis存在key
        if (flag) {
            //是否成功获取锁标识
            boolean lockFlag = false;
            //加锁，进行添加数据
            while (!lockFlag) {
                //循环，直到获取锁为止
                lockFlag = orderCache.tryFairLock(redissonClient, coinName, unitName, tradingType);
                //拿到锁，处理数据
                if (lockFlag) {
                    //根据单价（sortSet排序分数）获取订单数据
                    Set redisOrder = orderCache.getZSetByScore(coinName, unitName, tradingType, price);
                    //用迭代器更新数据
                    Iterator iterator = redisOrder.iterator();
                    //处理数据
                    while (iterator.hasNext()) {
                        MarketDTO order = (MarketDTO) iterator.next();
                        //更新剩余数量
                        order.setTotalLastNum(order.getTotalLastNum().subtract(updateNum));
                        //删除旧数据
                        orderCache.removeZSetByScore(coinName, unitName, tradingType, price);
                        //如果剩余数量大于0，添加数据
                        if (order.getTotalLastNum().compareTo(BigDecimal.ZERO) > 0) {
                            //覆盖数据
                            orderCache.setZSetValue(coinName, unitName, tradingType, order, price);
                        }
                    }
                    //释放锁
                    orderCache.unFairLock(redissonClient, coinName, unitName, tradingType);
                }
            }
        }
    }

}
