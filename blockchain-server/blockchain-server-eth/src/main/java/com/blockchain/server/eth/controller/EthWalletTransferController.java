package com.blockchain.server.eth.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eth.controller.api.EthTokenApi;
import com.blockchain.server.eth.controller.api.EthWalletApi;
import com.blockchain.server.eth.controller.api.EthWalletTransferApi;
import com.blockchain.server.eth.dto.EthGFCTransfer;
import com.blockchain.server.eth.entity.EthWalletTransfer;
import com.blockchain.server.eth.service.IEthWalletService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Api(EthWalletTransferApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/walletTransfer")
public class EthWalletTransferController extends BaseController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    private IEthWalletTransferService ethWalletTransferService;


    @GetMapping("/getTransfer")
    public ResultDTO getTransfer(HttpServletRequest request,
                                 @ApiParam(EthWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                 @ApiParam(EthWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                 @ApiParam(EthWalletTransferApi.GetTransfer.METHOD_API_TOKENADDR) @RequestParam(value = "tokenAddr", required = false) String tokenAddr,
                                 @ApiParam(EthWalletTransferApi.GetTransfer.METHOD_API_WALLETTYPE) @RequestParam(value = "walletType", required = false) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        return ResultDTO.requstSuccess(ethWalletTransferService.selectTransfer(userOpenId, tokenAddr, walletType));
    }

    @GetMapping("/pcGetTransfer")
    public ResultDTO pcGetTransfer(HttpServletRequest request,
                                   @ApiParam(EthWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                   @ApiParam(EthWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                   @ApiParam(EthWalletTransferApi.pcGetTransfer.METHOD_API_TOKENADDR) @RequestParam(value = "tokenAddr", required = false) String tokenAddr,
                                   @ApiParam(EthWalletTransferApi.pcGetTransfer.METHOD_API_WALLETTYPE) @RequestParam(value = "walletType", required = false) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        List<EthWalletTransfer> list = ethWalletTransferService.selectTransfer(userOpenId, tokenAddr, walletType);
        return generatePage(list);
    }


    @PostMapping("/GFCRecharge")
    public ResultDTO GFCRecharge(@RequestParam("account") String account, @RequestParam("amount") String amount,
                                 @RequestParam("checkCode") String checkCode) {
        //校验交易码
        ethWalletService.checkPass(account, checkCode);
        //检查用户
        String orderId = ethWalletService.GFCRecharge(account, amount, checkCode);
        return ResultDTO.requstSuccess(orderId);
    }

    @GetMapping("getGFCTransfer")
    public ResultDTO getGFCTransfer(@RequestParam("orderId") String orderId){
      EthGFCTransfer ethGFCTransfer=  ethWalletTransferService.getGFCTransfer(orderId);
      return ResultDTO.requstSuccess(ethGFCTransfer);
    }
}