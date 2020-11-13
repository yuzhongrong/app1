package com.blockchain.server.tron.service;

import com.blockchain.server.tron.entity.TronWalletKey;
import com.blockchain.server.tron.entity.TronWalletKeyInit;

/**
 * @author Harvey Luo
 * @date 2019/6/13 13:59
 */
public interface TronWalletKeyInitService {

    /**
     * 保存数据
     * @param addr
     * @param hexAddr
     * @param privateKey
     * @param keystore
     * @return
     */
    Integer insert(String addr, String hexAddr, String privateKey, String keystore);

    /**
     * 查询地址对象
     * @return
     */
    TronWalletKeyInit selectByLimitOne();

    /**
     * 根据addr删除地址信息
     * @param addr
     * @return
     */
    Integer deleteByAddr(String addr);

    /**
     * 创建地址
     */
    void dispose();

    /**
     * 同步数据
     */
    void synchronizationKeyInitDispose();

    /**
     * 保存对象
     * @param tronWalletKeyInit
     * @return
     */
    Integer insert(TronWalletKeyInit tronWalletKeyInit);
}
