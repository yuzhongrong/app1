package com.blockchain.server.tron.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.entity.TronToken;
import com.blockchain.server.tron.mapper.TronTokenMapper;
import com.blockchain.server.tron.redis.Redis;
import com.blockchain.server.tron.service.TronTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/25 17:09
 * @user WIN10
 */
@Service
public class TronTokenServiceImpl implements TronTokenService {

    @Autowired
    private TronTokenMapper tokenMapper;
    @Autowired
    private Redis redis;

    /**
     * 根据币种名称查询币种信息
     * @param tokenAddr
     * @return
     */
    @Override
    public TronToken selectTronTokenByTokenName(String tokenAddr) {
        checkParam(tokenAddr);
        return tokenMapper.selectTronTokenByTokenName(tokenAddr);
    }


    /**
     * 查找所有币种信息
     * @return
     */
    @Override
    public List<TronToken> listTronToken() {
        return tokenMapper.selectAll();
    }


    @Override
    public TronToken selectByTokenSymbol(String tokenSymbol) {
        checkParam(tokenSymbol);
        TronToken where = new TronToken();
        where.setTokenSymbol(tokenSymbol);
        TronToken tronToken = tokenMapper.selectOne(where);
        ExceptionPreconditionUtils.notEmpty(tronToken);
        return tronToken;
    }

    @Override
    public TronToken findByTokenAddr(String tokenAddr) {
        checkParam(tokenAddr, new TronWalletException(TronWalletEnums.NULL_TOKENADDR));
        String _tokenAddr = tokenAddr.toLowerCase(); // 全部转为小写
        return tokenMapper.selectByPrimaryKey(_tokenAddr);
    }

    /**
     * 查找所有币种信息
     * @return
     */
    @Override
    public List<TronToken> selectAll() {
        return tokenMapper.selectAll();
    }

    /**
     * 从币种列表中获取单个币种信息
     * @param listToken
     * @param tokenAddr
     * @return
     */
    @Override
    public TronToken listTokenGetTronToken(List<TronToken> listToken, String tokenAddr) {
        for (int i = 0; i < listToken.size(); i++) {
            if (tokenAddr.equalsIgnoreCase(listToken.get(i).getTokenAddr())) {
                return listToken.get(i);
            }
        }
        return null;
    }

    /**
     * 查询TRC20币种信息
     * @param tokenSymbol
     * @return
     */
    @Override
    public TronTokenDTO selectTRC20Token(String tokenSymbol) {
        checkParam(tokenSymbol);
        // 从缓存中获取TRC20币种信息
        TronTokenDTO tronTokenDTO = (TronTokenDTO) redis.getOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_RTC20_TOKEN);
        // 缓存为空查询数据库并存到缓存中
        if (tronTokenDTO == null) {
            tronTokenDTO = tokenMapper.selectByToken(tokenSymbol);
            tronTokenDTO.setTimestamp(new Date().getTime());
            redis.setOpsForValue(TronConstant.RedisKey.TRON_BLOCKCHAIN_RTC20_TOKEN, tronTokenDTO);
        }
        return tronTokenDTO;
    }


    private void checkParam(String param) {
        ExceptionPreconditionUtils.notEmpty(param);
    }
    private void checkParam(String param, TronWalletException exception) {
        ExceptionPreconditionUtils.checkStringNotBlank(param, exception);
    }

}
