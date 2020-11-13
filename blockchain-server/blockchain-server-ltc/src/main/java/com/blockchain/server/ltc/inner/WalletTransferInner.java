package com.blockchain.server.ltc.inner;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.ltc.inner.api.WalletTransferInnerApi;
import com.blockchain.server.ltc.service.WalletTransferService;
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

@Api(WalletTransferInnerApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/walletTx")
public class WalletTransferInner {
    private static final Logger LOG = LoggerFactory.getLogger(WalletTransferInner.class);
    @Autowired
    private WalletTransferService walletTransferService;

    @ApiOperation(value = WalletTransferInnerApi.Order.METHOD_API_NAME, notes = WalletTransferInnerApi.Order.METHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(WalletTransferInnerApi.Order.WALLETORDERDTO) @RequestBody WalletOrderDTO walletOrderDTO) {
        LOG.info("冻结、解冻余额，walletOrderDTO：" + walletOrderDTO.toString());
        return ResultDTO.requstSuccess(walletTransferService.handleOrder(walletOrderDTO));
    }

    @ApiOperation(value = WalletTransferInnerApi.Change.METHOD_API_NAME, notes = WalletTransferInnerApi.Change.METHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(WalletTransferInnerApi.Change.WALLETCHANGEDTO) @RequestBody WalletChangeDTO walletChangeDTO) {
        LOG.info("扣除、增加，walletChangeDTO：" + walletChangeDTO.toString());
        return ResultDTO.requstSuccess(walletTransferService.handleChange(walletChangeDTO));
    }

    @PostMapping("/batchChange")
    ResultDTO<List<WalletChangeDTO>> changeBatch(@RequestBody List<WalletChangeDTO> batchChangeList){
        LOG.info("批量改变钱包余额,大小:" + batchChangeList.size());
        List<WalletChangeDTO> errorChangeList=new ArrayList<>();
        for(WalletChangeDTO walletChangeDTO:batchChangeList){
            int retryCount=0;//因数据库插入繁忙等原因报错，给三次机会
            while(retryCount<3){
                try {
                    walletTransferService.handleChange(walletChangeDTO);
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
