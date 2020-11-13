package com.blockchain.server.tron.scheduleTask;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.entity.TronInform;
import com.blockchain.server.tron.service.TronInformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PayTimerTask {

    @Autowired
    private TronInformService informService;

    @Autowired
    RestTemplate restTemplate;

    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(PayTimerTask.class);

    public static final String SELECT_TX_STATUS = "0/10 * * * * ?";

    /**
     * 异步通知支付成功请求
     */
    public void selectTxStatus() {
        LOG.info("====== 异步通知支付成功请求 ======");
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dispose();
            }
        });
    }

    public void dispose() {
        List<TronInform> list = informService.selectByInformTypePendingAll(TronConstant.InformConstant.TYPE_PAY, TronConstant.InformConstant.PAY_TIME);
        for (TronInform row : list) {
            try {
                JSONObject postData = JsonUtils.jsonToPojo(row.getParamsJson(), JSONObject.class);
                JSONObject json = restTemplate.postForEntity(row.getUrl(), postData, JSONObject.class).getBody();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                informService.updateTimeInRowLock(row.getId());
            }
        }
    }


}
