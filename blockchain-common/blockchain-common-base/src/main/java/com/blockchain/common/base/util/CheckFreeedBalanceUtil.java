package com.blockchain.common.base.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: qinhui
 * @date: 2020/6/11 16:05
 */
public class CheckFreeedBalanceUtil {
     public static String FREE="FREE";
    public static String FREEZE="FREEZE";
    /**
     * 撤单的时候因前端四舍五入取8位小数，可能会导致冻结余额不足，本方法作判断使用
     * @param freezeBalance 账户的冻结资金
     * @param changeFreezeBalance 释放的冻结资金
     * */
    public synchronized static Map<String,BigDecimal> switchBalance(BigDecimal freezeBalance, BigDecimal changeFreezeBalance, BigDecimal changeFreeBalance){
        Map<String,BigDecimal> map=new HashMap<>();
        if(changeFreeBalance.add(changeFreezeBalance).compareTo(BigDecimal.ZERO)!=0||
                changeFreezeBalance.compareTo(BigDecimal.ZERO)>=0){
            //直接返回
            map.put(FREE,changeFreeBalance);
            map.put(FREEZE,changeFreezeBalance);
            return map;
        }

        boolean freezeFlag = freezeBalance.add(changeFreezeBalance).compareTo(BigDecimal.ZERO) < 0;
        if (freezeFlag) {
            //判断冻结余额和挂单金额之间的误差
            BigDecimal freezeBalance1 = freezeBalance.setScale(8, BigDecimal.ROUND_UP);//向上取整
            freezeFlag = changeFreezeBalance.add(freezeBalance1).compareTo(BigDecimal.ZERO) == 0;
            if(freezeFlag){
                changeFreezeBalance=freezeBalance.multiply(new BigDecimal("-1"));
                map.put(FREE,freezeBalance);
                map.put(FREEZE,changeFreezeBalance);
                return map;
            }
        }
            map.put(FREE,changeFreeBalance);
            map.put(FREEZE,changeFreezeBalance);
            return map;
    }

}
