package com.blockchain.server.tron.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.tron.common.constant.TronWalletConstants;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.redis.RedisPrivate;
import com.blockchain.server.tron.dto.*;
import com.blockchain.server.tron.entity.TronApply;
import com.blockchain.server.tron.entity.TronWallet;
import com.blockchain.server.tron.entity.TronWalletPrepayment;
import com.blockchain.server.tron.entity.TronWalletTransfer;
import com.blockchain.server.tron.feign.EthServerFeign;
import com.blockchain.server.tron.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 预支付接口——业务接口
 *
 * @author YH
 * @date 2018年12月5日10:21:27
 */
@Service("walletPayServiceImpl")
public class TronWalletPayServiceImpl implements TronWalletPayService {
    @Autowired
    private TronWalletPrepaymentService tronWalletPrepaymentService;
    @Autowired
    private TronApplyService tronApplyService;
    @Autowired
    private TronWalletService tronWalletService;
    @Autowired
    private TronWalletTransferService tronWalletTransferService;
    @Autowired
    EthServerFeign ethFeignServer;
    @Autowired
    private RedisPrivate redisPrivate;
    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(TronWalletPayServiceImpl.class);

    /**
     * 获取公钥，业务描述：
     * 1、查询预支付信息，获取用户id;
     * 2、生成秘钥对，返回公钥
     *
     * @param id 预支付id
     * @return 公钥串
     */
    @Override
    public String getPublicById(String id) {
        // #1
        ExceptionPreconditionUtils.checkStringNotBlank(id, new TronWalletException(TronWalletEnums.PAY_PREPAYIDNULL_ERROR));   //  {id}空验证
        TronWalletPrepayment walletPrepayment = tronWalletPrepaymentService.findById(id);  // 根据{id}查询预支付信息
        ExceptionPreconditionUtils.checkNotNull(walletPrepayment, new TronWalletException(TronWalletEnums.PAY_PREPAYID_ERROR));    // 预支付信息空验证
        // #2
        String userOpenId = walletPrepayment.getOpenId();   // 获取用户id
        String puvlicKey = redisPrivate.getPublicKey(userOpenId); //  生成秘钥对，私钥存入缓存，获取公钥

        return puvlicKey;
    }

    /**
     * 下单接口（预支付接口），业务描述：
     * 1、数据空验证；
     * 2、数据有效验证[ 应用信息验证，用户真实验证，数字货币验证，余额数验证，商户单号唯一验证 ]
     * 3、保存预支付记录，返回数据参数
     *
     * @param inParams 接收的数据
     * @return 返回的数据
     */
    @Override
    @Transactional
    public PrepaymentInsertOutDTO insertOrder(PrepaymentInsertInDTO inParams) {
        //  数据空验证
        checkInParams(inParams);

        //  #2
        //  ——应用信息有效性 验证
        TronApply apply = tronApplyService.findByAppidAndAppSecret(inParams.getAppId(), inParams.getAppSecret());
        ExceptionPreconditionUtils.checkNotNull(apply, new TronWalletException(TronWalletEnums.PAY_INPARAMS_APPLY_ERROR));
        //  ——用户信息有效性 验证
        TronWallet tronWalletDTO = tronWalletService.selectByUserOpenIdAndTokenAddrAndWalletType(inParams.getOpenId(), inParams.getCoinAddress(), TronWalletConstants.WalletType.WALLET);
        ExceptionPreconditionUtils.checkNotNull(tronWalletDTO, new TronWalletException(TronWalletEnums.PAY_INPARAMS_OPENID_ERROR));
        //  ——商户订单唯一标识 验证
        int count = tronWalletPrepaymentService.findByAppIdAndTradeNoLimit(inParams.getAppId(), inParams.getTradeNo());
        if (count == 1) {
            throw new TronWalletException(TronWalletEnums.PAY_INPARAMS_TRADENO_ERROR);
        }
        //  ——数字货币是否足够 验证
        BigDecimal amount = BigDecimal.ZERO;
        try {
            amount = new BigDecimal(inParams.getAmount());   //  获取需要支付的余额
        } catch (Exception e) {
            throw new TronWalletException(TronWalletEnums.PAY_INPARAMS_AMOUNT_ERROR);
        }
        // BigDecimal balance = new BigDecimal(tronWalletDTO.getFreeBalance());   //  获取ETH可用余额
        if (tronWalletDTO.getFreeBalance().compareTo(amount) < 0) {    //  判断ETH钱包可用余额是否足够支付
            throw new TronWalletException(TronWalletEnums.PAY_INPARAMS_AMOUNTLACK_ERROR);
        }
        //  #3
        PrepaymentInsertOutDTO outParams = tronWalletPrepaymentService.insert(inParams);
        return outParams;
    }

