package com.blockchain.server.eos.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.controller.api.EosWalletTransferApi;
import com.blockchain.server.eos.dto.WalletTransferDTO;
import com.blockchain.server.eos.service.EosWalletTransferService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/25 11:07
 * @user WIN10
 */
@Api(EosWalletTransferApi.EOS_WALLET_TRANSFER_API)
@RestController
@RequestMapping("/walletTransfer")
public class EosWalletTransferController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EosWalletTransferService eosWalletTransferService;


    @ApiOperation(value = EosWalletTransferApi.GetTransfer.METHOD_API_NAME, notes = EosWalletTransferApi.GetTransfer.METHOD_API_NOTE)
    @GetMapping("/getTransfer")
    public ResultDTO getTransfer(HttpServletRequest request,
                                 @ApiParam(EosWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                 @ApiParam(EosWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                 @ApiParam(EosWalletTransferApi.GetTransfer.METHOD_API_TOKEN_NAME) @RequestParam(value = "tokenName", required = false, defaultValue = EosConstant.EOS_TOKEN_NAME) String tokenName,
                                 @ApiParam(EosWalletTransferApi.GetTransfer.METHOD_API_WALLET_TYPE) @RequestParam(value = "walletType", required = false, defaultValue = EosConstant.TransferType.TRANSFER_CCT) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        return ResultDTO.requstSuccess(eosWalletTransferService.listWalletTransfer(userOpenId, tokenName, walletType));
    }

    @ApiOperation(value = EosWalletTransferApi.pcGetTransfer.METHOD_API_NAME, notes = EosWalletTransferApi.pcGetTransfer.METHOD_API_NOTE)
    @GetMapping("/pcGetTransfer")
    public ResultDTO pcGetTransfer(HttpServletRequest request,
                                   @ApiParam(EosWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                   @ApiParam(EosWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                   @ApiParam(EosWalletTransferApi.pcGetTransfer.METHOD_API_TOKEN_NAME) @RequestParam(value = "tokenName", required = false, defaultValue = EosConstant.EOS_TOKEN_NAME) String tokenName,
                                   @ApiParam(EosWalletTransferApi.pcGetTransfer.METHOD_API_WALLET_TYPE) @RequestParam(value = "walletType", required = false, defaultValue = EosConstant.TransferType.TRANSFER_CCT) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTransferDTO> list = eosWalletTransferService.listWalletTransfer(userOpenId, tokenName, walletType);
        return generatePage(list);
    }
}
