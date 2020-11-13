package com.blockchain.server.eos.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.constant.UserWalletInfoConstant;
import com.blockchain.common.base.dto.*;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.CheckFreeedBalanceUtil;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.common.util.AsyncOptionUtils;
import com.blockchain.server.eos.common.util.EosUtil;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.entity.Application;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.entity.Wallet;
import com.blockchain.server.eos.entity.WalletTransfer;
import com.blockchain.server.eos.feign.OreFegin;
import com.blockchain.server.eos.feign.UserServerFegin;
import com.blockchain.server.eos.feign.WalletTransferFegin;
import com.blockchain.server.eos.mapper.WalletMapper;
import com.blockchain.server.eos.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Harvey
 * @date 2019/2/18 16:39
 * @user WIN10
 */
@Service
public class EosWalletServiceImpl implements EosWalletService, ITxTransaction {
    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private ConfigWalletParamService configWalletParamService;
    @Autowired
    private EosWalletTransferService eosWalletTransferService;
    @Autowired
    private EosWalletInService eosWalletInService;
    @Autowired
    private WalletTransferFegin walletTransferFegin;
    @Autowired
    private EosApplicationService eosApplicationService;
    @Autowired
    private EosTokenService eosTokenService;
    @Autowired
    private UserServerFegin userServerFegin;
    @Autowired
    private EosUtil eosUtil;
    @Autowired
    private AsyncOptionUtils asyncOptionUtils;
    @Autowired
    private OreFegin oreFegin;

    /**
     * 通过id查询钱包
     *
     * @param walletId
     * @return
     */
    @Override
    public Wallet selectWalletById(String walletId) {
        return walletMapper.selectByPrimaryKey(walletId);
    }

    @Override
    public Wallet selectWalletByIdAndTokenName(Integer walletId, String tokenName) {
        // 数据验证
        Wallet where = new Wallet();
        where.setId(walletId);
        where.setTokenName(tokenName);
        Wallet wallet = walletMapper.selectOne(where);
        if (wallet == null) throw new EosWalletException(EosWalletEnums.EOSWALLET_GETWALLET_ERROR);
        return wallet;
    }

