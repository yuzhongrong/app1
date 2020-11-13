package com.blockchain.server.tron.mapper;

import com.blockchain.server.tron.entity.TronWalletKeyInit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey Luo
 * @date 2019/6/13 14:14
 */
@Repository
public interface TronWalletKeyInitMapper extends Mapper<TronWalletKeyInit> {

    /**
     * 查询一条地址对象
     * @return
     */
    TronWalletKeyInit selectByLimitOne();

    /**
     * 删除地址
     * @param addr
     * @return
     */
    Integer deleteByAddr(@Param("addr") String addr);

    /**
     * 统计数量
     * @return
     */
    Long selectWalletCount();

    /**
     * 按时间倒叙查询
     * @param l
     * @return
     */
    List<TronWalletKeyInit> selectCreateTimeAsc(@Param("limit") Long limit);
}
