/*
 Navicat Premium Data Transfer

 Source Server         : fkex-高防内外
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 47.75.92.218:3306
 Source Schema         : fkex

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 17/06/2020 19:50:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for otc_ad
-- ----------------------------
DROP TABLE IF EXISTS `otc_ad`;
CREATE TABLE `otc_ad`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告id',
  `ad_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告流水号',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `price` decimal(20, 8) NOT NULL COMMENT '广告单价',
  `total_num` decimal(20, 8) NOT NULL COMMENT '发布数量',
  `last_num` decimal(20, 8) NOT NULL COMMENT '剩余数量',
  `coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币-对应数量',
  `unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币-对应单价',
  `min_limit` decimal(20, 8) NOT NULL COMMENT '最小单价限额',
  `max_limit` decimal(20, 8) NOT NULL COMMENT '最大单价限额',
  `charge_ratio` decimal(20, 8) NOT NULL COMMENT '手续费比率',
  `ad_pay` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告支付方式-多个时用字符隔开',
  `ad_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告类型-买/卖',
  `ad_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告状态',
  `ad_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '广告备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_ad_number`(`ad_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_ad_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_ad_handle_log`;
CREATE TABLE `otc_ad_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ad_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告流水号',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员用户id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改前状态',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改后状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币广告操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal`;
CREATE TABLE `otc_appeal`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉记录id',
  `order_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单流水号',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal_detail
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal_detail`;
CREATE TABLE `otc_appeal_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉图片id',
  `appeal_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉记录id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `appeal_role` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉方角色-广告方申诉、订单方申诉',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉说明',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal_handle_log`;
CREATE TABLE `otc_appeal_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉日志id',
  `order_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉订单流水号',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作管理员ip地址',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '处理后的订单状态',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉处理日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_appeal_img
-- ----------------------------
DROP TABLE IF EXISTS `otc_appeal_img`;
CREATE TABLE `otc_appeal_img`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `appeal_detail_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉详情记录id',
  `appeal_img_url` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申诉图片路径',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币申诉图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_bill
-- ----------------------------
DROP TABLE IF EXISTS `otc_bill`;
CREATE TABLE `otc_bill`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对账id',
  `record_number` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单或广告流水号',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `freezeBalance` decimal(20, 8) NOT NULL COMMENT '冻结余额',
  `freeBalance` decimal(20, 8) NOT NULL COMMENT '可用余额',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种',
  `bill_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金变动类型-发布、成交等等',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币资金对账表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_coin
-- ----------------------------
DROP TABLE IF EXISTS `otc_coin`;
CREATE TABLE `otc_coin`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种配置id',
  `coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币',
  `unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币',
  `coin_net` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币主网标识',
  `coin_decimal` tinyint(4) NOT NULL COMMENT '基本货币小数长度',
  `unit_decimal` tinyint(4) NOT NULL COMMENT '二级货币小数长度',
  `coin_service_charge` decimal(20, 8) NOT NULL COMMENT '币种手续费',
  `rank` tinyint(4) NOT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币币种配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_coin_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_coin_handle_log`;
CREATE TABLE `otc_coin_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `coin_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改的币种记录id',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员用户id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的币种',
  `after_coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的币种',
  `before_unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的二级货币',
  `after_unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的二级货币',
  `before_coin_net` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的币种主网标识',
  `after_coin_net` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的币种主网标识',
  `before_coin_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改前的币种小数长度',
  `after_coin_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改后的币种小数长度',
  `before_unit_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改前的二级货币小数长度',
  `after_unit_decimal` tinyint(4) NULL DEFAULT NULL COMMENT '修改后的二级货币小数长度',
  `before_coin_service_charge` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的手续费',
  `after_coin_service_charge` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的手续费',
  `before_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的币种状态',
  `after_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的币种状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币币种操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_config
-- ----------------------------
DROP TABLE IF EXISTS `otc_config`;
CREATE TABLE `otc_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置id',
  `data_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息key',
  `data_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息value',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_config_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_config_handle_log`;
CREATE TABLE `otc_config_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员用户id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `data_key` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改的key',
  `before_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的值',
  `after_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的值',
  `before_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币配置操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_deal_stats
-- ----------------------------
DROP TABLE IF EXISTS `otc_deal_stats`;
CREATE TABLE `otc_deal_stats`  (
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `ad_trans_num` int(11) NOT NULL DEFAULT 0 COMMENT '广告交易次数',
  `ad_mark_num` int(11) NOT NULL DEFAULT 0 COMMENT '广告成交次数',
  `order_sell_num` int(11) NOT NULL DEFAULT 0 COMMENT '卖单成交次数',
  `order_buy_num` int(11) NOT NULL DEFAULT 0 COMMENT '买单成交次数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币用户成交信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_apply
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_apply`;
CREATE TABLE `otc_market_apply`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '冻结代币',
  `amount` decimal(20, 8) NOT NULL COMMENT '冻结金额',
  `apply_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请类型(MARKET：申请市商、CANCEL：申请取消市商)',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请状态(NEW：待处理、AGREE：同意、REJECT：驳回)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_apply_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_apply_handle_log`;
CREATE TABLE `otc_market_apply_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `market_apply_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '市商申请记录id',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_freeze
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_freeze`;
CREATE TABLE `otc_market_freeze`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `market_apply_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市商申请记录id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '冻结代币',
  `amount` decimal(20, 8) NOT NULL COMMENT '冻结数量',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_unique`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_user
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_user`;
CREATE TABLE `otc_market_user`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态 : (NOTMARKET：未认证、MARKET：认证、CANCELING：取消中)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_unique`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_market_user_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_market_user_handle_log`;
CREATE TABLE `otc_market_user_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_order
-- ----------------------------
DROP TABLE IF EXISTS `otc_order`;
CREATE TABLE `otc_order`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `order_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单流水号',
  `ad_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '广告id',
  `coin_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币',
  `unit_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币',
  `buy_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买方用户id',
  `buy_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '买方操作状态',
  `sell_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卖方用户id',
  `sell_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卖方操作状态',
  `amount` decimal(20, 8) NOT NULL COMMENT '交易数量',
  `price` decimal(20, 8) NOT NULL COMMENT '交易单价',
  `turnover` decimal(20, 8) NOT NULL COMMENT '交易金额',
  `charge_ratio` decimal(20, 8) NOT NULL COMMENT '手续费比率',
  `order_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单类型-买/卖',
  `order_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态-',
  `order_pay_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付方式',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_number`(`order_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_user_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `otc_user_handle_log`;
CREATE TABLE `otc_user_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `handle_number` char(23) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作的订单或广告流水号',
  `handle_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
  `handle_number_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作订单类型-广告或订单',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '法币用户操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for otc_user_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `otc_user_pay_info`;
CREATE TABLE `otc_user_pay_info`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `pay_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付类型:----微信WX，支付宝ZFB，银行卡BANK',
  `account_info` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信昵称、支付宝昵称',
  `collection_code_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款码地址',
  `bank_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `bank_user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡持卡人真实姓名',
  `bank_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户交易方式信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
