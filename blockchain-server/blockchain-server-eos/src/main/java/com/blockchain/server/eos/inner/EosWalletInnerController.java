package com.blockchain.server.eos.inner;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.eos.inner.api.EosWalletApi;
import com.blockchain.server.eos.service.EosWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/19 18:09
 * @user WIN10
 */
@Api(EosWalletApi.EOS_WALLET_API)
@RestController
@RequestMapping("/inner/walletTx")
public class EosWalletInnerController {
    private static final Logger LOG = LoggerFactory.getLogger(EosWalletInnerController.class);
    @Autowired
    private EosWalletService eosWalletService;

    @ApiOperation(value = EosWalletApi.Order.MATHOD_API_NAME, notes = EosWalletApi.Order.MATHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(EosWalletApi.Order.MATHOD_API_WALLET_ORDER_DTO) @RequestBody WalletOrderDTO walletOrderDTO) {
        LOG.info("冻结、解冻余额，walletOrderDTO：" + walletOrderDTO.toString());
        eosWalletService.updateWalletOrder(walletOrderDTO);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletApi.Change.MATHOD_API_NAME, notes = EosWalletApi.Change.MATHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(EosWalletApi.Change.MATHOD_API_WALLET_CHANGE_DTO) @RequestBody WalletChangeDTO walletChangeDTO) {
        LOG.info("扣除、增加，walletChangeDTO：" + walletChangeDTO.toString());
        eosWalletService.handleWalletChange(walletChangeDTO);
        return ResultDTO.requstSuccess();
    }
     @PostMapping("/batchChange")
    ResultDTO<List<WalletChangeDTO>> changeBatch(@RequestBody List<WalletChangeDTO> batchChangeList){
        LOG.info("批量改变钱包余额,大小:" + batchChangeList.size());
        List<WalletChangeDTO> errorChangeList=new ArrayList<>();
        for(WalletChangeDTO walletChangeDTO:batchChangeList){
            int retryCount=0;//因数据库插入繁忙等原因报错，给三次机会
            while(retryCount<3){
                try {
                    eosWalletService.handleWalletChange(walletChangeDTO);
                    break;
                }catch (Exception e){
                    try {
                        Thread.sleep(3000L);
                    }catch (InterruptedException i){
                        i.printStackTrace();
                    }
                }
                retryCount++;
                if(retryCount>=3){
                    errorChangeList.add(walletChangeDTO);
                }
            }
        }
        LOG.info("批量修改钱包出错个数:"+errorChangeList.size()+" 详情:"+ JSON.toJSONString(errorChangeList));
        return ResultDTO.requstSuccess(errorChangeList);
    }

    @ApiOperation(value = EosWalletApi.InitEosWallet.MATHOD_API_NAME, notes = EosWalletApi.InitEosWallet.MATHOD_API_NOTE)
    @GetMapping("/initEosWallet")
    public ResultDTO initEosWallet(@RequestParam("userOpenId") String userOpenId) {
        eosWalletService.initEosWallet(userOpenId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletApi.GetBalanceByIdAndTypes.METHOD_API_NAME, notes = EosWalletApi.GetBalanceByIdAndTypes.METHOD_API_NOTE)
    @GetMapping("/getBalanceByIdAndTypes")
    public ResultDTO getBalanceByIdAndTypes(@ApiParam(EosWalletApi.GetBalanceByIdAndTypes.USEROPENID) @RequestParam("userOpenId") String userOpenId,
                                            @ApiParam(EosWalletApi.GetBalanceByIdAndTypes.WALLET_TYPE) @RequestParam(name = "walletTypes", required = false) String[] walletTypes) {
        return ResultDTO.requstSuccess(eosWalletService.getBalanceByIdAndTypes(userOpenId, walletTypes));
    }

}
