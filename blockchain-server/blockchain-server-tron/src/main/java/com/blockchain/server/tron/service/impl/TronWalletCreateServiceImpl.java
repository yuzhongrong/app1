package com.blockchain.server.tron.service.impl;

import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.entity.TronWalletCreate;
import com.blockchain.server.tron.mapper.TronWalletCreateMapper;
import com.blockchain.server.tron.service.TronWalletCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Harvey Luo
 * @date 2019/6/21 11:46
 */
@Service
public class TronWalletCreateServiceImpl implements TronWalletCreateService {

    @Autowired
    private TronWalletCreateMapper tronWalletCreateMapper;

    /**
     * 查询创建钱包资金账户
     * @return
     */
    @Override
    public TronWalletCreate selectByOne() {
        TronWalletCreate tronWalletCreate = tronWalletCreateMapper.selectByStatus(TronConstant.WalletAccountStatus.TRON_WALLET_USABLE_USABLE);
        if (tronWalletCreate == null) return null;
        tronWalletCreate.setPrivateKey(RSACoderUtils.decryptPassword(tronWalletCreate.getPrivateKey()));
        return tronWalletCreate;
    }
}
