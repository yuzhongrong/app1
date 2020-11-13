package com.blockchain.server.tron.tron;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Harvey Luo
 * @date 2019/6/14 11:45
 */
@Component
public class Wallet {
    private static final Logger logger = LoggerFactory.getLogger(Wallet.class);

    private static byte addressPreFixByte = Constant.ADD_PRE_FIX_BYTE_MAINNET;

    public static byte[] decodeFromBase58Check(String addressBase58) {
        if (StringUtils.isEmpty(addressBase58)) {
            logger.warn("Warning: Address is empty !!");
            return null;
        }
        byte[] address = Base58.decode58Check(addressBase58);
        if (address == null) {
            return null;
        }

        if (!addressValid(address)) {
            return null;
        }

        return address;
    }

    public static boolean addressValid(byte[] address) {
        if (ArrayUtils.isEmpty(address)) {
            logger.warn("Warning: Address is empty !!");
            return false;
        }
        if (address.length != Constant.ADDRESS_SIZE / 2) {
            logger.warn(
                    "Warning: Address length need " + Constant.ADDRESS_SIZE + " but " + address.length
                            + " !!");
            return false;
        }
        if (address[0] != addressPreFixByte) {
            logger.warn("Warning: Address need prefix with " + addressPreFixByte + " but "
                    + address[0] + " !!");
            return false;
        }
        //Other rule;
        return true;
    }

}
