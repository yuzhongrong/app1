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

 Date: 17/06/2020 19:48:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bot_currency_config
-- ----------------------------
DROP TABLE IF EXISTS `bot_currency_config`;
CREATE TABLE `bot_currency_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_pair` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `k_change_percent` float NOT NULL COMMENT 'K线配置：日涨跌幅',
  `k_max_change_percent` float NOT NULL COMMENT 'K线配置：涨跌最大幅度（偏差）',
  `k_day_total_amount` float NOT NULL COMMENT 'K线配置：日发行总量',
  `k_max_price` float NOT NULL COMMENT 'K线配置：最高单价',
  `k_min_price` float NOT NULL COMMENT 'K线配置：最低单价',
  `buy_max_price` float NOT NULL COMMENT '深度配置：买盘最高单价',
  `buy_min_price` float NOT NULL COMMENT '深度配置：买盘最低单价',
  `buy_price_percent` float NOT NULL COMMENT '深度配置：买盘单价范围',
  `buy_total_amount` float NOT NULL COMMENT '深度配置：买盘发行总量',
  `sell_max_price` float NOT NULL COMMENT '深度配置：卖盘最高单价',
  `sell_min_price` float NOT NULL COMMENT '深度配置：卖盘最低单价',
  `sell_price_percent` float NOT NULL COMMENT '深度配置：卖盘单价范围',
  `sell_total_amount` float NOT NULL COMMENT '深度配置：卖盘发行总量',
  `price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单价范围规则类型：（定价范围：PRICE，最新成交价波动范围：PERCENT）',
  `create_time` datetime(0) NOT NULL,
  `modify_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bot_currency_config_copy1
-- ----------------------------
DROP TABLE IF EXISTS `bot_currency_config_copy1`;
CREATE TABLE `bot_currency_config_copy1`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_pair` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `k_change_percent` float NOT NULL COMMENT 'K线配置：日涨跌幅',
  `k_max_change_percent` float NOT NULL COMMENT 'K线配置：涨跌最大幅度（偏差）',
  `k_day_total_amount` float NOT NULL COMMENT 'K线配置：日发行总量',
  `k_max_price` float NOT NULL COMMENT 'K线配置：最高单价',
  `k_min_price` float NOT NULL COMMENT 'K线配置：最低单价',
  `buy_max_price` float NOT NULL COMMENT '深度配置：买盘最高单价',
  `buy_min_price` float NOT NULL COMMENT '深度配置：买盘最低单价',
  `buy_price_percent` float NOT NULL COMMENT '深度配置：买盘单价范围',
  `buy_total_amount` float NOT NULL COMMENT '深度配置：买盘发行总量',
  `sell_max_price` float NOT NULL COMMENT '深度配置：卖盘最高单价',
  `sell_min_price` float NOT NULL COMMENT '深度配置：卖盘最低单价',
  `sell_price_percent` float NOT NULL COMMENT '深度配置：卖盘单价范围',
  `sell_total_amount` float NOT NULL COMMENT '深度配置：卖盘发行总量',
  `price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单价范围规则类型：（定价范围：PRICE，最新成交价波动范围：PERCENT）',
  `create_time` datetime(0) NOT NULL,
  `modify_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bot_currency_config_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `bot_currency_config_handle_log`;
CREATE TABLE `bot_currency_config_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_pair` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对',
  `handle_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型（UPDATE更新、INSERT新增）',
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_k_change_percent` float NULL DEFAULT NULL COMMENT '修改前的K线配置：日涨跌幅',
  `after_k_change_percent` float NULL DEFAULT NULL COMMENT '修改后的K线配置：日涨跌幅',
  `before_k_max_change_percent` float NULL DEFAULT NULL COMMENT '修改前的K线配置：涨跌最大幅度（偏差）',
  `after_k_max_change_percent` float NULL DEFAULT NULL COMMENT '修改后的K线配置：涨跌最大幅度（偏差）',
  `before_k_day_total_amount` float NULL DEFAULT NULL COMMENT '修改前的K线配置：日发行总量',
  `after_k_day_total_amount` float NULL DEFAULT NULL COMMENT '修改后的K线配置：日发行总量',
  `before_k_max_price` float NULL DEFAULT NULL COMMENT '修改前的K线配置：最高单价',
  `after_k_max_price` float NULL DEFAULT NULL COMMENT '修改后的K线配置：最高单价',
  `before_k_min_price` float NULL DEFAULT NULL COMMENT '修改前的K线配置：最低单价',
  `after_k_min_price` float NULL DEFAULT NULL COMMENT '修改后的K线配置：最低单价',
  `before_buy_max_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘最高单价',
  `after_buy_max_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘最高单价',
  `before_buy_min_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘最低单价',
  `after_buy_min_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘最低单价',
  `before_buy_price_percent` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘单价范围',
  `after_buy_price_percent` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘单价范围',
  `before_buy_total_amount` float NULL DEFAULT NULL COMMENT '修改前的深度配置：买盘发行总量',
  `after_buy_total_amount` float NULL DEFAULT NULL COMMENT '修改后的深度配置：买盘发行总量',
  `before_sell_max_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘最高单价',
  `after_sell_max_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘最高单价',
  `before_sell_min_price` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘最低单价',
  `after_sell_min_price` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘最低单价',
  `before_sell_price_percent` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘单价范围',
  `after_sell_price_percent` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘单价范围',
  `before_sell_total_amount` float NULL DEFAULT NULL COMMENT '修改前的深度配置：卖盘发行总量',
  `after_sell_total_amount` float NULL DEFAULT NULL COMMENT '修改后的深度配置：卖盘发行总量',
  `before_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的单价范围规则类型',
  `after_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的单价范围规则类型',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bot_match_config
-- ----------------------------
DROP TABLE IF EXISTS `bot_match_config`;
CREATE TABLE `bot_match_config`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基本货币',
  `unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '二级货币',
  `min_price` decimal(20, 8) NOT NULL COMMENT '最低单价',
  `max_price` decimal(20, 8) NOT NULL COMMENT '最高单价',
  `min_percent` decimal(20, 8) NOT NULL COMMENT '最新成交价跌幅',
  `max_percent` decimal(20, 8) NOT NULL COMMENT '最新成交价涨幅',
  `price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单价范围规则类型：（定价范围：PRICE，最新成交价波动范围：PERCENT）',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态：（启用：Y，禁用：N）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bot_match_config_handle_log
-- ----------------------------
DROP TABLE IF EXISTS `bot_match_config_handle_log`;
CREATE TABLE `bot_match_config_handle_log`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员id',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `before_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的用户id',
  `after_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的用户id',
  `before_coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的基本货币',
  `after_coin_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的基本货币',
  `before_unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的二级货币',
  `after_unit_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的二级货币',
  `before_min_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最低单价',
  `after_min_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最低单价',
  `before_max_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最高单价',
  `after_max_price` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最高单价',
  `before_min_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最新成交价跌幅',
  `after_min_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最新成交价跌幅',
  `before_max_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改前的最新成交价涨幅',
  `after_max_percent` decimal(20, 8) NULL DEFAULT NULL COMMENT '修改后的最新成交价涨幅',
  `before_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的单价范围类型',
  `after_price_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的单价范围类型',
  `before_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改前的状态',
  `after_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改后的状态',
  `handle_type` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型（更新：UPDATE，新增：INSERT）',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
