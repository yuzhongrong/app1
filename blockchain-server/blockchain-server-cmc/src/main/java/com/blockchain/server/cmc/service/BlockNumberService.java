package com.blockchain.server.cmc.service;

import com.blockchain.server.cmc.dto.BlockNumberDTO;

import java.util.List;

public interface BlockNumberService {

    /**
     * 新增已经同步的区块，不成功报异常
     *
     * @param blockNumber 区块高度
     * @param status 状态
     * @return
     */
    void insertBlockNumber(int blockNumber, String status);

    /**
     * 修改区块装填，不成功报异常
     *
     * @param blockNumber 区块高度
     * @return
     */
    void updateStatus(int blockNumber, String status);

    /**
     * 根据状态获取同步区块
     *
     * @param status 状态
     * @return
     */
    List<BlockNumberDTO> listByStatus(String status);

    /**
     * 查询已经同步的最大区块号
     *
     * @return
     */
    int selectBigest();
}
