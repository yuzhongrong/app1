package com.blockchain.server.user.mapper;

import com.blockchain.server.user.dto.UserReplyDTO;
import com.blockchain.server.user.entity.UserReply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserReplyMapper extends Mapper<UserReply> {

    /**
     * 获取我的回复信息
     *
     * @param userOpenId
     * @return
     */
    List<UserReplyDTO> listMine(@Param("userOpenId") String userOpenId);

}
