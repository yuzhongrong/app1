package com.blockchain.server.eth.web3j.impl;

import com.blockchain.server.eth.common.config.EthConfig;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.dto.Web3jWalletDTO;
import com.blockchain.server.eth.web3j.IBaseWeb3j;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.ipc.WindowsIpcService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class BaseWeb3jImpl implements IBaseWeb3j {
    Admin admin;
    Web3j web3j;
    Geth geth;

    @Autowired
    EthConfig ethConfig;

    // ETH小数位
    static final int ETH_DECIMALS = 18;

    static final long TIME_DIFFE = 1800000;


    public BaseWeb3jImpl(EthConfig ethConfig) {
        if (ethConfig.getUrlType().equals("ipc")) {
            if (ethConfig.getOs().equals("windows")) {
                this.admin = Admin.build(new WindowsIpcService(ethConfig.getUrl()));
                this.web3j = Web3j.build(new WindowsIpcService(ethConfig.getUrl()));
                this.geth = Geth.build(new WindowsIpcService(ethConfig.getUrl()));
            } else if (ethConfig.getOs().equals("unix")) {
                this.admin = Admin.build(new UnixIpcService(ethConfig.getUrl()));
                this.web3j = Web3j.build(new UnixIpcService(ethConfig.getUrl()));
                this.geth = Geth.build(new UnixIpcService(ethConfig.getUrl()));
            }
        } else if (ethConfig.getUrlType().equals("rpc")) {
            this.admin = Admin.build(new HttpService(ethConfig.getUrl()));
            this.web3j = Web3j.build(new HttpService(ethConfig.getUrl()));
            this.geth = Geth.build(new HttpService(ethConfig.getUrl()));
        }
    }

    /**
     * 执行合约打包
     *
     * @param fromAddr        支付地址
     * @param fromPrivateKey  支付地址私钥
     * @param hashVal         合约地址
     * @param month           合约方法
     * @param gasPrice        旷工费用
     * @param inputParameters 方法参数
     * @return hash
     */
    @Override
    public String transact(String fromAddr, String fromPrivateKey, String hashVal, String month, BigInteger gasPrice,
                           BigInteger gasLimit, List<Type> inputParameters) {
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    fromAddr,
                    DefaultBlockParameterName.LATEST
            ).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            Function function = new Function(
                    month,
                    inputParameters,
                    Arrays.asList(new TypeReference<Type>() {
                    }));
            Credentials credentials = Credentials.create(fromPrivateKey);
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, hashVal,
                    encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public List<Type> staticFun(String fromAddr, String hashVal, String month, List<Type> inputParameters,
                                List<TypeReference<?>> outputParameters) {
        try {
            Function function = new Function(month, inputParameters, outputParameters);
            String functionEncoder = FunctionEncoder.encode(function);
            org.web3j.protocol.core.methods.request.Transaction transaction =
                    org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(fromAddr, hashVal,
                            functionEncoder);
            EthCall response = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> types = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
            return types;
        } catch (IOException e) {
            e.printStackTrace();
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }


    @Override
    public TransactionReceipt getTransactionStatusByHash(String hash, Date date) {
        try {
            EthTransaction ethTransaction = web3j.ethGetTransactionByHash(hash).send();
            if (ethTransaction.getResult() == null) {
                Long currTime = new Date().getTime();
                if (currTime - date.getTime() >= TIME_DIFFE) {
                    TransactionReceipt transactionReceipt = new TransactionReceipt();
                    transactionReceipt.setTransactionHash(hash);
                    transactionReceipt.setGasUsed("0x0");
                    transactionReceipt.setStatus("0x16");
                    return transactionReceipt;
                }else{
                    return null;
                }
            }
            EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(hash).send();
            return ethGetTransactionReceipt.getTransactionReceipt().get();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public byte[] stringToBytes(String string, int length) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen = new byte[length];
        System.arraycopy(byteValue, 0, byteValueLen, 0, byteValue.length);
        return byteValueLen;
    }

    @Override
    public BigInteger getEthBlock() {
        try {
            return web3j.ethBlockNumber().send().getBlockNumber();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public List<EthBlock.TransactionResult> getTokenTransactionList(BigInteger block) {
        try {
            EthBlock.Block ethBlock =
                    web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(block), true).send().getBlock();
            return ethBlock.getTransactions();
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }


}
