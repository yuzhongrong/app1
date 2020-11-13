package com.blockchain.server.imjg.inner.api;

public class ImjgInnerApi {

    public static final String IMJG_INNER_API = "极光IM服务接口";

    public static class SendSingleMsg{
        public static final String METHOD_API_PTYPE = "服务模块名";
        public static final String METHOD_API_NAME = "发送单聊消息接口";
        public static final String METHOD_API_NOTE = "发送单聊信息并保存消息记录";
        public static final String METHOD_API_DATAID = "业务ID";
        public static final String METHOD_API_MSGTYPE = "消息类型";
        public static final String METHOD_API_FUSERID = "发送方用户ID";
        public static final String METHOD_API_TUSERID = "接收方用户ID";
        public static final String METHOD_API_MSGBODYJSON = "消息DTO类的JSON串";
        /*0:默认，不是节点消息;1:双方可见的节点消息;2:接收方可见的节点消息*/
        public static final String METHOD_API_NODECUE = "节点消息标志状态";

    }

    public static class GetUserStatFromJG{
        public static final String METHOD_API_NAME = "获取用户状态接口";
        public static final String METHOD_API_NOTE = "根据系统UserId,获取用户在极光的登录状态";
        public static final String METHOD_API_USERID = "系统用户ID";


    }

    public static class GetJGAccount{
        public static final String METHOD_API_NAME = "获取用户极光的用户名";
        public static final String METHOD_API_NOTE = "获取当前用户的极光用户名";
        public static final String METHOD_API_USERID = "用户ID";
    }

    public static class GetUserMessages{

        public static final String METHOD_API_NAME = "获取保存在系统的用户的消息记录接口";
        public static final String METHOD_API_NOTE = "根据业务ID(订单ID)获取当前用户消息记录，一次20条";

        public static final String METHOD_API_PTYPE = "服务模块名";
        public static final String METHOD_API_DATAID = "业务ID";
        public static final String METHOD_API_USERID = "当前用户ID";

        public static final String METHOD_API_ORDER = "排序方式";
        public static final String METHOD_API_PAGE = "页码";
        public static final String METHOD_API_SIZE = "每页大小";
    }

    public static class GetUserMessagesFromJG{
        public static final String METHOD_API_NAME = "获取保存在极光的用户消息记录(15天内)";
        public static final String METHOD_API_NOTE = "获取保存在极光的用户消息记录(15天内)";
        public static final String METHOD_API_USERID = "系统用户ID";
        public static final String METHOD_API_BEGIN_TIME = "消息开始时间";
        public static final String METHOD_API_END_TIME = "消息截止时间(7天内)";
        public static final String METHOD_API_CURSOR = "获取下一次查询";
    }

    public static class GetGroupMessagesFromJG{
        public static final String METHOD_API_NAME = "获取保存在极光的群消息记录(15天内)";
        public static final String METHOD_API_NOTE = "获取保存在极光的群消息记录(15天内)";
        public static final String METHOD_API_GID = "群ID";
        public static final String METHOD_API_BEGIN_TIME = "消息开始时间";
        public static final String METHOD_API_END_TIME = "消息截止时间(7天内)";
        public static final String METHOD_API_CURSOR = "获取下一次查询";
    }

    public static class GetRoomMessagesFromJG{
        public static final String METHOD_API_NAME = "获取保存在极光的聊天室消息记录(15天内)";
        public static final String METHOD_API_NOTE = "获取保存在极光的聊天室消息记录(15天内)";
        public static final String METHOD_API_CHATROOMID = "聊天室ID";
        public static final String METHOD_API_BEGIN_TIME = "消息开始时间";
        public static final String METHOD_API_END_TIME = "消息截止时间(7天内)";
        public static final String METHOD_API_CURSOR = "获取下一次查询";
    }

}


