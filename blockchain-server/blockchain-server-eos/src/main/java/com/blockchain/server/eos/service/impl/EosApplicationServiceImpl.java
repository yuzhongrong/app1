package com.blockchain.server.eos.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.entity.Application;
import com.blockchain.server.eos.mapper.ApplicationMapper;
import com.blockchain.server.eos.service.EosApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/26 18:22
 * @user WIN10
 */
@Service
public class EosApplicationServiceImpl implements EosApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

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
    public List<Application> listEosApplication() {
        return applicationMapper.selectAll();
    }
}
