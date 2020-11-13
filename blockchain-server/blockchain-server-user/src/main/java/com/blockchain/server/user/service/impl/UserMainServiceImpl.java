package com.blockchain.server.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constants.other.InternationalConstant;
import com.blockchain.server.user.common.constants.other.StringFormatConstant;
import com.blockchain.server.user.common.constants.sql.UserMainConstant;
import com.blockchain.server.user.common.constants.sql.UserOptConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.CommonUtils;
import com.blockchain.server.user.common.utils.RestTemplateUtils;
import com.blockchain.server.user.dto.UserBaseDTO;
import com.blockchain.server.user.entity.UserInfo;
import com.blockchain.server.user.entity.UserMain;
import com.blockchain.server.user.feign.*;
import com.blockchain.server.user.mapper.UserMainMapper;
import com.blockchain.server.user.service.*;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author huangxl
 * @create 2019-02-23 14:59
 */
@Service
public class UserMainServiceImpl implements UserMainService {
    private static final Logger LOG = LoggerFactory.getLogger(UserMainServiceImpl.class);
    @Autowired
    private UserMainMapper userMainMapper;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private UserOptLogService userOptLogService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RestTemplateUtils restTemplateUtils;
    @Autowired
    private EthFeign ethFeign;
    @Autowired
    private EosFeign eosFeign;
    @Autowired
    private TrxFeign trxFeign;
    @Autowired
    private BtcFeign btcFeign;
    @Autowired
    private LtcFeign ltcFeign;

    @Autowired
    private TEthFeign tethFeign;




    @Override
    public UserMain selectByMobilePhone(String tel) {
        ExceptionPreconditionUtils.notEmpty(tel);
        return userMainMapper.selectByMobilePhone(tel);
    }

    @Override
    public UserMain selectById(String id) {
//        ExceptionPreconditionUtils.notEmpty(id);
        UserMain userMain = userMainMapper.selectByPrimaryKey(id);
        return userMain;
    }

