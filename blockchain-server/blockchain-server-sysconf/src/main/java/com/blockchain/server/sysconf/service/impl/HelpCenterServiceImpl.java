package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.constants.ContactUsConstant;
import com.blockchain.server.sysconf.entity.HelpCenter;
import com.blockchain.server.sysconf.mapper.HelpCenterMapper;
import com.blockchain.server.sysconf.service.HelpCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 帮助中心 服务层实现
 *
 * @author ruoyi
 * @date 2018-10-30
 */
@Service
public class HelpCenterServiceImpl implements HelpCenterService {
	@Autowired
	private HelpCenterMapper helpCenterMapper;

	@Override
	public List<HelpCenter> selectHelpCenterForApp(String userLocale) {
		return helpCenterMapper.selectHelpCenterForApp(ContactUsConstant.STATUS_SHOW,userLocale);
	}

	@Override
	public List<HelpCenter> selectHelpCenterForPc(String title, String userLocale) {
		return helpCenterMapper.selectHelpCenterForPc(ContactUsConstant.STATUS_SHOW,title,userLocale);
	}

	@Override
	public String selectContentById(String id) {
		return helpCenterMapper.selectContentById(id);
	}
}
