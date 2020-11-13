package com.blockchain.common.base.util;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.constant.TokenTypeEnums;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.TokenDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 单点登录工具类，使用token获取、设置用户信息
 */
public class SSOHelper {
    private static final int TOKEN_INVALID_DAYS = 30;//token过期时间
    private static final int TOKEN_PC_INVALID_DAYS = 30;//token过期时间

    /**
     * 获取登录用户id
     *
     * @param redisTemplate redis
     */
    public static String getUserId(RedisTemplate redisTemplate, HttpServletRequest request) {
        SessionUserDTO user = getUser(redisTemplate, request);
//        resetTokenTTL(redisTemplate, user.getTel(), user);//重新设置过期时间
        return user.getId();
    }

    /***
     * 获取登录用户缓存信息
     * 已登录返回用户id
     * 未登录返回null
     * @param redisTemplate
     * @param request
     * @return
     */
    public static String getUserIdIsExits(RedisTemplate redisTemplate, HttpServletRequest request) {
        SessionUserDTO effectiveUserIfExist = getEffectiveUserIfExist(redisTemplate, request);
        if (effectiveUserIfExist == null) {
            return null;
        }
        return effectiveUserIfExist.getId();
    }

    /**
     * 检查用户登录状态
     */
    public static void checkUser(RedisTemplate redisTemplate, HttpServletRequest request) {
        getUser(redisTemplate, request);
    }

    /**
     * 获取登录用户缓存信息
     *
     * @param redisTemplate redis
     */
    public static SessionUserDTO getUser(RedisTemplate redisTemplate, HttpServletRequest request) {
        SessionUserDTO userDTO = getEffectiveUserIfExist(redisTemplate, request);
        if (userDTO == null) {
            throw new BaseException(BaseResultEnums.NO_LOGIN);
        }
        return userDTO;
    }

    /**
     * 设置用户
     *
     * @param user          用户信息
     * @param redisTemplate redis
     */
    public static void setUser(SessionUserDTO user, RedisTemplate redisTemplate) {
        setUser(user, redisTemplate, TokenTypeEnums.APP.getValue());
    }

    /**
     * 设置用户
     *
     * @param user          用户信息
     * @param redisTemplate redis
     * @param tokenType     tokenType
     */
    public static void setUser(SessionUserDTO user, RedisTemplate redisTemplate, String tokenType) {
        if (StringUtils.isBlank(user.getTel())) {
            throw new BaseException(BaseResultEnums.DEFAULT);
        }
        resetTokenTTL(redisTemplate, user.getTel(), user, tokenType);
    }

    /**
     * 移除用户token信息
     */
    public static void removeUser(HttpServletRequest request, RedisTemplate redisTemplate) {
        removeUser(request, redisTemplate, TokenTypeEnums.APP.getValue());
    }

    /**
     * 移除用户token信息
     */
    public static void removeUser(HttpServletRequest request, RedisTemplate redisTemplate, String tokenType) {
        SessionUserDTO user = getEffectiveUserIfExist(redisTemplate, request);
        if (user == null) {
            return;
        }
        String tel = user.getTel();
        if (!StringUtils.isEmpty(tel)) {
            String key = BaseConstant.REDIS_TOKEN_KEY + tel;
            if (tokenType.equals(TokenTypeEnums.PC.getValue())) {
                key = BaseConstant.REDIS_TOKEN_PC_KEY + tel;
            }
            if (redisTemplate.hasKey(key)) {
                redisTemplate.delete(key);
            }
        }
    }

    /**
     * 重置token过期时间
     *
     * @param redisTemplate redis缓存
     * @param tel           tel
     * @param user          用户信息
     */
    public static void resetTokenTTL(RedisTemplate redisTemplate, String tel, SessionUserDTO user, String tokenType) {
        if (tokenType.equals(TokenTypeEnums.APP.getValue())) {
            redisTemplate.opsForValue().set(BaseConstant.REDIS_TOKEN_KEY + tel, user, TOKEN_INVALID_DAYS, TimeUnit.DAYS);
        } else {
            redisTemplate.opsForValue().set(BaseConstant.REDIS_TOKEN_PC_KEY + tel, user, TOKEN_PC_INVALID_DAYS, TimeUnit.HOURS);
        }
    }

    /**
     * 获取登录用户缓存信息
     *
     * @param redisTemplate redis
     */
    private static SessionUserDTO getEffectiveUserIfExist(RedisTemplate redisTemplate, HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.SSO_TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        TokenDTO tokenDTO = RSACoderUtils.decryptToken(token);
        if (tokenDTO == null) {
            return null;
        }
        String key = BaseConstant.REDIS_TOKEN_KEY + tokenDTO.getTel();
        if (tokenDTO.getTokenType().equals(TokenTypeEnums.PC.getValue())) {
            key = BaseConstant.REDIS_TOKEN_PC_KEY + tokenDTO.getTel();
        }
        if (redisTemplate.hasKey(key)) {//如果有token，拿到用户信息
            SessionUserDTO user = (SessionUserDTO) redisTemplate.opsForValue().get(key);
            if (!user.getTimestamp().equals(tokenDTO.getTimestamp())) {
                throw new BaseException(BaseResultEnums.LOGIN_REPLACED);//如果时间戳不对，则提示账号在别处登录
            }
            return user;
        }
        return null;
    }
}
