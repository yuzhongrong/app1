package com.blockchain.server.databot.schedule;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.dto.rpc.CurrencyMarketDTO;
import com.blockchain.server.databot.dto.rpc.PublishOrderDTO;
import com.blockchain.server.databot.entity.CurrencyConfig;
import com.blockchain.server.databot.entity.MatchConfig;
import com.blockchain.server.databot.feign.CctFeign;
import com.blockchain.server.databot.feign.CurrencyFeign;
import com.blockchain.server.databot.service.CurrencyConfigService;
import com.blockchain.server.databot.service.MatchConfigService;
import com.blockchain.server.databot.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MatchScheduling {

    @Autowired
    private CurrencyConfigService currencyConfigService;
    @Autowired
    private MatchConfigService matchConfigService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private CurrencyFeign currencyFeign;
    @Autowired
    private CctFeign cctFeign;

    @Scheduled(cron = "*/15 * * * * ?")
    public void matchRobot() {
        //查询行情机器人可用币种列表
        List<CurrencyConfig> configs = currencyConfigService.selectByStatus(CommonConstant.YES);
        for (CurrencyConfig config : configs) {
            //分割币对为数组，索引0是coinName、1是unitName
            String[] currencyPair = config.getCurrencyPair().split("-");
            String coinName = currencyPair[0];
            String unitName = currencyPair[1];

            //查询撮合机器人配置
            MatchConfig matchConfig = matchConfigService.getStatusIsY(coinName, unitName);
            //不等于空时
            if (matchConfig != null) {
                //计算撮合的单价范围，并且进行撮合
                computeRange(matchConfig);
            }
        }
    }

    /***
     * 计算撮合单价范围
     * @param matchConfig
     */
    private void computeRange(MatchConfig matchConfig) {
        //最低价
        BigDecimal minPrice = BigDecimal.ZERO;
        //最高价
        BigDecimal maxPrice = BigDecimal.ZERO;

        //如果单价类型是定价范围，获取最低最高定价
        if (matchConfig.getPriceType().equals(CommonConstant.PRICE)) {
            minPrice = matchConfig.getMinPrice();
            maxPrice = matchConfig.getMaxPrice();
        }

        //如果单价类型是最新成交价波动范围，获取最新行情单价并设置最低最高的波动单价
        if (matchConfig.getPriceType().equals(CommonConstant.PERCENT)) {
            //查询最新行情
            CurrencyMarketDTO currency = getCurrency(matchConfig.getCoinName(), matchConfig.getUnitName());
            //行情不为空
            if (currency != null) {
                //最新成交价
                BigDecimal amount = currency.getAmount();
                //最低单价 = 最新成交价 - （最新成交价 * 最低跌幅）
                minPrice = amount.subtract(amount.multiply(matchConfig.getMinPercent()));
                //最高单价 = 最新成交价 + （最新成交价 * 最高涨幅）
                maxPrice = amount.add(amount.multiply(matchConfig.getMaxPercent()));
            }
        }

        //防止初始化最大最小单价失败
        if (minPrice.compareTo(BigDecimal.ZERO) != 0
                && maxPrice.compareTo(BigDecimal.ZERO) != 0) {
            //查询未撮合订单，并生成对手订单进行撮合
            iterationMatch(matchConfig.getUserId(), matchConfig.getCoinName(), matchConfig.getUnitName(), minPrice, maxPrice);
        }

    }

    /***
     * 查询未撮合订单，循环并生成对手订单进行撮合
     *
     * @param userId
     * @param coinName
     * @param unitName
     * @param minPrice
     * @param maxPrice
     */
    private void iterationMatch(String userId, String coinName, String unitName, BigDecimal minPrice, BigDecimal maxPrice) {
        //查询单价范围内没撮合的订单
        List<PublishOrderDTO> bymatchs = listBeMatchOrderToBot(coinName, unitName, minPrice, maxPrice);
        //循环发布对应的订单并且撮合
        for (PublishOrderDTO bymatch : bymatchs) {
            //开始撮合
            matchService.match(userId, bymatch);
        }
    }

    /***
     * 获取最新行情
     * @param coinName
     * @param unitName
     * @return
     */
    private CurrencyMarketDTO getCurrency(String coinName, String unitName) {
        ResultDTO<CurrencyMarketDTO> resultDTO = currencyFeign.get(coinName + "-" + unitName);
        return resultDTO.getData();
    }

    /***
     * 查询单价范围内没撮合的订单
     * @param coinName
     * @param unitName
     * @param minPrice
     * @param maxPrice
     * @return
     */
    private List<PublishOrderDTO> listBeMatchOrderToBot(String coinName, String unitName,
                                                        BigDecimal minPrice, BigDecimal maxPrice) {
        ResultDTO<List<PublishOrderDTO>> resultDTO = cctFeign.listBeMatchOrderToBot(coinName, unitName, minPrice, maxPrice);
        return resultDTO.getData();
    }
}
