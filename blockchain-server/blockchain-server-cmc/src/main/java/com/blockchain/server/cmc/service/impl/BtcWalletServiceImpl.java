package com.blockchain.server.cmc.service.impl;

import com.blockchain.common.base.constant.UserWalletInfoConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.server.cmc.common.constants.BtcTransferConstans;
import com.blockchain.server.cmc.common.constants.UsdtConstans;
import com.blockchain.server.cmc.common.enums.BtcEnums;
import com.blockchain.server.cmc.common.exception.BtcException;
import com.blockchain.server.cmc.common.util.BtcAddressSetRedisUtils;
import com.blockchain.server.cmc.dto.BtcApplicationDTO;
import com.blockchain.server.cmc.dto.BtcTokenDTO;
import com.blockchain.server.cmc.dto.BtcWalletDTO;
import com.blockchain.server.cmc.entity.BtcToken;
import com.blockchain.server.cmc.entity.BtcWallet;
import com.blockchain.server.cmc.entity.BtcWalletTransfer;
import com.blockchain.server.cmc.feign.EthServerFegin;
import com.blockchain.server.cmc.feign.OreFegin;
import com.blockchain.server.cmc.mapper.BtcWalletMapper;
import com.blockchain.server.cmc.rpc.BtcUtils;
import com.blockchain.server.cmc.service.BtcApplicationService;
import com.blockchain.server.cmc.service.BtcTokenService;
import com.blockchain.server.cmc.service.BtcWalletService;
import com.blockchain.server.cmc.service.BtcWalletTransferService;
import com.codingapi.tx.annotation.TxTransaction;
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
public class BtcWalletServiceImpl implements BtcWalletService {
    @Autowired
    private BtcUtils btcUtils;

    @Autowired
    private BtcWalletTransferService btcWalletTransferService;

    @Autowired
    private BtcAddressSetRedisUtils btcAddressSetRedisUtils;

    @Autowired
    private BtcWalletMapper btcWalletMapper;

    @Autowired
    private BtcApplicationService btcApplicationService;

    @Autowired
    private BtcTokenService btcTokenService;

    @Autowired
    private BtcWalletKeyServiceImpl btcWalletKeyService;
    @Autowired
    private EthServerFegin ethServerFegin;

    @Autowired
    private OreFegin oreFegin;
    private static final Logger LOG = LoggerFactory.getLogger(BtcWalletServiceImpl.class);

    @Override
    @Transactional
    public Integer insertWallet(String userOpenId) {
        List<BtcApplicationDTO> btcApplicationDTOList = btcApplicationService.listApplication();
        for (BtcApplicationDTO btcApplicationDTO : btcApplicationDTOList) {
            String address = "";
            try {
                address = btcUtils.getNewAddress();
                String privateKey = btcUtils.getPrivateKeyByAddress(address);
                btcAddressSetRedisUtils.insert(address);
                btcWalletKeyService.insertWalletKey(address, privateKey);
            } catch (Exception e) {
                throw new BtcException(BtcEnums.CREATE_WALLET_ERROR);
            }
            List<BtcTokenDTO> btcTokenDTOList = btcTokenService.listToken();
            for (BtcTokenDTO btcTokenDTO : btcTokenDTOList) {
                BtcWallet btcWallet = new BtcWallet();
                btcWallet.setAddr(address);
                btcWallet.setTokenId(btcTokenDTO.getTokenId());
                btcWallet.setUserOpenId(userOpenId);
                btcWallet.setTokenSymbol(btcTokenDTO.getTokenSymbol());
                btcWallet.setBalance(BigDecimal.ZERO);
                btcWallet.setFreeBalance(BigDecimal.ZERO);
                btcWallet.setFreezeBalance(BigDecimal.ZERO);
                btcWallet.setWalletType(btcApplicationDTO.getAppId());
                btcWallet.setCreateTime(new Date());
                btcWallet.setUpdateTime(btcWallet.getCreateTime());
                LOG.info("******btcWallet******"+btcWallet.toString());
                int count = btcWalletMapper.insertSelective(btcWallet);
                if (count != 1) {
                    throw new BtcException(BtcEnums.CREATE_WALLET_ERROR);
                }
            }
        }

        return 1;
    }

    @Override
    public List<BtcWalletDTO> selectAllByUserOpenId(String userOpenId, String walletType) {
        //校验钱包类型
        btcApplicationService.checkWalletType(walletType);
        return btcWalletMapper.selectAllByUserOpenId(userOpenId, walletType);
    }

    @Override
    public BtcWalletDTO selectByUserOpenId(String userOpenId, Integer tokenId, String walletType) {
        return btcWalletMapper.selectByUserOpenId(userOpenId, tokenId, walletType);
    }

    @Override
    public BtcWalletDTO selectByAddr(String addr, Integer tokenId, String walletType) {
        return btcWalletMapper.selectByAddr(addr, tokenId, walletType);
    }

    @Override
    public Integer updateBalanceByAddrInRowLock(String address, Integer tokenId, BigDecimal freeAmount, BigDecimal freezeAmount, BigDecimal totalAmount, Date modifyTime) {
        return btcWalletMapper.updateBalanceByAddrInRowLock(address, tokenId, freeAmount, freezeAmount, totalAmount, modifyTime);
    }

    @Override
    public Set<String> getAllWalletAddr() {
        return btcWalletMapper.getAllWalletAddr();
    }

