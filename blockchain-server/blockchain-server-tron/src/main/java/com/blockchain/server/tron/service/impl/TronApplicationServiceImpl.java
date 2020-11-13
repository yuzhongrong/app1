package com.blockchain.server.tron.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.entity.TronApplication;
import com.blockchain.server.tron.mapper.TronApplicationMapper;
import com.blockchain.server.tron.service.TronApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/26 18:22
 * @user WIN10
 */
@Service
public class TronApplicationServiceImpl implements TronApplicationService {

    @Autowired
    private TronApplicationMapper applicationMapper;

    /**
     * 查询是否存在该应用钱包
     * @param walletType
     * @return
     */
    @Override
    public Integer selectWalletCountByWalletType(String walletType) {
        ExceptionPreconditionUtils.notEmpty(walletType);
        return applicationMapper.selectWalletCountByWalletType(walletType);
    }

    /**
     * 查找所有应用信息
     * @return
     */
    @Override
    public List<TronApplication> listTronApplication() {
        return applicationMapper.selectAll();
    }

    @Override
    public void checkWalletType(String walletType) {
        List<TronApplication> list = listTronApplication();
        for (TronApplication row : list) {
            if (walletType.equalsIgnoreCase(row.getAppId())) {
                return;
            }
        }
        throw new TronWalletException(TronWalletEnums.INEXISTENCE_WALLETTYPE);
    }
}
