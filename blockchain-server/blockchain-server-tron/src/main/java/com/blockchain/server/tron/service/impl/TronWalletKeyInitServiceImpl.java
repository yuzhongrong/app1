package com.blockchain.server.tron.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.entity.TronWalletCreate;
import com.blockchain.server.tron.entity.TronWalletKeyInit;
import com.blockchain.server.tron.mapper.TronWalletKeyInitMapper;
import com.blockchain.server.tron.redis.Redis;
import com.blockchain.server.tron.service.TronWalletCreateService;
import com.blockchain.server.tron.service.TronWalletKeyInitService;
import com.blockchain.server.tron.tron.TronUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Harvey Luo
 * @date 2019/8/1 13:44
 */
@Service
public class TronWalletKeyInitServiceImpl implements TronWalletKeyInitService {

    @Autowired
    private TronWalletKeyInitMapper tronWalletKeyInitMapper;
    @Autowired
    private TronUtil tronUtil;
    @Autowired
    private TronWalletCreateService tronWalletCreateService;
    @Autowired
    private Redis redis;

    private static final Logger LOG = LoggerFactory.getLogger(TronWalletKeyInitServiceImpl.class);

    /**
     * 创建初始化地址
     *
     * @param addr
     * @param hexAddr
     * @param privateKey
     * @param keystore
     * @return
     */
    @Override
    public Integer insert(String addr, String hexAddr, String privateKey, String keystore) {
        TronWalletKeyInit tronWalletKeyInit = new TronWalletKeyInit();
        tronWalletKeyInit.setAddr(addr);
        tronWalletKeyInit.setHexAddr(hexAddr);
        tronWalletKeyInit.setPrivateKey(RSACoderUtils.encryptPassword(privateKey));
        tronWalletKeyInit.setKeystore(keystore);
        tronWalletKeyInit.setCreateTime(new Date());
        redis.leftPush(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY, tronWalletKeyInit);
        return tronWalletKeyInitMapper.insert(tronWalletKeyInit);
    }

    /**
     * 查询一个钱包地址
     *
     * @return
     */
    @Override
    public TronWalletKeyInit selectByLimitOne() {
        return tronWalletKeyInitMapper.selectByLimitOne();
    }

    /**
     * 删除地址
     *
     * @param addr
     * @return
     */
    @Override
    public Integer deleteByAddr(String addr) {
        return tronWalletKeyInitMapper.deleteByAddr(addr);
    }


    /**
     * 创建地址
     */
    @Override
    public void dispose() {
        Long count = null;
        try {
            count = redis.listLength(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY);
        } catch (Exception e) {
            LOG.error("<====== tron 第一次创建地址异常 ======>",e);
            count = 0L;

        }
        if (count > TronConstant.InitWalletKey.MIN_WALLET_COUNT) return;
        TronWalletCreate tronWalletCreate = tronWalletCreateService.selectByOne();
        if (tronWalletCreate == null) return;
        // 创建钱包地址
        for (long i = 0; i < TronConstant.InitWalletKey.WALLET_COUNT - count; i++) {
            try {
                String dataList = tronUtil.getCreateAccount(tronWalletCreate.getHexAddr(), tronWalletCreate.getPrivateKey());
                String[] split = dataList.split(TronConstant.DELIMIT);
                String privateKey = split[0];
                String address = split[1];
                String hexAddress =split[2];
                insert(address, hexAddress, privateKey, null);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("<====== tron 创建地址异常 ======>",e);
            }
        }
    }

    /**
     * 同步数据
     */
    @Override
    public void synchronizationKeyInitDispose() {
        Long count = redis.listLength(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY);
        if (count == null) return;
        Long keyCount = tronWalletKeyInitMapper.selectWalletCount();
        if (count.equals(keyCount) || keyCount - count == 1) return;
        if(keyCount - count < 1){
            return ;
        }
        List<TronWalletKeyInit> tronWalletKeyInits = tronWalletKeyInitMapper.selectCreateTimeAsc((keyCount - count) / 2);
        Long time = (Long) redis.getOpsForValue(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY_TIME);
        if (time == null) return;
        Long keyTime = tronWalletKeyInits.get(tronWalletKeyInits.size() - 1).getCreateTime().getTime();
        if (keyTime < time) {
            for (TronWalletKeyInit tronWalletKeyInit : tronWalletKeyInits) {
                redis.leftPush(TronConstant.RedisKey.TRON_WALLET_KEY_INIT_KEY, tronWalletKeyInit);
            }
        }
    }

    @Override
    public Integer insert(TronWalletKeyInit tronWalletKeyInit) {
        return tronWalletKeyInitMapper.insert(tronWalletKeyInit);
    }

}
