package com.blockchain.server.ltc.schedule;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.ltc.common.constants.TokenConstans;
import com.blockchain.server.ltc.common.constants.TransferConstans;
import com.blockchain.server.ltc.entity.WalletTransfer;
import com.blockchain.server.ltc.rpc.CoinUtils;
import com.blockchain.server.ltc.service.WalletService;
import com.blockchain.server.ltc.service.WalletTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 钱包业务处理的定时器
 */
@Component
public class WalletTimer {

    // 钱包提现处理
    private static ExecutorService txOutExecutorService = Executors.newSingleThreadExecutor();
    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(WalletTimer.class);

    public static final String CRAWL_TX_OUT_CRON = "0/30 * * * * ?";

    @Autowired
    WalletTransferService walletTransferService;
    @Autowired
    WalletService walletService;
    @Autowired
    CoinUtils coinUtils;

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
        List<WalletTransfer> txs = walletTransferService.selectByTxTypeAndStatus(TransferConstans.TYPE_OUT,
                TransferConstans.STATUS_ALREADY_OUT, 0, 99);
        for (WalletTransfer tx : txs) {
            try {
                if (TokenConstans.TOKEN_SYMBOL.equalsIgnoreCase(tx.getTokenSymbol())) {
                    JSONObject obj = coinUtils.getTransaction(tx.getHash());
                    int confirmations = obj.getIntValue("confirmations");
                    if (confirmations >= 1) {
                        walletService.updateTxOutSuccess(tx);
                    } else if (confirmations == -1) {
                        walletService.updateTxOutError(tx);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
