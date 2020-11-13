package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.SystemImage;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:54
 **/
public interface SystemImageService {
    List<SystemImage> systemImageList(String type, String status, String group,String  language);
}
