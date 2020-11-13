package com.blockchain.server.otc.service;

public interface AppealImgService {

    /***
     * 新增申诉图片记录
     * @param appealDetailId
     * @param appealImgUrl
     * @return
     */
    int insertAppealImg(String appealDetailId, String appealImgUrl);
}
