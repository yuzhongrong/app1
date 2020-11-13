package com.blockchain.server.currency.service;

import com.blockchain.server.currency.dto.MyTokenDTO;

import java.util.List;

/**
 * Datetime:    2020/4/29   13:48
 * Author:      Xra rong tao
 * @title: Mytoken业务类
 */

public interface MyTokenService {

    final static  String FK_USDT = "FK-USDT";
    /**
     * 获取当前系统实时行情信息
     * @return
     */
     List<MyTokenDTO> getMyToken();
}
