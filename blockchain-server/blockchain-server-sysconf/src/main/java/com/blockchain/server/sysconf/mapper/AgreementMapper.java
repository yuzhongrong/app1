package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.Agreement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AgreementMapper extends Mapper<Agreement> {

    /**
     * 查询用户协议（客户端）
     * @param languages
     * @return
     */
    Agreement findAgreement(@Param("type") String type,@Param("languages") String languages);


}
