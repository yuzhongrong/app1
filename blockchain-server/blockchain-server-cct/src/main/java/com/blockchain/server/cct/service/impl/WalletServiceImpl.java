package com.blockchain.server.cct.service.impl;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.cct.common.enums.CctDataEnums;
import com.blockchain.server.cct.common.enums.CctEnums;
import com.blockchain.server.cct.common.exception.CctException;
import com.blockchain.server.cct.feign.*;
import com.blockchain.server.cct.service.WalletService;
import feign.Feign;
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
    private CMCFeign cmcFeign;

    //主网标识
    private static final String BTC_NET = "BTC";
    private static final String ETH_NET = "ETH";
    private static final String EOS_NET = "EOS";
    private static final String LTC_NET = "LTC";
    private static final String TRX_NET = "TRX";
    private static final String TETH_NET = "TETH";
    private static final String CMC_NET = "CMC";
    @Override
    @Transactional
    public void handleBalance(String userId, String publishId, String tokenName, String coinNet, BigDecimal freeBalance, BigDecimal freezeBalance) {
        WalletOrderDTO order = new WalletOrderDTO();
        order.setUserId(userId);
        order.setRecordId(publishId);
        order.setTokenName(tokenName);
        order.setWalletType(CctDataEnums.CCT_APP.getStrVlue());
        order.setFreeBalance(freeBalance);
        order.setFreezeBalance(freezeBalance);
        LOG.info("调用钱包Feign，入参： order :" + order.toString() + " coinNet :" + coinNet);
        //根据主网标识，区分微服务调用
        switch (coinNet) {
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
                break;

            case TETH_NET:
                tethFeign.order(order);
                break;

            case CMC_NET:
                cmcFeign.order(order);
                break;
            default:
                LOG.error("更新余额失败，钱包处理出现未知主网标识！");
                throw new CctException(CctEnums.PUBLISH_ORDER_WALLET_ERROR);
        }
    }

    @Override
    @Transactional
    public void handleRealBalance(String userId, String recordId, String tokenName, String coinNet, BigDecimal freeBalance, BigDecimal freezeBalance, BigDecimal gasBalance) {
        WalletChangeDTO change = new WalletChangeDTO();
        change.setUserId(userId);
        change.setRecordId(recordId);
        change.setTokenName(tokenName);
        change.setFreeBalance(freeBalance);
        change.setFreezeBalance(freezeBalance);
        change.setGasBalance(gasBalance);
        change.setWalletType(CctDataEnums.CCT_APP.getStrVlue());
        LOG.info("调用钱包Feign，入参： change :" + change.toString() + " coinNet :" + coinNet);
        //根据主网标识，区分微服务调用
        switch (coinNet) {
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
                break;
            case TETH_NET:
                tethFeign.change(change);

            case CMC_NET:
                cmcFeign.change(change);
                break;
            default:
                LOG.error("扣款或加钱失败，钱包处理出现未知主网标识！");
                throw new CctException(CctEnums.PUBLISH_ORDER_WALLET_ERROR);
        }
    }

    @Override
    public void isPassword(String pass) {
        LOG.info("校验密码，参数：" + pass);
        ethFeign.isPassword(pass);
    }
}
