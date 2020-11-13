package com.blockchain.server.tron.tron;


import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.util.ExeptionPrintUtils;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.tron.common.constant.TronConstant;
import com.blockchain.server.tron.common.enums.TronWalletEnums;
import com.blockchain.server.tron.common.exception.TronWalletException;
import com.blockchain.server.tron.entity.TronWalletTransfer;
import com.blockchain.server.tron.service.TronWalletUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tron.demo.ECCreateKey;

import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * @author Harvey Luo
 * @date 2019/5/27 10:54
 */
@Component
public class TronUtil {

    @Autowired
    private TronWalletUtilService tronWalletUtilService;

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(TronUtil.class);


    /**
     * 解析tron查询交易信息状态
     *
     * @param hashId
     * @param tronWalletTransfer
     * @return
     */
    public Boolean getTransactions(String hashId, TronWalletTransfer tronWalletTransfer) {
        JSONObject body = tronWalletUtilService.getTransaction(hashId);
        if (body == null) return null;
        if ("".equalsIgnoreCase(body.toString())) {
            LOG.info("<====== 出币失败：交易hash ==> " + hashId + " ======>");
            return false;
        }
        try {
            // 返回结果分析出币信息
            String success = body.getJSONArray("ret").getJSONObject(0).getObject("contractRet", String.class);
            if (!success.equalsIgnoreCase(TronConstant.ResultType.RESULT_SUCCESS)) {
                LOG.info("<====== 出币失败：交易hash ==> " + hashId + " ======>");
                return false;
            }
            String txID = body.getObject("txID", String.class);
            JSONObject contract = body.getJSONObject("raw_data").getJSONArray("contract").getJSONObject(0);
            JSONObject value = contract.getJSONObject("parameter").getJSONObject("value");
            // 判断hash值是否相同
            if (txID.equalsIgnoreCase(tronWalletTransfer.getHash())) {
                // 判断出币内容是否相同
                    LOG.info("<====== 出币成功：交易hash ==> " + hashId + " ======>");
                    return true;
            } else {
                LOG.info("<====== 出币失败：交易hash ==> " + hashId + " ======>");
                return false;
            }
        } catch (Exception e) {
            LOG.info("<====== 出币异常：交易hash ==> " + hashId + " ======>");
            return null;
        }
    }

    /**
     * 创建账号方法
     *
     * @param owner
     * @param privateKey
     */
    @Transactional
    public String getCreateAccount(String owner, String privateKey) {
        String newHexAddress = null;
        String broadcastResponse = null;
        String accountAddress = null;
        // 生成地址
        try {
            accountAddress = ECCreateKey.private2Address();
            String[] split = accountAddress.split(TronConstant.DELIMIT);
            newHexAddress = split[2];
            LOG.info("newHexAddress is:"+newHexAddress);
            // 创建账户交易
            String accountTransaction = tronWalletUtilService.createAccount(owner, newHexAddress);
            JSONObject transactionJson = JSONObject.parseObject(accountTransaction);
            transactionJson.remove("raw_data_hex");
            // 签名
            String signResponse = tronWalletUtilService.getSign(JsonUtils.objectToJson(transactionJson), privateKey);
            JSONObject sign = JSONObject.parseObject(signResponse);
            sign.remove("raw_data_hex");
            String broadcastParam = JsonUtils.objectToJson(sign);
            // 广播交易
            broadcastResponse = tronWalletUtilService.getBroadcast(broadcastParam);
        } catch (Exception e) {
            LOG.error("exception happened! detail:{}", ExeptionPrintUtils.getExceptionStackTrace(e));
            e.printStackTrace();
            throw new TronWalletException(TronWalletEnums.CREATION_WALLET_ERROR);
        }
        JSONObject broadcast = JSONObject.parseObject(broadcastResponse);
        // if (!broadcast.getBoolean("result")) throw new TronWalletException(TronWalletEnums.CREATION_WALLET_ERROR);
        // 保存地址私钥
        return accountAddress;
    }

    /**
     * 获取最新区块号
     *
     * @return
     */
    public BigInteger getNewBlock() {
        JSONObject newBlock = tronWalletUtilService.getNewBlock();
        if (newBlock == null) return null;
        return newBlock.getJSONObject("block_header").getJSONObject("raw_data").getBigInteger("number");
    }

    public static String tryToHexAddr(String addr) {
        if (isValid(addr)) {
            addr = addr.replace("0x", "");
            String regPattern = "[0-9a-fA-F]{42}";
            if (Pattern.matches(regPattern, addr)) {
                return addr;
            }
            return decodeBase58Address(addr);

        }
        return null;
    }

    private static boolean isValid(String param) {
        return param != null && !"".equalsIgnoreCase(param);
    }


    public static String decodeBase58Address(String addr) {
        if (isValid(addr) && addr.length() >= 4) {
            byte[] bytes = Base58.decode58Check(addr);
            return bytesToHexString(bytes);
        }
        return null;
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        return sb.toString();
    }


}
