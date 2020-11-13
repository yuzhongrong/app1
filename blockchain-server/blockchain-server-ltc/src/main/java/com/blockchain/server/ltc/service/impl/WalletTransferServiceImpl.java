package com.blockchain.server.ltc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.*;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.CheckFreeedBalanceUtil;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.ltc.common.constants.TransferConstans;
import com.blockchain.server.ltc.common.enums.ExceptionEnums;
import com.blockchain.server.ltc.common.exception.ServiceException;
import com.blockchain.server.ltc.common.util.AsyncOptionUtils;
import com.blockchain.server.ltc.common.util.CheckEthFeginResult;
import com.blockchain.server.ltc.dto.WalletDTO;
import com.blockchain.server.ltc.dto.WalletTransferDTO;
import com.blockchain.server.ltc.entity.WalletTransfer;
import com.blockchain.server.ltc.feign.EthServerFeign;
import com.blockchain.server.ltc.feign.UserServerFeign;
import com.blockchain.server.ltc.mapper.WalletTransferMapper;
import com.blockchain.server.ltc.rpc.CoinUtils;
import com.blockchain.server.ltc.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class WalletTransferServiceImpl implements WalletTransferService, ITxTransaction {
    @Autowired
    private WalletTransferMapper walletTransferMapper;

    @Autowired
    private WalletService walletService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CoinUtils coinUtils;

    @Autowired
    private ConfigWalletParamService walletParamService;

    @Autowired
    private EthServerFeign ethServerFeign;

    @Autowired
    private UserServerFeign userServerFeign;

    @Autowired
    private AsyncOptionUtils asyncOptionUtils;


    @Override
    public Integer insertTransfer(WalletTransfer walletTransfer) {
        return walletTransferMapper.insertSelective(walletTransfer);
    }

    @Override
    public List<WalletTransferDTO> selectTransfer(String userOpenId, Integer tokenId, String walletType, Integer pageNum, Integer pageSize) {
        //校验钱包类型
        applicationService.checkWalletType(walletType);
        //校验币种id
        tokenService.getAndVerifyTokenNameById(tokenId);
        WalletDTO walletDTO = walletService.selectByUserOpenId(userOpenId, tokenId, walletType);
        String addr = walletDTO.getAddr();

        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        return walletTransferMapper.selectTransfer(addr, tokenId);
    }

    @Override
    public Integer updateStatusById(String id, int status) {
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(id);
        walletTransfer.setStatus(status);
        return walletTransferMapper.updateByPrimaryKeySelective(walletTransfer);
    }

    @Override
    public Boolean isNotExistsTransferIn(String txId) {
        String id = walletTransferMapper.selectIdByTxIdAndType(txId, TransferConstans.TYPE_IN);
        return StringUtils.isEmpty(id);
    }

    @Override
    @Transactional
    public void handleBlockRecharge(WalletTransfer walletTransfer) {
        if (isNotExistsTransferIn(walletTransfer.getHash())) {
            //插入充值记录
            int countIt = insertTransfer(walletTransfer);
            if (countIt != 1) {
                throw new ServiceException(ExceptionEnums.PARSE_TRANSFER_IN_ERROR);
            }
            //增加钱包可用余额、总额
            int countUb = walletService.updateBalanceByAddrInRowLock(walletTransfer.getToAddr(), walletTransfer.getTokenId(),
                    walletTransfer.getAmount(), BigDecimal.ZERO, walletTransfer.getAmount(), walletTransfer.getCreateTime());
            if (countUb != 1) {
                throw new ServiceException(ExceptionEnums.PARSE_TRANSFER_IN_ERROR);
            }
        }
    }

    @Override
    @Transactional
    @TxTransaction
    public WalletDTO handleOrder(WalletOrderDTO walletOrderDTO) {
        BigDecimal freeBalance = walletOrderDTO.getFreeBalance();
        BigDecimal freezeBalance = walletOrderDTO.getFreezeBalance();

        int tokenId = tokenService.getAndVerifyTokenIdByName(walletOrderDTO.getTokenName());
        //查询用户钱包
        WalletDTO walletDTO = walletService.selectByUserOpenId(walletOrderDTO.getUserId(), tokenId, walletOrderDTO.getWalletType());
        Map<String, BigDecimal> map = CheckFreeedBalanceUtil.switchBalance(walletDTO.getFreezeBalance(), freezeBalance,freeBalance);
        if(map!=null&&map.size()==2){
            freezeBalance=map.get(CheckFreeedBalanceUtil.FREEZE);
            freeBalance=map.get(CheckFreeedBalanceUtil.FREE);
        }
        boolean freeFlag = walletDTO.getFreeBalance().add(freeBalance).compareTo(BigDecimal.ZERO) < 0;
        boolean freezeFlag = walletDTO.getFreezeBalance().add(freezeBalance).compareTo(BigDecimal.ZERO) < 0;
        if (freeFlag || freezeFlag) {
            throw new ServiceException(ExceptionEnums.FREEBALANCE_NOT_ENOUGH);
        }

        //加减钱包可用余额、冻结余额，之间转换
        int countUb = walletService.updateBalanceByAddrInRowLock(walletDTO.getAddr(), walletDTO.getTokenId(), freeBalance, freezeBalance, BigDecimal.ZERO, new Date());
        if (countUb != 1) {
            throw new ServiceException(ExceptionEnums.TRANSFER_ERROR);
        }

        //返回加减余额后的数据
        return walletService.selectByUserOpenId(walletOrderDTO.getUserId(), tokenId, walletOrderDTO.getWalletType());
    }

    @Override
    @Transactional
    @TxTransaction
    public Integer handleChange(WalletChangeDTO walletChangeDTO) {
        BigDecimal freeBalance = walletChangeDTO.getFreeBalance();
        BigDecimal freezeBalance = walletChangeDTO.getFreezeBalance();
        BigDecimal totalBalance = freeBalance.add(freezeBalance);

        Date now = new Date();

        int tokenId = tokenService.getAndVerifyTokenIdByName(walletChangeDTO.getTokenName());
        //查询用户钱包
        WalletDTO walletDTO = walletService.selectByUserOpenId(walletChangeDTO.getUserId(), tokenId, walletChangeDTO.getWalletType());

        boolean freeFlag = walletDTO.getFreeBalance().add(freeBalance).compareTo(BigDecimal.ZERO) < 0;
        boolean freezeFlag = walletDTO.getFreezeBalance().add(freezeBalance).compareTo(BigDecimal.ZERO) < 0;
        if (freeFlag || freezeFlag) {
            throw new ServiceException(ExceptionEnums.BALANCE_NOT_ENOUGH);
        }

        if (walletChangeDTO.getFreeBalance().compareTo(BigDecimal.ZERO) != 0 || walletChangeDTO.getFreezeBalance().compareTo(BigDecimal.ZERO) != 0) {
            //加减钱包可用余额、冻结余额、总额
            int countUb = walletService.updateBalanceByAddrInRowLock(walletDTO.getAddr(), walletDTO.getTokenId(), freeBalance, freezeBalance, totalBalance, now);
            if (countUb != 1) {
                throw new ServiceException(ExceptionEnums.TRANSFER_ERROR);
            }
        }

        //插入一条交易记录
        WalletTransfer walletTransfer = new WalletTransfer();
        if (totalBalance.compareTo(BigDecimal.ZERO) < 0) {
            walletTransfer.setFromAddr(walletDTO.getAddr());
            walletTransfer.setToAddr(null);
        } else {
            walletTransfer.setFromAddr(null);
            walletTransfer.setToAddr(walletDTO.getAddr());
        }
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setHash(walletChangeDTO.getRecordId());
        walletTransfer.setAmount(totalBalance.abs());
        walletTransfer.setTokenId(tokenId);
        walletTransfer.setTokenSymbol(walletChangeDTO.getTokenName());
        walletTransfer.setGasPrice(walletChangeDTO.getGasBalance());
        if (StringUtils.isNotEmpty(walletChangeDTO.getTransferType())) {
            walletTransfer.setTransferType(walletChangeDTO.getTransferType());
        } else {
            walletTransfer.setTransferType(walletChangeDTO.getWalletType());
        }
        walletTransfer.setStatus(TransferConstans.STATUS_SUCCESS);
        walletTransfer.setCreateTime(now);
        walletTransfer.setUpdateTime(now);
        int countIt = insertTransfer(walletTransfer);

        if (countIt != 1) {
            throw new ServiceException(ExceptionEnums.TRANSFER_ERROR);
        }

        return 1;
    }

    @Override
    @Transactional
    public WalletDTO handleWithdraw(String userOpenId, String password, String toAddress, Integer tokenId, BigDecimal amount,
                                    String walletType, String verifyCode, String account) {
        ExceptionPreconditionUtils.checkStringNotBlank(verifyCode, new ServiceException(ExceptionEnums.WITHDRAW_CODE_NULL));

        //校验钱包类型
        applicationService.checkWalletType(walletType);
        //校验币种id
        String tokenSymbol = tokenService.getAndVerifyTokenNameById(tokenId);

        //获取提现手续费
        GasDTO gasDTO = walletParamService.getGasConfig(tokenSymbol);
        //与最小提现数额做对比
        if (gasDTO.getMinWdAmount().compareTo(amount) > 0) {
            throw new ServiceException(ExceptionEnums.LOW_WITHDRAW_AMOUNT_ERROR);
        }

        //检验密码
        CheckEthFeginResult.checkIsPassword(ethServerFeign.isPassword(password));
        //检验提现验证码
        ResultDTO verifyWithdrawSms = userServerFeign.verifyWithdrawSms(verifyCode, account);
        if (verifyWithdrawSms == null) {
            throw new ServiceException(ExceptionEnums.SERVER_IS_TOO_BUSY);
        }
        if (verifyWithdrawSms.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(verifyWithdrawSms);
        }

        //验证提现地址是否有效
        boolean addrValid = false;
        try {
            addrValid = coinUtils.validateAddress(toAddress);
        } catch (Exception e) {
            throw new ServiceException(ExceptionEnums.ADDRESS_ERROR);
        }
        //验证提现地址是否有效
        if (!addrValid) {
            throw new ServiceException(ExceptionEnums.ADDRESS_ERROR);
        }

        // 查询是否存在提现黑名单中
        // 抛出错误表示用户禁止提现
        userServerFeign.verifyBanWithdraw(userOpenId);

        //获取钱包余额
        WalletDTO walletDTO = walletService.selectByUserOpenId(userOpenId, tokenId, walletType);
        if (walletDTO.getFreeBalance().add(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(ExceptionEnums.FREEBALANCE_NOT_ENOUGH);
        }
        //用户转出钱包地址
        String fromAddress = walletDTO.getAddr();

        Date now = new Date();

        //验证提现地址是属于节点钱包
        JSONObject addrInfo = null;
        try {
            addrInfo = coinUtils.getAddressInfo(toAddress);
        } catch (Exception e) {
            throw new ServiceException(ExceptionEnums.SERVER_IS_TOO_BUSY);
        }
        //判断是否属于节点钱包
        if (addrInfo.getBoolean("ismine")) {
            //*********** 是，快速转账，数据库划转 ***********

            //该用户减去提现可用余额、总额
            int countUb = walletService.updateBalanceByAddrInRowLock(fromAddress, tokenId, amount.negate(), BigDecimal.ZERO, amount.negate(), now);
            if (countUb != 1) {
                throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
            }
            //接收用户加上可用余额、总额
            int countUbR = walletService.updateBalanceByAddrInRowLock(toAddress, tokenId, amount, BigDecimal.ZERO, amount, now);
            if (countUbR != 1) {
                throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
            }

            //并插入一条提现记录，站内快速转账
            WalletTransfer walletTransfer = new WalletTransfer();
            walletTransfer.setId(UUID.randomUUID().toString());
//            walletTransfer.setHash(null);
            walletTransfer.setFromAddr(fromAddress);
            walletTransfer.setToAddr(toAddress);
            walletTransfer.setAmount(amount.abs());
//            walletTransfer.setGasPrice(null);
            walletTransfer.setTokenId(tokenId);
            walletTransfer.setTokenSymbol(tokenSymbol);
            walletTransfer.setTransferType(TransferConstans.TYPE_FAST);
            walletTransfer.setStatus(TransferConstans.STATUS_SUCCESS);
            walletTransfer.setCreateTime(now);
            walletTransfer.setUpdateTime(now);
            int countIt = insertTransfer(walletTransfer);
            if (countIt != 1) {
                throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
            }
        } else {
            //*********** 否，区块链转账 ***********

            // 判断用户是否存在提现白名单中
            ResultDTO<Boolean> resultDTO = userServerFeign.verifyFreeWithdraw(userOpenId);
            if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS)
                throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
            // 用户存在提现白名单中，设置提现手续费为零
            if (resultDTO.getData()) gasDTO.setGasPrice(BigDecimal.ZERO);
            // 余额加上扣除手续费
            //该用户减去提现可用余额、加上冻结余额
            int countUb = walletService.updateBalanceByAddrInRowLock(fromAddress, tokenId, amount.negate(), amount, BigDecimal.ZERO, now);
            if (countUb != 1) {
                throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
            }
            //并插入一条提现记录
            WalletTransfer walletTransfer = new WalletTransfer();
            walletTransfer.setId(UUID.randomUUID().toString());
//            walletTransfer.setHash(null);
            walletTransfer.setFromAddr(fromAddress);
            walletTransfer.setToAddr(toAddress);
            walletTransfer.setAmount(amount.abs());
            walletTransfer.setTokenId(tokenId);
            walletTransfer.setTokenSymbol(tokenSymbol);
            //设置手续费
            walletTransfer.setGasPrice(gasDTO.getGasPrice());
            walletTransfer.setGasTokenType(gasDTO.getGasTokenType());
            walletTransfer.setGasTokenSymbol(gasDTO.getGasTokenSymbol());
            walletTransfer.setGasTokenName(gasDTO.getGasTokenName());
            walletTransfer.setTransferType(TransferConstans.TYPE_OUT);
            walletTransfer.setStatus(TransferConstans.STATUS_FIRST_TRIAL);
            walletTransfer.setCreateTime(now);
            walletTransfer.setUpdateTime(now);
            int countIt = insertTransfer(walletTransfer);
            if (countIt != 1) {
                throw new ServiceException(ExceptionEnums.WITHDRAW_ERROR);
            }
        }

        NotifyOutSMS notifyOutSMS = new NotifyOutSMS();
        notifyOutSMS.setUserId(userOpenId);
        notifyOutSMS.setAmount(amount.stripTrailingZeros().toPlainString());
        notifyOutSMS.setCoin(tokenSymbol);
        //异步通知
        asyncOptionUtils.notifyOut(userServerFeign, notifyOutSMS);

        //返回加减余额后的数据
        return walletService.selectByAddr(fromAddress, tokenId, walletType);
    }

    @Override
    public List<WalletTransfer> selectByTxTypeAndStatus(String transferType, int Status, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);  // 分页处理
        WalletTransfer where = new WalletTransfer();
        where.setTransferType(transferType);
        where.setStatus(Status);
        return walletTransferMapper.select(where);
    }

    @Override
    @Transactional
    public void updateStatus(String id, int status, Date date) {
        WalletTransfer ethWalletTransfer = walletTransferMapper.selectByPrimaryKey(id);
        ethWalletTransfer.setStatus(status);
        ethWalletTransfer.setUpdateTime(date);
        // 失败抛出异常
        int row = walletTransferMapper.updateByPrimaryKeySelective(ethWalletTransfer);
        if (row == 0) {
            throw new ServiceException(ExceptionEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
