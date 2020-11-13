package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.dto.PushInfoDTO;
import com.blockchain.common.base.enums.PushEnums;
import com.blockchain.server.user.common.utils.push.PushUtils;
import com.blockchain.server.user.entity.PushUser;
import com.blockchain.server.user.service.PushService;
import com.blockchain.server.user.service.PushUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private PushUtils pushUtils;
    @Autowired
    private PushUserService pushUserService;

    @Override
    public void pushToSingle(String userId, String pushType, Map<String, Object> payLoadMap) {
        PushUser pushUser = pushUserService.selectByUserId(userId);
        //用户存在并且客户端ID不为空
        if (pushUser != null && StringUtils.isNotBlank(pushUser.getClientId())) {
            //根据pushType获取推送信息枚举
            PushEnums pushEnums = PushEnums.getEnumByPushType(pushType);
            //获取推送内容对象
            PushInfoDTO pushInfo = new PushInfoDTO(pushEnums, pushUser.getLanguage());
            //推送消息
            pushUtils.pushToSingle(pushUser.getClientId(), pushInfo.getPushTitle(), pushInfo.getPushContent(), payLoadMap);
        }
    }

}
