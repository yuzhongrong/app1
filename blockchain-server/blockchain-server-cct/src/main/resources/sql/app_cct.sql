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

 Date: 17/06/2020 19:44:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_cct_bill
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_bill`;
CREATE TABLE `app_cct_bill`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种名称',
  `plan_money` decimal(20, 8) NOT NULL COMMENT '预计金额',
  `real_money` decimal(20, 8) NOT NULL COMMENT '实际金额',
  `service_charge` decimal(20, 8) NOT NULL COMMENT '手续费',
  `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对账标签，因为什么操作而产生金额变化',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '充值提现对账表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_coin
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_coin`;
CREATE TABLE `app_cct_coin`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-二级货币',
  `coin_net` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币主网标识',
  `unit_net` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币主网标识',
  `coin_decimals` tinyint(4) NOT NULL COMMENT '基本货币的小数位',
  `unit_decimals` tinyint(4) NOT NULL COMMENT '二级货币的小数位',
  `rank` tinyint(4) NOT NULL DEFAULT 0 COMMENT '排序,值越小排序越靠前',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否可用，可用(Y)，不可用(N)',
  `tag` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '币币交易币种表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_config
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_config`;
CREATE TABLE `app_cct_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `data_tag` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息说明',
  `data_key` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息的key',
  `data_value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息的value',
  `data_status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置信息状态,可用(Y)、不可用(N)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '币币交易配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_match_config
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_match_config`;
CREATE TABLE `app_cct_match_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币',
  `min_percent` decimal(20, 8) NOT NULL COMMENT '最小跌幅',
  `max_percent` decimal(20, 8) NOT NULL COMMENT '最大涨幅',
  `create_time` date NOT NULL COMMENT '创建时间',
  `modify_time` date NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_match_config_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_match_config_handle_log`;
CREATE TABLE `app_cct_match_config_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作者ip地址',
  `handle_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `before_coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的基本货币',
  `after_coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的基本货币',
  `before_unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的二级货币',
  `after_unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的二级货币',
  `before_min_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最小跌幅',
  `after_min_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最小跌幅',
  `before_max_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最大涨幅',
  `after_max_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最大涨幅',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`, `handle_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_publish_order
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_publish_order`;
CREATE TABLE `app_cct_publish_order`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `unit_price` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '单价',
  `total_num` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '发布数量',
  `last_num` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '剩余数量',
  `total_turnover` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '发布交易额',
  `last_turnover` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '剩余交易额',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-二级货币',
  `order_status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态:新建(NEW),已撮合(MATCH),已完成(FINISH),撤单(CANCEL)',
  `publish_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布类型 限价(LIMIT) 市价(MARKET)',
  `order_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单类型 买(BUY) 卖(SELL)',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发布表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_publish_order_copy1
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_publish_order_copy1`;
CREATE TABLE `app_cct_publish_order_copy1`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `unit_price` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '单价',
  `total_num` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '发布数量',
  `last_num` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '剩余数量',
  `total_turnover` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '发布交易额',
  `last_turnover` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '剩余交易额',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-二级货币',
  `order_status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态:新建(NEW),已撮合(MATCH),已完成(FINISH),撤单(CANCEL)',
  `publish_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布类型 限价(LIMIT) 市价(MARKET)',
  `order_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单类型 买(BUY) 卖(SELL)',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '版本',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发布表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_trading_detail
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_trading_detail`;
CREATE TABLE `app_cct_trading_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '成交记录id',
  `record_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '成交记录id',
  `publish_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布id',
  `total_amount` decimal(20, 8) NOT NULL COMMENT '成交交易额/数量，根据买卖成交的金额不一样',
  `real_amount` decimal(20, 8) NOT NULL COMMENT '实际到账',
  `charge_ratio` decimal(7, 6) NOT NULL COMMENT '手续费比例',
  `service_charge` decimal(20, 8) NOT NULL COMMENT '手续费',
  `unit_price` decimal(20, 8) NOT NULL COMMENT '成交单价',
  `trading_num` decimal(20, 8) NOT NULL COMMENT '撮合数量',
  `trading_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '撮合类型',
  `coin_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '到账的币种名称',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '撮合详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for app_cct_trading_record
-- ----------------------------
DROP TABLE IF EXISTS `app_cct_trading_record`;
CREATE TABLE `app_cct_trading_record`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '成交记录id',
  `maker_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '挂单id',
  `taker_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '吃单id',
  `maker_price` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '挂单单价',
  `taker_price` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '吃单单价',
  `trading_num` decimal(20, 8) NOT NULL DEFAULT 0.00000000 COMMENT '成交数量',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对-二级货币',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `trading_type` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '*用于行情识别成交订单买卖类型，对于币币程序并无作用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '撮合记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
