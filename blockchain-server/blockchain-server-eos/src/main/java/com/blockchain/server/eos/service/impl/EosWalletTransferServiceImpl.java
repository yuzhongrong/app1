package com.blockchain.server.eos.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.dto.WalletTransferDTO;
import com.blockchain.server.eos.entity.WalletTransfer;
import com.blockchain.server.eos.mapper.WalletTransferMapper;
import com.blockchain.server.eos.service.EosWalletService;
import com.blockchain.server.eos.service.EosWalletTransferService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Harvey
 * @date 2019/2/19 11:40
 * @user WIN10
 */
@Service
public class EosWalletTransferServiceImpl implements EosWalletTransferService {

    @Autowired
    private EosWalletService eosWalletService;
    @Autowired
    private WalletTransferMapper walletTransferMapper;

    /**
     * 添加记录并修改账户金额
     *
     * @param hash
     * @param transferType
     * @param amount
     * @param tokenName
     * @param tokenSymbol
     * @param memo
     * @param accountId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int handleWalletAndWalletTransfer(String hash, Integer accountId, String accountName, String transferType, BigDecimal amount, String tokenName, String tokenSymbol, String memo, BigInteger blockNumber) {
        Date now = new Date();
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setRemark(memo);
        walletTransfer.setTimestamp(now);
        walletTransfer.setBlockNumber(blockNumber.toString());
        // 充值保存数据
        if (EosConstant.TransferType.TRANSFER_IN.equals(transferType)) {
            walletTransfer.setHash(hash);
            walletTransfer.setAmount(amount);
            walletTransfer.setTokenName(tokenName);
            walletTransfer.setTokenSymbol(tokenSymbol);
            walletTransfer.setToId(accountId);
            walletTransfer.setTransferType(EosConstant.TransferType.TRANSFER_IN);
        }
        walletTransfer.setStatus(EosConstant.TransferStatus.SUCCESS);
        this.insertWalletTransfer(walletTransfer);

        // 添加一条交易记录
        // 修改账户金额

        return eosWalletService.updateWalletBalanceByIdInRowLock(amount, accountId, now);
    }

    /**
     * 插入交易记录
     *
     * @param walletTransfer
     * @return
     */
    @Override
    @Transactional
    public int insertWalletTransfer(WalletTransfer walletTransfer) {
        ExceptionPreconditionUtils.notEmpty(walletTransfer);
        return walletTransferMapper.insertSelective(walletTransfer);
    }

    /**
     * 插入交易记录
     *
     * @return
     */
    @Override
    @Transactional
    public int insertWalletTransfer(Integer from, Integer to, String account, BigDecimal amount, String tokenName, String tokenSymbol, BigDecimal gasAmount, String gasTokenName, String gasTokenType, String gasTokenSymbol, String type, Integer status, String remark, Date date) {
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setFromId(from);
        walletTransfer.setToId(to);
        walletTransfer.setAccountName(account);
        walletTransfer.setAmount(amount);
        walletTransfer.setTokenName(tokenName);
        walletTransfer.setTokenSymbol(tokenSymbol);
        walletTransfer.setGasPrice(gasAmount);
        walletTransfer.setGasTokenName(gasTokenName);
        walletTransfer.setGasTokenType(gasTokenType);
        walletTransfer.setGasTokenSymbol(gasTokenSymbol);
        walletTransfer.setTransferType(type);
        walletTransfer.setStatus(status);
        walletTransfer.setRemark(remark);
        walletTransfer.setTimestamp(date);
        return walletTransferMapper.insertSelective(walletTransfer);
    }

    /**
     * 查询钱包交易记录
     *
     * @param userOpenId
     * @param tokenName
     * @param walletType
     * @return
     */
    @Override
    public List<WalletTransferDTO> listWalletTransfer(String userOpenId, String tokenName, String walletType) {
        WalletDTO walletDTO = eosWalletService.selectWallet(userOpenId, tokenName, walletType);
        Integer walletId = walletDTO.getId();
        return walletTransferMapper.listWalletTransfer(walletId, tokenName);
    }


    @Override
    public List<WalletTransfer> selectByTxTypeAndStatus(String transferType, int Status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        WalletTransfer where = new WalletTransfer();
        where.setTransferType(transferType);
        where.setStatus(Status);
        return walletTransferMapper.select(where);
    }

    @Override
    @Transactional
    public void updateStatus(String id, int status, Date date) {
        WalletTransfer ethWalletTransfer = walletTransferMapper.selectByPrimaryKey(id);
        ExceptionPreconditionUtils.checkNotNull(ethWalletTransfer, new EosWalletException(EosWalletEnums.INEXISTENCE_TX));
        ethWalletTransfer.setStatus(status);
        ethWalletTransfer.setTimestamp(date);
        // 失败抛出异常
        int row = walletTransferMapper.updateByPrimaryKeySelective(ethWalletTransfer);
        if (row == 0) throw new EosWalletException(EosWalletEnums.GET_PUBLICKEY_ERROR);
    }

    /**
     * 根据hash查询交易记录
     *
     * @param hashId
     * @return
     */
    @Override
    public WalletTransfer selectByHashId(String hashId) {
        WalletTransfer example = new WalletTransfer();
        example.setHash(hashId);
        return walletTransferMapper.selectOne(example);
    }

    /**
     * 根据hash查询是否存在数据
     *
     * @param hash
     * @param transferType
     * @return
     */
    @Override
    public Integer countByHashAndTransferType(String hash, String transferType) {
        ExceptionPreconditionUtils.notEmpty(hash);
        ExceptionPreconditionUtils.notEmpty(transferType);
        return walletTransferMapper.countByHashAndTransferType(hash, transferType);
    }

}
