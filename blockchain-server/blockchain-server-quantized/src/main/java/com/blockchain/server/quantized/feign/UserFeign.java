package com.blockchain.server.quantized.feign;


import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.annotation.BypassedFeign;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * 用户微服务
 *
 * @author huangxl
 * @create 2019-02-28 17:39
 */
@FeignClient("dapp-user-server")
@BypassedFeign
public interface UserFeign {
    @PostMapping("/user/inner/validateSmsg")
     ResultDTO validateSmsg(@RequestParam("verifyCode") String verifyCode,@RequestParam("phone") String phone);
}
