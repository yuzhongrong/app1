package com.blockchain.server.tron.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.constant.UserWalletInfoConstant;
import com.blockchain.common.base.dto.*;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.*;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.common.constant.TronWalletConstants;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.common.util.AsyncOptionUtils;
import com.blockchain.server.tron.dto.PayInsertOutDTO;
import com.blockchain.server.tron.dto.RechargeInDTO;
import com.blockchain.server.tron.dto.RechargeOutDTO;
import com.blockchain.server.tron.dto.RefundOutDTO;
import com.blockchain.server.tron.entity.*;
import com.blockchain.server.tron.feign.EthServerFeign;
import com.blockchain.server.tron.feign.OreFegin;
import com.blockchain.server.tron.feign.UserServerFegin;
import com.blockchain.server.tron.mapper.TronWalletMapper;
import com.blockchain.server.tron.redis.Redis;
import com.blockchain.server.tron.service.*;
import com.blockchain.server.tron.tron.TronUtil;
import com.blockchain.server.tron.tron.ValidateAddressServlet;
import com.codingapi.tx.annotation.ITxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
public class TronWalletServiceImpl implements TronWalletService, ITxTransaction {
    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private TronWalletMapper tronWalletMapper;
    @Autowired
    private TronConfigWalletParamService configWalletParamService;
    @Autowired
    private TronWalletTransferService tronWalletTransferService;
    @Autowired
    private EthServerFeign ethServerFeign;
    @Autowired
    private TronApplicationService tronApplicationService;
    @Autowired
    private TronTokenService tronTokenService;
    @Autowired
    private UserServerFegin userServerFegin;
    @Autowired
    private TronUtil tronUtil;
    @Autowired
    private ValidateAddressServlet validateAddressServlet;
    @Autowired
    private TronInformService tronInformService;
    @Autowired
    private TronWalletKeyInitService tronWalletKeyInitService;
    @Autowired
    private TronWalletKeyService tronWalletKeyService;
    @Autowired
    private TronWalletCreateService tronWalletCreateService;
    @Autowired
    private Redis redis;
    @Autowired
    private AsyncOptionUtils asyncOptionUtils;
    @Autowired
    private OreFegin oreFegin;


    /**
     * 通过16进制地址和币种id查询钱包
     *
     * @param hexAddress
     * @param tokenAddr
     * @return
     */
    @Override
    public TronWallet selectWalletByIdAndTokenName(String hexAddress, String tokenAddr) {
        // 数据验证
        TronWallet where = new TronWallet();
        where.setHexAddr(hexAddress);
        where.setTokenAddr(tokenAddr);
        TronWallet tronWallet = tronWalletMapper.selectOne(where);
        // 爬取用户账号查询会出现异常，禁用该异常抛出
        // if (tronWallet == null) throw new TronWalletException(TronWalletEnums.TRONWALLET_GETWALLET_ERROR);
        return tronWallet;
    }

