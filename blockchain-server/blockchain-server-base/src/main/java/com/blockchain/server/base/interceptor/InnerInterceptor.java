package com.blockchain.server.base.interceptor;

import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.base.conf.GlobalExceptionHandle;
import com.blockchain.server.base.redis.IpInnerCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InnerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @Autowired
    private IpInnerCache innerCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //经过nginx代理后用户的真实ip
        String ip = request.getHeader("X-Real-IP");
        System.out.println("------------内部访问接口IP-----------"+ip);
        String innerIps = innerCache.getIpInner();
        //若不是内部访问返回链接错误
        if(innerIps != null && !innerIps.equals("") && innerIps.indexOf(ip) == -1){
            LOG.info("被拦截ip:"+ ip);
            throw new BaseException(BaseResultEnums.NOT_FOUND);
        }
        return super.preHandle(request, response, handler);
    }
}
