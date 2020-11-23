package com.blockchain.server.cmc.mapper;

import com.blockchain.server.cmc.dto.BtcTokenDTO;
import com.blockchain.server.cmc.entity.BtcToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BtcTokenMapper 数据访问类
 * @date 2019-02-16 15:08:16
 * @version 1.0
 */
@Repository
public interface BtcTokenMapper extends Mapper<BtcToken> {

    /**
     * 获取币种
     *
     * @return
     */
    List<BtcTokenDTO> listToken();

    /**
     * 获取币种，根据tokenid
     *
     * @param tokenId 币种id
     * @return
     */
    BtcTokenDTO selectTokenById(@Param("tokenId") Integer tokenId);

}