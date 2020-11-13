package com.blockchain.server.tron.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.controller.api.TronWalletTransferApi;
import com.blockchain.server.tron.dto.TronWalletTransferDto;
import com.blockchain.server.tron.service.TronWalletTransferService;
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
@Api(tags = TronWalletTransferApi.TRON_WALLET_TRANSFER_API)
@RestController
@RequestMapping("/walletTransfer")
public class TronWalletTransferController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TronWalletTransferService tronWalletTransferService;


    @ApiOperation(value = TronWalletTransferApi.GetTransfer.METHOD_API_NAME, notes = TronWalletTransferApi.GetTransfer.METHOD_API_NOTE)
    @GetMapping("/getTransfer")
    public ResultDTO getTransfer(HttpServletRequest request,
                                 @ApiParam(TronWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                 @ApiParam(TronWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                 @ApiParam(TronWalletTransferApi.GetTransfer.METHOD_API_TOKEN_NAME) @RequestParam(value = "tokenAddr", required = false, defaultValue = TronConstant.TRON_TOKEN_ID) String tokenAddr,
                                 @ApiParam(TronWalletTransferApi.GetTransfer.METHOD_API_WALLET_TYPE) @RequestParam(value = "walletType", required = false, defaultValue = TronConstant.TransferType.TRANSFER_CCT) String walletType) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        return ResultDTO.requstSuccess(tronWalletTransferService.listWalletTransfer(userId, tokenAddr, walletType));
    }

    @ApiOperation(value = TronWalletTransferApi.pcGetTransfer.METHOD_API_NAME, notes = TronWalletTransferApi.pcGetTransfer.METHOD_API_NOTE)
    @GetMapping("/pcGetTransfer")
    public ResultDTO pcGetTransfer(HttpServletRequest request,
                                   @ApiParam(TronWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                   @ApiParam(TronWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", required = false, defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                   @ApiParam(TronWalletTransferApi.pcGetTransfer.METHOD_API_TOKEN_NAME) @RequestParam(value = "tokenAddr", required = false, defaultValue = TronConstant.TRON_TOKEN_ID) String tokenAddr,
                                   @ApiParam(TronWalletTransferApi.pcGetTransfer.METHOD_API_WALLET_TYPE) @RequestParam(value = "walletType", required = false, defaultValue = TronConstant.TransferType.TRANSFER_CCT) String walletType) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        List<TronWalletTransferDto> list = tronWalletTransferService.listWalletTransfer(userId, tokenAddr, walletType);
        return ResultDTO.requstSuccess(list);
    }
}