    /**
     * 用户充值修改钱包金额
     *
     * @param balance
     * @param address
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Integer updateWalletBalanceByAddressInRowLock(BigDecimal balance, String address, String tokenAddr) {
        return tronWalletMapper.updateWalletBalanceByAddrInRowLock(balance, address, tokenAddr, new Date());
    }

    /**
     * 用户提现
     *
     * @param password
     * @param toAddr
     * @param amount
     * @param tokenAddr
     * @param walletType
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public int handleWithdrawDeposit(String userOpenId, String password, String toAddr, BigDecimal amount, String tokenAddr,
                                     String walletType, String verifyCode, String account) {
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId, new TronWalletException(TronWalletEnums.PARAM_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(toAddr, new TronWalletException(TronWalletEnums.PARAM_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(tokenAddr, new TronWalletException(TronWalletEnums.PARAM_ERROR));
        ExceptionPreconditionUtils.checkStringNotBlank(verifyCode, new TronWalletException(TronWalletEnums.WITHDRAW_CODE_NULL));
        if (amount == null) throw new TronWalletException(TronWalletEnums.PARAM_ERROR);
        // 查询是否存在提现黑名单中
        // 抛出错误表示用户禁止提现
        userServerFegin.verifyBanWithdraw(userOpenId);
        TronWallet tronWallet = this.selectWallet(userOpenId, tokenAddr, walletType);
        if (amount.compareTo(tronWallet.getFreeBalance()) > 0)
            throw new TronWalletException(TronWalletEnums.BALANCE_AMOUNT_ERROR);
        ResultDTO resultDTO = ethServerFeign.isPassword(tronWallet.getUserOpenId(), password);
        if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(resultDTO);
        TronToken tronToken = tronTokenService.findByTokenAddr(tokenAddr);

        //检验提现验证码
        ResultDTO verifyWithdrawSms = userServerFegin.verifyWithdrawSms(verifyCode, account);
        if (verifyWithdrawSms == null) {
            throw new TronWalletException(TronWalletEnums.SERVER_IS_TOO_BUSY);
        }
        if (verifyWithdrawSms.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(verifyWithdrawSms);
        }

        // 判断提现地址是否为站内钱包
        TronWallet toWallet = tronWalletMapper.selectByAddr(toAddr, tokenAddr, walletType);
        Date now = new Date();
        // 站内钱包直接快速转账
        if (toWallet != null) {
            tronWalletTransferService.insert(null, tronWallet.getHexAddr(), toWallet.getHexAddr(), amount, tronToken, TronConstant.TransferType.TYPE_FAST);
            int fromRow = tronWalletMapper.updateWalletAllBalanceInRowLock(amount.multiply(new BigDecimal(-1)), amount.multiply(new BigDecimal(-1)), BigDecimal.ZERO, userOpenId, tokenAddr, walletType, now);
            int toRow = tronWalletMapper.updateWalletAllBalanceInRowLock(amount, amount, BigDecimal.ZERO, toWallet.getUserOpenId(), tokenAddr, walletType, now);
            if (fromRow != 1 || toRow != 1) throw new TronWalletException(TronWalletEnums.WITHDRAW_ERROR);
            return 1;
        }
        // 判断用户实名认证
//        ResultDTO<Boolean> result = userServerFegin.determineIdentity(userOpenId);
//        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
//        if (result.getData()) throw new TronWalletException(TronWalletEnums.NO_AUTHENTICATION);

        // base58地址验证并转为hex16进制地址
        toAddr = validateAddress(toAddr);
        if (toAddr == null) throw new TronWalletException(TronWalletEnums.CHECK_ADDRESS_ERROR);

        // 扣除手续费，可用余额转入冻结余额
        // 查询config_wallet_para手续费配置表
        GasDTO gasDTO = configWalletParamService.selectConfigWalletParam(configWalletParamService.selectOne(tronWallet.getTokenSymbol().toLowerCase()));
        // 与最小提现数额做对比
        if (gasDTO.getMinWdAmount().compareTo(amount) > 0) throw new TronWalletException(TronWalletEnums.ERROR);
        // 修改钱包数额
        int updateRow = tronWalletMapper.updateWalletAllBalanceInRowLock(
                BigDecimal.ZERO,
                amount.multiply(new BigDecimal(-1)),
                amount,
                userOpenId,
                tokenAddr,
                walletType,
                now);
        if (updateRow == 0) throw new TronWalletException(TronWalletEnums.WITHDRAW_ERROR);
        // 添加待审核记录
        TronWalletTransfer tronWalletTransfer = new TronWalletTransfer();
        tronWalletTransfer.setId(UUID.randomUUID().toString());
        tronWalletTransfer.setFromHexAddr(tronWallet.getHexAddr());
        tronWalletTransfer.setToHexAddr(toAddr);
        tronWalletTransfer.setAmount(amount);
        tronWalletTransfer.setTokenAddr(tokenAddr);
        tronWalletTransfer.setTokenSymbol(tronWallet.getTokenSymbol());
        tronWalletTransfer.setGasPrice(gasDTO.getGasPrice());
        tronWalletTransfer.setGasTokenName(gasDTO.getGasTokenName());
        tronWalletTransfer.setGasTokenType(gasDTO.getGasTokenType());
        tronWalletTransfer.setGasTokenSymbol(gasDTO.getGasTokenSymbol());
        tronWalletTransfer.setTransferType(TronConstant.TransferType.TRANSFER_OUT);
        tronWalletTransfer.setStatus(TronConstant.TransferStatus.FIRST_TRIAL);
        tronWalletTransfer.setCreateTime(now);
        tronWalletTransfer.setUpdateTime(now);

        NotifyOutSMS notifyOutSMS = new NotifyOutSMS();
        notifyOutSMS.setUserId(userOpenId);
        notifyOutSMS.setAmount(amount.stripTrailingZeros().toPlainString());
        notifyOutSMS.setCoin(tronWallet.getTokenSymbol());
        //异步通知
        asyncOptionUtils.notifyOut(userServerFegin, notifyOutSMS);

        return tronWalletTransferService.insertWalletTransfer(tronWalletTransfer);
    }


    /**
     * 查询钱包
     *
     * @param walletType
     * @param userOpenId
     * @return
     */
    @Override
    public List<TronWallet> listWalletByWalletType(String walletType, String userOpenId) {
        ExceptionPreconditionUtils.notEmpty(userOpenId);
        return tronWalletMapper.listWalletByWalletType(walletType, userOpenId);
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
            throw new TronWalletException(TronWalletEnums.PARAM_ERROR);
        // 判断是否存在该钱包
        TronWallet tronWallet = this.selectWalletByTokenSymbol(walletOrderDTO.getUserId(), walletOrderDTO.getTokenName(), walletOrderDTO.getWalletType());
        Map<String, BigDecimal> map = CheckFreeedBalanceUtil.switchBalance(tronWallet.getFreezeBalance(), freezeBalance, freeBalance);
        if (map != null && map.size() == 2) {
            freezeBalance = map.get(CheckFreeedBalanceUtil.FREEZE);
            freeBalance = map.get(CheckFreeedBalanceUtil.FREE);
        }
        // 对比可用余额和冻结余额
        if (freeBalance.add(tronWallet.getFreeBalance()).compareTo(BigDecimal.ZERO) < 0)
            throw new TronWalletException(TronWalletEnums.BALANCE_AMOUNT_ERROR);
        if (freezeBalance.add(tronWallet.getFreezeBalance()).compareTo(BigDecimal.ZERO) < 0)
            throw new TronWalletException(TronWalletEnums.BALANCE_AMOUNT_ERROR);
        return tronWalletMapper.updateWalletAllBalanceInRowLock(BigDecimal.ZERO, freeBalance, freezeBalance, walletOrderDTO.getUserId(), tronWallet.getTokenAddr(), walletOrderDTO.getWalletType(), new Date());
    }

