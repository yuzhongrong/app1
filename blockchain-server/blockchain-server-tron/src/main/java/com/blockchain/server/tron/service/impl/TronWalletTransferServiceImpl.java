package com.blockchain.server.tron.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.common.constant.TronWalletConstants;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.common.util.DataCheckUtil;
import com.blockchain.server.tron.dto.RefundOutDTO;
import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.dto.TronWalletTransferDto;
import com.blockchain.server.tron.entity.TronToken;
import com.blockchain.server.tron.entity.TronWallet;
import com.blockchain.server.tron.entity.TronWalletTransfer;
import com.blockchain.server.tron.mapper.TronWalletTransferMapper;
import com.blockchain.server.tron.service.TronWalletService;
import com.blockchain.server.tron.service.TronWalletTransferService;
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
public class TronWalletTransferServiceImpl implements TronWalletTransferService {

    static final String DEFAULT = "";

    @Autowired
    private TronWalletService tronWalletService;
    @Autowired
    private TronWalletTransferMapper tronWalletTransferMapper;

    /**
     * 添加记录并修改账户金额
     *
     * @param txID
     * @param fromHexAddress
     * @param toHexAddress
     * @param amount
     * @param transferType
     * @param tokenAddr
     * @param tokenSymbol
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Integer handleWalletAndWalletTransfer(String txID, String fromAddress, String fromHexAddress, String toHexAddress, String amount, String transferType, String tokenAddr, String tokenSymbol, Integer tokenDecimal) {
        BigDecimal amountBigDecimal = DataCheckUtil.bitToBigDecimal(new BigInteger(amount), tokenDecimal);
        Date now = new Date();
        TronWalletTransfer tronWalletTransfer = new TronWalletTransfer();
        tronWalletTransfer.setId(UUID.randomUUID().toString());
        tronWalletTransfer.setCreateTime(now);
        tronWalletTransfer.setUpdateTime(now);
        tronWalletTransfer.setFromHexAddr(fromHexAddress);
        tronWalletTransfer.setToHexAddr(toHexAddress);
        // 充值保存数据
        if (TronConstant.TransferType.TRANSFER_IN.equals(transferType)) {
            tronWalletTransfer.setHash(txID);
            tronWalletTransfer.setAmount(amountBigDecimal);
            tronWalletTransfer.setTokenAddr(tokenAddr);
            tronWalletTransfer.setTokenSymbol(tokenSymbol);
            tronWalletTransfer.setTransferType(TronConstant.TransferType.TRANSFER_IN);
        }
        tronWalletTransfer.setStatus(TronConstant.TransferStatus.SUCCESS);
        this.insertWalletTransfer(tronWalletTransfer);

        // 添加一条交易记录
        // 修改账户金额
        return tronWalletService.updateWalletBalanceByAddressInRowLock(amountBigDecimal, fromAddress, tokenAddr);
    }

    /**
     * 插入交易记录
     *
     * @param tronWalletTransfer
     * @return
     */
    @Override
    @Transactional
    public Integer insertWalletTransfer(TronWalletTransfer tronWalletTransfer) {
        ExceptionPreconditionUtils.notEmpty(tronWalletTransfer);
        return tronWalletTransferMapper.insertSelective(tronWalletTransfer);
    }

    /**
     * 查询钱包交易记录
     *
     * @param userOpenId
     * @param tokenAddr
     * @param walletType
     * @return
     */
    @Override
    public List<TronWalletTransferDto> listWalletTransfer(String userOpenId, String tokenAddr, String walletType) {
        TronWallet tronWallet = tronWalletService.selectWallet(userOpenId, tokenAddr, walletType);
        List<TronWalletTransferDto> tronWalletTransferList = tronWalletTransferMapper.listWalletTransfer(tronWallet.getHexAddr(), tokenAddr);
        // 循环赋值钱包地址
        for (TronWalletTransferDto tronWalletTransfer : tronWalletTransferList) {
            // 判断钱包地址为收款人或者支付人
            if (tronWalletTransfer.getFromHexAddr().equalsIgnoreCase(tronWallet.getHexAddr())) {
                tronWalletTransfer.setFromAddr(tronWallet.getAddr());
                if (tronWalletTransfer.getToHexAddr() != null)
                    tronWalletTransfer.setToAddr(tronWalletService.selectAddressByHexAddress(tronWalletTransfer.getToHexAddr()));
            } else if (tronWalletTransfer.getToHexAddr().equalsIgnoreCase(tronWallet.getHexAddr())) {
                tronWalletTransfer.setToAddr(tronWallet.getAddr());
                if (tronWalletTransfer.getFromHexAddr() != null)
                tronWalletTransfer.setFromAddr(tronWalletService.selectAddressByHexAddress(tronWalletTransfer.getFromHexAddr()));
            }
        }
        return tronWalletTransferList;
    }


    @Override
    public List<TronWalletTransfer> selectByTxTypeAndStatus(String transferType, Integer Status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TronWalletTransfer where = new TronWalletTransfer();
        where.setTransferType(transferType);
        where.setStatus(Status);
        return tronWalletTransferMapper.select(where);
    }

    @Override
    @Transactional
    public void updateStatus(String id, Integer status, Date date) {
        TronWalletTransfer TronWalletTransfer = tronWalletTransferMapper.selectByPrimaryKey(id);
        ExceptionPreconditionUtils.checkNotNull(TronWalletTransfer, new TronWalletException(TronWalletEnums.INEXISTENCE_TX));
        TronWalletTransfer.setStatus(status);
        TronWalletTransfer.setUpdateTime(date);
        // 失败抛出异常
        int row = tronWalletTransferMapper.updateByPrimaryKeySelective(TronWalletTransfer);
        if (row == 0) throw new TronWalletException(TronWalletEnums.GET_PUBLICKEY_ERROR);
    }

