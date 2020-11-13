package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.ad.ListAdDTO;
import com.blockchain.server.otc.dto.ad.ListUserAdDTO;
import com.blockchain.server.otc.dto.ad.PublishAdParamDTO;
import com.blockchain.server.otc.entity.Ad;

import java.util.List;

public interface AdService {
    /***
     * 发布买入广告
     * @param param
     */
    void publishBuyAd(PublishAdParamDTO param);

    /***
     * 发布卖出广告
     * @param param
     */
    void publishSellAd(PublishAdParamDTO param);

    /***
     * 撤销广告
     * @param userId
     * @param adId
     */
    void cancelAd(String userId, String adId);

    /***
     * 下架广告
     * @param userId
     * @param adId
     */
    void setAdToPending(String userId, String adId);

    /***
     * 上架广告
     * @param userId
     * @param adId
     */
    void setAdToDefault(String userId,String adId);

    /***
     * 查询交易大厅广告列表
     * @param userId
     * @param adType
     * @param coinName
     * @param unitName
     * @param adStatus
     * @param priceSort
     * @return
     */
    List<ListAdDTO> listAd(String userId, String adType, String coinName, String unitName,
                           String[] adStatus, String priceSort, String beginTime, String endTime);

    /***
     * 查询用户发布的广告列表
     * @param userId
     * @param adType
     * @param coinName
     * @param unitName
     * @param adStatus
     * @return
     */
    List<ListUserAdDTO> listUserAd(String userId, String adType, String coinName, String unitName, String adStatus, String beginTime, String endTime);

    /***
     * 使用排他锁查询广告
     * @param adId
     * @return
     */
    Ad selectByIdForUpdate(String adId);

    /***
     * 根据id查询广告
     * @param adId
     * @return
     */
    Ad selectById(String adId);

    /***
     * 更新广告
     * @param ad
     * @return
     */
    int updateByPrimaryKeySelective(Ad ad);
}
