package com.blockchain.server.btc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.btc.common.constants.BtcApplicationConstans;
import com.blockchain.server.btc.common.constants.BtcBlockNumberConstans;
import com.blockchain.server.btc.common.util.BtcAddressSetRedisUtils;
import com.blockchain.server.btc.controller.api.BtcWalletTransferApi;
import com.blockchain.server.btc.dto.BtcBlockNumberDTO;
import com.blockchain.server.btc.dto.BtcWalletTransferDTO;
import com.blockchain.server.btc.rpc.BtcUtils;
import com.blockchain.server.btc.service.BtcWalletTransferService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Api(BtcWalletTransferApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/walletTransfer")
public class BtcWalletTransferController extends BaseController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BtcWalletTransferService btcWalletTransferService;


    @ApiOperation(value = BtcWalletTransferApi.GetTransfer.METHOD_API_NAME, notes = BtcWalletTransferApi.GetTransfer.METHOD_API_NOTE)
    @GetMapping("/getTransfer")
    public ResultDTO getTransfer(HttpServletRequest request,
                                 @ApiParam(BtcWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                 @ApiParam(BtcWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                 @ApiParam(BtcWalletTransferApi.GetTransfer.TOKENID) @RequestParam("tokenId") Integer tokenId,
                                 @ApiParam(BtcWalletTransferApi.GetTransfer.WALLET_TYPE) @RequestParam(value = "walletType", defaultValue = BtcApplicationConstans.TYPE_CCT) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        return ResultDTO.requstSuccess(btcWalletTransferService.selectTransfer(userOpenId, tokenId, walletType));
    }

    @ApiOperation(value = BtcWalletTransferApi.pcGetTransfer.METHOD_API_NAME, notes = BtcWalletTransferApi.pcGetTransfer.METHOD_API_NOTE)
    @GetMapping("/pcGetTransfer")
    public ResultDTO pcGetTransfer(HttpServletRequest request,
                                   @ApiParam(BtcWalletTransferApi.PAGENUM) @RequestParam(name = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX) Integer pageNum,
                                   @ApiParam(BtcWalletTransferApi.PAGESIZE) @RequestParam(name = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE) Integer pageSize,
                                   @ApiParam(BtcWalletTransferApi.pcGetTransfer.TOKENID) @RequestParam("tokenId") Integer tokenId,
                                   @ApiParam(BtcWalletTransferApi.pcGetTransfer.WALLET_TYPE) @RequestParam(value = "walletType", defaultValue = BtcApplicationConstans.TYPE_CCT) String walletType) {
        String userOpenId = SSOHelper.getUserId(redisTemplate, request);
        //分页查询记录
        PageHelper.startPage(pageNum, pageSize);
        List<BtcWalletTransferDTO> list = btcWalletTransferService.selectTransfer(userOpenId, tokenId, walletType);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletTransferApi.Withdraw.METHOD_API_NAME, notes = BtcWalletTransferApi.Withdraw.METHOD_API_NOTE)
    @PostMapping("/withdraw")
    public ResultDTO withdraw(HttpServletRequest request,
                              @ApiParam(BtcWalletTransferApi.Withdraw.TOADDR) @RequestParam("toAddr") String toAddr,
                              @ApiParam(BtcWalletTransferApi.Withdraw.PASSWORD) @RequestParam("password") String password,
                              @ApiParam(BtcWalletTransferApi.Withdraw.AMOUNT) @RequestParam("amount") BigDecimal amount,
                              @ApiParam(BtcWalletTransferApi.Withdraw.TOKENID) @RequestParam("tokenId") Integer tokenId,
                              @ApiParam(BtcWalletTransferApi.Withdraw.WALLET_TYPE) @RequestParam(value = "walletType", defaultValue = BtcApplicationConstans.TYPE_CCT) String walletType,
                              @ApiParam(BtcWalletTransferApi.Withdraw.VERIFY_CODE) @RequestParam(name = "verifyCode", required = false) String verifyCode) {
        SessionUserDTO user = SSOHelper.getUser(redisTemplate, request);
        return ResultDTO.requstSuccess(btcWalletTransferService.handleWithdraw(user.getId(), password, toAddr, tokenId, amount, walletType, verifyCode, user.getTel()));
    }

    private static final Logger LOG = LoggerFactory.getLogger(BtcWalletTransferController.class);

    @Autowired
    private BtcUtils btcUtils;
    @Autowired
    private BtcAddressSetRedisUtils btcAddressSetRedisUtils;

    @GetMapping("/getBlockInfo")
    public ResultDTO getBlockInfo(@RequestParam("block") Integer block) {
        long nowTime = System.currentTimeMillis();
        try {
            //本区块hash
            String thisBlockHash = btcUtils.getBlockHash(block);
            //上一个区块hash
            String previousBlockHash = btcUtils.getBlockHash(block - 1);
            //交易数组
            JSONArray transferArr = btcUtils.listSinceBlock(previousBlockHash);

            int transferArrLen = transferArr.size();
            for (int i = 0; i < transferArrLen; i++) {
                JSONObject transferObj = transferArr.getJSONObject(i);
                //不为本次区块交易，不需要解析
                if (!thisBlockHash.equals(transferObj.getString("blockhash"))) {
                    continue;
                }
                if ("receive".equals(transferObj.getString("category"))) {
                    String toAddr = transferObj.getString("address");
                    if (btcAddressSetRedisUtils.isExistsAddr(toAddr)) {
                        String txId = transferObj.getString("txid");

                        JSONObject transferBtc = btcUtils.getTransaction(txId);
                        //交易金额，正数表示该交易增加钱包余额，负数表示该交易减少钱包余额
                        if (transferBtc.getDouble("amount") <= 0) {
                            continue;
                        }
                        JSONArray details = transferBtc.getJSONArray("details");
                        for (int j = 0; j < details.size(); j++) {
                            JSONObject jsonObject = details.getJSONObject(j);
                            LOG.info("transferJson is:" + JSON.toJSON(jsonObject).toString());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultDTO.requstSuccess();
    }
}