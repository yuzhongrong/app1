package com.blockchain.server.teth.service;

import com.blockchain.server.teth.entity.EthWalletKey;

import java.util.Set;

/**
 * 以太坊钱包主要信息表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthWalletKeyService {

    EthWalletKey findByUserOpenId(String userOpenId);

    /**
     * 是否存在密码
     *
     * @param userOpenId
     * @return
     */
    boolean existsPass(String userOpenId);

    EthWalletKey selectOne(EthWalletKey ethWalletKey);

    int insert(EthWalletKey ethWalletKey);

    EthWalletKey insert(String userOpenId);

    int update(EthWalletKey ethWalletKey);

    Set<String> selectAddrs();

    /**
     * 返回地址
     **/
    String isPassword(String userOpenId, String password);

    void updatePassword(String userOpenId, String password);

    void checkPass(String telPhone, String checkCode);
}
