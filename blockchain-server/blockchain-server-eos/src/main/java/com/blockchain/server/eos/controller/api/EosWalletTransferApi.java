package com.blockchain.server.eos.controller.api;

/**
 * @author Harvey
 * @date 2019/2/19 18:10
 * @user WIN10
 */
public class EosWalletTransferApi {
    public static final String EOS_WALLET_TRANSFER_API = "EOS钱包交易记录控制器";

    public static final String PAGENUM = "当前页数";
    public static final String PAGESIZE = "页数展示条数";


    public static class GetTransfer {
        public static final String METHOD_API_NAME = "app端查询所有交易记录";
        public static final String METHOD_API_NOTE = "app端查询所有交易记录";
        public static final String METHOD_API_TOKEN_NAME = "币种名称";
        public static final String METHOD_API_WALLET_TYPE = "钱包标识";
    }

    public static class pcGetTransfer {
        public static final String METHOD_API_NAME = "pc端查询所有交易记录";
        public static final String METHOD_API_NOTE = "pc端查询所有交易记录";
        public static final String METHOD_API_TOKEN_NAME = "币种名称";
        public static final String METHOD_API_WALLET_TYPE = "钱包标识";
    }
}
