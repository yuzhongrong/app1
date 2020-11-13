package com.blockchain.server.eos.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.common.util.RedisUtil;
import com.blockchain.server.eos.dto.WalletInDTO;
import com.blockchain.server.eos.entity.BlockNumber;
import com.blockchain.server.eos.entity.Wallet;
import com.blockchain.server.eos.mapper.BlockNumberMapper;
import com.blockchain.server.eos.mapper.WalletInMapper;
import com.blockchain.server.eos.scheduleTask.TransferDisposeTimerTask;
import com.blockchain.server.eos.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/16 17:49
 * @user WIN10
 */
@Service
public class EosBlockNumberServiceImpl implements EosBlockNumberService {

    @Autowired
    private BlockNumberMapper blockNumberMapper;
    @Autowired
    private EosWalletUtilService eosWalletUtilService;
    @Autowired
    private EosWalletService eosWalletService;
    @Autowired
    private EosWalletTransferService eosWalletTransferService;
    @Autowired
    private EosWalletInService eosWalletInService;
    @Autowired
    private RedisTemplate redisTemplate;

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(TransferDisposeTimerTask.class);

    /**
     * 查询最大区块
     *
     * @return
     */
    @Override
    public BigInteger selectCurrentBlockNum() {
        BigInteger current = RedisUtil.getCurrentBlock(redisTemplate);
        if (current == null) current = blockNumberMapper.selectMaxBlockNum();
        return current;
    }

    /**
     * 添加区块交易信息
     *
     * @param nowBlock      区块号
     * @param listTokenAddr 所有系统代币地址
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void handleBlockTransactions(BigInteger nowBlock, HashSet<String> listTokenAddr) {
        ExceptionPreconditionUtils.notEmpty(nowBlock);
        ExceptionPreconditionUtils.notEmpty(listTokenAddr);
        if (EosConstant.BlockNumber.EOS_BLOCK_NUMBER_WAIT.equals(blockNumberMapper.selectBlockNumStatusByBlockNum(nowBlock))) {
            // 查询eos此区块所有交易信息
            JSONObject resultData = eosWalletUtilService.listBlockTransaction(nowBlock);
            if (resultData != null) {
                JSONArray transactions = resultData.getJSONArray("transactions");
                if (transactions.size() > 0) {
                    for (int i = 0; i < transactions.size(); i++) {
                        // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        JSONObject job = transactions.getJSONObject(i);

                        JSONObject trx = null;
                        try {
                            trx = job.getJSONObject("trx");
                        } catch (Exception e) {
                            continue;
                        }
                        String hash = trx.getObject("id", String.class);
                        JSONObject transaction = trx.getJSONObject("transaction");
                        if (transaction.size() > 0) {
                            JSONArray actions = transaction.getJSONArray("actions");
                            JSONObject action = actions.getJSONObject(0);
                            // System.out.println("action: " + action);
                            JSONObject data = null;
                            try {
                                data = action.getJSONObject("data");
                            } catch (Exception e) {
                                continue;
                            }

                            if (null != data && !"".equals(data)) {
                                // 解析收款地址是否平台收款地址
                                String to = data.getObject("to", String.class);
                                if(to == null) continue;
                                List<WalletInDTO> walletInDTOS = eosWalletInService.listWalletInByAccountName(to);
                                if (walletInDTOS != null && walletInDTOS.size() > 0) {
                                    // 解析区块信息，获取充值记录
                                    String from = data.getObject("from", String.class);
                                    String tokenName = action.getObject("account", String.class);
                                    // String hexData = action.getObject("hex_data", String.class);
                                    String quantity = data.getObject("quantity", String.class);
                                    String[] quantitys = null;
                                    if (null != quantity && quantity.length() > 0) quantitys = quantity.split(" ");
                                    // 备注格式为备注 钱包地址
                                    String memo = data.getObject("memo", String.class).trim();

                                    for (WalletInDTO walletInDTO : walletInDTOS) {
                                        // 判断代币地址
                                        if (tokenName.equals(walletInDTO.getTokenName())) {
                                            Wallet wallet = eosWalletService.selectWalletById(memo);
                                            if (null != wallet) {
                                                // 判断token_name 和 token_symbol
                                                if (wallet.getTokenName().equals(tokenName) && wallet.getTokenSymbol().equals(quantitys[1])) {
                                                    // 添加充值记录
                                                    eosWalletTransferService.handleWalletAndWalletTransfer(hash,
                                                            wallet.getId(),
                                                            null,
                                                            EosConstant.TransferType.TRANSFER_IN,
                                                            new BigDecimal(quantitys[0]),
                                                            tokenName,
                                                            quantitys[1],
                                                            memo,
                                                            nowBlock);
                                                    LOG.info("************************充值成功：" + hash + " + " + wallet.getId() + " + " + quantity + " + " + memo + "************************");
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int insertBlockNumber(BlockNumber blockNumber) {
        // System.out.println("添加区块高度");
        return blockNumberMapper.insertSelectiveIgnore(blockNumber);
    }

    /**
     * 插入区块处理状态
     *
     * @param nowBlock
     * @param status
     * @return
     */
    @Override
    @Transactional
    public int insertBlockNumberByStatus(BigInteger nowBlock, Character status) {
        BlockNumber blockNumber = new BlockNumber();
        blockNumber.setBlockNumber(nowBlock);
        blockNumber.setStatus(status);
        blockNumber.setCreateTime(new Date());
        return this.insertBlockNumber(blockNumber);
    }

    /**
     * 根据区块状态获取区块
     *
     * @param status
     * @return
     */
    @Override
    public List<BigInteger> listBlockNumberByStatus(Character status) {
        return blockNumberMapper.listBlockNumberByStatus(status);
    }

    /**
     * 更新处理区块状态
     *
     * @param blockNum
     * @param status
     * @return
     */
    @Override
    @Transactional
    public int updateBlockNumberByStatus(BigInteger blockNum, Character status) {
        return blockNumberMapper.updateBlockNumberByStatus(blockNum, status);
    }
}
