package com.blockchain.server.cmc.inner;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.cmc.inner.api.BtcWalletTransferInnerApi;
import com.blockchain.server.cmc.service.BtcWalletTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(BtcWalletTransferInnerApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/walletTx")
public class BtcWalletTransferInner {
    private static final Logger LOG = LoggerFactory.getLogger(BtcWalletTransferInner.class);
    @Autowired
    private BtcWalletTransferService btcWalletTransferService;

    @ApiOperation(value = BtcWalletTransferInnerApi.Order.METHOD_API_NAME, notes = BtcWalletTransferInnerApi.Order.METHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(BtcWalletTransferInnerApi.Order.WALLETORDERDTO) @RequestBody WalletOrderDTO walletOrderDTO) {
        LOG.info("冻结、解冻余额，walletOrderDTO：" + walletOrderDTO.toString());
        return ResultDTO.requstSuccess(btcWalletTransferService.handleOrder(walletOrderDTO));
    }

    @ApiOperation(value = BtcWalletTransferInnerApi.Change.METHOD_API_NAME, notes = BtcWalletTransferInnerApi.Change.METHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(BtcWalletTransferInnerApi.Change.WALLETCHANGEDTO) @RequestBody WalletChangeDTO walletChangeDTO) {
        LOG.info("扣除、增加，walletChangeDTO：" + walletChangeDTO.toString());
        return ResultDTO.requstSuccess(btcWalletTransferService.handleChange(walletChangeDTO));
    }

     @PostMapping("/batchChange")
    ResultDTO<List<WalletChangeDTO>> changeBatch(@RequestBody List<WalletChangeDTO> batchChangeList){
       LOG.info("批量改变钱包余额,大小:" + batchChangeList.size());
        List<WalletChangeDTO> errorChangeList=new ArrayList<>();
       for(WalletChangeDTO walletChangeDTO:batchChangeList){
           int retryCount=0;//因数据库插入繁忙等原因报错，给三次机会
           while(retryCount<3){
               try {
                   btcWalletTransferService.handleChange(walletChangeDTO);
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



}
