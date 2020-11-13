package com.blockchain.server.base.conf;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author huangxl
 * 全局拦截器
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    /**
     * 处理参数异常
     *
     * @param e 异常信息
     * @return 返回内容
     */
    @ExceptionHandler(BaseException.class)
    public ResultDTO handleInvalidArgumentException(BaseException e) {
        e.printStackTrace();
        return new ResultDTO(e.getCode(), e.getMsg(), null);
    }

    /**
     * 处理rpc调用异常
     *
     * @param e 异常信息
     * @return 返回内容
     */
    @ExceptionHandler(RPCException.class)
    public ResultDTO handleRPCException(RPCException e) {
        e.printStackTrace();
        return new ResultDTO(e.getCode(), e.getMsg(), null);
    }

    /**
     * 处理未知异常
     *
     * @param e 异常信息
     * @return 返回内容
     */
    @ExceptionHandler(Exception.class)
    public ResultDTO handleUnknownException(Exception e) {
        LOG.error("{}系统抛出异常，异常是：{}，异常信息是：{}", new Date(), e.getClass().getName(), e.getMessage());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        if (request != null) {
            LOG.error("请求路径：{}", request.getRequestURL().toString());
        }
        if (response != null) {
            LOG.error("响应头：{}", response.getHeader("Content-Type"));
        }
        e.printStackTrace();
        BaseException base = new BaseException();
        return new ResultDTO(base.getCode(), base.getMsg(), null);
    }
}