    /**
     * 用户充值修改钱包金额
     *
     * @param balance
     * @param id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int updateWalletBalanceByIdInRowLock(BigDecimal balance, Integer id, Date modifyTime) {
        return walletMapper.updateWalletBalanceByIdInRowLock(balance, id, modifyTime);
    }

    /**
     * 用户提现
     *
     * @param password
     * @param account
     * @param amount
     * @param tokenName
     * @param walletType
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int handleWithdrawDeposit(String userOpenId, String password, String account, BigDecimal amount, String tokenName,
                                     String walletType, String remark, String verifyCode, String username) {
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId, new EosWalletException(EosWalletEnums.PARAM_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(account, new EosWalletException(EosWalletEnums.PARAM_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(tokenName, new EosWalletException(EosWalletEnums.PARAM_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(verifyCode, new EosWalletException(EosWalletEnums.WITHDRAW_CODE_NULL));
        if (amount == null) throw new EosWalletException(EosWalletEnums.PARAM_ERROR);

        if (StringUtils.isNotEmpty(remark)) {
            if (remark.length() > 50) {
                throw new EosWalletException(EosWalletEnums.REMAEKLENGTH_ERROR);
            }
        }

        if (account.length() != 12) throw new EosWalletException(EosWalletEnums.DEPOSIT_ACCOUNT_ERROR);
        // 判断账号是否为平台用户
        WalletDTO wallet = this.selectWallet(userOpenId, tokenName, walletType);
        if (amount.compareTo(wallet.getFreeBalance()) > 0)
            throw new EosWalletException(EosWalletEnums.BALANCE_AMOUNT_ERROR);
        ResultDTO resultDTO = walletTransferFegin.isPassword(password);
        if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(resultDTO);

        //检验提现验证码
        ResultDTO verifyWithdrawSms = userServerFegin.verifyWithdrawSms(verifyCode, username);
        if (verifyWithdrawSms == null) {
            throw new EosWalletException(EosWalletEnums.GET_PUBLICKEY_ERROR);
        }
        if (verifyWithdrawSms.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(verifyWithdrawSms);
        }

        Date date = new Date();
        // 判断是否站内转账
        // 判断提现账户是否为充值账户,站内转账
        if (eosWalletInService.selectByAccountName(account) != null) {
            Wallet eosWallet = walletMapper.selectByPrimaryKey(remark);
            if (eosWallet != null && !eosWallet.getId().equals(wallet.getId()) && eosWallet.getWalletType().equalsIgnoreCase(walletType)) {
                // 站内转账，修改两个用户可用余额，并保存记录
                if (walletMapper.updateWalletBalanceByIdInRowLock(amount.negate(), wallet.getId(), date) != 1 || walletMapper.updateWalletBalanceByIdInRowLock(amount, eosWallet.getId(), date) != 1)
                    throw new EosWalletException(EosWalletEnums.GET_PUBLICKEY_ERROR);
                return eosWalletTransferService.insertWalletTransfer(wallet.getId(),
                        Integer.valueOf(remark),
                        account,
                        amount,
                        tokenName,
                        wallet.getTokenSymbol(),
                        null,
                        null,
                        null,
                        null,
                        EosConstant.TransferType.TYPE_FAST,
                        EosConstant.TransferStatus.SUCCESS,
                        remark,
                        date);
            } else throw new EosWalletException(EosWalletEnums.TRANSFER_ERROR);
        }

        // 查询是否存在提现黑名单中
        // 抛出错误表示用户禁止提现
        userServerFegin.verifyBanWithdraw(userOpenId);
        // 扣除手续费，可用余额转入冻结余额
        // 查询config_wallet_para手续费配置表
        GasDTO gasDTO = configWalletParamService.selectConfigWalletParam(configWalletParamService.selectOne(wallet.getTokenSymbol().toLowerCase()));
        // 与最小提现数额做对比
        if (gasDTO.getMinWdAmount().compareTo(amount) > 0) throw new EosWalletException(EosWalletEnums.ERROR);
        // 判断用户是否存在提现白名单中
        ResultDTO<Boolean> freeWithdraw = userServerFegin.verifyFreeWithdraw(userOpenId);
        if (freeWithdraw.getCode() != BaseConstant.REQUEST_SUCCESS)
            throw new RPCException(freeWithdraw.getCode(), freeWithdraw.getMsg());
        // 用户存在提现白名单中，设置提现手续费为零
        if (freeWithdraw.getData()) gasDTO.setGasPrice(BigDecimal.ZERO);
        Date now = new Date();
        int updateRow = walletMapper.updateWalletAllBalanceInRowLock(
                BigDecimal.ZERO,
                amount.multiply(new BigDecimal(-1)),
                amount,
                userOpenId,
                tokenName,
                walletType,
                now);
        if (updateRow == 0) throw new EosWalletException(EosWalletEnums.WITHDRAW_ERROR);
        // 添加待审核记录
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setFromId(wallet.getId());
        walletTransfer.setAccountName(account);
        walletTransfer.setAmount(amount);
        walletTransfer.setTokenName(tokenName);
        walletTransfer.setTokenSymbol(wallet.getTokenSymbol());
        walletTransfer.setGasPrice(gasDTO.getGasPrice());
        walletTransfer.setGasTokenName(gasDTO.getGasTokenName());
        walletTransfer.setGasTokenType(gasDTO.getGasTokenType());
        walletTransfer.setGasTokenSymbol(gasDTO.getGasTokenSymbol());
        walletTransfer.setTransferType(EosConstant.TransferType.TRANSFER_OUT);
        walletTransfer.setStatus(EosConstant.TransferStatus.FIRST_TRIAL);
        walletTransfer.setTimestamp(now);
        walletTransfer.setRemark(remark);

        NotifyOutSMS notifyOutSMS = new NotifyOutSMS();
        notifyOutSMS.setUserId(userOpenId);
        notifyOutSMS.setAmount(amount.stripTrailingZeros().toPlainString());
        notifyOutSMS.setCoin(wallet.getTokenSymbol());
        //异步通知
        asyncOptionUtils.notifyOut(userServerFegin, notifyOutSMS);

        return eosWalletTransferService.insertWalletTransfer(walletTransfer);
    }


    /**
     * 查询钱包
     *
     * @param walletType
     * @param userOpenId
     * @return
     */
    @Override
    public List<WalletDTO> listWalletByWalletType(String walletType, String userOpenId) {
        ExceptionPreconditionUtils.notEmpty(userOpenId);
        return walletMapper.listWalletByWalletType(walletType, userOpenId);
    }

