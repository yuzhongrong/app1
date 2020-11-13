package com.blockchain.server.databot.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.annotation.BypassedFeign;
import com.blockchain.server.databot.dto.rpc.PublishOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "dapp-cct-server", path = "/cct/inner")
public interface CctFeign {

    /***
     * 推送前端买卖盘口数据请求
     * @param coinName
     * @param unitName
     * @return
     */
    @GetMapping("/send")
    ResultDTO send(@RequestParam("coinName") String coinName,
                   @RequestParam("unitName") String unitName);

    /***
     * 查询单价范围内的挂单
     * @param coinName
     * @param unitName
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @PostMapping("/listBeMatchOrderToBot")
    ResultDTO<List<PublishOrderDTO>> listBeMatchOrderToBot(@RequestParam("coinName") String coinName,
                                                           @RequestParam("unitName") String unitName,
                                                           @RequestParam("minPrice") BigDecimal minPrice,
                                                           @RequestParam("maxPrice") BigDecimal maxPrice);

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
    @BypassedFeign
    ResultDTO<String> handleLimitBuyToBot(@RequestParam("userId") String userId,
                                          @RequestParam("coinName") String coinName,
                                          @RequestParam("unitName") String unitName,
                                          @RequestParam("totalNum") BigDecimal totalNum,
                                          @RequestParam("price") BigDecimal price);

    /****
     * 发布限价卖单 - 用于撮合机器人
     * @param userId
     * @param coinName
     * @param unitName
     * @param totalNum
     * @param price
     * @return
     */
    @PostMapping("/handleLimitSellToBot")
    @BypassedFeign
    ResultDTO<String> handleLimitSellToBot(@RequestParam("userId") String userId,
                                           @RequestParam("coinName") String coinName,
                                           @RequestParam("unitName") String unitName,
                                           @RequestParam("totalNum") BigDecimal totalNum,
                                           @RequestParam("price") BigDecimal price);

    /***
     * 撮合 - 用于撮合机器人
     * @param matchId
     * @param bymatchId
     * @return
     */
    @PostMapping("/handleMatchToBot")
    @BypassedFeign
    ResultDTO<Boolean> handleMatchToBot(@RequestParam("matchId") String matchId,
                                        @RequestParam("bymatchId") String bymatchId);
}
