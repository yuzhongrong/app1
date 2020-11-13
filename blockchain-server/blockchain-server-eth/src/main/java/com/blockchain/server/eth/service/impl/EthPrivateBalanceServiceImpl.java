package com.blockchain.server.eth.service.impl;

import com.blockchain.server.eth.common.constants.EthWalletConstants;
import com.blockchain.server.eth.entity.EthPrivateBalance;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.mapper.EthPrivateBalanceMapper;
import com.blockchain.server.eth.service.IEthPrivateBalanceService;
import com.blockchain.server.eth.service.IEthTokenService;
import com.blockchain.server.eth.service.IEthWalletService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EthPrivateBalanceServiceImpl implements IEthPrivateBalanceService {

    @Autowired
    private EthPrivateBalanceMapper privateBalanceMapper;
    @Autowired
    private IEthWalletService walletService;
    @Autowired
    private IEthWalletTransferService ethWalletTransferService;
    @Autowired
    private IEthTokenService ethTokenService;

    @Override
    public List<EthPrivateBalance> listPrivateBalance() {
        return privateBalanceMapper.listPrivateBalance();
    }

    @Override
    @Transactional
    public void handlePrivateBalance(EthPrivateBalance privateBalance, Date date) {
        //扣减私募资金
        privateBalanceMapper.releaseBalance(privateBalance.getId(), date);

        //释放钱包冻结余额到可用余额
        walletService.updateBalanceByAddrInRowLock(privateBalance.getAddr(), privateBalance.getTokenAddr(),
                privateBalance.getWalletType(), privateBalance.getReleaseBalance(),
                privateBalance.getReleaseBalance().negate(), date);

        //插入记录
        EthToken ethToken = ethTokenService.findByTokenName(privateBalance.getTokenSymbol());
        ethWalletTransferService.insert(UUID.randomUUID().toString(), "", privateBalance.getAddr(),
                privateBalance.getReleaseBalance(), ethToken, EthWalletConstants.TransferType.PRIVATE_BALANCE, date);
    }

}
