package com.blockchain.server.sysconf.service;


import com.blockchain.server.sysconf.entity.AboutUs;

import java.util.List;

public interface AboutUsService {

    /**
     * 查询关于我们信息列表 按照创建时间倒叙（后台）
     * @return
     */
    List<AboutUs> listAll();

    /**
     * 查询关于我们信息 (客户端)
     * @param languages
     * @return
     */
    AboutUs findNewestAboutUs(String languages);

}