    /**
     * 冻结与解冻方法
     *
     * @param walletOrderDTO
     * @return
     */
    @Override
    @Transactional
    public Integer updateWalletOrder(WalletOrderDTO walletOrderDTO) {
        ExceptionPreconditionUtils.notEmpty(walletOrderDTO);
        BigDecimal freeBalance = walletOrderDTO.getFreeBalance();
        BigDecimal freezeBalance = walletOrderDTO.getFreezeBalance();
        ExceptionPreconditionUtils.notEmpty(freeBalance);
        ExceptionPreconditionUtils.notEmpty(freezeBalance);
        // 判断传过来的可用余额和冻结余额是否有误
        if (freeBalance.add(freezeBalance).compareTo(BigDecimal.ZERO) != 0)
            throw new EosWalletException(EosWalletEnums.PARAM_ERROR);
        // 判断是否存在该钱包
        WalletDTO walletDTO = this.selectWalletByTokenSymbol(walletOrderDTO.getUserId(), walletOrderDTO.getTokenName(), walletOrderDTO.getWalletType());
        // 对比可用余额和冻结余额
        Map<String, BigDecimal> map = CheckFreeedBalanceUtil.switchBalance(walletDTO.getFreezeBalance(), freezeBalance,freeBalance);
        if(map!=null&&map.size()==2){
            freezeBalance=map.get(CheckFreeedBalanceUtil.FREEZE);
            freeBalance=map.get(CheckFreeedBalanceUtil.FREE);
        }
        if (freeBalance.add(walletDTO.getFreeBalance()).compareTo(BigDecimal.ZERO) < 0)
            throw new EosWalletException(EosWalletEnums.BALANCE_AMOUNT_ERROR);
        if (freezeBalance.add(walletDTO.getFreezeBalance()).compareTo(BigDecimal.ZERO) < 0)
            throw new EosWalletException(EosWalletEnums.BALANCE_AMOUNT_ERROR);
        return walletMapper.updateWalletAllBalanceInRowLock(BigDecimal.ZERO, freeBalance, freezeBalance, walletOrderDTO.getUserId(), walletDTO.getTokenName(), walletOrderDTO.getWalletType(), new Date());
    }

    /**
     * 通过用户id和币种名称和钱包标识查找用户
     *
     * @param userOpenId
     * @param tokenName
     * @return
     */
    @Override
    public WalletDTO selectWallet(String userOpenId, String tokenName, String walletType) {
        ExceptionPreconditionUtils.notEmpty(walletType);
        Integer count = eosApplicationService.selectWalletCountByWalletType(walletType);
        if (count < 1) throw new EosWalletException(EosWalletEnums.EOSWALLET_GETWALLET_ERROR);
        WalletDTO walletDTO = walletMapper.selectWallet(userOpenId, tokenName, walletType);
        if (walletDTO == null) throw new EosWalletException(EosWalletEnums.EOSWALLET_GETWALLET_ERROR);
        return walletDTO;
    }

