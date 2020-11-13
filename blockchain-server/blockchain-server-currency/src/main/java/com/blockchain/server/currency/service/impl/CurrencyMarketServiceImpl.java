package com.blockchain.server.currency.service.impl;

import com.alibaba.fastjson.JSON;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.util.DateTimeUtils;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.currency.Scheduling.HuobiMarketScheduling;
import com.blockchain.server.currency.common.constant.*;
import com.blockchain.server.currency.common.enums.CurrencyMarketResultEnums;
import com.blockchain.server.currency.common.exception.CurrencyMarketExeption;
import com.blockchain.server.currency.dto.*;
import com.blockchain.server.currency.mapper.CurrencyMarketMapper;
import com.blockchain.server.currency.model.CurrencyMarket;
import com.blockchain.server.currency.model.CurrencyPair;
import com.blockchain.server.currency.redis.HistoryCache;
import com.blockchain.server.currency.redis.MarketCache;
import com.blockchain.server.currency.redis.MarketKCache;
import com.blockchain.server.currency.redis.MarketLegalCache;
import com.blockchain.server.currency.service.CurrencyMarketService;
import com.blockchain.server.currency.service.CurrencyPairService;
import com.blockchain.server.currency.websocket.WebSocketSendMsgUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CurrencyMarketServiceImpl implements CurrencyMarketService {
    static final Logger LOG = LoggerFactory.getLogger(CurrencyMarketServiceImpl.class);

    @Autowired
    private CurrencyMarketMapper currencyMarketMapper;

    @Autowired
    private CurrencyPairService currencyPairService;

    @Autowired
    private MarketCache marketCache;

    @Autowired
    private MarketKCache marketKCache;

    @Autowired
    private MarketLegalCache legalCache;

    @Autowired
    private HistoryCache historyCache;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private RedissonClient redissonClient;

    private static ExecutorService cachedThreadPool = Executors.newSingleThreadExecutor();

    private static long TIME_DIFFERENCE = 28800000;


    private static BigDecimal volatilityBtc = new BigDecimal("500");

    @Override
    public CurrencyMarket save(CurrencyMarket currencyMarket) {
        currencyMarketMapper.insert(currencyMarket);
        return currencyMarket;
    }

    /**
     * 保存最新交易行情信息
     *
     * @param coinName  币种名称
     * @param unitName  币种单位
     * @param amount
     * @param total
     * @param timestamp
     * @return
     */
    @Override
    public CurrencyMarketDTO save(String coinName, String unitName, BigDecimal amount, BigDecimal total, Long timestamp) {
        return save(coinName, unitName, amount, total, timestamp, TradingTypeEnums.SELL.getValue(), null);
    }

    /**
     * 保存最新交易行情信息
     *
     * @param coinName    币种名称
     * @param unitName    币种单位
     * @param amount      交易单价
     * @param total       交易额
     * @param timestamp
     * @param tradingType 交易类型buy/sell
     * @return
     */
    @Override
    public CurrencyMarketDTO save(String coinName, String unitName, BigDecimal amount, BigDecimal total, Long timestamp, String tradingType, BigDecimal tradingVolume) {

        String currencyPair = coinName + "-" + unitName;
        //检测币对是否合法
        checkCurrency(currencyPair);
        CurrencyMarket currencyMarket = new CurrencyMarket();
        currencyMarket.setCurrencyPair(currencyPair);
        currencyMarket.setTotal(total);
        currencyMarket.setAmount(amount);
        currencyMarket.setTimestamp(timestamp);
        try {
        //保存新数据
        save(currencyMarket);
        CurrencyMarketDTO dto = new CurrencyMarketDTO();
        BeanUtils.copyProperties(currencyMarket, dto);

        dto.setTradingVolume(tradingVolume);

        //发送历史成交记录信息
        sendHistoryMsg(dto, tradingType);
        //添加缓存数据，发送行情变动信息
        setMarketCache(dto);
            return dto;
        }catch (Exception ex){
            LOG.error("推送k线发生异常",ex);
        }
       return  null;
    }

    @Override
    public CurrencyMarketDTO get(String currencyPair) {
        //检测币对是否合法
        checkCurrency(currencyPair);
        //获取一天的K线行情
        SortedMap<String, CurrencyMarketKDTO> map = getOneDayMarketK(currencyPair,
                MarketKTypeEnums.ONEDAY.getTimeType(), MarketKTypeEnums.ONEDAY.getTimeNumber());
        CurrencyMarketDTO dto = getMarketInfo(currencyPair, map);
        return dto;
    }

    @Override
    public CurrencyMarketDTO getLast(String currencyPair) {
        //检测币对是否合法
        checkCurrency(currencyPair);
        //获取一天的K线行情
        SortedMap<String, CurrencyMarketKDTO> map = getOneDayMarketK(currencyPair,
                MarketKTypeEnums.ONEDAY.getTimeType(), MarketKTypeEnums.ONEDAY.getTimeNumber());
        //获取今天之前的行情
        CurrencyMarketDTO dto = getLastDayMarketInfo(currencyPair, map);
        return dto;
    }

    /**
     * 获取所有法币对应的数据货币行情
     *
     * @return
     */
    @Override
    public Map<String, Double> getRates(String coin) {
        Map<String, Double> rates = new HashMap();
        for (BaseCoinEnums value : BaseCoinEnums.values()) {
            rates.put(value.getValue(), legalCache.getMarketCache(value.getValue() + coin));
        }
        return rates;
    }

    /**
     * 获取可用行情列表
     *
     * @return
     */
    @Override
    public List<CurrencyMarketDTO> getList() {
        List<CurrencyPairDTO> currencyPairDTOList = marketCache.getList();
        //LOG.info("get redis currencyPairDTOList size is:"+currencyPairDTOList.size());
        if (currencyPairDTOList == null) {
            currencyPairDTOList = currencyPairService.getUsableList();
            marketCache.setList(currencyPairDTOList);
            LOG.info("currencyPairDTOList size is:" + currencyPairDTOList.size());
        }
        List<CurrencyMarketDTO> currencyMarketDTOList = new ArrayList();
        for (CurrencyPairDTO currencyPairDTO : currencyPairDTOList) {
            CurrencyMarketDTO currencyMarketDTO = get(currencyPairDTO.getCurrencyPair());
            if (currencyMarketDTO == null)
                continue;
            currencyMarketDTOList.add(currencyMarketDTO);
        }
        return currencyMarketDTOList;
    }


    /**
     * 获取首页行情列表
     *
     * @return
     */
    @Override
    public List<CurrencyMarketDTO> getHomeList() {
        List<CurrencyPairDTO> dtoList = marketCache.getHomeList();
        if (dtoList == null) {
            dtoList = currencyPairService.getHomeList();
            marketCache.setHomeList(dtoList);
        }
        List<CurrencyMarketDTO> list = new ArrayList();
        for (CurrencyPairDTO dto : dtoList) {
            list.add(get(dto.getCurrencyPair()));
        }
        return list;
    }

    /**
     * 获取行情涨跌榜
     *
     * @return
     */
    @Override
    public List<CurrencyMarketDTO> getTopList() {
        List<CurrencyMarketDTO> list = new ArrayList();
        List<CurrencyPairDTO> usableList = currencyPairService.getUsableList();
        LOG.info("usableList siez is" + usableList.size() + " json is:" + JSON.toJSONString(usableList));
        for (int i = 0; i < usableList.size(); i++) {
            list.add(get(usableList.get(i).getCurrencyPair()));
        }
        LOG.info("get TOPLIST size :" + list.size());
        Collections.sort(list);
        List<CurrencyMarketDTO> topList = new ArrayList();
        for (int i = 0; i < CommonConstants.TOP_SIZE && i < usableList.size(); i++) {
            topList.add(list.get(i));
        }
        return topList;
    }

    /**
     * 获取历史成交列表
     *
     * @param currencyPair
     * @return
     */
    @Override
    public List getHistoryList(String currencyPair) {
        return historyCache.getList(currencyPair);
    }

    /**
     * 根据主要货币获取其所有币对
     *
     * @param currencyName
     * @return
     */
    @Override
    public List<CurrencyMarketDTO> getQuoteList(String currencyName) {
        List<CurrencyMarketDTO> list = new ArrayList();
        List<CurrencyPairDTO> quoteList = currencyPairService.getQuoteList(currencyName);
        for (int i = 0; i < quoteList.size(); i++) {
            list.add(get(quoteList.get(i).getCurrencyPair()));
        }
        return list;
    }

    /**
     * 查询行情K线图数据
     *
     * @param currencyPair
     * @param timeType     时间段类型：MINUTE，HOUR，DAY，WEEK，MONTH
     * @param timeNumber   时间间隔数据
     * @param start
     * @param stop
     * @return
     */
    @Override
    public List<CurrencyMarketKDTO> queryForK(String currencyPair, String timeType, Integer timeNumber, Integer start, Integer stop) {
        SortedMap<String, CurrencyMarketKDTO> map = getKMap(currencyPair, timeType, timeNumber);
        List<CurrencyMarketKDTO> list = new ArrayList<>();
        Integer index = 0;
        for (CurrencyMarketKDTO obj : map.values()) {
            if (index >= start && index < stop) {
                list.add(obj);
            }
            index++;
            if (index == stop) {
                break;
            }
        }
        return list;
    }

    @Override
    public List<CurrencyMarketKDTO> queryForKSortDESC(String currencyPair, String timeType, Integer timeNumber, Integer start, Integer stop) {
        List<CurrencyMarketKDTO> list = this.queryForK(currencyPair, timeType, timeNumber, start, stop);

        //倒序
        Collections.sort(list, new Comparator<CurrencyMarketKDTO>() {
            @Override
            public int compare(CurrencyMarketKDTO o1, CurrencyMarketKDTO o2) {
                long diff = o1.getTimestamp() - o2.getTimestamp();
                if (diff > 0) {
                    return -1;
                } else if (diff < 0) {
                    return 1;
                }
                return 0;
            }
        });

        return list;
    }

    /**
     * 获取情信息
     *
     * @param currencyPair
     * @param map
     * @return
     */
    private CurrencyMarketDTO getMarketInfo(String currencyPair, SortedMap<String, CurrencyMarketKDTO> map) {
        try {
            map.lastKey();//多条记录，获取最后一条最新的行情记录
        } catch (Exception e) {
            LOG.info("lastKey isException");
            return null;
        }

        CurrencyMarketKDTO kdto = map.get(map.lastKey());
        //查询最新行情
        CurrencyMarketDTO dto = new CurrencyMarketDTO();
        dto.setCurrencyPair(currencyPair);
        dto.setOpen(kdto.getOpen());
        dto.setLowest(kdto.getLowest());
        dto.setHighest(kdto.getHighest());
        dto.setAmount(kdto.getClose());
        dto.setTotal(kdto.getTotal());
        dto.setTimestamp(kdto.getCloseTime());
        if (new Date().getTime() - 86400000 < kdto.getCloseTime()) {//间隔小于一天
            //计算公式:
            //涨幅公式（关盘价-开盘价）/开盘价
            dto.setPercent(kdto.getClose().subtract(kdto.getOpen()).divide(kdto.getOpen(), 4, BigDecimal.ROUND_HALF_UP).floatValue());
        } else {
            dto.setPercent(0);
        }
        //添加法币值
        dto = setLegalMarket(currencyPair.split("-")[1], dto);
        return dto;
    }

    /***
     * 获取今天之前最新的行情
     * @param currencyPair
     * @param map
     * @return
     */
    private CurrencyMarketDTO getLastDayMarketInfo(String currencyPair, SortedMap<String, CurrencyMarketKDTO> map) {
        //获取最后一个
        CurrencyMarketKDTO kdto = map.get(map.lastKey());

        //获取当前年月日毫秒数
        MarketKTypeEnums ktype = MarketKTypeEnums.ONEDAY;
        Date now = new Date();
        Long timestamp = (now.getTime() / (ktype.getTimeNumber() * MarketKEnums.valueOf(ktype.getTimeType()).getSecond())) * ktype.getTimeNumber() * MarketKEnums.valueOf(ktype.getTimeType()).getSecond();

        //数据量在 1-2以内时,获取第一天数据
        if (map.size() > 0 && map.size() < 3) {
            kdto = map.get(map.firstKey());
        } else {
            //数据量大于2时,获取倒数第二条
            //循环
            while (map != null || map.size() != 0) {
                String lastKey = map.lastKey();
                //如果最后一个Key毫秒数大于当前年月日毫秒数
                if (Long.valueOf(lastKey) >= timestamp) {
                    //删除最后一个
                    map.remove(lastKey);
                } else {
                    //获取最后一个
                    kdto = map.get(lastKey);
                    break;
                }
            }
        }

        //查询最新行情
        CurrencyMarketDTO dto = new CurrencyMarketDTO();
        dto.setCurrencyPair(currencyPair);
        dto.setOpen(kdto.getOpen());
        dto.setLowest(kdto.getLowest());
        dto.setHighest(kdto.getHighest());
        dto.setAmount(kdto.getClose());
        dto.setTotal(kdto.getTotal());
        dto.setTimestamp(kdto.getCloseTime());
        if (new Date().getTime() - 86400000 < kdto.getCloseTime()) {
            dto.setPercent(kdto.getClose().subtract(kdto.getOpen()).divide(kdto.getOpen(), 4, BigDecimal.ROUND_HALF_UP).floatValue());
        } else {
            dto.setPercent(0);
        }
        //添加法币值
        dto = setLegalMarket(currencyPair.split("-")[1], dto);
        return dto;

    }

    /**
     * 获取K线图map数据
     *
     * @param currencyPair
     * @param timeType
     * @param timeNumber
     * @return
     */
    private SortedMap<String, CurrencyMarketKDTO> getKMap(String currencyPair, String timeType, Integer timeNumber) {
        if (currencyPair == null) {
            throw new CurrencyMarketExeption(CurrencyMarketResultEnums.CURRENCY_PAIR_NULL);
        }
        //检测币对是否合法
        checkCurrency(currencyPair);
        return getOneDayMarketK(currencyPair, timeType, timeNumber);
    }

    /**
     * 保存历史成交记录缓存
     *
     * @param dto
     * @param tradingType
     * @return
     */
    private List pushHistory(CurrencyMarketDTO dto, String tradingType) {
        CurrencyMarketHistoryDTO currencyMarketHistoryDTO = new CurrencyMarketHistoryDTO();
        String[] currencys = dto.getCurrencyPair().split("-");
        currencyMarketHistoryDTO.setCoinName(currencys[0]);
        currencyMarketHistoryDTO.setUnitName(currencys[1]);
        currencyMarketHistoryDTO.setCreateTime(DateTimeUtils.format(dto.getTimestamp(), DateTimeUtils.DATE_TIME_FORMAT));
        currencyMarketHistoryDTO.setMakerPrice(dto.getAmount());
        currencyMarketHistoryDTO.setTradingNum(dto.getTotal());
        if ("BTC-USDT".equals(dto.getCurrencyPair()) && dto.getTradingVolume() != null) {
            currencyMarketHistoryDTO.setTradingNum(dto.getTradingVolume());
        }
        currencyMarketHistoryDTO.setTradingType(tradingType);
        return historyCache.push(dto.getCurrencyPair(), currencyMarketHistoryDTO);
    }

    /**
     * 添加法币价格
     *
     * @param unitName
     * @param dto
     */
    private CurrencyMarketDTO setLegalMarket(String unitName, CurrencyMarketDTO dto) {
        dto.setCnyAmount(legalCache.getMarketCache(unitName + RatesEnums.CNY.getValue()));
        dto.setUsdAmount(legalCache.getMarketCache(unitName + RatesEnums.USD.getValue()));
        dto.setHkdAmount(legalCache.getMarketCache(unitName + RatesEnums.HKD.getValue()));
        dto.setEurAmount(legalCache.getMarketCache(unitName + RatesEnums.EUR.getValue()));
        return dto;
    }

    /**
     * 添加缓存数据
     *
     * @param dto 新行情数据
     */
    private void setMarketCache(CurrencyMarketDTO dto) {
        //将最新数据存入各个时间段
        for (MarketKTypeEnums ktype : MarketKTypeEnums.values()) {
            boolean lock = false;
//            while (!lock) {
            //若拿到锁则结束，若拿不到循环尝试
            lock = marketKCache.tryFairLock(redissonClient, dto.getCurrencyPair(),
                    ktype.getTimeType(), ktype.getTimeNumber());
            if (lock) {
                SortedMap<String, CurrencyMarketKDTO> map = marketKCache.getMarketKListCache(dto.getCurrencyPair(), ktype.getTimeType(), ktype.getTimeNumber());
                if (map == null)
                    map = selectMarketK(dto.getCurrencyPair(), ktype.getTimeType(), ktype.getTimeNumber());
                if (map == null) continue;
                //用于判断key的时间戳
                //公式:(当前时间戳/(时间段大小))*(时间段大小)  作用：取整
                Long checkTimestamp = (dto.getTimestamp() / (ktype.getTimeNumber() * MarketKEnums.valueOf(ktype.getTimeType()).getSecond())) * ktype.getTimeNumber() * MarketKEnums.valueOf(ktype.getTimeType()).getSecond();
                //用于存储的时间戳
                Long timestamp = checkTimestamp;
                //类型是一天时
                if (ktype.getTimeType().equals(MarketKTypeEnums.ONEDAY.getTimeType())) {
                    //需要减除时差(2020.02.24该算法错误，以废弃)
//                  checkTimestamp = ((dto.getTimestamp() + TIME_DIFFERENCE) / (ktype.getTimeNumber() * MarketKEnums.valueOf(ktype.getTimeType()).getSecond())) * ktype.getTimeNumber() * MarketKEnums.valueOf(ktype.getTimeType()).getSecond();
                    checkTimestamp = dto.getTimestamp() - (dto.getTimestamp() + TimeZone.getDefault().getRawOffset()) % MarketKEnums.DAY.getSecond();
                }
                CurrencyMarketKDTO kdto = map.get(checkTimestamp.toString());
                if (kdto == null) { //当前时间段还没数据，插入第一次数据
                    if (map.size() >= CommonConstants.MARKET_SIZE) {
                        map.remove(map.firstKey());//存1000条，删除最旧的数据
                    }
                    kdto = new CurrencyMarketKDTO();
                    if (dto.getAmount() == null || dto.getTimestamp() == null || dto.getTotal() == null) {
                        //释放锁
                        marketKCache.unFairLock(redissonClient, dto.getCurrencyPair(),
                                ktype.getTimeType(), ktype.getTimeNumber());
                        continue;
                    }
                    kdto.setHighest(dto.getAmount());//amount是单价
                    kdto.setLowest(dto.getAmount());
                    kdto.setOpen(dto.getAmount());
                    kdto.setClose(dto.getAmount());
                    kdto.setOpenTime(dto.getTimestamp());
                    kdto.setCloseTime(dto.getTimestamp());
                    kdto.setDate(getDate(dto.getTimestamp(), ktype.getTimeType(), ktype.getTimeNumber()));
                    kdto.setTotal(dto.getTotal());
                    kdto.setTimestamp(timestamp);
                } else {
                    if ("BTC-USDT".equals(dto.getCurrencyPair())) {
                        //直接拿线上数据，允許一定范围内的波动不累加
                       //涨幅大于500
                            kdto.setTotal(dto.getTotal());//直接更新
                        LOG.info("BTC-USDT Total"+dto.getTotal());
                    } else {
                        kdto.setTotal(kdto.getTotal().add(dto.getTotal()));
                    }

                    //判断更新开盘收盘
                    if (kdto.getOpenTime() > dto.getTimestamp()) {
                        kdto.setOpenTime(dto.getTimestamp());
                        kdto.setOpen(dto.getAmount());
                    } else if (kdto.getCloseTime() < dto.getTimestamp()) {
                        kdto.setCloseTime(dto.getTimestamp());
                        kdto.setClose(dto.getAmount());
                    }
                    //判断更新最高最低
                    if (kdto.getLowest().compareTo(dto.getAmount()) == 1) {
                        kdto.setLowest(dto.getAmount());
                    } else if (kdto.getHighest().compareTo(dto.getAmount()) == -1) {
                        kdto.setHighest(dto.getAmount());
                    }
                }
                //发送行情信息
                if (ktype == MarketKTypeEnums.ONEDAY) {
                    sendMarketMsg(dto.getCurrencyPair(), map);
                }
                sendMarketKMsg(dto.getCurrencyPair(), kdto, ktype.getTimeType(), ktype.getTimeNumber());
                map.put(checkTimestamp.toString(), kdto);
                marketKCache.setMarketKListCache(map, dto.getCurrencyPair(), ktype.getTimeType(), ktype.getTimeNumber());
                //释放锁
                marketKCache.unFairLock(redissonClient, dto.getCurrencyPair(),
                        ktype.getTimeType(), ktype.getTimeNumber());
            } else {
                LOG.info("等待锁:" + dto.getCurrencyPair());
            }
//            }
        }
    }

    /**
     * 发送K线变化信息
     *
     * @param kdto
     * @param timeType
     * @param timeNumber
     */
    private void sendMarketKMsg(String currencyPair, CurrencyMarketKDTO kdto, String timeType, Integer timeNumber) {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if(currencyPair.equals("BTC-USDT")){
                    LOG.info("BTC-USDT当前k线行情是 highest:"+kdto.getHighest()+" lowest:"+kdto.getLowest());
                }
                WebSocketSendMsgUtils.sendMarketKMsg(template, currencyPair, timeNumber + timeType, JsonUtils.objectToJson(kdto));
            }
        });
    }

    /**
     * 发送行情信息
     *
     * @param currencyPair
     * @param map
     */
    private void sendMarketMsg(String currencyPair, SortedMap<String, CurrencyMarketKDTO> map) {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if(currencyPair.equals("BTC-USDT")){
                    String key = map.lastKey();
                    CurrencyMarketKDTO kdto = map.get(key);
                    LOG.info("BTC-USDT当前行情是 highest:"+kdto.getHighest()+" lowest:"+kdto.getLowest());
                }
                WebSocketSendMsgUtils.sendMarketMsg(template, JsonUtils.objectToJson(getMarketInfo(currencyPair, map)));
            }
        });
    }

    /**
     * 发送历史成交记录信息
     *
     * @param dto
     * @param tradingType
     */
    private void sendHistoryMsg(CurrencyMarketDTO dto, String tradingType) {

        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List historyList = pushHistory(dto, tradingType);
                LOG.info(dto.getCurrencyPair() + " list size is:" + JSON.toJSONString(historyList).length());
                WebSocketSendMsgUtils.sendHistoryMsg(template, dto.getCurrencyPair(), JsonUtils.objectToJson(historyList));
            }
        });
    }

    /**
     * 查询数据库获取K线行情
     *
     * @param currencyPair
     * @param timeType
     * @param timeNumber
     * @return
     */
    private SortedMap<String, CurrencyMarketKDTO> selectMarketK(String currencyPair, String timeType, Integer timeNumber) {
        Map<String, Object> params = new HashMap();
        params.put("currencyPair", currencyPair);
        params.put("timeType", timeType);
        params.put("timeNumber", timeNumber);
        params.put("second", timeNumber * MarketKEnums.valueOf(timeType).getSecond());
        List<CurrencyMarketKDTO> list = currencyMarketMapper.queryForK(params);
        SortedMap<String, CurrencyMarketKDTO> map = new TreeMap();
        for (CurrencyMarketKDTO kdto : list) {
            //用于key的时间戳
            Long checkTimestamp = (kdto.getOpenTime() / (timeNumber * MarketKEnums.valueOf(timeType).getSecond())) * timeNumber * MarketKEnums.valueOf(timeType).getSecond();
            //用于展示数据的时间戳
            Long timestamp = checkTimestamp;
            kdto.setDate(getDate(kdto.getOpenTime(), timeType, timeNumber));
            kdto.setTimestamp(timestamp);
            //如果是一天时
            if (timeType.equals(MarketKTypeEnums.ONEDAY.getTimeType())) {
                //加上8小时时差再进行计算(2020.02.24该算法错误，以废弃)
//              checkTimestamp = ((kdto.getOpenTime() + TIME_DIFFERENCE) / (timeNumber * MarketKEnums.valueOf(timeType).getSecond())) * timeNumber * MarketKEnums.valueOf(timeType).getSecond();
                checkTimestamp = kdto.getOpenTime() - (kdto.getOpenTime() + TimeZone.getDefault().getRawOffset()) % MarketKEnums.DAY.getSecond();
            }
            map.put(checkTimestamp.toString(), kdto);
        }
        return map;
    }

    /**
     * 获取格式化日期
     *
     * @param timestamp
     * @param timeType
     * @param timeNumber
     * @return
     */
    private String getDate(Long timestamp, String timeType, Integer timeNumber) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp);
        if (timeType.equals(MarketKEnums.MINUTE.getValue())) {
            int minute = calendar.get(Calendar.MINUTE) / timeNumber * timeNumber;
            String date = DateTimeUtils.format(timestamp, DateTimeUtils.MONTH_DAY_HOUR_FORMAT) + ":" +
                    (minute >= 10 ? minute : "0" + minute);
            return date;
        } else if (timeType.equals(MarketKEnums.HOUR.getValue())) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY) / timeNumber * timeNumber;
            String date = DateTimeUtils.format(timestamp, DateTimeUtils.MONTH_DAY_FORMAT) + " " +
                    (hour >= 10 ? hour : "0" + hour) + ":00";
            return date;
        } else if (timeType.equals(MarketKEnums.DAY.getValue())) {
            return DateTimeUtils.format(timestamp, DateTimeUtils.YEAR_MONTH_DATE_FORMAT);
        }
        return null;
    }

    /**
     * 检测币对合法性
     *
     * @param currencyPair
     */
    private void checkCurrency(String currencyPair) {
        if (currencyPair == null) {
            throw new CurrencyMarketExeption(CurrencyMarketResultEnums.CURRENCY_PAIR_NULL);
        }
        CurrencyPair currencyPairModel = currencyPairService.get(currencyPair);
        if (currencyPairModel == null) {
            throw new CurrencyMarketExeption(CurrencyMarketResultEnums.CURRENCY_PAIR_ERROR);
        }
        if (currencyPairModel.getStatus() != 1) {
            throw new CurrencyMarketExeption(CurrencyMarketResultEnums.CURRENCY_PAIR_UNUSABLE);
        }
    }

    /**
     * 获取一天的K线行情
     *
     * @param currencyPair
     */
    private SortedMap<String, CurrencyMarketKDTO> getOneDayMarketK(String currencyPair, String timeType, Integer timeNumber) {
        Long t1 = System.currentTimeMillis();
        SortedMap<String, CurrencyMarketKDTO> map = marketKCache.getMarketKListCache(currencyPair, timeType, timeNumber);
        Long t2 = System.currentTimeMillis();
        LOG.info("从缓存获取行情,耗时:" + (t2 - t1));
        if (map == null) {

            boolean lock = marketKCache.tryFairLock(redissonClient, currencyPair,
                    timeType, timeNumber);
            if (lock) {
                map = selectMarketK(currencyPair, timeType, timeNumber);
                //保存K线缓存
                marketKCache.setMarketKListCache(map, currencyPair, timeType, timeNumber);
                //释放锁
                marketKCache.unFairLock(redissonClient, currencyPair,
                        timeType, timeNumber);
            } else {
                throw new BaseException(BaseResultEnums.BUSY);
            }
            Long t3 = System.currentTimeMillis();
            LOG.info("从数据库查找数据，耗时:" + (t3 - t2));
        }
        return map;
    }
}
