package com.blockchain.server.tron.scheduleTask;

import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.entity.TronWalletTransfer;
import com.blockchain.server.tron.service.TronWalletService;
import com.blockchain.server.tron.service.TronWalletTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 钱包业务处理的定时器
 */
@Component
public class WallertTimerTask {

    // 钱包提现处理
    private static ExecutorService txOutExecutorService = Executors.newSingleThreadExecutor();
    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(WallertTimerTask.class);

    public static final String CRAWL_TX_OUT = "0/10 * * * * ?";

    @Autowired
    TronWalletTransferService walletTransferService;
    @Autowired
    TronWalletService walletService;

    public void crawlTxOut() {
        txOutExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                crawlTxOutDispose(); // 爬取提现记录
            }
        });
    }

    void crawlTxOutDispose() {
        // 查询提现的未处理的数据
        List<TronWalletTransfer> txs = walletTransferService.selectByTxTypeAndStatus(TronConstant.TransferType.TRANSFER_OUT, TronConstant.TransferStatus.YI_CHU_BI, 0, 99);
        for (TronWalletTransfer tx : txs) {
            if (new Date().getTime() - tx.getCreateTime().getTime() < 30) continue;
            try {
                Boolean status = walletService.getTransaction(tx.getHash());
                if (status == null) continue;
                // 修改失败状态
                if (!status) walletService.updateTxOutError(tx);
                    // 修改余额改变成功状态
                else walletService.updateTxOutSuccess(tx);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }


}
