package com.blockchain.server.btc.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.btc.common.constants.BtcBlockNumberConstans;
import com.blockchain.server.btc.common.constants.BtcConstans;
import com.blockchain.server.btc.common.constants.BtcTransferConstans;
import com.blockchain.server.btc.common.constants.UsdtConstans;
import com.blockchain.server.btc.common.util.BtcAddressSetRedisUtils;
import com.blockchain.server.btc.common.util.BtcBlockRedisUtils;
import com.blockchain.server.btc.dto.BtcBlockNumberDTO;
import com.blockchain.server.btc.entity.BtcWalletTransfer;
import com.blockchain.server.btc.rpc.BtcUtils;
import com.blockchain.server.btc.rpc.UsdtUtils;
import com.blockchain.server.btc.service.BtcBlockNumberService;
import com.blockchain.server.btc.service.BtcWalletTransferService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class RechargeBtcAndUsdtBlockTimer {

    @Autowired
    private BtcAddressSetRedisUtils btcAddressSetRedisUtils;

    @Autowired
    private BtcBlockRedisUtils btcBlockRedisUtils;

    @Autowired
    private BtcUtils btcUtils;

    @Autowired
    private UsdtUtils usdtUtils;

    @Autowired
    private BtcBlockNumberService btcBlockNumberService;

    @Autowired
    private BtcWalletTransferService btcWalletTransferService;

    public static final String RECHARGE_BTC_AND_USDT_BLOCK_CRON = "0/30 * * * * ?";
    public static final String CRAWL_OMIT_TX_IN_CRON = "0 0/2 * * * ?";

    /**
     * 查询区块链充值信息
     */
    public void rechargeBtcAndUsdtBlock() {
        //获取已经同步的最大区块号
        int bigestBlock = btcBlockNumberService.selectBigest();
        //区块链最新区块高度
        int blockHeightRpc = 0;
        //获取该区块hash
        String blockHash = "";
        //交易信息
        JSONArray transferArr = null;
        try {
            blockHeightRpc = btcUtils.getBlockCount();
            //是否已经同步到最新区块
            if (bigestBlock >= blockHeightRpc) {
                return;
            }
//            //判断是否有未确认交易
//            if (btcUtils.getUnconfirmedBalance() != 0) {
//                return;
//            }

            blockHash = btcUtils.getBlockHash(bigestBlock);
            transferArr = btcUtils.listSinceBlock(blockHash);
        } catch (Exception e) {
            return;
        }

        //区块号自增
        bigestBlock++;
        //保存已经同步区块高度的到数据库
        btcBlockNumberService.insertBlockNumber(bigestBlock, BtcBlockNumberConstans.STATUS_W);

        int transferArrLen = transferArr.size();
        for (int i = 0; i < transferArrLen; i++) {
            JSONObject transferObj = transferArr.getJSONObject(i);
            if ("receive".equals(transferObj.getString("category"))) {
                String toAddr = transferObj.getString("address");
                if (btcAddressSetRedisUtils.isExistsAddr(toAddr)) {
                    String txId = transferObj.getString("txid");

                    try {
                        //先用usdt解析，如果不能解析，则用btc解析
                        JSONObject transferUsdt = usdtUtils.getTransaction(txId);
                        try {
                            //修改该usdt钱包余额，并插入一条充值记录
                            parseUsdtTransfer(transferUsdt, txId);
                        } catch (Exception e) {
                            return;
                        }
                    } catch (Exception e) {
                        //btc解析
                        try {
                            JSONObject transferBtc = btcUtils.getTransaction(txId);
                            parseBtcTransfer(transferBtc, txId);
                        } catch (Exception e1) {
                            return;
                        }
                    }
                }
            }
        }

        //修改区块同步状态
        btcBlockNumberService.updateStatus(bigestBlock, BtcBlockNumberConstans.STATUS_Y);

    }

    /**
     * 查询区块链遗漏的充值信息
     */
    public void crawlOmitTxIn() {
        long nowTime = System.currentTimeMillis();
        //获取等待区块列表
        List<BtcBlockNumberDTO> blockNumberDTOList = btcBlockNumberService.listByStatus(BtcBlockNumberConstans.STATUS_W);
        for (BtcBlockNumberDTO blockNumberDTO : blockNumberDTOList) {
            //判断时间差
            if (nowTime - blockNumberDTO.getCreateTime().getTime() < BtcBlockNumberConstans.TIME_DIFFERENCE) {
                continue;
            }
            //获取爬取次数
            int count = btcBlockRedisUtils.getBlockOptMapCount(blockNumberDTO.getBlockNumber());
            if (count >= 5) {   // 爬取次数超过五次跳出
                //删除缓存
                btcBlockRedisUtils.delBlockOptMapCount(blockNumberDTO.getBlockNumber());
                //记录改为失败
                btcBlockNumberService.updateStatus(blockNumberDTO.getBlockNumber(), BtcBlockNumberConstans.STATUS_N);
                continue;
            }

            try {
                //本区块hash
                String thisBlockHash = btcUtils.getBlockHash(blockNumberDTO.getBlockNumber());
                //上一个区块hash
                String previousBlockHash = btcUtils.getBlockHash(blockNumberDTO.getBlockNumber() - 1);
                //交易数组
                JSONArray transferArr = btcUtils.listSinceBlock(previousBlockHash);

                int transferArrLen = transferArr.size();
                for (int i = 0; i < transferArrLen; i++) {
                    JSONObject transferObj = transferArr.getJSONObject(i);
                    //不为本次区块交易，不需要解析
                    if (!thisBlockHash.equals(transferObj.getString("blockhash"))) {
                        continue;
                    }
                    if ("receive".equals(transferObj.getString("category"))) {
                        String toAddr = transferObj.getString("address");
                        if (btcAddressSetRedisUtils.isExistsAddr(toAddr)) {
                            String txId = transferObj.getString("txid");

                            try {
                                //先用usdt解析，如果不能解析，则用btc解析
                                JSONObject transferUsdt = usdtUtils.getTransaction(txId);
                                try {
                                    //修改该usdt钱包余额，并插入一条充值记录
                                    parseUsdtTransfer(transferUsdt, txId);
                                } catch (Exception e) {
                                    continue;
                                }
                            } catch (Exception e) {
                                //btc解析
                                try {
                                    JSONObject transferBtc = btcUtils.getTransaction(txId);
                                    parseBtcTransfer(transferBtc, txId);
                                } catch (Exception e1) {
                                    continue;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }

            //爬取次数加1
            btcBlockRedisUtils.incrementBlockOptMapCount(blockNumberDTO.getBlockNumber());
            try {
                //修改区块同步状态
                btcBlockNumberService.updateStatus(blockNumberDTO.getBlockNumber(), BtcBlockNumberConstans.STATUS_Y);
                //删除缓存
                btcBlockRedisUtils.delBlockOptMapCount(blockNumberDTO.getBlockNumber());
            } catch (Exception e) {
            }

        }
    }

    /**
     * 解析btc交易
     *
     * @param transferBtc btc交易
     * @param txId        交易id
     */
    private void parseBtcTransfer(JSONObject transferBtc, String txId) {
        //交易金额，正数表示该交易增加钱包余额，负数表示该交易减少钱包余额
        if (transferBtc.getDouble("amount") <= 0) {
            return;
        }

        JSONArray details = transferBtc.getJSONArray("details");
        String toAddr = null;
        String fromAddr = null;
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal fee = BigDecimal.ZERO;

        for (int i = 0; i < details.size(); i++) {
            JSONObject jsonObject = details.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
            String category = jsonObject.getString("category");
            if ("receive".equals(category)) {
                toAddr = jsonObject.getString("address");
                amount = jsonObject.getBigDecimal("amount");
            } else if ("send".equals(category)) {
                fromAddr = jsonObject.getString("address");
                fee = jsonObject.getBigDecimal("fee").abs();
            }
        }

        //如果支付地址是节点钱包里面地址，不能算作充值，可能是随机找零过来的金额
        if (StringUtils.isNotEmpty(fromAddr)) {
            try {
                JSONObject valFfromAddr = btcUtils.validateAddress(fromAddr);
                if (valFfromAddr.getBoolean("ismine")) {
                    return;
                }
            } catch (Exception e) {
            }
        }

        if (btcAddressSetRedisUtils.isExistsAddr(toAddr)) {
            //修改该钱包余额，并插入一条充值记录
            BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
            btcWalletTransfer.setId(UUID.randomUUID().toString());
            btcWalletTransfer.setHash(txId);
            btcWalletTransfer.setFromAddr(fromAddr);
            btcWalletTransfer.setToAddr(toAddr);
            btcWalletTransfer.setAmount(amount.abs());
            btcWalletTransfer.setGasPrice(fee);
            btcWalletTransfer.setTokenId(BtcConstans.BTC_PROPERTY_ID);
            btcWalletTransfer.setTokenSymbol(BtcConstans.BTC_SYMBOL);
            btcWalletTransfer.setTransferType(BtcTransferConstans.TYPE_IN);
            btcWalletTransfer.setStatus(BtcTransferConstans.STATUS_SUCCESS);
            btcWalletTransfer.setCreateTime(new Date());
            btcWalletTransfer.setUpdateTime(btcWalletTransfer.getCreateTime());
            btcWalletTransferService.handleBlockRecharge(btcWalletTransfer);
        }
    }

    /**
     * 解析usdt交易
     *
     * @param transferUsdt usdt交易
     * @param txId         交易id
     */
    private void parseUsdtTransfer(JSONObject transferUsdt, String txId) {
        //交易是否有效交易
        if (transferUsdt.getBoolean("valid")) {
            //是否USDT交易
            if (transferUsdt.getInteger("propertyid") == UsdtConstans.USDT_PROPERTY_ID) {
                //支付地址
                String fromAddr = transferUsdt.getString("sendingaddress");
                if (StringUtils.isNotEmpty(fromAddr) && btcAddressSetRedisUtils.isExistsAddr(fromAddr)) {
                    return;
                }
                //是否充值到本地钱包地址
                String toAddr = transferUsdt.getString("referenceaddress");
                if (btcAddressSetRedisUtils.isExistsAddr(toAddr)) {
                    //修改该钱包余额，并插入一条充值记录
                    BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
                    btcWalletTransfer.setId(UUID.randomUUID().toString());
                    btcWalletTransfer.setHash(txId);
                    btcWalletTransfer.setFromAddr(fromAddr);
                    btcWalletTransfer.setToAddr(toAddr);
                    btcWalletTransfer.setAmount(transferUsdt.getBigDecimal("amount"));
                    btcWalletTransfer.setGasPrice(transferUsdt.getBigDecimal("fee"));
                    btcWalletTransfer.setTokenId(UsdtConstans.USDT_PROPERTY_ID);
                    btcWalletTransfer.setTokenSymbol(UsdtConstans.USDT_SYMBOL);
                    btcWalletTransfer.setTransferType(BtcTransferConstans.TYPE_IN);
                    btcWalletTransfer.setStatus(BtcTransferConstans.STATUS_SUCCESS);
                    btcWalletTransfer.setCreateTime(new Date());
                    btcWalletTransfer.setUpdateTime(btcWalletTransfer.getCreateTime());
                    btcWalletTransferService.handleBlockRecharge(btcWalletTransfer);
                }
            }
        }
    }

}
