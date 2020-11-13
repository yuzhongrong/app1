package com.blockchain.server.btc.mapper;

import com.blockchain.server.btc.dto.BtcApplicationDTO;
import com.blockchain.server.btc.entity.BtcApplication;
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