package com.blockchain.server.ltc.service.impl;

import com.blockchain.server.ltc.common.constants.BlockNumberConstans;
import com.blockchain.server.ltc.common.enums.ExceptionEnums;
import com.blockchain.server.ltc.common.exception.ServiceException;
import com.blockchain.server.ltc.dto.BlockNumberDTO;
import com.blockchain.server.ltc.entity.BlockNumber;
import com.blockchain.server.ltc.mapper.BlockNumberMapper;
import com.blockchain.server.ltc.rpc.CoinUtils;
import com.blockchain.server.ltc.service.BlockNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlockNumberServiceImpl implements BlockNumberService {
    @Autowired
    private BlockNumberMapper blockNumberMapper;
    @Autowired
    private CoinUtils coinUtils;

    @Override
    public void insertBlockNumber(int blockNumber, String status) {
        BlockNumber blockNumberInsert = new BlockNumber();
        blockNumberInsert.setBlockNumber(blockNumber);
        blockNumberInsert.setStatus(status);
        blockNumberInsert.setCreateTime(new Date());
        blockNumberInsert.setUpdateTime(blockNumberInsert.getCreateTime());
        int count = blockNumberMapper.insertSelective(blockNumberInsert);
        if (count != 1) {
            throw new ServiceException(ExceptionEnums.INSERT_BLOCKNUMBER_ERROR);
        }
    }

    @Override
    public void updateStatus(int blockNumber, String status) {
        BlockNumber blockNumberUp = new BlockNumber();
        blockNumberUp.setBlockNumber(blockNumber);
        blockNumberUp.setStatus(status);
        blockNumberUp.setUpdateTime(new Date());
        int countRow = blockNumberMapper.updateByPrimaryKeySelective(blockNumberUp);
        if (countRow != 1) {
            throw new ServiceException(ExceptionEnums.UPDATE_BLOCK_NUMBER_STATUS_ERROR);
        }
    }

    @Override
    public List<BlockNumberDTO> listByStatus(String status) {
        return blockNumberMapper.listByStatus(status);
    }

    @Override
    public int selectBigest() {
        Integer blockNumber = blockNumberMapper.selectBigest();
        if (blockNumber == null) {
            try {
                blockNumber = coinUtils.getBlockCount();
                this.insertBlockNumber(blockNumber, BlockNumberConstans.STATUS_Y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return blockNumber;
    }

}
