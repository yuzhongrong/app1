package com.blockchain.server.sysconf.common.enums;

import lombok.Getter;

/**
 * @author huangxl
 * @create 2018-11-22 09:29
 */
public enum SysConfigEnums {
    NOTICE_DOES_NOT_EXIST(1009, "公告不存在","no motice"),
    IMAGE_DOES_NOT_EXIST(1010, "轮播图不存在","no image"),
    IS_STAR(1012, "不可重复点赞","Non repeatable thumb up"),
    FILE_UPLOAD_ERROR(1011, "文件上传失败","file upload error");

    @Getter
    private int code;
    @Getter
    private String msg;
    @Getter
    private String enMsg;

    private SysConfigEnums(int code, String msg, String enMsg) {
        this.code = code;
        this.msg = msg;
        this.enMsg = enMsg;
    }
}
