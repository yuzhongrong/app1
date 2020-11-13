package com.blockchain.server.eos.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletBalanceBatchDTO;
import com.blockchain.server.eos.service.EosWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: qinhui
 * @date: 2020/6/2 16:44
 */
@RestController
@RequestMapping("/inner/wallet")
public class EosWalletBatchInner {
    private static final Logger LOG = LoggerFactory.getLogger(EosWalletBatchInner.class);
    @Autowired
    private EosWalletService eosWalletService;

    @PostMapping("/getBalanceByIdAndTypesBatch")
    ResultDTO getBalanceByIdAndTypesBatch(@RequestParam(name = "walletTypes", required = false) String[] walletTypes) {
        eosWalletService.getBalanceByIdAndTypesBatch(walletTypes);
        return ResultDTO.requstSuccess();
    }
}
