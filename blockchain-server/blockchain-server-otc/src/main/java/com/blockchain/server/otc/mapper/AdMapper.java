package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.ad.ListAdDTO;
import com.blockchain.server.otc.dto.ad.ListUserAdDTO;
import com.blockchain.server.otc.entity.Ad;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AdMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-15 10:37:31
 */
@Repository
public interface AdMapper extends Mapper<Ad> {

    /***
     * 根据参数查询广告（用于交易大厅）
     * @param userId
     * @param adType
     * @param coinName
     * @param unitName
     * @param adStatus
     * @param priceSort 单价排序规则
     * @return
     */
    List<ListAdDTO> listAd(@Param("userId") String userId, @Param("adType") String adType,
                           @Param("coinName") String coinName, @Param("unitName") String unitName,
                           @Param("adStatus") String[] adStatus, @Param("priceSort") String priceSort,
                           @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /***
     * 根据参数查询用户发布的广告（用于用户广告列表）
     * @param userId
     * @param adType
     * @param coinName
     * @param unitName
     * @param adStatus
     * @return
     */
    List<ListUserAdDTO> listUserAd(@Param("userId") String userId, @Param("adType") String adType,
                                   @Param("coinName") String coinName, @Param("unitName") String unitName,
                                   @Param("adStatus") String adStatus, @Param("beginTime") String beginTime,
                                   @Param("endTime") String endTime);

    /***
     * 根据多个状态和类型查询广告
     * @param userId
     * @param adType
     * @param coinName
     * @param unitName
     * @param adStatus
     * @return
     */
    List<Ad> listUserAdByStatusAndType(@Param("userId") String userId, @Param("adType") String adType,
                                       @Param("coinName") String coinName, @Param("unitName") String unitName,
                                       @Param("adStatus") String[] adStatus);

    /***
     * 使用排他锁查询广告
     * @param adId
     * @return
     */
    Ad selectByIdForUpdate(@Param("adId") String adId);
}