package com.blockchain.server.teth.web3j.impl;

import com.blockchain.server.teth.common.enums.EthWalletEnums;
import com.blockchain.server.teth.common.exception.EthWalletException;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.WindowsIpcService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.blockchain.server.teth.web3j.impl.WalletWeb3jImpl.BALANCE_OF;
import static org.junit.Assert.*;

public class WalletWeb3jImplTest {

    @Test
    public void getTokenBalance() {

        String addr="0x7e5b98021d862c9d41290d767af6b1f4033e454e";
        String tokenAddr="0xd6A47B2384B25eca74BdC077D1Eb077c03936DB1";
        String methodName = BALANCE_OF;
        List inputParameters = new ArrayList<>();
        List outputParameters = new ArrayList<>();
        Address address = new Address(addr);
        inputParameters.add(address);
        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(addr, tokenAddr, data);
        BigInteger balanceValue = BigInteger.ZERO;
        try {


            Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/bfc1481597114b739f5697d15c043d37"));
//            Web3j web3j = Web3j.build(new WindowsIpcService(""));

            EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());

            System.out.println((BigInteger) results.get(0).getValue());
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }

    }




    @Test
    public void getTokenBalance1()  {


        String account="0x7e5b98021d862c9d41290d767af6b1f4033e454e";
        String coinAddress="0xd6A47B2384B25eca74BdC077D1Eb077c03936DB1";
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/bfc1481597114b739f5697d15c043d37"));

        Function balanceOf = new Function("balanceOf",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));

        if (coinAddress == null) {

        }
        String value = null;
        try {
            value = web3j.ethCall(org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(account, coinAddress, FunctionEncoder.encode(balanceOf)), DefaultBlockParameterName.PENDING).send().getValue();
        } catch (IOException e) {

        }
        int decimal = getTokenDecimal(web3j, coinAddress);
        System.out.println("---------获取合约代币 精度--->" + decimal);
        BigDecimal balance = new BigDecimal(new BigInteger(value.substring(2), 16).toString(10)).divide(BigDecimal.valueOf(Math.pow(10, decimal)));
        System.out.println("---------获取合约代币余额成功-->" + balance.toPlainString());

    }



    /**
     * 查询代币精度
     */
    public static int getTokenDecimal(Web3j web3j, String contractAddr) {
        String methodName = "decimals";
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters = new ArrayList<>();

        TypeReference<Uint8> typeReference = new TypeReference<Uint8>() {
        };
        outputParameters.add(typeReference);

        Function function = new Function(methodName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.request.Transaction transaction = org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction("0x0000000000000000000000000000000000000000", contractAddr, data);


        EthCall ethCall = null;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();

        } catch (InterruptedException e) {

            System.out.println("获取代币精度失败");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("获取代币精度失败");
            e.printStackTrace();
        }
        List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
        if (null == results || 0 == results.size()) {
            return 0;
        }
        return Integer.parseInt(results.get(0).getValue().toString());
    }



}