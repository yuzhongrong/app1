package com.blockchain.server.ltc.mapper;

import com.blockchain.server.ltc.dto.BlockNumberDTO;
import com.blockchain.server.ltc.entity.BlockNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BlockNumberMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BlockNumberMapper extends Mapper<BlockNumber> {

    /**
     * 查询已经同步的最大区块号
     *
     * @return
     */
    Integer selectBigest();

    /**
     * 根据状态获取同步区块
     *
     * @param status 状态
     * @return
     */
    List<BlockNumberDTO> listByStatus(@Param("status") String status);

}