package com.blockchain.server.teth.common.util;

import com.blockchain.common.base.dto.NotifyOutSMS;
import com.blockchain.server.teth.feign.UserFeign;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 异步操作处理
 */
@Component
@EnableAsync
public class AsyncOptionUtils {

    /**
     * 异步提现短信通知
     */
    @Async
    public void notifyOut(UserFeign userServerFegin, NotifyOutSMS notifyOutSMS) {
        userServerFegin.notifyOut(notifyOutSMS);
    }

}
