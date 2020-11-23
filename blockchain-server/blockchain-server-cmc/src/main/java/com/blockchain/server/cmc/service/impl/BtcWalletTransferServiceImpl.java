package com.blockchain.server.cmc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.*;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.cmc.common.constants.BtcTransferConstans;
import com.blockchain.server.cmc.common.enums.BtcEnums;
import com.blockchain.server.cmc.common.exception.BtcException;
import com.blockchain.server.cmc.common.util.AsyncOptionUtils;
import com.blockchain.server.cmc.common.util.CheckEthFeginResult;
import com.blockchain.server.cmc.dto.BtcWalletDTO;
import com.blockchain.server.cmc.dto.BtcWalletTransferDTO;
import com.blockchain.server.cmc.entity.BtcWalletTransfer;
import com.blockchain.server.cmc.feign.EthServerFegin;
import com.blockchain.server.cmc.feign.UserServerFegin;
import com.blockchain.server.cmc.mapper.BtcWalletTransferMapper;
import com.blockchain.server.cmc.rpc.BtcUtils;
import com.blockchain.server.cmc.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BtcWalletTransferServiceImpl implements BtcWalletTransferService, ITxTransaction {

    private static final Logger LOG = LoggerFactory.getLogger(BtcWalletTransferServiceImpl.class);


    @Autowired
    private BtcWalletTransferMapper btcWalletTransferMapper;

    @Autowired
    private BtcWalletService btcWalletService;

    @Autowired
    private BtcApplicationService btcApplicationService;

    @Autowired
    private BtcTokenService btcTokenService;

    @Autowired
    private BtcUtils btcUtils;

    @Autowired
    private ConfigWalletParamService walletParamService;

    @Autowired
    private EthServerFegin ethServerFegin;

    @Autowired
    private UserServerFegin userServerFegin;

    @Autowired
    private AsyncOptionUtils asyncOptionUtils;


    @Override
    public Integer insertTransfer(BtcWalletTransfer btcWalletTransfer) {
        return btcWalletTransferMapper.insertSelective(btcWalletTransfer);
    }

    @Override
    public List<BtcWalletTransferDTO> selectTransfer(String userOpenId, Integer tokenId, String walletType) {
        //校验钱包类型
        btcApplicationService.checkWalletType(walletType);
        //校验币种id
        btcTokenService.getAndVerifyTokenNameById(tokenId);
        BtcWalletDTO btcWalletDTO = btcWalletService.selectByUserOpenId(userOpenId, tokenId, walletType);
        String addr = btcWalletDTO.getAddr();
        return btcWalletTransferMapper.selectTransfer(addr, tokenId);
    }

    @Override
    public Integer updateStatusById(String id, int status) {
        BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
        btcWalletTransfer.setId(id);
        btcWalletTransfer.setStatus(status);
        return btcWalletTransferMapper.updateByPrimaryKeySelective(btcWalletTransfer);
    }

    @Override
    public Boolean isNotExistsTransferIn(String txId) {
        String id = btcWalletTransferMapper.selectIdByTxIdAndType(txId, BtcTransferConstans.TYPE_IN);
        return StringUtils.isEmpty(id);
    }

    @Override
    @Transactional
    public void handleBlockRecharge(BtcWalletTransfer btcWalletTransfer) {
        if (isNotExistsTransferIn(btcWalletTransfer.getHash())) {
            //插入充值记录
            int countIt = insertTransfer(btcWalletTransfer);
            if (countIt != 1) {
                throw new BtcException(BtcEnums.PARSE_TRANSFER_IN_ERROR);
            }
            //增加钱包可用余额、总额
            int countUb = btcWalletService.updateBalanceByAddrInRowLock(btcWalletTransfer.getToAddr(), btcWalletTransfer.getTokenId(),
                    btcWalletTransfer.getAmount(), BigDecimal.ZERO, btcWalletTransfer.getAmount(), btcWalletTransfer.getCreateTime());
            if (countUb != 1) {
                throw new BtcException(BtcEnums.PARSE_TRANSFER_IN_ERROR);
            }
        }
    }

    @Override
    @Transactional
    @TxTransaction
    public BtcWalletDTO handleOrder(WalletOrderDTO walletOrderDTO) {
        BigDecimal freeBalance = walletOrderDTO.getFreeBalance();
        BigDecimal freezeBalance = walletOrderDTO.getFreezeBalance();

        int tokenId = btcTokenService.getAndVerifyTokenIdByName(walletOrderDTO.getTokenName());
        //查询用户钱包
        BtcWalletDTO btcWalletDTO = btcWalletService.selectByUserOpenId(walletOrderDTO.getUserId(), tokenId, walletOrderDTO.getWalletType());
        if (btcWalletDTO == null) {
            throw new BtcException(BtcEnums.NO_WALLET_ERROR);
        }
        //585.419183992150000000  -585.41918400
        boolean freeFlag = btcWalletDTO.getFreeBalance().add(freeBalance).compareTo(BigDecimal.ZERO) < 0;
        boolean freezeFlag = btcWalletDTO.getFreezeBalance().add(freezeBalance).compareTo(BigDecimal.ZERO) < 0;

        if (freeFlag || freezeFlag) {
            //判断冻结余额和挂单金额之间的误差
            LOG.info(walletOrderDTO.getUserId() + "--------11111111111-----用户出现金额 + -负数情况，开始异常处理  btcWalletDTO" + JSON.toJSON(btcWalletDTO) + ";walletOrderDTO :" + JSON.toJSONString(walletOrderDTO));
            if (freezeFlag) { //.setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal freezeBalance1 = btcWalletDTO.getFreezeBalance().setScale(8, BigDecimal.ROUND_HALF_UP);
                freezeFlag = freezeBalance.add(freezeBalance1).compareTo(BigDecimal.ZERO) < 0;
                if ((!freezeFlag) && freezeBalance1.add(freezeBalance).compareTo(BigDecimal.ZERO) == 0) {
                    walletOrderDTO.setFreezeBalance(btcWalletDTO.getFreezeBalance().multiply(new BigDecimal("-1")));
                } else {
                    //四舍五入失败，求误差直接保留8位
                    BigDecimal subFreezeBalance = btcWalletDTO.getFreezeBalance().add(freezeBalance);
                    System.out.println(subFreezeBalance);
                    if (subFreezeBalance.compareTo(new BigDecimal("0.00000001")) < 0 &&
                            subFreezeBalance.compareTo(new BigDecimal("-0.00000001")) > 0) {
                        freezeFlag = false;
                        walletOrderDTO.setFreezeBalance(btcWalletDTO.getFreezeBalance().multiply(new BigDecimal("-1")));
                    }
                }
            }

            if (freeFlag || freezeFlag) {
                LOG.info(walletOrderDTO.getUserId() + "---2222222222----处理失败抛出异常");
                throw new BtcException(BtcEnums.FREEBALANCE_NOT_ENOUGH);
            }
            LOG.info(walletOrderDTO.getUserId() + "---2222222222----处理成功，处理流程继续......");
        }

        //加减钱包可用余额、冻结余额，之间转换
        int countUb = btcWalletService.updateBalanceByAddrInRowLock(btcWalletDTO.getAddr(), btcWalletDTO.getTokenId(),
                walletOrderDTO.getFreeBalance(), walletOrderDTO.getFreezeBalance(), BigDecimal.ZERO, new Date());
        if (countUb != 1) {
            throw new BtcException(BtcEnums.TRANSFER_ERROR);
        }

        //返回加减余额后的数据
        return btcWalletService.selectByUserOpenId(walletOrderDTO.getUserId(), tokenId, walletOrderDTO.getWalletType());
    }


    @Override
    @Transactional
    @TxTransaction
    public Integer handleChange(WalletChangeDTO walletChangeDTO) {
        BigDecimal freeBalance = walletChangeDTO.getFreeBalance();
        BigDecimal freezeBalance = walletChangeDTO.getFreezeBalance();
        BigDecimal totalBalance = freeBalance.add(freezeBalance);

        Date now = new Date();

        int tokenId = btcTokenService.getAndVerifyTokenIdByName(walletChangeDTO.getTokenName());
        //查询用户钱包
        BtcWalletDTO btcWalletDTO = btcWalletService.selectByUserOpenId(walletChangeDTO.getUserId(), tokenId, walletChangeDTO.getWalletType());
        if (btcWalletDTO == null) {
            throw new BtcException(BtcEnums.NO_WALLET_ERROR);
        }

        boolean freeFlag = btcWalletDTO.getFreeBalance().add(freeBalance).compareTo(BigDecimal.ZERO) < 0;
        boolean freezeFlag = btcWalletDTO.getFreezeBalance().add(freezeBalance).compareTo(BigDecimal.ZERO) < 0;
        if (freeFlag || freezeFlag) {
            throw new BtcException(BtcEnums.BALANCE_NOT_ENOUGH);
        }

        if (walletChangeDTO.getFreeBalance().compareTo(BigDecimal.ZERO) != 0 || walletChangeDTO.getFreezeBalance().compareTo(BigDecimal.ZERO) != 0) {
            //加减钱包可用余额、冻结余额、总额
            int countUb = btcWalletService.updateBalanceByAddrInRowLock(btcWalletDTO.getAddr(), btcWalletDTO.getTokenId(),
                    walletChangeDTO.getFreeBalance(), walletChangeDTO.getFreezeBalance(), totalBalance, now);
            if (countUb != 1) {
                throw new BtcException(BtcEnums.TRANSFER_ERROR);
            }
        }

        //插入一条交易记录
        BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
        if (totalBalance.compareTo(BigDecimal.ZERO) < 0) {
            btcWalletTransfer.setFromAddr(btcWalletDTO.getAddr());
            btcWalletTransfer.setToAddr(null);
        } else {
            btcWalletTransfer.setFromAddr(null);
            btcWalletTransfer.setToAddr(btcWalletDTO.getAddr());
        }
        btcWalletTransfer.setId(UUID.randomUUID().toString());
        btcWalletTransfer.setHash(walletChangeDTO.getRecordId());

        btcWalletTransfer.setAmount(totalBalance.abs());
        btcWalletTransfer.setTokenId(tokenId);
        btcWalletTransfer.setTokenSymbol(walletChangeDTO.getTokenName());
        btcWalletTransfer.setGasPrice(walletChangeDTO.getGasBalance());
        if (StringUtils.isNotEmpty(walletChangeDTO.getTransferType())) {
            btcWalletTransfer.setTransferType(walletChangeDTO.getTransferType());
        } else {
            btcWalletTransfer.setTransferType(walletChangeDTO.getWalletType());
        }
        btcWalletTransfer.setStatus(BtcTransferConstans.STATUS_SUCCESS);
        btcWalletTransfer.setCreateTime(now);
        btcWalletTransfer.setUpdateTime(now);
        int countIt = insertTransfer(btcWalletTransfer);

        if (countIt != 1) {
            throw new BtcException(BtcEnums.TRANSFER_ERROR);
        }

        return 1;
    }

    @Override
    @Transactional
    public BtcWalletDTO handleWithdraw(String userOpenId, String password, String toAddress, Integer tokenId, BigDecimal amount,
                                       String walletType, String verifyCode, String account) {
        ExceptionPreconditionUtils.checkStringNotBlank(verifyCode, new BtcException(BtcEnums.WITHDRAW_CODE_NULL));

        //校验钱包类型
        btcApplicationService.checkWalletType(walletType);
        //校验币种id
        String tokenSymbol = btcTokenService.getAndVerifyTokenNameById(tokenId);

        //获取提现手续费
        GasDTO gasDTO = walletParamService.getGasConfig(tokenSymbol);
        //与最小提现数额做对比
        if (gasDTO.getMinWdAmount().compareTo(amount) > 0) {
            throw new BtcException(BtcEnums.LOW_WITHDRAW_AMOUNT_ERROR);
        }

        //检验密码
        CheckEthFeginResult.checkIsPassword(ethServerFegin.isPassword(password));
        //检验提现验证码
        ResultDTO verifyWithdrawSms = userServerFegin.verifyWithdrawSms(verifyCode, account);
        if (verifyWithdrawSms == null) {
            throw new BtcException(BtcEnums.SERVER_IS_TOO_BUSY);
        }
        if (verifyWithdrawSms.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(verifyWithdrawSms);
        }

        //验证提现地址是否有效
        JSONObject vaObj = null;
        try {
            vaObj = btcUtils.validateAddress(toAddress);
        } catch (Exception e) {
            throw new BtcException(BtcEnums.ADDRESS_ERROR);
        }
        //验证提现地址是否有效
        if (!vaObj.getBoolean("isvalid")) {
            throw new BtcException(BtcEnums.ADDRESS_ERROR);
        }

        // 查询是否存在提现黑名单中 抛出错误表示用户禁止提现
        userServerFegin.verifyBanWithdraw(userOpenId);

        //获取钱包余额
        BtcWalletDTO btcWalletDTO = btcWalletService.selectByUserOpenId(userOpenId, tokenId, walletType);
        if (btcWalletDTO.getFreeBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new BtcException(BtcEnums.FREEBALANCE_NOT_ENOUGH);
        }
        //用户转出钱包地址
        String fromAddress = btcWalletDTO.getAddr();

        Date now = new Date();

        //判断是否属于节点钱包
        if (vaObj.getBoolean("ismine")) {
            //*********** 是，快速转账，数据库划转 ***********

            //该用户减去提现可用余额、总额
            int countUb = btcWalletService.updateBalanceByAddrInRowLock(fromAddress, tokenId, amount.negate(), BigDecimal.ZERO, amount.negate(), now);
            if (countUb != 1) {
                throw new BtcException(BtcEnums.WITHDRAW_ERROR);
            }
            //接收用户加上可用余额、总额
            int countUbR = btcWalletService.updateBalanceByAddrInRowLock(toAddress, tokenId, amount, BigDecimal.ZERO, amount, now);
            if (countUbR != 1) {
                throw new BtcException(BtcEnums.WITHDRAW_ERROR);
            }

            //并插入一条提现记录，站内快速转账
            BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
            btcWalletTransfer.setId(UUID.randomUUID().toString());
//            btcWalletTransfer.setHash(null);
            btcWalletTransfer.setFromAddr(fromAddress);
            btcWalletTransfer.setToAddr(toAddress);
            btcWalletTransfer.setAmount(amount.abs());
//            btcWalletTransfer.setGasPrice(null);
            btcWalletTransfer.setTokenId(tokenId);
            btcWalletTransfer.setTokenSymbol(tokenSymbol);
            btcWalletTransfer.setTransferType(BtcTransferConstans.TYPE_FAST);
            btcWalletTransfer.setStatus(BtcTransferConstans.STATUS_SUCCESS);
            btcWalletTransfer.setCreateTime(now);
            btcWalletTransfer.setUpdateTime(now);
            int countIt = insertTransfer(btcWalletTransfer);
            if (countIt != 1) {
                throw new BtcException(BtcEnums.WITHDRAW_ERROR);
            }
        } else {
            //*********** 否，区块链转账 ***********

            // 判断用户是否存在提现白名单中
            ResultDTO<Boolean> resultDTO = userServerFegin.verifyFreeWithdraw(userOpenId);
            if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS)
                throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
            // 用户存在提现白名单中，设置提现手续费为零
            if (resultDTO.getData()) gasDTO.setGasPrice(BigDecimal.ZERO);
            // 余额加上扣除手续费
            //该用户减去提现可用余额、加上冻结余额
            int countUb = btcWalletService.updateBalanceByAddrInRowLock(fromAddress, tokenId, amount.negate(), amount, BigDecimal.ZERO, now);
            if (countUb != 1) {
                throw new BtcException(BtcEnums.WITHDRAW_ERROR);
            }
            //并插入一条提现记录
            BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
            btcWalletTransfer.setId(UUID.randomUUID().toString());
//            btcWalletTransfer.setHash(null);
            btcWalletTransfer.setFromAddr(fromAddress);
            btcWalletTransfer.setToAddr(toAddress);
            btcWalletTransfer.setAmount(amount.abs());
            btcWalletTransfer.setTokenId(tokenId);
            btcWalletTransfer.setTokenSymbol(tokenSymbol);
            //设置手续费
            btcWalletTransfer.setGasPrice(gasDTO.getGasPrice());
            btcWalletTransfer.setGasTokenType(gasDTO.getGasTokenType());
            btcWalletTransfer.setGasTokenSymbol(gasDTO.getGasTokenSymbol());
            btcWalletTransfer.setGasTokenName(gasDTO.getGasTokenName());
            btcWalletTransfer.setTransferType(BtcTransferConstans.TYPE_OUT);
            btcWalletTransfer.setStatus(BtcTransferConstans.STATUS_FIRST_TRIAL);
            btcWalletTransfer.setCreateTime(now);
            btcWalletTransfer.setUpdateTime(now);
            int countIt = insertTransfer(btcWalletTransfer);
            if (countIt != 1) {
                throw new BtcException(BtcEnums.WITHDRAW_ERROR);
            }
        }

        NotifyOutSMS notifyOutSMS = new NotifyOutSMS();
        notifyOutSMS.setUserId(userOpenId);
        notifyOutSMS.setAmount(amount.stripTrailingZeros().toPlainString());
        notifyOutSMS.setCoin(tokenSymbol);
        //异步通知
        asyncOptionUtils.notifyOut(userServerFegin, notifyOutSMS);

        //返回加减余额后的数据
        return btcWalletService.selectByAddr(fromAddress, tokenId, walletType);
    }

    @Override
    public List<BtcWalletTransfer> selectByTxTypeAndStatus(String transferType, int Status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);  // 分页处理
        BtcWalletTransfer where = new BtcWalletTransfer();
        where.setTransferType(transferType);
        where.setStatus(Status);
        return btcWalletTransferMapper.select(where);
    }

    @Override
    @Transactional
    public void updateStatus(String id, int status, Date date) {
        BtcWalletTransfer ethWalletTransfer = btcWalletTransferMapper.selectByPrimaryKey(id);
        ethWalletTransfer.setStatus(status);
        ethWalletTransfer.setUpdateTime(date);
        // 失败抛出异常
        int row = btcWalletTransferMapper.updateByPrimaryKeySelective(ethWalletTransfer);
        if (row == 0) {
            throw new BtcException(BtcEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
