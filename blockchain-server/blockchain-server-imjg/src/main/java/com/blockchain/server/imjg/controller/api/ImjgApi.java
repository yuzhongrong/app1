package com.blockchain.server.imjg.controller.api;

public class ImjgApi {

    public static final String JGIM_CONTROLLER_API = "极光IM控制器";


    public static class GetSignature{
        public static final String METHOD_API_NAME = "获取极光签名接口";
        public static final String METHOD_API_NOTE = "获取极光初始化的一些相关参数";

    }

    public static class Register{
        public static final String METHOD_API_NAME = "极光IM注册接口";
        public static final String METHOD_API_NOTE = "注册用户到极光IM";
        public static final String METHOD_API_USERNAME="用户名";
        public static final String METHOD_API_PASSWORD="密码";
    }

    public static class GetUserMessages{
        public static final String METHOD_API_NAME = "获取用户的消息记录接口";
        public static final String METHOD_API_NOTE = "根据业务ID(订单ID)获取当前用户消息记录，一次20条";
        public static final String METHOD_API_DATAID = "业务ID";
        public static final String METHOD_API_USERID = "当前用户ID";

        public static final String METHOD_API_ORDER = "排序方式";
        public static final String METHOD_API_PAGE = "页码";
        public static final String METHOD_API_SIZE = "每页大小";

        public static final String METHOD_API_APPID = "appid";
        public static final String METHOD_API_TOKEN = "token";

    }

    public static class SaveMessageLog{
        public static final String METHOD_API_NAME = "保存消息接口";
        public static final String METHOD_API_NOTE = "在页面发送极光消息成功后，保存消息到服务端";
        public static final String METHOD_API_DATAID = "业务ID";
        public static final String METHOD_API_TARGETUSERID = "消息接收方UserId";
        public static final String METHOD_API_FROMUSERID = "消息发送方UserId";
        public static final String METHOD_API_MSGTYPE = "消息类型";
        public static final String METHOD_API_MSGBODY = "消息体";
        public static final String METHOD_API_NODECUE = "是否是节点提示词(0:不是节点提示词,1:两边均可见的提示词,2:接收方可见的节点提示词)";
    }

    public static class GetJGAccount{
        public static final String METHOD_API_NAME = "获取用户极光的用户名";
        public static final String METHOD_API_NOTE = "获取当前用户的极光用户名";
        public static final String METHOD_API_USERID = "用户ID";
        public static final String METHOD_API_APPID = "appid";
        public static final String METHOD_API_TOKEN = "token";
    }

    public static class MsgBody{
        public static final String METHOD_API_NAME = "消息体具体字段(加*代表必传)";
        public static final String METHOD_API_NOTE = "发送消息记录中的消息体字段说明";

        public static final String METHOD_API_MSGTYPE = "消息类型(text,voice,image,file,video,location,custom)-*";

        public static final String METHOD_API_TEXT = "消息类型为text时,文本内容";
        public static final String METHOD_API_MEDIA_ID = "发送图片、音视频、文件时的链接返回的资源ID";
        public static final String METHOD_API_FORMAT = "文件后缀";
        public static final String METHOD_API_FSIZE = "文件大小";
        public static final String METHOD_API_FNAME = "文件名";
        public static final String METHOD_API_WIDTH = "图片宽度";
        public static final String METHOD_API_HEIGHT = "图片高度";

    }

    public static class GetResource{
        public static final String METHOD_API_NAME = "获取资源的访问路径";
        public static final String METHOD_API_NOTE = "根据极光提供的mediaId访问资源";

        public static final String METHOD_API_MEDIA_ID = "发送图片、音视频、文件时的链接返回的资源ID";
    }



}
