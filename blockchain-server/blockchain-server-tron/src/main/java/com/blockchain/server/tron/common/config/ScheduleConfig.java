package com.blockchain.server.tron.common.config;

import com.blockchain.server.tron.scheduleTask.CreateWalletKeyTimerTask;
import com.blockchain.server.tron.scheduleTask.PayTimerTask;
import com.blockchain.server.tron.scheduleTask.TransferDisposeTimerTask;
import com.blockchain.server.tron.scheduleTask.WallertTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class ScheduleConfig {

    @Value("${schedule.open}")
    private boolean scheduleOpen;

    @Autowired
    private CreateWalletKeyTimerTask createWalletKeyTimerTask;
    @Autowired
    private PayTimerTask payTimerTask;
    @Autowired
    private TransferDisposeTimerTask transferDisposeTimerTask;
    @Autowired
    private WallertTimerTask wallertTimerTask;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private Map<String, ScheduledFuture> futureMap;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                if (scheduleOpen) {
                    schedule(() -> createWalletKeyTimerTask.selectTxStatus(), CreateWalletKeyTimerTask.SELECT_TX_STATUS_CRON);
                    schedule(() -> createWalletKeyTimerTask.synchronizationKeyInit(), "0/1 * * * * ?");
//                    schedule(() -> payTimerTask.selectTxStatus(), PayTimerTask.SELECT_TX_STATUS);
                    schedule(() -> transferDisposeTimerTask.selectTxStatus(), TransferDisposeTimerTask.SELECT_TX_STATUS);
                    schedule(() -> transferDisposeTimerTask.selectTxStatusOmit(), TransferDisposeTimerTask.SELECT_TX_STATUS_OMIT);
//                    schedule(() -> transferDisposeTimerTask.selectTxStatusTRC20(), TransferDisposeTimerTask.SELECT_TX_STATUS_TRC20);
                    schedule(() -> wallertTimerTask.crawlTxOut(), WallertTimerTask.CRAWL_TX_OUT);
                }
            }
        };
    }

    /***
     * 启动定时器
     * @param task 定时器执行的方法
     * @param cron 定时的cron表达式
     */
    private void schedule(Runnable task, String cron) {
        threadPoolTaskScheduler.schedule(task, new CronTrigger(cron));
    }
}
