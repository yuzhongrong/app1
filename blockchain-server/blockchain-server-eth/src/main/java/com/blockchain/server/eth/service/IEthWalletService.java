package com.blockchain.server.eth.service;

import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.common.base.dto.WalletBalanceDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.eth.dto.EthWalletDTO;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.entity.EthWalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthWalletService {
    /**
     * 自定义条件查询钱包信息
     *
     * @param ethWallet 条件
     * @return
     */
    EthWallet select(EthWallet ethWallet);

    /**
     * 自定义条件查询钱包信息
     *
     * @param ethWallet 条件
     * @return
     */
    List<EthWallet> selects(EthWallet ethWallet);

    /**
     * 创建某应用类型指定的某类币种钱包
     *
     * @param userOpenId 用户标识
     * @param tokenAddr  币种地址
     * @param walletType 钱包类型
     * @param pass       钱包密码
     * @return
     */
    EthWalletDTO insert(String userOpenId, String tokenAddr, String walletType, String pass);

    /**
     * 创建某应用类型所有币种钱包
     *
     * @param userOpenId 用户标识
     * @param walletType 钱包类型
     * @param pass       钱包密码
     * @return
     */
    List<EthWalletDTO> insertByWalletTypeAll(String userOpenId, String walletType, String pass);

    /**
     * 初始化所有钱包
     *
     * @param userOpenId 用户ID
     * @return
     */
    void insertInit(String userOpenId);

    /**
     * 根据用户ID，币种地址，钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param tokenAddr  币种地址
     * @param walletType 钱包类型
     * @return
     */
    EthWalletDTO selectByUserOpenIdAndTokenAddrAndWalletType(String userOpenId, String tokenAddr, String walletType);

    /**
     * 根据钱包地址，币种地址查询钱包信息
     *
     * @param addr      钱包地址
     * @param tokenAddr 币种地址
     * @return
     */
    EthWalletDTO selectByAddrAndTokenAddr(String addr, String tokenAddr);

    /**
     * 根据钱包地址，币种地址，钱包类型查询钱包信息
     *
     * @param addr       钱包地址
     * @param tokenAddr  币种地址
     * @param walletType 钱包类型
     * @return
     */
    EthWalletDTO selectByAddrAndTokenAddrAndWalletType(String addr, String tokenAddr, String walletType);

    /**
     * 根据用户ID，币种地址，钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param walletType 钱包类型
     * @return
     */
    List<EthWalletDTO> selectByUserOpenIdAndWalletType(String userOpenId, String walletType);

    /**
     * 根据钱包地址，币种地址，钱包类型查询钱包信息
     *
     * @param addr       钱包地址
     * @param walletType 钱包类型
     * @return
     */
    List<EthWalletDTO> selectByAddrAndWalletType(String addr, String walletType);

    /**
     * 获取加密密码的公钥
     *
     * @param userOpenId
     * @return
     */
    String getPublicKey(String userOpenId);

    /**
     * 验证钱包密码是否正确
     *
     * @param userOpenId 用户标识
     * @param password   密码
     */
    void isPassword(String userOpenId, String password);

    /**
     * 重置钱包密码
     *
     * @param userOpenId 用户标识
     * @param password   钱包密码
     */
    void updatePassword(String userOpenId, String password);

    /**
     * 用户应用钱包的相互划转
     *
     * @param userOpenId     用户标识
     * @param tokenAddr      币种地址
     * @param formWalletType 支付的应用钱包
     * @param toWalletType   接收的应用钱包
     * @param amount         划转的金额
     * @param password       加密的密码
     * @return
     */
    EthWalletTransfer updateTypeTransfer(String userOpenId, String tokenAddr, String formWalletType,
                                         String toWalletType, String amount, String password);

    /**
     * 应用钱包充值业务
     *
     * @param ethWalletTransfer 充值记录
     */
    void updateBlanceTxIn(EthWalletTransfer ethWalletTransfer);

    /**
     * 提现申请
     *
     * @param userOpenId 用户标识
     * @param tokenAddr  币种地址
     * @param toAddr     提现地址
     * @param walletType 钱包类型
     * @param amount     提现额度
     * @param password   密码
     */
    EthWalletTransfer handleWelletOutApply(String userOpenId, String tokenAddr, String toAddr, String walletType,
                                           String amount, String password, String verifyCode, String account);

    /**
     * 钱包冻结与可用余额转换
     *
     * @param orderDTO
     */
    void updateBlanceTransform(WalletOrderDTO orderDTO);

    /**
     * 钱包余额变动
     *
     * @param changeDTO
     */
    void updateBlance(WalletChangeDTO changeDTO);

    /**
     * 提现失败处理
     *
     * @param tx
     */
    void updateTxOutError(EthWalletTransfer tx);

    /**
     * 提现成功处理
     *
     * @param tx
     */
    void updateTxOutSuccess(EthWalletTransfer tx);

    /**
     * 划转接口
     *
     * @param fromType 支付的钱包
     * @param toType   收款的钱包
     * @param coinName 币种名称
     * @param amount   金额
     * @return
     */
    EthWalletTransfer handleTransfer(String userId, String fromType, String toType, String coinName, BigDecimal amount);

    /**
     * 获取钱包余额，通过用户id，与钱包类型
     *
     * @param userOpenId  用户id
     * @param walletTypes 钱包类型
     * @return 币种对应的可用、冻结余额
     */
    List<WalletBalanceDTO> getBalanceByIdAndTypes(String userOpenId, String[] walletTypes);

    /**
     * 获取用户某个币种所有金额
     */
    BigDecimal getAllBalanceByToken(String userOpenId, String coinName);

    /**
     * 通过地址修改账户余额，行锁
     */
    void updateBalanceByAddrInRowLock(String addr, String tokenAddr, String walletType, BigDecimal freeBlance,
                                      BigDecimal freezeBlance, Date date);

    /**
     * 根据钱包类型获取全量的钱包信息
     * */
    void getBalanceByIdAndTypesBatch(String[] walletTypes);


    void updateBatchBlance(List<WalletChangeDTO> batchChangeList);

    /**
     * 外部GFC钱包充值
     * */
    String GFCRecharge(String telPhone, String amount, String checkCode);

    /*检查校验码是否匹配telPhone
    * */
    void checkPass(String telPhone, String checkCode);

    String getWalletPublicKey(String userName);
}
