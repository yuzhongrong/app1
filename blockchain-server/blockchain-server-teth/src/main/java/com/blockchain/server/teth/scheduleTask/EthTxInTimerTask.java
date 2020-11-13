package com.blockchain.server.teth.scheduleTask;

import com.blockchain.server.teth.common.constants.EthWalletConstants;
import com.blockchain.server.teth.common.util.RedisBlockUtil;
import com.blockchain.server.teth.entity.EthBlockNumber;
import com.blockchain.server.teth.entity.EthToken;
import com.blockchain.server.teth.service.IEthBlockNumberService;
import com.blockchain.server.teth.service.IEthTokenService;
import com.blockchain.server.teth.service.IEthWalletKeyService;
import com.blockchain.server.teth.web3j.IWalletWeb3j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 充值记录爬取的定时器
 */
@Component
public class EthTxInTimerTask {


    static ExecutorService singleThreadExecutorNormal = Executors.newSingleThreadExecutor();

    static ExecutorService singleThreadExecutorOmit = Executors.newSingleThreadExecutor();
    // 日志
    static final Logger LOG = LoggerFactory.getLogger(EthTxInTimerTask.class);
    // 区块与当前爬取区块的偏差量
    static final BigInteger BIAS_CURRENT = BigInteger.valueOf(10);
    // 规定遗漏爬取区块的时间差
    static final long TIME_DIFFERENCE = 60;
    //定时器Cron表达式
    public static final String CRAWL_TX_IN_CRON = "0/10 * * * * ?";
    public static final String CRAWL_OMIT_TX_IN_CRON = "0/30 * * * * ?";

    @Autowired
    IEthBlockNumberService ethBlockNumberService;
    @Autowired
    IWalletWeb3j walletWeb3j;
    @Autowired
    IEthWalletKeyService ethWalletKeyService;
    @Autowired
    IEthTokenService ethTokenService;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 充值记录爬取
     */
    public void crawlTxIn() {
        singleThreadExecutorNormal.execute(new Runnable() {
            @Override
            public void run() {
                crawlTxInDispose(); // 爬取充值记录
            }
        });
    }

    /**
     * 查询以太坊遗漏的以太坊数据
     */
    public void crawlOmitTxIn() {
        singleThreadExecutorOmit.execute(new Runnable() {
            @Override
            public void run() {
                crawlOmitTxInDispose(); // 爬取遗漏数据的业务逻辑
            }
        });
    }


    /**
     * 充值记录爬取处理方法
     */
    @Transactional
    public void crawlTxInDispose() {
        LOG.info("爬取充值记录");
        BigInteger newestBlock = ethBlockNumberService.selectNewest();  // 获取最新区块
        BigInteger currentBlock = ethBlockNumberService.selectCurrent();  //当前区块
        LOG.info("当前块高度："+newestBlock+"："+currentBlock);
        // 缓冲区块差为50个,不足退出
        if (newestBlock.compareTo(currentBlock.add(BIAS_CURRENT)) < 0) return;
        Map<String, EthToken> coinAddrs = ethTokenService.selectMaps(); // 获取所有币种地址
        Set<String> adds = ethWalletKeyService.selectAddrs(); // 获取所有用户钱包地址
        // 遍历爬取区块
        for (int i = 0; i < 10; i++) {
            currentBlock = currentBlock.add(BigInteger.ONE);
            try {
                // 插入当前区块号
                ethBlockNumberService.insert(currentBlock, EthWalletConstants.StatusType.BLOCK_WAIT);
                // 插入区块号的充值数据，修改区块状态
                ethBlockNumberService.handleBlockTxIn(currentBlock, coinAddrs, adds);
                RedisBlockUtil.setBlockCurrent(currentBlock, redisTemplate);
            } catch (Exception e) {
                e.printStackTrace();
                //查询当前数据库最新的block
                EthBlockNumber ethBlockNumber = ethBlockNumberService.selectByBlockNumber(currentBlock);
                if (ethBlockNumber != null && ethBlockNumber.getStatus() == EthWalletConstants.StatusType.BLOCK_WAIT) {
                    ethBlockNumberService.deleteBlock(currentBlock);
                }
            }
        }
    }


    /**
     * 充值记录遗漏爬取的处理方法
     */
    void crawlOmitTxInDispose() {
        Map<String, EthToken> coinAddrs = ethTokenService.selectMaps(); // 获取所有币种地址
        Set<String> adds = ethWalletKeyService.selectAddrs(); // 获取所有用户钱包地址

        BigInteger currentBlock = ethBlockNumberService.selectCurrent();  // 当前区块
        // 获取当前区块的创建时间
        long currentBlockTime = ethBlockNumberService.selectByBlockNumber(currentBlock).getCreateTime().getTime();
        List<EthBlockNumber> blockWaits = ethBlockNumberService.selectWaitBlocks(); // 获取等待区块列表
        for (EthBlockNumber row : blockWaits) {
            long rowTime = row.getCreateTime().getTime(); // 获取等待区块的时间
            long diffTime = (currentBlockTime - rowTime) / 1000; // 获取时间差
            if (diffTime <= TIME_DIFFERENCE) return; // 区块时间未超过一分钟，跳出处理
            int count = RedisBlockUtil.getBlockOptMapCount(row.getBlockNumber(), redisTemplate); // 获取爬取次数
            if (count >= 5) {   // 爬取次数超过五次跳出
                RedisBlockUtil.delBlockOptMapCount(row.getBlockNumber(), redisTemplate);
                ethBlockNumberService.updateByBlockNumber(row.getBlockNumber(),
                        EthWalletConstants.StatusType.BLOCK_ERROR);
                return;
            }
            // 爬取次数 + 1
            RedisBlockUtil.incrementBlockOptMapCount(row.getBlockNumber(), redisTemplate);
            try {
                // 插入区块号的充值数据，修改区块状态
                ethBlockNumberService.handleBlockTxIn(row.getBlockNumber(), coinAddrs, adds);
                RedisBlockUtil.delBlockOptMapCount(row.getBlockNumber(), redisTemplate);
            } catch (Exception e) {
            }
        }

    }


}
