package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.constant.PushConstants;
import com.blockchain.common.base.enums.PushEnums;
import com.blockchain.server.otc.common.constant.AppealConstants;
import com.blockchain.server.otc.common.constant.CommonConstans;
import com.blockchain.server.otc.common.constant.OrderConstants;
import com.blockchain.server.otc.common.constant.UserHandleConstants;
import com.blockchain.server.otc.common.enums.JgMsgEnums;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.common.util.ImUtil;
import com.blockchain.server.otc.entity.Appeal;
import com.blockchain.server.otc.entity.Order;
import com.blockchain.server.otc.feign.PushFeign;
import com.blockchain.server.otc.mapper.AppealMapper;
import com.blockchain.server.otc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AppealServiceImpl implements AppealService {

    @Autowired
    private AppealMapper appealMapper;
    @Autowired
    private AppealDetailService appealDetailService;
    @Autowired
    private AppealImgService appealImgService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserHandleLogService userHandleLogService;
    @Autowired
    private ImUtil imUtil;
    @Autowired
    private PushFeign pushFeign;

    @Override
    @Transactional
    public void appeal(String userId, String orderId, String[] urls, String remark) {
        //查询订单
        Order order = orderService.selectByIdForUpdate(orderId);
        //如果订单状态不等于进行中和申诉中，不能申诉
        if (!order.getOrderStatus().equals(OrderConstants.UNDERWAY) &&
                !order.getOrderStatus().equals(OrderConstants.APPEAL)) {
            throw new OtcException(OtcEnums.ORDER_STATUS_CHANGES);
        }
        //操作用户是否是订单其中一方用户
        if (!order.getSellUserId().equals(userId) && !order.getBuyUserId().equals(userId)) {
            throw new OtcException(OtcEnums.ORDER_APPEAL_USERID_NOT_EQUALS);
        }
        //如果卖家状态等于确认收款，不能申诉
        if (order.getSellStatus().equals(OrderConstants.CONFIRM)) {
            throw new OtcException(OtcEnums.ORDER_SELL_STATUS_CHANGES);
        }
        //如果买家状态等于取消，不能申诉
        if (order.getBuyStatus().equals(OrderConstants.CANCEL)) {
            throw new OtcException(OtcEnums.ORDER_BUY_STATUS_CHANGES);
        }
        //判断申诉记录是否存在
        String appealId = checkAppealIsExist(order.getOrderNumber());
        //更新订单状态
        updateOrderStatus(order, userId);
        //新增申诉详情记录
        String appealDetailId = insertAppealDetail(userId, appealId, order, remark);
        //新增申诉图片记录
        insertAppealImg(appealDetailId, urls);
        //新增用户操作记录
        insertUserHandleLog(userId, order.getOrderNumber(), UserHandleConstants.APPEAL);
        //发送聊天信息
        sendAppealMsg(order.getSellUserId(), order.getBuyUserId(), orderId);
        //申诉后发送消息通知给被诉方
        checkRoleAndPush(userId, order.getSellUserId(), order.getBuyUserId(), orderId);
    }

    @Override
    @Transactional
    public int updateAppeal(String orderNumber, String appealStatus) {
        Appeal appeal = appealMapper.selectByOrderNumber(orderNumber);
        appeal.setStatus(appealStatus);
        appeal.setModifyTime(new Date());
        return appealMapper.updateByPrimaryKeySelective(appeal);
    }

    /***
     * 申诉后发送聊天提示信息（极光IM聊天API）
     * @param sellUserId
     * @param buyUserId
     * @param orderId
     */
    private void sendAppealMsg(String sellUserId, String buyUserId, String orderId) {
        imUtil.postMsgIMSelf(JgMsgEnums.APPEAL.getName(), sellUserId, buyUserId, orderId);
        imUtil.postMsgIMSelf(JgMsgEnums.APPEAL.getName(), buyUserId, sellUserId, orderId);
    }

    /***
     * 申诉后发送消息通知给被诉方
     * @param userId
     * @param sellUserId
     * @param buyUserId
     * @param orderId
     */
    private void checkRoleAndPush(String userId, String sellUserId, String buyUserId, String orderId) {
        //判断提交申诉的用户然后发送消息通知被诉用户
        if (userId.equals(sellUserId)) {
            pushToSingle(buyUserId, orderId, PushEnums.OTC_ORDER_APPEAL.getPushType());
        }
        if (userId.equals(buyUserId)) {
            pushToSingle(sellUserId, orderId, PushEnums.OTC_ORDER_APPEAL.getPushType());
        }
    }

    /***
     * 发送手机消息通知（个推消息推送API）
     * @param userId
     * @param orderId
     * @param pushType
     */
    private void pushToSingle(String userId, String orderId, String pushType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(PushConstants.ORDER_ID, orderId);
        payload.put(PushConstants.PUSH_TYPE, pushType);
        pushFeign.pushToSingle(userId, pushType, payload);
    }

    /***
     * 判断申诉记录是否存在
     * @param orderNumber
     * @return
     */
    private String checkAppealIsExist(String orderNumber) {
        //根据流水号查询申诉记录
        Appeal appeal = appealMapper.selectByOrderNumber(orderNumber);
        //如果记录为空，新建一个
        if (appeal == null) {
            Appeal newAppeal = new Appeal();
            Date now = new Date();
            String appealId = UUID.randomUUID().toString();
            newAppeal.setId(appealId);
            newAppeal.setCreateTime(now);
            newAppeal.setModifyTime(now);
            newAppeal.setOrderNumber(orderNumber);
            newAppeal.setStatus(AppealConstants.NEW);
            appealMapper.insertSelective(newAppeal);
            return appealId;
        } else {
            //订单id
            return appeal.getId();
        }
    }

    /***
     * 更新订单状态
     * @param order
     * @param userId
     */
    private void updateOrderStatus(Order order, String userId) {
        //判断是那一方进行申诉
        if (order.getBuyUserId().equals(userId)) {
            order.setBuyStatus(OrderConstants.APPEAL);
        }
        if (order.getSellUserId().equals(userId)) {
            order.setSellStatus(OrderConstants.APPEAL);
        }
        order.setOrderStatus(OrderConstants.APPEAL);
        order.setModifyTime(new Date());
        orderService.updateByPrimaryKeySelective(order);
    }

    /***
     * 新建申诉详情
     * @param userId
     * @param appealId
     * @param order
     * @param remark
     * @return
     */
    private String insertAppealDetail(String userId, String appealId, Order order, String remark) {
        String role;
        //买单
        if (order.getOrderType().equals(CommonConstans.BUY)) {
            //申诉用户和买家匹配
            if (order.getBuyUserId().equals(userId)) {
                //下单方申诉
                role = AppealConstants.ORDER;
            } else {
                //否则，发布方申诉
                role = AppealConstants.AD;
            }
        } else {
            //卖单
            //申诉用户和卖家匹配
            if (order.getSellUserId().equals(userId)) {
                //下单方申诉
                role = AppealConstants.ORDER;
            } else {
                //否则，发布方申诉
                role = AppealConstants.AD;
            }
        }

        //新增申诉详情记录
        return appealDetailService.insertAppealDetail(userId, appealId, role, remark);
    }

    /***
     * 新增申诉图片记录
     * @param appealDetailId
     * @param urls
     */
    private void insertAppealImg(String appealDetailId, String[] urls) {
        for (String url : urls) {
            appealImgService.insertAppealImg(appealDetailId, url);
        }
    }

    /***
     * 新增用户操作日志
     * @param userId
     * @param orderNumber
     * @param handleType
     * @return
     */
    private int insertUserHandleLog(String userId, String orderNumber, String handleType) {
        return userHandleLogService.insertUserHandleLog(userId, orderNumber, handleType, UserHandleConstants.ORDER);
    }
}
