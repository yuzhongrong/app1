package com.blockchain.server.user.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.enums.PushEnums;
import com.blockchain.server.user.inner.api.PushInnerApi;
import com.blockchain.server.user.service.PushService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inner/push")
public class PushInner {

    @Autowired
    private PushService pushService;

    @ApiOperation(value = PushInnerApi.PushToSingle.METHOD_NAME,
            notes = PushInnerApi.PushToSingle.METHOD_NOTE)
    @PostMapping("/pushToSingle")
    public ResultDTO pushToSingle(@ApiParam(PushInnerApi.PushToSingle.METHOD_API_USER_ID) @RequestParam("/userId") String userId,
                                  @ApiParam(PushInnerApi.PushToSingle.METHOD_API_PUSH_TYPE) @RequestParam("/pushType") String pushType,
                                  @ApiParam(PushInnerApi.PushToSingle.METHOD_API_PAYLOAD) @RequestBody Map<String, Object> payload) {
        pushService.pushToSingle(userId, pushType, payload);
        return ResultDTO.requstSuccess();
    }

}
