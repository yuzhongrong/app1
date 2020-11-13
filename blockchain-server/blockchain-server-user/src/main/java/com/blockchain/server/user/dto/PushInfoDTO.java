package com.blockchain.server.user.dto;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.enums.PushEnums;
import lombok.Data;

@Data
public class PushInfoDTO {

    private String pushType;
    private String pushTitle;
    private String pushContent;

    public PushInfoDTO(PushEnums pushEnums, String language) {
        String title = "";
        String content = "";
        switch (language) {
            case BaseConstant.USER_LOCALE_EN_US:
                title = pushEnums.getEnTitle();
                content = pushEnums.getEnContent();
                break;
            default:
                title = pushEnums.getCnTitle();
                content = pushEnums.getCnContent();
                break;
        }
        this.pushType = pushEnums.getPushType();
        this.pushTitle = title;
        this.pushContent = content;
    }
}
