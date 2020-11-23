package com.blockchain.server.cmc.schedule;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.cmc.common.constants.BtcConstans;
import com.blockchain.server.cmc.common.constants.BtcTransferConstans;
import com.blockchain.server.cmc.entity.BtcWalletTransfer;
import com.blockchain.server.cmc.rpc.BtcUtils;
import com.blockchain.server.cmc.rpc.UsdtUtils;
import com.blockchain.server.cmc.service.BtcWalletService;
import com.blockchain.server.cmc.service.BtcWalletTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public static final String CRAWL_TX_OUT_CRON = "0/30 * * * * ?";

    @Autowired
    BtcWalletTransferService walletTransferService;
    @Autowired
    BtcWalletService btcWalletService;
    @Autowired
    BtcUtils btcUtils;
    @Autowired
    UsdtUtils usdtUtils;

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
        List<BtcWalletTransfer> txs = walletTransferService.selectByTxTypeAndStatus(BtcTransferConstans.TYPE_OUT,
                BtcTransferConstans.STATUS_ALREADY_OUT, 0, 99);
        for (BtcWalletTransfer tx : txs) {
            try {
                if (BtcConstans.BTC_SYMBOL.equalsIgnoreCase(tx.getTokenSymbol())) {
                    JSONObject obj = btcUtils.getTransaction(tx.getHash());
                    int confirmations = obj.getIntValue("confirmations");
                    if (confirmations >= 1) {
                        btcWalletService.updateTxOutSuccess(tx);
                    } else if (confirmations == -1) {
                        btcWalletService.updateTxOutError(tx);
                    }
                } else {
                    JSONObject obj = usdtUtils.getTransaction(tx.getHash());
                    int confirmations = obj.getIntValue("confirmations");
                    Boolean valid = obj.getBoolean("valid");
                    if (valid != null) {
                        if (!valid) {
                            btcWalletService.updateTxOutError(tx);
                        } else if (valid && confirmations >= 1) {
                            btcWalletService.updateTxOutSuccess(tx);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
