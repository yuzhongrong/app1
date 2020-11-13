package com.blockchain.server.user.controller.api;

/**
 * @author Harvey
 * @date 2019/3/7 10:27
 * @user WIN10
 */
public class UserAuthenticationApi {
    public static final String USER_AUTHENTICATION_API = "用户信息认证管理器";

    public static class SelectLowAuth {
        public static final String METHOD_API_NAME = "查询用户初级审核申请";
        public static final String METHOD_API_NOTE = "根据用户id查询用户初级审核申请";

        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class SelectHighAuth {
        public static final String METHOD_API_NAME = "查询用户高级审核申请";
        public static final String METHOD_API_NOTE = "根据用户id查询用户高级审核申请";

        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class PassLowAuth {
        public static final String METHOD_API_NAME = "通过用户初级审核申请";
        public static final String METHOD_API_NOTE = "通过用户初级审核申请";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_APPLY_ID = "申请表单id";
    }

    public static class RejectLowAuth {
        public static final String METHOD_API_NAME = "驳回用户初级审核申请";
        public static final String METHOD_API_NOTE = "驳回用户初级审核申请";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_APPLY_ID = "申请表单id";
        public static final String METHOD_API_REMARK = "驳回原因(备注)";
    }

    public static class PassHighAuth {
        public static final String METHOD_API_NAME = "通过用户高级审核申请";
        public static final String METHOD_API_NOTE = "通过用户高级审核申请";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_APPLY_ID = "申请表单id";
    }

    public static class RejectHighAuth {
        public static final String METHOD_API_NAME = "驳回用户高级审核申请";
        public static final String METHOD_API_NOTE = "驳回用户高级审核申请";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_APPLY_ID = "申请表单id";
        public static final String METHOD_API_REMARK = "驳回原因(备注)";
    }

    public static class SelectUserAuthentication {
        public static final String METHOD_API_NAME = "查询用户认证信息";
        public static final String METHOD_API_NOTE = "查询用户认证信息";

        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class SelectUserAuthenticationInfo {
        public static final String METHOD_API_NAME = "查询用户基本认证信息";
        public static final String METHOD_API_NOTE = "查询用户基本认证信息";

        public static final String METHOD_API_USER_ID = "用户id";
    }
}
