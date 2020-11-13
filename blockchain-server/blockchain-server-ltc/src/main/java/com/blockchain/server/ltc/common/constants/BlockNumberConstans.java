package com.blockchain.server.ltc.common.constants;

import com.blockchain.common.base.util.DateTimeUtils;

/**
 * @author hugq
 * @date 2019/2/16 0016 16:31
 */
public class BlockNumberConstans {
    //同步区块高度状态 等待
    public static final String STATUS_W = "W";
    //同步区块高度状态 失败
    public static final String STATUS_N = "N";
    //同步区块高度状态 成功
    public static final String STATUS_Y = "Y";

    //查询遗漏区块高度时间差 毫秒数
    public static final long TIME_DIFFERENCE = 10 * DateTimeUtils.MILLISECOND_OF_MINUTE;

}
