package com.blockchain.server.quantized.scheduleTask;

import com.blockchain.server.quantized.common.constant.OrderCancelConstant;
import com.blockchain.server.quantized.common.constant.QuantizedOrderInfoConstant;
import com.blockchain.server.quantized.common.constant.TradingOnConstant;
import com.blockchain.server.quantized.entity.QuantizedOrder;
import com.blockchain.server.quantized.entity.QuantizedOrderInfo;
import com.blockchain.server.quantized.feign.CctFeign;
import com.blockchain.server.quantized.service.QuantizedOrderInfoService;
import com.blockchain.server.quantized.service.QuantizedOrderService;
import com.blockchain.server.quantized.service.SubscriptionService;
import com.blockchain.server.quantized.service.TradingOnService;
import com.codingapi.tx.datasource.relational.txc.rollback.DeleteRollback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SyncOrderTimerTask {


    @Autowired
    private QuantizedOrderInfoService quantizedOrderInfoService;
    @Autowired
    private  SubscriptionService subscriptionService;

    @Autowired
    private QuantizedOrderService quantizedOrderService;

    private static ExecutorService syncThreadExecutor = Executors.newSingleThreadExecutor();

    @Autowired
    private CctFeign cctFeign;

    @Autowired
    private TradingOnService tradingOnService;

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(SyncOrderTimerTask.class);

    @Scheduled(cron = "13 0/5 * * * ?")
    public void selectTxStatus() {
        LOG.info("爬取火币交易记录");
        syncThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    syncOrder();
                } catch (Exception e) {
                    LOG.error("爬取交易记录出现异常:" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void send() {
        syncThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    sendCoin();
                } catch (Exception e) {
                    LOG.error("爬取交易记录出现异常:" + e.getMessage());
                    e.printStackTrace();
                }
            }

        });
    }

    public void sendCoin() {
//        tradingOnService.list(TradingOnConstant.STATE_SUBSCRIBE).forEach(symbol -> {
//            subscriptionService.push(symbol);
//        });
    }
    public void syncOrder() {
        List<QuantizedOrderInfo> quantizedOrderInfoList = quantizedOrderInfoService.listByStatusAndCreateTime(QuantizedOrderInfoConstant.STATUS_N, new Date(System.currentTimeMillis() - QuantizedOrderInfoConstant.DEFAULT_CREATETIME));
        for (QuantizedOrderInfo quantizedOrderInfo : quantizedOrderInfoList) {
            //排他锁
            quantizedOrderInfo = quantizedOrderInfoService.selectByPrimaryKeyForUpdate(quantizedOrderInfo.getId());
            if (quantizedOrderInfo.getStatus().equals(QuantizedOrderInfoConstant.STATUS_Y)) {
                return;
            }
            String cctId = quantizedOrderInfo.getCctId();
            String status = cctFeign.orderStatus(cctId).getData();
            if (status == null || !status.equals(OrderCancelConstant.CANCELING))//只爬取取消中的订单
            {
                return;
            }
            try {
                LOG.info("重新尝试撤单,cctId:"+cctId);
                quantizedOrderService.updateOrder(quantizedOrderInfo.getSymbol(), quantizedOrderInfo.getOrderId());
                //update为成功
                quantizedOrderInfo.setStatus(QuantizedOrderInfoConstant.STATUS_Y);
                quantizedOrderInfoService.update(quantizedOrderInfo);
            } catch (Exception e) {
                //如果一直失败到指定次数，则修改状态
                if (quantizedOrderInfo.getTimes() == QuantizedOrderInfoConstant.MAX_TIMES) {
                    quantizedOrderInfo.setStatus(QuantizedOrderInfoConstant.STATUS_F);
                    quantizedOrderInfo.setDie(QuantizedOrderInfoConstant.IS_DIE);
                } else {
                    quantizedOrderInfo.setTimes(quantizedOrderInfo.getTimes() + 1);
                }
                quantizedOrderInfoService.update(quantizedOrderInfo);
            }
        }
        LOG.info("本次爬取记录完毕");
    }

}
