package com.blockchain.server.teth.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.teth.common.enums.EthWalletEnums;
import com.blockchain.server.teth.common.exception.EthWalletException;
import com.blockchain.server.teth.controller.api.EthWalletApi;
import com.blockchain.server.teth.entity.EthWalletTransfer;
import com.blockchain.server.teth.feign.UserFeign;
import com.blockchain.server.teth.service.IEthWalletKeyService;
import com.blockchain.server.teth.service.IEthWalletService;
import com.blockchain.server.teth.web3j.IWalletWeb3j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.EthBlock;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author YH
 * @date 2018年11月6日15:51:56
 */
@Api(EthWalletApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/wallet")
public class EthWalletController {
    private static final Logger LOG = LoggerFactory.getLogger(EthWalletController.class);

    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    IEthWalletKeyService ethWalletKeyService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private UserFeign userFeign;


    @Autowired
    IWalletWeb3j walletWeb3j;


    @GetMapping("/blockNumber")
    public ResultDTO blockNumber(String blockNumber) {

        List<EthBlock.TransactionResult> txResults = walletWeb3j.getTokenTransactionList(new BigInteger(blockNumber));

        return ResultDTO.requstSuccess(txResults);
    }


    @ApiOperation(value = EthWalletApi.GetWallet.METHOD_API_NAME, notes = EthWalletApi.GetWallet.METHOD_API_NOTE)
    @GetMapping("/getWallet")
    public ResultDTO getWallet(
            @ApiParam(EthWalletApi.GetWallet.METHOD_API_WALLETTYPE) @RequestParam(name = "walletType", required =
                    false) String walletType,
            @ApiParam(EthWalletApi.GetWallet.METHOD_API_TOKENADDR) @RequestParam(name = "tokenAddr",
                    required = false) String tokenAddr,
            HttpServletRequest request) {

        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(ethWalletService.selectByUserOpenIdAndTokenAddrAndWalletType(userId, tokenAddr
                , walletType));
    }

    @ApiOperation(value = EthWalletApi.GetWallets.METHOD_API_NAME, notes = EthWalletApi.GetWallets.METHOD_API_NOTE)
    @GetMapping("/getWallets")
    public ResultDTO getWallets(
            @ApiParam(EthWalletApi.GetWallets.METHOD_API_WALLETTYPE) @RequestParam(name = "walletType", required =
                    false) String walletType,
            HttpServletRequest request) {
        LOG.info("==============1===================");

        String userId = SSOHelper.getUserId(redisTemplate, request);
        LOG.info("danqian denl ============ {}",userId);

        LOG.info("danqian denl ============ {}",userId);

        LOG.info("danqian denl ============ {}",userId);
        LOG.info("danqian denl ============ {}",userId);


        return ResultDTO.requstSuccess(ethWalletService.selectByUserOpenIdAndWalletType(userId, walletType));
    }

    @ApiOperation(value = EthWalletApi.GetPublicKey.METHOD_API_NAME, notes = EthWalletApi.GetPublicKey.METHOD_API_NOTE)
    @GetMapping("/getPublicKey")
    public ResultDTO getPublicKey(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(ethWalletService.getPublicKey(userId));
    }

    @ApiOperation(value = EthWalletApi.GetPublicKey.METHOD_API_EXPER, notes = EthWalletApi.GetPublicKey.METHOD_API_NOTE)
    @GetMapping("/getWalletPublicKey")
    public ResultDTO getWalletPublicKey(@RequestParam("account") String account) {
        return ResultDTO.requstSuccess(ethWalletService.getWalletPublicKey(account));
    }

    @ApiOperation(value = EthWalletApi.CreationWallet.METHOD_API_NAME, notes =
            EthWalletApi.CreationWallet.METHOD_API_NOTE)
    @PostMapping("/creationWallet")
    public ResultDTO creationWallet(@ApiParam(EthWalletApi.CreationWallet.METHOD_API_WALLETTYPE) @RequestParam(name =
            "walletType", required = false) String walletType,
                                    @ApiParam(EthWalletApi.CreationWallet.METHOD_API_TOKENADDR) @RequestParam(name =
                                            "tokenAddr", required = false) String tokenAddr,
                                    @ApiParam(EthWalletApi.CreationWallet.METHOD_API_PASSWORD) @RequestParam(name =
                                            "password", required = false) String password,
                                    HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(ethWalletService.insert(userId, tokenAddr, walletType, password));
    }

    @ApiOperation(value = EthWalletApi.SaveWalletPass.METHOD_API_NAME, notes =
            EthWalletApi.SaveWalletPass.METHOD_API_NOTE)
    @PostMapping("/saveWalletPass")
    public ResultDTO saveWalletPass(
            @ApiParam(EthWalletApi.SaveWalletPass.METHOD_API_PASSWORD) @RequestParam(name = "password", required = false) String password,
            @ApiParam(EthWalletApi.SaveWalletPass.METHOD_API_CODE) @RequestParam(name = "code", required = false) String code,
            HttpServletRequest request) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        ResultDTO resultDTO = userFeign.validateSmsg(code, user.getTel());
        if (resultDTO == null) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
        if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
        }
        ethWalletService.updatePassword(user.getId(), password);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthWalletApi.ExistsPass.METHOD_API_NAME, notes = EthWalletApi.ExistsPass.METHOD_API_NOTE)
    @GetMapping("/existsPass")
    public ResultDTO existsPass(HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        boolean existsPass = ethWalletKeyService.existsPass(userId);
        return ResultDTO.requstSuccess(existsPass);
    }

    @ApiOperation(value = EthWalletApi.WithdrawDeposit.METHOD_API_NAME, notes =
            EthWalletApi.WithdrawDeposit.METHOD_API_NOTE)
    @PostMapping("/withdrawDeposit")
    public ResultDTO withdrawDeposit(
            @ApiParam(EthWalletApi.WithdrawDeposit.METHOD_API_WALLETTYPE) @RequestParam(name = "walletType", required = false) String walletType,
            @ApiParam(EthWalletApi.WithdrawDeposit.METHOD_API_TOKENADDR) @RequestParam(name = "tokenAddr", required = false) String tokenAddr,
            @ApiParam(EthWalletApi.WithdrawDeposit.METHOD_API_PASSWORD) @RequestParam(name = "password", required = false) String password,
            @ApiParam(EthWalletApi.WithdrawDeposit.METHOD_API_TOADDDR) @RequestParam(name = "toAddr", required = false) String toAddr,
            @ApiParam(EthWalletApi.WithdrawDeposit.METHOD_API_AMOUNT) @RequestParam(name = "amount", required = false) String amount,
            @ApiParam(EthWalletApi.WithdrawDeposit.VERIFY_CODE) @RequestParam(name = "verifyCode", required = false) String verifyCode,
            HttpServletRequest request) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        EthWalletTransfer tx = ethWalletService.handleWelletOutApply(user.getId(), tokenAddr, toAddr, walletType, amount, password, verifyCode, user.getTel());
        return ResultDTO.requstSuccess(tx);
    }

    @ApiOperation(value = EthWalletApi.Transfer.METHOD_API_NAME, notes = EthWalletApi.Transfer.METHOD_API_NOTE)
    @PostMapping("/transfer")
    public ResultDTO handleTransfer(HttpServletRequest request,
                                    @ApiParam(EthWalletApi.Transfer.METHOD_API_FROMTYPE) @RequestParam("fromType") String fromType,
                                    @ApiParam(EthWalletApi.Transfer.METHOD_API_TOTYPE) @RequestParam("toType") String toType,
                                    @ApiParam(EthWalletApi.Transfer.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                                    @ApiParam(EthWalletApi.Transfer.METHOD_API_AMOUNT) @RequestParam("amount") BigDecimal amount) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        return ResultDTO.requstSuccess(ethWalletService.handleTransfer(userOpenId, fromType, toType, coinName, amount));
    }


}
