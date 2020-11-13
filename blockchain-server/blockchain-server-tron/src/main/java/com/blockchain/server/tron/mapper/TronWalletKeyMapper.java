package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronWalletKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Set;

/**
 * @author Harvey Luo
 * @date 2019/6/13 14:14
 */
@Repository
public interface TronWalletKeyMapper extends Mapper<TronWalletKey> {

    /**
     * 查询所有16进制地址
     * @return
     */
    Set<String> selectAllHexAddrs();
}
