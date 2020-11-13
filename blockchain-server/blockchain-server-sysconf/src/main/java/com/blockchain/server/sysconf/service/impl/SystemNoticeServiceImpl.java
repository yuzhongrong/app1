package com.blockchain.server.sysconf.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.server.sysconf.common.constants.NoticeConstant;
import com.blockchain.server.sysconf.entity.SystemNotice;
import com.blockchain.server.sysconf.mapper.SystemNoticeMapper;
import com.blockchain.server.sysconf.service.SystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:55
 **/
@Service
public class SystemNoticeServiceImpl implements SystemNoticeService {

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;


    @Override
    public List<SystemNotice> systemNoticeList(String languages) {
        return systemNoticeMapper.selectByStatusAndLanguages(NoticeConstant.STATUS_SHOW,languages);
    }
}
