package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.UserReplyDTO;

import java.util.List;

public interface UserReplyService {

    /**
     * 获取我的回复信息
     *
     * @param userOpenId
     * @return
     */
    List<UserReplyDTO> listMine(String userOpenId);

}
