package com.blockchain.server.eth.common.config;

import com.blockchain.server.eth.scheduleTask.EthPrivateBalanceSchedule;
import com.blockchain.server.eth.scheduleTask.EthTxInTimerTask;
import com.blockchain.server.eth.scheduleTask.EthWallertTimerTask;
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

/***
 * 用于统一管理测试环境下不需要的定时器
 * 在 scheduleconfig-xxx.yml 配置的open字段判断是否打开这些定时器
 * true为打开定时器
 */
@Configuration
public class ScheduleConfig {

    @Value("${schedule.open}")
    private boolean scheduleOpen;

    @Autowired
    private EthTxInTimerTask ethTxInTimerTask;
    @Autowired
    private EthWallertTimerTask ethWallertTimerTask;
    @Autowired
    private EthPrivateBalanceSchedule privateBalanceSchedule;

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
                    schedule(() -> ethTxInTimerTask.crawlTxIn(), EthTxInTimerTask.CRAWL_TX_IN_CRON);
                    schedule(() -> ethTxInTimerTask.crawlOmitTxIn(), EthTxInTimerTask.CRAWL_OMIT_TX_IN_CRON);
                    schedule(() -> ethWallertTimerTask.crawlTxIn(), EthWallertTimerTask.CRAWL_TX_IN_CRON);
                    schedule(() -> ethWallertTimerTask.crawlTxOut(), EthWallertTimerTask.CRAWL_TX_OUT_CRON);
                    schedule(() -> privateBalanceSchedule.handlePrivateBalance(), EthPrivateBalanceSchedule.CRON);
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
