package com.blockchain.server.ltc.service.impl;

import com.blockchain.server.ltc.common.enums.ExceptionEnums;
import com.blockchain.server.ltc.common.exception.ServiceException;
import com.blockchain.server.ltc.dto.ApplicationDTO;
import com.blockchain.server.ltc.mapper.ApplicationMapper;
import com.blockchain.server.ltc.service.ApplicationService;
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
