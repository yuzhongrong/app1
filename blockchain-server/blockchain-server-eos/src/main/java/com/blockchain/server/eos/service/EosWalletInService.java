package com.blockchain.server.eos.service;

import com.blockchain.server.eos.dto.WalletInDTO;
import com.blockchain.server.eos.entity.WalletIn;

import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 16:39
 * @user WIN10
 */
public interface EosWalletInService {

    /**
     * 查询对应的代币名称充值账户
     * @param tokenName
     * @return
     */
    WalletInDTO selectWalletInAccount(String tokenName);

    /**
     * 查询充值资金账户
     * @param to
     * @return
     */
    List<WalletInDTO> listWalletInByAccountName(String to);

    /**
     * 充值方法
     * @param listTokenName
     */
    void handleAccountBlockTransactions(HashSet<String> listTokenName);

    /**
     * 根据账号查询信息
     * @param account
     * @return
     */
    WalletIn selectByAccountName(String account);

}
