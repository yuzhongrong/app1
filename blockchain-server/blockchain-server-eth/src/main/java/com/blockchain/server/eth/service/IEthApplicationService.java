package com.blockchain.server.eth.service;

import com.blockchain.server.eth.dto.EthWalletDTO;
import com.blockchain.server.eth.entity.EthApplication;
import com.blockchain.server.eth.entity.EthWallet;

import java.util.List;

/**
 * 以太坊钱包应用提现表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthApplicationService {
    /**
     * 验证是否有该应用体系的钱包
     *
     * @param appId 应用标识
     */
    void CheckWalletType(String appId);

    /**
     * 查询所有钱包应用类型
     *
     * @return
     */
    List<EthApplication> selectAll();

}
