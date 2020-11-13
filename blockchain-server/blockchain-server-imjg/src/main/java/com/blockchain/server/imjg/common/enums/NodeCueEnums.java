package com.blockchain.server.imjg.common.enums;


import lombok.Getter;
import lombok.Setter;

public enum NodeCueEnums {

    NODE_CUE_NO(0,"不是节点消息"),
    NODE_CUE_ALL(1,"双方可见的节点消息"),
    NODE_CUE_SEND(2,"接收方可见的节点消息");


    NodeCueEnums(int code, String name){
        this.code = code;
        this.name = name;
    }

    @Setter
    @Getter
    private int code;

    @Setter
    @Getter
    private String name;
}
