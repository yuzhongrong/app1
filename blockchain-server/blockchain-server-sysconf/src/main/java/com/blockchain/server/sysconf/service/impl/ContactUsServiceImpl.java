package com.blockchain.server.sysconf.service.impl;


import com.blockchain.server.sysconf.common.constants.UserConstant;
import com.blockchain.server.sysconf.entity.ContactUs;
import com.blockchain.server.sysconf.mapper.ContactUsMapper;
import com.blockchain.server.sysconf.service.ContactUsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsServiceImpl implements ContactUsService{

    @Autowired
    private ContactUsMapper contactUsMapper;

    @Override
    public List<ContactUs> listAll(Integer showStatus, String userLocal) {
        //查询默认为中文语种
        if(StringUtils.isEmpty(userLocal)){
            userLocal = UserConstant.USER_LOCAL_CHINA;
        }
        return contactUsMapper.listAll(showStatus,userLocal);
    }

}
