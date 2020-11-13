package com.blockchain.server.teth.common.constants;

/**
 * 以太坊Config配置常量参数
 *
 * @author YH
 * @date 2019年2月18日15:25:04
 */
public class EthConfigConstants {

    public static final String MODULE_TYPE = "eth"; // 所属于钱包模块
    public static final String MODULE_TYPE_USDT="USDT";//USDT地址
    public static final String MODULE_TYPE_GFC="GFC";   //GFC地址
    public static final String MODULE_TYPE_ETH="ETH";   //GFC地址
    public static final String USDT_TOKENADDR="0xdac17f958d2ee523a2206206994597c13d831ec7";   //USDT地址
    public static final String GFC_TOKENADDR="0xF35b2d419fdaDC7F418cc630dB78545C6A03f2eB";   //GFC地址
    public static final int STATUS_DISABLED = 0; // 禁用
    public static final int STATUS_NORMAL = 1; // 启用

    /**
     * 是否允许提现的配置
     */
    public static class IsApplyTxout {
        public static final String PARAM_NAME = "is_apply_txout"; // 参数名称
        public static final String PARAM_VALUE_YES = "Y"; // 允许
        public static final String PARAM_VALUE_NO = "N"; // 不允许
    }

    /**
     * 手续费配置
     */
    public static class EthGasConfig {
        public static final String PARAM_NAME = "eth_gas_config_"; // 参数名称
    }
}
