package com.blockchain.server.otc.feign;

import com.blockchain.common.base.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "dapp-user-server", path = "/user/inner")
public interface PushFeign {

    /***
     * 对单个用户推送消息
     * @param userId 用户id
     * @param pushType 推送通知信息类型
     * @param payload 透传数据（通知附带数据）
     * @return
     */
    @PostMapping("/push/pushToSingle")
    ResultDTO pushToSingle(@RequestParam("/userId") String userId,
                           @RequestParam("/pushType") String pushType,
                           @RequestBody Map<String, Object> payload);
}
