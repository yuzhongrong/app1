package com.blockchain.server.cct.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.cct.dto.order.PublishOrderDTO;
import com.blockchain.server.cct.entity.PublishOrder;
import com.blockchain.server.cct.service.MatchService;
import com.blockchain.server.cct.service.PublishOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huangxl
 * @create 2019-04-25 19:02
 */
@RestController
@RequestMapping("/inner")
public class CctInner {
    @Autowired
    private MatchService matchService;
    @Autowired
    private PublishOrderService publishOrderService;
    private static final Logger LOG = LoggerFactory.getLogger(CctInner.class);
    /**
     * 处理量化交易完全成交
     *
     * @param id       订单id
     * @param transNum 成交总量
     * @param price    成交总价
     * @return
     */
    @PostMapping("/quantizedFillTransaction")
    public ResultDTO handleFillQuantizedTransation(@RequestParam("id") String id,
                                                   @RequestParam("transNum") BigDecimal transNum, @RequestParam("price") BigDecimal price) {
        matchService.handleQuantizedTransation(id, transNum, price, true);
        return ResultDTO.requstSuccess();
    }

    /**
     * 处理量化交易部分成交
     *
     * @param id       订单id
     * @param transNum 成交总量
     * @param price    成交总价
     * @return
     */
    @PostMapping("/quantizedPartialTransaction")
    public ResultDTO handlePartialQuantizedTransation(@RequestParam("id") String id,
                                                      @RequestParam("transNum") BigDecimal transNum, @RequestParam("price") BigDecimal price) {
        matchService.handleQuantizedTransation(id, transNum, price, false);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/quantizedCancel")
    public ResultDTO handleQuantizedCancel(@RequestParam("id") String id) {
        //量化交易调用接口
        PublishOrder order = publishOrderService.selectById(id);
        publishOrderService.handleCancelOrder(id, order.getUserId());
        return ResultDTO.requstSuccess();
    }

    /**
     * 量化交易订阅到新的广告消息刷新深度
     *
     * @param coinName 基本货币
     * @param unitName 二级货币
     */
    @PostMapping("/pushRecord")
    public ResultDTO pushNewOrderRecord(@RequestParam("coinName") String coinName,
                                        @RequestParam("unitName") String unitName) {
        LOG.info("量化交易推送coinName:"+coinName+"unitName"+unitName);
        matchService.convertAndSend(coinName, unitName);
        return ResultDTO.requstSuccess();
    }

    @GetMapping("/send")
    public ResultDTO send(@RequestParam("coinName") String coinName,
                          @RequestParam("unitName") String unitName) {
        matchService.convertAndSend(coinName, unitName);
        return ResultDTO.requstSuccess();
    }

    /***
     * 查询单价范围内的挂单
     * @param coinName
     * @param unitName
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @PostMapping("/listBeMatchOrderToBot")
    public ResultDTO listBeMatchOrderToBot(@RequestParam("coinName") String coinName,
                                           @RequestParam("unitName") String unitName,
                                           @RequestParam("minPrice") BigDecimal minPrice,
                                           @RequestParam("maxPrice") BigDecimal maxPrice) {
        List<PublishOrderDTO> orderDTOS = matchService.listBeMatchOrderToBot(coinName, unitName, minPrice, maxPrice);
        return ResultDTO.requstSuccess(orderDTOS);
    }

    /****
     * 发布限价买单 - 用于撮合机器人
     * @param userId
     * @param coinName
     * @param unitName
     * @param totalNum
     * @param price
     * @return
     */
    @PostMapping("/handleLimitBuyToBot")
    public ResultDTO handleLimitBuyToBot(@RequestParam("userId") String userId,
                                         @RequestParam("coinName") String coinName,
                                         @RequestParam("unitName") String unitName,
                                         @RequestParam("totalNum") BigDecimal totalNum,
                                         @RequestParam("price") BigDecimal price) {
        String pubilshId = publishOrderService.handleLimitBuyToBot(userId, coinName, unitName, totalNum, price);
        return ResultDTO.requstSuccess(pubilshId);
    }

    /***
     * 发布限价卖单 - 用于撮合机器人
     * @param userId
     * @param coinName
     * @param unitName
     * @param totalNum
     * @param price
     * @return
     */
    @PostMapping("/handleLimitSellToBot")
    public ResultDTO handleLimitSellToBot(@RequestParam("userId") String userId,
                                          @RequestParam("coinName") String coinName,
                                          @RequestParam("unitName") String unitName,
                                          @RequestParam("totalNum") BigDecimal totalNum,
                                          @RequestParam("price") BigDecimal price) {
        String pubilshId = publishOrderService.handleLimitSellToBot(userId, coinName, unitName, totalNum, price);
        return ResultDTO.requstSuccess(pubilshId);
    }

    /***
     * 撮合 - 用于撮合机器人
     * @param matchId
     * @param bymatchId
     * @return
     */
    @PostMapping("/handleMatchToBot")
    public ResultDTO handleMatchToBot(@RequestParam("matchId") String matchId,
                                      @RequestParam("bymatchId") String bymatchId) {
        Boolean flag = matchService.handleMatch(matchId, bymatchId);
        return ResultDTO.requstSuccess(flag);
    }

    /**
     * 查询订单状态
     * @param cctId 订单ID
     * */
    @GetMapping("/orderStatus")
    public ResultDTO<String> orderStatus(@RequestParam("cctId") String cctId,@RequestParam(value="status",required = false) String status) {
        PublishOrder publishOrder = publishOrderService.selectById(cctId);
       // String status="";
        if(publishOrder!=null)
            status=publishOrder.getOrderStatus();
        return ResultDTO.requstSuccess(status);
    }

    @GetMapping("/Status")
    public ResultDTO<List<String>> orderStatus(@RequestParam(value="status",required = false) String status) {
        List<PublishOrder> publishOrder = publishOrderService.selectByStatus(status);
        //String status="";
     //   if(publishOrder!=null)
         //   status=publishOrder.getOrderStatus();
        return ResultDTO.requstSuccess(status);
    }

}
