package com.blockchain.server.tron.scheduleTask;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.entity.TronToken;
import com.blockchain.server.tron.redis.Redis;
import com.blockchain.server.tron.service.TronBlockNumberService;
import com.blockchain.server.tron.service.TronTokenService;
import com.blockchain.server.tron.service.TronWalletKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TransferDisposeTimerTask {
    @Autowired
    private TronTokenService tokenService;
    @Autowired
    private TronBlockNumberService tronBlockNumberService;
    @Autowired
    private TronWalletKeyService tronWalletKeyService;
    @Autowired
    private Redis redis;

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(TransferDisposeTimerTask.class);
    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private static ExecutorService singleThreadExecutorOmit = Executors.newSingleThreadExecutor();

    private static ExecutorService singleThreadExecutorTRC20 = Executors.newSingleThreadExecutor();

    public static final String SELECT_TX_STATUS = "0/10 * * * * ?";
    public static final String SELECT_TX_STATUS_OMIT = "0/30 * * * * ?";
    public static final String SELECT_TX_STATUS_TRC20 = "0/10 * * * * ?";

    /**
     * 查tron区块信息
     */
    public void selectTxStatus() {
        LOG.info("<====== tron 爬取区块交易信息的定时任务 ======>");
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dispose();
            }
        });
    }

    /**
     * 查tron遗漏区块信息
     */
    public void selectTxStatusOmit() {
        LOG.info("<====== tron 爬取遗漏区块交易信息的定时任务 ======>");
        singleThreadExecutorOmit.execute(new Runnable() {
            @Override
            public void run() {
                disposeOmit();
            }
        });
    }

    /**
     * 查tron TRC20代币信息
     */
    public void selectTxStatusTRC20() {
        LOG.info("<====== tron 获取TRC20代币交易信息的定时任务 ======>");
        singleThreadExecutorTRC20.execute(new Runnable() {
            @Override
            public void run() {
                disposeTRC20();
            }
        });
    }

    /**
     * 爬取区块信息
     */
    private void dispose() {
        // 获取区块信息
        JSONObject blockInformation = tronBlockNumberService.getBlockLimitNext();
        if (blockInformation == null) return;
        List<TronToken> listToken = tokenService.listTronToken();
        Set<String> addrs = tronWalletKeyService.getWalletHexAddrs();
        try {
            // 充值处理方法
            tronBlockNumberService.handleBlockTransactions(blockInformation, listToken, addrs);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("<====== 区块交易信息处理异常 ======>");
        }

    }

    /**
     * 爬取遗漏区块
     */
    private void disposeOmit() {
        // 获取区块信息
        List<BigInteger> blockNumbers = tronBlockNumberService.listBlockNumberOmit(TronConstant.BlockNumber.TRON_BLOCK_NUMBER_WAIT);
        if (blockNumbers == null || blockNumbers.size() <= 0) return;
        List<TronToken> listToken = tokenService.listTronToken();
        Set<String> addrs = tronWalletKeyService.getWalletHexAddrs();
        try {
            // 充值处理方法
            tronBlockNumberService.handleBlockTransactionsByOmit(blockNumbers, listToken, addrs);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("<====== 区块交易信息处理异常 ======>");
        }
    }


    /**
     * 爬取TRC20代币信息
     */
    private void disposeTRC20() {
        // 获取TRC20合约地址
        TronTokenDTO tronTokenDTO = tokenService.selectTRC20Token(TronConstant.TronToken.TRON_TOKEN_SYMBOL_ACE);
        Set<String> addrs = tronWalletKeyService.getWalletHexAddrs();
        try {
            // 处理TRC20代币充值
            tronBlockNumberService.handleBlockTransactionsByTRC20(tronTokenDTO, addrs);
            redis.setOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_RTC20_TOKEN, tronTokenDTO);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("<====== TRC20交易信息处理异常 ======>");
        }

    }


}
