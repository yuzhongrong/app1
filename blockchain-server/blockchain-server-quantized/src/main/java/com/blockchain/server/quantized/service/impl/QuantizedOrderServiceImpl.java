package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.dto.QuantizedOrderDto;
import com.blockchain.server.quantized.entity.QuantizedAccount;
import com.blockchain.server.quantized.entity.QuantizedOrder;
import com.blockchain.server.quantized.feign.CctFeign;
import com.blockchain.server.quantized.mapper.QuantizedOrderMapper;
import com.blockchain.server.quantized.service.AccountService;
import com.blockchain.server.quantized.service.QuantizedOrderInfoService;
import com.blockchain.server.quantized.service.QuantizedOrderService;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author: Liusd
 * @create: 2019-04-19 16:51
 **/
@Service
public class QuantizedOrderServiceImpl implements QuantizedOrderService, ITxTransaction {


    private static final Logger LOG = LoggerFactory.getLogger(QuantizedOrderServiceImpl.class);


    @Autowired
    private QuantizedOrderMapper quantizedOrderMapper;
    @Autowired
    private QuantizedOrderInfoService quantizedOrderInfoService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private CctFeign cctFeign;

    /**
     * 账号信息
     */
    private QuantizedAccount getAccount() {
        return accountService.findOne();
    }

    @Override
    @Transactional
    public int insert(QuantizedOrder quantizedOrder) {
        LOG.info("下单成功、新增本地订单记录");
        return quantizedOrderMapper.insert(quantizedOrder);
    }

    @Override
    public QuantizedOrder selectByCctId(String cctId) {
        QuantizedOrder quantizedOrder = new QuantizedOrder();
        quantizedOrder.setCctId(cctId);
        QuantizedOrder order = quantizedOrderMapper.selectOne(quantizedOrder);
        return order;
    }

    @Override
    public List<QuantizedOrderDto> listByNoFinished() {
        return quantizedOrderMapper.listByNoFinished();
    }

    @Override
    public QuantizedOrder selectByPrimaryKey(long orderId) {
        return quantizedOrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void updateOrder(String symbol, long orderId) {
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        //火币查询订单信息
        Order order = syncRequestClient.getOrder(symbol,orderId);
        LOG.info("+++++++++++++++++++++++++++"+order.toString());
        //排他锁
        QuantizedOrder quantizedOrder = quantizedOrderMapper.selectByPrimaryKeyForUpdate(orderId);
        //如果本地不是完结状态
        if (quantizedOrder.getFinishedAt() == null || quantizedOrder.getFinishedAt() ==0) {
            //防止出现quantizedOrder.getFieldAmount()为空bigdecimal计算出错
            String field = quantizedOrder.getFieldAmount();
            if (field == null || field.equals("")) {
                field = "0";
            }
            String localCashAmount = quantizedOrder.getFieldCashAmount();
            if (localCashAmount == null || localCashAmount.equals("")) {
                localCashAmount = "0";
            }
            //撤单与部分撤单
            if (OrderState.CANCELED.equals(order.getState()) || OrderState.PARTIALCANCELED.equals(order.getState())) {
                LOG.info("撤单/部分撤单调用 cctFeign ，cctId为 {}",quantizedOrder.getCctId());
                cctFeign.handleQuantizedCancel(quantizedOrder.getCctId());
            }else if (order.getFilledAmount().compareTo(new BigDecimal(field)) < 1 || order.getFilledCashAmount().compareTo(new BigDecimal(localCashAmount)) < 1){
                //订单推送已完成数量或者已完成总金额小于等于本地金额，不予处理。
                return;
            }else if (OrderState.PARTIALFILLED.equals(order.getState())) {
                //部分成交
                LOG.info("部分成交调用 cctFeign ，cctId为 {},成交数量为 {}",quantizedOrder.getCctId(),order.getFilledAmount().subtract(new BigDecimal(field)).toString());
                cctFeign.handlePartialQuantizedTransation(quantizedOrder.getCctId(), order.getFilledAmount().subtract(new BigDecimal(field)),order.getFilledCashAmount().subtract(new BigDecimal(localCashAmount)) );
            }else if(OrderState.FILLED.equals(order.getState())){
                //完全成交
                LOG.info("完全成交调用 cctFeign ，cctId为 {}",quantizedOrder.getCctId(),order.getFilledAmount().subtract(new BigDecimal(field)).toString());
                cctFeign.handleFillQuantizedTransation(quantizedOrder.getCctId(), order.getFilledAmount().subtract(new BigDecimal(field)),order.getFilledCashAmount().subtract(new BigDecimal(localCashAmount)) );
            }else {
                LOG.info("出现未知状态，状态为:{}",order.getState().toString());
            }
            quantizedOrder.setAmount(order.getAmount().toString());
            quantizedOrder.setCreatedAt(order.getCreatedTimestamp());
            quantizedOrder.setCanceledAt(order.getCanceledTimestamp());
            quantizedOrder.setFieldAmount(order.getFilledAmount().toString());
            quantizedOrder.setFieldCashAmount(order.getFilledCashAmount().toString());
            quantizedOrder.setFieldFees(order.getFilledFees().toString());
            quantizedOrder.setFinishedAt(order.getFinishedTimestamp());
            quantizedOrder.setPrice(order.getPrice().toString());
            quantizedOrder.setSource(order.getSource().toString());
            quantizedOrder.setState(order.getState().toString());
            quantizedOrder.setSymbol(order.getSymbol());
            quantizedOrder.setType(order.getType().toString());
            //修改本地
            quantizedOrderMapper.updateByPrimaryKey(quantizedOrder);
        }
    }

}
