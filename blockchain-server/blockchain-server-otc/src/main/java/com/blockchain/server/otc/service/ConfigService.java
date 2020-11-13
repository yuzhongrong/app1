package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.config.ConfigDTO;
import com.blockchain.server.otc.entity.Config;

import java.math.BigDecimal;
import java.util.List;

public interface ConfigService {

    /***
     * 根据配置key查询配置信息
     * @param key
     * @return
     */
    Config selectByKey(String key);

    /***
     * 查询自动撤单是否开启
     * true 开启
     * false 关闭
     * @return
     */
    boolean selectAutoCancelIsY();

    /***
     * 查询自动撤单时间间隔
     * @return
     */
    Long selectAutoCancelInterval();

    /***
     * 查询手续费开启状态
     * @return
     */
    List<ConfigDTO> selectServiceCharge();

    /***
     * 查询市商可发布多少卖单
     * @return
     */
    Integer selectMarketSellAdCount();

    /***
     * 查询市商可发布多少买单
     * @return
     */
    Integer selectMarketBuyAdCount();

    /***
     * 查询市商保证金扣除的代币
     * @return
     */
    String selectMarketFreezeCoin();

    /***
     * 查询市商保证金数量
     * @return
     */
    BigDecimal selectMarketFreezeAmount();
}
