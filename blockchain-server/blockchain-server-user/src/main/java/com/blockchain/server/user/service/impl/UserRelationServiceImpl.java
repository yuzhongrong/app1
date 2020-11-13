package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.constant.UserWalletInfoConstant;
import com.blockchain.common.base.dto.UserRelationDTO;
import com.blockchain.common.base.dto.UserTeamDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exceprion.UserException;
import com.blockchain.server.user.common.utils.CommonUtils;
import com.blockchain.server.user.entity.UserInfo;
import com.blockchain.server.user.entity.UserRelation;
import com.blockchain.server.user.mapper.UserRelationMapper;
import com.blockchain.server.user.service.UserInfoService;
import com.blockchain.server.user.service.UserRelationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserRelationServiceImpl implements UserRelationService {

    @Autowired
    private UserRelationMapper userRelationMapper;
    @Autowired
    private UserInfoService userInfoService;


    private Logger LOG = LoggerFactory.getLogger(UserRelationServiceImpl.class);

    @Override
    @Transactional
    public int insertRelationChain(String userId, String invitationCode) {
        ExceptionPreconditionUtils.notEmpty(userId);
        UserRelation userRelation = new UserRelation();
        //如果邀请码不为空，插入用户关系链
        if (StringUtils.isEmpty(invitationCode)) {
            userRelation.setPid(null);
            userRelation.setRelationChain(userId);
            userRelation.setTreeDepth(0);
        } else {
            Integer incrCode = CommonUtils.getUserIncrCodeFromInvitationCode(invitationCode);//解析出用户自增长码
            if (incrCode == null) {
                throw new UserException(UserEnums.INVALID_INVITATION_CODE);
            }
            UserInfo userInfo = userInfoService.selectByIncrCode(incrCode);
            if (userInfo == null) {
                throw new UserException(UserEnums.INVALID_INVITATION_CODE);
            }
            //拿到父级关系链
            UserRelation parent = findByUserId(userInfo.getUserId());
            userRelation.setPid(parent.getUserId());
            userRelation.setRelationChain(parent.getRelationChain() + "," + userId);
            userRelation.setTreeDepth(parent.getTreeDepth() + 1);

        }

        String id = UUID.randomUUID().toString();
        userRelation.setId(id);
        userRelation.setUserId(userId);
        userRelation.setCreateTime(new Date());
        return userRelationMapper.insertSelective(userRelation);
    }

    @Override
    public int insertRelationChainByNotInvited(String userId) {
        UserRelation userRelation = new UserRelation();
        String id = UUID.randomUUID().toString();
        userRelation.setId(id);
        userRelation.setUserId(userId);
        userRelation.setRelationChain(userId);
        userRelation.setCreateTime(new Date());
        return userRelationMapper.insert(userRelation);
    }

    @Override
    public UserRelation findByUserId(String userId) {
        UserRelation where = new UserRelation();
        where.setUserId(userId);
        return userRelationMapper.selectOne(where);
    }

    @Override
    public Set<String> getDirects(String userId) {
        return userRelationMapper.getDirects(userId);
    }

    @Override
    public Set<String> getAllSubordinate(String userId) {
        return userRelationMapper.getAllSubordinate(userId);
    }

    @Override
    public List<UserTeamDTO> listUserDirectTeam(String userId) {
        return userRelationMapper.listUserDirectTeam(userId);
    }

    @Override
    public void getAllUserRelation() {
        LOG.info("开始查询用户关系");
        Long firDate = System.currentTimeMillis();
        List<UserRelationDTO> userRelationList = userRelationMapper.getAllUserRelation();
        //回调

        if (userRelationList == null || userRelationList.size() == 0)
            throw new UserException(UserEnums.REQUEST_TOO_BUSY);
        Long endSelectDate = System.currentTimeMillis();
        LOG.info("开始查询用户关系结束，耗时:" + (firDate - endSelectDate));
        //异步回调
        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                //回调
                int retryCount = 0;
                while (retryCount < 3) {
                    try {
                        LOG.info("用户关系回调，大小：" + userRelationList.size());
                        Long endDate = System.currentTimeMillis();
                        LOG.info("回调用户关系回调结束，耗时:" + (endSelectDate - endDate));
                        break;
                    } catch (Exception e) {
                        LOG.info("回调失败，回调次数:" + retryCount);
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(30000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    retryCount++;
                }
            }
        });
        th.start();

    }

}
