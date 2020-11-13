package com.blockchain.server.tron.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.common.util.DataCheckUtil;
import com.blockchain.server.tron.common.util.HexUtil;
import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.entity.TronBlockNumber;
import com.blockchain.server.tron.entity.TronToken;
import com.blockchain.server.tron.entity.TronWallet;
import com.blockchain.server.tron.mapper.TronBlockNumberMapper;
import com.blockchain.server.tron.redis.Redis;
import com.blockchain.server.tron.service.*;
import com.blockchain.server.tron.tron.TronUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Harvey Luo
 * @date 2019/6/21 15:06
 */
@Service
public class TronBlockNumberServiceImpl implements TronBlockNumberService {
    @Autowired
    private TronWalletService tronWalletService;
    @Autowired
    private TronWalletTransferService tronWalletTransferService;
    @Autowired
    private TronWalletUtilService tronWalletUtilService;
    @Autowired
    private TronBlockNumberMapper tronBlockNumberMapper;
    @Autowired
    private TronTokenService tronTokenService;
    @Autowired
    private TronUtil tronUtil;
    @Autowired
    private Redis redis;

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);

    private static final Logger LOG = LoggerFactory.getLogger(TronBlockNumberServiceImpl.class);

    /**
     * 获取最新区块
     *
     * @return JSONObject
     */
    @Override
    @Transactional
    public JSONObject getBlockLimitNext() {
        // 查询最新区块信息
        BigInteger newBlockNumber = tronUtil.getNewBlock();
        if (newBlockNumber == null) return null;
        // 从缓存获取当前区块
        BigInteger blockNumber = (BigInteger) redis.getOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_CURRENT);
        if (blockNumber == null) {
            blockNumber = tronBlockNumberMapper.selectCurrentBlockNumber();
            // 数据库没有数据直接存储在缓存以及数据库中，不处理此次操作
            if (blockNumber == null) {
                redis.setOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_CURRENT, newBlockNumber);
                this.insert(newBlockNumber, TronConstant.BlockNumber.TRON_BLOCK_NUMBER_WAIT);
                return null;
            }
        }
        JSONObject blockLimitNext = tronWalletUtilService.getBlockLimitNext(blockNumber, newBlockNumber);
        if (blockLimitNext == null) return null;
        // 往数据库中保存区块信息
        try {
            this.insertBetweenBlockNumber(blockNumber, newBlockNumber, TronConstant.BlockNumber.TRON_BLOCK_NUMBER_WAIT);
        } catch (Exception e) {
            e.printStackTrace();
            redis.setOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_CURRENT, tronBlockNumberMapper.selectCurrentBlockNumber());
            return null;
        }
        // 把最新区块高度存放近缓存中
        redis.setOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_CURRENT, newBlockNumber);
        return blockLimitNext;
    }

    /**
     * 保存数据
     *
     * @param blockNumber
     * @param status
     * @return
     */
    @Override
    public Integer insert(BigInteger blockNumber, Character status) {
        TronBlockNumber tronBlockNumber = new TronBlockNumber();
        tronBlockNumber.setBlockNumber(blockNumber);
        tronBlockNumber.setStatus(status);
        Date date = new Date();
        tronBlockNumber.setCreateTime(date);
        tronBlockNumber.setUpdateTime(date);
        return tronBlockNumberMapper.insert(tronBlockNumber);
    }

    /**
     * 处理爬取区间区块信息
     *
     * @param blockInformation
     * @param listToken
     */
    @Override
    // @Transactional
    public void handleBlockTransactions(JSONObject blockInformation, List<TronToken> listToken, Set<String> addrs) {
        JSONArray blockArray = blockInformation.getJSONArray("block");
        if (blockArray == null) return;
        for (int i = 0; i < blockArray.size(); i++) {
            JSONObject data = blockArray.getJSONObject(i);

            // 使用多线程处理区块信息
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    BigInteger blockNumber = data.getJSONObject("block_header").getJSONObject("raw_data").getBigInteger("number");
                    try {
                        handleMultithreadingDispose(data, listToken, blockNumber, addrs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 保存区块数据
     *
     * @param blockNumber
     * @param newBlockNumber
     * @param status
     * @return
     */
    @Override
    public Integer insertBetweenBlockNumber(BigInteger blockNumber, BigInteger newBlockNumber, Character status) {
        // 起始值为数据库和缓存中存在的最高区块高度
        BigInteger saveNumber = blockNumber.add(BigInteger.ONE);
        while (saveNumber.compareTo(newBlockNumber) <= 0) {
            this.insert(saveNumber, status);
            saveNumber = saveNumber.add(BigInteger.ONE);
        }
        return 1;
    }

    /**
     * 根据区块高度修改区块爬取状态
     *
     * @param blockNumber
     * @param status
     * @return
     */
    @Override
    public Integer updateStatusByBlockNumber(BigInteger blockNumber, Character status) {
        return tronBlockNumberMapper.updateStatusByBlockNumber(blockNumber, status, new Date());
    }

    /**
     * 查询区块高度为等待的区块
     *
     * @param status
     * @return
     */
    @Override
    public List<BigInteger> listBlockNumberOmit(Character status) {
        return tronBlockNumberMapper.listBlockNumberOmit(status, new Date());
    }

    /**
     * 处理遗漏区块信息
     *
     * @param blockNumbers
     * @param listToken
     */
    @Override
    @Transactional
    public void handleBlockTransactionsByOmit(List<BigInteger> blockNumbers, List<TronToken> listToken, Set<String> addrs) {
        // 循环遗漏区块
        blockNumbers.forEach(num -> {
            JSONObject body = null;
            for (int i = 0; i < 10; i++) {
                try {
                    if (body == null) body = tronWalletUtilService.getBlockByNum(num);
                    handleBlockTransaction(body, listToken, addrs);
                    this.updateStatusByBlockNumber(num, TronConstant.BlockNumber.TRON_BLOCK_NUMBER_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOG.info("<====== tron 遗漏区块处理第 " + i + " 次，区块号：" + num + " ======>");
                    // 循环处理5次，5次失败不处理
                    if (i >= 5) {
                        LOG.info("<====== tron 遗漏区块处理次数：" + i + "次，不给处理，区块号：" + num + " ======>");
                        this.updateStatusByBlockNumber(num, TronConstant.BlockNumber.TRON_BLOCK_NUMBER_ERROR);
                        i = 10;
                    }
                }
            }
        });
    }

    /**
     * TRC20代币充值处理方法
     *
     * @param tronTokenDTO
     */
    @Override
    @Transactional
    public void handleBlockTransactionsByTRC20(TronTokenDTO tronTokenDTO, Set<String> addrs) {
        JSONObject trc20Transaction = tronWalletUtilService.getTRC20Transaction(tronTokenDTO.getTokenAddr(), tronTokenDTO.getTimestamp());
        if (trc20Transaction == null) return;
        Boolean success = trc20Transaction.getBoolean("success");
        if (!success) return;
        JSONArray dataArray = trc20Transaction.getJSONArray("data");
        if (dataArray == null || dataArray.size() < 1) return;
        Long timestamp = dataArray.getJSONObject(0).getLong("block_timestamp");
        tronTokenDTO.setTimestamp(timestamp);
        redis.setOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_RTC20_TOKEN, tronTokenDTO);
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject data = dataArray.getJSONObject(i);
            JSONObject result = data.getJSONObject("result");
            String to = result.getString("_to");
            to = "41" + to.substring(2);
            if (!addrs.contains(to)) continue;
            TronWallet tronWallet = tronWalletService.selectWalletByIdAndTokenName(to, tronTokenDTO.getTokenAddr());
            if (tronWallet == null) continue;
            String txID = data.getString("transaction_id");
            if (!tronWalletTransferService.countByHashAndTransferType(txID, TronConstant.TransferType.TRANSFER_IN).equals(0))
                continue;
            String from = result.getString("_from");
            from = "41" + from.substring(2);
            String value = result.getString("value");
            BigDecimal amount = DataCheckUtil.bitToBigDecimal(value);
            // 插入一条充值记录
            tronWalletTransferService.insert(txID, from, to, amount, tronTokenDTO, TronConstant.TransferType.TRANSFER_IN);
            // 修改钱包数额
            tronWalletService.updateWalletBalanceByAddressInRowLock(amount, tronWallet.getAddr(), tronTokenDTO.getTokenAddr());
            LOG.info("<====== 充值成功： 交易hashId值 ==》 " + txID + " + 币种名称 ==》 " + tronTokenDTO.getTokenSymbol() + " + 钱包地址 ==》 " + to + " + 金额 ==》" + amount + " ======>");
        }
    }

    /**
     * 每个线程加事物处理
     *
     * @param data
     * @param listToken
     * @param blockNumber
     */
    @Transactional
    public void handleMultithreadingDispose(JSONObject data, List<TronToken> listToken, BigInteger blockNumber, Set<String> addrs) {
        handleBlockTransaction(data, listToken, addrs);
        updateStatusByBlockNumber(blockNumber, TronConstant.BlockNumber.TRON_BLOCK_NUMBER_SUCCESS);
    }

    /**
     * 处理区块信息方法
     *
     * @param data
     * @param listToken
     */
    @Transactional
    protected void handleBlockTransaction(JSONObject data, List<TronToken> listToken, Set<String> addrs) {
        if (data == null) return;
        JSONArray transactions = data.getJSONArray("transactions");
        if (transactions == null || transactions.size() <= 0) return;
        for (int i = 0; i < transactions.size(); i++) {
            JSONObject transaction = transactions.getJSONObject(i);
            String contractRet = transaction.getJSONArray("ret").getJSONObject(0).getString("contractRet");
            // if (!TronConstant.ResultType.RESULT_SUCCESS.equalsIgnoreCase(contractRet)) continue;
            String txID = transaction.getString("txID");
            JSONObject contract = transaction.getJSONObject("raw_data").getJSONArray("contract").getJSONObject(0);
            String type = contract.getString("type");
            TronWallet tronWallet = null;
            String to = null;
            TronToken tronToken = null;
            String owner = null;
            BigDecimal amountBigDecimal = null;
            // 主币交易信息
            if (type.equalsIgnoreCase(TronConstant.TransferType.TRANSFER_CONTRACT)) {
                JSONObject value = contract.getJSONObject("parameter").getJSONObject("value");
                to = value.getString("to_address");
                // 判断是否充值到平台用户
                tronToken = tronTokenService.listTokenGetTronToken(listToken, TronConstant.TRON_TOKEN_ID);
                tronWallet = walletExist(to, addrs, tronToken, txID);
                if (tronWallet == null) continue;
                String amount = value.getString("amount");
                owner = value.getString("owner_address");
                amountBigDecimal = DataCheckUtil.bitToBigDecimal(amount);
            }
            // TRC10代币交易信息
            if (type.equalsIgnoreCase(TronConstant.TransferType.TRANSFER_ASSET_CONTRACT)) {
                JSONObject value = contract.getJSONObject("parameter").getJSONObject("value");
                // 获取币种地址，判断是否为太平台支持
                String assetName = value.getString("asset_name");
                String tokenAddr = HexUtil.hexStr2Str(assetName);
                tronToken = tronTokenService.listTokenGetTronToken(listToken, tokenAddr);
                if (tronToken == null) continue;
                to = value.getString("to_address");
                // 判断是否充值到平台用户
                tronWallet = walletExist(to, addrs, tronToken, txID);
                if (tronWallet == null) continue;
                owner = value.getString("owner_address");
                String amount = value.getString("amount");
                amountBigDecimal = DataCheckUtil.bitToBigDecimal(amount);
            }
            if (tronWallet == null || tronToken == null) continue;
            // 保存一条充值交易信息
            tronWalletTransferService.insert(txID, owner, to, amountBigDecimal, tronToken, TronConstant.TransferType.TRANSFER_IN);
            // 修改用户钱包数额
            if (tronWalletService.updateWalletBalanceByAddressInRowLock(amountBigDecimal, tronWallet.getAddr(), tronWallet.getTokenAddr()) == 1)
                LOG.info("<====== tron 主链币或TRC10代币充值成功 ==> 钱包地址：" + to + "，币种名称：" + tronToken.getTokenSymbol() + "，交易id：" + txID + "，交易数额：" + amountBigDecimal + " ======>");

        }

    }

    /**
     * 判断平台是否存在该用户已经充值记录是否存在该记录
     *
     * @param addr
     * @param addrs
     * @param tronToken
     * @param txID
     * @return
     */
    private TronWallet walletExist(String addr, Set<String> addrs, TronToken tronToken, String txID) {
        // 判断是否充值到平台用户
        if (!addrs.contains(addr)) return null;
        TronWallet tronWallet = tronWalletService.selectWalletByIdAndTokenName(addr, tronToken.getTokenAddr());
        if (tronWallet == null) return null;
        // 判断该条交易记录是否已存在
        if (!tronWalletTransferService.findByHashAndTypeTheLime(txID, TronConstant.TransferType.TRANSFER_IN).equals(0))
            return null;
        return tronWallet;
    }

}
