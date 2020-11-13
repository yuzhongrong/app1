package com.blockchain.server.btc.service.impl;

import com.blockchain.server.btc.common.enums.BtcEnums;
import com.blockchain.server.btc.common.exception.BtcException;
import com.blockchain.server.btc.dto.BtcApplicationDTO;
import com.blockchain.server.btc.mapper.BtcApplicationMapper;
import com.blockchain.server.btc.service.BtcApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BtcApplicationServiceImpl implements BtcApplicationService {
    @Autowired
    private BtcApplicationMapper btcApplicationMapper;

    @Override
    public List<BtcApplicationDTO> listApplication() {
        return btcApplicationMapper.listApplication();
    }

    @Override
    public void checkWalletType(String walletType) {
        List<BtcApplicationDTO> list = listApplication();
        for (BtcApplicationDTO row : list) {
            if (walletType.equalsIgnoreCase(row.getAppId())) {
                return;
            }
        }
        throw new BtcException(BtcEnums.INEXISTENCE_WALLETTYPE);
    }

}
