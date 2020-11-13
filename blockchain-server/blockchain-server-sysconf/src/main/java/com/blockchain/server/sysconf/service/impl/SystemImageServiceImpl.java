package com.blockchain.server.sysconf.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.server.sysconf.entity.SystemImage;
import com.blockchain.server.sysconf.mapper.SystemImageMapper;
import com.blockchain.server.sysconf.service.SystemImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**c
 * @author: Liusd
 * @create: 2019-03-25 13:55
 **/
@Service
public class SystemImageServiceImpl implements SystemImageService {

    private static final Logger LOG = LoggerFactory.getLogger(SystemImageServiceImpl.class);
    @Autowired
    private SystemImageMapper systemImageMapper;

    @Override
    public List<SystemImage> systemImageList(String type, String status, String group,String  language) {
        LOG.info("language is:"+language);
        if(!language.equals(BaseConstant.USER_LOCALE_EN_US))
            language=BaseConstant.USER_LOCALE_DEFAULT;
        return systemImageMapper.selectByTypeAndStatus(type, status, group,   language);
    }

}
