package com.blockchain.server.eth.scheduleTask;

import com.blockchain.server.eth.common.constants.EthWalletConstants;
import com.blockchain.server.eth.common.util.RedisBlockUtil;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWalletTransfer;
import com.blockchain.server.eth.service.*;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 钱包业务处理的定时器
 */
@Component
public class EthWallertTimerTask {

    // 钱包充值处理
    private static ExecutorService txInExecutorService = Executors.newSingleThreadExecutor();
    // 钱包提现处理
    private static ExecutorService txOutExecutorService = Executors.newSingleThreadExecutor();
    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(EthWallertTimerTask.class);

    public static final String CRAWL_TX_IN_CRON = "0/10 * * * * ?";
    public static final String CRAWL_TX_OUT_CRON = "0/10 * * * * ?";

    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    IEthWalletTransferService ethWalletTransferService;
    @Autowired
    IWalletWeb3j walletWeb3j;


    public void crawlTxIn() {
        txInExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                crawlTxInDispose(); // 爬取充值记录
            }
        });
    }

    public void crawlTxOut() {
        txOutExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                crawlTxOutDispose(); // 爬取提现记录
            }
        });
    }

    void crawlTxInDispose() {
        LOG.info("处理充值记录:");
        // 查询充值的未处理的数据
        List<EthWalletTransfer> txs =
                ethWalletTransferService.selectByTxTypeAndStatus(EthWalletConstants.TransferType.IN,
                        EthWalletConstants.StatusType.IN_LOAD, 0, 99);
        // 查询记录打包状态
        for (EthWalletTransfer tx : txs) {
            try {
                tx = ethWalletTransferService.updateGas(tx); // 修改记录的旷工费用
                if (tx.getStatus() == EthWalletConstants.StatusType.IN_SUCCESS) {
                    // 修改余额改变成功状态
                    ethWalletService.updateBlanceTxIn(tx);
                } else if (tx.getStatus() == EthWalletConstants.StatusType.IN_ERROR) {
                    // 修改失败状态
                    ethWalletTransferService.updateStatus(tx);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("查询充值信息发生异常",e);
                continue;
            }
        }

    }

    void crawlTxOutDispose() {
        // 查询提现的未处理的数据
        List<EthWalletTransfer> txs =
                ethWalletTransferService.selectByTxTypeAndStatus(EthWalletConstants.TransferType.OUT,
                        EthWalletConstants.StatusType.OUT_LOAD4, 0, 99);
        // 查询记录打包状态
        for (EthWalletTransfer tx : txs) {
            try {
                tx = ethWalletTransferService.updateGas(tx); // 修改记录的旷工费用
                if (tx.getStatus() == EthWalletConstants.StatusType.IN_ERROR) {
                    // 修改失败状态
                    ethWalletService.updateTxOutError(tx);
                } else if (tx.getStatus() == EthWalletConstants.StatusType.IN_SUCCESS) {
                    // 修改余额改变成功状态
                    ethWalletService.updateTxOutSuccess(tx);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }


}
