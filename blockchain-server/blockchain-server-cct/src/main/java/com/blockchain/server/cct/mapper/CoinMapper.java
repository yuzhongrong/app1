package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.Coin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppCctCoinMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-15 17:38:07
 */
@Repository
public interface CoinMapper extends Mapper<Coin> {

    /***
     * 根据币对、状态查询币对信息
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    Coin selectByCoinUnitAndStatus(
            @Param("coinName") String coinName, @Param("unitName") String unitName, @Param("status") String status);

    /***
     * 根据状态查询币种
     * @param status
     * @return
     */
    List<Coin> listCoin(@Param("stauts") String status);

    /***
     * 根据单位查询交易币对
     * @param unitName
     * @return
     */
    List<Coin> listCoinByUnit(@Param("unitName") String unitName, @Param("status") String status);

    /***
     * 查询主币列表
     * @return
     */
    List<String> listCoinGroupByUnit();
}