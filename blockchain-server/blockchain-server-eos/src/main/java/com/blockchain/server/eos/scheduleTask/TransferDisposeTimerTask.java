package com.blockchain.server.eos.scheduleTask;

import com.blockchain.server.eos.service.EosTokenService;
import com.blockchain.server.eos.service.EosWalletInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class TransferDisposeTimerTask {
    @Autowired
    private EosTokenService tokenService;
    @Autowired
    private EosWalletInService eosWalletInService;

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(TransferDisposeTimerTask.class);

    //5秒钟执行一次 */5 * * * * ?   5分钟执行一次 0 */5 * * * ?
    public static final String SELECT_TX_STATUS_CRON = "0/10 * * * * ?";

    private volatile boolean preOver = true;

    /**
     * 查eos区块信息
     */
    public void selectTxStatus() {
        LOG.info("************************eos交易记录定时器************************");
        if (preOver) {
            synchronized (TransferDisposeTimerTask.this) {
                if (preOver) {
                    LOG.info("************************eos交易记录爬取任务************************");
                    preOver = false;
                    try {
                        dispose();
                    } finally {
                        preOver = true;
                    }
                }
            }
        }
    }

    public void dispose() {
        // Token查询所有代币
        HashSet<String> listTokenName = tokenService.listTokenNameAll();
        try {
            // 充值处理方法
            eosWalletInService.handleAccountBlockTransactions(listTokenName);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("************************ 充值处理异常 ************************");
        }

    }

}