    /**
     * 通过用户id和币种名称和钱包标识查找用户
     *
     * @param userOpenId
     * @param tokenAddr
     * @return
     */
    @Override
    public TronWallet selectWallet(String userOpenId, String tokenAddr, String walletType) {
        ExceptionPreconditionUtils.notEmpty(walletType);
        Integer count = tronApplicationService.selectWalletCountByWalletType(walletType);
        if (count < 1) throw new TronWalletException(TronWalletEnums.TRONWALLET_GETWALLET_ERROR);
        TronWallet tronWallet = tronWalletMapper.selectWallet(userOpenId, tokenAddr, walletType);
        if (tronWallet == null) throw new TronWalletException(TronWalletEnums.TRONWALLET_GETWALLET_ERROR);
        return tronWallet;
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
        TronWallet tronWallet = this.selectWalletByTokenSymbol(walletChangeDTO.getUserId(), walletChangeDTO.getTokenName(), walletChangeDTO.getWalletType());
        BigDecimal dtoBalance = tronWallet.getBalance();
        BigDecimal dtoFreeBalance = tronWallet.getFreeBalance();
        BigDecimal dtoFreezeBalance = tronWallet.getFreezeBalance();
        // 判断余额是否充足
        if (dtoFreezeBalance.add(freezeBalance).compareTo(BigDecimal.ZERO) < 0)
            throw new TronWalletException(TronWalletEnums.BALANCE_AMOUNT_ERROR);
        if (dtoFreeBalance.add(freeBalance).compareTo(BigDecimal.ZERO) < 0)
            throw new TronWalletException(TronWalletEnums.BALANCE_AMOUNT_ERROR);
//        if (dtoBalance.compareTo(dtoFreeBalance.add(dtoFreezeBalance).add(freeBalance.add(freezeBalance))) < 0)
//            throw new TronWalletException(TronWalletEnums.BALANCE_AMOUNT_ERROR);
        //记录总额改动
        BigDecimal balance = BigDecimal.ZERO;
        Date now = new Date();
        // 添加记录
        TronWalletTransfer tronWalletTransfer = new TronWalletTransfer();
        tronWalletTransfer.setId(UUID.randomUUID().toString());
        tronWalletTransfer.setHash(walletChangeDTO.getRecordId());
        balance = freeBalance.add(freezeBalance);
        // 可用余额增加
        if (balance.compareTo(BigDecimal.ZERO) > 0) tronWalletTransfer.setToHexAddr(tronWallet.getHexAddr());
        else tronWalletTransfer.setFromHexAddr(tronWallet.getHexAddr());
        tronWalletTransfer.setAmount(balance);
        tronWalletTransfer.setTokenAddr(tronWallet.getTokenAddr());
        tronWalletTransfer.setTokenSymbol(tronWallet.getTokenSymbol());
        tronWalletTransfer.setGasPrice(gasBalance.abs());
        tronWalletTransfer.setGasTokenName(tronWallet.getTokenSymbol());
        tronWalletTransfer.setGasTokenSymbol(tronWallet.getTokenSymbol());
        tronWalletTransfer.setGasTokenType(tronWallet.getTokenAddr());
        if (StringUtils.isNotEmpty(walletChangeDTO.getTransferType())) {
            tronWalletTransfer.setTransferType(walletChangeDTO.getTransferType());
        } else {
            tronWalletTransfer.setTransferType(walletChangeDTO.getWalletType());
        }
        tronWalletTransfer.setStatus(TronConstant.TransferStatus.SUCCESS);
        tronWalletTransfer.setCreateTime(now);
        tronWalletTransfer.setUpdateTime(now);
        int insert = tronWalletTransferService.insertWalletTransfer(tronWalletTransfer);
        if (insert != 1) throw new TronWalletException(TronWalletEnums.GET_PUBLICKEY_ERROR);
        return tronWalletMapper.updateWalletAllBalanceInRowLock(balance, freeBalance, freezeBalance, walletChangeDTO.getUserId(), tronWallet.getTokenAddr(), walletChangeDTO.getWalletType(), now);
    }

