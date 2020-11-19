package com.blockchain.server.cmc.service.impl;

import com.blockchain.common.base.constant.UserWalletInfoConstant;
import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.server.cmc.common.constants.TransferConstans;
import com.blockchain.server.cmc.common.enums.ExceptionEnums;
import com.blockchain.server.cmc.common.exception.ServiceException;
import com.blockchain.server.cmc.common.util.AddressSetRedisUtils;
import com.blockchain.server.cmc.dto.ApplicationDTO;
import com.blockchain.server.cmc.dto.TokenDTO;
import com.blockchain.server.cmc.dto.WalletDTO;
import com.blockchain.server.cmc.entity.Token;
import com.blockchain.server.cmc.entity.Wallet;
import com.blockchain.server.cmc.entity.WalletTransfer;
import com.blockchain.server.cmc.feign.OreFegin;
import com.blockchain.server.cmc.mapper.WalletMapper;
import com.blockchain.server.cmc.rpc.CoinUtils;
import com.blockchain.server.cmc.service.ApplicationService;
import com.blockchain.server.cmc.service.TokenService;
import com.blockchain.server.cmc.service.WalletService;
import com.blockchain.server.cmc.service.WalletTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {
    private Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);
    @Autowired
    private CoinUtils coinUtils;

    @Autowired
    private WalletTransferService walletTransferService;

    @Autowired
    private AddressSetRedisUtils addressSetRedisUtils;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WalletKeyServiceImpl walletKeyService;

    @Autowired
    private OreFegin oreFegin;

    @Override
    @Transactional
    public Integer insertWallet(String userOpenId) {
        List<ApplicationDTO> applicationDTOList = applicationService.listApplication();
        for (ApplicationDTO applicationDTO : applicationDTOList) {
            String address = "";
            try {
                address = coinUtils.getNewAddress();
                String privateKey = coinUtils.getPrivateKeyByAddress(address);
                addressSetRedisUtils.insert(address);
                walletKeyService.insertWalletKey(address, privateKey);
//                // 测试
//                address = UUID.randomUUID().toString();
            } catch (Exception e) {
                throw new ServiceException(ExceptionEnums.CREATE_WALLET_ERROR);
            }
            List<TokenDTO> tokenDTOList = tokenService.listToken();
            for (TokenDTO tokenDTO : tokenDTOList) {
                Wallet wallet = new Wallet();
                wallet.setAddr(address);
                wallet.setTokenId(tokenDTO.getTokenId());
                wallet.setUserOpenId(userOpenId);
                wallet.setTokenSymbol(tokenDTO.getTokenSymbol());
                wallet.setBalance(BigDecimal.ZERO);
                wallet.setFreeBalance(BigDecimal.ZERO);
                wallet.setFreezeBalance(BigDecimal.ZERO);
                wallet.setWalletType(applicationDTO.getAppId());
                wallet.setCreateTime(new Date());
                wallet.setUpdateTime(wallet.getCreateTime());

                int count = walletMapper.insertSelective(wallet);
                if (count != 1) {
                    throw new ServiceException(ExceptionEnums.CREATE_WALLET_ERROR);
                }
            }
        }

        return 1;
    }

    @Override
    public List<WalletDTO> selectAllByUserOpenId(String userOpenId, String walletType) {
        //校验钱包类型
        applicationService.checkWalletType(walletType);
        return walletMapper.selectAllByUserOpenId(userOpenId, walletType);
    }

    @Override
    public WalletDTO selectByUserOpenId(String userOpenId, Integer tokenId, String walletType) {
        WalletDTO walletDTO = walletMapper.selectByUserOpenId(userOpenId, tokenId, walletType);
        if (walletDTO == null) {
            throw new ServiceException(ExceptionEnums.NO_WALLET_ERROR);
        }
        return walletDTO;
    }

    @Override
    public WalletDTO selectByAddr(String addr, Integer tokenId, String walletType) {
        return walletMapper.selectByAddr(addr, tokenId, walletType);
    }

    @Override
    public Integer updateBalanceByAddrInRowLock(String address, Integer tokenId, BigDecimal freeAmount, BigDecimal freezeAmount, BigDecimal totalAmount, Date modifyTime) {
        return walletMapper.updateBalanceByAddrInRowLock(address, tokenId, freeAmount, freezeAmount, totalAmount, modifyTime);
    }

    @Override
    public Set<String> getAllWalletAddr() {
        return walletMapper.getAllWalletAddr();
    }

    @Override
    @Transactional
    public void updateTxOutError(WalletTransfer walletTransfer) {
        WalletDTO wallet = selectByAddr(walletTransfer.getFromAddr(), walletTransfer.getTokenId(), null);
        Date date = new Date();
        // 业务操作（1）- 恢复提现余额,扣除手续费
        BigDecimal amount = walletTransfer.getAmount();
        this.updateBalanceByAddrInRowLock(wallet.getAddr(), wallet.getTokenId(), amount, amount.negate(), BigDecimal.ZERO, date);
        // 业务操作（2）- 修改记录状态
        walletTransferService.updateStatus(walletTransfer.getId(), TransferConstans.STATUS_FILE, date);
    }

    @Override
    @Transactional
    public void updateTxOutSuccess(WalletTransfer walletTransfer) {
        WalletDTO wallet = selectByAddr(walletTransfer.getFromAddr(), walletTransfer.getTokenId(), null);
        Date date = new Date();
        // 业务操作（1）- 恢复提现余额,扣除手续费
        BigDecimal amount = walletTransfer.getAmount();
        this.updateBalanceByAddrInRowLock(wallet.getAddr(), wallet.getTokenId(), BigDecimal.ZERO, amount.negate(), amount.negate(), date);
        // 业务操作（2）- 修改记录状态
        walletTransferService.updateStatus(walletTransfer.getId(), TransferConstans.STATUS_SUCCESS, date);
    }

    @Override
    public Wallet findWallet(String userId, String walletType, String coinName) {
        Wallet condition = new Wallet();
        condition.setWalletType(walletType);
        condition.setTokenSymbol(coinName);
        condition.setUserOpenId(userId);
        Wallet wallet = walletMapper.selectOne(condition);
        if (wallet == null) {
            throw new ServiceException(ExceptionEnums.INEXISTENCE_WALLET);
        }
        return wallet;
    }

    @Override
    @Transactional
    public WalletTransfer handleTransfer(String userId, String fromType, String toType, String coinName, BigDecimal amount) {
        Date end = new Date();
        //校验钱包类型
        applicationService.checkWalletType(fromType);
        applicationService.checkWalletType(toType);
        Token token = tokenService.selectByTokenName(coinName);
        Wallet fromWallet = this.findWallet(userId, fromType, coinName);
        Wallet toWallet = this.findWallet(userId, toType, coinName);
        //该用户减去提现可用余额、总额
        int countUb = this.updateBalanceByAddrInRowLock(fromWallet.getAddr(), fromWallet.getTokenId(), amount.negate(), BigDecimal.ZERO, amount.negate(), end);
        if (countUb != 1) {
            throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
        }
        //接收用户加上可用余额、总额
        int countUbR = this.updateBalanceByAddrInRowLock(toWallet.getAddr(), toWallet.getTokenId(), amount, BigDecimal.ZERO, amount, end);
        if (countUbR != 1) {
            throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
        }

        //并插入一条提现记录，站内快速转账
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setFromAddr(fromWallet.getAddr());
        walletTransfer.setToAddr(toWallet.getAddr());
        walletTransfer.setAmount(amount.abs());
        walletTransfer.setTokenId(token.getTokenId());
        walletTransfer.setTokenSymbol(token.getTokenSymbol());
        walletTransfer.setTransferType(TransferConstans.TYPE_FAST);
        walletTransfer.setStatus(TransferConstans.STATUS_SUCCESS);
        walletTransfer.setCreateTime(end);
        walletTransfer.setUpdateTime(end);
        int countIt = walletTransferService.insertTransfer(walletTransfer);
        if (countIt != 1) {
            throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
        }
        return walletTransfer;
    }

    @Override
    public List<WalletBalanceDTO> getBalanceByIdAndTypes(String userOpenId, String[] walletTypes) {
        return walletMapper.getBalanceByIdAndTypes(userOpenId, walletTypes);
    }

    @Override
    public void getBalanceByIdAndTypesBatch(String[] walletTypes) {
        LOG.info("开始查询ltc钱包数据");
        Long firDate =System.currentTimeMillis();
        List<WalletBalanceBatchDTO> walletList = walletMapper.getBalanceByIdAndTypesBatch(walletTypes);
        if(walletList==null||walletList.size()==0)
            throw new ServiceException(ExceptionEnums.SERVER_IS_TOO_BUSY);
        Long endSelectDate =System.currentTimeMillis();
        LOG.info("查询ltc钱包信息结束，耗时:"+(firDate-endSelectDate));
        //异步回调
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                int retryCount = 0;
                while (retryCount < 3) {
                    try {
                        LOG.info("trx钱包信息回调，大小："+walletList.size());
                        oreFegin.pushUserWallet(UserWalletInfoConstant.LTC_WALLET, walletList);
                        Long endDate =System.currentTimeMillis();
                        LOG.info("回调ltc钱包信息结束，耗时:"+(endSelectDate-endDate));
                        break;
                    } catch (Exception e) {
                        LOG.info("回调失败，回调次数:" + retryCount);
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(30000L);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    retryCount++;
                }
            }
        });
        th.start();

    }

}
