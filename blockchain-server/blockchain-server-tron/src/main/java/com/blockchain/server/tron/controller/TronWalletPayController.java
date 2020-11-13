package com.blockchain.server.tron.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.server.tron.dto.UserBaseDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.tron.controller.api.TronWalletPayApi;
import com.blockchain.server.tron.dto.PayInsertOutDTO;
import com.blockchain.server.tron.dto.UserWalletInfoDTO;
import com.blockchain.server.tron.feign.UserServerFegin;
import com.blockchain.server.tron.service.TronWalletPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Harvey
 * @date 2019/3/23 11:33
 */
//@Api(TronWalletPayApi.MARKET_CONTROLLER_API)
//@RestController
public class TronWalletPayController {

    @Autowired
    private TronWalletPayService walletPayService;
    @Autowired
    private UserServerFegin userServerFegin;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = TronWalletPayApi.Pay.METHOD_API_NAME, notes = TronWalletPayApi.Pay.METHOD_API_NOTE)
    @PostMapping("/pay")
    public ResultDTO pay(
            @ApiParam(TronWalletPayApi.Pay.METHOD_API_PASSWORD) @RequestParam("password") String password,
            @ApiParam(TronWalletPayApi.Pay.METHOD_API_PREPAYID) @RequestParam("prePayId") String prePayId,
            HttpServletRequest request
    ) {
        String userId = SSOHelper.getUser(redisTemplate, request).getId();
        PayInsertOutDTO payInsertOutDTO = walletPayService.insertOrder(userId, prePayId, password);
        return ResultDTO.requstSuccess(payInsertOutDTO);
    }

    @ApiOperation(value = TronWalletPayApi.CheckToken.METHOD_API_NAME, notes = TronWalletPayApi.CheckToken.METHOD_API_NOTE)
    @PostMapping("/checkToken")
    public ResultDTO checkToken(
            @ApiParam(TronWalletPayApi.CheckToken.METHOD_API_APPID) @RequestParam("appId") String appId,
            @ApiParam(TronWalletPayApi.CheckToken.METHOD_API_APPSECRET) @RequestParam("appSecret") String appSecret,
            HttpServletRequest request
    ) {
        SessionUserDTO userDTO = SSOHelper.getUser(redisTemplate, request);
        ResultDTO<UserBaseDTO> data = userServerFegin.getUserById(userDTO.getId());
        if (data.getCode() != BaseResultEnums.SUCCESS.getCode()) {
            throw new RPCException(data);
        }
        UserWalletInfoDTO userWalletInfoDTO = walletPayService.checkToken(appId, appSecret, userDTO.getId());
        userWalletInfoDTO.setTel(userDTO.getTel());
        userWalletInfoDTO.setNickname(data.getData().getNickName());
        return ResultDTO.requstSuccess(userWalletInfoDTO);
    }

}
