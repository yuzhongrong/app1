package com.blockchain.server.eos.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.common.util.EosUtil;
import com.blockchain.server.eos.dto.WalletInDTO;
import com.blockchain.server.eos.entity.WalletIn;
import com.blockchain.server.eos.mapper.WalletInMapper;
import com.blockchain.server.eos.service.EosWalletInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/25 10:11
 * @user WIN10
 */
@Service
public class EosWalletInServiceImpl implements EosWalletInService {

    @Autowired
    private WalletInMapper walletInMapper;
    @Autowired
    private EosUtil eosUtil;

    /**
     * 查询对应的代币名称充值账户
     * @param tokenName
     * @return
     */
    @Override
    public WalletInDTO selectWalletInAccount(String tokenName) {
        ExceptionPreconditionUtils.notEmpty(tokenName);
        return walletInMapper.selectWalletInAccount(tokenName, EosConstant.WalletInStatus.EOS_WALLET_IN_USABLE);
    }

    /**
     * 查询充值资金账户
     * @param to
     * @return
     */
    @Override
    public List<WalletInDTO> listWalletInByAccountName(String to) {
        ExceptionPreconditionUtils.notEmpty(to);
        return walletInMapper.listWalletInByAccountName(to);
    }

    /**
     * 充值方法
     *
     * @param listTokenName
     */
    @Override
    @Transactional
    public void handleAccountBlockTransactions(HashSet<String> listTokenName) {
        for (String tokenName : listTokenName) {
            WalletInDTO walletInDTO = this.selectWalletInAccount(tokenName);

            JSONArray actions = eosUtil.recursion(walletInDTO, EosConstant.RPCConstant.OFFSET);
            if (actions == null) return;
            Integer row = updateWalletInBlockNumber(walletInDTO, actions);
            if (row != 1) return;

            eosUtil.handTransaction(actions, tokenName, walletInDTO);
        }
    }

    @Override
    public WalletIn selectByAccountName(String account) {
        WalletIn example = new WalletIn();
        example.setAccountName(account);
        return walletInMapper.selectOne(example);
    }

    /**
     * 修改资金账户区块字段
     *
     * @param walletInDTO
     * @param actions
     * @return
     */
    private Integer updateWalletInBlockNumber(WalletInDTO walletInDTO, JSONArray actions) {
        JSONObject job = actions.getJSONObject(actions.size() - 1);
        JSONObject action_trace = job.getJSONObject("action_trace");
        BigInteger blockNum = action_trace.getBigInteger("block_num");
        return walletInMapper.updateWalletInBlockNumber(walletInDTO.getAccountName(), blockNum);
    }


}
