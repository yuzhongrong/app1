package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.SystemNotice;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:54
 **/
public interface SystemNoticeService {

    List<SystemNotice> systemNoticeList(String languages);
}
