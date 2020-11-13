package com.blockchain.server.btc.service.impl;

import com.blockchain.server.btc.common.constants.BtcBlockNumberConstans;
import com.blockchain.server.btc.common.enums.BtcEnums;
import com.blockchain.server.btc.common.exception.BtcException;
import com.blockchain.server.btc.dto.BtcBlockNumberDTO;
import com.blockchain.server.btc.entity.BtcBlockNumber;
import com.blockchain.server.btc.mapper.BtcBlockNumberMapper;
import com.blockchain.server.btc.rpc.BtcUtils;
import com.blockchain.server.btc.service.BtcBlockNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BtcBlockNumberServiceImpl implements BtcBlockNumberService {
    @Autowired
    private BtcBlockNumberMapper btcBlockNumberMapper;
    @Autowired
    private BtcUtils btcUtils;


    @Override
    public void insertBlockNumber(int blockNumber, String status) {
        BtcBlockNumber btcBlockNumber = new BtcBlockNumber();
        btcBlockNumber.setBlockNumber(blockNumber);
        btcBlockNumber.setStatus(status);
        btcBlockNumber.setCreateTime(new Date());
        btcBlockNumber.setUpdateTime(btcBlockNumber.getCreateTime());
        int count = btcBlockNumberMapper.insertSelective(btcBlockNumber);
        if (count != 1) {
            throw new BtcException(BtcEnums.INSERT_BLOCKNUMBER_ERROR);
        }
    }

    @Override
    public void updateStatus(int blockNumber, String status) {
        BtcBlockNumber btcBlockNumber = new BtcBlockNumber();
        btcBlockNumber.setBlockNumber(blockNumber);
        btcBlockNumber.setStatus(status);
        btcBlockNumber.setUpdateTime(new Date());
        int countRow = btcBlockNumberMapper.updateByPrimaryKeySelective(btcBlockNumber);
        if (countRow != 1) {
            throw new BtcException(BtcEnums.UPDATE_BLOCK_NUMBER_STATUS_ERROR);
        }
    }

    @Override
    public List<BtcBlockNumberDTO> listByStatus(String status) {
        return btcBlockNumberMapper.listByStatus(status);
    }

    @Override
    public int selectBigest() {
        Integer blockNumber = btcBlockNumberMapper.selectBigest();
        if (blockNumber == null) {
            try {
                blockNumber = btcUtils.getBlockCount();
                this.insertBlockNumber(blockNumber, BtcBlockNumberConstans.STATUS_Y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return blockNumber;
    }

}
