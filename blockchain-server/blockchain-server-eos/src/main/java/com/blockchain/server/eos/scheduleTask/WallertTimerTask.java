package com.blockchain.server.eos.scheduleTask;

import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.entity.WalletTransfer;
import com.blockchain.server.eos.service.EosWalletService;
import com.blockchain.server.eos.service.EosWalletTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 钱包业务处理的定时器
 */
@Component
public class WallertTimerTask {
    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(WallertTimerTask.class);

    public static final String CRAWL_TX_OUT_CRON = "0/5 * * * * ?";

    @Autowired
    EosWalletTransferService walletTransferService;
    @Autowired
    EosWalletService walletService;

    private volatile boolean preOver = true;

    public void crawlTxOut() {
        if (preOver) {
            synchronized (WallertTimerTask.class) {
                if (preOver) {
                    preOver = false;
                    try {
                        crawlTxOutDispose(); // 爬取提现记录
                    } finally {
                        preOver = true;
                    }
                }
            }
        }
    }

    void crawlTxOutDispose() {
        // 查询提现的未处理的数据
        List<WalletTransfer> txs = walletTransferService.selectByTxTypeAndStatus(EosConstant.TransferType.TRANSFER_OUT, EosConstant.TransferStatus.YI_CHU_BI, 0, 99);
        for (WalletTransfer tx : txs) {
            try {
                Boolean status = walletService.getTransaction(tx.getBlockNumber(), tx.getHash());
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
