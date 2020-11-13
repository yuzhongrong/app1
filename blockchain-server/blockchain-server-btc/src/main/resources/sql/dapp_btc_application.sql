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

 Date: 17/06/2020 19:34:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dapp_btc_application
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_application`;
CREATE TABLE `dapp_btc_application`  (
  `app_id` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币币交易：CCT',
  `app_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_block_number
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_block_number`;
CREATE TABLE `dapp_btc_block_number`  (
  `block_number` int(11) NOT NULL COMMENT '区块号',
  `status` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '同步区块状态',
  `create_time` datetime(0) NOT NULL COMMENT '同步时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`block_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '已同步的区块' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_clearing_corr
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_clearing_corr`;
CREATE TABLE `dapp_btc_clearing_corr`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末表记录id',
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币名称',
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包类型',
  `pre_balance` decimal(20, 8) NOT NULL COMMENT '更正前资金',
  `after_balance` decimal(20, 8) NOT NULL COMMENT '更正后资金',
  `system_user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人id',
  `clearing_time` datetime(0) NOT NULL COMMENT '结算日期',
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '更正记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_clearing_count_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_clearing_count_detail`;
CREATE TABLE `dapp_btc_clearing_count_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末记录id',
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变动类型',
  `transfer_amount` decimal(20, 8) NOT NULL COMMENT '变动总金额,正负数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流水账记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_clearing_count_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_clearing_count_total`;
