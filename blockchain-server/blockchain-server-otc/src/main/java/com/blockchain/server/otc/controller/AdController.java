package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.common.constant.AdConstants;
import com.blockchain.server.otc.common.constant.CommonConstans;
import com.blockchain.server.otc.controller.api.AdApi;
import com.blockchain.server.otc.dto.ad.ListAdDTO;
import com.blockchain.server.otc.dto.ad.ListUserAdDTO;
import com.blockchain.server.otc.dto.ad.PublishAdParamDTO;
import com.blockchain.server.otc.entity.Ad;
import com.blockchain.server.otc.service.AdService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(AdApi.AD_API)
@RestController
@RequestMapping("/ad")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = AdApi.publishBuyAd.METHOD_TITLE_NAME,
            notes = AdApi.publishBuyAd.METHOD_TITLE_NOTE)
    @PostMapping("/publishBuyAd")
    public ResultDTO publishBuyAd(@ApiParam(AdApi.publishBuyAd.METHOD_API_PARAM) PublishAdParamDTO param,
                                  HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        param.setUserId(userId);
        adService.publishBuyAd(param);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AdApi.publishSellAd.METHOD_TITLE_NAME,
            notes = AdApi.publishSellAd.METHOD_TITLE_NOTE)
    @PostMapping("/publishSellAd")
    public ResultDTO publishSellAd(@ApiParam(AdApi.publishSellAd.METHOD_API_PARAM) PublishAdParamDTO param,
                                   HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        param.setUserId(userId);
        adService.publishSellAd(param);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AdApi.cancelAd.METHOD_TITLE_NAME,
            notes = AdApi.cancelAd.METHOD_TITLE_NOTE)
    @PostMapping("/cancelAd")
    public ResultDTO cancelAd(@ApiParam(AdApi.cancelAd.METHOD_API_ADID) @RequestParam("adId") String adId,
                              HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        adService.cancelAd(userId, adId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AdApi.defaultAd.METHOD_TITLE_NAME,
            notes = AdApi.defaultAd.METHOD_TITLE_NOTE)
    @PostMapping("/defaultAd")
    public ResultDTO defaultAd(@ApiParam(AdApi.defaultAd.METHOD_API_ADID) @RequestParam("adId") String adId,
                               HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        adService.setAdToDefault(userId, adId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AdApi.pendingAd.METHOD_TITLE_NAME,
            notes = AdApi.pendingAd.METHOD_TITLE_NOTE)
    @PostMapping("/pendingAd")
    public ResultDTO pendingAd(@ApiParam(AdApi.pendingAd.METHOD_API_ADID) @RequestParam("adId") String adId,
                               HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        adService.setAdToPending(userId, adId);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AdApi.listBuyAd.METHOD_TITLE_NAME,
            notes = AdApi.listBuyAd.METHOD_TITLE_NOTE)
    @GetMapping("/listBuyAd")
    public ResultDTO listBuyAd(@ApiParam(AdApi.METHOD_API_COIN_NAME) @RequestParam("coinName") String coinName,
                               @ApiParam(AdApi.METHOD_API_UNIT_NAME) @RequestParam("unitName") String unitName,
                               @ApiParam(AdApi.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                               @ApiParam(AdApi.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                               @ApiParam(AdApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @ApiParam(AdApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                               HttpServletRequest request) {
        String userId = SSOHelper.getUserIdIsExits(redisTemplate, request);
        List<ListAdDTO> buyAdList = listAd(userId, CommonConstans.BUY, coinName, unitName, CommonConstans.DESC, beginTime, endTime, pageNum, pageSize);
        return ResultDTO.requstSuccess(buyAdList);
    }

    @ApiOperation(value = AdApi.listSellAd.METHOD_TITLE_NAME,
            notes = AdApi.listSellAd.METHOD_TITLE_NOTE)
    @GetMapping("/listSellAd")
    public ResultDTO listSellAd(@ApiParam(AdApi.METHOD_API_COIN_NAME) @RequestParam("coinName") String coinName,
                                @ApiParam(AdApi.METHOD_API_UNIT_NAME) @RequestParam("unitName") String unitName,
                                @ApiParam(AdApi.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                @ApiParam(AdApi.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                @ApiParam(AdApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @ApiParam(AdApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                HttpServletRequest request) {
        String userId = SSOHelper.getUserIdIsExits(redisTemplate, request);
        List<ListAdDTO> sellAdList = listAd(userId, CommonConstans.SELL, coinName, unitName, CommonConstans.ASC, beginTime, endTime, pageNum, pageSize);
        return ResultDTO.requstSuccess(sellAdList);
    }

    @ApiOperation(value = AdApi.listUserAd.METHOD_TITLE_NAME,
            notes = AdApi.listUserAd.METHOD_TITLE_NOTE)
    @GetMapping("/listUserAd")
    public ResultDTO listUserAd(@ApiParam(AdApi.METHOD_API_COIN_NAME) @RequestParam(value = "coinName", required = false) String coinName,
                                @ApiParam(AdApi.METHOD_API_UNIT_NAME) @RequestParam(value = "unitName", required = false) String unitName,
                                @ApiParam(AdApi.listUserAd.METHOD_API_AD_TYPE) @RequestParam(value = "adType", required = false) String adType,
                                @ApiParam(AdApi.listUserAd.METHOD_API_AD_STATUS) @RequestParam(value = "adStatus", required = false) String adStatus,
                                @ApiParam(AdApi.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                @ApiParam(AdApi.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                @ApiParam(AdApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @ApiParam(AdApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<ListUserAdDTO> userAdList = adService.listUserAd(userId, adType, coinName, unitName,
                adStatus, beginTime, endTime);
        return ResultDTO.requstSuccess(userAdList);
    }

    @ApiOperation(value = AdApi.selectById.METHOD_TITLE_NAME,
            notes = AdApi.selectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(AdApi.selectById.METHOD_API_AD_ID) @RequestParam("adId") String adId,
                                HttpServletRequest request) {
        //验证下是否已登录
        SSOHelper.getUser(redisTemplate, request);
        Ad ad = adService.selectById(adId);
        return ResultDTO.requstSuccess(ad);
    }

    /****************************pc分页接口****************************/

    @ApiOperation(value = AdApi.pcListBuyAd.METHOD_TITLE_NAME,
            notes = AdApi.pcListBuyAd.METHOD_TITLE_NOTE)
    @GetMapping("/pcListBuyAd")
    public ResultDTO pcListBuyAd(@ApiParam(AdApi.METHOD_API_COIN_NAME) @RequestParam("coinName") String coinName,
                                 @ApiParam(AdApi.METHOD_API_UNIT_NAME) @RequestParam("unitName") String unitName,
                                 @ApiParam(AdApi.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                 @ApiParam(AdApi.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                 @ApiParam(AdApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                 @ApiParam(AdApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                 HttpServletRequest request) {
        String userId = SSOHelper.getUserIdIsExits(redisTemplate, request);
        List<ListAdDTO> buyAdList = listAd(userId, CommonConstans.BUY, coinName, unitName, CommonConstans.DESC, beginTime, endTime, pageNum, pageSize);
        return generatePage(buyAdList);
    }

    @ApiOperation(value = AdApi.pcListSellAd.METHOD_TITLE_NAME,
            notes = AdApi.pcListSellAd.METHOD_TITLE_NOTE)
    @GetMapping("/pcListSellAd")
    public ResultDTO pcListSellAd(@ApiParam(AdApi.METHOD_API_COIN_NAME) @RequestParam("coinName") String coinName,
                                  @ApiParam(AdApi.METHOD_API_UNIT_NAME) @RequestParam("unitName") String unitName,
                                  @ApiParam(AdApi.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                  @ApiParam(AdApi.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                  @ApiParam(AdApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @ApiParam(AdApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  HttpServletRequest request) {
        String userId = SSOHelper.getUserIdIsExits(redisTemplate, request);
        List<ListAdDTO> sellAdList = listAd(userId, CommonConstans.SELL, coinName, unitName, CommonConstans.ASC, beginTime, endTime, pageNum, pageSize);
        return generatePage(sellAdList);
    }

    @ApiOperation(value = AdApi.pcListUserAd.METHOD_TITLE_NAME,
            notes = AdApi.pcListUserAd.METHOD_TITLE_NOTE)
    @GetMapping("/pcListUserAd")
    public ResultDTO pcListUserAd(@ApiParam(AdApi.METHOD_API_COIN_NAME) @RequestParam("coinName") String coinName,
                                  @ApiParam(AdApi.METHOD_API_UNIT_NAME) @RequestParam("unitName") String unitName,
                                  @ApiParam(AdApi.pcListUserAd.METHOD_API_AD_TYPE) @RequestParam(value = "adType", required = false) String adType,
                                  @ApiParam(AdApi.pcListUserAd.METHOD_API_AD_STATUS) @RequestParam(value = "adStatus", required = false) String adStatus,
                                  @ApiParam(AdApi.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                                  @ApiParam(AdApi.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                  @ApiParam(AdApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @ApiParam(AdApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        PageHelper.startPage(pageNum, pageSize);
        List<ListUserAdDTO> userAdList = adService.listUserAd(userId, adType, coinName, unitName, adStatus, beginTime, endTime);
        return generatePage(userAdList);
    }

    /***
     * 查询广告列表
     * @param userId
     * @param adType
     * @param coinName
     * @param unitName
     * @param priceSort
     * @param beginTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    private List<ListAdDTO> listAd(String userId, String adType, String coinName, String unitName, String priceSort,
                                   String beginTime, String endTime, Integer pageNum, Integer pageSize) {
        //查询的广告状态
        String[] adStatus = {AdConstants.DEFAULT, AdConstants.UNDERWAY};
        //分页
        PageHelper.startPage(pageNum, pageSize);
        //查询广告列表
        List<ListAdDTO> adList = adService.listAd(userId, adType, coinName,
                unitName, adStatus, priceSort, beginTime, endTime);
        return adList;
    }

}
