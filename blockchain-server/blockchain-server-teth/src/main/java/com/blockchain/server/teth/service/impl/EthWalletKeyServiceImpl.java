package com.blockchain.server.teth.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.teth.common.enums.EthWalletEnums;
import com.blockchain.server.teth.common.exception.EthWalletException;
import com.blockchain.server.teth.common.util.RedisPrivateUtil;
import com.blockchain.server.teth.common.util.RedisWalletAddrUtil;
import com.blockchain.server.teth.dto.Web3jWalletDTO;
import com.blockchain.server.teth.entity.EthWalletKey;
import com.blockchain.server.teth.mapper.EthWalletKeyMapper;
import com.blockchain.server.teth.service.IEthWalletKeyService;
import com.blockchain.server.teth.web3j.IWalletWeb3j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthWalletKeyServiceImpl implements IEthWalletKeyService {
    private static final Logger LOG = LoggerFactory.getLogger(EthWalletKeyServiceImpl.class);

    @Autowired
    EthWalletKeyMapper ethWalletKeyMapper;
    @Autowired
    IWalletWeb3j walletWeb3j;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public EthWalletKey findByUserOpenId(String userOpenId) {
        EthWalletKey where = new EthWalletKey();
        where.setUserOpenId(userOpenId);
        List<EthWalletKey> list = ethWalletKeyMapper.select(where);
        return list.size() <= 0 ? null : list.get(0);
    }

    @Override
    public boolean existsPass(String userOpenId) {
        EthWalletKey ethWalletKey = this.findByUserOpenId(userOpenId);
        String keystore = ethWalletKey.getKeystore();
        return keystore != null;
    }

    @Override
    public EthWalletKey selectOne(EthWalletKey ethWalletKey) {
        EthWalletKey _ethWalletKey = ethWalletKeyMapper.selectOne(ethWalletKey);
        return _ethWalletKey;
    }

    @Override
    @Transactional
    public int insert(EthWalletKey ethWalletKey) {
        RedisWalletAddrUtil.setWalletAddr(ethWalletKey.getAddr(), redisTemplate);
        return ethWalletKeyMapper.insertSelective(ethWalletKey);
    }

    @Override
    @Transactional
    public EthWalletKey insert(String userOpenId) {
//        EthWalletKey initRow = findByUserOpenId(userOpenId); // 查询钱包是否初始化过
        // 创建以太坊钱包
        EthWalletKey ethWalletKey = new EthWalletKey();
        Date date = new Date();
        ethWalletKey.setUserOpenId(userOpenId);
        Web3jWalletDTO wallet = walletWeb3j.creationEthWallet();
        ethWalletKey.setAddr(wallet.getAddr());
        ethWalletKey.setPrivateKey(RSACoderUtils.encryptPassword(wallet.getPrivateKey())); // 私钥加密
        ethWalletKey.setUpdateTime(date);
        ethWalletKey.setCreateTime(date);
        int row = ethWalletKeyMapper.insertSelective(ethWalletKey);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        } else {
            RedisWalletAddrUtil.setWalletAddr(ethWalletKey.getAddr(), redisTemplate); // 存入缓存
            return ethWalletKey;
        }
    }

    @Override
    @Transactional
    public int update(EthWalletKey ethWalletKey) {
        return ethWalletKeyMapper.updateByPrimaryKey(ethWalletKey);
    }

    @Override
    public Set<String> selectAddrs() {
        Set<String> addrs = RedisWalletAddrUtil.getWalletAddrs(redisTemplate);
        if (addrs == null || addrs.size() == 0) {
            addrs = ethWalletKeyMapper.selectAllAddrs();
            RedisWalletAddrUtil.setWalletAddrs(addrs, redisTemplate);
        }
        return addrs;
    }

    @Override
    public String isPassword(String userOpenId, String password) {
        // 数据验证
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        EthWalletKey ethWalletKey = this.findByUserOpenId(userOpenId);
        if (ethWalletKey == null) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        if (StringUtils.isBlank(ethWalletKey.getKeystore())) {
            throw new EthWalletException(EthWalletEnums.NOT_WALLETPASSWORD);
        }
        String _pass = getPassword(userOpenId, password);
        walletWeb3j.isPassword(ethWalletKey.getKeystore(), _pass);
        return ethWalletKey.getAddr();
    }

    @Override
    @Transactional
    public void updatePassword(String userOpenId, String password) {
        // 数据验证
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
//        EthWalletKey ethWalletKey = ethWalletKeyMapper.selectByPrimaryKey(userOpenId);
        EthWalletKey where = new EthWalletKey();
        where.setUserOpenId(userOpenId);
        List<EthWalletKey> list = ethWalletKeyMapper.select(where);
        if (list.size() == 0) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        // 重置密码
        String _pass = getPassword(userOpenId, password);
        for (EthWalletKey row : list) {
            String privateKey = RSACoderUtils.decryptPassword(row.getPrivateKey()); // 获取解密后的私钥
            Web3jWalletDTO walletDTO = walletWeb3j.updateEthWallet(privateKey, _pass);
            row.setKeystore(walletDTO.getKeystore());
            row.setUpdateTime(new Date());
            int update = ethWalletKeyMapper.update(row);
            if (update == 0) {
                throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
            }
        }
    }

    @Override
    public void checkPass(String telPhone, String checkCode) {
        {
            // 数据验证
            ExceptionPreconditionUtils.checkStringNotBlank(telPhone,
                    new EthWalletException(EthWalletEnums.NULL_USEROPENID));
            String _pass = chechCode(telPhone, checkCode);
            if(_pass==null||!_pass.equals(checkCode)){
                throw new EthWalletException(EthWalletEnums.WITHDRAW_CODE_ERROR);
            }
        }
    }

    private String chechCode(String telPhone, String checkCode) {
        ExceptionPreconditionUtils.checkStringNotBlank(checkCode, new EthWalletException(EthWalletEnums.NULL_PASSWORD));
        String key = RedisPrivateUtil.getPrivateKey(telPhone, redisTemplate);    // 获取私钥
        LOG.info("key is:"+key);
        return key;
    }

    /**
     * 获取解密后的密码
     *
     * @param userOpenId 用户ID
     * @param password   加密后的密码
     * @return
     */
    private String getPassword(String userOpenId, String password) {
        ExceptionPreconditionUtils.checkStringNotBlank(password, new EthWalletException(EthWalletEnums.NULL_PASSWORD));
        String key = RedisPrivateUtil.getPrivateKey(userOpenId, redisTemplate);    // 获取私钥
        LOG.info("password is:"+password);
        String _pass = RSACoderUtils.decryptPassword(password, key);    // 获取解密后的密码
        return _pass;
    }
}