    /**
     * 钱包余额变动方法
     *
     * @param walletChangeDTO
     * @return
     */
    @Override
    @Transactional
    public Integer handleWalletChange(WalletChangeDTO walletChangeDTO) {
        // 判断参数是否为空
        ExceptionPreconditionUtils.notEmpty(walletChangeDTO);
        BigDecimal freeBalance = walletChangeDTO.getFreeBalance();
        BigDecimal freezeBalance = walletChangeDTO.getFreezeBalance();
        BigDecimal gasBalance = walletChangeDTO.getGasBalance();
        ExceptionPreconditionUtils.notEmpty(freeBalance);
        ExceptionPreconditionUtils.notEmpty(freezeBalance);
        ExceptionPreconditionUtils.notEmpty(gasBalance);
        // 判断是否存在该钱包
        WalletDTO walletDTO = this.selectWalletByTokenSymbol(walletChangeDTO.getUserId(), walletChangeDTO.getTokenName(), walletChangeDTO.getWalletType());
        BigDecimal dtoBalance = walletDTO.getBalance();
        BigDecimal dtoFreeBalance = walletDTO.getFreeBalance();
        BigDecimal dtoFreezeBalance = walletDTO.getFreezeBalance();
        // 判断余额是否充足
        if (dtoFreezeBalance.add(freezeBalance).compareTo(BigDecimal.ZERO) < 0)
            throw new EosWalletException(EosWalletEnums.BALANCE_AMOUNT_ERROR);
        if (dtoFreeBalance.add(freeBalance).compareTo(BigDecimal.ZERO) < 0)
            throw new EosWalletException(EosWalletEnums.BALANCE_AMOUNT_ERROR);
//        if (dtoBalance.compareTo(dtoFreeBalance.add(dtoFreezeBalance).add(freeBalance.add(freezeBalance))) < 0)
//            throw new EosWalletException(EosWalletEnums.BALANCE_AMOUNT_ERROR);
        //记录总额改动
        BigDecimal balance = BigDecimal.ZERO;
        Date now = new Date();
        // 添加记录
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setHash(walletChangeDTO.getRecordId());
        balance = freeBalance.add(freezeBalance);
        // 可用余额增加
        if (balance.compareTo(BigDecimal.ZERO) > 0) walletTransfer.setToId(walletDTO.getId());
        else walletTransfer.setFromId(walletDTO.getId());
        walletTransfer.setAmount(balance);
        walletTransfer.setTokenName(walletDTO.getTokenName());
        walletTransfer.setTokenSymbol(walletDTO.getTokenSymbol());
        walletTransfer.setGasPrice(gasBalance.abs());
        walletTransfer.setGasTokenName(walletDTO.getTokenSymbol());
        walletTransfer.setGasTokenSymbol(walletDTO.getTokenSymbol());
        walletTransfer.setGasTokenType(walletDTO.getTokenName());
        if (StringUtils.isNotEmpty(walletChangeDTO.getTransferType())) {
            walletTransfer.setTransferType(walletChangeDTO.getTransferType());
        } else {
            walletTransfer.setTransferType(walletChangeDTO.getWalletType());
        }
        walletTransfer.setStatus(EosConstant.TransferStatus.SUCCESS);
        walletTransfer.setTimestamp(now);
        int insert = eosWalletTransferService.insertWalletTransfer(walletTransfer);
        if (insert != 1) throw new EosWalletException(EosWalletEnums.GET_PUBLICKEY_ERROR);
        return walletMapper.updateWalletAllBalanceInRowLock(balance, freeBalance, freezeBalance, walletChangeDTO.getUserId(), walletDTO.getTokenName(), walletChangeDTO.getWalletType(), now);
    }

