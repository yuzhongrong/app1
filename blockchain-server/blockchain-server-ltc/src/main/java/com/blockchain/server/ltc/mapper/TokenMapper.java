package com.blockchain.server.ltc.mapper;

import com.blockchain.server.ltc.dto.TokenDTO;
import com.blockchain.server.ltc.entity.Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * TokenMapper 数据访问类
 * @date 2019-02-16 15:08:16
 * @version 1.0
 */
@Repository
public interface TokenMapper extends Mapper<Token> {

    /**
     * 获取币种
     *
     * @return
     */
    List<TokenDTO> listToken();

    /**
     * 获取币种，根据tokenid
     *
     * @param tokenId 币种id
     * @return
     */
    TokenDTO selectTokenById(@Param("tokenId") Integer tokenId);

}