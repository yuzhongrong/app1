package com.blockchain.server.eth.inner;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.eth.inner.api.EthWalletTransferApi;
import com.blockchain.server.eth.service.IEthWalletService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author YH
 * @date 2018年11月6日15:51:56
 */
@Api(EthWalletTransferApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/walletTx")
public class EthWalletTransferInner {
    private static final Logger LOG = LoggerFactory.getLogger(EthWalletTransferInner.class);
    private static boolean isPass = false;
    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    RedisTemplate redisTemplate;
    //线程池
    private ExecutorService threadPool = Executors.newSingleThreadExecutor();

    @ApiOperation(value = EthWalletTransferApi.Order.METHOD_API_NAME, notes =
            EthWalletTransferApi.Order.METHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(EthWalletTransferApi.Order.METHOD_API_ORDERDTO) @RequestBody WalletOrderDTO orderDTO) {
        LOG.info("冻结、解冻余额，orderDTO：" + orderDTO.toString());
        ethWalletService.updateBlanceTransform(orderDTO);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthWalletTransferApi.Change.METHOD_API_NAME, notes =
            EthWalletTransferApi.Change.METHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(EthWalletTransferApi.Change.METHOD_API_CHANGEDTO) @RequestBody WalletChangeDTO changeDTO) {
        LOG.info("扣除、增加，changeDTO：" + changeDTO.toString());
        ethWalletService.updateBlance(changeDTO);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/batchChange")
    ResultDTO changeBatch(@RequestBody List<WalletChangeDTO> batchChangeList) {
        //防止报错后重复访问插入重复
        if (EthWalletTransferInner.isPass == true)
            return ResultDTO.requstSuccess();
        EthWalletTransferInner.isPass = true;
        LOG.info("批量改变钱包余额,大小:" + batchChangeList.size());
        //异步执行与回调
        threadPool.execute(() -> {
            Long t1 = System.currentTimeMillis();
            ethWalletService.updateBatchBlance(batchChangeList);
            Long t2 = System.currentTimeMillis();
            LOG.info("发放不锁仓奖励完成，耗时：" + (t2 - t1));
        });
        //结束后将访问值为false
        EthWalletTransferInner.isPass = false;
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/transfer")
    ResultDTO transfer(@RequestParam(name = "userOpenId") String userOpenId,
                       @RequestParam(name = "fromType") String fromType,
                       @RequestParam(name = "toType") String toType,
                       @RequestParam(name = "coinName") String coinName,
                       @RequestParam(name = "amount") BigDecimal amount) {
        return ResultDTO.requstSuccess( ethWalletService.handleTransfer(userOpenId, fromType, toType, coinName, amount));
    }


}
