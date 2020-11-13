package com.blockchain.server.tron.service;

import com.blockchain.server.tron.entity.TronWalletKey;

import java.util.Set;

/**
 * @author Harvey Luo
 * @date 2019/6/13 13:59
 */
public interface TronWalletKeyService {

    /**
     * 保存数据
     * @param userOpenId
     * @param addr
     * @param hexAddr
     * @param privateKey
     * @param keystore
     * @return
     */
    TronWalletKey insert(String userOpenId, String addr, String hexAddr, String privateKey, String keystore);

    /**
     * 通过base58查询地址对象
     * @param addr
     * @return
     */
    TronWalletKey selectByAddr(String addr);

    /**
     * 获取所有16进制地址
     * @return
     */
    Set<String> getWalletHexAddrs();
}
