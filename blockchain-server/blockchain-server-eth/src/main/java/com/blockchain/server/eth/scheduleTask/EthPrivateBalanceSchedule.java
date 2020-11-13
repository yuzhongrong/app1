package com.blockchain.server.eth.scheduleTask;

import com.blockchain.server.eth.entity.EthPrivateBalance;
import com.blockchain.server.eth.service.IEthPrivateBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 私募资金处理定时器
 */
@Component
public class EthPrivateBalanceSchedule {

    @Autowired
    private IEthPrivateBalanceService privateBalanceService;

    //定时器Cron表达式，每天0点执行
    public static final String CRON = "0 0 0 * * ?";
    //线程池
    private ExecutorService threadPool = Executors.newFixedThreadPool(4);


    //私募资金处理
    public void handlePrivateBalance() {
        Date date = new Date();
        List<EthPrivateBalance> pbList = privateBalanceService.listPrivateBalance();
        for (EthPrivateBalance pb : pbList) {
            threadPool.execute(() -> privateBalanceService.handlePrivateBalance(pb, date));
        }
    }

}
