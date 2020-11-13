package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.ContactUs;

import java.util.List;

public interface ContactUsService {


    /**
     * 查询联系我们信息列表
     *
     * @return
     */
    List<ContactUs> listAll(Integer showStatus, String userLocal);



}
