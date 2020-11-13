package com.blockchain.server.ltc.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.ltc.common.constants.BlockNumberConstans;
import com.blockchain.server.ltc.common.constants.TokenConstans;
import com.blockchain.server.ltc.common.constants.TransferConstans;
import com.blockchain.server.ltc.common.util.AddressSetRedisUtils;
import com.blockchain.server.ltc.common.util.BlockRedisUtils;
import com.blockchain.server.ltc.dto.BlockNumberDTO;
import com.blockchain.server.ltc.entity.WalletTransfer;
import com.blockchain.server.ltc.rpc.CoinUtils;
import com.blockchain.server.ltc.service.BlockNumberService;
import com.blockchain.server.ltc.service.WalletTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class RechargeCoinListenerTimer {

    @Autowired
    private AddressSetRedisUtils addressSetRedisUtils;

    @Autowired
    private BlockRedisUtils blockRedisUtils;

    @Autowired
    private CoinUtils coinUtils;

    @Autowired
    private BlockNumberService blockNumberService;

    @Autowired
    private WalletTransferService walletTransferService;

    public static final String RECHARGE_COIN_LISTENER_CRON = "0/30 * * * * ?"; //30秒每次
    public static final String CRAWL_OMIT_TX_IN_CRON = "0 0/2 * * * ?"; //2分钟每次

    /**
     * 查询区块链充值信息
     */
    public void rechargeCoinListener() {
        //获取已经同步的最大区块号
        int bigestBlock = blockNumberService.selectBigest();
        //区块链最新区块高度
        int blockHeightRpc = 0;
        //获取该区块hash
        String blockHash = "";
        //交易信息
        JSONArray transferArr = null;
        try {
            blockHeightRpc = coinUtils.getBlockCount();
            //是否已经同步到最新区块
            if (bigestBlock >= blockHeightRpc) {
                return;
            }

            blockHash = coinUtils.getBlockHash(bigestBlock);
            transferArr = coinUtils.listSinceBlock(blockHash);
        } catch (Exception e) {
            return;
        }

        //区块号自增
        bigestBlock++;
        //保存已经同步区块高度的到数据库
        blockNumberService.insertBlockNumber(bigestBlock, BlockNumberConstans.STATUS_W);

        int transferArrLen = transferArr.size();
        for (int i = 0; i < transferArrLen; i++) {
            JSONObject transferObj = transferArr.getJSONObject(i);
            if ("receive".equals(transferObj.getString("category"))) {
                String toAddr = transferObj.getString("address");
                if (addressSetRedisUtils.isExistsAddr(toAddr)) {
                    String txId = transferObj.getString("txid");

                    //解析交易
                    try {
                        JSONObject transfer = coinUtils.getTransaction(txId);
                        parseTransfer(transfer, txId);
                    } catch (Exception e1) {
                        return;
                    }
                }
            }
        }

        //修改区块同步状态
        blockNumberService.updateStatus(bigestBlock, BlockNumberConstans.STATUS_Y);

    }

    /**
     * 查询区块链遗漏的充值信息
     */
    public void crawlOmitTxIn() {
        long nowTime = System.currentTimeMillis();
        //获取等待区块列表
        List<BlockNumberDTO> blockNumberDTOList = blockNumberService.listByStatus(BlockNumberConstans.STATUS_W);
        for (BlockNumberDTO blockNumberDTO : blockNumberDTOList) {
            //判断时间差
            if (nowTime - blockNumberDTO.getCreateTime().getTime() < BlockNumberConstans.TIME_DIFFERENCE) {
                continue;
            }
            //获取爬取次数
            int count = blockRedisUtils.getBlockOptMapCount(blockNumberDTO.getBlockNumber());
            if (count >= 5) {   // 爬取次数超过五次跳出
                //删除缓存
                blockRedisUtils.delBlockOptMapCount(blockNumberDTO.getBlockNumber());
                //记录改为失败
                blockNumberService.updateStatus(blockNumberDTO.getBlockNumber(), BlockNumberConstans.STATUS_N);
                continue;
            }

            try {
                //本区块hash
                String thisBlockHash = coinUtils.getBlockHash(blockNumberDTO.getBlockNumber());
                //上一个区块hash
                String previousBlockHash = coinUtils.getBlockHash(blockNumberDTO.getBlockNumber() - 1);
                //交易数组
                JSONArray transferArr = coinUtils.listSinceBlock(previousBlockHash);

                int transferArrLen = transferArr.size();
                for (int i = 0; i < transferArrLen; i++) {
                    JSONObject transferObj = transferArr.getJSONObject(i);
                    //不为本次区块交易，不需要解析
                    if (!thisBlockHash.equals(transferObj.getString("blockhash"))) {
                        continue;
                    }
                    if ("receive".equals(transferObj.getString("category"))) {
                        String toAddr = transferObj.getString("address");
                        if (addressSetRedisUtils.isExistsAddr(toAddr)) {
                            String txId = transferObj.getString("txid");

                            //解析
                            try {
                                JSONObject transfer = coinUtils.getTransaction(txId);
                                parseTransfer(transfer, txId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                continue;
            }

            //爬取次数加1
            blockRedisUtils.incrementBlockOptMapCount(blockNumberDTO.getBlockNumber());
            try {
                //修改区块同步状态
                blockNumberService.updateStatus(blockNumberDTO.getBlockNumber(), BlockNumberConstans.STATUS_Y);
                //删除缓存
                blockRedisUtils.delBlockOptMapCount(blockNumberDTO.getBlockNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 解析交易
     *
     * @param transfer 交易
     * @param txId     交易id
     */
    private void parseTransfer(JSONObject transfer, String txId) {
        //交易金额，正数表示该交易增加钱包余额，负数表示该交易减少钱包余额
        if (transfer.getDouble("amount") <= 0) {
            return;
        }

        JSONArray details = transfer.getJSONArray("details");
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

        if (addressSetRedisUtils.isExistsAddr(toAddr)) {
            //修改该钱包余额，并插入一条充值记录
            WalletTransfer walletTransfer = new WalletTransfer();
            walletTransfer.setId(UUID.randomUUID().toString());
            walletTransfer.setHash(txId);
            walletTransfer.setFromAddr(fromAddr);
            walletTransfer.setToAddr(toAddr);
            walletTransfer.setAmount(amount.abs());
            walletTransfer.setGasPrice(fee);
            walletTransfer.setTokenId(TokenConstans.TOKEN_PROPERTY_ID);
            walletTransfer.setTokenSymbol(TokenConstans.TOKEN_SYMBOL);
            walletTransfer.setTransferType(TransferConstans.TYPE_IN);
            walletTransfer.setStatus(TransferConstans.STATUS_SUCCESS);
            walletTransfer.setCreateTime(new Date());
            walletTransfer.setUpdateTime(walletTransfer.getCreateTime());
            walletTransferService.handleBlockRecharge(walletTransfer);
        }
    }

}