CREATE TABLE `dapp_btc_clearing_count_total`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币符号',
  `balance` decimal(20, 8) NOT NULL COMMENT '当前资金',
  `real_balance` decimal(20, 8) NOT NULL COMMENT '实际资金',
  `diff_balance` decimal(20, 8) NOT NULL COMMENT '资金偏差',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态,新建(NEW)、已更正(CORR)',
  `pre_time` datetime(0) NULL DEFAULT NULL COMMENT '期初时间(上一次结算时间)',
  `pre_balance` decimal(20, 8) NOT NULL COMMENT '期初金额(上一次结算金额)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '期初期末表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_clearing_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_clearing_detail`;
CREATE TABLE `dapp_btc_clearing_detail`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `total_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '期初期末记录id',
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变动类型',
  `transfer_amount` decimal(20, 8) NOT NULL COMMENT '变动总金额,正负数',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流水账记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_clearing_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_clearing_total`;
CREATE TABLE `dapp_btc_clearing_total`  (
  `id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包地址',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币符号',
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包类型',
  `balance` decimal(20, 8) NOT NULL COMMENT '当前资金',
  `real_balance` decimal(20, 8) NOT NULL COMMENT '实际资金',
  `diff_balance` decimal(20, 8) NOT NULL COMMENT '资金偏差',
  `wallet_last_time` datetime(0) NOT NULL COMMENT '最后一次资金变动时间',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态,新建(NEW)、已更正(CORR)',
  `pre_time` datetime(0) NOT NULL COMMENT '期初时间(上一次结算时间)',
  `pre_balance` decimal(20, 8) NOT NULL COMMENT '期初金额(上一次结算金额)',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '期初期末表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_collection_transfer
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_collection_transfer`;
CREATE TABLE `dapp_btc_collection_transfer`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
  `hash` varchar(66) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '充值或提现需保存的hash值',
  `from_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付人地址',
  `to_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收款人地址',
  `amount` decimal(36, 18) NOT NULL COMMENT '金额',
  `token_addr` varchar(44) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gas_price` decimal(36, 18) NULL DEFAULT NULL COMMENT '收取的手续费',
  `status` tinyint(4) NOT NULL COMMENT '状态，0失败，1成功，5打包',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'ETH归集历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_token
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_token`;
CREATE TABLE `dapp_btc_token`  (
  `token_id` int(11) NOT NULL COMMENT 'BTC为0，USDT为31',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'BTC，USDT',
  `issue_time` datetime(0) NULL DEFAULT NULL COMMENT '发行时间',
  `total_supply` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发行总量',
  `total_circulation` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流通量',
  `descr` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`token_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '币种表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_transfer_auditing
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_transfer_auditing`;
CREATE TABLE `dapp_btc_transfer_auditing`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sys_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台操作用户id',
  `ip_addr` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台操作用户的ip地址',
  `transfer_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作的提现记录id',
  `transfer_status` tinyint(4) NOT NULL COMMENT '1成功，2待初审提币，3待复审提币，4待出币，5已出币，6出币失败(1为出币成功)，7审核不通过',
  `create_time` datetime(0) NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提币审核记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_wallet
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_wallet`;
CREATE TABLE `dapp_btc_wallet`  (
  `addr` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'btc托管钱包地址',
  `token_id` int(11) NOT NULL COMMENT 'BTC为0，USDT为31',
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `balance` decimal(36, 18) NOT NULL COMMENT '余额',
  `free_balance` decimal(36, 18) NOT NULL COMMENT '可用余额',
  `freeze_balance` decimal(36, 18) NOT NULL COMMENT '冻结余额',
  `wallet_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'CCT:币币交易钱包',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`addr`, `token_id`) USING BTREE,
  INDEX `index_user_open_id`(`user_open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '托管钱包表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_wallet_blocked_detail
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_wallet_blocked_detail`;
CREATE TABLE `dapp_btc_wallet_blocked_detail`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `wallet_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包唯一标识(地址|id)',
  `token_symbol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币名称',
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `opt_total` decimal(36, 18) NOT NULL COMMENT '操作数量',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型：冻结(BLOCK)、解冻(UNBLOCK)',
  `system_user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作的后台管理人员id',
  `ip_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作的ip地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '钱包冻结明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_wallet_blocked_total
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_wallet_blocked_total`;
CREATE TABLE `dapp_btc_wallet_blocked_total`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `wallet_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '钱包唯一标识(地址|id)',
  `token_symbol` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代币名称',
  `user_open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `blocked_total` decimal(36, 18) NOT NULL COMMENT '冻结的总额，解冻时不能超过该值',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '修改时间',
  UNIQUE INDEX `uk_wallet_uid_token_symbol`(`wallet_uid`, `token_symbol`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '钱包冻结总表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_wallet_key
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_wallet_key`;
CREATE TABLE `dapp_btc_wallet_key`  (
  `addr` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `private_key` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '托管钱包私钥',
  PRIMARY KEY (`addr`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_wallet_out
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_wallet_out`;
CREATE TABLE `dapp_btc_wallet_out`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `addr` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金账户地址',
  `token_id` int(11) NOT NULL COMMENT '币种id',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种符号',
  `private_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资金账户私钥',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提现审核通过需要输入的密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资金钱包备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '提现资金钱包(用于用户提现)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dapp_btc_wallet_transfer
-- ----------------------------
DROP TABLE IF EXISTS `dapp_btc_wallet_transfer`;
CREATE TABLE `dapp_btc_wallet_transfer`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hash` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '充值或提现需保存的hash值',
  `from_addr` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付人地址',
  `to_addr` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收款人地址',
  `amount` decimal(36, 18) NOT NULL COMMENT '金额',
  `token_id` int(11) NOT NULL COMMENT 'BTC为0，USDT为31',
  `token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'BTC，USDT',
  `gas_price` decimal(36, 18) NULL DEFAULT NULL COMMENT '收取的手续费',
  `gas_token_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手续费币种类型，BTC、ETH、EOS',
  `gas_token_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手续费币种名称',
  `gas_token_symbol` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `transfer_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型：OUT提现，IN充值，CCT币币交易，GAS手续费',
  `status` tinyint(4) NOT NULL COMMENT '0失败，1成功，2待初审提币，3待复审提币，4待出币，5已出币，6出币失败(1为出币成功)，7审核不通过',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户钱包历史记录' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
