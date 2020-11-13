package com.blockchain.server.tron.inner;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.tron.inner.api.TronWalletApi;
import com.blockchain.server.tron.service.TronWalletService;
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
@Api(TronWalletApi.TRON_WALLET_API)
@RestController
@RequestMapping("/inner/walletTx")
public class TronWalletInnerController {
    private static final Logger LOG = LoggerFactory.getLogger(TronWalletInnerController.class);
    @Autowired
    private TronWalletService tronWalletService;

    @ApiOperation(value = TronWalletApi.Order.MATHOD_API_NAME, notes = TronWalletApi.Order.MATHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(TronWalletApi.Order.MATHOD_API_WALLET_ORDER_DTO) @RequestBody WalletOrderDTO walletOrderDTO) {
        LOG.info("冻结、解冻余额，walletOrderDTO：" + walletOrderDTO.toString());
        tronWalletService.updateWalletOrder(walletOrderDTO);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = TronWalletApi.Change.MATHOD_API_NAME, notes = TronWalletApi.Change.MATHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(TronWalletApi.Change.MATHOD_API_WALLET_CHANGE_DTO) @RequestBody WalletChangeDTO walletChangeDTO) {
        LOG.info("扣除、增加，walletChangeDTO：" + walletChangeDTO.toString());
        tronWalletService.handleWalletChange(walletChangeDTO);
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
                    tronWalletService.handleWalletChange(walletChangeDTO);
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

    @ApiOperation(value = TronWalletApi.InitTronWallet.MATHOD_API_NAME, notes = TronWalletApi.InitTronWallet.MATHOD_API_NOTE)
    @GetMapping("/initTronWallet")
    public ResultDTO initTronWallet(@RequestParam("userOpenId") String userOpenId) {
        tronWalletService.initTronWallet(userOpenId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = TronWalletApi.GetBalanceByIdAndTypes.METHOD_API_NAME, notes = TronWalletApi.GetBalanceByIdAndTypes.METHOD_API_NOTE)
    @GetMapping("/getBalanceByIdAndTypes")
    public ResultDTO getBalanceByIdAndTypes(@ApiParam(TronWalletApi.GetBalanceByIdAndTypes.USEROPENID) @RequestParam("userOpenId") String userOpenId,
                                            @ApiParam(TronWalletApi.GetBalanceByIdAndTypes.WALLET_TYPE) @RequestParam(name = "walletTypes", required = false) String[] walletTypes) {
        return ResultDTO.requstSuccess(tronWalletService.getBalanceByIdAndTypes(userOpenId, walletTypes));
    }

}
