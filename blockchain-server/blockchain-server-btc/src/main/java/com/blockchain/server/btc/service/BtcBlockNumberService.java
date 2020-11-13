package com.blockchain.server.btc.service;

import com.blockchain.server.btc.dto.BtcBlockNumberDTO;

import java.util.List;

public interface BtcBlockNumberService {

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
    List<BtcBlockNumberDTO> listByStatus(String status);

    /**
     * 查询已经同步的最大区块号
     *
     * @return
     */
    int selectBigest();
}