    /**
     * 根据hash查询交易记录
     *
     * @param hashId
     * @return
     */
    @Override
    public TronWalletTransfer selectByHashId(String hashId) {
        TronWalletTransfer example = new TronWalletTransfer();
        example.setHash(hashId);
        return tronWalletTransferMapper.selectOne(example);
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
        return tronWalletTransferMapper.countByHashAndTransferType(hash, transferType);
    }

    /**
     * 插入一条钱包流水记录
     *
     * @param hash           转账唯一标识
     * @param fromHexAddress 支付地址
     * @param toHexAddress   接收地址
     * @param amount         转账余额
     * @param amountCoin     转账币种类型
     * @param transferType   转账类型
     * @return
     */
    @Override
    public TronWalletTransfer insert(String hash, String fromHexAddress, String toHexAddress, BigDecimal amount,
                                     TronToken amountCoin, String transferType) {
        return insert(hash, fromHexAddress, toHexAddress, amount, amountCoin.getTokenAddr(), amountCoin.getTokenSymbol(),
                BigDecimal.ZERO, DEFAULT, DEFAULT, DEFAULT, transferType, TronWalletConstants.StatusType.SUCCESS,
                DEFAULT);
    }

    /**
     * 插入一条钱包流水记录
     *
     * @param hash           转账唯一标识
     * @param fromHexAddress 支付地址
     * @param toHexAddress   接收地址
     * @param amount         转账余额
     * @param amountCoin     转账币种类型
     * @param transferType   转账类型
     * @return
     */
    @Override
    public TronWalletTransfer insert(String hash, String fromHexAddress, String toHexAddress, BigDecimal amount, TronTokenDTO amountCoin, String transferType) {
        return insert(hash, fromHexAddress, toHexAddress, amount, amountCoin.getTokenAddr(), amountCoin.getTokenSymbol(),
                BigDecimal.ZERO, DEFAULT, DEFAULT, DEFAULT, transferType, TronWalletConstants.StatusType.SUCCESS,
                DEFAULT);
    }

    /**
     * 查询记录是否存在
     *
     * @param hash
     * @param txType
     * @return
     */
    @Override
    public Integer findByHashAndTypeTheLime(String hash, String txType) {
        ExceptionPreconditionUtils.checkStringNotBlank(hash, new TronWalletException(TronWalletEnums.NULL_HASH));
        ExceptionPreconditionUtils.checkStringNotBlank(txType, new TronWalletException(TronWalletEnums.NULL_TXTYPE));
        return tronWalletTransferMapper.findByHashAndTypeTheLime(hash, txType);
    }

    /**
     * 根据要求查询一条交易信息
     *
     * @param transfer
     * @return
     */
    @Override
    public TronWalletTransfer selectOne(TronWalletTransfer transfer) {
        return tronWalletTransferMapper.selectOne(transfer);
    }

    /**
     * 根据要求查询多条交易信息
     *
     * @param transfer
     * @return
     */
    @Override
    public List<TronWalletTransfer> select(TronWalletTransfer transfer) {
        return tronWalletTransferMapper.select(transfer);
    }

    /**
     * 根据商户单号查询退款记录
     *
     * @param tradeNo 商户单号
     * @return
     */
    @Override
    public List<RefundOutDTO> selectRefundByTradeNo(String tradeNo) {
        return tronWalletTransferMapper.selectByAddrAndType(tradeNo, null, TronWalletConstants.TransferType.REFUND);
    }

    /**
     * 插入一条记录数据的通用方法
     */
    private TronWalletTransfer insert(String hash, String fromHexAddress, String toHexAddress, BigDecimal amount, String tokenAddr
            , String tokenSymbol, BigDecimal gasPrice, String gasTokenType, String gasTokenName,
                                      String gasTokenSymbol, String transferType, int status, String remark) {
        Date date = new Date();
        TronWalletTransfer tronWalletTransfer = new TronWalletTransfer();
        tronWalletTransfer.setId(UUID.randomUUID().toString());
        tronWalletTransfer.setHash(hash);
        tronWalletTransfer.setAmount(amount);
        tronWalletTransfer.setFromHexAddr(fromHexAddress);
        tronWalletTransfer.setToHexAddr(toHexAddress);
        tronWalletTransfer.setTokenAddr(tokenAddr);
        tronWalletTransfer.setTokenSymbol(tokenSymbol);
        tronWalletTransfer.setGasPrice(gasPrice);
        tronWalletTransfer.setGasTokenType(gasTokenType);
        tronWalletTransfer.setGasTokenName(gasTokenName);
        tronWalletTransfer.setGasTokenSymbol(gasTokenSymbol);
        tronWalletTransfer.setTransferType(transferType);
        tronWalletTransfer.setStatus(status);
        tronWalletTransfer.setRemark(remark);
        tronWalletTransfer.setCreateTime(date);
        tronWalletTransfer.setUpdateTime(date);
        int row = tronWalletTransferMapper.insertSelective(tronWalletTransfer);
        if (row == 0) {
            throw new TronWalletException(TronWalletEnums.SERVER_IS_TOO_BUSY);
        }
        return tronWalletTransfer;
    }

}
