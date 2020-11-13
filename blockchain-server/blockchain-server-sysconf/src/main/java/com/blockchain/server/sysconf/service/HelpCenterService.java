package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.HelpCenter;

import java.util.List;

/**
 * 帮助中心 服务层
 * 
 * @author ruoyi
 * @date 2018-10-30
 */
public interface HelpCenterService {

	List<HelpCenter> selectHelpCenterForApp(String userLocale);

	List<HelpCenter> selectHelpCenterForPc(String title, String userLocale);

	String selectContentById(String id);
	
}
