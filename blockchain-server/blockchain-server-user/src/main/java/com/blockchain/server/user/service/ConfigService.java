package com.blockchain.server.user.service;

import com.blockchain.common.base.dto.NotifyOutSMS;

/**
 * @author huangxl
 * @create 2019-02-25 17:48
 */
public interface ConfigService {

    String getValidValueByKey(String key);

    /**
     * 用户提现短信通知
     */
    void notifyOut(NotifyOutSMS notifyOutSMS);

}
