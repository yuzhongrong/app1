package com.blockchain.server.cmc.mapper;

import com.blockchain.server.cmc.dto.BtcBlockNumberDTO;
import com.blockchain.server.cmc.entity.BtcBlockNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BtcBlockNumberMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BtcBlockNumberMapper extends Mapper<BtcBlockNumber> {

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
    List<BtcBlockNumberDTO> listByStatus(@Param("status") String status);

}