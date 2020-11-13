package com.blockchain.server.teth.service;

import com.blockchain.server.teth.entity.EthPrivateBalance;

import java.util.Date;
import java.util.List;

public interface IEthPrivateBalanceService {

    /**
     * 获取私募资金列表
     */
    List<EthPrivateBalance> listPrivateBalance();

    /**
     * 私募资金处理
     */
    void handlePrivateBalance(EthPrivateBalance privateBalance, Date date);

}
