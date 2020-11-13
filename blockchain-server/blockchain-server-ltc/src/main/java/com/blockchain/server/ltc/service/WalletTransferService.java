package com.blockchain.server.ltc.service;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.ltc.dto.WalletDTO;
import com.blockchain.server.ltc.dto.WalletTransferDTO;
import com.blockchain.server.ltc.entity.WalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletTransferService {

    /**
     * 插入一条钱包历史记录
     *
     * @param walletTransfer 记录对象
     * @return
     */
    Integer insertTransfer(WalletTransfer walletTransfer);

    /**
     * 获取用户钱包记录
     *
     * @param userOpenId 用户id
     * @param tokenId    币种id
     * @param walletType 应用类型，CCT
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<WalletTransferDTO> selectTransfer(String userOpenId, Integer tokenId, String walletType, Integer pageNum, Integer pageSize);

    /**
     * 修改钱包历史记录状态
     *
     * @param id     记录id
     * @param status 状态
     * @return
     */
    Integer updateStatusById(String id, int status);

    /**
     * 数据库不存在该笔交易
     *
     * @param txId 交易id
     * @return
     */
    Boolean isNotExistsTransferIn(String txId);

    /**
     * 处理同步区块充值
     *
     * @param walletTransfer 记录信息
     * @return
     */
    void handleBlockRecharge(WalletTransfer walletTransfer);

    /**
     * 处理CCT交易可用、冻结余额加减
     *
     * @param walletOrderDTO 币币交易，冻结、解冻参数
     * @return
     */
    WalletDTO handleOrder(WalletOrderDTO walletOrderDTO);

    /**
     * 处理加减可用、冻结余额，及其总额
     *
     * @param walletChangeDTO 变动参数
     * @return
     */
    Integer handleChange(WalletChangeDTO walletChangeDTO);

    /**
     * 提现
     *
     * @param userOpenId 用户id
     * @param password   加密密码
     * @param toAddress  接收地址
     * @param tokenId    币种id
     * @param amount     金额
     * @param walletType 应用类型，币币交易等
     */
    WalletDTO handleWithdraw(String userOpenId, String password, String toAddress, Integer tokenId, BigDecimal amount,
                             String walletType, String verifyCode, String account);

    /**
     * 根据记录类型与记录状态查询记录
     *
     * @param transferType 记录类型
     * @param Status       记录状态
     * @param pageNum      当前页数
     * @param pageSize     页数展示条数
     * @return
     */
    List<WalletTransfer> selectByTxTypeAndStatus(String transferType, int Status, int pageNum, int pageSize);

    /**
     * 修改记录状态
     *
     * @param id
     * @param status 记录状态
     */
    void updateStatus(String id, int status, Date date);
}
