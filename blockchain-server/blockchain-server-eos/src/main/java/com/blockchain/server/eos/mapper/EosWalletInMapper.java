package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.dto.WalletInDTO;
import com.blockchain.server.eos.entity.WalletIn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/19 13:58
 * @user WIN10
 */
@Repository
public interface EosWalletInMapper extends Mapper<WalletIn> {

    /**
     * 查询充值资金账户
     * @param to
     * @return
     */
    List<WalletInDTO> listWalletInByAccountName(@Param("to") String to);
}
