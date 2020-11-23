package com.blockchain.server.cmc.mapper;

import com.blockchain.server.cmc.dto.BtcApplicationDTO;
import com.blockchain.server.cmc.entity.BtcApplication;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BtcApplicationMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BtcApplicationMapper extends Mapper<BtcApplication> {

    /**
     * 获取应用列表
     *
     * @return
     */
    List<BtcApplicationDTO> listApplication();

}