package com.blockchain.server.teth.service.impl;

import com.blockchain.server.teth.common.constants.EthWalletConstants;
import com.blockchain.server.teth.common.util.DataCheckUtil;
import com.blockchain.server.teth.common.util.RedisBlockUtil;
import com.blockchain.server.teth.dto.Web3jTransferDTO;
import com.blockchain.server.teth.entity.EthBlockNumber;
import com.blockchain.server.teth.entity.EthToken;
import com.blockchain.server.teth.mapper.EthBlockNumberMapper;
import com.blockchain.server.teth.web3j.IWalletWeb3j;
import com.blockchain.server.teth.service.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 以太坊区块高度记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class EthBlockNumberServiceImpl implements IEthBlockNumberService {

    static final String GAS_TOKENADDR = "teth"; // 默认手续费币种
    static final String REGEX = "^0xa9059cbb.+";   // 转账正则判断
    static final Pattern PATTERN = Pattern.compile("^(.+)(.{64})(.{64}$)");     // input 数据解析
    static final BigInteger BIAS_CURRENT = BigInteger.valueOf(20);
    private static final Logger LOG = LoggerFactory.getLogger(EthBlockNumberServiceImpl.class);
    @Autowired
    EthBlockNumberMapper ethBlockNumberMapper;
    @Autowired
    IEthWalletKeyService ethWalletKeyService;
    @Autowired
    IEthTokenService ethTokenService;
    @Autowired
    IEthWalletTransferService ethWalletTransferService;
    @Autowired
    IEthGasWalletService ethGasWalletService;

    @Autowired
    IWalletWeb3j walletWeb3j;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void insert(BigInteger blockNumber, char status) {
        Date date = new Date();
        EthBlockNumber ethBlockNumber = new EthBlockNumber();
        ethBlockNumber.setBlockNumber(blockNumber);
        ethBlockNumber.setCreateTime(date);
        ethBlockNumber.setUpdateTime(date);
        ethBlockNumber.setStatus(status);
        ethBlockNumberMapper.insertSelective(ethBlockNumber);
    }

    @Override
    @Transactional
    public void updateByBlockNumber(BigInteger blockNumber, char status) {
        EthBlockNumber ethBlockNumber = ethBlockNumberMapper.selectByPrimaryKey(blockNumber);
        if (ethBlockNumber == null) {
            insert(blockNumber, status);
        } else {
            Date date = new Date();
            ethBlockNumber.setUpdateTime(date);
            ethBlockNumber.setStatus(status);
            ethBlockNumberMapper.updateByPrimaryKeySelective(ethBlockNumber);
        }
    }

    @Override
    public boolean isBlock(BigInteger bigInteger) {
        EthBlockNumber ethBlockNumber = ethBlockNumberMapper.selectByPrimaryKey(bigInteger);
        return ethBlockNumber != null;
    }

    @Override
    public EthBlockNumber selectByBlockNumber(BigInteger blockNumber) {
        return ethBlockNumberMapper.selectByPrimaryKey(blockNumber);
    }

    @Override
    public BigInteger selectCurrent() {
        BigInteger current = RedisBlockUtil.getBlockCurrent(redisTemplate);
        if (current == null) {
            current = ethBlockNumberMapper.selectMaxBlockNumber();
            if(current == null){
                current = walletWeb3j.getEthBlock();
            }
            RedisBlockUtil.setBlockCurrent(current, redisTemplate);
        }
        return current;
    }


    @Override
    public BigInteger selectNewest() {
        BigInteger newest = RedisBlockUtil.getBlockNewest(redisTemplate);
        if (newest == null || newest.compareTo(selectCurrent().add(BIAS_CURRENT)) <= 0) {
            newest = walletWeb3j.getEthBlock();
            RedisBlockUtil.setBlockNewest(newest, redisTemplate);
        }
        return newest;
    }

    @Override
    public List<EthBlockNumber> selectWaitBlocks() {
        EthBlockNumber ethBlockNumber = new EthBlockNumber();
        ethBlockNumber.setStatus(EthWalletConstants.StatusType.BLOCK_WAIT);
        return ethBlockNumberMapper.select(ethBlockNumber);
    }

    @Override
    @Transactional
    public void handleBlockTxIn(BigInteger blockNumber, Map<String, EthToken> coinAddrs, Set<String> adds) {
        // 查询该区块的所有交易记录
        List<EthBlock.TransactionResult> txResults = walletWeb3j.getTokenTransactionList(blockNumber);
        try {
            for (EthBlock.TransactionResult<Transaction> txResult : txResults) {
                Transaction tx = txResult.get();
                // 忽略打油费的钱包的充值
                if(ethGasWalletService.isGasWallet(tx.getFrom())){
                    continue;
                }
                if (coinAddrs.containsKey(tx.getTo())) { // 充值ETH代币
                    if (!tx.getInput().matches(REGEX)) continue;
                    Web3jTransferDTO transferDTO = getTokenWalletDto(tx, coinAddrs.get(tx.getTo()));
                    if (adds.contains(transferDTO.getToAddr())) {
                        // 插入ETH代币钱包充值记录
                        ethWalletTransferService.insert(transferDTO.getHash(), transferDTO.getFromAddr(),
                                transferDTO.getToAddr(), transferDTO.getAmount(), transferDTO.getGasPrice(),
                                coinAddrs.get(tx.getTo()),
                                coinAddrs.get(GAS_TOKENADDR),
                                EthWalletConstants.TransferType.IN, EthWalletConstants.StatusType.IN_LOAD, new Date());
                    }
                } else if (adds.contains(tx.getTo())) { // 充值ETH

                    Web3jTransferDTO transferDTO = getWalletDto(tx);
                    // 插入ETH钱包充值记录
                    ethWalletTransferService.insert(transferDTO.getHash(), transferDTO.getFromAddr(),
                            transferDTO.getToAddr(), transferDTO.getAmount(), transferDTO.getGasPrice(),
                            coinAddrs.get(GAS_TOKENADDR),
                            coinAddrs.get(GAS_TOKENADDR),
                            EthWalletConstants.TransferType.IN, EthWalletConstants.StatusType.IN_LOAD, new Date());
                }
            }
            // 修改当前区块为成功
            updateByBlockNumber(blockNumber, EthWalletConstants.StatusType.BLOCK_SUCCESS);
        }catch(Exception e){
            LOG.warn("get transfer error "+e.getMessage());
            e.printStackTrace();

        }


    }

    @Override
    public void deleteBlock(BigInteger currentBlock) {
        ethBlockNumberMapper.deleteByPrimaryKey(currentBlock);
    }


    /**
     * 代币数据DTO
     *
     * @param transaction 数据信息
     * @return
     */
    private Web3jTransferDTO getTokenWalletDto(Transaction transaction, EthToken coin) {
        Matcher matcher = PATTERN.matcher(transaction.getInput());
        matcher.matches();
        String toAddr = matcher.group(2).replaceAll(".+(.{40})", "0x$1");
        BigInteger amount = new BigInteger(matcher.group(3).replaceAll("0+(.+)", "$1"), 16);
        Web3jTransferDTO transferDTO = new Web3jTransferDTO();
        transferDTO.setHash(transaction.getHash());
        transferDTO.setFromAddr(transaction.getFrom());
        transferDTO.setToAddr(toAddr);
        transferDTO.setTokenAddr(transaction.getTo());
        transferDTO.setGasPrice(new BigDecimal(transaction.getGasPrice()));
        transferDTO.setAmount(DataCheckUtil.bitToBigDecimal(amount, coin.getTokenDecimals()));
        return transferDTO;
    }

    /**
     * eth数据DTO
     *
     * @param transaction
     * @return
     */
    private Web3jTransferDTO getWalletDto(Transaction transaction) {
        Web3jTransferDTO transferDTO = new Web3jTransferDTO();
        transferDTO.setHash(transaction.getHash());
        transferDTO.setFromAddr(transaction.getFrom());
        transferDTO.setToAddr(transaction.getTo());
        transferDTO.setTokenAddr(GAS_TOKENADDR);
        transferDTO.setAmount(DataCheckUtil.bitToBigDecimal(transaction.getValue()));
        transferDTO.setGasPrice(DataCheckUtil.bitToBigDecimal(  transaction.getGasPrice()));
        return transferDTO;
    }

}