    /**
     * 初始化钱包方法
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Integer initEosWallet(String userId) {
        // 判断是否存在钱包
        ExceptionPreconditionUtils.notEmpty(userId);
        Integer walletCount = walletMapper.selectCountWalletByUserOpenId(userId);
        if (walletCount != 0) throw new EosWalletException(EosWalletEnums.WALLETTOKENT_ADD_ERROR);
        // 获取币种和应用
        List<Application> applications = eosApplicationService.listEosApplication();
        List<Token> tokens = eosTokenService.listEosToken();
        if (applications == null || applications.size() == 0 || tokens == null || tokens.size() == 0)
            throw new EosWalletException(EosWalletEnums.CREATION_WALLET_ERROR);
        // 创建钱包
        BigDecimal zero = BigDecimal.ZERO;
        for (Application application : applications) {
            for (Token token : tokens) {
                Wallet wallet = new Wallet();
                wallet.setTokenName(token.getTokenName());
                wallet.setUserOpenId(userId);
                wallet.setTokenSymbol(token.getTokenSymbol());
                wallet.setBalance(zero.toString());
                wallet.setFreeBalance(zero.toString());
                wallet.setFreezeBalance(zero.toString());
                Date date = new Date();
                wallet.setCreateTime(date);
                wallet.setUpdateTime(date);
                wallet.setWalletType(application.getAppId());
                walletMapper.insertSelective(wallet);
            }
        }

        return 1;
    }

    /**
     * 根据符号查询钱包
     *
     * @param userId
     * @param tokenSymbol
     * @param walletType
     * @return
     */
    @Override
    public WalletDTO selectWalletByTokenSymbol(String userId, String tokenSymbol, String walletType) {
        ExceptionPreconditionUtils.notEmpty(walletType);
        Integer count = eosApplicationService.selectWalletCountByWalletType(walletType);
        if (count < 1) throw new EosWalletException(EosWalletEnums.EOSWALLET_GETWALLET_ERROR);
        WalletDTO walletDTO = walletMapper.selectWalletByTokenSymbol(userId, tokenSymbol, walletType);
        if (walletDTO == null) throw new EosWalletException(EosWalletEnums.EOSWALLET_GETWALLET_ERROR);
        return walletDTO;
    }

    @Override
    public Boolean getTransaction(String blockNum, String hashId) {
        ExceptionPreconditionUtils.notEmpty(blockNum);
        ExceptionPreconditionUtils.notEmpty(hashId);
        WalletTransfer walletTransfer = eosWalletTransferService.selectByHashId(hashId);
        // 调用接口查询出币信息
        return eosUtil.getTransactions(blockNum, hashId, walletTransfer);
    }


    @Override
    @Transactional
    public void updateTxOutError(WalletTransfer tx) {
        Wallet wallet = this.selectWalletByIdAndTokenName(tx.getFromId(), tx.getTokenName());
        Date date = new Date();
        // 业务操作（1）- 恢复提现余额
        BigDecimal amount = tx.getAmount();
        int row = walletMapper.updateWalletAllBalanceInRowLock(BigDecimal.ZERO, amount, amount.negate(), wallet.getUserOpenId(), wallet.getTokenName(), wallet.getWalletType(), date);
        if (row == 0) throw new EosWalletException(EosWalletEnums.GET_PUBLICKEY_ERROR);
        // 业务操作（2）- 修改记录状态
        eosWalletTransferService.updateStatus(tx.getId(), EosConstant.TransferStatus.DEFEATED, date);
    }

    @Override
    @Transactional
    public void updateTxOutSuccess(WalletTransfer tx) {
        // 数据验证
        Wallet wallet = this.selectWalletByIdAndTokenName(tx.getFromId(), tx.getTokenName());
        Date date = new Date();
        // 业务操作（1）- 扣除提现余额
        BigDecimal amount = tx.getAmount();
        int row = walletMapper.updateWalletAllBalanceInRowLock(amount.negate(), BigDecimal.ZERO, amount.negate(), wallet.getUserOpenId(), wallet.getTokenName(), wallet.getWalletType(), date);
        if (row == 0) throw new EosWalletException(EosWalletEnums.GET_PUBLICKEY_ERROR);
        // 业务操作（2）- 修改记录状态
        eosWalletTransferService.updateStatus(tx.getId(), EosConstant.TransferStatus.SUCCESS, date);
    }

