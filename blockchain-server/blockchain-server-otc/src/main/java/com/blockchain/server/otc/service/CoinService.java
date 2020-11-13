package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.coin.CoinDTO;
import com.blockchain.server.otc.dto.coin.CoinServiceChargeDTO;
import com.blockchain.server.otc.entity.Coin;

import java.util.List;

public interface CoinService {

    /***
     * 根据基本货币、二级货币查询币种信息
     * @param coinName
     * @param unitName
     * @return
     */
    Coin selectByCoinAndUnit(String coinName, String unitName);

    /***
     * 查询可用币种列表
     * @return
     */
    List<CoinDTO> listCoin(String coinStatus);

    /***
     * 查询币种手续费列表
     * @return
     */
    List<CoinServiceChargeDTO> listCoinServiceCharge();
}
