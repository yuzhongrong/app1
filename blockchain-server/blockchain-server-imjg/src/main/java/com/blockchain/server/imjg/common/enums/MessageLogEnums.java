package com.blockchain.server.imjg.common.enums;

import lombok.Getter;
import lombok.Setter;

public enum MessageLogEnums {

    STATUS_NORMAL("NORMAL", "正常"),
    STATUS_CANCEL("CANCEL", "已删除");

    MessageLogEnums(String code, String name) {
        this.code=code;
        this.name=name;
    }

    @Setter
    @Getter
    /** **/
    private String code;

    @Setter
    @Getter
    /** **/
    private String name;

}
