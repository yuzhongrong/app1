/*
 Navicat Premium Data Transfer

 Source Server         : fkex-wallet
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 127.0.0.1:3306
 Source Schema         : fkex

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 17/06/2020 19:41:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dapp_currency
-- ----------------------------
DROP TABLE IF EXISTS `dapp_currency`;
CREATE TABLE `dapp_currency`  (
  `currency_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `currency_name_cn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数字货币类型，BTC、ETH、EOS',
  `currency_name_hk` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的代币唯一标识',
  `currency_name_en` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的代币唯一标识',
  `status` tinyint(4) NULL DEFAULT NULL,
  `order_by` int(11) NULL DEFAULT NULL,
  `issue_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行时间',
  `total_supply` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行总量',
  `total_circulation` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流通量',
  `ico_amount` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '众筹价格',
  `white_paper` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '白皮书',
  `official_website` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '官网',
  `block_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区块查询地址',
  `descr_cn` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '中文简介',
  `descr_en` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '英文简介',
  `descr_hk` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '繁体简介',
  `currency_type` tinyint(1) NULL DEFAULT NULL COMMENT '是否是主币（1：主币，0：非主币）',
  PRIMARY KEY (`currency_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_currency_market
-- ----------------------------
DROP TABLE IF EXISTS `dapp_currency_market`;
CREATE TABLE `dapp_currency_market`  (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `currency_pair` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货币对id',
  `amount` decimal(32, 18) NULL DEFAULT NULL,
  `timestamp` bigint(8) NULL DEFAULT NULL,
  `total` decimal(32, 18) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `currency_pair`(`currency_pair`) USING BTREE,
  INDEX `timestamp`(`timestamp`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 213338424 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_currency_pair
-- ----------------------------
DROP TABLE IF EXISTS `dapp_currency_pair`;
CREATE TABLE `dapp_currency_pair`  (
  `currency_pair` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币对',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '1可用，0禁用',
  `order_by` int(11) NULL DEFAULT 1 COMMENT '币对排序',
  `is_home` tinyint(1) NULL DEFAULT 0 COMMENT '是否显示在首页',
  `is_cct` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`currency_pair`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
