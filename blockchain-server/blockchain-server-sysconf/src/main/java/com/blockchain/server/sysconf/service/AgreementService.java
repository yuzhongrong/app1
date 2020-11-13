package com.blockchain.server.sysconf.service;



import com.blockchain.server.sysconf.entity.Agreement;


public interface AgreementService {

    /**
     * 查询用户协议（客户端）
     * @param languages
     * @return
     */
    Agreement findAgreement(String type,String languages);


}
