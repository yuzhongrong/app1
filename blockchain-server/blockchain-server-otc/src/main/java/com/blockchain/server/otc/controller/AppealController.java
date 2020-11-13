package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.FileUploadHelper;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.otc.common.constant.AppealConstants;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.controller.api.AppealApi;
import com.blockchain.server.otc.dto.appeal.AppealHandleLogDTO;
import com.blockchain.server.otc.service.AppealHandleLogService;
import com.blockchain.server.otc.service.AppealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(AppealApi.APPEAL_API)
@RestController
@RequestMapping("/appeal")
public class AppealController extends FileUploadHelper {

    @Autowired
    private AppealService appealService;
    @Autowired
    private AppealHandleLogService appealHandleLogService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${FILES_DIR.ROOT}")
    private String FILE_ROOT_PATH;//文件上传根目录

    @ApiOperation(value = AppealApi.handleAppeal.METHOD_TITLE_NAME,
            notes = AppealApi.handleAppeal.METHOD_TITLE_NOTE)
    @PostMapping("/handleAppeal")
    public ResultDTO handleAppeal(@ApiParam(AppealApi.handleAppeal.METHOD_API_ORDER_ID) @RequestParam("orderId") String orderId,
                                  @ApiParam(AppealApi.handleAppeal.METHOD_API_URLS) @RequestParam("urls") String[] urls,
                                  @ApiParam(AppealApi.handleAppeal.METHOD_API_REMARK) @RequestParam("remark") String remark,
                                  HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        appealService.appeal(userId, orderId, urls, remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = AppealApi.uploadAppealFile.METHOD_TITLE_NAME,
            notes = AppealApi.uploadAppealFile.METHOD_TITLE_NOTE)
    @PostMapping("/uploadAppealFile")
    public ResultDTO uploadAppealFile(@ApiParam(AppealApi.uploadAppealFile.METHOD_API_APPEAL_FILE) @RequestParam("appealFile") MultipartFile file,
                                      HttpServletRequest request) {
        //认证用户是否有登录
        SSOHelper.getUser(redisTemplate, request);
        String filePath = saveFile(file);
        return ResultDTO.requstSuccess(filePath);
    }

    @ApiOperation(value = AppealApi.selectAppealHandleLog.METHOD_TITLE_NAME,
            notes = AppealApi.selectAppealHandleLog.METHOD_TITLE_NOTE)
    @GetMapping("/selectAppealHandleLog")
    public ResultDTO selectAppealHandleLog(@ApiParam(AppealApi.selectAppealHandleLog.METHOD_API_ORDER_NUMBER) @RequestParam("orderNumber") String orderNumber,
                                           HttpServletRequest request) {
        //认证用户是否有登录
        SSOHelper.getUser(redisTemplate, request);
        AppealHandleLogDTO appealHandleLogDTO = appealHandleLogService.selectByOrderNumber(orderNumber);
        return ResultDTO.requstSuccess(appealHandleLogDTO);
    }

    /***
     * 保存图片
     * @param file
     * @return
     */
    private String saveFile(MultipartFile file) {
        try {
            //保存图片，返回保存图片的路径
            return saveFile(file, FILE_ROOT_PATH, AppealConstants.APPEAL_URL);
        } catch (IOException e) {
            e.printStackTrace();
            throw new OtcException(OtcEnums.UPLOAD_ERROR);
        }
    }

}
