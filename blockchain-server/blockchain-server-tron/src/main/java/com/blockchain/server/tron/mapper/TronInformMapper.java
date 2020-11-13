package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronInform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * InformMapper 数据访问类
 *
 * @version 1.0
 * @date 2018-11-05 15:10:47
 */
@Repository
public interface TronInformMapper extends Mapper<TronInform> {
    /**
     * 根据{type}查出小于{time}的记录
     *
     * @param type type
     * @param time 次数
     * @return
     */
    List<TronInform> selectByTypeAndTime(@Param("type") String type, @Param("time") int time);

    /**
     * 查询次数加一
     *
     * @param id
     * @return
     */
    int updateTimeInRowLock(@Param("id") String id);
}