    /**
     * 初始化钱包方法
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public synchronized Integer initTronWallet(String userId) {
        try {
// 判断是否存在钱包
            ExceptionPreconditionUtils.notEmpty(userId);
            Integer walletCount = tronWalletMapper.selectCountWalletByUserOpenId(userId);
            if (walletCount != 0) throw new TronWalletException(TronWalletEnums.WALLETTOKENT_ADD_ERROR);
            // 获取钱包应用应用
            List<TronApplication> tronApplications = tronApplicationService.listTronApplication();
            // 查询币种地址
            List<TronToken> tronTokens = tronTokenService.listTronToken();
            if (tronTokens == null || tronTokens.size() == 0 || tronApplications == null || tronApplications.size() == 0)
                throw new TronWalletException(TronWalletEnums.CREATION_WALLET_ERROR);
            // 循环应用创建钱包
            for (TronApplication tronApplication : tronApplications) {
                TronWalletKeyInit tronWalletKeyInit = null;
                TronWalletKey tronWalletKey = null;
                int retryCount = 100;
                while (retryCount > 0) {
                    try {
                        //取创建钱包地址
                        tronWalletKeyInit = (TronWalletKeyInit) redis.rightPop(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY);
                        // redis 地址数据为空，单独调用方法创建钱包地址
                        if (tronWalletKeyInit == null) {
                            tronWalletKeyInit = createWalletKeyInit();
                        }
                        String addr = tronWalletKeyInit.getAddr();
                        System.out.println("tronWalletKeyInit.getAddr() -----》》" + addr);
                        tronWalletKey = tronWalletKeyService.selectByAddr(addr);
                        if (tronWalletKey != null) {
                            LOG.info("addr is:"+addr+" tronWalletKey:"+tronWalletKey);
                            tronWalletKeyInit = getAddr(tronWalletKey);
                        }
                        break;
                    } catch (Exception e) {
                        LOG.info("获取地址出错,剩余次数:"+(retryCount-1)+" mess:"+e.getMessage());
                        if (retryCount == 1)
                            throw new TronWalletException(TronWalletEnums.GET_PUBLICKEY_ERROR);
                    }
                    retryCount--;
                }


                redis.setOpsForValue(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY_TIME, tronWalletKeyInit.getCreateTime().getTime());

                // 判断是否位空
                if (tronWalletKey != null) throw new TronWalletException(TronWalletEnums.GET_PUBLICKEY_ERROR);
                // 删除数据初始钱包地址
                tronWalletKeyInitService.deleteByAddr(tronWalletKeyInit.getAddr());
                // 插入钱包地址私钥表
                tronWalletKey = tronWalletKeyService.insert(userId, tronWalletKeyInit.getAddr(), tronWalletKeyInit.getHexAddr(), tronWalletKeyInit.getPrivateKey(), tronWalletKeyInit.getKeystore());
                // 创建钱包
                BigDecimal zero = BigDecimal.ZERO;
                for (TronToken tronToken : tronTokens) {
                    int row = this.insert(tronWalletKey.getAddr(), tronWalletKey.getHexAddr(), tronToken.getTokenAddr(), userId, tronToken.getTokenSymbol(), tronToken.getTokenDecimal(), zero, zero, zero, tronApplication.getAppId());
                    if (row != 1) throw new TronWalletException(TronWalletEnums.CREATE_WALLET_ERROR);
                }
            }
        } catch (TronWalletException t) {
            LOG.info("初始化钱包发生异常:" + t.getMsg());
            throw t;
        } catch (Exception e) {
            LOG.info("初始化钱包发生异常:" + e.getMessage());
            LOG.error("exception happened! detail:{}", ExeptionPrintUtils.getExceptionStackTrace(e));
            throw e;
        }

        return 1;
    }

    /**
     * 获取可用的系统地址（重试10次）
     *
     * @param tronWalletKey
     * @return
     */
    private TronWalletKeyInit getAddr(TronWalletKey tronWalletKey) {

        if (tronWalletKey != null) {
            //代表存在继续redis继续取最左边
            int count = 10;
            while (count > 0) {

                TronWalletKeyInit tronWalletKeyInit = (TronWalletKeyInit) redis.rightPop(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY);
                //判断是否存在如果存在继续循环
                tronWalletKey = tronWalletKeyService.selectByAddr(tronWalletKeyInit.getAddr());
                if (tronWalletKey == null) {

                    return tronWalletKeyInit;
                }
                getAddr(tronWalletKey);
                count--;
            }

        }

        return null;
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
    public TronWallet selectWalletByTokenSymbol(String userId, String tokenSymbol, String walletType) {
        ExceptionPreconditionUtils.notEmpty(walletType);
        Integer count = tronApplicationService.selectWalletCountByWalletType(walletType);
        if (count < 1) throw new TronWalletException(TronWalletEnums.TRONWALLET_GETWALLET_ERROR);
        TronWallet tronWallet = tronWalletMapper.selectWalletByTokenSymbol(userId, tokenSymbol, walletType);
        if (tronWallet == null) throw new TronWalletException(TronWalletEnums.TRONWALLET_GETWALLET_ERROR);
        return tronWallet;
    }

    @Override
    public Boolean getTransaction(String hashId) {
        ExceptionPreconditionUtils.notEmpty(hashId);
        TronWalletTransfer tronWalletTransfer = tronWalletTransferService.selectByHashId(hashId);
        // 调用接口查询出币信息
        return tronUtil.getTransactions(hashId, tronWalletTransfer);
    }


    @Override
    @Transactional
    public void updateTxOutError(TronWalletTransfer tx) {
        TronWallet tronWallet = this.selectWalletByIdAndTokenName(tx.getFromHexAddr(), tx.getTokenAddr());
        Date date = new Date();
        // 业务操作（1）- 恢复提现余额
        BigDecimal amount = tx.getAmount();
        int row = tronWalletMapper.updateWalletAllBalanceInRowLock(BigDecimal.ZERO, amount, amount.negate(), tronWallet.getUserOpenId(), tx.getTokenAddr(), tronWallet.getWalletType(), date);
        if (row == 0) throw new TronWalletException(TronWalletEnums.GET_PUBLICKEY_ERROR);
        // 业务操作（2）- 修改记录状态
        tronWalletTransferService.updateStatus(tx.getId(), TronConstant.TransferStatus.DEFEATED, date);
    }

    @Override
    @Transactional
    public void updateTxOutSuccess(TronWalletTransfer tx) {
        // 数据验证
        TronWallet tronWallet = this.selectWalletByIdAndTokenName(tx.getFromHexAddr(), tx.getTokenAddr());
        Date date = new Date();
        // 业务操作（1）- 扣除提现余额
        BigDecimal amount = tx.getAmount();
        int row = tronWalletMapper.updateWalletAllBalanceInRowLock(amount.negate(), BigDecimal.ZERO, amount.negate(), tronWallet.getUserOpenId(), tronWallet.getTokenAddr(), tronWallet.getWalletType(), date);
        if (row == 0) throw new TronWalletException(TronWalletEnums.GET_PUBLICKEY_ERROR);
        // 业务操作（2）- 修改记录状态
        tronWalletTransferService.updateStatus(tx.getId(), TronConstant.TransferStatus.SUCCESS, date);
    }

    @Override
    public TronWallet findWallet(String userId, String walletType, String coinName) {
        TronWallet where = new TronWallet();
        where.setWalletType(walletType);
        where.setTokenSymbol(coinName);
        where.setUserOpenId(userId);
        TronWallet tronWallet = tronWalletMapper.selectOne(where);
        if (tronWallet == null) {
            throw new TronWalletException(TronWalletEnums.INEXISTENCE_WALLET);
        }
        return tronWallet;
    }

    @Override
    @Transactional
    public TronWalletTransfer handleTransfer(String userOpenId, String fromType, String toType, String coinName, BigDecimal amount) {
        Date end = new Date();
        // 钱包类型验证
        Integer count = tronApplicationService.selectWalletCountByWalletType(fromType);
        if (count < 1) throw new TronWalletException(TronWalletEnums.TRONWALLET_GETWALLET_ERROR);
        count = tronApplicationService.selectWalletCountByWalletType(toType);
        if (count < 1) throw new TronWalletException(TronWalletEnums.TRONWALLET_GETWALLET_ERROR);
        TronToken tronToken = tronTokenService.selectByTokenSymbol(coinName);
        TronWallet fromTronWallet = this.findWallet(userOpenId, fromType, coinName);
        TronWallet toTronWallet = this.findWallet(userOpenId, toType, coinName);
        //该用户减去提现可用余额、总额
        count = tronWalletMapper.updateWalletAllBalanceInRowLock(amount.abs().negate(),
                amount.abs().negate(),
                BigDecimal.ZERO,
                fromTronWallet.getUserOpenId(),
                fromTronWallet.getTokenAddr(),
                fromTronWallet.getWalletType(),
                end);
        if (count == 0) {
            throw new TronWalletException(TronWalletEnums.ERROR);
        }
        //接收用户加上可用余额、总额
        count = tronWalletMapper.updateWalletAllBalanceInRowLock(amount.abs(),
                amount.abs(),
                BigDecimal.ZERO,
                toTronWallet.getUserOpenId(),
                toTronWallet.getTokenAddr(),
                toTronWallet.getWalletType(),
                end);
        if (count == 0) {
            throw new TronWalletException(TronWalletEnums.ERROR);
        }
        // 添加待审核记录
        TronWalletTransfer tronWalletTransfer = new TronWalletTransfer();
        tronWalletTransfer.setId(UUID.randomUUID().toString());
        tronWalletTransfer.setFromHexAddr(fromTronWallet.getHexAddr());
        tronWalletTransfer.setToHexAddr(toTronWallet.getHexAddr());
        tronWalletTransfer.setAmount(amount.abs());
        tronWalletTransfer.setTokenAddr(tronToken.getTokenAddr());
        tronWalletTransfer.setTokenSymbol(tronToken.getTokenSymbol());
        tronWalletTransfer.setTransferType(TronConstant.TransferType.TYPE_FAST);
        tronWalletTransfer.setStatus(TronConstant.TransferStatus.SUCCESS);
        tronWalletTransfer.setCreateTime(end);
        tronWalletTransfer.setUpdateTime(end);
        count = tronWalletTransferService.insertWalletTransfer(tronWalletTransfer);
        if (count == 0) {
            throw new TronWalletException(TronWalletEnums.ERROR);
        }
        return tronWalletTransfer;
    }

    /**
     * 保存账号信息
     *
     * @return
     */
    @Override
    public Integer insert(String addr, String hexAddr, String tokenAddr, String userId, String tokenSymbol, Integer tokenDecimal, BigDecimal balance, BigDecimal freeBalance, BigDecimal freezeBalance, String appId) {
        TronWallet tronWallet = new TronWallet();
        tronWallet.setAddr(addr);
        tronWallet.setHexAddr(hexAddr);
        tronWallet.setTokenAddr(tokenAddr);
        tronWallet.setUserOpenId(userId);
        tronWallet.setTokenSymbol(tokenSymbol);
        tronWallet.setTokenDecimal(tokenDecimal);
        tronWallet.setBalance(balance);
        tronWallet.setFreeBalance(freeBalance);
        tronWallet.setFreezeBalance(freezeBalance);
        Date date = new Date();
        tronWallet.setCreateTime(date);
        tronWallet.setUpdateTime(date);
        tronWallet.setWalletType(appId);
        return tronWalletMapper.insertSelective(tronWallet);
    }

    /**
     * 通过base58地址查询16进制地址
     *
     * @param addr
     * @return
     */
    @Override
    public String selectHexAddressByAddress(String addr) {
        ExceptionPreconditionUtils.notEmpty(addr);
        return tronWalletMapper.selectHexAddrByAddr(addr);
    }

    /**
     * 通过16进制地址查询base58地址
     *
     * @param hexAddress
     * @return
     */
    @Override
    public String selectAddressByHexAddress(String hexAddress) {
        ExceptionPreconditionUtils.notEmpty(hexAddress);
        return tronWalletMapper.selectAddrByHexAddr(hexAddress);
    }

    /**
     * 判断体现地址是否为正确的体现地址
     * 地址错误抛出错误
     * 地址为base58 --> 转为hex16进制地址并重新赋值
     * 地址为bex16直接返回
     *
     * @param address 验证的地址
     * @return String address
     */
    private String validateAddress(String address) {
        JSONObject validAddress = JSONObject.parseObject(validateAddressServlet.validAddress(address));
        if (!validAddress.getBoolean("result"))
            throw new TronWalletException(TronWalletEnums.WITHDRAW_DEPOSIT_ADDRESS_ERROR);
        String message = validAddress.getObject("message", String.class);
        if (message.equalsIgnoreCase(TronConstant.AddressConstant.FORMAT_EOORO))
            throw new TronWalletException(TronWalletEnums.WITHDRAW_DEPOSIT_ADDRESS_ERROR);
        if (message.equalsIgnoreCase(TronConstant.AddressConstant.BASE_64_FORMAT))
            throw new TronWalletException(TronWalletEnums.WITHDRAW_DEPOSIT_ADDRESS_NONSUPPORT);
        if (message.equalsIgnoreCase(TronConstant.AddressConstant.BASE_58_FORMAT))
            return TronUtil.tryToHexAddr(address);
        return address;
    }

    /**
     * 根据用户ID，币种地址，钱包类型查询钱包信息
     *
     * @param openId
     * @param coinAddress
     * @param walletType
     * @return
     */
    @Override
    public TronWallet selectByUserOpenIdAndTokenAddrAndWalletType(String openId, String coinAddress, String walletType) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(openId, new TronWalletException(TronWalletEnums.NULL_USEROPENID));
        // 检查币种地址是否正确
        checkTokenAddr(coinAddress);
        // 验证是否有该应用体系的钱包
        tronApplicationService.checkWalletType(walletType);
        TronWallet tronWalletDTO = tronWalletMapper.selectByUserOpenId(openId, coinAddress, walletType);
        ExceptionPreconditionUtils.checkNotNull(tronWalletDTO, new TronWalletException(TronWalletEnums.INEXISTENCE_WALLET));
        return tronWalletDTO;
    }

