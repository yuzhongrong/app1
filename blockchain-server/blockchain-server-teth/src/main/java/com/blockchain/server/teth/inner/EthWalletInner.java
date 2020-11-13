package com.blockchain.server.teth.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.teth.inner.api.EthWalletApi;
import com.blockchain.server.teth.service.IEthWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YH
 * @date 2018年11月6日15:51:56
 */
@Api(EthWalletApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/wallet")
public class EthWalletInner {
    private static final Logger LOG = LoggerFactory.getLogger(EthWalletInner.class);
    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation(value = EthWalletApi.IsPassword.METHOD_API_NAME, notes = EthWalletApi.IsPassword.METHOD_API_NOTE)
    @GetMapping("/isPassword")
    public ResultDTO isPassword(@ApiParam(EthWalletApi.IsPassword.METHOD_API_PASSWORD) @RequestParam(name = "password", required = false) String password,
                                HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        LOG.info("校验密码，密码：" + password);
        ethWalletService.isPassword(userId, password);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthWalletApi.GetPublicKey.METHOD_API_NAME, notes = EthWalletApi.GetPublicKey.METHOD_API_NOTE)
    @GetMapping("/getPublicKey")
    public ResultDTO getPublicKey(@ApiParam(EthWalletApi.GetPublicKey.METHOD_API_USERID) @RequestParam("userOpenId") String userOpenId, HttpServletRequest request) {
        LOG.info("获取公钥密码，userOpenId：" + userOpenId);
        return ResultDTO.requstSuccess(ethWalletService.getPublicKey(userOpenId));
    }

    @ApiOperation(value = EthWalletApi.InitWallets.METHOD_API_NAME, notes = EthWalletApi.InitWallets.METHOD_API_NOTE)
    @GetMapping("/initWallets")
    public ResultDTO initWallets(@ApiParam(EthWalletApi.InitWallets.METHOD_API_USERID) @RequestParam("userOpenId") String userOpenId) {
        LOG.info("获取公钥密码，userOpenId：" + userOpenId);
        ethWalletService.insertInit(userOpenId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthWalletApi.GetBalanceByIdAndTypes.METHOD_API_NAME, notes = EthWalletApi.GetBalanceByIdAndTypes.METHOD_API_NOTE)
    @GetMapping("/getBalanceByIdAndTypes")
    public ResultDTO getBalanceByIdAndTypes(@ApiParam(EthWalletApi.GetBalanceByIdAndTypes.USEROPENID) @RequestParam("userOpenId") String userOpenId,
                                            @ApiParam(EthWalletApi.GetBalanceByIdAndTypes.WALLET_TYPE) @RequestParam(name = "walletTypes", required = false) String[] walletTypes) {
        return ResultDTO.requstSuccess(ethWalletService.getBalanceByIdAndTypes(userOpenId, walletTypes));
    }

    @ApiOperation(value = EthWalletApi.GetAllBalanceByToken.METHOD_API_NAME, notes = EthWalletApi.GetAllBalanceByToken.METHOD_API_NOTE)
    @GetMapping("/getAllBalanceByToken")
    public ResultDTO getAllBalanceByToken(@ApiParam(EthWalletApi.GetAllBalanceByToken.USEROPENID) @RequestParam("userOpenId") String userOpenId,
                                          @ApiParam(EthWalletApi.GetAllBalanceByToken.COINNAME) @RequestParam("coinName") String coinName) {
        return ResultDTO.requstSuccess(ethWalletService.getAllBalanceByToken(userOpenId, coinName));
    }

    @PostMapping("/getBalanceByIdAndTypesBatch")
    ResultDTO getBalanceByIdAndTypesBatch(@RequestParam(name = "walletTypes", required = false)String[] walletTypes){
        ethWalletService.getBalanceByIdAndTypesBatch(walletTypes);
        return ResultDTO.requstSuccess();
    }

}
