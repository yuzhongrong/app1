package com.blockchain.server.eos.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.eos.common.constant.EosConstant;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.dto.WalletInDTO;
import com.blockchain.server.eos.entity.Wallet;
import com.blockchain.server.eos.entity.WalletTransfer;
import com.blockchain.server.eos.service.EosWalletService;
import com.blockchain.server.eos.service.EosWalletTransferService;
import com.blockchain.server.eos.service.EosWalletUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Harvey Luo
 * @date 2019/5/27 10:54
 */
@Component
public class EosUtil {

    @Autowired
    private EosWalletService eosWalletService;
    @Autowired
    private EosWalletTransferService eosWalletTransferService;
    @Autowired
    private EosWalletUtilService eosWalletUtilService;

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(EosUtil.class);

    /**
     * 递归方式判断查询返回账户历史数据
     *
     * @param walletInDTO
     * @param offset
     * @return
     */
    public JSONArray recursion(WalletInDTO walletInDTO, Integer offset) {

        LOG.info("********recursion********"+eosWalletUtilService+" "+walletInDTO+"  "+offset);
        if(walletInDTO==null)return null;
        JSONObject bodyBody = eosWalletUtilService.getActions(walletInDTO.getAccountName(), offset.toString());
        if (bodyBody == null) return null;
        JSONArray actions = bodyBody.getJSONArray("actions");
        BigInteger blockNumber = walletInDTO.getBlockNumber();
        if (actions.size() > 0) {
            JSONObject job = actions.getJSONObject(0);
            JSONObject action_trace = job.getJSONObject("action_trace");
            BigInteger blockNum = action_trace.getBigInteger("block_num");
            if (blockNum.compareTo(blockNumber) <= 0 || offset.equals(EosConstant.RPCConstant.OFFSET_MAX))
                return actions;
            else {
                return recursion(walletInDTO, offset + EosConstant.RPCConstant.ADD_OFFSET);
            }
        }
        return null;
    }

    /**
     * 分析账户历史记录
     *
     * @param actions
     * @param tokenName
     * @param walletInDTO
     */
    public void handTransaction(JSONArray actions, String tokenName, WalletInDTO walletInDTO) {
        if (actions.size() > 0) {
            for (int i = 0; i < actions.size(); i++) {
                JSONObject job = actions.getJSONObject(i);
                JSONObject action_trace = job.getJSONObject("action_trace");
                JSONObject act = action_trace.getJSONObject("act");
                String token = act.getObject("account", String.class);
                // 判断币种地址
                if (!token.equalsIgnoreCase(tokenName)) continue;
                JSONObject data = act.getJSONObject("data");
                String to = data.getObject("to", String.class);
                // 判断资金账户
                if (to == null || !walletInDTO.getAccountName().equalsIgnoreCase(to)) continue;
                // 判断是否为本地钱包
                String memo = data.getObject("memo", String.class);
                Integer walletId = null;
                try {
                    walletId = Integer.parseInt(memo.trim());
                } catch (Exception e) {
                    continue;
                }
                Wallet wallet = eosWalletService.selectWalletById(walletId.toString());
                if (wallet == null) continue;
                // 获取充值金额和币种符号
                String quantity = data.getObject("quantity", String.class);
                String[] quantitys = quantity.split(" ");
                String amount = quantitys[0];
                String symbol = quantitys[1];
                if (!wallet.getTokenSymbol().equalsIgnoreCase(symbol)) continue;

                String hash = action_trace.getString("trx_id");
                // 判断数据库是否存在该记录
                Integer row = eosWalletTransferService.countByHashAndTransferType(hash, EosConstant.TransferType.TRANSFER_IN);
                if (row != 0) continue;
                String from = data.getObject("from", String.class);
                BigInteger blockNum = action_trace.getBigInteger("block_num");
                // 修改钱包插入充值记录
                eosWalletTransferService.handleWalletAndWalletTransfer(hash,
                        wallet.getId(),
                        null,
                        EosConstant.TransferType.TRANSFER_IN,
                        new BigDecimal(quantitys[0]),
                        tokenName,
                        quantitys[1],
                        memo,
                        blockNum);
                LOG.info("************************充值成功： hash值 ==》 " + hash + " + 币种名称 ==》 " + tokenName + " + 钱包地址 ==》 " + wallet.getId() + " + 金额 ==》" + quantity + "************************");

                // System.out.println("hashId ===>" + hash);
                // System.out.println("blockNum ===>" + blockNum);
                // System.out.println("amount ==> " + amount);
                // System.out.println("symbol ==> " + symbol);
                // System.out.println("from ==> " + from);
                // System.out.println("to ==> " + to);
                // System.out.println("quantity ==> " + quantity);
                // System.out.println("memo ==> " + memo);
            }
        }
    }

    /**
     * 解析eos查询交易信息状态
     *
     * @param blockNum
     * @param hashId
     * @param walletTransfer
     * @return
     */
    public Boolean getTransactions(String blockNum, String hashId, WalletTransfer walletTransfer) {
        JSONObject body = eosWalletUtilService.getTransaction(blockNum, hashId);
        if (body == null || "".equalsIgnoreCase(body.toString())) {
            LOG.info("************************出币失败：区块高度" + blockNum + "交易hash：" + hashId + "************************");
            return false;
        }
        JSONObject action = null;
        JSONObject data = null;
        try {
            // 返回结果分析出币信息
            JSONObject trx = body.getJSONObject("trx");
            JSONObject trx1 = trx.getJSONObject("trx");
            JSONArray actions = trx1.getJSONArray("actions");
            action = actions.getJSONObject(0);
            data = action.getJSONObject("data");
            if (null == data || "".equalsIgnoreCase(data.toString())) {
                LOG.info("************************出币失败：区块高度" + blockNum + "交易hash：" + hashId + "************************");
                return false;
            } else {
                // 支付人地址：
                // String from = data.getObject("from", String.class);
                // 收款人地址：
                String to = data.getObject("to", String.class);
                // 提现由系统账户转账到用户提供账户
                // 判断to是否为用户提供的地址名称
                if (to.equalsIgnoreCase(walletTransfer.getAccountName())) return true;
                else {
                    LOG.info("************************出币失败：区块高度" + blockNum + "交易hash：" + hashId + "************************");
                    throw new EosWalletException(EosWalletEnums.CURRENCY_FAILURE_ERROR);
                }
            }
        } catch (Exception e) {
            LOG.info("************************出币失败：区块高度" + blockNum + "交易hash：" + hashId + "************************");
            // TODO 出币失败是否抛出错误
            // throw new EosWalletException(EosWalletEnums.CURRENCY_FAILURE_ERROR);
            return false;
        }
    }

}