    /**
     * 支付接口，业务描述：
     * 1、数据空验证；
     * 2、数据有效验证[ 预支付验证，密码验证 ]；
     * 3、生成支付记录
     *
     * @param id       预支付id
     * @param password 加密后的密码
     * @return
     */
    @Override
    @Transactional
    public PayInsertOutDTO insertOrder(String openId, String id, String password) {
        //  #1
        ExceptionPreconditionUtils.checkStringNotBlank(id, new TronWalletException(TronWalletEnums.PAY_PAY_PREPAYIDNULL_ERROR));   //  预支付ID空判断
        ExceptionPreconditionUtils.checkStringNotBlank(password, new TronWalletException(TronWalletEnums.PAY_PAY_PASSWORNULL_ERROR));  //  钱包密码空判断
        //  #2
        //  ——预支付信息验证
        TronWalletPrepayment walletPrepayment = tronWalletPrepaymentService.findById(id);
        ExceptionPreconditionUtils.checkNotNull(walletPrepayment, new TronWalletException(TronWalletEnums.PAY_PAY_PASSWORABSENCE_ERROR));
        //  验证支付记录是否存在该预支付ID的数据
        int prePayRow = tronWalletTransferService.findByHashAndTypeTheLime(walletPrepayment.getId(), TronWalletConstants.TransferType.PAY);
        if (prePayRow == 1) {
            throw new TronWalletException(TronWalletEnums.PAY_PREPAYIDEXIST_ERROR);
        }

        //  ——钱包密码验证
        TronWallet wallet = tronWalletService.selectByUserOpenIdAndTokenAddrAndWalletType(walletPrepayment.getOpenId(), walletPrepayment.getCoinAddress(), TronWalletConstants.WalletType.WALLET);
        ExceptionPreconditionUtils.checkNotNull(walletPrepayment, new TronWalletException(TronWalletEnums.PAY_PAY_USEROPENTID_ERROR));
        ResultDTO resultDTO = ethFeignServer.isPassword(openId, password);
        if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS)
            throw new TronWalletException(TronWalletEnums.PAY_PAY_PASSWORD_ERROR);
//        if (!resultDTO.getData()) throw new TronWalletException(TronWalletEnums.PAY_PAY_PASSWORD_ERROR);
        //生成支付记录
        return tronWalletService.updatePayInRowLock(walletPrepayment, wallet.getHexAddr());
    }

    /**
     * 订单查询接口，业务描述：
     * 1、数据验证；
     * 2、数据查询返回；
     *
     * @param appId     应用ID
     * @param appSecret 应用安全码
     * @param tradeNo   商户单号
     * @return
     */
    @Override
    public PayQueryOrderDTO queryOrder(String appId, String appSecret, String tradeNo) {
        //  #1
        ExceptionPreconditionUtils.checkStringNotBlank(tradeNo, new TronWalletException(TronWalletEnums.QUERYORDER_TRADENONULL_ERROR)); //  商户单号唯一标识 空验证
        //  ——应用信息有效性 验证
        TronApply apply = tronApplyService.findByAppidAndAppSecret(appId, appSecret);
        ExceptionPreconditionUtils.checkNotNull(apply, new TronWalletException(TronWalletEnums.QUERYORDER_INPARAMS_APPLY_ERROR));
        //  #2
        TronWalletPrepayment walletPrepayment = tronWalletPrepaymentService.findByTradeNo(appId, tradeNo);  // 预支付信息查询
        if (walletPrepayment == null) {
            throw new TronWalletException(TronWalletEnums.QUERYORDER_TRADENO_ERROR);
        }
        // 订单查询参数
        PayQueryOrderDTO payQueryOrderDTO = new PayQueryOrderDTO();
        payQueryOrderDTO.setAmount(walletPrepayment.getAmount());
        payQueryOrderDTO.setCoinAddress(walletPrepayment.getCoinAddress());
        payQueryOrderDTO.setCoinName(walletPrepayment.getCoinName());
        payQueryOrderDTO.setPrePayId(walletPrepayment.getId());
        payQueryOrderDTO.setTradeNo(walletPrepayment.getTradeNo());
        payQueryOrderDTO.setTimestamp(walletPrepayment.getCreateTime().getTime());
        TronWalletTransfer transfer = new TronWalletTransfer();
        transfer.setHash(walletPrepayment.getId());
        transfer.setTransferType(TronWalletConstants.TransferType.PAY);
        transfer.setStatus(TronWalletConstants.StatusType.SUCCESS);
        TronWalletTransfer walletTransfer = tronWalletTransferService.selectOne(transfer);
        if (walletTransfer != null) {
            PayInsertOutDTO payData = new PayInsertOutDTO();
            payData.setAmount(walletTransfer.getAmount().toString());
            payData.setCoinAddress(walletTransfer.getTokenAddr());
            payData.setCoinName(walletTransfer.getTokenSymbol());
            payData.setTransactionId(walletTransfer.getId());
            payData.setTimestamp(walletTransfer.getCreateTime().getTime());
            payData.setTradeNo(walletTransfer.getToHexAddr());
            payQueryOrderDTO.setPayData(payData);
        }
        return payQueryOrderDTO;
    }

    /**
     * 退款接口，业务描述：
     * 1、数据空验证；
     * 2、数据有效验证[ 应用信息验证，商户单号验证，退款数额有效验证]
     *
     * @param inParams 接收的数据
     * @return 返回的数据
     */
    @Override
    @Transactional
    public RefundOutDTO insertRefund(RefundInDTO inParams) {
        //  #1
        checkInParams(inParams);
        //  #2
        //  ——应用信息有效性 验证
        TronApply apply = tronApplyService.findByAppidAndAppSecret(inParams.getAppId(), inParams.getAppSecret());
        ExceptionPreconditionUtils.checkNotNull(apply, new TronWalletException(TronWalletEnums.REFUND_INPARAMS_APPLY_ERROR));
        //  ——商户号验证
        TronWalletPrepayment walletPrepayment = tronWalletPrepaymentService.findByTradeNo(inParams.getAppId(), inParams.getTradeNo());
        if (walletPrepayment == null) {
            throw new TronWalletException(TronWalletEnums.REFUND_TRADENO_ERROR);
        }
        if (walletPrepayment.getCoinName().equalsIgnoreCase(inParams.getCoinName())) {
            if (!walletPrepayment.getCoinAddress().equalsIgnoreCase(inParams.getCoinAddress())) {
                throw new TronWalletException(TronWalletEnums.REFUND_TOKENTYPE_ERROR);
            }
        }
        //  关于退款的业务描述，退款金额不可超过支付金额，且存在多次退款，多次退款金额累加，不能超过支付金额
        //  获取退款记录表中，退款金额
        BigDecimal amount = BigDecimal.ZERO;    //  获取支付金额
        BigDecimal refundAmount = BigDecimal.ZERO; //  获取此次退款金额
        try {
            refundAmount = new BigDecimal(inParams.getAmount());
        } catch (Exception e) {
        }
        if (refundAmount.compareTo(BigDecimal.ZERO) == 0) {
            throw new TronWalletException(TronWalletEnums.REFUND_AMOUHNT_ZROE_ERROR);
        }
        BigDecimal refundAllAmount = BigDecimal.ZERO;  //   获取累计的退款金额
        TronWalletTransfer where = new TronWalletTransfer(); // 查询条件
        // 查询支付记录的信息
        where.setHash(walletPrepayment.getId());
        where.setStatus(TronWalletConstants.StatusType.SUCCESS);
        where.setTransferType(TronWalletConstants.TransferType.PAY);
        TronWalletTransfer payTx = tronWalletTransferService.selectOne(where);
        // 查询退款记录的信息
        where = new TronWalletTransfer();
        where.setFromHexAddr(payTx.getToHexAddr());
        where.setToHexAddr(payTx.getFromHexAddr());
        where.setStatus(TronWalletConstants.StatusType.SUCCESS);
        where.setTransferType(TronWalletConstants.TransferType.REFUND);
        List<TronWalletTransfer> list = tronWalletTransferService.select(where);
        // 退款金额验证
        amount = payTx.getAmount();
        for (TronWalletTransfer row : list) {
            refundAllAmount = refundAllAmount.add(row.getAmount());
        }
        if (amount.compareTo(refundAmount.add(refundAllAmount)) < 0) {
            throw new TronWalletException(TronWalletEnums.REFUND_AMOUNT_ERROR);
        } else {
            // 执行退款操作
            RefundOutDTO refundOutDTO = tronWalletService.updateRefundInRowLock(payTx, refundAmount);
            return refundOutDTO;
        }
    }

    /**
     * 退款查询接口，业务描述：
     * 1、数据验证；
     * 2、数据查询返回；
     *
     * @param appId     应用ID
     * @param appSecret 应用安全码
     * @param tradeNo   商户单号
     * @return
     */
    @Override
    public List<RefundOutDTO> queryRefund(String appId, String appSecret, String tradeNo) {
        //  #1
        ExceptionPreconditionUtils.checkStringNotBlank(tradeNo, new TronWalletException(TronWalletEnums.QUERYORDER_TRADENONULL_ERROR)); //  商户单号唯一标识 空验证
        //  ——应用信息有效性 验证
        TronApply apply = tronApplyService.findByAppidAndAppSecret(appId, appSecret);
        ExceptionPreconditionUtils.checkNotNull(apply, new TronWalletException(TronWalletEnums.QUERYORDER_INPARAMS_APPLY_ERROR));
        //  #2
        TronWalletPrepayment walletPrepayment = tronWalletPrepaymentService.findByTradeNo(appId, tradeNo);  // 预支付信息查询
        if (walletPrepayment == null) {
            throw new TronWalletException(TronWalletEnums.QUERYORDER_TRADENO_ERROR);
        }
        return tronWalletTransferService.selectRefundByTradeNo(tradeNo);
    }

    /**
     * 请求用户数据
     *
     * @param appId      应用ID
     * @param appSecret  应用安全码
     * @param userOpenId 用户ID
     * @return
     */
    @Override
    public UserWalletInfoDTO checkToken(String appId, String appSecret, String userOpenId) {
        //  ——应用信息有效性 验证
        TronApply apply = tronApplyService.findByAppidAndAppSecret(appId, appSecret);
        ExceptionPreconditionUtils.checkNotNull(apply, new TronWalletException(TronWalletEnums.QUERYORDER_INPARAMS_APPLY_ERROR));
        //  ——数据封装
        TronWallet where = new TronWallet();
        where.setUserOpenId(userOpenId);
        List<TronWallet> wallets = tronWalletService.selects(where);
        if (wallets == null) {
            throw new TronWalletException(TronWalletEnums.INEXISTENCE_WALLET);
        }
        List<WalletPayTokentDTO> lists = new ArrayList<>();
        for (TronWallet row : wallets) {
            WalletPayTokentDTO walletPayTokentDTO = new WalletPayTokentDTO();
            walletPayTokentDTO.setBalance(row.getBalance().toString());
//            walletPayTokentDTO.setDecimals(row.getTokenDecimals());
            walletPayTokentDTO.setTokenAddress(row.getTokenAddr().toString());
            walletPayTokentDTO.setTokenName(row.getTokenSymbol());
            walletPayTokentDTO.setWalletType(row.getWalletType());
            lists.add(walletPayTokentDTO);
        }
        UserWalletInfoDTO userWalletInfoDTO = new UserWalletInfoDTO();
        userWalletInfoDTO.setOpenId(userOpenId);
        userWalletInfoDTO.setTokens(lists);
        return userWalletInfoDTO;
    }

    /**
     * 发放奖金
     * 1、数据空验证；
     * 2、数据有效验证；
     * 3、执行奖金发放
     *
     * @param inParams 接收的数据
     * @return
     */
    @Override
    @Transactional
    public RechargeOutDTO insertRecharge(RechargeInDTO inParams) {
        checkInParams(inParams);
        //  #2
        //  ——发放金额有效验证
        BigInteger amount = BigInteger.ZERO;
        try {
            amount = new BigDecimal(inParams.getAmount()).toBigInteger();
        } catch (Exception e) {

        }
        if (amount.compareTo(BigInteger.ZERO) == 0) {
            throw new TronWalletException(TronWalletEnums.RECHARGE_AMOUHNT_ZROE_ERROR);
        }
        //  ——应用信息有效性 验证
        TronApply apply = tronApplyService.findByAppidAndAppSecret(inParams.getAppId(), inParams.getAppSecret());
        ExceptionPreconditionUtils.checkNotNull(apply,
                new TronWalletException(TronWalletEnums.RECHARGE_INPARAMS_APPLY_ERROR));
        //  ——用户信息有效性 验证
        TronWallet wallet = tronWalletService.selectByUserOpenIdAndTokenAddrAndWalletType(inParams.getOpenId(),
                inParams.getCoinAddress(), TronWalletConstants.WalletType.WALLET);
        ExceptionPreconditionUtils.checkNotNull(wallet,
                new TronWalletException(TronWalletEnums.RECHARGE_INPARAMS_OPENID_ERROR));
        //  #3 发放奖金，增加发放奖金的记录
        RechargeOutDTO refundOutDTO = tronWalletService.updateRechargeInRowLock(inParams, wallet.getHexAddr());
        return refundOutDTO;
    }


    @Override
    public RechargeOutDTO queryRecharge(String appId, String appSecret, String tradeNo) {
        //  #1
        ExceptionPreconditionUtils.checkStringNotBlank(tradeNo,
                new TronWalletException(TronWalletEnums.QUERYORDER_TRADENONULL_ERROR)); //  商户单号唯一标识 空验证
        //  ——应用信息有效性 验证
        TronApply apply = tronApplyService.findByAppidAndAppSecret(appId, appSecret);
        ExceptionPreconditionUtils.checkNotNull(apply,
                new TronWalletException(TronWalletEnums.QUERYORDER_INPARAMS_APPLY_ERROR));
        //  #2
        TronWalletTransfer where = new TronWalletTransfer();
        where.setHash(tradeNo);
        where.setTransferType(TronWalletConstants.TransferType.RECHARGE);
        TronWalletTransfer rechargeTx = tronWalletTransferService.selectOne(where);
        if (rechargeTx == null) {
            throw new TronWalletException(TronWalletEnums.QUERYORDER_TRADENO_ERROR);
        }
        RechargeOutDTO rechargeOutDTO = new RechargeOutDTO();
        rechargeOutDTO.setRechargeId(rechargeTx.getId());
        rechargeOutDTO.setTimestamp(rechargeTx.getCreateTime().getTime());
        rechargeOutDTO.setAmount(rechargeTx.getAmount().toString());
        rechargeOutDTO.setCoinAddress(rechargeTx.getTokenAddr().toString());
        rechargeOutDTO.setCoinName(rechargeTx.getTokenSymbol());
        rechargeOutDTO.setTradeNo(rechargeTx.getHash());
        TronWallet wallet = tronWalletService.selectByAddrAndTokenAddrAndWalletType(rechargeTx.getToHexAddr(),
                rechargeTx.getTokenAddr().toString(), TronWalletConstants.WalletType.WALLET);
        rechargeOutDTO.setOpenId(wallet.getUserOpenId());
        return rechargeOutDTO;
    }

    private void checkInParams(PrepaymentInsertInDTO inParams) {
        ExceptionPreconditionUtils.checkNotNull(inParams, new TronWalletException(TronWalletEnums.PAY_INPARAMSNULL_ERROR));   //  接收参数对象，空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAppId(), new TronWalletException(TronWalletEnums.PAY_INPARAMS_APPIDNULL_ERROR));   //  appid 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAppSecret(), new TronWalletException(TronWalletEnums.PAY_INPARAMS_APPSECRETNULL_ERROR));   //  安全码 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAmount(), new TronWalletException(TronWalletEnums.PAY_INPARAMS_AMOUNTNULL_ERROR)); //  支付金额 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getCoinName(), new TronWalletException(TronWalletEnums.PAY_INPARAMS_COINNAMENULL_ERROR)); //  数字货币名称 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getNotifyUrl(), new TronWalletException(TronWalletEnums.PAY_INPARAMS_NOTIFYURLNULL_ERROR));   //  支付通知地址 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getOpenId(), new TronWalletException(TronWalletEnums.PAY_INPARAMS_OPENIDNULL_ERROR)); //  用户ID 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getTradeNo(), new TronWalletException(TronWalletEnums.PAY_INPARAMS_TRADENONULL_ERROR)); //  商户单号唯一标识 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getSign(), new TronWalletException(TronWalletEnums.PAY_SIGE_TRADENONULL_ERROR));  // 签名空验证
    }

    public void checkInParams(RefundInDTO inParams) {
        ExceptionPreconditionUtils.checkNotNull(inParams, new TronWalletException(TronWalletEnums.REFUND_INPARAMSNULL_ERROR));   //  接收参数对象，空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAppId(), new TronWalletException(TronWalletEnums.REFUND_INPARAMS_APPIDNULL_ERROR));   //  appid 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAppSecret(), new TronWalletException(TronWalletEnums.REFUND_INPARAMS_APPSECRETNULL_ERROR));   //  安全码 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAmount(), new TronWalletException(TronWalletEnums.REFUND_INPARAMS_AMOUNTNULL_ERROR)); //  支付金额 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getCoinName(), new TronWalletException(TronWalletEnums.REFUND_INPARAMS_COINNAMENULL_ERROR)); //  数字货币名称 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getTradeNo(), new TronWalletException(TronWalletEnums.REFUND_INPARAMS_TRADENONULL_ERROR)); //  商户单号唯一标识 空验证
    }

    private void checkInParams(RechargeInDTO inParams) {
        ExceptionPreconditionUtils.checkNotNull(inParams, new TronWalletException(TronWalletEnums.RECHARGE_INPARAMSNULL_ERROR));   //  接收参数对象，空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAppId(), new TronWalletException(TronWalletEnums.RECHARGE_INPARAMS_APPIDNULL_ERROR));   //  appid 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAppSecret(), new TronWalletException(TronWalletEnums.RECHARGE_INPARAMS_APPSECRETNULL_ERROR));   //  安全码 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getAmount(), new TronWalletException(TronWalletEnums.RECHARGE_INPARAMS_AMOUNTNULL_ERROR)); //  支付金额 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getCoinName(), new TronWalletException(TronWalletEnums.RECHARGE_INPARAMS_COINNAMENULL_ERROR)); //  数字货币名称 空验证
        ExceptionPreconditionUtils.checkStringNotBlank(inParams.getTradeNo(), new TronWalletException(TronWalletEnums.RECHARGE_INPARAMS_TRADENONULL_ERROR)); //  商户单号唯一标识 空验证
    }
}
