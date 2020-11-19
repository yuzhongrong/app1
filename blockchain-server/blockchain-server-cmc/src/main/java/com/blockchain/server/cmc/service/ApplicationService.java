package com.blockchain.server.cmc.service;

import com.blockchain.server.cmc.dto.ApplicationDTO;

import java.util.List;

public interface ApplicationService {

    /**
     * 获取应用列表
     *
     * @return
     */
    List<ApplicationDTO> listApplication();

    /**
     * 验证是否有该应用体系的钱包
     *
     * @param walletType 应用标识
     */
    void checkWalletType(String walletType);

}
