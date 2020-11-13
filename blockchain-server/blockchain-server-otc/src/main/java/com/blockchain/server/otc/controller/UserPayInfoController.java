package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.FileUploadHelper;
import com.blockchain.common.base.util.SSOHelper;
import com.blockchain.server.otc.common.constant.UserPayConstants;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.controller.api.UserPayApi;
import com.blockchain.server.otc.entity.UserPayInfo;
import com.blockchain.server.otc.service.UserPayInfoService;
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
import java.util.List;

@Api(UserPayApi.USER_PAY_API)
@RestController
@RequestMapping("/userPay")
public class UserPayInfoController extends FileUploadHelper {

    @Autowired
    private UserPayInfoService userPayInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${FILES_DIR.ROOT}")
    private String FILE_ROOT_PATH; //文件上传路径

    @ApiOperation(value = UserPayApi.listUserPay.METHOD_TITLE_NAME,
            notes = UserPayApi.listUserPay.METHOD_TITLE_NOTE)
    @GetMapping("/listUserPay")
    public ResultDTO listUserPay(
            HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        List<UserPayInfo> userPayInfos = userPayInfoService.listUserPay(userId);
        return ResultDTO.requstSuccess(userPayInfos);
    }

    @ApiOperation(value = UserPayApi.selectByAdPayType.METHOD_TITLE_NAME,
            notes = UserPayApi.selectByAdPayType.METHOD_TITLE_NOTE)
    @GetMapping("/selectByAdPayType")
    public ResultDTO selectByAdPayType(@ApiParam(UserPayApi.selectByAdPayType.METHOD_API_ORDER_ID) @RequestParam("orderId") String orderId,
                                       HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        List<UserPayInfo> userPayInfos = userPayInfoService.selectSellUserPayInfoByAdPayType(userId, orderId);
        return ResultDTO.requstSuccess(userPayInfos);
    }

    @ApiOperation(value = UserPayApi.selectByPayType.METHOD_TITLE_NAME,
            notes = UserPayApi.selectByPayType.METHOD_TITLE_NOTE)
    @GetMapping("/selectByPayType")
    public ResultDTO selectByPayType(@ApiParam(UserPayApi.selectByPayType.METHOD_API_PAP_TYPE) @RequestParam("payType") String payType,
                                     HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        UserPayInfo userPayInfo = userPayInfoService.selectByUserIdAndPayType(userId, payType);
        return ResultDTO.requstSuccess(userPayInfo);
    }

