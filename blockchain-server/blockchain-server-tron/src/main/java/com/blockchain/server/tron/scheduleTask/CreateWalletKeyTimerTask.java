package com.blockchain.server.tron.scheduleTask;

import com.blockchain.server.tron.service.TronWalletKeyInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CreateWalletKeyTimerTask {

    @Autowired
    private TronWalletKeyInitService tronWalletKeyInitService;

    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private static ExecutorService synchronizationKeyInit = Executors.newSingleThreadExecutor();

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(CreateWalletKeyTimerTask.class);

    public static final String SELECT_TX_STATUS_CRON = "0/1 * * * * ?";
    public static final String SYNCHRONIZATION_KEY_INIT_CRON = "0 5 0/1 * * ?";

    /**
     * 查tron区块信息
     */
    public void selectTxStatus() {
         LOG.info("<====== tron 创建地址 ======>");
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dispose();
            }
        });
    }

    /**
     * 爬取区块信息
     */
    private void dispose() {
        // 创建初始化地址
        try {
            tronWalletKeyInitService.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("<====== tron 创建地址异常 ======>");
        }
    }

    /**
     * 查询 redis和db数据不同步
     */
    public void synchronizationKeyInit() {
        // LOG.info("<====== tron 数据同步 ======>");
        synchronizationKeyInit.execute(new Runnable() {
            @Override
            public void run() {
                synchronizationKeyInitDispose();
            }
        });
    }

    /**
     * 同步数据
     */
    private void synchronizationKeyInitDispose() {
        // 创建初始化地址
        try {
            tronWalletKeyInitService.synchronizationKeyInitDispose();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("<====== tron 数据同步异常 ======>");
        }
    }


}
