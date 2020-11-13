package com.blockchain.server.databot.service.impl;

import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.entity.MatchConfig;
import com.blockchain.server.databot.mapper.MatchConfigMapper;
import com.blockchain.server.databot.redis.MatchConfigCache;
import com.blockchain.server.databot.service.MatchConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchConfigServiceImpl implements MatchConfigService {

    @Autowired
    private MatchConfigMapper matchConfigMapper;
    @Autowired
    private MatchConfigCache matchConfigCache;

    @Override
    public MatchConfig getStatusIsY(String coinName, String unitName) {
        //数据key
        String key = matchConfigCache.getKey(coinName, unitName);

        //查询缓存是否存在Key
        boolean flag = matchConfigCache.hasKey(key);

        if (flag) {
            //存在-直接返回缓存数据
            return matchConfigCache.getValue(key);
        } else {
            //不存在-查询数据库并设置进缓存中
            MatchConfig matchConfig = matchConfigMapper.selectByCoinAndUnitAndStauts(coinName, unitName, CommonConstant.YES);

            //不等于空时，存入缓存中
            if (matchConfig != null) {
                //获取锁标识
                boolean lockFlag = false;
                //循环获取锁
                while (!lockFlag) {
                    //锁key
                    String lockKey = matchConfigCache.getLockKey(coinName, unitName);
                    //获取锁
                    lockFlag = matchConfigCache.tryFairLock(lockKey);
                    //获取成功
                    if (lockFlag) {
                        matchConfigCache.setValue(key, matchConfig);
                        //释放锁
                        matchConfigCache.unFairLock(lockKey);
                    }
                }
            }

            //返回数据
            return matchConfig;
        }
    }
}
