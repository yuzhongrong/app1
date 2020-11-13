package com.blockchain.server.eth.service.impl;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.constant.WalletTypeConstants;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.UserInfoDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eth.common.constants.EthWalletConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.common.util.BaseHelperUtil;
import com.blockchain.server.eth.common.util.DataCheckUtil;
import com.blockchain.server.eth.dto.EthGFCTransfer;
import com.blockchain.server.eth.dto.EthWalletDTO;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWalletTransfer;
import com.blockchain.server.eth.feign.UserFeign;
import com.blockchain.server.eth.mapper.EthWalletTransferMapper;
import com.blockchain.server.eth.service.IEthWalletService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import com.esotericsoftware.minlog.Log;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 以太坊钱包记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class EthWalletTransferServiceImpl implements IEthWalletTransferService {
    private static final Logger LOG = LoggerFactory.getLogger(EthWalletTransferServiceImpl.class);

    static final String DEFAULT = "";

    @Autowired
    UserFeign userFeign;
    @Autowired
    EthWalletTransferMapper ethWalletTransferMapper;
    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    IWalletWeb3j walletWeb3j;

    @Override
    public EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount,
                                    EthToken amountCoin, String transferType, Date date) {
        return insert(hash, fromAddr, toAddr, amount, amountCoin.getTokenAddr(), amountCoin.getTokenSymbol(),
                BigDecimal.ZERO, DEFAULT, DEFAULT, DEFAULT, transferType, EthWalletConstants.StatusType.SUCCESS,
                DEFAULT, date);
    }

    @Override
    public EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount,
                                    EthToken amountCoin, String transferType, int status, Date date) {
        return insert(hash, fromAddr, toAddr, amount, amountCoin.getTokenAddr(), amountCoin.getTokenSymbol(),
                BigDecimal.ZERO, DEFAULT, DEFAULT, DEFAULT, transferType, status,
                DEFAULT, date);
    }

    @Override
    public EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, BigDecimal gas,
                                    EthToken amountCoin, EthToken gasCoin, String transferType, int status, Date date) {
        return insert(hash, fromAddr, toAddr, amount, amountCoin.getTokenAddr(), amountCoin.getTokenSymbol(), gas,
                gasCoin.getTokenAddr(), gasCoin.getTokenSymbol(), gasCoin.getTokenSymbol(), transferType, status,
                DEFAULT, date);
    }

    @Override
    public EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, BigDecimal gas,
                                    EthToken amountCoin, EthToken gasCoin, String transferType, int status,
                                    String remark, Date date) {
        return insert(hash, fromAddr, toAddr, amount, amountCoin.getTokenAddr(), amountCoin.getTokenSymbol(), gas,
                gasCoin.getTokenAddr(), gasCoin.getTokenSymbol(), gasCoin.getTokenSymbol(), transferType, status,
                remark, date);
    }

    @Override
    @Transactional
    public EthWalletTransfer updateGas(EthWalletTransfer ethWalletTransfer) {
        LOG.info("send walletWeb3j.getTransactionStatusByHash" + ethWalletTransfer.getHash() + "---》" + ethWalletTransfer.getId());
        TransactionReceipt receipt = walletWeb3j.getTransactionStatusByHash(ethWalletTransfer.getHash(), ethWalletTransfer.getUpdateTime());

        BigInteger gas = ethWalletTransfer.getGasPrice().multiply(new BigDecimal(receipt.getGasUsed())).toBigInteger();
        ethWalletTransfer.setGasPrice(DataCheckUtil.bitToBigDecimal(gas));
        ethWalletTransfer.setUpdateTime(new Date());
        // 失败抛出异常
        int row = ethWalletTransferMapper.updateByPrimaryKeySelective(ethWalletTransfer);
        if (row == 0) throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        // 判断记录是否成功，ture成功，false失败
        int status = receipt.isStatusOK() ? EthWalletConstants.StatusType.IN_SUCCESS :
                EthWalletConstants.StatusType.IN_ERROR;
        ethWalletTransfer.setStatus(status);
        return ethWalletTransfer;
    }

    @Override
    @Transactional
    public void updateStatus(EthWalletTransfer ethWalletTransfer) {
        // 失败抛出异常
        int row = ethWalletTransferMapper.updateByPrimaryKeySelective(ethWalletTransfer);
        if (row == 0) throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
    }

    @Override
    @Transactional
    public void updateStatus(String id, int status, Date date) {
        EthWalletTransfer ethWalletTransfer = ethWalletTransferMapper.selectByPrimaryKey(id);
        ExceptionPreconditionUtils.checkNotNull(ethWalletTransfer,
                new EthWalletException(EthWalletEnums.INEXISTENCE_TX));
        ethWalletTransfer.setStatus(status);
        ethWalletTransfer.setUpdateTime(date);
        // 失败抛出异常
        int row = ethWalletTransferMapper.updateByPrimaryKeySelective(ethWalletTransfer);
        if (row == 0) throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
    }

    @Override
    public List<EthWalletTransfer> selectByTxTypeAndStatus(String transferType, int Status,
                                                           Integer pageNum, Integer pageSize) {
        BaseHelperUtil.startPage(pageNum, pageSize);  // 分页处理
        EthWalletTransfer where = new EthWalletTransfer();
        where.setTransferType(transferType);
        where.setStatus(Status);
        return ethWalletTransferMapper.select(where);
    }

    @Override
    public List<EthWalletTransfer> selectTransfer(String userOpenId, String tokenAddr, String walletType) {
        EthWalletDTO wallet = ethWalletService.selectByUserOpenIdAndTokenAddrAndWalletType(userOpenId, tokenAddr,
                walletType);
        return ethWalletTransferMapper.selectTransfer(wallet.getAddr(), tokenAddr);
    }

    @Override
    public EthGFCTransfer getGFCTransfer(String orderId) {
        ExceptionPreconditionUtils.checkNotNull(orderId,
                new EthWalletException(EthWalletEnums.INEXISTENCE_TX));
        EthWalletTransfer ethWalletTransfer = new EthWalletTransfer();
        ethWalletTransfer.setHash(orderId);
        List<EthWalletTransfer> select = ethWalletTransferMapper.select(ethWalletTransfer);
        if (select == null || select.size() == 0) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_TX);
        }
        ethWalletTransfer = select.get(0);
        List<EthWalletDTO> ethWalletDTOS = ethWalletService.selectByAddrAndWalletType(ethWalletTransfer.getToAddr(), WalletTypeConstants.CCT);
        if (ethWalletDTOS == null || ethWalletDTOS.size() == 0) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_TX);
        }
        String userId = ethWalletDTOS.get(0).getUserOpenId();
        UserInfoDTO userInfoDTO = userFeign.getUserInfo(null, userId).getData();
        if (userInfoDTO == null) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_TX);
        }
        EthGFCTransfer ethGFCTransfer = new EthGFCTransfer();
        ethGFCTransfer.setOrderId(ethWalletTransfer.getId());
        ethGFCTransfer.setAccount(userInfoDTO.getTelPhone());
        ethGFCTransfer.setAmount(ethWalletTransfer.getAmount());
        String status = "";
        if (ethWalletTransfer.getStatus().equals(1))
            status = EthWalletConstants.StatusType.GFC_SUCCESS;
        else
            status = EthWalletConstants.StatusType.GFC_FAIL;
        ethGFCTransfer.setStatus(status);
        ethGFCTransfer.setTransferType(EthWalletConstants.TransferType.IN);
        return ethGFCTransfer;
    }

    /**
     * 插入一条记录数据的通用方法
     */
    private EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, String tokenAddr
            , String tokenSymbol, BigDecimal gasPrice, String gasTokenType, String gasTokenName,
                                     String gasTokenSymbol, String transferType, int status, String remark, Date date) {
        EthWalletTransfer ethWalletTransfer = new EthWalletTransfer();
        ethWalletTransfer.setHash(hash);
        ethWalletTransfer.setTransferType(transferType);
        //查询数据库是否有本次记录防止重复插入
        List<EthWalletTransfer> walletList = null;
        try {
            walletList = ethWalletTransferMapper.select(ethWalletTransfer);
        } catch (Exception e) {

        }
        if (walletList != null && walletList.size() > 0) {
            LOG.info("the transfer is exist hash:" + walletList.get(0).getHash());
            return null;
        }

        ethWalletTransfer.setId(UUID.randomUUID().toString());
        ethWalletTransfer.setAmount(amount);
        ethWalletTransfer.setFromAddr(fromAddr);
        ethWalletTransfer.setToAddr(toAddr);
        ethWalletTransfer.setTokenAddr(tokenAddr);
        ethWalletTransfer.setTokenSymbol(tokenSymbol);
        ethWalletTransfer.setGasPrice(gasPrice);
        ethWalletTransfer.setGasTokenType(gasTokenType);
        ethWalletTransfer.setGasTokenName(gasTokenName);
        ethWalletTransfer.setGasTokenSymbol(gasTokenSymbol);
        ethWalletTransfer.setStatus(status);
        ethWalletTransfer.setRemark(remark);
        ethWalletTransfer.setUpdateTime(date);
        ethWalletTransfer.setCreateTime(date);
        int row = ethWalletTransferMapper.insertSelective(ethWalletTransfer);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
        return ethWalletTransfer;
    }
}
