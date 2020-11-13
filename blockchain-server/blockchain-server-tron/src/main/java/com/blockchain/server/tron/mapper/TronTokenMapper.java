package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.dto.TronTokenDTO;
import com.blockchain.server.tron.entity.TronToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 11:13
 * @user WIN10
 */
@Repository
public interface TronTokenMapper extends Mapper<TronToken> {

    /**
     * 根据代币名称查询代币信息
     * @param tokenAddr
     * @return
     */
    TronToken selectTronTokenByTokenName(@Param("tokenAddr") String tokenAddr);

    /**
     * 查询币种信息
     * @param tokenSymbol
     * @return
     */
    TronTokenDTO selectByToken(@Param("tokenSymbol") String tokenSymbol);
}