    @Override
    public TronWallet selectByAddrAndTokenAddrAndWalletType(String addr, String tokenAddr, String walletType) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(addr, new TronWalletException(TronWalletEnums.NULL_ADDR));
        checkTokenAddr(tokenAddr);
        tronApplicationService.checkWalletType(walletType);
        // 查询数据
        TronWallet wallet = tronWalletMapper.selectByHexAddr(addr, tokenAddr, walletType);
        ExceptionPreconditionUtils.checkNotNull(wallet, new TronWalletException(TronWalletEnums.INEXISTENCE_WALLET));
        return wallet;
    }

    @Override
    @Transactional
    public RefundOutDTO updateRefundInRowLock(TronWalletTransfer tronWalletTransfer, BigDecimal amount) {
        // 修改余额
        TronWallet wallet = this.updateBalanceByAddrMonth(tronWalletTransfer.getFromHexAddr(),
                tronWalletTransfer.getTokenAddr(),
                TronWalletConstants.WalletType.WALLET, amount);
        // 保存退款记录
        Date date = new Date();
        TronToken ethToken = tronTokenService.findByTokenAddr(tronWalletTransfer.getTokenAddr());
        TronWalletTransfer rTx = tronWalletTransferService.insert(UUID.randomUUID().toString(),
                tronWalletTransfer.getToHexAddr(), tronWalletTransfer.getFromHexAddr(), amount,
                ethToken, TronWalletConstants.TransferType.REFUND);
        // 返回数据
        RefundOutDTO refundOutDTO = new RefundOutDTO();
        refundOutDTO.setAmount(amount.toString());
        refundOutDTO.setCoinAddress(rTx.getTokenAddr());
        refundOutDTO.setTimestamp(date.getTime());
        refundOutDTO.setTradeNo(rTx.getFromHexAddr());
        refundOutDTO.setRefundId(rTx.getId());
        return refundOutDTO;
    }

    @Override
    public List<TronWallet> selects(TronWallet where) {
        return tronWalletMapper.select(where);
    }

    @Override
    public RechargeOutDTO updateRechargeInRowLock(RechargeInDTO inParams, String addr) {
        BigDecimal amount = new BigDecimal(inParams.getAmount());
        // 修改余额
        TronWallet wallet = this.updateBalanceByAddrMonth(addr, inParams.getCoinAddress(),
                TronWalletConstants.WalletType.WALLET, amount);
        // 保存退款记录
        TronToken ethToken = tronTokenService.findByTokenAddr(inParams.getCoinAddress());
        TronWalletTransfer tx = tronWalletTransferService.insert(inParams.getTradeNo(), inParams.getAppId(), addr,
                amount, ethToken, TronWalletConstants.TransferType.RECHARGE);
        // 返回数据
        RechargeOutDTO refundOutDTO = new RechargeOutDTO();
        BeanUtils.copyProperties(inParams, refundOutDTO);
        refundOutDTO.setRechargeId(tx.getId());
        refundOutDTO.setTimestamp(tx.getCreateTime().getTime());
        return refundOutDTO;
    }

    /**
     * 内部转账
     *
     * @param formUserOpenId
     * @param tel
     * @param amount
     * @param password
     * @return
     */
    @Override
    public Integer internalTransfer(String formUserOpenId, String tel, String amount, String password) {
        // 判空参数
        ExceptionPreconditionUtils.checkNotNull(formUserOpenId, new TronWalletException(TronWalletEnums.NULL_USEROPENID));
        ExceptionPreconditionUtils.checkNotNull(tel, new TronWalletException(TronWalletEnums.NULL_USEROPENID));
        ExceptionPreconditionUtils.checkNotNull(amount, new TronWalletException(TronWalletEnums.NULL_USEROPENID));
        ExceptionPreconditionUtils.checkNotNull(password, new TronWalletException(TronWalletEnums.NULL_USEROPENID));
        // 获取收款人id
        ResultDTO<String> resultDTO = userServerFegin.getUserIdByPhone(tel);
        if (resultDTO.getCode() != BaseResultEnums.SUCCESS.getCode()) {
            throw new RPCException(resultDTO);
        }
        // 获取两个TRON的托管钱包
        TronWallet formWallet = tronWalletMapper.selectByUserOpenId(formUserOpenId, TronConstant.TRON_TOKEN_ID, TronWalletConstants.WalletType.WALLET);
        TronWallet toWallet = tronWalletMapper.selectByUserOpenId(resultDTO.getData(), TronConstant.TRON_TOKEN_ID, TronWalletConstants.WalletType.WALLET);
        ExceptionPreconditionUtils.checkNotNull(formWallet, new TronWalletException(TronWalletEnums.INEXISTENCE_WALLET));
        ExceptionPreconditionUtils.checkNotNull(toWallet, new TronWalletException(TronWalletEnums.INEXISTENCE_WALLET));
        // 验证密码
        ResultDTO resultPassword = ethServerFeign.isPassword(formUserOpenId, password);
        if (resultPassword.getCode() != BaseConstant.REQUEST_SUCCESS)
            throw new RPCException(resultPassword.getCode(), resultPassword.getMsg());
//        if (!resultPassword.getData()) throw new TronWalletException(TronWalletEnums.TRANSFER_PASSWORD_ERROR);
        // 修改余额
        // 减钱
        int formRow = tronWalletMapper.updateBalanceAllBalanceByAddrInRowLock(formWallet.getHexAddr(), formWallet.getTokenAddr(), formWallet.getWalletType(), new BigDecimal("-" + amount), new BigDecimal("-" + amount), BigDecimal.ZERO);
        // 加钱
        int toRow = tronWalletMapper.updateBalanceAllBalanceByAddrInRowLock(toWallet.getHexAddr(), toWallet.getTokenAddr(), toWallet.getWalletType(), new BigDecimal(amount), new BigDecimal(amount), BigDecimal.ZERO);
        if (formRow == 0 && toRow == 0) throw new TronWalletException(TronWalletEnums.INSERT_PAYINFORM);
        TronWalletTransfer transfer = new TronWalletTransfer();
        String uuid = UUID.randomUUID().toString();
        transfer.setId(uuid);
        transfer.setHash(uuid);
        transfer.setFromHexAddr(formWallet.getHexAddr());
        transfer.setToHexAddr(toWallet.getHexAddr());
        transfer.setTokenAddr(formWallet.getTokenAddr());
        transfer.setAmount(new BigDecimal(amount));
        transfer.setTokenSymbol(formWallet.getTokenSymbol());
        transfer.setTransferType(TronWalletConstants.TransferType.TRANSFER_TYPE_INTX);
        transfer.setStatus(TronWalletConstants.StatusType.SUCCESS);
        Date date = new Date();
        transfer.setCreateTime(date);
        transfer.setUpdateTime(date);
        return tronWalletTransferService.insertWalletTransfer(transfer);
    }

    /**
     * TRON 划转
     *
     * @param tradeNo
     * @param amount
     * @param type
     * @param userOpenId
     * @param transferType
     * @return
     */
    @Override
    public boolean updateUsdtThransferInRowLock(String tradeNo, BigDecimal amount, String type, String userOpenId, String transferType) {
        ExceptionPreconditionUtils.checkStringNotBlank(type, new TronWalletException(TronWalletEnums.ETHWALLET_TRANSFERTYPE_ERROR));
        // 查询用户是否存在托管钱包
        TronWallet wallet = tronWalletMapper.selectByUserOpenId(userOpenId, TronConstant.TRON_TOKEN_ID, TronWalletConstants.WalletType.WALLET);
        if (wallet == null) {
            throw new TronWalletException(TronWalletEnums.ETHWALLET_GETWALLET_ERROR);
        }
        TronToken tronToken = tronTokenService.findByTokenAddr(wallet.getTokenAddr());
        if (TronWalletConstants.C2cTransfer.IN.equalsIgnoreCase(type)) {          // 转入操作
            int state = tronWalletMapper.updateBalanceAllBalanceByAddrInRowLock(wallet.getHexAddr(), wallet.getTokenAddr(), wallet.getWalletType(), amount, amount, BigDecimal.ZERO);
            if (state == 0) {
                throw new TronWalletException(TronWalletEnums.DIGCOIN_ERROR);
            } else {
                // 增加一条OTC划转记录
                tronWalletTransferService.insert(
                        tradeNo,
                        wallet.getHexAddr(),
                        "",
                        amount,
                        tronToken,
                        transferType);
            }
        } else if (TronWalletConstants.C2cTransfer.OUT.equalsIgnoreCase(type)) {  // 转出操作
            int state = tronWalletMapper.updateBalanceAllBalanceByAddrInRowLock(wallet.getHexAddr(), wallet.getTokenAddr(), wallet.getWalletType(), amount, amount, BigDecimal.ZERO);
            if (state == 0) {
                throw new TronWalletException(TronWalletEnums.NUMBER_INSUFFICIENT_ERROR);
            } else {
                // 增加一条OTC划转记录
                tronWalletTransferService.insert(
                        tradeNo,
                        "",
                        wallet.getHexAddr(),
                        amount,
                        tronToken,
                        transferType);
            }
        } else {
            throw new TronWalletException(TronWalletEnums.ETHWALLET_TRANSFERTYPE_ERROR);
        }
        return true;
    }

    @Override
    public List<WalletBalanceDTO> getBalanceByIdAndTypes(String userOpenId, String[] walletTypes) {
        return tronWalletMapper.getBalanceByIdAndTypes(userOpenId, walletTypes);
    }

    @Override
    public void getBalanceByIdAndTypesBatch(String[] walletTypes) {
        LOG.info("开始查询Trx钱包数据");
        Long firDate = System.currentTimeMillis();
        List<WalletBalanceBatchDTO> walletList = tronWalletMapper.getBalanceByIdAndTypesBatch(walletTypes);
        if (walletList == null || walletList.size() == 0)
            throw new TronWalletException(TronWalletEnums.SERVER_IS_TOO_BUSY);
        Long endSelectDate = System.currentTimeMillis();
        LOG.info("查询Trx钱包信息结束，耗时:" + (firDate - endSelectDate));
        //异步回调
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int retryCount = 0;
                while (retryCount < 3) {
                    try {
                        LOG.info("trx钱包信息回调，大小：" + walletList.size());
                        oreFegin.pushUserWallet(UserWalletInfoConstant.TRX_WALLET, walletList);
                        Long endDate = System.currentTimeMillis();
                        LOG.info("回调Trx钱包信息结束，耗时:" + (endSelectDate - endDate));
                        break;
                    } catch (Exception e) {
                        LOG.info("回调失败，回调次数:" + retryCount);
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(30000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    retryCount++;
                }
            }
        });
        th.start();
    }

    @Override
    @Transactional
    public PayInsertOutDTO updatePayInRowLock(TronWalletPrepayment walletPrepayment, String addr) {
        BigDecimal amouont = new BigDecimal(walletPrepayment.getAmount());
        TronWallet wallet = this.updateBalanceByAddrMonth(addr, walletPrepayment.getCoinAddress(), TronWalletConstants.WalletType.WALLET, amouont.negate());
        TronToken ethToken = tronTokenService.findByTokenAddr(walletPrepayment.getCoinAddress());
        // 插入一条ETH的支付记录
        TronWalletTransfer transfer = tronWalletTransferService.insert(walletPrepayment.getId(),
                addr,
                walletPrepayment.getTradeNo(),
                amouont,
                ethToken,
                TronWalletConstants.TransferType.PAY);
        // 返回参数数据
        PayInsertOutDTO outParams = new PayInsertOutDTO();
        outParams.setAmount(transfer.getAmount().toString());
        outParams.setTradeNo(transfer.getToHexAddr());
        outParams.setCoinAddress(transfer.getTokenAddr());
        outParams.setCoinName(transfer.getTokenSymbol());
        outParams.setTransactionId(transfer.getId());
        outParams.setTimestamp(transfer.getCreateTime().getTime());
        outParams.setSign(walletPrepayment.getSign());
        outParams.setOpenId(walletPrepayment.getOpenId());
        // 添加一条异步请求数据
        int informRow = tronInformService.insert(transfer.getId(), JsonUtils.objectToJson(outParams),
                walletPrepayment.getNotifyUrl(),
                TronConstant.InformConstant.TYPE_PAY);
        if (informRow == 0) {
            throw new TronWalletException(TronWalletEnums.INSERT_PAYINFORM);
        }
        outParams.setSign("");
        outParams.setOpenId("");
        return outParams;
    }


    /**
     * 修改指定应用钱包修改余额的简洁方法
     *
     * @param addr
     * @param tokenAddr
     * @param walletType
     * @param amount
     */
    private TronWallet updateBalanceByAddrMonth(String addr, String tokenAddr, String walletType, BigDecimal amount) {
        // 进行行锁转账（应用钱包-应用钱包）
        return this.updateBalanceByAddr(addr, tokenAddr, walletType, amount, BigDecimal.ZERO);
    }

    /**
     * 修改指定应用钱包修改余额的简洁方法
     *
     * @param addr
     * @param tokenAddr
     * @param walletType
     * @param tokenAddr
     * @param walletType
     */
    private TronWallet updateBalanceByAddr(String addr, String tokenAddr, String walletType, BigDecimal freeBlance, BigDecimal freezeBlance) {
        // 数据验证
        ExceptionPreconditionUtils.checkNotNull(freeBlance, new TronWalletException(TronWalletEnums.NULL_FREEBLANCE));
        ExceptionPreconditionUtils.checkNotNull(freezeBlance, new TronWalletException(TronWalletEnums.NULL_FREEZEBLANCE));
        TronWallet wallet = selectByAddrAndTokenAddrAndWalletType(addr, tokenAddr, walletType);
        if (freeBlance.add(wallet.getFreeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new TronWalletException(TronWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
        if (freezeBlance.add(wallet.getFreezeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new TronWalletException(TronWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        }
        // 进行行锁转账（应用钱包-应用钱包）
        int row = tronWalletMapper.updateBalanceAllBalanceByAddrInRowLock(addr, tokenAddr, walletType, freeBlance.add(freezeBlance),
                freeBlance, freezeBlance);
        if (row == 0) {
            throw new TronWalletException(TronWalletEnums.SERVER_IS_TOO_BUSY);
        }
        return selectByAddrAndTokenAddrAndWalletType(addr, tokenAddr, walletType);
    }

    /**
     * 检查币种地址是否正确
     *
     * @param tokenAddr 币种地址
     */
    private TronToken checkTokenAddr(String tokenAddr) {
        ExceptionPreconditionUtils.checkStringNotBlank(tokenAddr, new TronWalletException(TronWalletEnums.NULL_TOKENADDR));
        List<TronToken> list = tronTokenService.selectAll();
        for (TronToken row : list) {
            if (row.getTokenAddr().equalsIgnoreCase(tokenAddr)) {
                return row;
            }
        }
        throw new TronWalletException(TronWalletEnums.INEXISTENCE_TOKENADDR);
    }


    /**
     * 创建地址返回初是地址对象
     *
     * @return
     */
    @Transactional(noRollbackFor = Exception.class)
    public TronWalletKeyInit createWalletKeyInit() {
        TronWalletCreate tronWalletCreate = tronWalletCreateService.selectByOne();
        if (tronWalletCreate == null) throw new TronWalletException(TronWalletEnums.CREATION_WALLET_ERROR);
        // 创建钱包地址
        String dataList = tronUtil.getCreateAccount(tronWalletCreate.getHexAddr(), tronWalletCreate.getPrivateKey());
        // 创建钱包初是地址对象
        TronWalletKeyInit tronWalletKeyInit = new TronWalletKeyInit();
        String[] split = dataList.split(TronConstant.DELIMIT);
        String privateKey = split[0];
        String address = split[1];
        String hexAddress = split[2];
        tronWalletKeyInit.setPrivateKey(RSACoderUtils.encryptPassword(privateKey));
        tronWalletKeyInit.setAddr(address);
        tronWalletKeyInit.setHexAddr(hexAddress);
        tronWalletKeyInit.setCreateTime(new Date());
        tronWalletKeyInitService.insert(tronWalletKeyInit);
        return tronWalletKeyInit;
    }

}
