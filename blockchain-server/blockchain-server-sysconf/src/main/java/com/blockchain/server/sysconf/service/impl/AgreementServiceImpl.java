package com.blockchain.server.sysconf.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.server.sysconf.entity.Agreement;
import com.blockchain.server.sysconf.mapper.AgreementMapper;
import com.blockchain.server.sysconf.service.AgreementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private AgreementMapper agreementMapper;

    @Override
    public Agreement findAgreement(String type,String languages) {
        if(StringUtils.isEmpty(languages)){
            languages = BaseConstant.USER_LOCALE_DEFAULT;
        }
        return agreementMapper.findAgreement(type,languages);
    }


}