    @Override
    public List<UserMain> selectInIds(String[] ids) {
        List<UserMain> users = userMainMapper.listByIds(ids);
        return users;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public UserMain handleRegister(String tel, String invitationCode, String internationalCode, String password, String nickName) {
        ExceptionPreconditionUtils.notEmpty(tel, internationalCode);//校验信息
        UserMain userMain = selectByMobilePhone(tel);
        if (userMain != null) {
            throw new UserException(UserEnums.USER_PHONE_EXISTS);//已存在该手机号信息
        }
        String international = InternationalConstant.getInternational(internationalCode);
        if (international == null) {
            throw new UserException(UserEnums.CANNOT_FOUND_INTERNATIONAL);//通过国际编码找不到国际名称
        }
        LOG.info("tel:"+tel+"校验成功");
        String userId = UUID.randomUUID().toString();
        UserMain user = insertUserMain(tel, internationalCode, userId, international, nickName);//插入主体数据
        userLoginService.insertEntity(userId, password);//插入密码信息
        int insertRow = userRelationService.insertRelationChain(userId, invitationCode);//插入推荐关系
        userInfoService.saveUser(userId, tel, insertRow > 0);//插入用户其他信息
        LOG.info("开始初始化钱包");
        ResultDTO ethResult = ethFeign.initWallets(userId);
        if (ethResult.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(ethResult.getCode(), ethResult.getMsg());
        }
        LOG.info("初始化eth钱包成功");
        ResultDTO eosResult = eosFeign.initEosWallet(userId);
        if (eosResult.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(eosResult.getCode(), eosResult.getMsg());
        }
        LOG.info("初始化eos钱包成功");
        try {
            ResultDTO trxResult = trxFeign.initTronWallet(userId);
            if (trxResult.getCode() != BaseConstant.REQUEST_SUCCESS) {
                throw new RPCException(trxResult.getCode(), trxResult.getMsg());
            }
            LOG.info("初始化trx钱包成功");
        }catch (Exception e){
            LOG.info("初始化trx钱包失败");
            e.printStackTrace();
        }
        //目前btc的rpc存在问题 暂时屏蔽
//        ResultDTO btcResult = btcFeign.createWallet(userId);
//        if (btcResult.getCode() != BaseConstant.REQUEST_SUCCESS) {
//            throw new RPCException(btcResult.getCode(), btcResult.getMsg());
//        }
//        LOG.info("初始化btc钱包成功");
//        ResultDTO ltcResult = ltcFeign.createWallet(userId);
//        if (ltcResult.getCode() != BaseConstant.REQUEST_SUCCESS) {
//            throw new RPCException(ltcResult.getCode(), ltcResult.getMsg());
//        }
//        LOG.info("初始化ltc钱包成功");

        ResultDTO tethResult = tethFeign.initWallets(userId);
        if (tethResult.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(tethResult.getCode(), tethResult.getMsg());
        }
        LOG.info("初始化teth钱包成功");

        return user;
    }



    @Override
    @Transactional
    public void updateNickName(String userId, String nickName) {
        ExceptionPreconditionUtils.notEmpty(userId, nickName);
        UserMain userMain = userMainMapper.selectByPrimaryKey(userId);
        if (nickName.contains("<") || nickName.contains(">")) {
            throw new UserException(UserEnums.INVALID_NICK_NAME);
        }
        userMain.setNickName(nickName);
        userMain.setModifyTime(new Date());
        userMainMapper.updateByPrimaryKey(userMain);
    }

    @Override
    @Transactional
    public void updateTel(String userId, String tel, String internationalCode) {
        ExceptionPreconditionUtils.notEmpty(userId, tel, internationalCode);
        //查询是否已存在该手机号
        UserMain existUser = selectByMobilePhone(tel);
        if (existUser != null) {
            throw new UserException(UserEnums.USER_PHONE_EXISTS);//已存在该手机号信息
        }
        UserMain userMain = userMainMapper.selectByPrimaryKey(userId);
        Date now = new Date();
        //插入修改记录
        String content = StringFormatConstant.getUserChangeAccountFormat(userId,
                userMain.getMobilePhone(), tel,
                userMain.getInternationalCode(), internationalCode);
        userOptLogService.saveUserOptLog(userId, UserOptConstant.TYPE_CHANGE_TEL, content);

        //更改手机号
        userMain.setInternationalCode(internationalCode);

        if (userMain.getMobilePhone().equals(userMain.getNickName())) {//如果手机号和昵称一致，同时修改昵称
            userMain.setNickName(tel);
        }
        userMain.setMobilePhone(tel);
        userMain.setModifyTime(now);
        userMainMapper.updateByPrimaryKey(userMain);
    }

    @Override
    public void handleSendChangePhoneCodeBefore(String userId, String tel) {
        ExceptionPreconditionUtils.notEmpty(userId, tel);
        UserMain me = selectById(userId);
        if (me.getMobilePhone().equals(tel.trim())) {
            throw new UserException(UserEnums.PHONE_NOT_CHANGE);
        }
        UserMain userMain = selectByMobilePhone(tel);
        if (userMain != null) {
            throw new UserException(UserEnums.USER_PHONE_EXISTS);
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public UserBaseDTO selectUserInfoById(String userId) {
        UserMain userMain = userMainMapper.selectByPrimaryKey(userId);
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        boolean hasPassword = userLoginService.selectUserPasswordIsExist(userId);
        LOG.info("userId is:"+userId+" userMain:"+userMain+" userInfo:"+userId);
        UserBaseDTO userBaseDTO = new UserBaseDTO();
//        BeanUtils.copyProperties(userMain, userBaseDTO);
//        BeanUtils.copyProperties(userInfo, userBaseDTO);
        BeanUtils.copyProperties(userInfo, userBaseDTO);
        BeanUtils.copyProperties(userMain, userBaseDTO);
        userBaseDTO.setHasPassword(hasPassword);
        userBaseDTO.setBindGoogleAuth(userInfo.getGoogleAuth() != null);
        userBaseDTO.setInvitationCode(CommonUtils.generateInvitationCode(userInfo.getIncrCode(), userInfo.getRandomNumber()));
        return userBaseDTO;
    }

    private UserMain insertUserMain(String tel, String internationalCode, String userId, String international, String nickName) {
        UserMain user = new UserMain();
        user.setId(userId);
        if (StringUtils.isEmpty(internationalCode)) {
            internationalCode = UserMainConstant.INTERNATIONAL_CODE_DEFAULT;
        }
        if (StringUtils.isEmpty(nickName)) nickName = tel;
        user.setInternationalCode(internationalCode);
        user.setInternational(international);
        user.setMobilePhone(tel);
        user.setNickName(nickName);
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        userMainMapper.insert(user);
        return user;
    }
}