    @Override
    public Wallet findWallet(String userId, String walletType, String coinName) {
        Wallet where = new Wallet();
        where.setWalletType(walletType);
        where.setTokenSymbol(coinName);
        where.setUserOpenId(userId);
        Wallet wallet = walletMapper.selectOne(where);
        if (wallet == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLET);
        }
        return wallet;
    }

    @Override
    @Transactional
    public WalletTransfer handleTransfer(String userOpenId, String fromType, String toType, String coinName, BigDecimal amount) {
        Date end = new Date();
        amount = amount.abs();
        // 钱包类型验证
        Integer count = eosApplicationService.selectWalletCountByWalletType(fromType);
        if (count < 1) throw new EosWalletException(EosWalletEnums.EOSWALLET_GETWALLET_ERROR);
        count = eosApplicationService.selectWalletCountByWalletType(toType);
        if (count < 1) throw new EosWalletException(EosWalletEnums.EOSWALLET_GETWALLET_ERROR);
        Token token = eosTokenService.selectByTokenSymbol(coinName);
        Wallet fromWallet = this.findWallet(userOpenId, fromType, coinName);
        Wallet toWallet = this.findWallet(userOpenId, toType, coinName);
        //该用户减去提现可用余额、总额
        count = walletMapper.updateWalletAllBalanceInRowLock(amount.negate(),
                amount.negate(),
                BigDecimal.ZERO,
                fromWallet.getUserOpenId(),
                fromWallet.getTokenName(),
                fromWallet.getWalletType(),
                end);
        if (count == 0) {
            throw new EosWalletException(EosWalletEnums.ERROR);
        }
        //接收用户加上可用余额、总额
        count = walletMapper.updateWalletAllBalanceInRowLock(amount,
                amount,
                BigDecimal.ZERO,
                toWallet.getUserOpenId(),
                toWallet.getTokenName(),
                toWallet.getWalletType(),
                end);
        if (count == 0) {
            throw new EosWalletException(EosWalletEnums.ERROR);
        }
        // 添加待审核记录
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setFromId(fromWallet.getId());
        walletTransfer.setToId(toWallet.getId());
        walletTransfer.setAmount(amount);
        walletTransfer.setTokenName(token.getTokenName());
        walletTransfer.setTokenSymbol(token.getTokenSymbol());
        walletTransfer.setTransferType(EosConstant.TransferType.TYPE_FAST);
        walletTransfer.setStatus(EosConstant.TransferStatus.SUCCESS);
        walletTransfer.setTimestamp(end);
        count = eosWalletTransferService.insertWalletTransfer(walletTransfer);
        if (count == 0) {
            throw new EosWalletException(EosWalletEnums.ERROR);
        }
        return walletTransfer;
    }

    @Override
    public List<WalletBalanceDTO> getBalanceByIdAndTypes(String userOpenId, String[] walletTypes) {
        return walletMapper.getBalanceByIdAndTypes(userOpenId, walletTypes);
    }

    @Override
    public void getBalanceByIdAndTypesBatch(String[] walletTypes) {
        LOG.info("开始查询Eos钱包数据");
        Long firDate =System.currentTimeMillis();
        List<WalletBalanceBatchDTO> walletList = walletMapper.getBalanceByIdAndTypesBatch( walletTypes);

        if(walletList==null||walletList.size()==0)
            throw new EosWalletException(EosWalletEnums.GET_PUBLICKEY_ERROR);
        Long endSelectDate =System.currentTimeMillis();
        LOG.info("查询Eos钱包信息结束，耗时:"+(firDate-endSelectDate));
        //异步回调
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                int retryCount = 0;
                while (retryCount < 3) {
                    try {
                        LOG.info("trx钱包信息回调，大小："+walletList.size());
                        oreFegin.pushUserWallet(UserWalletInfoConstant.EOS_WALLET, walletList);
                        Long endDate =System.currentTimeMillis();
                        LOG.info("回调Eos钱包信息结束，耗时:"+(endSelectDate-endDate));
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
