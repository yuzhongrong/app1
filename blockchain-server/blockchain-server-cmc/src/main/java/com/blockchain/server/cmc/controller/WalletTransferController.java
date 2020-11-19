package com.blockchain.server.cmc.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cmc.common.constants.ApplicationConstans;
import com.blockchain.server.cmc.common.constants.TokenConstans;
import com.blockchain.server.cmc.controller.api.WalletTransferApi;
import com.blockchain.server.cmc.dto.WalletTransferDTO;
import com.blockchain.server.cmc.service.WalletTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = WalletTransferApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/walletTransfer")
public class WalletTransferController extends BaseController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WalletTransferService walletTransferService;


    @ApiOperation(value = WalletTransferApi.GetTransfer.METHOD_API_NAME, notes = WalletTransferApi.GetTransfer.METHOD_API_NOTE)
    @GetMapping("/getTransfer")
    public ResultDTO getTransfer(HttpServletRequest request,
                                 @ApiParam(WalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                 @ApiParam(WalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                 @ApiParam(WalletTransferApi.GetTransfer.TOKENID) @RequestParam(name = "tokenId", defaultValue = TokenConstans.TOKEN_PROPERTY_ID + "") Integer tokenId,
                                 @ApiParam(WalletTransferApi.GetTransfer.WALLET_TYPE) @RequestParam(value = "walletType", defaultValue = ApplicationConstans.TYPE_CCT) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(walletTransferService.selectTransfer(userOpenId, tokenId, walletType, pageNum, pageSize));
    }

    @ApiOperation(value = WalletTransferApi.pcGetTransfer.METHOD_API_NAME, notes = WalletTransferApi.pcGetTransfer.METHOD_API_NOTE)
    @GetMapping("/pcGetTransfer")
    public ResultDTO pcGetTransfer(HttpServletRequest request,
                                   @ApiParam(WalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                   @ApiParam(WalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                   @ApiParam(WalletTransferApi.pcGetTransfer.TOKENID) @RequestParam(name = "tokenId", defaultValue = TokenConstans.TOKEN_PROPERTY_ID + "") Integer tokenId,
                                   @ApiParam(WalletTransferApi.pcGetTransfer.WALLET_TYPE) @RequestParam(value = "walletType", defaultValue = ApplicationConstans.TYPE_CCT) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
//        //分页查询记录
//        PageHelper.startPage(pageNum, pageSize);
        List<WalletTransferDTO> list = walletTransferService.selectTransfer(userOpenId, tokenId, walletType, pageNum, pageSize);
        return generatePage(list);
    }

    @ApiOperation(value = WalletTransferApi.Withdraw.METHOD_API_NAME, notes = WalletTransferApi.Withdraw.METHOD_API_NOTE)
    @PostMapping("/withdraw")
    public ResultDTO withdraw(HttpServletRequest request,
                              @ApiParam(WalletTransferApi.Withdraw.TOADDR) @RequestParam("toAddr") String toAddr,
                              @ApiParam(WalletTransferApi.Withdraw.PASSWORD) @RequestParam("password") String password,
                              @ApiParam(WalletTransferApi.Withdraw.AMOUNT) @RequestParam("amount") BigDecimal amount,
                              @ApiParam(WalletTransferApi.Withdraw.TOKENID) @RequestParam(name = "tokenId", defaultValue = TokenConstans.TOKEN_PROPERTY_ID + "") Integer tokenId,
                              @ApiParam(WalletTransferApi.Withdraw.WALLET_TYPE) @RequestParam(value = "walletType", defaultValue = ApplicationConstans.TYPE_CCT) String walletType,
                              @ApiParam(WalletTransferApi.Withdraw.VERIFY_CODE) @RequestParam(name = "verifyCode", required = false) String verifyCode) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        return ResultDTO.requstSuccess(walletTransferService.handleWithdraw(user.getId(), password, toAddr, tokenId, amount, walletType, verifyCode, user.getTel()));
    }

}