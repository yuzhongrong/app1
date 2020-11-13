package com.blockchain.server.otc.schedule;

import com.blockchain.server.otc.common.constant.OrderConstants;
import com.blockchain.server.otc.service.ConfigService;
import com.blockchain.server.otc.service.OrderService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AutoCancelOrderScheduling {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ConfigService configService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void autoCancelOrder() {
        //查询是否开启自动撤单
        boolean autoCancelIsY = configService.selectAutoCancelIsY();
        //关闭自动撤单
        if (!autoCancelIsY) {
            return;
        }
        //查询倒计时分钟数
        Long minute = configService.selectAutoCancelInterval();
        //计算当前时间减去倒计时分钟数的时间
        Date cancelTime = DateUtils.addMinutes(new Date(), -minute.intValue());
        //检查是否存在新建状态的超时订单
        boolean flag = checkNewOrderExist(cancelTime);
        //存在超时订单
        if (flag) {
            Integer pageNum = 1;
            Integer pageSize = 10;
            //撤单
            cancelOrders(cancelTime, pageNum, pageSize);
        }
    }

    /***
     * 检查是否存在新建状态的超时订单
     * @return
     */
    private boolean checkNewOrderExist(Date cancelTime) {
        //查询超时订单
        List<String> orderIds = orderService.selectAutoCancelOrder(OrderConstants.NEW, cancelTime, 0, 1);
        if (orderIds.size() > 0) {
            return true;
        }
        return false;
    }

    /***
     * 撤销订单
     * @param cancelTime
     * @param pageNum
     * @param pageSize
     */
    private void cancelOrders(Date cancelTime, Integer pageNum, Integer pageSize) {
        //根据分页查询超时订单
        List<String> orderIds = orderService.selectAutoCancelOrder(OrderConstants.NEW, cancelTime, (pageNum - 1) * pageSize, pageSize);
        //迭代撤销
        for (String orderId : orderIds) {
            orderService.autoCancelOrder(orderId);
        }
        //如果超时订单列表还有下一页
        if (orderIds.size() >= pageSize) {
            //翻页
            pageNum++;
            //递归，继续撤销
            cancelOrders(cancelTime, pageNum, pageSize);
        }
    }
}
