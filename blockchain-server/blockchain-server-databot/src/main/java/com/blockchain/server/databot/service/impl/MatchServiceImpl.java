package com.blockchain.server.databot.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.dto.rpc.PublishOrderDTO;
import com.blockchain.server.databot.feign.CctFeign;
import com.blockchain.server.databot.service.MatchService;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MatchServiceImpl implements MatchService, ITxTransaction {

    @Autowired
    private CctFeign cctFeign;

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public boolean match(String matchUserId, PublishOrderDTO bymatch) {
        BigDecimal totalNum = bymatch.getLastNum();
        BigDecimal price = bymatch.getUnitPrice();
        String coinName = bymatch.getCoinName();
        String unitName = bymatch.getUnitName();
        String matchId;

        //生成对应的订单
        if (bymatch.getOrderType().equals(CommonConstant.BUY)) {
            //被撮合订单是买单，发布一条对应的卖单
            matchId = handleLimitSellToBot(matchUserId, coinName, unitName, totalNum, price);
        } else if (bymatch.getOrderType().equals(CommonConstant.SELL)) {
            //被撮合订单是卖单，发布一条对应的买单
            matchId = handleLimitBuyToBot(matchUserId, coinName, unitName, totalNum, price);
        } else {
            return false;
        }

        //调用撮合方法
        return handleMatchToBot(matchId, bymatch.getId());
    }

    /***
     * 发布限价买单 - 用于撮合机器人
     * @param userId
     * @param coinName
     * @param unitName
     * @param totalNum
     * @param price
     * @return
     */
    private String handleLimitBuyToBot(String userId, String coinName, String unitName,
                                       BigDecimal totalNum, BigDecimal price) {
        ResultDTO<String> resultDTO = cctFeign.handleLimitBuyToBot(userId, coinName, unitName, totalNum, price);
        return resultDTO.getData();
    }

    /***
     * 发布限价卖单 - 用于撮合机器人
     * @param userId
     * @param coinName
     * @param unitName
     * @param totalNum
     * @param price
     * @return
     */
    private String handleLimitSellToBot(String userId, String coinName, String unitName,
                                        BigDecimal totalNum, BigDecimal price) {
        ResultDTO<String> resultDTO = cctFeign.handleLimitSellToBot(userId, coinName, unitName, totalNum, price);
        return resultDTO.getData();
    }

    /***
     * 撮合 - 用于撮合机器人
     * @param matchId
     * @param bymatchId
     * @return
     */
    private Boolean handleMatchToBot(String matchId, String bymatchId) {
        ResultDTO<Boolean> resultDTO = cctFeign.handleMatchToBot(matchId, bymatchId);
        return resultDTO.getData();
    }
}
