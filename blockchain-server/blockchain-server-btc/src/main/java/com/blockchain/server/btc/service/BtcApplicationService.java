package com.blockchain.server.btc.service;

import com.blockchain.server.btc.dto.BtcApplicationDTO;

import java.util.List;

public interface BtcApplicationService {

    /**
     * 获取应用列表
     *
     * @return
     */
    List<BtcApplicationDTO> listApplication();

    /**
     * 验证是否有该应用体系的钱包
     *
     * @param walletType 应用标识
     */
    void checkWalletType(String walletType);

}
