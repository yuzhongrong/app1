package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.otc.common.constant.CommonConstans;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.entity.Coin;
import com.blockchain.server.otc.feign.*;
import com.blockchain.server.otc.service.CoinService;
import com.blockchain.server.otc.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    private BTCFeign btcFeign;
    @Autowired
    private EOSFeign eosFeign;
    @Autowired
    private ETHFeign ethFeign;
    @Autowired
    private LTCFeign ltcFeign;
    @Autowired
    private TronFeign tronFeign;

    @Autowired
    private TETHFeign tethFeign;

    @Autowired
    private CoinService coinService;

    //主网标识
    private static final String BTC_NET = "BTC";
    private static final String ETH_NET = "ETH";
    private static final String EOS_NET = "EOS";
    private static final String LTC_NET = "LTC";
    private static final String TRX_NET = "TRX";

    private static final String TETH_NET = "TETH";

    @Override
    @Transactional
    public void handleBalance(String userId, String publishId, String coinName, String unitName, BigDecimal freeBalance, BigDecimal freezeBalance) {
        WalletOrderDTO order = new WalletOrderDTO();
        order.setUserId(userId);
        order.setRecordId(publishId);
        order.setTokenName(coinName);
        order.setWalletType(CommonConstans.C2C_APP);
        order.setFreeBalance(freeBalance);
        order.setFreezeBalance(freezeBalance);
        LOG.info("调用钱包Feign，入参： order :" + order.toString() + " unitName :" + unitName);
        Coin coin = selectByCoinAndUnit(coinName, unitName);
        //根据主网标识，区分微服务调用
        switch (coin.getCoinNet()) {
            case BTC_NET:
                btcFeign.order(order);
                break;
            case ETH_NET:
                ethFeign.order(order);
                break;
            case EOS_NET:
                eosFeign.order(order);
                break;
            case LTC_NET:
                ltcFeign.order(order);
                break;
            case TRX_NET:
                tronFeign.order(order);

            case TETH_NET:
                tethFeign.order(order);
                break;
            default:
                LOG.error("更新余额失败，钱包处理出现未知主网标识：" + coin.getCoinNet());
                throw new OtcException(OtcEnums.WALLET_COIN_NET_ERROR);
        }
    }

    @Override
    @Transactional
    public void handleRealBalance(String userId, String recordId, String coinName, String unitName, BigDecimal freeBalance, BigDecimal freezeBalance, BigDecimal gasBalance) {
        WalletChangeDTO change = new WalletChangeDTO();
        change.setUserId(userId);
        change.setRecordId(recordId);
        change.setTokenName(coinName);
        change.setFreeBalance(freeBalance);
        change.setFreezeBalance(freezeBalance);
        change.setGasBalance(gasBalance);
        change.setWalletType(CommonConstans.C2C_APP);
        LOG.info("调用钱包Feign，入参： change :" + change.toString() + " unitName :" + unitName);
        Coin coin = selectByCoinAndUnit(coinName, unitName);
        //根据主网标识，区分微服务调用
        switch (coin.getCoinNet()) {
            case BTC_NET:
                btcFeign.change(change);
                break;
            case ETH_NET:
                ethFeign.change(change);
                break;
            case EOS_NET:
                eosFeign.change(change);
                break;
            case LTC_NET:
                ltcFeign.change(change);
                break;
            case TRX_NET:
                tronFeign.change(change);

            case TETH_NET:
                tethFeign.change(change);
                break;
            default:
                LOG.error("扣款或加钱失败，钱包处理出现未知主网标识：" + coin.getCoinNet());
                throw new OtcException(OtcEnums.WALLET_COIN_NET_ERROR);
        }
    }

    @Override
    public void isPassword(String pass) {
        LOG.info("校验密码，参数：" + pass);
        if (StringUtils.isBlank(pass)) {
            throw new OtcException(OtcEnums.PASS_NULL);
        }
        ethFeign.isPassword(pass);
    }

    /***
     * 查询代币信息
     * @param coinName
     * @param unitName
     * @return
     */
    private Coin selectByCoinAndUnit(String coinName, String unitName) {
        Coin coin = coinService.selectByCoinAndUnit(coinName, unitName);
        if (coin == null) {
            throw new OtcException(OtcEnums.COIN_NULL);
        }
        return coin;
    }
}
