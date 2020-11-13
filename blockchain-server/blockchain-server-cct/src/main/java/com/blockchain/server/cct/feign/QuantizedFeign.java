package com.blockchain.server.cct.feign;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.TradingRecordDTO;
import com.blockchain.server.cct.entity.TradingOn;
import com.blockchain.server.cct.feign.api.OrderApi;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huangxl
 * @create 2019-04-25 17:13
 */
@FeignClient("dapp-quantized-server")
public interface QuantizedFeign {

    /**
     * @Description: buy-market：市价买
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @PostMapping("/quantized/inner/order/createBuyMarket")
    ResultDTO createBuyMarket(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId);

    /**
     * @Description: buy-limit：限价买
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @PostMapping("/quantized/inner/order/createBuyLimit")
    ResultDTO createBuyLimit(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.PRICE) @RequestParam("price") BigDecimal price, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId);

    /**
     * @Description: ell-market：市价卖
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @PostMapping("/quantized/inner/order/createSellMarket")
    ResultDTO createSellMarket(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId);

    /**
     * @Description: sell-limit：限价卖
     * @Param: [coinName, unitName, publishType, orderType, amount, price]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @PostMapping("/quantized/inner/order/createSellLimit")
    ResultDTO createSellLimit(@ApiParam(OrderApi.Create.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Create.AMOUNT) @RequestParam("amount") BigDecimal amount, @ApiParam(OrderApi.Create.PRICE) @RequestParam("price") BigDecimal price, @ApiParam(OrderApi.Create.USERID) @RequestParam("userId") String userId, @ApiParam(OrderApi.Create.USERID) @RequestParam("cctId") String cctId);

    /**
     * @Description: 撤单
     * @Param: [symbol, orderId]
     * @return: OVER：撤销订单   SUCCESS:火币进行撤单请求(实际不一定撤单完成)  FAIL:火币撤单失败
     * @Author: Liu.sd
     * @Date: 2019/4/18
     */
    @PostMapping("/quantized/inner/order/cancellations")
    ResultDTO<String> cancellations(@ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("orderId") String orderId);

    /**
     * @Description: 查询交易对
     * @Param: [coinName, unitName]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/25
     */
    @PostMapping("/quantized/inner/order/getTradingOnByCoinNameAndUnitName")
    ResultDTO<TradingOn> getTradingOn(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("coinName") String coinName, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("unitName") String unitName);

    /**
     * @Description: 盘口卖
     * @Param: [symbol, size]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/26
     */
    @PostMapping("/quantized/inner/order/getSellDepth")
    ResultDTO<List<MarketDTO>> getSellDepth(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam(value = "size", defaultValue = "5") Integer size);

    /**
     * @Description: 盘口买
     * @Param: [symbol, size]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/4/26
     */
    @PostMapping("/quantized/inner/order/getBuyDepth")
    ResultDTO<List<MarketDTO>> getPriceDepth(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("symbol") String symbol, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam(value = "size", defaultValue = "5") Integer size);

    /**
     * @Description: 最新历史成交列表
     * @Param: [coinName, unitName, size]
     * @return: com.blockchain.common.base.dto.ResultDTO<java.util.List                               <                               com.blockchain.server.cct.entity.TradingRecord>>
     * @Author: Liu.sd
     * @Date: 2019/5/13
     */
    @PostMapping("/quantized/inner/order/getHistoricalTrade")
    ResultDTO<List<TradingRecordDTO>> getHistoricalTrade(@ApiParam(OrderApi.Cancel.SYMBOL) @RequestParam("coinName") String coinName, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("unitName") String unitName, @ApiParam(OrderApi.Cancel.ORDERID) @RequestParam("size") Integer size);

    /**
     * 查询火币历史成交列表
     * 用于币币查询行情深度数据
     *
     * @param coinName
     * @param unitName
     * @param size
     * @return
     */
    @PostMapping("/quantized/inner/order/getMarketDTOHistoricalTrade")
    ResultDTO<List<MarketDTO>> getMarketDTOHistoricalTrade(@RequestParam("coinName") String coinName, @RequestParam("unitName") String unitName, @RequestParam("size") Integer size);
}
