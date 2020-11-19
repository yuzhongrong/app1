package com.blockchain.server.cmc.service.impl;

import com.blockchain.server.cmc.common.enums.ExceptionEnums;
import com.blockchain.server.cmc.common.exception.ServiceException;
import com.blockchain.server.cmc.dto.ApplicationDTO;
import com.blockchain.server.cmc.mapper.ApplicationMapper;
import com.blockchain.server.cmc.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public List<ApplicationDTO> listApplication() {
        return applicationMapper.listApplication();
    }

    @Override
    public void checkWalletType(String walletType) {
        List<ApplicationDTO> list = listApplication();
        for (ApplicationDTO row : list) {
            if (walletType.equalsIgnoreCase(row.getAppId())) {
                return;
            }
        }
        throw new ServiceException(ExceptionEnums.INEXISTENCE_WALLETTYPE);
    }

}
