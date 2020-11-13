package com.blockchain.server.databot.schedule;

import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.common.enums.DataBotEnums;
import com.blockchain.server.databot.common.exception.DataBotExeption;
import com.blockchain.server.databot.dto.rpc.CurrencyMarketDTO;
import com.blockchain.server.databot.entity.CurrencyConfig;
import com.blockchain.server.databot.feign.CctFeign;
import com.blockchain.server.databot.feign.CurrencyFeign;
import com.blockchain.server.databot.redis.CurrencyConfigCache;
import com.blockchain.server.databot.redis.PublishOrderCache;
import com.blockchain.server.databot.service.CurrencyConfigService;
import com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KMarketScheduling {

    @Autowired
    private CurrencyConfigService currencyConfigService;
    @Autowired
    private PublishOrderCache orderCache;
    @Autowired
    private CurrencyConfigCache currencyConfigCache;
    @Autowired
    private CurrencyFeign currencyFeign;
    @Autowired
    private CctFeign cctFeign;

    private static final Logger LOG = LoggerFactory.getLogger(KMarketScheduling.class);
    private static final int DEPTH_TOTAL_AMOUNT = 150; //盘口数据总量
    private static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(3); //异步线程池

    /***
     * 每一秒创建一次K线数据和深度数据
     */
    @Scheduled(cron = "*/1 * * * * ?")
    public void createMarketData() {

        {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    LOG.info("创建K线开始");
                    //查询需要生成数据的币对
                    List<CurrencyConfig> currencyConfigs = currencyConfigService.selectByStatus(CommonConstant.YES);
                    LOG.info("查询K线配置：{}", currencyConfigs);
                    try {
                        for (CurrencyConfig config : currencyConfigs) {
                            asyncCreateMarketData(config);
                        }
                    } catch (Exception e) {
                        LOG.error("创建K线异常：{}", e.getMessage());
                        e.printStackTrace();
                    }

                }
            });


        }
    }

    /***
     * 每天凌晨创建一天的K线数量并放入缓存中
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void createKAmount() {
        //查询需要设置数量的币对
        List<CurrencyConfig> currencyConfigs = currencyConfigService.selectByStatus(CommonConstant.YES);
        for (CurrencyConfig currencyConfig : currencyConfigs) {
            setUpOneDayKTotalAmount(currencyConfig.getCurrencyPair(), BigDecimal.valueOf(currencyConfig.getKDayTotalAmount()));
        }
    }

    /***
     * 使用异步线程执行创建数据的操作
     * @param config
     */
    private void asyncCreateMarketData(CurrencyConfig config) {
        Date start = new Date();
        //判断是定价还是浮动价
        //根据定价创建数据
        if (config.getPriceType().equals(CommonConstant.PRICE)) {
            createMarketByPrice(config);
        }
        //根据浮动价创建
        if (config.getPriceType().equals(CommonConstant.PERCENT)) {
            createMarketByPercent(config);
        }
        Date end = new Date();
        LOG.info("创建K线行情方法消耗时间：" + (end.getTime() - start.getTime()) / 1000);
    }

    /***
     * 根据定价配置生成K线和深度数据
     */
    private void createMarketByPrice(CurrencyConfig config) {
        //创建K线数据
        createKLineByPrice(config);
        //创建深度数据
        createDepthByPrice(config);
    }

    /***
     * 根据涨跌幅生成K线和深度数据
     */
    private void createMarketByPercent(CurrencyConfig config) {
        //查询最新行情
        CurrencyMarketDTO market = getCurrencyMarket(config.getCurrencyPair());
        //行情对象不为空，再进行创建数据操作
        if (market != null) {
            //创建K线数据
            BigDecimal newPrice = createKLineByPercent(market, config);
            //设置新创单价，作为深度数据的单价标准
            market.setAmount(newPrice);
            //创建深度数据
            createDepthByPercent(market, config);
        }
    }

    /***
     * 根据涨跌幅创建K线图数据
     * @param market   当前行情信息
     * @param config   系统配置行情信息
     */
    private BigDecimal createKLineByPercent(CurrencyMarketDTO market, CurrencyConfig config) {
        //日涨跌幅
        float changePercent = config.getKChangePercent();
        //涨跌最大幅度（偏差）
        float maxChangePercent = config.getKMaxChangePercent();
        //当前最新行情的涨跌幅
        float nowChangePercent = market.getPercent();
        //新创涨跌幅
        float newChangePercent = 0f;
        //随机数对象
        Random random = new Random();
        //当前时间
        Date now = new Date();
        //当前小时数
        int hour = getNowDate(now, Calendar.HOUR_OF_DAY);
        int minute = getNowDate(now, Calendar.MINUTE);
        //当前时间是当天最后四小时
        if ((hour == 13 || hour == 15 || hour == 20 || hour == 21 || hour == 22 || hour == 23) && minute / 15 % 2 == 0) {
            //新创涨跌幅 - 当前最新行情涨跌幅 * 随机数 / 定数
            newChangePercent = (changePercent - nowChangePercent) * (float) random.nextInt(1000) / 2880000.0F;
            //当前时间秒数
            int second = getNowDate(now, Calendar.SECOND);
            //当前时间 大于5秒并且小于15秒 或者 大于50秒并且小于60秒
            if (second > 5 && second < 15 || second > 50 && second < 60) {
                newChangePercent = -newChangePercent;
            }
        } else {
            //随机一个布尔值
            boolean randomFlag = random.nextBoolean();
            //如果 最新行情涨跌幅 < 涨跌最大幅度 && (随机布尔值的为真 或者 最新行情涨跌幅 < 取反的涨跌最大幅度）
            if (!randomFlag || (changePercent > 0 && nowChangePercent < -maxChangePercent)
                    || (changePercent < 0 && nowChangePercent < maxChangePercent)) {
                //新创涨跌幅 = 随机数 * 涨跌最大幅度 / 定数
                newChangePercent = random.nextFloat() * maxChangePercent / 300;
            } else {
                //新创涨跌幅 = -随机数 * 涨跌最大幅度 / 定数
                newChangePercent = -random.nextFloat() * maxChangePercent / 300;
            }
        }

        //新创成交单价 = 行情最新成交 + (行情最新成交 * 新创涨跌幅)
        BigDecimal price = market.getAmount().add(market.getAmount().multiply(BigDecimal.valueOf(newChangePercent))).setScale(16, BigDecimal.ROUND_DOWN);
        //新创成交数量
        BigDecimal amount = getKAmount(String.valueOf(getNowSecond(now)), config.getCurrencyPair(), config.getKDayTotalAmount());
        //币对，根据索引取：0为coinName币种，1为unitName单位
        String[] currencyPair = config.getCurrencyPair().split("-");

        //异步线程调用行情插入数据
        asyncCurrencySave(currencyPair[0], currencyPair[1], price, amount, now.getTime(), random.nextBoolean());

        //返回最新单价
        return price;
    }

    /***
     * 根据定价创建K线图数据
     * @param config
     * @return
     */
    private BigDecimal createKLineByPrice(CurrencyConfig config) {
        Date now = new Date();
        Random random = new Random();
        //新创成交单价
        BigDecimal price = randomNum(config.getKMinPrice(), config.getKMaxPrice());
        //新创成交数量
        BigDecimal amount = getKAmount(String.valueOf(getNowSecond(now)), config.getCurrencyPair(), config.getKDayTotalAmount());
        //币对，根据索引取：0为coinName币种，1为unitName单位
        String[] currencyPair = config.getCurrencyPair().split("-");
        //异步线程调用行情插入数据
        asyncCurrencySave(currencyPair[0], currencyPair[1], price, amount, now.getTime(), random.nextBoolean());
        //返回最新单价
        return price;
    }

    /***
     * 根据定价范围创建深度图数据
     * @param config
     */
    private void createDepthByPrice(CurrencyConfig config) {
        //币种
        String coinName = config.getCurrencyPair().split("-")[0];
        //单位
        String unitName = config.getCurrencyPair().split("-")[1];
        //设置买盘数据
        setDepthByPriceInRedis(config, CommonConstant.BUY, coinName, unitName);
        //设置卖盘数据
        setDepthByPriceInRedis(config, CommonConstant.SELL, coinName, unitName);
    }

    /***
     * 根据涨跌幅创建深度图数据
     * @param market
     * @param config
     */
    private void createDepthByPercent(CurrencyMarketDTO market, CurrencyConfig config) {
        //币种
        String coinName = config.getCurrencyPair().split("-")[0];
        //单位
        String unitName = config.getCurrencyPair().split("-")[1];
        //当前最新单价
        float nowPrice = market.getAmount().floatValue();

        //设置买盘数据
        setDepthByPercentInRedis(config, CommonConstant.BUY, coinName, unitName, nowPrice);
        //设置卖盘数据
        setDepthByPercentInRedis(config, CommonConstant.SELL, coinName, unitName, nowPrice);
    }

    /***
     * 封装定价范围生成的深度图设值进缓存中
     * @param config
     * @param tradingType
     * @param coinName
     * @param unitName
     */
    private void setDepthByPriceInRedis(CurrencyConfig config, String tradingType, String coinName, String unitName) {
        //发行总数量
        float totalAmount = getTotalAmount(config, tradingType);
        //盘口每条数据的数量平均值
        float avgAmount = totalAmount / DEPTH_TOTAL_AMOUNT;
        //随机数对象
        Random random = new Random();
        //新创盘口数据集合
        Map<Float, MarketDTO> newPrices = new HashMap<>();
        //创建数据
        while (totalAmount > 0) {
            //新创盘口单价
            BigDecimal depthPrice = BigDecimal.ZERO;
            //根据买卖的最低最高范围生成单价
            if (tradingType.equals(CommonConstant.BUY)) {
                depthPrice = randomNum(config.getBuyMinPrice(), config.getBuyMaxPrice());
            }
            if (tradingType.equals(CommonConstant.SELL)) {
                depthPrice = randomNum(config.getSellMinPrice(), config.getSellMaxPrice());
            }

            //返回格式化后的单价
            float unitPrice = formatValue(depthPrice.floatValue());
            //新创盘口数量
            float newAmount = avgAmount * (random.nextInt(100) + 1) / 50;
            //减去发行总数量
            totalAmount -= newAmount;
            //判断是否存在相同单价
            if (newPrices.containsKey(unitPrice)) {
                continue;
            } else {
                //构建盘口数据对象
                MarketDTO marketDTO = new MarketDTO(new BigDecimal(unitPrice + ""), new BigDecimal(newAmount + ""),
                        new BigDecimal(newAmount + ""), unitName, coinName, tradingType);

                //将新创单价添加进map中
                newPrices.put(unitPrice, marketDTO);
            }
        }
        //设置深度数据到缓存中
        setDepthInRedis(newPrices, coinName, unitName, tradingType);

        //发送推送前端请求
        send(coinName, unitName);
    }

    /***
     * 封装涨跌幅生成的深度图设值进缓存中
     * @param config
     * @param tradingType
     * @param coinName
     * @param unitName
     * @param nowPrice
     */
    private void setDepthByPercentInRedis(CurrencyConfig config, String tradingType, String coinName, String unitName,
                                          float nowPrice) {
        //发行总数量
        float totalAmount = getTotalAmount(config, tradingType);

        //盘口每条数据的单价平均差
        float avgPriceDifference = getAvgPriceDifference(config, tradingType, nowPrice);
        //盘口每条数据的数量平均值
        float avgAmount = totalAmount / DEPTH_TOTAL_AMOUNT;

        //随机数对象
        Random random = new Random();
        //新创盘口数据集合
        Map<Float, MarketDTO> newPrices = new HashMap<>();
        //累加的深度价格
        float depthPrice = nowPrice;
        //判断是买是卖
        boolean mark = checkBuyOrSell(tradingType);
        //根据实际情况买盘数据频率调整3倍
        totalAmount = totalAmount * 3 ;
        //创建数据
        while (totalAmount > 0) {
            //新创盘口单价 = 单价平均差 * 随机数 / 定数
            float newPirce = avgPriceDifference * random.nextInt(10) / 5;
            //根据买卖判断加或减
            if (mark) {
                depthPrice -= newPirce;
                //如果累减的单价过小，跳出循环
                if (depthPrice < nowPrice - nowPrice * config.getBuyPricePercent()) {
                    break;
                }
            } else {
                depthPrice += newPirce;
                //如果累减的单价过大，跳出循环
                if (depthPrice > nowPrice + nowPrice * config.getSellPricePercent()) {
                    break;
                }
            }
            //格式化数据
            float unitPrice = formatValue(depthPrice);
            //要求FK代币生成的小数长度为5位
            if (coinName.equals("FK")) {
                unitPrice = new BigDecimal(depthPrice + "").setScale(5, BigDecimal.ROUND_DOWN).floatValue();
            }
            //新创盘口数量
            float newAmount = avgAmount * (random.nextInt(100) + 1) / 50;
            //减去发行总数量
            totalAmount -= newAmount;
            //判断是否存在相同单价
            if (newPrices.containsKey(unitPrice)) {

                continue;
            } else {
                //构建盘口数据对象
                MarketDTO marketDTO = new MarketDTO(new BigDecimal(unitPrice + ""), new BigDecimal(newAmount + ""),
                        new BigDecimal(newAmount + ""), unitName, coinName, tradingType);

                //将新创单价添加进map中
                newPrices.put(unitPrice, marketDTO);
            }
        }

        //设置深度数据到缓存中
        setDepthInRedis(newPrices, coinName, unitName, tradingType);

        //发送推送前端请求
        send(coinName, unitName);
    }


    /***
     * 设置深度数据到缓存中
     * @param newPrices
     * @param coinName
     * @param unitName
     * @param tradingType
     */
    private void setDepthInRedis(Map<Float, MarketDTO> newPrices, String coinName, String unitName, String
            tradingType) {
        //缓存中的sortSet集合
        Set<ZSetOperations.TypedTuple<MarketDTO>> tuples = new HashSet();
        //封装sortSet结构的数据
        for (Map.Entry<Float, MarketDTO> order : newPrices.entrySet()) {
            double price = order.getValue().getUnitPrice().doubleValue();
            //redis的sortSet对象
            ZSetOperations.TypedTuple<MarketDTO> tuple = new DefaultTypedTuple(order.getValue(), price);
            //将对象添加进sortSet中
            tuples.add(tuple);
        }

//        boolean lockFlag = false;
//        //获取锁并更新缓存
//        while (!lockFlag) {
//            lockFlag = orderCache.tryFairLock(coinName, unitName, tradingType);
//            //获取锁成功
//            if (lockFlag) {
        //删除集合中所有数据
        orderCache.removeZSetByRange(coinName, unitName, tradingType, 0, -1);
        //将新数据设置进缓存中
        orderCache.setZSetValue(coinName, unitName, tradingType, tuples);
        //释放锁
        //orderCache.unFairLock(coinName, unitName, tradingType);
//            }
//        }
    }

    /***
     * 根据交易方向返回发行总量
     * @param config
     * @param tradingType
     * @return
     */
    private float getTotalAmount(CurrencyConfig config, String tradingType) {
        if (checkBuyOrSell(tradingType)) {
            return config.getBuyTotalAmount();
        } else {
            return config.getSellTotalAmount();
        }
    }

    /***
     * 根据交易方向返回单价平均差
     * @param config
     * @param tradingType
     * @param nowPrice
     * @return
     */
    private float getAvgPriceDifference(CurrencyConfig config, String tradingType, float nowPrice) {
        if (checkBuyOrSell(tradingType)) {
            return nowPrice * config.getBuyPricePercent() / DEPTH_TOTAL_AMOUNT;
        } else {
            return nowPrice * config.getSellPricePercent() / DEPTH_TOTAL_AMOUNT;
        }
    }

    /***
     * 判断交易方向
     * @param tradingType
     * @return 买入true, 卖出false
     */
    private boolean checkBuyOrSell(String tradingType) {
        if (tradingType.equals(CommonConstant.BUY)) {
            return true;
        }
        if (tradingType.equals(CommonConstant.SELL)) {
            return false;
        }
        throw new DataBotExeption(DataBotEnums.UNKNOWN_TRADING_TYPE);
    }

    /***
     * 获取当前时间数据
     * @param calendarConstant
     * @return
     */
    private int getNowDate(Date now, int calendarConstant) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        return calendar.get(calendarConstant);
    }

    /***
     * 获取当前秒数（一天的秒数为86399s）
     * @param now
     * @return
     */
    private int getNowSecond(Date now) {
        try {
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
            //当前时间毫秒数 - 凌晨0点毫秒数 / 1000 = 当前秒数
            long overTime = (now.getTime() - (sdfOne.parse(sdfOne.format(now)).getTime())) / 1000;
            return (int) overTime;
        } catch (Exception e) {
            return new Random().nextInt(86399);
        }
    }

    /***
     * 获取数字货币最新行情
     * @param currencyPair
     * @return
     */
    private CurrencyMarketDTO getCurrencyMarket(String currencyPair) {
        ResultDTO<CurrencyMarketDTO> resultDTO = currencyFeign.get(currencyPair);
        return resultDTO.getData();
    }

    /***
     * 当前秒数作为Key获取K线数量
     * @param nowSecond
     * @param currencyPair
     * @param kTotalAmount
     * @return
     */
    private BigDecimal getKAmount(String nowSecond, String currencyPair, float kTotalAmount) {
        //是否存在缓存
        boolean hasKey = currencyConfigCache.hasKDayAmountKey(currencyPair);
        //如果不存在Key或者缓存中为空
        if (!hasKey) {
            //重新生成数据
            setUpOneDayKTotalAmount(currencyPair, BigDecimal.valueOf(kTotalAmount));
        }
        BigDecimal amount = currencyConfigCache.getHashKDayAmount(currencyPair, nowSecond);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            double randomNum = (80D + new Random().nextInt(40)) / 100;//80%~120%  波动
            double randomAmount = kTotalAmount / 86400 * randomNum;
            return new BigDecimal(randomAmount).setScale(8, BigDecimal.ROUND_DOWN);
        }
        Log.info(currencyPair+"------22222-------->>"+amount);


        //从缓存中获取数据
        return amount;
    }

    /***
     * 使用异步线程执行行情Feign调用
     * @param coinName
     * @param unitName
     * @param price
     * @param amount
     * @param nowTime
     * @param randomFlag
     */
    private void asyncCurrencySave(String coinName, String unitName, BigDecimal price, BigDecimal amount,
                                   long nowTime, boolean randomFlag) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        LOG.info(coinName+unitName+":"+amount);
        //插入新创K线数据
        currencyFeign.save(coinName, unitName, price, amount, nowTime, randomFlag ? CommonConstant.BUY : CommonConstant.SELL);
    }

    /***
     * 推送前端消息
     * @param coinName
     * @param unitName
     */
    private void send(String coinName, String unitName) {
        //发送推送前端请求
        cctFeign.send(coinName, unitName);
    }

    /****
     * 将K线每天总量分配到每天每一秒
     * @param currencyPair
     * @param totalAmount
     */
    public void setUpOneDayKTotalAmount(String currencyPair, BigDecimal totalAmount) {
        //锁key
        String lockKey = currencyConfigCache.getCurrencyKDayAmountLockKey(currencyPair);
        //锁标识
        boolean lockFlag = false;
        //循环获取锁
        if (!lockFlag) {
            lockFlag = currencyConfigCache.tryFairLock(lockKey);
            //获取成功
            if (lockFlag) {
                //是否存在缓存
                boolean hasKey = currencyConfigCache.hasKDayAmountKey(currencyPair);
                if (!hasKey) {
                    //创建每天每天深度数量，设置进缓存中
                    createOneDayKTotalAmount(currencyPair, totalAmount);
                }
                //释放锁
                currencyConfigCache.unFairLock(lockKey);
            }
        }
    }

    /***
     * 循环创建每日每秒数据并设置进缓存中
     * @param currencyPair
     * @param totalAmount
     */
    private void createOneDayKTotalAmount(String currencyPair, BigDecimal totalAmount) {
        LOG.info(currencyPair+"循环创建每日每秒数据并设置进缓存中totalAmount:"+totalAmount);
        //每日交易总量
        Random random = new Random();
        //记录每秒
        int nowSecond = 0;
        //每小时随机分配总量
        for (int hour = 0; hour < 24; hour++) {
            BigDecimal hourAmount = totalAmount.multiply(BigDecimal.valueOf(random.nextInt(100)))
                    .divide(BigDecimal.valueOf(1200), 8, BigDecimal.ROUND_DOWN);
            //随机出每秒的分配总量
            //每分钟随机分配总量
            for (int minute = 0; minute < 60; minute++) {
                BigDecimal minuteAmount = hourAmount.multiply(BigDecimal.valueOf(random.nextInt(100)))
                        .divide(BigDecimal.valueOf(3000), 8, BigDecimal.ROUND_DOWN);
                //随机出每秒的分配总量
                for (int second = 0; second < 60; second++) {
                    BigDecimal secondAmount = minuteAmount.multiply(BigDecimal.valueOf(random.nextInt(100)))
                            .divide(BigDecimal.valueOf(3000), 8, BigDecimal.ROUND_DOWN);

                    //缓存中的key
                    String totalAmountKey = currencyConfigCache.getCurrencyKDayTotalAmountKey(currencyPair);
                    //自增记录秒数
                    nowSecond += 1;
                    //将每一秒的单价设置进缓存中
                    currencyConfigCache.setHashValue(totalAmountKey, nowSecond + "", secondAmount);
                }
            }
        }
    }


    /***
     * 格式化数据小数
     * @param value
     * @return
     */
    private static float formatValue(float value) {
//        if (value < 0.001) {
//            return BigDecimal.valueOf(value).setScale(8, BigDecimal.ROUND_HALF_DOWN).floatValue();
//        } else if (value < 0.1) {
//            return BigDecimal.valueOf(value).setScale(6, BigDecimal.ROUND_HALF_DOWN).floatValue();
//        } else if (value < 10) {
//            return BigDecimal.valueOf(value).setScale(4, BigDecimal.ROUND_HALF_DOWN).floatValue();
//        } else {
//            return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_DOWN).floatValue();
//        }
        return BigDecimal.valueOf(value).setScale(8, BigDecimal.ROUND_HALF_DOWN).floatValue();
    }

    /***
     * 根据范围随机生成小数
     * @param min
     * @param max
     * @return
     */
    public static BigDecimal randomNum(Float min, Float max) {
        return new BigDecimal(Math.random() * (max - min) + min);
    }
}
