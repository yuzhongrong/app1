package com.blockchain.server.tron.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.entity.TronWalletKey;
import com.blockchain.server.tron.mapper.TronWalletKeyMapper;
import com.blockchain.server.tron.redis.Redis;
import com.blockchain.server.tron.service.TronWalletKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * @author Harvey Luo
 * @date 2019/6/13 14:01
 */
@Service
public class TronWalletKeyServiceImpl implements TronWalletKeyService {

    @Autowired
    private TronWalletKeyMapper tronWalletKeyMapper;
    @Autowired
    private Redis redis;

    private static final Logger LOG = LoggerFactory.getLogger(TronWalletKeyServiceImpl.class);

    /**
     * 保存数据
     * @param userOpenId
     * @param addr
     * @param hexAddr
     * @param privateKey
     * @param keystore
     * @return
     */
    @Override
    @Transactional(noRollbackFor = Exception.class)
    public TronWalletKey insert(String userOpenId, String addr, String hexAddr, String privateKey, String keystore) {
        parameterNotEmpty(userOpenId, addr, hexAddr, privateKey, keystore);
        TronWalletKey tronWalletKey = new TronWalletKey();
        tronWalletKey.setUserOpenId(userOpenId);
        tronWalletKey.setAddr(addr);
        tronWalletKey.setHexAddr(hexAddr);
        tronWalletKey.setPrivateKey(privateKey);
        tronWalletKey.setKeystore(keystore);
        Date date = new Date();
        tronWalletKey.setCreateTime(date);
        tronWalletKey.setUpdateTime(date);
        int row = tronWalletKeyMapper.insert(tronWalletKey);
        if (row != 1) throw new TronWalletException(TronWalletEnums.CREATION_WALLET_ERROR);
        redis.setWalletAddr(hexAddr);
        return tronWalletKey;
    }

    /**
     * 通过base58查询地址对象
     * @param addr
     * @return
     */
    @Override
    public TronWalletKey selectByAddr(String addr) {
        parameterNotEmpty(addr);
        TronWalletKey example = new TronWalletKey();
        example.setAddr(addr);
        TronWalletKey tronWalletKey = tronWalletKeyMapper.selectOne(example);
        if (tronWalletKey == null) return null;
        tronWalletKey.setPrivateKey(RSACoderUtils.decryptPassword(tronWalletKey.getPrivateKey()));
        return tronWalletKey;
    }

    /**
     * 获取所有16进制地址
     * @return
     */
    @Override
    public Set<String> getWalletHexAddrs() {
        Set<String> addrs = redis.getWalletHexAddrs();
        if (addrs == null || addrs.size() == 0) {
            addrs = tronWalletKeyMapper.selectAllHexAddrs();
            LOG.info("********getWalletHexAddrs********"+addrs);
            if (addrs!=null&&addrs.size()!=0){
                redis.setWalletAddrs(addrs);
            }

        }
        return addrs;
    }

    /**
     * 判断参数是否为空
     * @param userOpenId
     * @param addr
     * @param hexAddr
     * @param privateKey
     * @param keystore
     */
    private void parameterNotEmpty(String userOpenId, String addr, String hexAddr, String privateKey, String keystore) {
        ExceptionPreconditionUtils.notEmpty(userOpenId);
        ExceptionPreconditionUtils.notEmpty(addr);
        ExceptionPreconditionUtils.notEmpty(hexAddr);
        ExceptionPreconditionUtils.notEmpty(privateKey);
        // ExceptionPreconditionUtils.notEmpty(keystore);
    }

    /**
     * 判断参数是否为空
     * @param param
     */
    private void parameterNotEmpty(String param) {
        ExceptionPreconditionUtils.notEmpty(param);
    }
}
