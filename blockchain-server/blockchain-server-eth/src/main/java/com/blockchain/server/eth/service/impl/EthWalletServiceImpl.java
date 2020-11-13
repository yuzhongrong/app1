package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.constant.UserWalletInfoConstant;
import com.blockchain.common.base.constant.WalletTypeConstants;
import com.blockchain.common.base.dto.*;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.CheckFreeedBalanceUtil;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.eth.common.constants.EthConfigConstants;
import com.blockchain.server.eth.common.constants.EthWalletConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.common.util.AsyncOptionUtils;
import com.blockchain.server.eth.common.util.DataCheckUtil;
import com.blockchain.server.eth.common.util.RedisPrivateUtil;
import com.blockchain.server.eth.dto.EthWalletDTO;
import com.blockchain.server.eth.entity.*;
import com.blockchain.server.eth.feign.OreFegin;
import com.blockchain.server.eth.feign.UserFeign;
import com.blockchain.server.eth.inner.EthWalletTransferInner;
import com.blockchain.server.eth.mapper.EthWalletMapper;
import com.blockchain.server.eth.service.*;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthWalletServiceImpl implements IEthWalletService, ITxTransaction {
    private static final Logger LOG = LoggerFactory.getLogger(EthWalletServiceImpl.class);

    static final String DEFAULT = "";

    @Autowired
    EthWalletMapper ethWalletMapper;
    @Autowired
    IEthTokenService ethTokenService;
    @Autowired
    IEthWalletKeyService ethWalletKeyService;
    @Autowired
    IEthApplicationService ethApplicationService;
    @Autowired
    IEthWalletTransferService ethWalletTransferService;
    @Autowired
    IConfigWalletParamService configWalletParamService;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private AsyncOptionUtils asyncOptionUtils;

    @Autowired
    private OreFegin oreFegin;


    @Autowired
    IWalletWeb3j walletWeb3j;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public EthWallet select(EthWallet ethWallet) {
        return ethWalletMapper.selectOne(ethWallet);
    }

    @Override
    public List<EthWallet> selects(EthWallet ethWallet) {
        return ethWalletMapper.select(ethWallet);
    }

    @Override
    @Transactional
    public EthWalletDTO insert(String userOpenId, String tokenAddr, String walletType, String pass) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        ethApplicationService.CheckWalletType(walletType);
        EthToken coin = checkTokenAddr(tokenAddr); // 获取该币种信息
        String addr = ethWalletKeyService.isPassword(userOpenId, pass); // 钱包密码验证
        // 创建一个没有创建的钱包
        insertMonth(userOpenId, addr, tokenAddr, coin.getTokenDecimals(), coin.getTokenSymbol(), walletType);
        return selectByUserOpenIdAndTokenAddrAndWalletType(userOpenId, tokenAddr, walletType);
    }

    public static void main(String[] args){
        String key="KFLjxFw+6b8CjeIL2rq6xNA2mxnVqmLOrdSWVigESIqk9VT6Ti6O4UGmWgWO/wEqUa7Vm4E/D1LTyZn3r2RCcCDTXqnU2QWqNOeDn8yrGjdXCyW5UeFU+vP8kSAjcEH5NyrI2R7Ui4Ic7Vl6XIgiVe+e45RN4iyQ1R5OAsB4DSg=";
        String privatekey= RSACoderUtils.decryptPassword(key);
        System.out.println("privateKey is:"+privatekey);
       System.out.println("加密私钥:"+ RSACoderUtils.encryptPassword(privatekey));

    }

    @Override
    @Transactional
    public List<EthWalletDTO> insertByWalletTypeAll(String userOpenId, String walletType, String pass) { // 数据格式判断
        // 数据格式验证
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        ethApplicationService.CheckWalletType(walletType);
        String addr = ethWalletKeyService.isPassword(userOpenId, pass); // 钱包密码验证
        // 插入未创建的钱包
        List<EthToken> coins = ethTokenService.selectAll();
        List<EthWalletDTO> list = ethWalletMapper.selectByUserOpenIdAndWalletType(userOpenId, walletType);
        for (EthToken coin : coins) {
            boolean isInsert = true;
            for (EthWalletDTO row : list) {
                if (row.getTokenAddr().equalsIgnoreCase(coin.getTokenAddr())) {
                    isInsert = false;
                }
            }
            if (isInsert) {
                insertMonth(userOpenId, addr, coin.getTokenAddr(), coin.getTokenDecimals(), coin.getTokenSymbol(),
                        walletType);
            }
        }
        // 返回该应用类型的所有钱包
        return ethWalletMapper.selectByUserOpenIdAndWalletType(userOpenId, walletType);
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional
    public void insertInit(String userOpenId) {
        LOG.info("userOpenId is"+userOpenId);
        // 数据格式验证
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        LOG.info("check is ok");
        List<EthApplication> applications = ethApplicationService.selectAll();
        LOG.info("applications is:"+applications);
        for (EthApplication application : applications) {
            // 初始化以太坊钱包主要信息
            EthWalletKey ethWalletKey = ethWalletKeyService.insert(userOpenId);
            LOG.info("ethWalletKey is"+ethWalletKey);
            List<EthToken> coins = ethTokenService.selectAll();
            LOG.info("coins is"+coins);
            if (0 == coins.size() || 0 == applications.size()) {
                throw new EthWalletException(EthWalletEnums.INSERT_INITWALLERT);
            }
            for (EthToken coin : coins) {
                insertMonth(userOpenId, ethWalletKey.getAddr(), coin.getTokenAddr(), coin.getTokenDecimals(),
                        coin.getTokenSymbol(), application.getAppId());
            }
        }
    }


    @Override
    public EthWalletDTO selectByUserOpenIdAndTokenAddrAndWalletType(String userOpenId, String tokenAddr,
                                                                    String walletType) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        checkTokenAddr(tokenAddr);
        ethApplicationService.CheckWalletType(walletType);
        // 查询数据
        EthWalletDTO walletDTO = ethWalletMapper.selectByUserOpenIdAndTokenAddrAndWalletType(userOpenId, tokenAddr,
                walletType);
        ExceptionPreconditionUtils.checkNotNull(walletDTO, new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET));
        return walletDTO;
    }

    @Override
    public EthWalletDTO selectByAddrAndTokenAddr(String addr, String tokenAddr) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(addr, new EthWalletException(EthWalletEnums.NULL_ADDR));
        checkTokenAddr(tokenAddr);
        // 查询数据
        EthWalletDTO walletDTO = ethWalletMapper.selectByAddrAndTokenAddr(addr, tokenAddr);
        ExceptionPreconditionUtils.checkNotNull(walletDTO, new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET));
        return walletDTO;
    }

    @Override
    public EthWalletDTO selectByAddrAndTokenAddrAndWalletType(String addr, String tokenAddr, String walletType) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(addr, new EthWalletException(EthWalletEnums.NULL_ADDR));
        checkTokenAddr(tokenAddr);
        ethApplicationService.CheckWalletType(walletType);
        // 查询数据
        EthWalletDTO walletDTO = ethWalletMapper.selectByAddrAndTokenAddrAndWalletType(addr, tokenAddr, walletType);
        ExceptionPreconditionUtils.checkNotNull(walletDTO, new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET));
        return walletDTO;
    }

    @Override
    public List<EthWalletDTO> selectByUserOpenIdAndWalletType(String userOpenId, String walletType) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        ethApplicationService.CheckWalletType(walletType);
        // 查询数据
        List<EthWalletDTO> list = ethWalletMapper.selectByUserOpenIdAndWalletType(userOpenId, walletType);
        return list;
    }

    @Override
    public List<EthWalletDTO> selectByAddrAndWalletType(String addr, String walletType) {
        // 数据格式判断
        ExceptionPreconditionUtils.checkStringNotBlank(addr, new EthWalletException(EthWalletEnums.NULL_ADDR));
        ethApplicationService.CheckWalletType(walletType);
        // 查询数据
        List<EthWalletDTO> list = ethWalletMapper.selectByAddrAndWalletType(addr, walletType);
        return list;
    }

    @Override
    public String getPublicKey(String userOpenId) {
        return RedisPrivateUtil.getPublicKey(userOpenId, redisTemplate);
    }

    @Override
    public void isPassword(String userOpenId, String password) {
        ethWalletKeyService.isPassword(userOpenId, password);
    }

    @Override
    @Transactional
    public void updatePassword(String userOpenId, String password) {
        ethWalletKeyService.updatePassword(userOpenId, password);
    }

    @Override
    @Transactional
    public EthWalletTransfer updateTypeTransfer(String userOpenId, String tokenAddr, String formWalletType,
                                                String toWalletType, String amount, String password) {
        // #1-数据验证
        EthToken amountCoin = checkTokenAddr(tokenAddr);
        ethApplicationService.CheckWalletType(formWalletType);
        ethApplicationService.CheckWalletType(toWalletType);
        if (formWalletType.equalsIgnoreCase(toWalletType)) {
            throw new EthWalletException(EthWalletEnums.EQWALLERTTYPE_ERROR);
        }
        ethWalletKeyService.isPassword(userOpenId, password);
        Date date = new Date();
        BigDecimal toAmount = DataCheckUtil.strToBigDecimal(amount); // 应用接收方增加的余额（字符串转数字类型）
        BigDecimal formAmount = toAmount.multiply(BigDecimal.valueOf(-1)); // 应用支付方减少的余额
        // #2-进行行锁转账（应用钱包-应用钱包）
        EthWalletDTO formWallet =
                updateBalanceByUserIdMonth(userOpenId, tokenAddr, formWalletType, formAmount, date); //
        // 支付应用的钱包扣减
        EthWalletDTO toWallet = updateBalanceByUserIdMonth(userOpenId, tokenAddr, toWalletType, toAmount, date);  //
        // 接收应用的钱包增加
        // #3-插入一条转账记录
        return ethWalletTransferService.insert(UUID.randomUUID().toString(), formWallet.getAddr(), toWallet.getAddr()
                , toAmount, amountCoin, EthWalletConstants.TransferType.TXMIN, date);
    }

    @Override
    @Transactional
    public void updateBlanceTxIn(EthWalletTransfer ethWalletTransfer) {
        // 数据验证
        ExceptionPreconditionUtils.checkNotNull(ethWalletTransfer, new EthWalletException(EthWalletEnums.NULL_TXIN));
        EthWalletDTO wallet = selectByAddrAndTokenAddr(ethWalletTransfer.getToAddr(), ethWalletTransfer.getTokenAddr());
        Date date = new Date();
        // 业务操作（1）- 修改钱包余额
        updateBalanceByAddrMonth(wallet.getAddr(), wallet.getTokenAddr(), wallet.getWalletType(),
                ethWalletTransfer.getAmount(), date);
        // 业务操作（2）- 修改记录状态
        ethWalletTransferService.updateStatus(ethWalletTransfer.getId(), EthWalletConstants.StatusType.IN_SUCCESS,
                date);
    }

    @Override
    @Transactional
    public EthWalletTransfer handleWelletOutApply(String userOpenId, String tokenAddr, String toAddr, String walletType,
                                                  String amount, String password, String verifyCode, String account) {
        ExceptionPreconditionUtils.checkStringNotBlank(toAddr, new EthWalletException(EthWalletEnums.NULL_OUT_TOADDR));
        ExceptionPreconditionUtils.checkStringNotBlank(verifyCode, new EthWalletException(EthWalletEnums.WITHDRAW_CODE_NULL));
        if (toAddr.length() != EthWalletConstants.WALLERT_LENGTH) {
            throw new EthWalletException(EthWalletEnums.OUT_TOADDR_ERROR);
        }
        toAddr = toAddr.toLowerCase();
        EthWalletDTO wallet = selectByUserOpenIdAndTokenAddrAndWalletType(userOpenId, tokenAddr, walletType); // 数据验证
        ethWalletKeyService.isPassword(wallet.getUserOpenId(), password); // 验证密码
        //验证提现短信
        ResultDTO resultDTO = userFeign.verifyWithdrawSms(verifyCode, account);
        if (resultDTO == null) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
        if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(resultDTO);
        }

        // 查询是否存在提现黑名单中
        // 抛出错误表示用户禁止提现
        userFeign.verifyBanWithdraw(userOpenId);

        Date date = new Date();
        Set<String> addrs = ethWalletKeyService.selectAddrs();// 获取所有的用户地址
        Map<String, EthToken> coins = ethTokenService.selectMaps(); // 查询所有币种
        EthWalletTransfer tx;
        BigDecimal amountNumber = DataCheckUtil.strToBigDecimal(amount); // 转化余额格式
        if (addrs.contains(toAddr)) {  // 站内转账
            int outRow = ethWalletMapper.updateBalanceByAddrInRowLock(wallet.getAddr(), wallet.getTokenAddr(),
                    walletType, amountNumber.negate(), amountNumber.negate(), BigDecimal.ZERO, date); // 减少余额操作
            if (outRow == 0) {
                throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENT_ERROR);
            }
            int inRow = ethWalletMapper.updateBalanceByAddrInRowLock(toAddr, wallet.getTokenAddr(),
                    walletType, amountNumber, amountNumber, BigDecimal.ZERO, date); // 增加余额操作
            if (inRow == 0) {
                throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENT_ERROR);
            }
            tx = ethWalletTransferService.insert(UUID.randomUUID().toString(), wallet.getAddr(), toAddr,
                    amountNumber, coins.get(wallet.getTokenAddr()), EthWalletConstants.TransferType.FAST,
                    EthWalletConstants.StatusType.OUT_SUCCESS, date); // 快速到账记录
        } else {
            // 查询手续费操作
            GasDTO ethGasDTO = configWalletParamService.getGasConfig(wallet.getTokenSymbol());
            // 判断用户是否存在提现白名单中
            ResultDTO<Boolean> resultDto = userFeign.verifyFreeWithdraw(userOpenId);
            if (resultDto.getCode() != BaseConstant.REQUEST_SUCCESS)
                throw new RPCException(resultDto.getCode(), resultDto.getMsg());
            // 用户存在提现白名单中，设置提现手续费为零
            if (resultDto.getData()) ethGasDTO.setGasPrice(BigDecimal.ZERO);
            // 增加提现申请记录，冻结提现余额
            // ——冻结余额
            if (ethGasDTO.getMinWdAmount().compareTo(amountNumber) > 0) { // 与最小提现数额做对比
                throw new EthWalletException(EthWalletEnums.NUMBER_MINWDAMOUNT_ERROR);
            }
            int row = ethWalletMapper.updateBalanceByAddrInRowLock(wallet.getAddr(), wallet.getTokenAddr(), walletType,
                    BigDecimal.ZERO, amountNumber.negate(), amountNumber, date); // 冻结余额操作
            if (row == 0) {
                throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENT_ERROR);
            }
            // ——生成提现申请记录
            if (ethGasDTO == null) {
                LOG.info("from addr" + wallet.getAddr() + " gas is null");
                tx = ethWalletTransferService.insert(UUID.randomUUID().toString(), wallet.getAddr(), toAddr,
                        amountNumber, coins.get(wallet.getTokenAddr()), EthWalletConstants.TransferType.OUT,
                        EthWalletConstants.StatusType.OUT_LOAD1, date);
            } else {// ethGasDTO = 0xc26535d70f79c7c122475ae8a3345a25b329195f //0xf6fe745e5647298639072d942e0eb4f2e3930ecb
                BigDecimal gas = ethGasDTO.getGasPrice(); // 格式化手续费
                LOG.info("from addr" + wallet.getAddr() + " gas:" + gas);
                String tokenAdr="";
                if(ethGasDTO.getGasTokenType().equals(EthConfigConstants.MODULE_TYPE_USDT)){
                    tokenAdr=EthConfigConstants.USDT_TOKENADDR;
                }else if(ethGasDTO.getGasTokenType().equals(EthConfigConstants.MODULE_TYPE_GFC)){
                    tokenAdr=EthConfigConstants.GFC_TOKENADDR;
                }
                else{
                    tokenAdr=EthConfigConstants.MODULE_TYPE;
                }
                tx = ethWalletTransferService.insert(UUID.randomUUID().toString(), wallet.getAddr(), toAddr,
                        amountNumber,
                        gas, coins.get(wallet.getTokenAddr()), coins.get(tokenAdr),
                        EthWalletConstants.TransferType.OUT, EthWalletConstants.StatusType.OUT_LOAD1, date);
            }
        }

        NotifyOutSMS notifyOutSMS = new NotifyOutSMS();
        notifyOutSMS.setUserId(userOpenId);
        notifyOutSMS.setAmount(amount);
        notifyOutSMS.setCoin(wallet.getTokenSymbol());
        //异步通知
        asyncOptionUtils.notifyOut(userFeign, notifyOutSMS);

        return tx;
    }

    @Override
    @TxTransaction
    @Transactional
    public void updateBlanceTransform(WalletOrderDTO orderDTO) {
        ExceptionPreconditionUtils.checkNotNull(orderDTO, new EthWalletException(EthWalletEnums.NULL_ERROR));
        EthToken ethToken = ethTokenService.findByTokenName(orderDTO.getTokenName());
        ExceptionPreconditionUtils.checkNotNull(orderDTO.getFreeBalance(),
                new EthWalletException(EthWalletEnums.NULL_FREEBLANCE));
        ExceptionPreconditionUtils.checkNotNull(orderDTO.getFreezeBalance(),
                new EthWalletException(EthWalletEnums.NULL_FREEZEBLANCE));
        if (orderDTO.getFreeBalance().compareTo(orderDTO.getFreezeBalance().negate()) != 0) {
            throw new EthWalletException(EthWalletEnums.DATA_EXCEPTION_ERROR);
        }
        this.updateBalanceByUserOpenId(orderDTO.getUserId(), ethToken.getTokenAddr(), orderDTO.getWalletType(),
                orderDTO.getFreeBalance(), orderDTO.getFreezeBalance(), new Date());
    }

    @Override
    @TxTransaction
    @Transactional
    public void updateBlance(WalletChangeDTO changeDTO) {
        ExceptionPreconditionUtils.checkNotNull(changeDTO, new EthWalletException(EthWalletEnums.NULL_ERROR));
        EthToken ethToken = ethTokenService.findByTokenName(changeDTO.getTokenName());
        Date date = new Date();
        EthWalletDTO ethWalletDTO = this.updateBalanceByUserOpenId(changeDTO.getUserId(), ethToken.getTokenAddr(),
                changeDTO.getWalletType(), changeDTO.getFreeBalance(), changeDTO.getFreezeBalance(), date);
        BigDecimal count = changeDTO.getFreeBalance().add(changeDTO.getFreezeBalance());
        //设置交易类型
        if (StringUtils.isEmpty(changeDTO.getTransferType())) {
            changeDTO.setTransferType(changeDTO.getWalletType());
        }
        if (count.compareTo(BigDecimal.ZERO) >= 0) {
            ethWalletTransferService.insert(changeDTO.getRecordId(), DEFAULT, ethWalletDTO.getAddr(), count.abs(),
                    ethToken, changeDTO.getTransferType(), date);
        } else {
            ethWalletTransferService.insert(changeDTO.getRecordId(), ethWalletDTO.getAddr(), DEFAULT, count.abs(),
                    ethToken, changeDTO.getTransferType(), date);
        }
    }

    @Override
    @Transactional
    public void updateTxOutError(EthWalletTransfer tx) {
        // 数据验证
        EthWalletDTO wallet = selectByAddrAndTokenAddr(tx.getFromAddr(), tx.getTokenAddr());
        Date date = new Date();
        // 业务操作（1）- 恢复提现余额
        BigDecimal amount = tx.getAmount();
        this.updateBalanceByAddr(wallet.getAddr(), wallet.getTokenAddr(), wallet.getWalletType(), amount, amount.negate(), date);
        // 业务操作（2）- 修改记录状态
        ethWalletTransferService.updateStatus(tx.getId(), EthWalletConstants.StatusType.OUT_ERROR, date);
    }


    @Override
    @Transactional
    public void updateTxOutSuccess(EthWalletTransfer tx) {
        // 数据验证
        EthWalletDTO wallet = selectByAddrAndTokenAddr(tx.getFromAddr(), tx.getTokenAddr());
        Date date = new Date();
        // 业务操作（1）- 扣除提现余额
        BigDecimal amount = tx.getAmount();
        this.updateBalanceByAddr(wallet.getAddr(), wallet.getTokenAddr(), wallet.getWalletType(), BigDecimal.ZERO, amount.negate(), date);
        // 业务操作（2）- 修改记录状态
        ethWalletTransferService.updateStatus(tx.getId(), EthWalletConstants.StatusType.OUT_SUCCESS, date);
    }


    @Override
    @Transactional
    @TxTransaction
    public EthWalletTransfer handleTransfer(String userId, String fromType, String toType, String coinName, BigDecimal amount) {
        Date end = new Date();
        amount = amount.abs();
        //数据验证处理
        ethApplicationService.CheckWalletType(fromType);
        ethApplicationService.CheckWalletType(toType);
        EthToken token = ethTokenService.findByTokenName(coinName);
        //錢包查詢處理
        EthWallet where = new EthWallet();
        where.setTokenSymbol(coinName);
        where.setWalletType(fromType);
        where.setUserOpenId(userId);
        EthWallet fromWallet = ethWalletMapper.selectOne(where);
        where.setWalletType(toType);
        EthWallet toWallet = ethWalletMapper.selectOne(where);
        //余额修改
        this.updateBalanceByAddr(fromWallet.getAddr(), token.getTokenAddr(), fromType, amount.negate(), BigDecimal.ZERO, end);
        this.updateBalanceByAddr(toWallet.getAddr(), token.getTokenAddr(), toType, amount, BigDecimal.ZERO, end);
        // 记录
        EthWalletTransfer tx = ethWalletTransferService.insert(UUID.randomUUID().toString(), fromWallet.getAddr(), toWallet.getAddr(),
                amount, token, EthWalletConstants.TransferType.FAST,
                EthWalletConstants.StatusType.OUT_SUCCESS, end); // 快速到账记录
        return tx;
    }

    @Override
    public List<WalletBalanceDTO> getBalanceByIdAndTypes(String userOpenId, String[] walletTypes) {
        return ethWalletMapper.getBalanceByIdAndTypes(userOpenId, walletTypes);
    }

    @Override
    public BigDecimal getAllBalanceByToken(String userOpenId, String coinName) {
        return ethWalletMapper.getAllBalanceByToken(userOpenId, coinName);
    }

    @Override
    public void updateBalanceByAddrInRowLock(String addr, String tokenAddr, String walletType, BigDecimal freeBlance,
                                             BigDecimal freezeBlance, Date date) {
        this.updateBalanceByAddr(addr, tokenAddr, walletType, freeBlance, freezeBlance, date);
    }

    @Override
    public void getBalanceByIdAndTypesBatch(String[] walletTypes) {
        LOG.info("开始查询Eth钱包数据");
        Long firDate = System.currentTimeMillis();
        List<WalletBalanceBatchDTO> walletList = ethWalletMapper.getBalanceByIdAndTypesBatch(walletTypes);
        if (walletList == null || walletList.size() == 0)
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        Long endSelectDate = System.currentTimeMillis();
        LOG.info("查询Eth钱包信息结束，耗时:" + (firDate - endSelectDate));
        //异步回调
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                int retryCount = 0;
                while (retryCount < 3) {
                    try {
                        LOG.info("trx钱包信息回调，大小：" + walletList.size());
                        oreFegin.pushUserWallet(UserWalletInfoConstant.ETH_WALLET, walletList);
                        Long endDate = System.currentTimeMillis();
                        LOG.info("回调Eth钱包信息结束，耗时:" + (endSelectDate - endDate));
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
    public void updateBatchBlance(List<WalletChangeDTO> batchChangeList) {
        List<WalletChangeDTO> errorList = new ArrayList<>();
        inserSingle(errorList, batchChangeList);
        //回调ore模块
        if (errorList.size() > 0) {
            LOG.info("改变钱包持币量失败数:" + errorList.size());
            oreFegin.pushErrorInserWallet(errorList);
        }
    }

    @Override
    public String GFCRecharge(String telPhone, String amount, String checkCode) {
        if(amount==null){
            throw new EthWalletException(EthWalletEnums.NULL_AMOUNT);
        }
        if(new BigDecimal(amount).compareTo(BigDecimal.ZERO)<=0){
            throw new EthWalletException(EthWalletEnums.NUMBER_COUNT_ERROR);
        }
        //检查用户
        UserInfoDTO userInfoDTO=null;
        try {
            userInfoDTO= userFeign.getUserInfo(telPhone,null).getData();
        }catch (Exception e){
            e.printStackTrace();
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }

        if(userInfoDTO==null){
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        String userId=userInfoDTO.getUserId();
        EthWallet ethWallet=new EthWallet();
        ethWallet.setUserOpenId(userId);
        ethWallet.setTokenSymbol(EthWalletConstants.TokenSymbolConstants.GFC);
        ethWallet.setWalletType(WalletTypeConstants.CCT);
        EthWallet userWallet = ethWalletMapper.selectOne(ethWallet);
        if(userWallet==null){
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        WalletChangeDTO walletChangeDTO=new WalletChangeDTO();
        walletChangeDTO.setUserId(userId);
        walletChangeDTO.setWalletType(WalletTypeConstants.CCT);
        walletChangeDTO.setRecordId(UUID.randomUUID().toString());
        walletChangeDTO.setGasBalance(BigDecimal.ZERO);
        walletChangeDTO.setTokenName(EthWalletConstants.TokenSymbolConstants.GFC);
        walletChangeDTO.setFreeBalance(new BigDecimal(amount));
        walletChangeDTO.setFreezeBalance(BigDecimal.ZERO);
        walletChangeDTO.setTransferType(EthWalletConstants.TransferType.IN);
        this.updateBlance(walletChangeDTO);
        return walletChangeDTO.getRecordId();
    }

    @Override
    public void checkPass(String telPhone, String checkCode) {
        ethWalletKeyService.checkPass(telPhone, checkCode);
    }

    @Override
    public String getWalletPublicKey(String userName) {
        return RedisPrivateUtil.getWalletPublicKey(userName, redisTemplate);
    }

    private void inserBatch(List<WalletChangeDTO> errorList, List<WalletChangeDTO> batchChangeList) {
        EthToken ethToken = ethTokenService.findByTokenName(EthWalletConstants.TokenSymbolConstants.FK);
        Date date = new Date();
//        EthWalletDTO ethWalletDTO = this.updateBalanceByUserOpenId(changeDTO.getUserId(), ethToken.getTokenAddr(),
//                changeDTO.getWalletType(), changeDTO.getFreeBalance(), changeDTO.getFreezeBalance(), date);
//        BigDecimal count = changeDTO.getFreeBalance().add(changeDTO.getFreezeBalance());
//        //设置交易类型
//        if (StringUtils.isEmpty(changeDTO.getTransferType())) {
//            changeDTO.setTransferType(changeDTO.getWalletType());
//        }
//            ethWalletTransferService.insert(changeDTO.getRecordId(), DEFAULT, ethWalletDTO.getAddr(), count.abs(),
//                    ethToken, changeDTO.getTransferType(), date);

    }

    private void inserSingle(List<WalletChangeDTO> errorList, List<WalletChangeDTO> batchChangeList) {
        int count = 0;
        for (WalletChangeDTO changeDTO : batchChangeList) {
            int retryCount = 0;
            while (retryCount < 2) {
                try {
                    this.updateBlance(changeDTO);
                    break;
                } catch (Exception e) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException i) {
                        i.printStackTrace();
                    }
                    e.printStackTrace();
                }
                retryCount++;
                if (retryCount == 2) {
                    //将入库失败数据回调给ore
                    errorList.add(changeDTO);
                }
            }
            if (count % 200 == 0) {
                LOG.info("改变钱包当前量：" + count + " 总量" + batchChangeList.size());
            }
            count++;
        }
    }


    /**
     * 检查币种地址是否正确
     *
     * @param tokenAddr 币种地址
     */
    private EthToken checkTokenAddr(String tokenAddr) {
        ExceptionPreconditionUtils.checkStringNotBlank(tokenAddr,
                new EthWalletException(EthWalletEnums.NULL_TOKENADDR));
        List<EthToken> list = ethTokenService.selectAll();
        for (EthToken row : list) {
            if (row.getTokenAddr().equalsIgnoreCase(tokenAddr)) {
                return row;
            }
        }
        throw new EthWalletException(EthWalletEnums.INEXISTENCE_TOKENADDR);
    }

    /**
     * 创建某应用的单个钱包简洁方法
     *
     * @param userOpenId    用户ID
     * @param addr          钱包地址
     * @param tokenAddr     币种地址
     * @param tokenDecimals 小数位
     * @param tokenSymbol   全称
     * @param walletType    钱包类型
     * @return
     */
    private void insertMonth(String userOpenId, String addr, String tokenAddr, int tokenDecimals, String tokenSymbol,
                             String walletType) {
        Date date = new Date();
        EthWallet ethWallet = new EthWallet();
        ethWallet.setAddr(addr);
        ethWallet.setTokenAddr(tokenAddr.toLowerCase());
        ethWallet.setBalance(BigDecimal.ZERO);
        ethWallet.setFreeBalance(BigDecimal.ZERO);
        ethWallet.setFreezeBalance(BigDecimal.ZERO);
        ethWallet.setTokenDecimals(tokenDecimals);
        ethWallet.setUserOpenId(userOpenId);
        ethWallet.setTokenSymbol(tokenSymbol);
        ethWallet.setWalletType(walletType);
        ethWallet.setCreateTime(date);
        ethWallet.setUpdateTime(date);
        try {
            LOG.info("insert ethWallet"+ethWallet);
            int ethWalletInt = ethWalletMapper.insertSelective(ethWallet);
            LOG.info("insert is ok"+ethWalletInt);
            if (ethWalletInt == 0) {
                throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
            }
        } catch (EthWalletException e) {
            // 未插入成功的异常
            throw e;
        } catch (Exception e) {
            // 重复创建的异常
            throw new EthWalletException(EthWalletEnums.INSERT_ADDWALLERT);
        }
    }

    /**
     * 修改指定应用钱包修改余额的简洁方法
     *
     * @param userOpenId
     * @param tokenAddr
     * @param walletType
     * @param amount
     */
    private EthWalletDTO updateBalanceByUserIdMonth(String userOpenId, String tokenAddr, String walletType,
                                                    BigDecimal amount, Date date) {
        return this.updateBalanceByUserOpenId(userOpenId, tokenAddr, walletType, amount, BigDecimal.ZERO, date);
    }

    /**
     * 修改指定应用钱包修改余额的简洁方法
     *
     * @param addr
     * @param tokenAddr
     * @param walletType
     * @param amount
     */
    private EthWalletDTO updateBalanceByAddrMonth(String addr, String tokenAddr, String walletType, BigDecimal amount
            , Date date) {
        // 进行行锁转账（应用钱包-应用钱包）
        return this.updateBalanceByAddr(addr, tokenAddr, walletType, amount, BigDecimal.ZERO, date);
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
    private EthWalletDTO updateBalanceByAddr(String addr, String tokenAddr, String walletType, BigDecimal freeBlance,
                                             BigDecimal freezeBlance, Date date) {
        // 数据验证
        ExceptionPreconditionUtils.checkNotNull(freeBlance, new EthWalletException(EthWalletEnums.NULL_FREEBLANCE));
        ExceptionPreconditionUtils.checkNotNull(freezeBlance, new EthWalletException(EthWalletEnums.NULL_FREEZEBLANCE));
        EthWalletDTO walletDTO = selectByAddrAndTokenAddrAndWalletType(addr, tokenAddr, walletType);
        if (freeBlance.add(new BigDecimal(walletDTO.getFreeBalance())).compareTo(BigDecimal.ZERO) < 0) {
            throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
        if (freezeBlance.add(new BigDecimal(walletDTO.getFreezeBalance())).compareTo(BigDecimal.ZERO) < 0) {
            throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        }
        // 进行行锁转账（应用钱包-应用钱包）
        int row = ethWalletMapper.updateBalanceByAddrInRowLock(addr, tokenAddr, walletType, freeBlance.add(freezeBlance),
                freeBlance, freezeBlance, date);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
        return selectByAddrAndTokenAddrAndWalletType(addr, tokenAddr, walletType);
    }

    /**
     * 修改指定应用钱包修改余额的简洁方法
     *
     * @param userOpenId
     * @param tokenAddr
     * @param walletType
     * @param tokenAddr
     * @param walletType
     */
    private EthWalletDTO updateBalanceByUserOpenId(String userOpenId, String tokenAddr, String walletType,
                                                   BigDecimal freeBlance, BigDecimal freezeBlance, Date date) {
        // 数据验证
        ExceptionPreconditionUtils.checkNotNull(freeBlance, new EthWalletException(EthWalletEnums.NULL_FREEBLANCE));
        ExceptionPreconditionUtils.checkNotNull(freezeBlance, new EthWalletException(EthWalletEnums.NULL_FREEZEBLANCE));
        EthWalletDTO walletDTO = selectByUserOpenIdAndTokenAddrAndWalletType(userOpenId, tokenAddr, walletType);
        Map<String, BigDecimal> map = CheckFreeedBalanceUtil.switchBalance(new BigDecimal(walletDTO.getFreezeBalance()), freezeBlance, freeBlance);
        if(map!=null&&map.size()==0){
            freezeBlance = map.get(CheckFreeedBalanceUtil.FREEZE);
            freeBlance = map.get(CheckFreeedBalanceUtil.FREE);
        }
        if (freeBlance.add(new BigDecimal(walletDTO.getFreeBalance())).compareTo(BigDecimal.ZERO) < 0) {
            throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
        if (freezeBlance.add(new BigDecimal(walletDTO.getFreezeBalance())).compareTo(BigDecimal.ZERO) < 0) {
            LOG.info("userOpenId:" + userOpenId + ",tokenAddr:" + tokenAddr + ",freezeBlance:" + freezeBlance + ",getFreezeBalance" + walletDTO.getFreezeBalance());
            throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        }
        // 进行行锁转账（应用钱包-应用钱包）
        int row = ethWalletMapper.updateBalanceByAddrInRowLock(walletDTO.getAddr(), tokenAddr, walletType,
                freeBlance.add(freezeBlance),
                freeBlance, freezeBlance, date);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
        return selectByAddrAndTokenAddrAndWalletType(walletDTO.getAddr(), tokenAddr, walletType);
    }
}
