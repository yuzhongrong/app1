package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.dto.NotifyOutSMS;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constants.sql.ConfigConstant;
import com.blockchain.server.user.common.utils.SendSmsgCode;
import com.blockchain.server.user.entity.Config;
import com.blockchain.server.user.entity.UserMain;
import com.blockchain.server.user.mapper.ConfigMapper;
import com.blockchain.server.user.service.ConfigService;
import com.blockchain.server.user.service.UserMainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangxl
 * @create 2019-02-25 17:50
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private SendSmsgCode sendSmsgCode;

    @Override
    public String getValidValueByKey(String key) {
        ExceptionPreconditionUtils.notEmpty(key);
        Config config = new Config();
        config.setDataKey(key);
        config.setDataStatus(ConfigConstant.STATUS_YES);
        Config exist = configMapper.selectOne(config);
        if (exist != null) {
            return exist.getDataValue();
        }
        return null;
    }

    @Override
    public void notifyOut(NotifyOutSMS notifyOutSMS) {
        //查询通知号码
        Config config = new Config();
        config.setDataKey(ConfigConstant.USER_OUT_NOTIFY_MOBILE);
        config.setDataStatus(ConfigConstant.STATUS_YES);
        Config exist = configMapper.selectOne(config);
        if (exist == null) return;
        if (StringUtils.isBlank(exist.getDataValue())) return;
        //查询提现用户名
        UserMain userMain = userMainService.selectById(notifyOutSMS.getUserId());
        if (userMain == null) return;
        //短信通知
        sendSmsgCode.notifyOut(exist.getDataValue(), userMain.getMobilePhone(), notifyOutSMS);
    }

}