    @Override
    @Transactional
    public void updateTxOutError(BtcWalletTransfer btcWalletTransfer) {
        BtcWalletDTO wallet = selectByAddr(btcWalletTransfer.getFromAddr(), btcWalletTransfer.getTokenId(), null);
        Date date = new Date();
        // 业务操作（1）- 恢复提现余额,扣除手续费
        BigDecimal amount = btcWalletTransfer.getAmount();
        this.updateBalanceByAddrInRowLock(wallet.getAddr(), wallet.getTokenId(), amount, amount.negate(), BigDecimal.ZERO, date);
        // 业务操作（2）- 修改记录状态
        btcWalletTransferService.updateStatus(btcWalletTransfer.getId(), BtcTransferConstans.STATUS_FILE, date);
    }

    @Override
    @Transactional
    public void updateTxOutSuccess(BtcWalletTransfer btcWalletTransfer) {
        BtcWalletDTO wallet = selectByAddr(btcWalletTransfer.getFromAddr(), btcWalletTransfer.getTokenId(), null);
        Date date = new Date();
        // 业务操作（1）- 恢复提现余额,扣除手续费
        BigDecimal amount = btcWalletTransfer.getAmount();
        this.updateBalanceByAddrInRowLock(wallet.getAddr(), wallet.getTokenId(), BigDecimal.ZERO, amount.negate(), amount.negate(), date);
        // 业务操作（2）- 修改记录状态
        btcWalletTransferService.updateStatus(btcWalletTransfer.getId(), BtcTransferConstans.STATUS_SUCCESS, date);
    }

    @Override
    public BtcWallet findWallet(String userId, String walletType, String coinName) {
        BtcWallet wallet = new BtcWallet();
        wallet.setWalletType(walletType);
        wallet.setTokenSymbol(coinName);
        wallet.setUserOpenId(userId);
        BtcWallet btcWallet = btcWalletMapper.selectOne(wallet);
        if (btcWallet == null) {
            throw new BtcException(BtcEnums.INEXISTENCE_WALLET);
        }
        return btcWallet;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public ResultDTO handleTransfer(String userId, String fromType, String toType, String coinName, BigDecimal amount) {
        /**
         * 何总项目将usdt-omin换成usdt-erc20，前端改东西太多，这边做统一处理
         * */
        if(coinName!=null&&coinName.equals(UsdtConstans.USDT_SYMBOL)){

            return ethServerFegin.transfer(userId,fromType, toType, coinName, amount);
        }
        Date end = new Date();
        //校验钱包类型
        btcApplicationService.checkWalletType(fromType);
        btcApplicationService.checkWalletType(toType);
        BtcToken btcToken = btcTokenService.selectByTokenName(coinName);
        BtcWallet fromWallet = this.findWallet(userId, fromType, coinName);
        BtcWallet toWallet = this.findWallet(userId, toType, coinName);
        //该用户减去提现可用余额、总额
        int countUb = this.updateBalanceByAddrInRowLock(fromWallet.getAddr(), fromWallet.getTokenId(), amount.negate(), BigDecimal.ZERO, amount.negate(), end);
        if (countUb != 1) {
            throw new BtcException(BtcEnums.WITHDRAW_ERROR);
        }
        //接收用户加上可用余额、总额
        int countUbR = this.updateBalanceByAddrInRowLock(toWallet.getAddr(), toWallet.getTokenId(), amount, BigDecimal.ZERO, amount, end);
        if (countUbR != 1) {
            throw new BtcException(BtcEnums.WITHDRAW_ERROR);
        }

        //并插入一条提现记录，站内快速转账
        BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
        btcWalletTransfer.setId(UUID.randomUUID().toString());
        btcWalletTransfer.setFromAddr(fromWallet.getAddr());
        btcWalletTransfer.setToAddr(toWallet.getAddr());
        btcWalletTransfer.setAmount(amount.abs());
        btcWalletTransfer.setTokenId(btcToken.getTokenId());
        btcWalletTransfer.setTokenSymbol(btcToken.getTokenSymbol());
        btcWalletTransfer.setTransferType(BtcTransferConstans.TYPE_FAST);
        btcWalletTransfer.setStatus(BtcTransferConstans.STATUS_SUCCESS);
        btcWalletTransfer.setCreateTime(end);
        btcWalletTransfer.setUpdateTime(end);
        int countIt = btcWalletTransferService.insertTransfer(btcWalletTransfer);
        if (countIt != 1) {
            throw new BtcException(BtcEnums.WITHDRAW_ERROR);
        }
        return ResultDTO.requstSuccess(btcWalletTransfer);
    }

    @Override
    public List<WalletBalanceDTO> getBalanceByIdAndTypes(String userOpenId, String[] walletTypes) {
        return btcWalletMapper.getBalanceByIdAndTypes(userOpenId, walletTypes);
    }

    @Override
    public void getBalanceByIdAndTypesBatch(String[] walletTypes) {
        LOG.info("开始查询Btc钱包数据");
        Long firDate =System.currentTimeMillis();
        List<WalletBalanceBatchDTO> walletList = btcWalletMapper.getBalanceByIdAndTypesBatch(walletTypes);
        if(walletList==null||walletList.size()==0)
            throw new BtcException(BtcEnums.SERVER_IS_TOO_BUSY);
        Long endSelectDate =System.currentTimeMillis();
        LOG.info("查询Btc钱包信息结束，耗时:"+(firDate-endSelectDate));
        //异步回调
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                int retryCount = 0;
                while (retryCount < 3) {
                    try {
                        LOG.info("Btc钱包信息回调，大小："+walletList.size());
                        oreFegin.pushUserWallet(UserWalletInfoConstant.BTC_WALLET, walletList);
                        Long endDate =System.currentTimeMillis();
                        LOG.info("回调Btc钱包信息结束，耗时:"+(endSelectDate-endDate));
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
