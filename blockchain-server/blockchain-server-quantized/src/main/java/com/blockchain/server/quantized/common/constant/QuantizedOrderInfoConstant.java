package com.blockchain.server.quantized.common.constant;

public class QuantizedOrderInfoConstant {
    public static final String STATUS_U = "U";//不存在
    public static final String STATUS_Y = "Y";//同步成功
    public static final String STATUS_N = "N";//同步中
    public static final String STATUS_F = "F";//同步失败
    public static final int DEFAULT_TIMES = 1;//默认处理次数
    public static final int DEFAULT_DIE = 0;//默认活跃状态为死亡
    public static final int MAX_TIMES = 5;//处理次数
    public static final int IS_DIE = 1;//死亡状态
    public static final int DEFAULT_CREATETIME = 120*1000;//两分钟

}
