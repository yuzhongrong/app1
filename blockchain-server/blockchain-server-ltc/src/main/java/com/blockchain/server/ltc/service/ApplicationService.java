package com.blockchain.server.ltc.service;

import com.blockchain.server.ltc.dto.ApplicationDTO;

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
