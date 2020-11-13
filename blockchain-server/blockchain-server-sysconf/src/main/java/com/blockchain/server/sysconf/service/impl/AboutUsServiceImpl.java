package com.blockchain.server.sysconf.service.impl;


import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.server.sysconf.entity.AboutUs;
import com.blockchain.server.sysconf.mapper.AboutUsMapper;
import com.blockchain.server.sysconf.service.AboutUsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("aboutUsService")
public class AboutUsServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsMapper aboutUsMapper;

    @Override
    public List<AboutUs> listAll() {
        return aboutUsMapper.listAllOrderByCreateTimeDesc();
    }

    @Override
    public AboutUs findNewestAboutUs(String languages) {
        if(StringUtils.isEmpty(languages)){
           languages = BaseConstant.USER_LOCALE_DEFAULT;
        }
        return aboutUsMapper.findNewestAboutUs(languages);
    }


}
