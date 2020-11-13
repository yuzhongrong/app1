package com.blockchain.server.teth.web3j;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * web3j 连接基础类
 */
public interface IBaseWeb3j {

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
    String transact(String fromAddr, String fromPrivateKey, String hashVal, String month, BigInteger gasPrice, BigInteger gasLimit, List<Type> inputParameters);

    /**
     * 调用智能合约常量方法
     *
     * @param fromAddr         支付地址
     * @param hashVal          合约地址
     * @param month            方法名
     * @param inputParameters  接收参数
     * @param outputParameters 输出参数
     * @return
     */
    List<Type> staticFun(String fromAddr, String hashVal, String month, List<Type> inputParameters, List<TypeReference<?>> outputParameters);

    /**
     * 根据hash值查询交易信息
     *
     * @param hash
     * @return
     */
    TransactionReceipt getTransactionStatusByHash(String hash, Date time);

    /**
     * 字符串转bytes长度
     *
     * @param string 字符串
     * @param length 长度
     * @return
     */
    byte[] stringToBytes(String string, int length);

    /**
     * 获取以太坊最新区块
     *
     * @return
     */
    BigInteger getEthBlock();

    /**
     * 获取区块交易数据
     *
     * @param block 区块号
     * @return
     */
    List<EthBlock.TransactionResult> getTokenTransactionList(BigInteger block);
}