    @ApiOperation(value = UserPayApi.insertWX.METHOD_TITLE_NAME,
            notes = UserPayApi.insertWX.METHOD_TITLE_NOTE)
    @PostMapping("/insertWX")
    public ResultDTO insertWX(@ApiParam(UserPayApi.insertWX.METHOD_API_ACCOUNT_INFO) @RequestParam("accountInfo") String accountInfo,
                              @ApiParam(UserPayApi.insertWX.METHOD_API_CODE_URL) @RequestParam("codeUrl") String codeUrl,
                              @ApiParam(UserPayApi.insertWX.METHOD_API_PASS) @RequestParam("pass") String pass,
                              HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userPayInfoService.insertWXorZFB(userId, UserPayConstants.WX, accountInfo, codeUrl, pass);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserPayApi.insertZFB.METHOD_TITLE_NAME,
            notes = UserPayApi.insertZFB.METHOD_TITLE_NOTE)
    @PostMapping("/insertZFB")
    public ResultDTO insertZFB(@ApiParam(UserPayApi.insertZFB.METHOD_API_ACCOUNT_INFO) @RequestParam("accountInfo") String accountInfo,
                               @ApiParam(UserPayApi.insertZFB.METHOD_API_CODE_URL) @RequestParam("codeUrl") String codeUrl,
                               @ApiParam(UserPayApi.insertZFB.METHOD_API_PASS) @RequestParam("pass") String pass,
                               HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userPayInfoService.insertWXorZFB(userId, UserPayConstants.ZFB, accountInfo, codeUrl, pass);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserPayApi.insertBank.METHOD_TITLE_NAME,
            notes = UserPayApi.insertBank.METHOD_TITLE_NOTE)
    @PostMapping("/insertBank")
    public ResultDTO insertBank(@ApiParam(UserPayApi.insertBank.METHOD_API_BANK_NUMBER) @RequestParam("bankNumber") String bankNumber,
                                @ApiParam(UserPayApi.insertBank.METHOD_API_BANK_USER_NAME) @RequestParam("bankUserName") String bankUserName,
                                @ApiParam(UserPayApi.insertBank.METHOD_API_BANK_TYPE) @RequestParam("bankType") String bankType,
                                @ApiParam(UserPayApi.insertBank.METHOD_API_PASS) @RequestParam("pass") String pass,
                                HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userPayInfoService.insertBank(userId, bankNumber, bankUserName, bankType, pass);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserPayApi.updateWX.METHOD_TITLE_NAME,
            notes = UserPayApi.updateWX.METHOD_TITLE_NOTE)
    @PostMapping("/updateWX")
    public ResultDTO updateWX(@ApiParam(UserPayApi.updateWX.METHOD_API_ACCOUNT_INFO) @RequestParam("accountInfo") String accountInfo,
                              @ApiParam(UserPayApi.updateWX.METHOD_API_CODE_URL) @RequestParam("codeUrl") String codeUrl,
                              @ApiParam(UserPayApi.updateWX.METHOD_API_PASS) @RequestParam("pass") String pass,
                              HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userPayInfoService.updateWXorZFB(userId, UserPayConstants.WX, accountInfo, codeUrl, pass);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserPayApi.updateZFB.METHOD_TITLE_NAME,
            notes = UserPayApi.updateZFB.METHOD_TITLE_NOTE)
    @PostMapping("/updateZFB")
    public ResultDTO updateZFB(@ApiParam(UserPayApi.updateZFB.METHOD_API_ACCOUNT_INFO) @RequestParam("accountInfo") String accountInfo,
                               @ApiParam(UserPayApi.updateZFB.METHOD_API_CODE_URL) @RequestParam("codeUrl") String codeUrl,
                               @ApiParam(UserPayApi.updateZFB.METHOD_API_PASS) @RequestParam("pass") String pass,
                               HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userPayInfoService.updateWXorZFB(userId, UserPayConstants.ZFB, accountInfo, codeUrl, pass);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserPayApi.updateBank.METHOD_TITLE_NAME,
            notes = UserPayApi.updateBank.METHOD_TITLE_NOTE)
    @PostMapping("/updateBank")
    public ResultDTO updateBank(@ApiParam(UserPayApi.updateBank.METHOD_API_BANK_NUMBER) @RequestParam("bankNumber") String bankNumber,
                                @ApiParam(UserPayApi.updateBank.METHOD_API_BANK_USER_NAME) @RequestParam("bankUserName") String bankUserName,
                                @ApiParam(UserPayApi.updateBank.METHOD_API_BANK_TYPE) @RequestParam("bankType") String bankType,
                                @ApiParam(UserPayApi.updateBank.METHOD_API_PASS) @RequestParam("pass") String pass,
                                HttpServletRequest request) {
        String userId = SSOHelper.getUserId(redisTemplate, request);
        userPayInfoService.updateBank(userId, bankNumber, bankUserName, bankType, pass);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = UserPayApi.uploadWX.METHOD_TITLE_NAME,
            notes = UserPayApi.uploadWX.METHOD_TITLE_NOTE)
    @PostMapping("/uploadWX")
    public ResultDTO uploadWX(@ApiParam(UserPayApi.uploadWX.METHOD_API_PAY_FILE) @RequestParam("payFile") MultipartFile file,
                              HttpServletRequest request) {
        //认证用户是否有登录
        SSOHelper.getUserId(redisTemplate, request);
        //保存图片，返回保存图片的路径
        String filePath = saveFile(file, UserPayConstants.WX_URL);
        return ResultDTO.requstSuccess(filePath);
    }

    @ApiOperation(value = UserPayApi.uploadZFB.METHOD_TITLE_NAME,
            notes = UserPayApi.uploadZFB.METHOD_TITLE_NOTE)
    @PostMapping("/uploadZFB")
    public ResultDTO uploadZFB(@ApiParam(UserPayApi.uploadZFB.METHOD_API_PAY_FILE) @RequestParam("payFile") MultipartFile file,
                               HttpServletRequest request) {
        //认证用户是否有登录
        SSOHelper.getUserId(redisTemplate, request);
        //保存图片，返回保存图片的路径
        String filePath = saveFile(file, UserPayConstants.ZFB_URL);
        return ResultDTO.requstSuccess(filePath);
    }

    /**
     * 保存文件
     *
     * @param file        文件
     * @param relativeDir 文件相对目录
     * @return 文件相对路径
     * @throws IOException
     */
    private String saveFile(MultipartFile file, String relativeDir) {
        try {
            return saveFile(file, FILE_ROOT_PATH, relativeDir);
        } catch (IOException e) {
            e.printStackTrace();
            throw new OtcException(OtcEnums.UPLOAD_ERROR);
        }
    }
}
