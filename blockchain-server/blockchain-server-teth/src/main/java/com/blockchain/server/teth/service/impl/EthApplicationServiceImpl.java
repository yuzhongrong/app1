package com.blockchain.server.teth.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.teth.common.enums.EthWalletEnums;
import com.blockchain.server.teth.common.exception.EthWalletException;
import com.blockchain.server.teth.entity.EthApplication;
import com.blockchain.server.teth.mapper.EthApplicationMapper;
import com.blockchain.server.teth.service.IEthApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 以太坊币种表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthApplicationServiceImpl implements IEthApplicationService {

    @Autowired
    EthApplicationMapper ethApplicationMapper;

    @Override
    public void CheckWalletType(String appId) {
        ExceptionPreconditionUtils.checkStringNotBlank(appId, new EthWalletException(EthWalletEnums.NULL_WALLETTYPE));
        List<EthApplication> list = selectAll();
        for (EthApplication row : list) {
            if (appId.equalsIgnoreCase(row.getAppId())) {
                return;
            }
        }
        throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLETTYPE);
    }


    @Override
    public List<EthApplication> selectAll() {
        return ethApplicationMapper.selectAll();
    }
}
