package com.blockchain.server.user.service;


import com.blockchain.server.user.common.enums.SmsCountEnum;

public interface SmsCountService {
    /**
     * 是否能发送短信
     *
     * @return
     */
    void handleInsertSmsCode(String phone, String internationalCode, SmsCountEnum type);

}
