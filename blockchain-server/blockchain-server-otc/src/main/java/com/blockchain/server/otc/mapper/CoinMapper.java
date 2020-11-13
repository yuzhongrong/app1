package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.coin.CoinDTO;
import com.blockchain.server.otc.dto.coin.CoinServiceChargeDTO;
import com.blockchain.server.otc.entity.Coin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CoinMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Repository
public interface CoinMapper extends Mapper<Coin> {

    /***
     *  根据基本货币、二级货币查询币种信息
     * @param coinName
     * @param unitName
     * @return
     */
    Coin selectByCoinAndUnit(@Param("coinName") String coinName, @Param("unitName") String unitName);

    /***
     * 根据状态查询币种列表
     * @param coinStatus
     * @return
     */
    List<CoinDTO> listCoin(@Param("status") String coinStatus);

    /***
     * 查询所有代币手续费费率
     * @return
     */
    List<CoinServiceChargeDTO> listCoinServiceCharge();
}